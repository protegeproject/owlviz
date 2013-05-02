package uk.ac.man.cs.mig.util.graph.ui;

import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.graph.controller.GraphSelectionModel;
import uk.ac.man.cs.mig.util.graph.controller.VisualisedObjectManager;
import uk.ac.man.cs.mig.util.graph.controller.impl.DefaultController;
import uk.ac.man.cs.mig.util.graph.event.*;
import uk.ac.man.cs.mig.util.graph.factory.EdgeFactory;
import uk.ac.man.cs.mig.util.graph.factory.GraphFactory;
import uk.ac.man.cs.mig.util.graph.factory.NodeFactory;
import uk.ac.man.cs.mig.util.graph.model.GraphModel;
import uk.ac.man.cs.mig.util.graph.model.impl.DefaultGraphModel;
import uk.ac.man.cs.mig.util.graph.renderer.EdgeRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.NodeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.NodeRenderer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 29, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class GraphComponent extends JComponent implements ThumbnailViewSource {

	/**
     * 
     */
    private static final long serialVersionUID = 5765860310008732145L;
    private Controller controller;
	private JScrollPane scrollPane;

	private static final int THUMBNAIL_EVENT_BOUNDS_CHANGED = 1;
	private static final int THUMBNAIL_EVENT_VISIBLE_RECT_CHANGED = 2;
	private static final int THUMBNAIL_EVENT_CONTENT_CHANGED = 3;

	private ArrayList<ThumbnailViewSourceListener> thumbnailViewSourceListeners;

    private String name;


    public GraphComponent() {
        this("Unnamed");
    }
    
    public GraphComponent(String name) {
        this.name = name;

        controller = new DefaultController(new DefaultGraphModel());

		this.setLayout(new BorderLayout());

		this.add(scrollPane = new JScrollPane(controller.getGraphView()));

        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);

        setFocusable(true);

		thumbnailViewSourceListeners = new ArrayList<ThumbnailViewSourceListener>();

		setupThumbnailListeners();
	}


	public Dimension getPreferredSize() {
		return controller.getGraphView().getPreferredSize();
	}


	public Dimension getMaximumSize() {
		return controller.getGraphView().getMaximumSize();
	}


	public Dimension getMinimumSize() {
		return controller.getGraphView().getMinimumSize();
	}


	public GraphView getGraphView() {
		return controller.getGraphView();
	}


    public String getName() {
        return name;
    }

