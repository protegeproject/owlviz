package uk.ac.man.cs.mig.util.graph.event;

import uk.ac.man.cs.mig.util.graph.controller.GraphSelectionModel;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 26, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public class GraphSelectionModelEvent
{
	private GraphSelectionModel source;

	/**
	 * Constructs a selection model event, using
	 * the specified <code>GraphSelectionModel</code> as
	 * the source for the event.
	 * @param source The <code>GraphSelctionModel</code> that has generated the event.
	 */
	public GraphSelectionModelEvent(GraphSelectionModel source)
	{
		this.source = source;
	}

	/**
	 * Gets the source of the event.
	 * @return The <code>GraphSelectionModel</code> that generated the event.
	 */
	public GraphSelectionModel getSource()
	{
		return source;
	}
}
