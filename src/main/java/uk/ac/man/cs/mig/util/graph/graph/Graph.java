package uk.ac.man.cs.mig.util.graph.graph;

import java.awt.*;
import java.util.Iterator;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Dec 27, 2003<br><br>
 *
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * The interface for a <code>Graph</code>.  The interface
 * defines methods for adding and removing <code>Nodes</code>
 * and <code>Edges</code>.
 *
 * @see Node
 * @see Edge
 */
public interface Graph
{
	/**
	 * Adds a <code>Node</code> to the graph.  If the graph
	 * already isShown the specified node, the node is replaced with
	 * the specified node.
	 * @param node  The node to be added.
	 */
	public void add(Node node);

	/**
	 * Removes the specified <code>Node</code> from the <code>Graph</code>.
	 * Note that any edges that have this node as their head node, or tail node
	 * will also be removed.
	 * @param node  The <code>Node</code> to be removed.
	 */
	public void remove(Node node);

	/**
	 * Adds an <code>Edge</code> to the graph.  Note that the Edge will
	 * only be added if the nodes that the edge connects are contained
	 * in the graph.
	 * @param edge The <code>Edge</code> to be added.
	 */
	public void add(Edge edge);

	/**
	 * Removes an <code>Edge</code> from the <code>Graph</code>.
	 * @param edge The <code>Edge</code> to be removed.
	 */
	public void remove(Edge edge);

	/**
	 * Removes the <code>Edge</code> that connects the specified
	 * <code>Nodes</code> (if the <code>Nodes</code> are contained
	 * in the <code>Graph</code>.
	 * @param tailNode The <code>Egde</code>'s tail <code>Node</code>.
	 * @param headNode The <code>Edge</code>'s head <code>Node</code>.
	 */
	public void remove(Node tailNode, Node headNode);

	/**
	 * Removes all the edges and nodes from the graph.
	 */
	public void removeAll();

	/**
	 * Returns the number of <code>Nodes</code> that the <code>Graph</code>
	 * contains.
	 * @return The number of <code>Nodes</code>.
	 */
	public int getNodeCount();

	/**
	 * Returns the number of <code>Edges</code> that the <code>Graph</code>
	 * contains.
	 * @return The number of <code>Edges</code>.
	 */
	public int getEdgeCount();

	/**
	 * Gets the <code>Nodes</code> contained in the <code>Graph</code>
	 * as an array.
	 * @return An array containing the <code>Nodes</code> in the <code>Graph</code>.
	 */
	public Node [] getNodes();

	/**
	 * Gets the <code>Edges</code> contained in the <code>Graph</code> as an array.
	 * @return An array containing the <code>Edges</code> in the <code>Graph</code>.
	 */
	public Edge [] getEdges();

	/**
	 * Retrieves an <code>Iterator</code> that can be
	 * used to traverse the <code>Nodes</code> in the <code>Graph</code>.
	 * @return The <code>Iterator</code>.
	 */
	public Iterator getNodeIterator();

	/**
	 * Retrieves an <code>Iterator</code> that can be
	 * used to traverse the <code>Edges</code> in the <code>Graph</code>.
	 * @return The <code>Iterator</code>.
	 */
	public Iterator getEdgeIterator();

	/**
	 * Gets the shape that isShown all of the <code>Nodes</code> and
	 * <code>Edges</code> belonging to the <code>Graph</code>.
	 * @return A shape that describes the <code>Graphs</code> bounds.
	 */
	public Shape getShape();

	/**
	 * Sets the shape that describes the <code>Graph</code> bounds.
	 * @param shape The <code>Shape</code> bounding the graph.
	 */
	public void setShape(Shape shape);

	/**
	 * Determines if the <code>Graph</code> isShown the specified <code>Node</code>.
	 * @param node The <code>Node</code>
	 * @return <code>true</code> if the <code>Graph</code> isShown the <code>Node</code>
	 * or <code>false</code> if the <code>Graph</code> does not contain the <code>Node</code>.
	 */
	public boolean contains(Node node);

	/**
	 * Determines if the <code>Graph</code> isShown the specified <code>Edge</code>.
	 * @param edge The <code>Edge</code>.
	 * @return <code>true</code> if the <code>Graph</code> isShown the <code>Edge</code>
	 * or <code>false</code> if the <code>Graph</code> does not contain the <code>Edge</code>.
	 */
	public boolean contains(Edge edge);

	

}