//	public Object[] getObjects() {
//		return controller.getVisualisedObjectManager().getVisualisedObjects();
//	}
//
//
//	public void showObject(Object obj) {
//		controller.getVisualisedObjectManager().showObject(obj);
//	}
//
//
//	public void hideObject(Object obj) {
//		controller.getVisualisedObjectManager().hideObject(obj);
//	}
//
//
//	public void showChildren(Object obj) {
//		controller.getVisualisedObjectManager().showChildren(obj);
//	}
//
//
//	public void showObjects(Object[] objs) {
//		controller.getVisualisedObjectManager().showObjects(objs);
//	}
//
//
//	public void hideObjects(Object[] objs) {
//		controller.getVisualisedObjectManager().hideObjects(objs);
//	}
//
//
//	public void showChildren(Object obj,
//	                         int levels) {
//		controller.getVisualisedObjectManager().showChildren(obj, levels);
//	}
//
//
//	public void hideChildren(Object obj) {
//		controller.getVisualisedObjectManager().hideChildren(obj);
//	}
//
//
//	public void showParents(Object obj) {
//		controller.getVisualisedObjectManager().showParents(obj);
//	}
//
//
//	public void hideParents(Object obj) {
//		controller.getVisualisedObjectManager().hideParents(obj);
//	}


	public Object getSelectedObject() {
		return controller.getGraphSelectionModel().getSelectedObject();
	}


	public Object[] getSelectedObjects() {
		return controller.getGraphSelectionModel().getSelectedObjects();
	}


	public void selectObject(Object obj) {
		controller.getGraphSelectionModel().setSelectedObject(obj);
	}


	public void addGraphSelectionModelListener(GraphSelectionModelListener lsnr) {
		controller.getGraphSelectionModel().addGraphSelectionModelListener(lsnr);
	}


	public void removeGraphSelectionModelListener(GraphSelectionModelListener lsnr) {
		controller.getGraphSelectionModel().removeGraphSelectionModelListener(lsnr);
	}


	public GraphSelectionModel getGraphSelectionModel() {
		return controller.getGraphSelectionModel();
	}


	public void setGraphSelectionModel(GraphSelectionModel model) {
		controller.setGraphSelectionModel(model);
	}


	public void setNodeRenderer(NodeRenderer renderer) {
		controller.setNodeRenderer(renderer);
	}


	public void setNodeFactory(NodeFactory nodeFactory) {
		controller.setNodeFactory(nodeFactory);
	}


	public NodeFactory getNodeFactory(NodeFactory nodeFactory) {
		return controller.getNodeFactory();
	}


	public void setEdgeFactory(EdgeFactory edgeFactory) {
		controller.setEdgeFactory(edgeFactory);
	}


	public EdgeFactory getEdgeFactory(EdgeFactory edgeFactory) {
		return controller.getEdgeFactory();
	}


	public void setGraphFactory(GraphFactory graphFactory) {
		controller.setGraphFactory(graphFactory);
	}


	public GraphFactory getGraphFactory() {
		return controller.getGraphFactory();
	}


	public void setEdgeRenderer(EdgeRenderer renderer) {
		controller.setEdgeRenderer(renderer);
	}


	public void setNodeLabelRenderer(NodeLabelRenderer renderer) {
		controller.setNodeLabelRenderer(renderer);
	}


	public void setGraphModel(GraphModel model) {
		controller.setGraphModel(model);
	}


	public GraphModel getGraphModel() {
		return controller.getGraphModel();
	}


	public VisualisedObjectManager getVisualisedObjectManager() {
		return controller.getVisualisedObjectManager();
	}


	public Controller getController() {
		return controller;
	}


	public void addNodeClickedListener(NodeClickedListener nodeClickedListener) {
		controller.getGraphView().addNodeClickedListener(nodeClickedListener);
	}


	public void removeNodeClickedListener(NodeClickedListener nodeClickedListener) {
		controller.getGraphView().removeNodeClickedListener(nodeClickedListener);
	}

	public void setPopupProvider(PopupProvider popupProvider) {
		controller.getGraphView().setPopupProvider(popupProvider);
	}


	/**
	 * Scrolls the <code>GraphView</code> so that the <code>Node</code>
	 * representing the specified object becomes visible.
	 *
	 * @param obj The object to be made visible.
	 */
	public void scrollObjectToVisible(Object obj) {
		controller.getGraphView().scrollObjectToVisible(obj);
	}



	///////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Implementation Of ThumbnailViewSource
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * The main view should draw itself on to the
	 * graphics context provided, to produce a representation
	 * of the main view in the thumbnail view. Note that
	 * no scaling should be applied to the graphics context.
	 *
	 * @param g2
	 */
	public void drawThumbnail(Graphics2D g2) {
		controller.getGraphView().draw(g2, false, false, false, false);
	}


	/**
	 * Should return the bounds of the main view.
	 *
	 * @return A <code>Dimension</code> that specified
	 *         the bounds of the main view that the thumbnail
	 *         represents.
	 */
	public Dimension getViewBounds() {
		Dimension viewBounds = controller.getGraphView().getBounds().getSize();

		return controller.getGraphView().dimensionFromZoomedDimension(viewBounds);
	}


	/**
	 * Should return a <code>Rectangle</code> that represents the
	 * portion of the main view that is visible.
	 *
	 * @return The visible <code>Rectangle</code>.
	 */
	public Rectangle getViewVisibleRect() {
		Rectangle visibleRect = controller.getGraphView().getVisibleRect();

		return controller.getGraphView().rectangleFromZoomedRectangle(visibleRect);
	}


	/**
	 * Allows the thumbnail view to request that the
	 * main view be scrolled by a given amount.
	 *
	 * @param deltaX The horizontal amount.
	 * @param deltaY The vertical amount.
	 */
	public void scrollView(int deltaX,
	                       int deltaY) {
		int zoomLevel = controller.getGraphView().getZoomLevel();

		deltaX = (int) (deltaX * zoomLevel / 100.0);

		deltaY = (int) (deltaY * zoomLevel / 100.0);

		Rectangle rect = controller.getGraphView().getVisibleRect();

		rect.translate(deltaX, deltaY);

		controller.getGraphView().scrollRectToVisible(rect);
	}


	/**
	 * Adds a <code>ThumbnailViewSourceListener</code> to the <code>ThumbnailViewSource</code>
	 * so that the <code>ThumbnailView</code> is informed of events
	 * that it might be interested in.
	 *
	 * @param lsnr The listener to be added.
	 */
	public void addThumbnailViewSourceListener(ThumbnailViewSourceListener lsnr) {
		thumbnailViewSourceListeners.add(lsnr);
	}


	/**
	 * Removeds a previously added listener.
	 *
	 * @param lsnr The listener to removed.
	 */
	public void removeThumbnailViewSourceListener(ThumbnailViewSourceListener lsnr) {
		thumbnailViewSourceListeners.remove(lsnr);
	}


	protected void setupThumbnailListeners() {
		// Listen to the graph view resizing events, so the
		// thumbnail can be updated appropriately
		controller.getGraphView().addComponentListener(new ComponentAdapter() {
			/**
			 * Invoked when the component's size changes.
			 */
			public void componentResized(ComponentEvent e) {
				fireThumbnailViewSourceEvent(new ThumbnailViewSourceEvent(GraphComponent.this), THUMBNAIL_EVENT_BOUNDS_CHANGED);
			}
		});

		// Listen to the viewport state change events
		scrollPane.getViewport().addChangeListener(new ChangeListener() {
			/**
			 * Invoked when the target of the listener has changed its state.
			 *
			 * @param e a ChangeEvent object
			 */
			public void stateChanged(ChangeEvent e) {
				fireThumbnailViewSourceEvent(new ThumbnailViewSourceEvent(GraphComponent.this),
				                             THUMBNAIL_EVENT_VISIBLE_RECT_CHANGED);
			}
		});

		// Listener to changes in the graph
		controller.getGraphGenerator().addGraphGeneratorListener(new GraphGeneratorListener() {
			/**
			 * Called when the <code>Graph</code> has been modified.
			 *
			 * @param evt The <code>GraphGeneratorEvent</code> containing the
			 *            event information.
			 */
			public void graphChanged(GraphGeneratorEvent evt) {
				fireThumbnailViewSourceEvent(new ThumbnailViewSourceEvent(GraphComponent.this), THUMBNAIL_EVENT_CONTENT_CHANGED);
			}
		});

	}


	protected void fireThumbnailViewSourceEvent(ThumbnailViewSourceEvent evt,
	                                            int type) {
		Iterator<ThumbnailViewSourceListener> it = thumbnailViewSourceListeners.iterator();

		while(it.hasNext()) {
			ThumbnailViewSourceListener obj = it.next();

			if(type == THUMBNAIL_EVENT_BOUNDS_CHANGED) {
				 obj.sourceViewBoundsChanged(evt);
			}
			else if(type == THUMBNAIL_EVENT_CONTENT_CHANGED) {
				 obj.sourceViewContentsChanged(evt);
			}
			else if(type == THUMBNAIL_EVENT_VISIBLE_RECT_CHANGED) {
				obj.sourceViewVisibleRectChanged(evt);
			}
		}
	}

}
