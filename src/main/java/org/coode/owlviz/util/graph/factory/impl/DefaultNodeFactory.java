package org.coode.owlviz.util.graph.factory.impl;

import org.coode.owlviz.util.graph.factory.NodeFactory;
import org.coode.owlviz.util.graph.graph.Node;
import org.coode.owlviz.util.graph.graph.impl.EllipticalNode;

import java.util.Random;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 14, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class DefaultNodeFactory implements NodeFactory {

    private Random rand;

    public DefaultNodeFactory() {
        rand = new Random(System.currentTimeMillis());
    }

    /**
     * Creates a node
     *
     * @param userObject Specifies the user object that the <code>Node</code>
     *                   is associated with.  May be <code>null</code
     * @return The newly created node.
     */
    public Node createNode(Object userObject) {
        return new EllipticalNode(userObject);

        // TEST

/*		double gaus = rand.nextGaussian();

		if(gaus < 0.3)
		{
			Node node = new EllipticalNode(userObject);

			node.setSize(70, 20);

			return node;
		}
		else if (gaus >= 0.3 && gaus < 0.6)
		{
			Node node = new TriangularNode(userObject);

			node.setSize(70, 20);

			return node;
		}
		else
		{
			Node node = new DefaultNode(userObject);

			node.setSize(70, 20);

			return node;
		}
*/

    }
}
