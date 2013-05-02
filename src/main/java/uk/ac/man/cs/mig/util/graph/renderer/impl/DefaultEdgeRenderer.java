package uk.ac.man.cs.mig.util.graph.renderer.impl;

import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.graph.graph.Edge;
import uk.ac.man.cs.mig.util.graph.renderer.EdgeRenderer;

import javax.swing.*;
import java.awt.*;

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
public class DefaultEdgeRenderer implements EdgeRenderer
{
	private Controller controller;
	private static Color edgeColor;
	private Color parentEdgeColor;
	private Color childEdgeColor;
	private Stroke selEdgeStroke;
	private Stroke edgeStroke;
	private static double edgeBrightness = 0.4;
	private Font labelFont;

	public DefaultEdgeRenderer(Controller controller)
	{
		this.controller = controller;

		updateEdgeColor();

		parentEdgeColor = new Color(125, 0, 125);

		childEdgeColor = new Color(0, 125, 0);

		selEdgeStroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

		edgeStroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

		JPanel pan = new JPanel();

		Font font = pan.getFont();

		labelFont = font.deriveFont(10.0f);
	}

	/**
	 * Gets the brightness of edges in the graph.
	 * @return The edge brightness.  A brightness of 0.0
	 * is equivalent to black, a brightness of 1.0 is
	 * equivalent to white.
	 */
	public static double getEdgeBrightness()
	{
		return edgeBrightness;
	}

	/**
	 * Sets the brightness (level of gray) of edges in the graph.
	 * @param brightness The brightness.  A brightness of 0.0 is
	 * equivalent to black, a brightness of 1.0 is equivalent to
	 * white.  The brightness is clipped between 0.0 and 1.0
	 */
	public static void setEdgeBrightness(double brightness)
	{
		if(brightness < 0)
		{
			brightness = 0;
		}

		if(brightness > 1)
		{
			brightness = 1;
		}

		edgeBrightness = brightness;

		updateEdgeColor();
	}

	protected static void updateEdgeColor()
	{
		int brightness = (int)(edgeBrightness * 255);

		edgeColor = new Color(brightness, brightness, brightness);
	}

	/**
	 * Called to render an <code>Edge</code>.  Typically, the <code>Shape</code>
	 * will be a <code>GeneralPath</code>.
	 * @param edge The <code>Edge</code> to be rendered.
	 * @param g2 The Graphics2D object on to which the <code>Edge</code> should be rendered.
     * @param forPrinting A flag to indicate if the graphics are being drawn to produce an
     * image for printing, or to draw onto the screen.
	 */
	public void renderEdge(Graphics2D g2, Edge edge, boolean forPrinting, boolean drawDetail)
	{
		Shape sh = edge.getShape();
		// Only render the edge if we are within
		// the clip bounds
		if(sh.intersects(g2.getClipBounds()))
		{
			g2.setColor(getEdgeColor(edge, forPrinting));

			Stroke oldStroke = g2.getStroke();

			g2.setStroke(getEdgeStroke(edge, forPrinting));

			g2.draw(sh);

			g2.setStroke(oldStroke);

			// Render label
			if(drawDetail == true) {
				String label = controller.getEdgeLabelRenderer().getEdgeLabel(edge);

				if(label != null)
				{
					Font oldFont = g2.getFont();

					g2.setFont(labelFont);

					int halfFontWidth = g2.getFontMetrics().stringWidth(label) / 2;

					int fudge = g2.getFontMetrics().getHeight() / 2;

					g2.drawString(label, edge.getLabelPosition().x - halfFontWidth, edge.getLabelPosition().y + fudge);

				 //   Ellipse2D.Double el = new Ellipse2D.Double(edge.getLabelPosition().x, edge.getLabelPosition().y, 3, 3);

				 //   g2.setColor(Color.RED);

				 //   g2.draw(el);

					g2.setFont(oldFont);
				}
			}
		}

	}

	protected Color getEdgeColor(Edge edge, boolean forPrinting)
	{
		Object selObj = controller.getGraphSelectionModel().getSelectedObject();

		Color color = Color.DARK_GRAY;

		if(forPrinting == false && selObj != null)
					{
						if(selObj == edge.getHeadNode().getUserObject()) // Node equals based on IDENTITY of encapsulated
						{                                                // object
							color = childEdgeColor;

						}
						else if(selObj == edge.getTailNode().getUserObject())
						{
							color = parentEdgeColor;

						}
						else
						{
							color = edgeColor;

						}
					}
					else
					{
						color = edgeColor;
					}

		return color;
	}

	protected Stroke getEdgeStroke(Edge edge, boolean forPrinting)
	{
		Stroke stroke = edgeStroke;

		Object selObj = controller.getGraphSelectionModel().getSelectedObject();

					if(forPrinting == false && selObj != null)
					{
						if(selObj == edge.getHeadNode().getUserObject()) // Node equals based on IDENTITY of encapsulated
						{                                                // object
							stroke = selEdgeStroke;
						}
						else if(selObj == edge.getTailNode().getUserObject())
						{
							stroke = selEdgeStroke;
						}
						else
						{
							stroke = edgeStroke;
						}
					}
					else
					{
						stroke = edgeStroke;
					}


		return stroke;
	}
}
