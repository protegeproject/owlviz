package org.coode.owlviz.model;

import org.coode.owlviz.util.graph.model.GraphModel;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class OWLVizAxiomGraphModel_TestCase {

    private final OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();

    @Test
    public void shouldDescribeSubClassEdges() {
        OWLClass child = dataFactory.getOWLClass(IRI.create("urn:test#Child"));
        OWLClass parent = dataFactory.getOWLClass(IRI.create("urn:test#Parent"));
        OWLVizAxiomGraphModel model = new OWLVizAxiomGraphModel(Collections.singleton(
                dataFactory.getOWLSubClassOfAxiom(child, parent)));

        assertEquals("is-a", model.getRelationshipType(parent, child));
        assertEquals(GraphModel.DIRECTION_BACK, model.getRelationshipDirection(parent, child));
    }

    @Test
    public void shouldRetainDefaultEdgeDescriptionForUnhandledAxioms() {
        OWLNamedIndividual subject = dataFactory.getOWLNamedIndividual(IRI.create("urn:test#Subject"));
        OWLNamedIndividual object = dataFactory.getOWLNamedIndividual(IRI.create("urn:test#Object"));
        OWLObjectProperty property = dataFactory.getOWLObjectProperty(IRI.create("urn:test#property"));
        OWLVizAxiomGraphModel model = new OWLVizAxiomGraphModel(Collections.singleton(
                dataFactory.getOWLObjectPropertyAssertionAxiom(property, subject, object)));

        assertEquals("<Don't know>", model.getRelationshipType(object, subject));
        assertEquals(GraphModel.DIRECTION_BACK, model.getRelationshipDirection(object, subject));
    }
}
