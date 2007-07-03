package uk.ac.man.cs.mig.coode.owlviz.command;

import uk.ac.man.cs.mig.coode.owlviz.ui.ClassRadiusDialog;
import uk.ac.man.cs.mig.coode.owlviz.ui.OWLVizIcons;
import uk.ac.man.cs.mig.coode.owlviz.ui.OWLVizView;
import uk.ac.man.cs.mig.util.graph.ui.GraphComponent;
import uk.ac.man.cs.mig.util.okcanceldialog.OKCancelDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;

import org.protege.editor.owl.ui.view.OWLSelectionViewAction;
import org.protege.editor.owl.model.OWLModelManager;
import org.semanticweb.owl.model.OWLClass;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 11, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class ShowClassCommand extends OWLSelectionViewAction {

    private OWLVizView view;
    private OWLModelManager kb;
    private Frame owner;


    public ShowClassCommand(OWLVizView view,
                            OWLModelManager kb,
                            Frame owner) {
        super("Show class", OWLVizIcons.getIcon(OWLVizIcons.SHOW_CLASS_ICON));
        this.putValue(AbstractAction.SHORT_DESCRIPTION, "Show class");
        this.view = view;
        this.kb = kb;
        this.owner = owner;
    }


    public void updateState() {
        setEnabled(true);
    }

    public void dispose() {
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
        ClassRadiusDialog dlg = new ClassRadiusDialog(this.owner, true);
        int retVal = dlg.showDialog();
        if(retVal == OKCancelDialog.OPTION_APPROVE) {
            Object selObj = view.getSelectionModel().getSelectedClass();
            if(selObj != null) {
                if(selObj instanceof OWLClass) {
                    int radius = dlg.getClassRadius();
                    int supersSubsOptions = dlg.getSupersSubsOption();
                    for(Iterator it = view.getGraphComponents().iterator(); it.hasNext(); ) {
                        GraphComponent curGraphComponent = (GraphComponent) it.next();
                        if(supersSubsOptions == ClassRadiusDialog.SUPERS_AND_SUBS) {
                            curGraphComponent.getVisualisedObjectManager().showObject(selObj, radius, OWLClass.class);;
                        }
                        else if(supersSubsOptions == ClassRadiusDialog.SUPERS_ONLY) {
                            curGraphComponent.getVisualisedObjectManager().showParents(selObj, radius, OWLClass.class);
                        }
                        else {
                            curGraphComponent.getVisualisedObjectManager().showChildren(selObj, radius, OWLClass.class);
                        }
                    }
                }
            }
        }
    }
}
