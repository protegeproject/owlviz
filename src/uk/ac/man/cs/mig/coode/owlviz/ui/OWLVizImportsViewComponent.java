package uk.ac.man.cs.mig.coode.owlviz.ui;

import org.protege.editor.core.ui.util.ComponentFactory;
import org.protege.editor.owl.model.event.EventType;
import org.protege.editor.owl.model.event.OWLModelManagerChangeEvent;
import org.protege.editor.owl.model.event.OWLModelManagerListener;
import org.protege.editor.owl.ui.renderer.OWLSystemColors;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.util.FilteringOWLOntologyChangeListener;
import uk.ac.man.cs.mig.coode.owlviz.model.OWLOntologyImportsGraphModel;
import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.graph.controller.impl.DefaultController;
import uk.ac.man.cs.mig.util.graph.event.NodeClickedEvent;
import uk.ac.man.cs.mig.util.graph.event.NodeClickedListener;
import uk.ac.man.cs.mig.util.graph.graph.Node;
import uk.ac.man.cs.mig.util.graph.renderer.NodeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.impl.DefaultNodeRenderer;
import uk.ac.man.cs.mig.util.graph.ui.GraphView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;


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
        controller = new DefaultController(new OWLOntologyImportsGraphModel(getOWLModelManager()));
        setupRenderers();
        setupListeners();
        setLayout(new BorderLayout());
        add(ComponentFactory.createScrollPane(controller.getGraphView()), BorderLayout.CENTER);
        controller.getVisualisedObjectManager().showObjects(getOWLModelManager().getOntologies().toArray());
        dirty = true;
        addHierarchyListener(new HierarchyListener() {
            public void hierarchyChanged(HierarchyEvent e) {
                if(isShowing() && dirty) {
                    // Layout
                    rebuild();

                }
            }
        });
    }

    private void setupRenderers() {
        controller.setNodeLabelRenderer(new NodeLabelRenderer() {
            public String getLabel(Node node) {
                OWLOntology ont = (OWLOntology) node.getUserObject();
                // @@TODO what about anonymous ontologies?
                String label = ont.getOntologyID().getDefaultDocumentIRI().toString();
                label = label.substring(label.lastIndexOf("/") + 1);
                return label;

            }
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
        graphView.addNodeClickedListener(new NodeClickedListener() {
            public void nodeClicked(NodeClickedEvent evt) {
                if (SwingUtilities.isRightMouseButton(evt.getMouseEvent())) {
                    // Show right click menu
                    showPopupMenu(evt);
                }
                else if (evt.getMouseEvent().getClickCount() == 2){
                    OWLOntology ont = (OWLOntology) evt.getNode().getUserObject();
                    getOWLModelManager().setActiveOntology(ont);
                }
            }
        });

        changeListener = new FilteringOWLOntologyChangeListener() {
            public void visit(OWLImportsDeclaration axiom) {
                rebuild();
            }
        };


        getOWLModelManager().addOntologyChangeListener(changeListener);
        getOWLModelManager().addListener(owlModelManagerListener = new OWLModelManagerListener() {
            public void handleChange(OWLModelManagerChangeEvent event) {
                if(event.isType(EventType.ACTIVE_ONTOLOGY_CHANGED)) {
                    rebuild();
                }
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
//        popupMenu.add(new AbstractAction("Redundant imports") {
//            public void actionPerformed(ActionEvent e) {
//                OWLOntology ont = (OWLOntology) evt.getNode().getUserObject();
//                RemoveRedundantImports rem = new RemoveRedundantImports(Collections.singleton(ont));
//                try {
//                    rem.getChanges();
//                } catch (OWLException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        });
        popupMenu.show(controller.getGraphView(), evt.getMouseEvent().getX(), evt.getMouseEvent().getY());
    }

    protected void disposeOWLView() {
        getOWLModelManager().removeOntologyChangeListener(changeListener);
        getOWLModelManager().removeListener(owlModelManagerListener);
    }

}
