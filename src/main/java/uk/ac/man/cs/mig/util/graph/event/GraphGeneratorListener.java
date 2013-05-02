package uk.ac.man.cs.mig.util.graph.event;



/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 14, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public interface GraphGeneratorListener
{
	/**
	 * Called when the <code>Graph</code> has been modified.
	 * @param evt The <code>GraphGeneratorEvent</code> containing the
	 * event information.
	 */
	public void graphChanged(GraphGeneratorEvent evt);

//	/**
//	 * Called when a <code>Node</code> has been added to the <code>Graph</code>,
//	 * this event also encapsulates the fact that <code>Edges</code> may have also
//	 * been added to the <code>Graph</code> due to the addition of the <code>Node</code>.
//	 * @param evt The <code>GraphGeneratorEvent</code> containing the event information.
//	 */
//	public void objectAdded(GraphGeneratorEvent evt);
//
//	/**
//	 * Called when a <code>Node</code> is removed from the <code>Graph</code>, this
//	 * event also encapsualtes the fact that <code>Edges</code> that were connected
//	 * to the <code>Node</code> may also have been removed.
//	 * @param evt The <code>GraphGeneratorEvent</code> containing the event information.
//	 */
//	public void nodeRemoved(GraphGenerator evt);
}
