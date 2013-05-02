package uk.ac.man.cs.mig.util.graph.ui.impl;

import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.graph.graph.Edge;
import uk.ac.man.cs.mig.util.graph.graph.Node;
import uk.ac.man.cs.mig.util.graph.renderer.EdgeRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.NodeRenderer;
import uk.ac.man.cs.mig.util.graph.ui.GraphView;
import uk.ac.man.cs.mig.util.graph.ui.PopupProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Iterator;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 14, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class DefaultGraphView extends GraphView {

//	private NodeRenderer nodeRen;
//	private EdgeRenderer edgeRen;

	/**
     * 
     */
    private static final long serialVersionUID = 5353379644823080882L;

    private Stroke selStroke;

	private int zoomLevel = 100; // Zoom level (in percent)
	private Color selectionColor = new Color(140, 140, 255);
	private Color backgroundColor = Color.WHITE;

	private PopupProvider popuProv;
	private Object toolTipObject = null;

	public static final int MAXIMUM_ZOOM = 500;
	public static final int MINIMUM_ZOOM = 10;

	public static final int TOOL_TIP_DISMISS_DELAY = 10000;




	public DefaultGraphView(Controller controller,
	                        NodeRenderer nodeRenderer,
	                        EdgeRenderer edgeRenderer) {

		super(controller);

//		nodeRen = nodeRenderer;
//
//		edgeRen = edgeRenderer;

		if(nodeRenderer == null) {
			throw new NullPointerException("NodeRenderer must not be null");
		}

		if(edgeRenderer == null) {
			throw new NullPointerException("EdgeRenderer must not be null");
		}

		selStroke = new BasicStroke(3);

		this.addMouseListener(new MouseAdapter() {
			/**
			 * Invoked when a mouse button has been released on a component.
			 */
			public void mouseReleased(MouseEvent e) {
				if(e.getClickCount() == 2) {
					displayPopup(e.getPoint());

				}
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				// We might have to show a tooltip
				// if the mouse is over a node.
				updateTooltipState(e.getPoint());
			}
		});

	}


	public void draw(Graphics2D g2,
	                 boolean scale,
	                 boolean paintSelection,
	                 boolean antialias,
	                 boolean drawDetail) {
		Shape clip = g2.getClip();

		g2.setColor(backgroundColor);

		g2.fill(clip);

		if(getGraph() != null) {
			if(scale == true && zoomLevel != 100) {
				g2.scale(zoomLevel / 100.0, zoomLevel / 100.0);
			}

			if(antialias == true) {
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			}

			// Paint the Nodes
			Iterator nodeIt = getNodes();
			while(nodeIt.hasNext()) {
				controller.getNodeRenderer().renderNode(g2, (Node) nodeIt.next(), !paintSelection, drawDetail);
			}

			Iterator edgeIt = getEdges();
			while(edgeIt.hasNext()) {
				controller.getEdgeRenderer().renderEdge(g2, (Edge) edgeIt.next(), !paintSelection, drawDetail);
			}

			if(paintSelection) {
				// Paint the selection
				controller.getGraphGenerator();

				Object[] selObjects = controller.getGraphSelectionModel().getSelectedObjects();

				Node selNode;

				for(int i = 0; i < selObjects.length; i++) {
					selNode = controller.getGraphGenerator().getNodeForObject(selObjects[i]);

					if(selNode != null) {
						g2.setColor(selectionColor);

						g2.setStroke(selStroke);

						Rectangle rect = selNode.getShape().getBounds();

						rect.grow(5, 5);

						g2.draw(rect);
					}
				}
			}
		}

	}


	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		draw(g2, true, true, true, true); // was g
	}


	public void setNodeRenderer(NodeRenderer renderer) {
		//nodeRen = renderer;
		repaint();
	}


	public void setEdgeRenderer(EdgeRenderer renderer) {
		//edgeRen = renderer;
		repaint();
	}


	///////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Implementation Of Zoomable
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setZoomLevel(int percentage) {
		if(percentage >= MAXIMUM_ZOOM) {
			percentage = MAXIMUM_ZOOM;
		}

		if(percentage <= MINIMUM_ZOOM) {
			percentage = MINIMUM_ZOOM;
		}

		zoomLevel = percentage;
		revalidate();
		repaint();
	}


	/**
	 * Gets the zoom level as a percentage.
	 *
	 */
	public int getZoomLevel() {
		return zoomLevel;
	}


	/**
	 * Gets the maximum allowed zoom level.
	 *
	 * @return The maximum allowed zoom level as a percentage.
	 */
	public int getMaximumZoomLevel() {
		return MAXIMUM_ZOOM;
	}


	/**
	 * Gets the minimum allowed zoom level.
	 *
	 * @return The minimum allowed zoom level as a percentage.
	 */
	public int getMinimumZoomLevel() {
		return MINIMUM_ZOOM;
	}


	/**
	 * Converts a <code>Point</code> to a 'zoomed' <code>Point</code>.
	 * e.g. (100, 200) zoomed 200 percent would be converted to
	 * (200, 400).
	 *
	 * @param pt The <code>Point</code>  to be converted
	 * @return The converted <code>Point</code>
	 */
	public Point pointToZoomedPoint(Point pt) {
		Point convertedPt = new Point(pt);
		convertedPt.x = (int) (convertedPt.x * (zoomLevel / 100.0));
		convertedPt.y = (int) (convertedPt.y * (zoomLevel / 100.0));
		return convertedPt;
	}


	/**
	 * Converts a <code>Point</code> from a 'zoomed' <code>Point</code>.
	 * e.g. (100, 200) zoomed 200 percent would be converted to
	 * (50, 100).
	 *
	 * @param pt The <code>Point</code>  to be converted
	 * @return The converted <code>Point</code>
	 */
	public Point pointFromZoomedPoint(Point pt) {
		Point convertedPt = new Point(pt);
		convertedPt.x = (int) (convertedPt.x * (100.0 / zoomLevel));
		convertedPt.y = (int) (convertedPt.y * (100.0 / zoomLevel));
		return convertedPt;
	}


	/**
	 * Converts a <code>Dimension</code> to a 'zoomed' <code>Dimension</code>.
	 * e.g. (100, 200) zoomed 200 percent would be converted to
	 * (200, 400).
	 *
	 * @param dim The <code>Dimension</code>  to be converted
	 * @return The converted <code>Dimension</code>
	 */
	public Dimension dimensionToZoomedDimension(Dimension dim) {
		Dimension convertedDim = new Dimension(dim);
		convertedDim.width = (int) (convertedDim.width * (zoomLevel / 100.0));
		convertedDim.height = (int) (convertedDim.height * (zoomLevel / 100.0));
		return convertedDim;
	}


	/**
	 * Converts a <code>Dimension</code> from a 'zoomed' <code>Dimension</code>.
	 * e.g. (100, 200) zoomed 200 percent would be converted to
	 * (50, 100).
	 *
	 * @param dim The <code>Dimension</code>  to be converted
	 * @return The converted <code>Dimension</code>
	 */
	public Dimension dimensionFromZoomedDimension(Dimension dim) {
		Dimension convertedDim = new Dimension(dim);
		convertedDim.width = (int) (convertedDim.width * (100.0 / zoomLevel));
		convertedDim.height = (int) (convertedDim.height * (100.0 / zoomLevel));
		return convertedDim;
	}


	/**
	 * Converts a <code>Rectangle</code> to a 'zoomed' <code>Rectangle</code>.
	 * e.g. (100, 200) zoomed 200 percent would be converted to
	 * (200, 400).
	 *
	 * @param rect The <code>Rectangle</code>  to be converted
	 * @return The converted <code>Rectangle</code>
	 */
	public Rectangle rectangleToZoomedRectangle(Rectangle rect) {
		Rectangle convertedRect = new Rectangle(rect);
		convertedRect.x = (int) (convertedRect.x * (zoomLevel / 100.0));
		convertedRect.y = (int) (convertedRect.y * (zoomLevel / 100.0));
		convertedRect.width = (int) (convertedRect.width * (zoomLevel / 100.0));
		convertedRect.height = (int) (convertedRect.height * (zoomLevel / 100.0));
		return convertedRect;
	}


	/**
	 * Converts a <code>Rectangle</code> from a 'zoomed' <code>Rectangle</code>.
	 * e.g. (100, 200) zoomed 200 percent would be converted to
	 * (50, 100).
	 *
	 * @param rect The <code>Rectangle</code>  to be converted
	 * @return The converted <code>Rectangle</code>
	 */
	public Rectangle rectangleFromZoomedRectangle(Rectangle rect) {
		Rectangle convertedRect = new Rectangle(rect);
		convertedRect.x = (int) (convertedRect.x * (100.0 / zoomLevel));
		convertedRect.y = (int) (convertedRect.y * (100.0 / zoomLevel));
		convertedRect.width = (int) (convertedRect.width * (100.0 / zoomLevel));
		convertedRect.height = (int) (convertedRect.height * (100.0 / zoomLevel));
		return convertedRect;
	}


	/**
	 * Sets the provider responsible for providing a popup
	 *
	 * @param popupProv The <code>PopupProvided</code>, or <code>null</code>
	 *                  to clear any previously set popup provider.
	 */
	public void setPopupProvider(PopupProvider popupProv) {
		this.popuProv = popupProv;
	}


	/**
	 * Returns the instance of <code>JToolTip</code> that should be used
	 * to display the tooltip.
	 * This method delegates to the popup provider.
	 *
	 * @return the <code>JToolTip</code> used to display this toolTip
	 */
	public JToolTip createToolTip() {
		// Check to see if we actually have a popup provider,
		// if not, then we don't want to display a tooltip
		if(popuProv != null) {
			JComponent content = new JPanel(new BorderLayout());
			content.add(popuProv.getPopup(getToolTipObject()), BorderLayout.NORTH);
			return prepareTooltip(content);
		}
		else {
			return prepareEmptyTooltip();
		}
	}

	private JToolTip prepareTooltip(JComponent content) {
		JToolTip toolTip = new JToolTip();
		toolTip.setLayout(new BorderLayout());
		toolTip.add(content);
		toolTip.setPreferredSize(content.getPreferredSize());
		toolTip.setFocusable(false);
		toolTip.setComponent(this);
		ToolTipManager.sharedInstance().setDismissDelay(TOOL_TIP_DISMISS_DELAY);
		return toolTip;
	}

	/**
	 * Creates an empty tool tip.
	 */
	private JToolTip prepareEmptyTooltip() {
		JToolTip tip = new JToolTip();
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(0, 0));
		tip.removeAll();
		tip.add(panel);
		return tip;
	}

	/**
	 * Determines the graph object that lies at the
	 * specified point, and then creates a tooltip for
	 * the graph object if appropriate.  This method
	 * is usually called from the mouse move method.
	 * @param pt The point where the tooltip should
	 * be displayed.
	 */
	private void updateTooltipState(Point pt) {
		// Convert the point into a point on the graph
		Point graphPt = pointFromZoomedPoint(pt);
		// Iterate through the nodes, and find out if
		// a node is located at the specified point
		Iterator nodeIt = controller.getGraphGenerator().getGraph().getNodeIterator();
		while(nodeIt.hasNext()) {
			final Node node = (Node) nodeIt.next();
			if(node.getShape().contains(graphPt)) {
				showToolTip(node.getUserObject());
				return;
			}
		}
		// Not on a node, so hide the tooltip
		hideToolTip();
	}

	private void hideToolTip() {
		// Setting the tooltip text to be null
		// will cause the tooltip to be hidden.
		setToolTipText(null);
	}

	private void showToolTip(Object toolTipObject) {
		this.toolTipObject = toolTipObject;
		// Set the tooltip text to be an empty string
		// as this will cause the tooltip to be displayed.
		setToolTipText("");
	}

	private Object getToolTipObject() {
		return toolTipObject;
	}
	/**
	 * Causes the popup to be displayed at the specified point if it
	 * lies within a <code>Node</code> boundary.
	 *
	 * @param pt The point on the view where the popup should be displayed.
	 */
	protected void displayPopup(Point pt) {
	}


	/**
	 * If the <code>preferredSize</code> has been set to a
	 * non-<code>null</code> value just returns it.
	 * If the UI delegate's <code>getPreferredSize</code>
	 * method returns a non <code>null</code> value then return that;
	 * otherwise defer to the component's layout manager.
	 *
	 * @return the value of the <code>preferredSize</code> property
	 * @see #setPreferredSize
	 * @see javax.swing.plaf.ComponentUI
	 */
	public Dimension getPreferredSize() {
		if(getGraph() == null) {
			return new Dimension(10, 10);
		}
		else {
			Dimension prefSize = new Dimension(getGraph().getShape().getBounds().getSize());

			prefSize.width = (int) (prefSize.width * (zoomLevel / 100.0));

			prefSize.height = (int) (prefSize.height * (zoomLevel / 100.0));

			return prefSize;
		}
	}


	/**
	 * If the maximum size has been set to a non-<code>null</code> value
	 * just returns it.  If the UI delegate's <code>getMaximumSize</code>
	 * method returns a non-<code>null</code> value then return that;
	 * otherwise defer to the component's layout manager.
	 *
	 * @return the value of the <code>maximumSize</code> property
	 * @see #setMaximumSize
	 * @see javax.swing.plaf.ComponentUI
	 */
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}


	/**
	 * If the minimum size has been set to a non-<code>null</code> value
	 * just returns it.  If the UI delegate's <code>getMinimumSize</code>
	 * method returns a non-<code>null</code> value then return that; otherwise
	 * defer to the component's layout manager.
	 *
	 * @return the value of the <code>minimumSize</code> property
	 * @see #setMinimumSize
	 * @see javax.swing.plaf.ComponentUI
	 */
	public Dimension getMinimumSize() {
		return new Dimension(10, 10); //getPreferredSize();
	}

}
