package uk.ac.man.cs.mig.util.graph.event;

import uk.ac.man.cs.mig.util.graph.controller.GraphGenerator;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 14, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * 
 */
public class GraphGeneratorEvent
{
	private GraphGenerator source;

	/**
	 * Constructs a <code>GraphGeneratorEvent</code> with the specified
	 * event source.
	 * @param source The source of the event i.e. a <code>GraphGenerator</code>.
	 */
	public GraphGeneratorEvent(GraphGenerator source)
	{
		this.source = source;
	}

	/**
	 * Retrieves the source of the event.
	 * @return The <code>GraphGenerator</code> that cause the event.
	 */
	public GraphGenerator getSource()
	{
		return source;
	}
}
