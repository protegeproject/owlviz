package uk.ac.man.cs.mig.coode.owlviz.ui;

import java.util.Collection;

import uk.ac.man.cs.mig.util.graph.ui.GraphComponent;

public interface OWLVizViewI {

	public OWLVizSelectionModel getSelectionModel();
	
	public Collection<GraphComponent> getGraphComponents();
	
	public Collection<GraphComponent> getAllGraphComponents();
}