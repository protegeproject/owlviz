package uk.ac.man.cs.mig.coode.owlviz.ui;

import uk.ac.man.cs.mig.coode.owlviz.ui.options.OWLVizViewOptions;
import uk.ac.man.cs.mig.util.graph.ui.GraphComponent;

import java.util.Collection;

public interface OWLVizViewI {

	public OWLVizSelectionModel getSelectionModel();
	
	public Collection<GraphComponent> getGraphComponents();
	
	public Collection<GraphComponent> getAllGraphComponents();

    public OWLVizViewOptions getOptions();
}