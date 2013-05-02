package uk.ac.man.cs.mig.util.graph.event;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jun 1, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public interface NodeClickedListener
{
	/**
	 * Invoked when a <code>Node</code>
	 * has been clicked by the mouse
	 * in the <code>GraphView</code>
	 * @param evt The event associated with this action.
	 */
	public void nodeClicked(NodeClickedEvent evt);
}
