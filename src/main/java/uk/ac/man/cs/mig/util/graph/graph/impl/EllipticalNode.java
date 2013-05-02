package uk.ac.man.cs.mig.util.graph.graph.impl;

import uk.ac.man.cs.mig.util.graph.graph.Node;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 15, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public class EllipticalNode implements Node
{
	private Object userObject;
	private Ellipse2D.Double ellipse;

	private Point pos;

	/**
	 * Constructs a <code>Node</code> that represents the specified object.
	 * @param userObject The object that the <code>Node</code> represents.
	 */
	public EllipticalNode(Object userObject)
	{
		this.userObject = userObject;

		ellipse = new Ellipse2D.Double();

		pos = new Point();
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
	 * Sets the (x,y) position of the <code>Node</code>.
	 * @param x The horizontal location.
	 * @param y The vertical location.
	 */
	public void setPosition(int x, int y)
	{
		int w = getSize().width;
		int h = getSize().height;

		pos.x = x;

		pos.y = y;

		ellipse.setFrame(x - w / 2, y - h / 2, w, h);
	}

	/**
	 * Gets the position of the <code>Node</code>.
	 * @return A <code>Point</code> containing the x,y position of the <code>Node</code>.
	 */
	public Point getPosition()
	{
		return pos;
	}

	/**
	 * Sets the <code>Node's</code> size.
	 * @param width The width of the <code>Node</code>.
	 * @param height The height of the <code>Node</code>.
	 */
	public void setSize(int width, int height)
	{
		int x = getPosition().x;
		int y = getPosition().y;

		ellipse.setFrame(x - width / 2, y - height / 2, width, height);//setLocation(x - width / 2, y - height / 2);

		//rect.setSize(width, height);
	}

	/**
	 * Gets the size of the <code>Node</code>.
	 * @return A <code>Dimension</code> containing the width and height
	 * of the <code>Node</code>.
	 */
	public Dimension getSize()
	{
		return ellipse.getBounds().getSize();
	}

	/**
	 * Returns the <code>Shape</code> of the <code>Node</code>.
	 * @return The <code>Shape</code> of the <code>Node</code>.
	 */
	public Shape getShape()
	{
		return ellipse;
	}
}
