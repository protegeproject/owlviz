package uk.ac.man.cs.mig.coode.owlviz.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JScrollPane;

import org.protege.editor.owl.ui.view.AbstractOWLClassViewComponent;
import org.semanticweb.owl.model.OWLAxiom;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLOntology;

import uk.ac.man.cs.mig.coode.owlviz.model.OWLVizAxiomGraphModel;
import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.graph.controller.impl.DefaultController;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-May-2007<br><br>
 */
public class OWLVizAxiomView extends AbstractOWLClassViewComponent {

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
                axioms.addAll(ont.getAxioms(cls));
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
