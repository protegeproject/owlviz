package uk.ac.man.cs.mig.util.graph.controller.impl;

import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.graph.controller.GraphGenerator;
import uk.ac.man.cs.mig.util.graph.controller.GraphSelectionModel;
import uk.ac.man.cs.mig.util.graph.controller.VisualisedObjectManager;
import uk.ac.man.cs.mig.util.graph.event.GraphGeneratorListener;
import uk.ac.man.cs.mig.util.graph.event.GraphModelListener;
import uk.ac.man.cs.mig.util.graph.event.GraphSelectionModelListener;
import uk.ac.man.cs.mig.util.graph.event.VisualisedObjectManagerListener;
import uk.ac.man.cs.mig.util.graph.factory.EdgeFactory;
import uk.ac.man.cs.mig.util.graph.factory.GraphFactory;
import uk.ac.man.cs.mig.util.graph.factory.NodeFactory;
import uk.ac.man.cs.mig.util.graph.factory.impl.DefaultEdgeFactory;
import uk.ac.man.cs.mig.util.graph.factory.impl.DefaultGraphFactory;
import uk.ac.man.cs.mig.util.graph.factory.impl.DefaultNodeFactory;
import uk.ac.man.cs.mig.util.graph.layout.GraphLayoutEngine;
import uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine.DotGraphLayoutEngine;
import uk.ac.man.cs.mig.util.graph.model.GraphModel;
import uk.ac.man.cs.mig.util.graph.renderer.EdgeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.EdgeRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.NodeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.NodeRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.impl.DefaultEdgeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.impl.DefaultEdgeRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.impl.DefaultNodeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.impl.DefaultNodeRenderer;
import uk.ac.man.cs.mig.util.graph.ui.GraphView;
import uk.ac.man.cs.mig.util.graph.ui.impl.DefaultGraphView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 15, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.visualisedObjectManager.ac.uk<br>
 * www.cs.visualisedObjectManager.ac.uk/~horridgm<br><br>
 */
public class DefaultController implements Controller {

	private GraphModel graphModel;
	private VisualisedObjectManager visualisedObjectManager;
	private GraphSelectionModel graphSelectionModel;
	private GraphGenerator graphGenerator;
	private GraphLayoutEngine graphLayoutEngine;
	private GraphView graphView;

	private EdgeFactory edgeFactory;
	private NodeFactory nodeFactory;
	private GraphFactory graphFactory;

	private NodeRenderer nodeRenderer;
	private NodeLabelRenderer nodeLabelRenderer;
	private EdgeRenderer edgeRenderer;
	private EdgeLabelRenderer edgeLabelRenderer;

	// Property Change listeners and Property names
	private ArrayList<PropertyChangeListener> listeners;
	//private MouseAdapter graphViewListener;
	//private GraphSelectionModelListener selectionListener;


	public DefaultController(GraphModel model) {
		listeners = new ArrayList<PropertyChangeListener>();

		this.graphModel = model;

		// Set up defaults

		visualisedObjectManager = new DefaultVisualisedObjectManager(model);

		graphFactory = new DefaultGraphFactory();

		nodeFactory = new DefaultNodeFactory();

		edgeFactory = new DefaultEdgeFactory(this);

		this.graphLayoutEngine = new DotGraphLayoutEngine();

		nodeLabelRenderer = new DefaultNodeLabelRenderer();

		nodeRenderer = new DefaultNodeRenderer(this);

		edgeRenderer = new DefaultEdgeRenderer(this);

		edgeLabelRenderer = new DefaultEdgeLabelRenderer();

		this.graphGenerator = new DefaultGraphGenerator(this, visualisedObjectManager, graphFactory, nodeFactory,
		                                                edgeFactory, nodeRenderer);

		this.graphSelectionModel = new DefaultGraphSelectionModel();

		this.graphView = new DefaultGraphView(this, nodeRenderer, edgeRenderer);
	}


	/**
	 * Returns the <code>GraphModel</code>, which is an interface
	 * to the underlying data that is represented by the <code>Graph</code>.
	 *
	 * @return The <code>GraphModel</code>.
	 */
	public GraphModel getGraphModel() {
		return graphModel;
	}


	/**
	 * Sets the <code>GraphModel</code>, which is an interface
	 * to the underlying data that is represented by the <code>Graph</code>
	 *
	 * @param model The <code>GraphModel</code>
	 */
	public void setGraphModel(GraphModel model) {
		if(model == null) {
			throw new NullPointerException("GraphModel must not be null");
		}

		Object oldValue = graphModel;
		Iterator it = graphModel.getListeners();
		//Transfer the listeners.
		while(it.hasNext()) {
			GraphModelListener lsnr = (GraphModelListener) it.next();
			model.addGraphModelListener(lsnr);
			it.remove();
		}

		graphModel = model;
		visualisedObjectManager.setGraphModel(graphModel);
		firePropertyChangeEvent(Controller.GRAPH_MODEL_PROPERTY, oldValue, graphModel);
	}


