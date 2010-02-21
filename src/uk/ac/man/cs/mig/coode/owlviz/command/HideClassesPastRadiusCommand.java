package uk.ac.man.cs.mig.coode.owlviz.command;

import org.protege.editor.owl.ui.view.OWLSelectionViewAction;
import org.semanticweb.owlapi.model.OWLClass;
import uk.ac.man.cs.mig.coode.owlviz.ui.ClassRadiusDialog;
import uk.ac.man.cs.mig.coode.owlviz.ui.OWLVizIcons;
import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.okcanceldialog.OKCancelDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Mar 5, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class HideClassesPastRadiusCommand extends OWLSelectionViewAction {

    /**
     * 
     */
    private static final long serialVersionUID = 8091430865811611828L;
    private Controller assertedController;
    private Controller inferredController;
    private ClassRadiusDialog dlg;


    public HideClassesPastRadiusCommand(Controller assertedController,
                                        Controller inferredController) {
        super("Hide classes past radius", OWLVizIcons.getIcon(OWLVizIcons.HIDE_CLASSES_PAST_RADIUS_ICON));
        this.putValue(AbstractAction.SHORT_DESCRIPTION, "Hide classes past radius");
        this.assertedController = assertedController;
        this.inferredController = inferredController;
        dlg = new ClassRadiusDialog(null, false);
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
        Object selObj = assertedController.getGraphSelectionModel().getSelectedObject();
        if(selObj != null) {
            if(dlg.showDialog() == OKCancelDialog.OPTION_APPROVE) {
                int classRadius = dlg.getClassRadius();
                assertedController.getVisualisedObjectManager().hideObjects(selObj, classRadius, OWLClass.class);
                inferredController.getVisualisedObjectManager().hideObjects(selObj, classRadius, OWLClass.class);
            }
        }
    }
}

