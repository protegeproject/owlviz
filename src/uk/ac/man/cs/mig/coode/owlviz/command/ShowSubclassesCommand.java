package uk.ac.man.cs.mig.coode.owlviz.command;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.AbstractAction;

import org.protege.editor.owl.ui.view.OWLSelectionViewAction;
import org.semanticweb.owl.model.OWLClass;

import uk.ac.man.cs.mig.coode.owlviz.ui.OWLVizIcons;
import uk.ac.man.cs.mig.coode.owlviz.ui.OWLVizViewI;
import uk.ac.man.cs.mig.util.graph.ui.GraphComponent;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 5, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class ShowSubclassesCommand extends OWLSelectionViewAction {

    private OWLVizViewI view;

    public ShowSubclassesCommand(OWLVizViewI view) {
        super("Show children", OWLVizIcons.getIcon(OWLVizIcons.SHOW_SUBCLASSES_ICON));
        this.putValue(AbstractAction.SHORT_DESCRIPTION, "Show children");
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
        Object selObj;
        selObj = view.getSelectionModel().getSelectedClass();
        if(selObj != null) {
            for(Iterator it = view.getGraphComponents().iterator(); it.hasNext(); ) {
                GraphComponent curGraphComponent = (GraphComponent) it.next();
                curGraphComponent.getVisualisedObjectManager().showChildren(selObj, OWLClass.class);
            }
        }
    }

}