	/**
	 * Returns the <code>GraphLayoutEngine</code>, which is
	 * capable of sizing and positioning <code>Nodes</code>
	 * and <code>Edges</code>.
	 *
	 * @return The GraphLayoutEngine
	 */
	public GraphLayoutEngine getGraphLayoutEngine() {
		return graphLayoutEngine;
	}


	/**
	 * Sets the <code>GraphLayoutEngine</code>, which is used
	 * to size and position <code>Nodes</code> and <code>Edges</code>
	 *
	 * @param layoutEngine The new <code>GraphLayoutEngine</code>
	 */
	public void setGraphLayoutEngine(GraphLayoutEngine layoutEngine) {
		if(layoutEngine == null) {
			throw new NullPointerException("GraphLayoutEngine must not be null");
		}

		Object oldValue = this.graphLayoutEngine;

		this.graphLayoutEngine = layoutEngine;

		// TODO: Layout graph

		firePropertyChangeEvent(Controller.GRAPH_LAYOUT_ENGINE_PROPERTY, oldValue, this.graphLayoutEngine);
	}


	/**
	 * Returns the selection model that is responsible for managing user
	 * interaction in the form of <code>Node</code> and <code>Edge</code>
	 * selections.
	 *
	 * @return The <code>DefaultGraphSelectionModel</code>.
	 */
	public GraphSelectionModel getGraphSelectionModel() {
		return graphSelectionModel;
	}


	/**
	 * Sets the selection model that is responsible for managing user
	 * interaction in the form of <code>Node</code> and <code>Edge</code>
	 * selections.
	 *
	 * @param model The <code>GraphSelectionModel</code> that should be used.
	 */
	public void setGraphSelectionModel(GraphSelectionModel model) {
		if(model == null) {
			throw new NullPointerException("GraphSelectionModel must not be null");
		}

		GraphSelectionModel oldGraphSelectionModel = graphSelectionModel;
		graphSelectionModel = model;

		// Replace the current selection model, transfer the
		// listeners.
		Object[] selObjs = oldGraphSelectionModel.getSelectedObjects();

		Iterator it = oldGraphSelectionModel.getListeners();

		while(it.hasNext()) {
			GraphSelectionModelListener lsnr = (GraphSelectionModelListener) it.next();
			graphSelectionModel.addGraphSelectionModelListener(lsnr);
			// Remove the listener from the old selection model
			it.remove();
		}

		for(int i = 0; i < selObjs.length; i++) {
			graphSelectionModel.setSelectedObject(selObjs[i]);
		}

		firePropertyChangeEvent(Controller.GRAPH_SELECTION_MODEL_PROPERTY, oldGraphSelectionModel, graphSelectionModel);
	}


	/**
	 * Returns the <code>VisualisedObjectManager</code> which is responsible
	 * for keeping track of the objects being visualised (and hence contained
	 * in the <code>Graph</code>).
	 *
	 * @return The <code>VisualisedObjectManager</code> that isShown details of the
	 *         objects from the <code>GraphModel</code> that are being visualised.
	 */
	public VisualisedObjectManager getVisualisedObjectManager() {
		return visualisedObjectManager;
	}


	/**
	 * Sets the <code>VisualisedObjectManager</code> that is responsible
	 * for keeping track of objects contained in the <code>Graph</code>.
	 * Note that any listeners that were on the old <code>VisualisedObjectManager</code>
	 * are swapped over to the new <code>VisualisedObjectManager</code>.
	 *
	 * @param manager The new <code>VisualisedObjectManager</code>.
	 */
	public void setVisualisedObjectManager(VisualisedObjectManager manager) {
		if(manager == null) {
			throw new NullPointerException("VisualisedObjectManager must not be null");
		}

		VisualisedObjectManager oldVisualisedObjectManager = this.visualisedObjectManager;

		// Swap over the listeners
		Iterator it = oldVisualisedObjectManager.getVisualisedObjectManagerListeners();

		this.visualisedObjectManager = manager;

		while(it.hasNext()) {
			VisualisedObjectManagerListener lsnr = (VisualisedObjectManagerListener) it.next();
			visualisedObjectManager.addVisualisedObjectManagerListener(lsnr);
			it.remove();
		}

		visualisedObjectManager.setGraphModel(graphModel);

		firePropertyChangeEvent(Controller.VISUALISED_OBJECT_MANAGER_PROPERTY, oldVisualisedObjectManager,
		                        visualisedObjectManager);
	}


