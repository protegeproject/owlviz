package org.coode.owlviz.command;

import org.coode.owlviz.ui.OWLVizIcons;
import org.coode.owlviz.ui.OWLVizViewI;
import org.coode.owlviz.util.graph.ui.GraphComponent;
import org.protege.editor.owl.ui.view.OWLSelectionViewAction;
import org.semanticweb.owlapi.model.OWLClass;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;

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

    /**
     *
     */
    private static final long serialVersionUID = -1482916648549593173L;

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
        if (selObj != null) {
            for (Iterator it = view.getGraphComponents().iterator(); it.hasNext(); ) {
                GraphComponent curGraphComponent = (GraphComponent) it.next();
                curGraphComponent.getVisualisedObjectManager().showChildren(selObj, OWLClass.class);
            }
        }
    }

}
