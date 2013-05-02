package uk.ac.man.cs.mig.util.graph.graph.impl;

import uk.ac.man.cs.mig.util.graph.graph.Edge;
import uk.ac.man.cs.mig.util.graph.graph.Node;
import uk.ac.man.cs.mig.util.graph.model.GraphModel;

import java.awt.*;
import java.awt.geom.GeneralPath;

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
public class DefaultEdge implements Edge
{
	private Object userObject;
	private Node tailNode;
	private Node headNode;
	private Point labelPos;

	private GeneralPath path;
	private static final int ARROWHEAD_HALF_BASE_WIDTH = 4;

	private int direction;


	public DefaultEdge(Node tailNode, Node headNode, Object userObject, int direction)
	{
		this.tailNode = tailNode;

		this.headNode = headNode;

		this.userObject = userObject;

		labelPos = new Point();

		path = new GeneralPath();

		if(direction == GraphModel.DIRECTION_NONE ||
		   direction == GraphModel.DIRECTION_FORWARD ||
		   direction == GraphModel.DIRECTION_BACK ||
		   direction == GraphModel.DIRECTION_BOTH)
		{
			this.direction = direction;
		}
	}

	/**
	 * Returns the node that the <i>head</i> of the head
	 * is connected to.
	 * @return The head Node.
	 */
	public Node getHeadNode()
	{
		return headNode;
	}

	/**
	 * Returns the node that the <i>tail</i> of the edge
	 * is connected to.
	 * @return The tail Node.
	 */
	public Node getTailNode()
	{
		return tailNode;
	}

	/**
	 * Allows an object to be associated with the <code>Edge</code>
	 * @param o The object to be set as the userObject
	 */
	public void setUserObject(Object o)
	{
		userObject = o;
	}

	/**
	 * Gets the userObject, previously set with <code>setUserObject(Object o)</code>
	 * @return The userObject, or <code>null</code> if no object has been set.
	 */
	public Object getUserObject()
	{
		return userObject;
	}

	/**
	 * Causes any information in the path that represents the <code>Egde</code>
	 * to be cleared, such as origin location and any control points.
	 */
	public void resetPath()
	{
		path.reset();
	}

	/**
	 * Sets the location of the start of the path that represents
	 * the <code>Edge</code>.
	 * @param x The horizontal location.
	 * @param y The vertical location.
	 */
	public void setPathOrigin(int x, int y)
	{
		path.reset();

		path.moveTo(x, y);

	}

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
	public void pathTo(int x1, int y1, int x2, int y2, int x3, int y3)
	{
		// If the path is a horizontal straighline, then add it as
		// such - there appears to be a 'feature' where
		// the curve will not be drawn if all y control points
		// are equal.
		if(y1 == y2 && y2 == y3)
		{
			path.lineTo(x3, y3);
		}
		else
		{
			path.curveTo(x1, y1, x2, y2, x3, y3);
		}
	}

	/**
	 * Extends the <code>Edge</code> to the specified location using
	 * a straight line.
	 * @param x The horizontal location of the end point.
	 * @param y The vertical location of the end point.
	 */
	public void pathTo(int x, int y)
	{
		path.lineTo(x, y);
	}

	/**
	 * Retrieves the position of the <code>Edge's</code> label.
	 * @return A <code>Point</code> containing the position of
	 * the label.
	 */
	public Point getLabelPosition()
	{
		return labelPos;
	}

	/**
	 * Sets the <code>Edge</code>'s label position.
	 * @param x The horizontal location (in pixels).
	 * @param y The vertical location (in pixels).
	 */
	public void setLabelPosition(int x, int y)
	{
		labelPos.x = x;

		labelPos.y = y;
	}

	public void setArrowHead(int baseX, int baseY, int tipX, int tipY)
	{
		int deltaX = tipX - baseX;
		int deltaY = tipY - baseY;

		double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

		double vecX = (deltaX / length) * ARROWHEAD_HALF_BASE_WIDTH;

		double vecY = (deltaY / length) * ARROWHEAD_HALF_BASE_WIDTH;



		int x1;
		int y1;
		int x2;
		int y2;

		x1 = (int)(baseX - vecY);
		y1 = (int)(baseY + vecX);
		x2 = (int)(baseX + vecY);
		y2 = (int)(baseY - vecX);

		path.moveTo(tipX, tipY);

		path.lineTo(x1, y1);

		path.lineTo(x2, y2);

		path.lineTo(tipX, tipY);
	}


	/**
	 * Sets the <code>Edge's</code> arrowtail
	 *
	 * @param baseX The x location of the centre of the arrowhead baseline.
	 * @param baseY The y location of the centre of the arrowhead tip.
	 * @param tipX  The x location of the tip of the arrowhead.
	 * @param tipY  The y location of the tip of the arrowhead.
	 */
	public void setArrowTail(int baseX, int baseY, int tipX, int tipY)
	{
		int deltaX = tipX - baseX;
		int deltaY = tipY - baseY;

		double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

		double vecX = (deltaX / length) * ARROWHEAD_HALF_BASE_WIDTH;

		double vecY = (deltaY / length) * ARROWHEAD_HALF_BASE_WIDTH;



		int x1;
		int y1;
		int x2;
		int y2;

		x1 = (int)(baseX - vecY);
		y1 = (int)(baseY + vecX);
		x2 = (int)(baseX + vecY);
		y2 = (int)(baseY - vecX);

		path.moveTo(tipX, tipY);

		path.lineTo(x1, y1);

		path.lineTo(x2, y2);

		path.lineTo(tipX, tipY);
	}


	/**
	 * Gets the <code>Shape</code> that represents the <code>Edge</code>.
	 * @return The <code>Shape</code> of the <code>Edge</code>.
	 */
	public Shape getShape()
	{
		return path;
	}


	/**
	 * Gets the direction of the <code>Edge</code>.  This is one of the
	 * predefined direction from the <code>Edge</code> interface.
	 *
	 * @return The direction of the <code>Edge</code>.
	 */
	public int getDirection()
	{
		return direction;
	}


	public int hashCode()
	{
		return tailNode.hashCode() * 13 + headNode.hashCode() * 37;
	}

	public boolean equals(Object obj)
	{
		if(obj instanceof Edge)
		{
			if(obj == this)
			{
				return true;
			}
			else
			{
				Edge edge = (Edge)obj;

				if(edge.getTailNode().equals(tailNode) && edge.getHeadNode().equals(headNode))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}

		return false;
	}
}