	/**
	 * Returns the <code>GraphGenerator</code> which is responsible for
	 * creating and maintaining the <code>Graph</code> that represents
	 * the list of objects held by the <code>VisualisedObject</code> manager.
	 *
	 * @return The <code>GraphGenerator</code>.
	 */
	public GraphGenerator getGraphGenerator() {
		return graphGenerator;
	}


	/**
	 * Sets the <code>GraphGenerator</code>, which is used to create
	 * a <code>Graph</code> that is based on the contents held by
	 * the <code>VisualisedObjectManager</code>.
	 *
	 * @param generator The new <code>GraphGenerator</code>.  Note that any
	 *                  listeners that were attached to the old <code>GraphGenerator</code> will
	 *                  be removed and registered with the new <code>GraphGenerator</code>.
	 */
	public void setGraphGenerator(GraphGenerator generator) {
		if(generator == null) {
			throw new NullPointerException("GraphGenerator must not be null");
		}

		GraphGenerator oldGraphGenerator = this.graphGenerator;
		this.graphGenerator = generator;
		Iterator it = oldGraphGenerator.getGraphGeneratorListeners();
		while(it.hasNext()) {
			GraphGeneratorListener lsnr = (GraphGeneratorListener) it.next();
			graphGenerator.addGraphGeneratorListener(lsnr);
			it.remove();
		}

		firePropertyChangeEvent(Controller.GRAPH_GENERATOR_PROPERTY, oldGraphGenerator, graphGenerator);
	}


	/**
	 * Retrieves the <code>GraphView</code> currently attached to the <code>Controller</code>
	 */
	public GraphView getGraphView() {
		return graphView;
	}


	/**
	 * Sets the view that is used paint the <code>Graph</code> on.
	 *
	 * @param view The new <code>GraphView</code>.  Note that any listeners
	 *             that were attached to the old <code>GraphView</code> will be removed
	 *             and added to the new <code>GraphView</code>.
	 */
	public void setGraphView(GraphView view) {
		if(view == null) {
			throw new NullPointerException("GraphView must not be null");
		}

		// TODO: Swap listeners
		GraphView oldGraphView = this.graphView;

		this.graphView = view;

		firePropertyChangeEvent(Controller.GRAPH_VIEW_PROPERTY, oldGraphView, graphView);
	}


	/**
	 * Sets the factory used to produce <code>Graphs</code>
	 *
	 * @param factory The new <code>GraphFactory</code>.
	 */
	public void setGraphFactory(GraphFactory factory) {
		if(factory == null) {
			throw new NullPointerException("GraphFactory must not be null");
		}
		GraphFactory oldGraphFactory = this.graphFactory;
		this.graphFactory = factory;
		firePropertyChangeEvent(Controller.GRAPH_FACTORY_PROPERTY, oldGraphFactory, graphFactory);
	}


	/**
	 * Gets the factory used to produce <code>Graphs</code>
	 */
	public GraphFactory getGraphFactory() {
		return graphFactory;
	}


	/**
	 * Sets the factory used to produce <code>Edges</code>
	 *
	 * @param factory The <code>GraphFactory</code>
	 */
	public void setEdgeFactory(EdgeFactory factory) {
		if(factory == null) {
			throw new NullPointerException("EdgeFactory must not be null");
		}

		Object oldValue = this.edgeFactory;
		this.edgeFactory = factory;
		firePropertyChangeEvent(Controller.EDGE_FACTORY_PROPERTY, oldValue, edgeFactory);
	}


	/**
	 * Gets the factory that produces <code>Edges</code>
	 */
	public EdgeFactory getEdgeFactory() {
		return edgeFactory;
	}


	/**
	 * Sets the factory used to produce <code>Nodes</code>.
	 *
	 * @param factory The <code>NodeFactory</code>
	 */
	public void setNodeFactory(NodeFactory factory) {
		if(factory == null) {
			throw new NullPointerException("NodeFactory must not be null");
		}

		Object oldValue = this.nodeFactory;
		this.nodeFactory = factory;
		firePropertyChangeEvent(Controller.NODE_FACTORY_PROPERTY, oldValue, nodeFactory);
	}


	/**
	 * Gets the factory used to produce <code>Nodes</code>.
	 */
	public NodeFactory getNodeFactory() {
		return nodeFactory;
	}


