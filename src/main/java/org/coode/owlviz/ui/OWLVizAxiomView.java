package org.coode.owlviz.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JScrollPane;

import org.protege.editor.owl.ui.view.cls.AbstractOWLClassViewComponent;

import org.coode.owlviz.model.OWLVizAxiomGraphModel;
import org.coode.owlviz.util.graph.controller.Controller;
import org.coode.owlviz.util.graph.controller.impl.DefaultController;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.parameters.Imports;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-May-2007<br><br>
 */
public class OWLVizAxiomView extends AbstractOWLClassViewComponent {

    /**
     * 
     */
    private static final long serialVersionUID = -2736965181564975528L;

    private Controller controller;

    private OWLVizAxiomGraphModel graphModel;

    private CardLayout cardLayout;

    public void disposeView() {
    }


    public void initialiseClassView() throws Exception {
        createGraphModel(null);
        controller = new DefaultController(graphModel);
        setLayout(new BorderLayout());
        add(new JScrollPane(controller.getGraphView()));
    }

    private void createGraphModel(OWLClass cls) {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        if(cls != null) {
            for(OWLOntology ont : getOWLModelManager().getActiveOntologies()) {
                axioms.addAll(ont.getAxioms(cls, Imports.EXCLUDED));
            }
        }
        graphModel = new OWLVizAxiomGraphModel(axioms);

    }

    protected OWLClass updateView(OWLClass selectedClass) {
        createGraphModel(selectedClass);
        controller.setGraphModel(graphModel);
        controller.getVisualisedObjectManager().showObjects(graphModel.getAllNodes().toArray());
        return selectedClass;
    }
}
