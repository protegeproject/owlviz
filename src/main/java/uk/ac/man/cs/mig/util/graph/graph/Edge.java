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
 * The interface for <code>Edges</code> contained
 * in a <code>Graph</code>.  An <code>Edge</code>
 * represents a 2D path that connects two <code>Nodes</code>,
 * that has the option to have a label.
 *
 * @see Graph
 * @see Edge
 *
 */
public interface Edge
{
	/**
	 * Returns the node that the <i>head</i> of the head
	 * is connected to.
	 * @return The head Node.
	 */
	public Node getHeadNode();

	/**
	 * Returns the node that the <i>tail</i> of the edge
	 * is connected to.
	 * @return The tail Node.
	 */
	public Node getTailNode();

	/**
	 * Allows an object to be associated with the <code>Edge</code>
	 * @param o The object to be set as the userObject
	 */
	public void setUserObject(Object o);

	/**
	 * Gets the userObject, previously set with <code>setUserObject(Object o)</code>
	 * @return The userObject, or <code>null</code> if no object has been set.
	 */
	public Object getUserObject();

	/**
	 * Causes any information in the path that represents the <code>Egde</code>
	 * to be cleared, such as origin location and any control points.
	 */
	public void resetPath();

	/**
	 * Sets the location of the start of the path that represents
	 * the <code>Edge</code>.
	 * @param x The horizontal location.
	 * @param y The vertical location.
	 */
	public void setPathOrigin(int x, int y);

	/**
	 * Extends the <code>Edge</code> using the following control points to
	 * form a Bezier curve extension to the end point (x3, y3).
	 * @param x1 Horizontal location of control point 1
	 * @param y1 Vertical location of control point 1
	 * @param x2 Horizontal location of control point 2
	 * @param y2 Vertical location of control point 2
	 * @param x3 Horizontal location of the end point.
	 * @param y3 Vertical location of the end point.
	 */
	public void pathTo(int x1, int y1, int x2, int y2, int x3, int y3);

	/**
	 * Extends the <code>Edge</code> to the specified location using
	 * a straight line.
	 * @param x The horizontal location of the end point.
	 * @param y The vertical location of the end point.
	 */
	public void pathTo(int x, int y);

	/**
	 * Sets the <code>Edge's</code> arrowhead.
	 * @param baseX The x location of the centre of the arrowhead baseline.
	 * @param baseY The y location of the centre of the arrowhead tip.
	 * @param tipX The x location of the tip of the arrowhead.
	 * @param tipY The y location of the tip of the arrowhead.
	 */
	public void setArrowHead(int baseX, int baseY, int tipX, int tipY);

	/**
	 * Sets the <code>Edge's</code> arrowtail
	 * @param baseX The x location of the centre of the arrowhead baseline.
	 * @param baseY The y location of the centre of the arrowhead tip.
	 * @param tipX The x location of the tip of the arrowhead.
	 * @param tipY The y location of the tip of the arrowhead.
	 */
	public void setArrowTail(int baseX, int baseY, int tipX, int tipY);

	/**
	 * Sets the <code>Edge</code>'s label position.
	 * @param x The horizontal location (in pixels).
	 * @param y The vertical location (in pixels).
	 */
	public void setLabelPosition(int x, int y);

	/**
	 * Retrieves the position of the <code>Edge's</code> label.
	 * @return A <code>Point</code> containing the position of
	 * the label.
	 */
	public Point getLabelPosition();

	/**
	 * Gets the <code>Shape</code> that represents the <code>Edge</code>.
	 * @return The <code>Shape</code> of the <code>Edge</code>.
	 */
	public Shape getShape();

	/**
	 * Gets the direction of the <code>Edge</code>.  This is one of the
	 * predefined direction from the <code>GraphModel</code> interface.
	 * @return The direction of the <code>Edge</code>.
	 */
	public int getDirection();


}
