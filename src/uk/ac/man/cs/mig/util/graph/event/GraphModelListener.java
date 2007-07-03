package uk.ac.man.cs.mig.util.graph.event;

/**
 * User: matthewhorridge
 * The Univeristy Of Manchester
 * Medical Informatics Group
 * Date: Dec 27, 2003
 *
 * matthew.horridge@cs.man.ac.uk
 * www.cs.man.ac.uk/~horridgm
 *
 * An interface for GraphModelListeners.
 */
public interface GraphModelListener
{
	/**
	 * Called when a node is added to the graph.
	 * @param evt The event, containing info about the
	 * objectAdded action.
	 */
	public void objectAdded(GraphModelEvent evt);

	/**
	 * Called when a node is removed from the graph.
	 * @param evt The event, containing info about the
	 * nodeRemoved action.
	 */
	public void objectRemoved(GraphModelEvent evt);

	/**
	 * Called when a node is changed.
	 * @param evt The event, containing info about the
	 * nodeChanged action.
	 */
	public void objectChanged(GraphModelEvent evt);

	public void parentAdded(GraphModelEvent evt);

	public void parentRemoved(GraphModelEvent evt);

	public void childAdded(GraphModelEvent evt);

	public void childRemoved(GraphModelEvent evt);

    /**
     * Fired when the model has changed drastically.
     * @param evt The event object associated with this
     * event.
     */
    public void modelChanged(GraphModelEvent evt);
}