	/**
	 * Sets the renderer that is responsible for drawing
	 * <code>Nodes</code>
	 *
	 * @param renderer The <code>NodeRenderer</code> to be used.
	 */
	public void setNodeRenderer(NodeRenderer renderer) {
		if(renderer == null) {
			throw new NullPointerException("NodeRenderer must not be null");
		}

		Object oldValue = this.nodeRenderer;
		this.nodeRenderer = renderer;
		graphView.setNodeRenderer(nodeRenderer);
		firePropertyChangeEvent(Controller.NODE_RENDERER_PROPERTY, oldValue, nodeRenderer);
	}


	/**
	 * Gets the renderer used to paint <code>Nodes</code>
	 */
	public NodeRenderer getNodeRenderer() {
		return nodeRenderer;
	}


	/**
	 * Sets the renderer that is responsible for getting
	 * the label for a given <code>Node</code>.
	 *
	 * @param renderer The <code>LabelRenderer</code> to be used.
	 */
	public void setNodeLabelRenderer(NodeLabelRenderer renderer) {
		if(renderer == null) {
			throw new NullPointerException("NodeLabelRenderer must not be null");
		}

		Object oldValue = this.nodeLabelRenderer;
		this.nodeLabelRenderer = renderer;
        firePropertyChangeEvent(Controller.NODE_LABEL_RENDERER_PROPERTY, oldValue, nodeLabelRenderer);
	}


	/**
	 * Gets the renderer used to generate labels for <code>Nodes</code>.
	 */
	public NodeLabelRenderer getNodeLabelRenderer() {
		return nodeLabelRenderer;
	}


	/**
	 * Gets the renderer used to generate labels for <code>Edges</code>
	 *
	 * @return The <code>EdgeLabelRenderer</code>
	 */
	public EdgeLabelRenderer getEdgeLabelRenderer() {
		return edgeLabelRenderer;
	}


	/**
	 * Sets the renderer used to generate labels for <code>Edges</code>.
	 *
	 * @param edgeLabelRenderer The <code>EdgeLabelRenderer</code> to be used.
	 */
	public void setEdgeLabelRenderer(EdgeLabelRenderer edgeLabelRenderer) {
		if(edgeLabelRenderer == null) {
			throw new NullPointerException("EdgeLabelRenderer must not be null");
		}

		Object oldValue = this.edgeLabelRenderer;

		this.edgeLabelRenderer = edgeLabelRenderer;

		firePropertyChangeEvent(Controller.EDGE_LABEL_RENDERER_PROPERTY, oldValue, this.edgeLabelRenderer);
	}


	/**
	 * Sets the renderer that is responsible for renderering <code>Edges</code>.
	 *
	 * @param renderer The <code>EdgeRenderer</code> to be used.
	 */
	public void setEdgeRenderer(EdgeRenderer renderer) {
		if(renderer == null) {
			throw new NullPointerException("EdgeRenderer must not be null");
		}

		Object oldValue = this.edgeRenderer;

		this.edgeRenderer = renderer;

		graphView.setEdgeRenderer(edgeRenderer);

		firePropertyChangeEvent(Controller.EDGE_RENDERER_PROPERTY, oldValue, edgeRenderer);
	}


	/**
	 * Gets the renderer that is used to paint <code>Edges</code>
	 */
	public EdgeRenderer getEdgeRenderer() {
		return edgeRenderer;
	}


	/**
	 * Adds a property change listener, which is informed when properties
	 * such as renderers, factories etc. change.
	 *
	 * @param lsnr The listener to be added.
	 */
	public void addPropertyChangeListener(PropertyChangeListener lsnr) {
		listeners.add(lsnr);
	}


	/**
	 * Removes a previously added <code>PropertyChangeListener</code>.
	 *
	 * @param lsnr The listener to be removed.
	 */
	public void removePropertyChangeListener(PropertyChangeListener lsnr) {
		listeners.remove(lsnr);
	}


	/**
	 * Propagates <code>PropertyChangeEvent</code>s to any registered listeners.
	 *
	 * @param propertyName The name of the property that has changed.
	 * @param oldValue     The old value of the property.
	 * @param newValue     The new value of the property.
	 */
	protected void firePropertyChangeEvent(String propertyName,
	                                       Object oldValue,
	                                       Object newValue) {
		Iterator<PropertyChangeListener> it = new ArrayList<PropertyChangeListener>(listeners).iterator();
		PropertyChangeEvent evt = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
		while(it.hasNext()) {
			final PropertyChangeListener pcl = it.next();
			pcl.propertyChange(evt);
		}
	}
}
