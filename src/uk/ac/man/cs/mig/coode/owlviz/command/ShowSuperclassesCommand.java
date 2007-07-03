package uk.ac.man.cs.mig.coode.owlviz.command;

import uk.ac.man.cs.mig.coode.owlviz.ui.OWLVizIcons;
import uk.ac.man.cs.mig.coode.owlviz.ui.OWLVizView;
import uk.ac.man.cs.mig.util.graph.ui.GraphComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;

import org.semanticweb.owl.model.OWLClass;
import org.protege.editor.owl.ui.view.OWLSelectionViewAction;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 11, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class ShowSuperclassesCommand extends OWLSelectionViewAction {

    private OWLVizView view;

    public ShowSuperclassesCommand(OWLVizView view) {
        super("Show parents", OWLVizIcons.getIcon(OWLVizIcons.SHOW_SUPERCLASSES_ICON));
        this.putValue(AbstractAction.SHORT_DESCRIPTION, "Show parents");
        putValue(AbstractAction.SHORT_DESCRIPTION, "Show parents");
        this.view = view;
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
        Object selObj = view.getSelectionModel().getSelectedClass();
        if(selObj != null) {
            for(Iterator it = view.getGraphComponents().iterator(); it.hasNext();) {
                GraphComponent graphComponent = (GraphComponent) it.next();
                graphComponent.getVisualisedObjectManager().showParents(selObj, OWLClass.class);
            }
        }
    }

}
