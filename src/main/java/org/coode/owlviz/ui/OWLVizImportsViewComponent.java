package org.coode.owlviz.ui;

import org.protege.editor.core.ui.util.ComponentFactory;
import org.protege.editor.owl.model.OWLEditorKitOntologyShortFormProvider;
import org.protege.editor.owl.model.event.EventType;
import org.protege.editor.owl.model.event.OWLModelManagerListener;
import org.protege.editor.owl.ui.renderer.OWLSystemColors;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;

import org.coode.owlviz.model.OWLOntologyImportsGraphModel;
import org.coode.owlviz.util.graph.controller.Controller;
import org.coode.owlviz.util.graph.controller.impl.DefaultController;
import org.coode.owlviz.util.graph.event.NodeClickedEvent;
import org.coode.owlviz.util.graph.event.NodeClickedListener;
import org.coode.owlviz.util.graph.graph.Node;
import org.coode.owlviz.util.graph.renderer.NodeLabelRenderer;
import org.coode.owlviz.util.graph.renderer.impl.DefaultNodeRenderer;
import org.coode.owlviz.util.graph.ui.GraphView;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.util.FilteringOWLOntologyChangeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 03-Oct-2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class OWLVizImportsViewComponent extends AbstractOWLViewComponent {

    private Controller controller;

    private OWLModelManagerListener owlModelManagerListener;

    private OWLOntologyChangeListener changeListener;

    private boolean dirty;

    protected void initialiseOWLView() throws Exception {
        setLayout(new BorderLayout());
        controller = new DefaultController(
                new OWLOntologyImportsGraphModel(getOWLModelManager()));
        setupRenderers();
        setupListeners();
        add(ComponentFactory.createScrollPane(controller.getGraphView()), BorderLayout.CENTER);
        controller.getVisualisedObjectManager().showObjects(getOWLModelManager().getOntologies().toArray());
        dirty = true;
        addHierarchyListener(e -> {
            if(isShowing() && dirty) {
                // Layout
                rebuild();
            }
        });
    }

    private void setupRenderers() {
        controller.setNodeLabelRenderer(node -> {
            OWLOntology ont = (OWLOntology) node.getUserObject();
            OWLEditorKitOntologyShortFormProvider sfp = new OWLEditorKitOntologyShortFormProvider(getOWLEditorKit());
            return sfp.getShortForm(ont);
        });
        controller.setNodeRenderer(new DefaultNodeRenderer(controller) {

            private Color lineColor = Color.LIGHT_GRAY;

            private Color activeOntologyFillColor = new Color(205, 220, 243);

            public Color activeOntologiesLineColor = OWLSystemColors.getOWLOntologyColor();;

            protected Color getFillColor(Node node) {
                if (node.getUserObject().equals(getOWLModelManager().getActiveOntology())) {
                    return activeOntologyFillColor;
                }
                return Color.WHITE;
            }

            protected Color getLineColor(Node node) {
                if (getOWLModelManager().getActiveOntologies().contains(node.getUserObject())){
                    return activeOntologiesLineColor;
                }
                return lineColor;
            }
        });
    }

    private void setupListeners() {
        GraphView graphView = controller.getGraphView();
        graphView.addNodeClickedListener(evt -> {
            if (SwingUtilities.isRightMouseButton(evt.getMouseEvent())) {
                // Show right click menu
                showPopupMenu(evt);
            }
            else if (evt.getMouseEvent().getClickCount() == 2){
                OWLOntology ont = (OWLOntology) evt.getNode().getUserObject();
                getOWLModelManager().setActiveOntology(ont);
            }
        });

        changeListener = new FilteringOWLOntologyChangeListener() {
            public void visit(OWLImportsDeclaration axiom) {
                rebuild();
            }
        };


        getOWLModelManager().addOntologyChangeListener(changeListener);
        getOWLModelManager().addListener(owlModelManagerListener = event -> {
            if(event.isType(EventType.ACTIVE_ONTOLOGY_CHANGED) || event.isType(EventType.ONTOLOGY_RELOADED)) {
                rebuild();
            }
        });
    }

    private void rebuild() {
        // Only rebuild if we are showing
        if(isShowing()) {
            ((OWLOntologyImportsGraphModel) controller.getGraphModel()).rebuild();
            controller.getVisualisedObjectManager().hideAll();
            controller.getVisualisedObjectManager().showObjects(getOWLModelManager().getOntologies().toArray());
            dirty = false;
        }
        else {
            // Not showing, so mark as dirty
            dirty = true;
        }
    }

    private void showPopupMenu(final NodeClickedEvent evt) {
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new AbstractAction("Set as active ontology") {
            public void actionPerformed(ActionEvent e) {
                OWLOntology ont = (OWLOntology) evt.getNode().getUserObject();
                getOWLModelManager().setActiveOntology(ont);
            }
        });
        popupMenu.show(controller.getGraphView(), evt.getMouseEvent().getX(), evt.getMouseEvent().getY());
    }

    protected void disposeOWLView() {
        getOWLModelManager().removeOntologyChangeListener(changeListener);
        getOWLModelManager().removeListener(owlModelManagerListener);
    }

}
