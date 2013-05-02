/**
 * Contains the interfaces and classes used to represent
 * a visualised graph.
 */
package uk.ac.man.cs.mig.util.graph.graph;

import java.awt.*;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Dec 27, 2003<br><br>
 *
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * The interface for <code>Nodes</code> contained in a
 * <code>Graph</code>.  A <code>Node</code> represents an object and manifests
 * itself as a two dimensional shape, that can be renerered in a <code>Graph</code>.
 * All basic <code>Nodes</code> have a position, a width and a height.
 *
 * @see Graph
 * @see Edge
 *
 */
public interface Node
{

	/**
	 * Gets the userObject, previously set with <code>setUserObject(Object o)</code>
	 * @return The userObject, or <code>null</code> if no object has been set.
	 */
	public Object getUserObject();

	/**
	 * Sets the (x,y) position of the <code>Node</code>.
	 * @param x The horizontal location.
	 * @param y The vertical location.
	 */
	public void setPosition(int x, int y);

	/**
	 * Gets the position of the <code>Node</code>.
	 * @return A <code>Point</code> containing the x,y position of the <code>Node</code>.
	 */
	public Point getPosition();

	/**
	 * Sets the <code>Node's</code> size.
	 * @param width The width of the <code>Node</code>.
	 * @param height The height of the <code>Node</code>.
	 */
	public void setSize(int width, int height);

	/**
	 * Gets the size of the <code>Node</code>.
	 * @return A <code>Dimension</code> containing the width and height
	 * of the <code>Node</code>.
	 */
	public Dimension getSize();

	/**
	 * Returns the <code>Shape</code> of the <code>Node</code>.
	 * @return The <code>Shape</code> of the <code>Node</code>.
	 */
	public Shape getShape();


}
