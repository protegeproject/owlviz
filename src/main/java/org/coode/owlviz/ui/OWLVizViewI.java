package org.coode.owlviz.ui;

import org.coode.owlviz.util.graph.ui.GraphComponent;

import java.util.Collection;

public interface OWLVizViewI {

    public OWLVizSelectionModel getSelectionModel();

    public Collection<GraphComponent> getGraphComponents();

    public Collection<GraphComponent> getAllGraphComponents();
}