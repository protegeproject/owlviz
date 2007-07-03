package uk.ac.man.cs.mig.util.graph.factory;

import uk.ac.man.cs.mig.util.graph.graph.Edge;
import uk.ac.man.cs.mig.util.graph.graph.Node;

/**
 * User: matthewhorridge
 * The Univeristy Of Manchester
 * Medical Informatics Group
 * Date: Dec 27, 2003<br>
 *
 * matthew.horridge@cs.man.ac.uk
 * www.cs.man.ac.uk/~horridgm<br>
 *
 * Defines the interface required to create Edges.
 */
public interface EdgeFactory
{
	/**
	 * Creates an <code>Edge</code>
	 * @param headNode The head node that the edge connects to. Must not be <code>null</code>.
	 * @param tailNode The tail node that the edge connects to. Must not be <code>null</code>.
	 * @param direction The direction of the <code>Edge</code>.  This should be one
	 * of the predefined directions in the <code>Edge</code> interface -
	 * (<code>DIRECTION_NONE</code>, <code>DIRECTION_FORWARD</code>,
	 * <code>DIRECTION_BACK</code> or <code>DIRECTION_BOTH</code>).
	 * @return The newly created <code>Edge</code>
	 */
	public Edge createEdge(Node tailNode, Node headNode, int direction);
}
