package uk.ac.man.cs.mig.util.graph.factory.impl;

import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.graph.factory.EdgeFactory;
import uk.ac.man.cs.mig.util.graph.graph.Edge;
import uk.ac.man.cs.mig.util.graph.graph.Node;
import uk.ac.man.cs.mig.util.graph.graph.impl.DefaultEdge;

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
public class DefaultEdgeFactory implements EdgeFactory
{
    private Controller controller;
    /**
     * Creates an <code>EdgeFactory</code> that produces
     * <code>Egde</code>s based on the model that may be accessed by the
     * specified controller.
     * @param controller The <code>Controller</code> that contains a
     * reference to the model, which is represented by the <code>Graph</code>.
     */
    public DefaultEdgeFactory(Controller controller)
    {
        this.controller = controller;
    }

	/**
	 * Creates an <code>Edge</code>
	 * @param headNode The head node that the edge connects to. Must not be <code>null</code>.
	 * @param tailNode The tail node that the edge connects to. Must not be <code>null</code>.
	 * @param direction The direction of the <code>Edge</code>.  This should be one
	 * of the predefined directions (<code>DIRECTION_NONE</code>, <code>DIRECTION_FORWARD</code>,
	 * <code>DIRECTION_BACK</code> or <code>DIRECTION_BOTH</code>).
	 * @return The newly created <code>Edge</code>
	 */
	public Edge createEdge(Node tailNode, Node headNode, int direction)
	{
		return new DefaultEdge(tailNode,
		                       headNode,
		                       controller.getGraphModel().getRelationshipType(tailNode.getUserObject(),
		                                                                      headNode.getUserObject()),
		                       direction);
	}
}
