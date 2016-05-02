package org.coode.owlviz.command;

import org.coode.owlviz.ui.OWLVizIcons;
import org.coode.owlviz.ui.OWLVizViewI;
import org.coode.owlviz.util.graph.ui.GraphComponent;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.ui.view.OWLSelectionViewAction;
import org.semanticweb.owlapi.model.OWLClass;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Iterator;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Apr 5, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class ShowAllClassesCommand extends OWLSelectionViewAction {

    /**
     *
     */
    private static final long serialVersionUID = 2462262315449283945L;

    private static final int WARNING_LEVEL = 200;

    private OWLModelManager model;

    private OWLVizViewI view;


    public ShowAllClassesCommand(OWLVizViewI view, OWLModelManager owlModelManager) {
        super("Show all classes", OWLVizIcons.getIcon(OWLVizIcons.SHOW_ALL_CLASSES_ICON));
        this.view = view;
        this.model = owlModelManager;
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
        Object[] clses = getClses();
        if (clses.length > WARNING_LEVEL) {
            int ret = JOptionPane.showConfirmDialog(null, "Warning: Over " + WARNING_LEVEL + " classes" + " will be added to the display.  Are you sure" + " that you wish to continue?", "Warning!", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (ret == JOptionPane.NO_OPTION) {
                return;
            }
        }

        for (Iterator it = view.getGraphComponents().iterator(); it.hasNext(); ) {
            GraphComponent graphComponent = (GraphComponent) it.next();
            graphComponent.getVisualisedObjectManager().showObjects(clses);
        }
    }

    public Object[] getClses() {
        Collection<OWLClass> clses = model.getActiveOntology().getClassesInSignature();
        return clses.toArray();
    }
}