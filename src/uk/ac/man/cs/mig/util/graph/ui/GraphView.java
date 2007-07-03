package uk.ac.man.cs.mig.util.graph.ui;

import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.graph.event.*;
import uk.ac.man.cs.mig.util.graph.graph.Edge;
import uk.ac.man.cs.mig.util.graph.graph.Graph;
import uk.ac.man.cs.mig.util.graph.graph.Node;
import uk.ac.man.cs.mig.util.graph.renderer.EdgeRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.NodeRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 12, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 * <p/>
 * The <code>GraphView</code> represents a view
 * that paints the <code>Graph</code> containing the
 * visualised <code>Nodes</code> and <code>Egdes</code>.
 * The default painting relies upon a <code>NodeRenderer</code>
 * and an <code>EdgeRenderer</code>.
 */
public abstract class GraphView extends JPanel implements Zoomable, PropertyChangeListener {

	protected Controller controller;
	protected MouseListener graphViewListener;
	protected GraphSelectionModelListener selectionListener;
	protected GraphGeneratorListener graphGenListener;

	protected ArrayList nodeClickedListeners;

	private Graph graph;

	private Object prevSelObj = null;

	private boolean updateGraph = true;


	public GraphView(Controller controller) {
		this.controller = controller;
		if(controller.getGraphGenerator() == null) {
			throw new NullPointerException("GraphGenerator must not be null");
		}
		if(controller.getGraphLayoutEngine() == null) {
			throw new NullPointerException("GraphLayoutEngine must not be null");
		}
		controller.addPropertyChangeListener(this);
		nodeClickedListeners = new ArrayList();
		setupGraphViewListeners();
	}


	private void setupGraphViewListeners() {
		// Want to select nodes when they are clicked
		addMouseListener(graphViewListener = new MouseAdapter() {
			/**
			 * Invoked when a mouse button has been pressed on a component.
			 */
			public void mousePressed(MouseEvent e) {
				Graph g = controller.getGraphGenerator().getGraph();
				Node[] nodes = g.getNodes();
				int zoomLevel = getZoomLevel();
				int xPos;
				int yPos;
				for(int i = 0; i < nodes.length; i++) {
					xPos = (int) (e.getPoint().x * 100.0 / zoomLevel);
					yPos = (int) (e.getPoint().y * 100.0 / zoomLevel);
					if(nodes[i].getShape().contains(xPos, yPos)) {
						prevSelObj = nodes[i].getUserObject();
						controller.getGraphSelectionModel().setSelectedObject(prevSelObj);
						break;
					}
				}
			}


			/**
			 * Invoked when the mouse has been clicked on a component.
			 */
			public void mouseClicked(MouseEvent e) {
				Graph g = controller.getGraphGenerator().getGraph();
				Node[] nodes = g.getNodes();
				int zoomLevel = getZoomLevel();
				int xPos;
				int yPos;
				for(int i = 0; i < nodes.length; i++) {
					xPos = (int) (e.getPoint().x * 100.0 / zoomLevel);
					yPos = (int) (e.getPoint().y * 100.0 / zoomLevel);
					if(nodes[i].getShape().contains(xPos, yPos)) {
						fireNodeClickedEvent(nodes[i], e);
						break;
					}
				}
			}
		});
		controller.getGraphSelectionModel().addGraphSelectionModelListener(selectionListener = new GraphSelectionModelListener() {
			public void selectionChanged(GraphSelectionModelEvent event) {
				repaint();
				if(controller.getGraphSelectionModel().getSelectedObject() != prevSelObj) {
					prevSelObj = controller.getGraphSelectionModel().getSelectedObject();
					scrollObjectToVisible(prevSelObj);
				}
			}
		});
		controller.getGraphGenerator().addGraphGeneratorListener(graphGenListener = new GraphGeneratorListener() {
			/**
			 * Called when the <code>Graph</code> has been modified.
			 *
			 * @param evt The <code>GraphGeneratorEvent</code> containing the
			 *            event information.
			 */
			public void graphChanged(GraphGeneratorEvent evt) {
				updateGraph = true;

				// Laying out a graph can be time consuming, so only
				// layout the graph if we need to - i.e. if the graph
				// has changed and we are showing the graph.

				if(isShowing() == true && updateGraph == true) {
					revalidateGraph();
				}
			}
		});
		this.addHierarchyListener(new HierarchyListener() {
			/**
			 * Called when the hierarchy has been changed. To discern the actual
			 * type of change, call <code>HierarchyEvent.getChangeFlags()</code>.
			 *
			 * @see java.awt.event.HierarchyEvent#getChangeFlags()
			 */
			public void hierarchyChanged(HierarchyEvent e) {
				// We are now showing the graph.  Check to see if the
				// graph has changed, if so, request the latest layout.
				if(updateGraph == true) {
					//System.out.println("TRACE: GraphView: RequestingLayout");
					revalidateGraph();
				}
			}
		});
	}


