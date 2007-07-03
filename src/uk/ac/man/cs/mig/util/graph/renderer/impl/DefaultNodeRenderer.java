package uk.ac.man.cs.mig.util.graph.renderer.impl;

import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.graph.controller.VisualisedObjectManager;
import uk.ac.man.cs.mig.util.graph.graph.Node;
import uk.ac.man.cs.mig.util.graph.layout.GraphLayoutEngine;
import uk.ac.man.cs.mig.util.graph.renderer.NodeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.NodeRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 14, 2004<br><br>
 * 
 * matthew.horridge@cs.visualisedObjectManager.ac.uk<br>
 * www.cs.visualisedObjectManager.ac.uk/~horridgm<br><br>
 *
 */
public class DefaultNodeRenderer implements NodeRenderer
{
//	private NodeLabelRenderer labelRenderer;

	private static Color fillColor;
	private static Color lineColor;
	private VisualisedObjectManager visualisedObjectManager;
	private Polygon leftArrow = new Polygon();
	private Polygon rightArrow = new Polygon();
	private FontMetrics fontMetrics;
	private static final int ARROW_SIZE = 5;
	private static final int HORIZONTAL_PADDING = ARROW_SIZE * 2 + 10;
	private static final int VERTICAL_PADDING = 15;
	private Font labelFont;
	private Controller controller;
	private int layoutDirection = GraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT;
    private static Stroke lineStroke = new BasicStroke(2.0f);

	public DefaultNodeRenderer(Controller controller)
	{
		this.controller = controller;

		if(controller.getVisualisedObjectManager() == null)
		{
			throw new NullPointerException("VisualisedObjectManager (in controller) must not be null");
		}

		visualisedObjectManager = controller.getVisualisedObjectManager();

		JPanel pan = new JPanel();

		Font font = pan.getFont();

		labelFont = font.deriveFont(10.0f);


		if(labelFont == null)
		{
			System.out.println("Font is NULL!");
		}

		fontMetrics = pan.getFontMetrics(labelFont);

		if(fontMetrics == null)
		{
			System.out.println("Font metrics is NULL!");
		}

		if(visualisedObjectManager == null)
		{
			throw new NullPointerException("DefaultNode renderer constructed before" +
			                               "VisualisedObjectManager");
		}

//		this.labelRenderer = labelRenderer;

		fillColor = Color.YELLOW;

		lineColor = Color.BLACK;

		setupArrows();
	}

    protected Color getFillColor(Node node) {
        return fillColor;
    }

    protected Color getLineColor(Node node) {
        return lineColor;
    }

    protected Stroke getLineStroke() {
        return lineStroke;
    }

    /**
	 * Generic shape renderer.  Typically, the shape of the <code>Node</code>
	 * will be a <code>Rectangle</code> or an <code>Ellipse</code>.
	 * @param node The <code>Node</code> being rendered.
	 * @param g2 The Graphics2D object on to which the <code>Node</code> should be rendered.
     * @param forPrinting A flag to indicate if the graphics are being drawn to produce an
     * image for printing, or to draw onto the screen.
	 */
	public void renderNode(Graphics2D g2, Node node, boolean forPrinting, boolean drawDetail)
	{
		Shape sh = node.getShape();

		// Only render if we are within the clip bounds
		if(sh.intersects(g2.getClipBounds()))
		{
			// Fill the node


			g2.setColor(getFillColor(node));

			g2.fill(sh);

			g2.setColor(getLineColor(node));

            g2.setStroke(getLineStroke());
            g2.draw(sh);




            Object obj = node.getUserObject();

			String label;

			Point pos = node.getPosition();

			if(drawDetail == true) {
				label = controller.getNodeLabelRenderer().getLabel(node);



				// Draw expansion arrows

				drawArrows(g2, sh, node.getUserObject());

				// Draw text

				Font f = g2.getFont();

				g2.setFont(labelFont);

				Rectangle2D labelBounds2D = g2.getFontMetrics().getStringBounds(label, g2);

				Rectangle labelBounds = labelBounds2D.getBounds();

				g2.drawString(label, pos.x - labelBounds.width / 2, pos.y + labelBounds.height / 3);

				g2.setFont(f);
			}
		}
	}

	public Dimension getPreferredSize(Node node, Dimension size)
	{
		String label = controller.getNodeLabelRenderer().getLabel(node);



		int width = SwingUtilities.computeStringWidth(fontMetrics, label);

		int height = fontMetrics.getHeight();

		if(size != null)
		{
			size.width = width + HORIZONTAL_PADDING;

			size.height = height + VERTICAL_PADDING;

			return size;
		}
		else
		{
			return new Dimension(width, height);
		}
	}

	protected void drawArrows(Graphics2D g2, Shape nodeShape, Object userObject)
    {
        if(controller.getGraphLayoutEngine().getLayoutDirection() != layoutDirection)
        {
            layoutDirection = controller.getGraphLayoutEngine().getLayoutDirection();

            setupArrows();
        }

        if(layoutDirection == GraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT)
        {

            if(visualisedObjectManager.getChildrenHiddenCount(userObject) > 0)
			{
				Rectangle rect = nodeShape.getBounds();

				g2.translate(rect.x + rect.width, rect.y + rect.height / 2);

				g2.fill(rightArrow);

				g2.translate(-rect.x - rect.width, -rect.y - rect.height / 2);
			}

			if(visualisedObjectManager.getParentsHiddenCount(userObject) > 0)
			{
				Rectangle rect = nodeShape.getBounds();

				g2.translate(rect.x, rect.y + rect.height / 2);

				g2.fill(leftArrow);

				g2.translate(-rect.x, -rect.y - rect.height / 2);
			}
        }
        else
        {
            if(visualisedObjectManager.getChildrenHiddenCount(userObject) > 0)
			{
				Rectangle rect = nodeShape.getBounds();

				g2.translate(rect.x + rect.width / 2, rect.y + rect.height);

				g2.fill(rightArrow);

				g2.translate(-rect.x  - rect.width / 2, -rect.y - rect.height);
			}

			if(visualisedObjectManager.getParentsHiddenCount(userObject) > 0)
			{
				Rectangle rect = nodeShape.getBounds();

				g2.translate(rect.x + rect.width / 2, rect.y);

				g2.fill(leftArrow);

				g2.translate(-rect.x - rect.width / 2, -rect.y);
			}
        }

    }

	protected void setupArrows()
    {
        if(controller.getGraphLayoutEngine().getLayoutDirection() == GraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT)
        {
            leftArrow.reset();

            leftArrow.addPoint(ARROW_SIZE, -ARROW_SIZE);
            leftArrow.addPoint(0, 0);
            leftArrow.addPoint(ARROW_SIZE, ARROW_SIZE);

            rightArrow.reset();

            rightArrow.addPoint(-ARROW_SIZE, -ARROW_SIZE);
            rightArrow.addPoint(0, 0);
            rightArrow.addPoint(-ARROW_SIZE, ARROW_SIZE);
        }
        else
        {
            leftArrow.reset();

            // Up Arrow
            leftArrow.addPoint(-ARROW_SIZE, ARROW_SIZE);
            leftArrow.addPoint(0, 0);
            leftArrow.addPoint(ARROW_SIZE, ARROW_SIZE);

            rightArrow.reset();
            // Down arrow
            rightArrow.addPoint(-ARROW_SIZE, -ARROW_SIZE);
            rightArrow.addPoint(0, 0);
            rightArrow.addPoint(ARROW_SIZE, -ARROW_SIZE);
        }
    }
}
