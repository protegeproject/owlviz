package uk.ac.man.cs.mig.util.graph.graph.impl;

import uk.ac.man.cs.mig.util.graph.graph.Edge;
import uk.ac.man.cs.mig.util.graph.graph.Graph;
import uk.ac.man.cs.mig.util.graph.graph.Node;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
public class DefaultGraph implements Graph
{
	private Map nodes;
	private Map edges;

	private Shape shape;

	public DefaultGraph()
	{
		nodes = new HashMap();

		edges = new HashMap();

		shape = new Rectangle();
	}

	/**
	 * Adds a <code>Node</code> to the graph.  If the <code>Graph</code>
	 * already contains the <code>Node</code> then the <code>Node</code>
	 * will not be added.
	 * @param node  The node to be added.
	 */
	public void add(Node node)
	{
		if(nodes.containsKey(node) == false)
		{
			nodes.put(node, node);
		}


	}

	/**
	 * Removes the specified <code>Node</code> from the <code>Graph</code>.
	 * Note that any edges that have this node as their head node, or tail node
	 * will also be removed.
	 * @param node  The <code>Node</code> to be removed.
	 */
	public void remove(Node node)
	{

		if(nodes.containsKey(node))
		{
			//System.out.println("TRACE(DefaultGraph: remove) Node removed");
			// Remove the node
			nodes.remove(node);

			Iterator it = getEdgeIterator();

			Edge edge;

			// Remove any edges that are connected to the node.
			while(it.hasNext())
			{
				edge = (Edge)it.next();

			//	System.out.println("TRACE(DefaultGraph: remove) Egde: " + edge);

			//	System.out.println("\t\tTRACE(DefaultGraph: remove) TailNode: " + edge.getTailNode());

			//	System.out.println("\t\tTRACE(DefaultGraph: remove) HeadNode: " + edge.getHeadNode());

			//	System.out.println("\t\tTRACE(DefaultGraph: remove) Node: " + node);


				if(node.equals(edge.getTailNode()))
				{
					it.remove();
				}
				else if(node.equals(edge.getHeadNode()))
				{
					it.remove();
				}
			}
		}
	}

	/**
	 * Adds an <code>Edge</code> to the graph.  Note that the Edge will
	 * only be added if the nodes that the edge connects are contained
	 * in the graph.
	 * @param edge The <code>Edge</code> to be added.
	 */
	public void add(Edge edge)
	{
		if(nodes.containsKey(edge.getTailNode()) && nodes.containsKey(edge.getHeadNode()))
		{
			EdgeKey key = new EdgeKey(edge);

			edges.put(key, edge);
		}
	}

	/**
	 * Removes an <code>Edge</code> from the <code>Graph</code>.
	 * @param edge The <code>Edge</code> to be removed.
	 */
	public void remove(Edge edge)
	{
		edges.remove(new EdgeKey(edge));
	}

	/**
	 * Removes the <code>Edge</code> that connects the specified
	 * <code>Nodes</code> (if the <code>Nodes</code> are contained
	 * in the <code>Graph</code>.
	 * @param tailNode The <code>Egde</code>'s tail <code>Node</code>.
	 * @param headNode The <code>Edge</code>'s head <code>Node</code>.
	 */
	public void remove(Node tailNode, Node headNode)
	{
		edges.remove(new EdgeKey(tailNode, headNode));
	}

	/**
	 * Removes all the edges and nodes from the graph.
	 */
	public void removeAll()
	{
		edges = new HashMap();

		nodes = new HashMap();
	}

	/**
	 * Returns the number of <code>Nodes</code> that the <code>Graph</code>
	 * contains.
	 * @return The number of <code>Nodes</code>.
	 */
	public int getNodeCount()
	{
		return nodes.size();
	}

	/**
	 * Returns the number of <code>Edges</code> that the <code>Graph</code>
	 * contains.
	 * @return The number of <code>Edges</code>.
	 */
	public int getEdgeCount()
	{
		return edges.size();
	}

	/**
	 * Retrieves an <code>Iterator</code> that can be
	 * used to traverse the <code>Nodes</code> in the <code>Graph</code>.
	 * @return The <code>Iterator</code>.
	 */
	public Iterator getNodeIterator()
	{
		return nodes.keySet().iterator();
	}

	/**
	 * Retrieves an <code>Iterator</code> that can be
	 * used to traverse the <code>Edges</code> in the <code>Graph</code>.
	 * @return The <code>Iterator</code>.
	 */
	public Iterator getEdgeIterator()
	{
		return edges.values().iterator();
	}

	/**
	 * Gets the shape that isShown all of the <code>Nodes</code> and
	 * <code>Edges</code> belonging to the <code>Graph</code>.
	 * @return A shape that describes the <code>Graphs</code> bounds.
	 */
	public Shape getShape()
	{
		return shape;
	}

	/**
	 * Sets the shape that describes the <code>Graph</code> bounds.
	 * @param shape The <code>Shape</code> bounding the graph.
	 */
	public void setShape(Shape shape)
	{
		this.shape = shape;
	}

	/**
	 * Determines if the <code>Graph</code> isShown the specified <code>Node</code>.
	 * @param node The <code>Node</code>
	 * @return <code>true</code> if the <code>Graph</code> isShown the <code>Node</code>
	 * or <code>false</code> if the <code>Graph</code> does not contain the <code>Node</code>.
	 */
	public boolean contains(Node node)
	{
		return nodes.containsKey(node);
	}

	/**
	 * Determines if the <code>Graph</code> isShown the specified <code>Edge</code>.
	 * @param edge The <code>Edge</code>.
	 * @return <code>true</code> if the <code>Graph</code> isShown the <code>Edge</code>
	 * or <code>false</code> if the <code>Graph</code> does not contain the <code>Edge</code>.
	 */
	public boolean contains(Edge edge)
	{
		return edges.containsKey(new EdgeKey(edge));
	}

	/**
	 * Gets the <code>Nodes</code> contained in the <code>Graph</code>
	 * as an array.
	 * @return An array containing the <code>Nodes</code> in the <code>Graph</code>.
	 */
	public Node[] getNodes()
	{
		Object [] array = nodes.keySet().toArray();

		Node [] nodeArray = new Node [array.length];

		System.arraycopy(array, 0, nodeArray, 0, array.length);

		return nodeArray;
	}

	/**
	 * Gets the <code>Edges</code> contained in the <code>Graph</code> as an array.
	 * @return An array containing the <code>Edges</code> in the <code>Graph</code>.
	 */
	public Edge[] getEdges()
	{
		Object [] array = edges.values().toArray();

		Edge [] edgeArray = new Edge [array.length];

		System.arraycopy(array, 0, edgeArray, 0, array.length);

		return edgeArray;
	}

	public class EdgeKey
	{
		private Node tailNode;
		private Node headNode;

		public EdgeKey(Edge edge)
		{
			this.tailNode = edge.getTailNode();

			this.headNode = edge.getHeadNode();
		}

		public EdgeKey(Node tailNode, Node headNode)
		{
			this.tailNode = tailNode;

			this.headNode = headNode;
		}

		public int hashCode()
		{
			return tailNode.hashCode() * 13 + headNode.hashCode() * 37;
		}

		public boolean equals(Object obj)
		{
			if(obj.getClass().equals(this.getClass()) == false)
			{
				return false;
			}

			EdgeKey key = (EdgeKey)obj;

			if(key.tailNode.equals(tailNode) && key.headNode.equals(headNode))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
}