	/**
	 * Adds a <code>NodeClickedListener</code> which is informed
	 * when a node is clicked (pressed and released).
	 *
	 * @param lsnr The listener to be added.
	 */
	public void addNodeClickedListener(NodeClickedListener lsnr) {
		nodeClickedListeners.add(lsnr);
	}


	/**
	 * Removes a previously added <code>NodeClickedListener</code>.
	 *
	 * @param lsnr The listener to be removed.
	 */
	public void removeNodeClickedListener(NodeClickedListener lsnr) {
		nodeClickedListeners.remove(lsnr);
	}


	/**
	 * Causes a <code>NodeClickedEvent</code> to be fired.
	 *
	 * @param node The <code>Node</code> associated with the event.
	 * @param evt  The <code>MouseEvent</code> associated with the event.
	 */
	protected void fireNodeClickedEvent(Node node,
	                                    MouseEvent evt) {
		NodeClickedEvent nce = new NodeClickedEvent(node, evt);
		for(int i = 0; i < nodeClickedListeners.size(); i++) {
			((NodeClickedListener) nodeClickedListeners.get(i)).nodeClicked(nce);
		}
	}


	/**
	 * Utility method to cache <code>Nodes</code>
	 * and <code>Edges</code>.
	 */
	protected void setGraph() {
		graph = controller.getGraphGenerator().getGraph();
		repaint();
	}


	/**
	 * Gets the <code>Graph</code> that the view should
	 * currently display.
	 *
	 * @return The current <code>Graph</code>
	 */
	protected Graph getGraph() {
		return graph;
	}


	/**
	 * Gets an <code>Iterator</code> that can be used
	 * to traverse the <code>Node</code>s in the <code>Graph</code>
	 */
	protected Iterator getNodes() {
		return graph.getNodeIterator();
	}


	/**
	 * Gets an <code>Iterator</code> that can be used
	 * to traverse the <code>Edge</code>s in the <code>Graph</code>
	 */
	protected Iterator getEdges() {
		return graph.getEdgeIterator();
	}


	/**
	 * If the <code>Graph</code> contains a <code>Node</code> that
	 * represents the specified object, then the view is scrolled
	 * if necessary to ensure that the <code>Node</code> is visible.
	 *
	 * @param obj
	 */
	public void scrollObjectToVisible(Object obj) {
		Node node = controller.getGraphGenerator().getNodeForObject(obj);
		if(node != null) {
			Rectangle rect = node.getShape().getBounds();

			// Inflate a bit
			rect.grow(20, 20);
			scrollRectToVisible(rect);
		}
	}


	/**
	 * Sets the renderer that is used to paint <code>Nodes</code>
	 * in the <code>GraphView</code>.
	 *
	 * @param renderer The <code>NodeRenderer</code> to use.
	 */
	public abstract void setNodeRenderer(NodeRenderer renderer);


	/**
	 * Sets the renderer that is used to paoint <code>Edges</code>
	 * in the <code>GraphView</code>.
	 *
	 * @param renderer The <code>EdgeRenderer</code> to be used.
	 */
	public abstract void setEdgeRenderer(EdgeRenderer renderer);


	/**
	 * Sets the <code>PopupProvider</code>, which supplies content
	 * to display a toolTip style popup when the mouse hovers over
	 * a <code>Node</code> in the view.
	 *
	 * @param popupProv
	 */
	public abstract void setPopupProvider(PopupProvider popupProv);


	/**
	 * Can be called to draw the GraphView onto the specified Graphics context.
	 *
	 * @param g2             The <code>Graphics2D</code> on to which the <code>Graph</code>
	 *                       should be drawn.
	 * @param scale          If <code>false</code> the scale operation should not be applied to
	 *                       the <code>Graphics2D</code> object.
	 * @param paintSelection If <code>false</code> the selection should not be painted onto
	 *                       the view.
	 * @param antialias      If <code>true</code> the view should be antialiased.
	 */
	public abstract void draw(Graphics2D g2,
	                          boolean scale,
	                          boolean paintSelection,
	                          boolean antialias,
	                          boolean drawDetail);

	/**
	 * Can be called to force the graph held by the view
	 * to update.
	 */
	public void revalidateGraph() {
		setGraph();
		GraphView.this.controller.getGraphLayoutEngine().layoutGraph(graph);
		revalidate();
		updateGraph = false;
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Implementation of PropertyChangeListener
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * This method gets called when a bound property is changed.
	 *
	 * @param evt A PropertyChangeEvent object describing the event source
	 *            and the property that has changed.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(Controller.GRAPH_GENERATOR_PROPERTY)) {
			// TODO: Do something here!!
		}
	}

}
