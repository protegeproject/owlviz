package org.coode.owlviz.command;

import org.coode.owlviz.ui.OWLVizIcons;
import org.coode.owlviz.ui.OWLVizViewI;
import org.coode.owlviz.util.graph.ui.GraphComponent;
import org.protege.editor.owl.ui.view.OWLSelectionViewAction;

import java.awt.event.ActionEvent;
import java.util.Iterator;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 11, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class HideClassCommand extends OWLSelectionViewAction {



    private OWLVizViewI view;


    public HideClassCommand(OWLVizViewI view) {
        super("Hide class", OWLVizIcons.getIcon(OWLVizIcons.HIDE_CLASS_ICON));
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
        if (selObj != null) {
            for (Iterator it = view.getGraphComponents().iterator(); it.hasNext(); ) {
                GraphComponent graphComponent = (GraphComponent) it.next();
                graphComponent.getVisualisedObjectManager().hideObject(selObj);
            }
        }
    }
}
