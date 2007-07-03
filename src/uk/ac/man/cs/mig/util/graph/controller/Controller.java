package uk.ac.man.cs.mig.util.graph.controller;

import uk.ac.man.cs.mig.util.graph.factory.EdgeFactory;
import uk.ac.man.cs.mig.util.graph.factory.GraphFactory;
import uk.ac.man.cs.mig.util.graph.factory.NodeFactory;
import uk.ac.man.cs.mig.util.graph.layout.GraphLayoutEngine;
import uk.ac.man.cs.mig.util.graph.model.GraphModel;
import uk.ac.man.cs.mig.util.graph.renderer.EdgeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.EdgeRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.NodeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.NodeRenderer;
import uk.ac.man.cs.mig.util.graph.ui.GraphView;

import java.beans.PropertyChangeListener;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 9, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * A controller, which ties together the various
 * models and views.
 */
public interface Controller
{
	/**
	 * Controller property names
	 */
	public static final String GRAPH_MODEL_PROPERTY = "graphModel";
	public static final String VISUALISED_OBJECT_MANAGER_PROPERTY = "visualisedObjectManager";
	public static final String GRAPH_SELECTION_MODEL_PROPERTY = "graphSelectionModel";
	public static final String GRAPH_GENERATOR_PROPERTY = "graphGenerator";
	public static final String GRAPH_LAYOUT_ENGINE_PROPERTY = "graphLayoutEngine";
	public static final String GRAPH_VIEW_PROPERTY = "graphView";

	public static final String NODE_RENDERER_PROPERTY = "nodeRenderer";
	public static final String NODE_LABEL_RENDERER_PROPERTY = "nodeLabelRenderer";
	public static final String EDGE_RENDERER_PROPERTY = "edgeRenderer";
	public static final String EDGE_LABEL_RENDERER_PROPERTY = "edgeLabelRenderer";

	public static final String GRAPH_FACTORY_PROPERTY = "graphFactory";
	public static final String NODE_FACTORY_PROPERTY = "nodeFactory";
	public static final String EDGE_FACTORY_PROPERTY = "edgeFactory";

	/**
	 * Returns the <code>GraphModel</code>, which is an interface
	 * to the underlying data that is represented by the <code>Graph</code>.
	 * @return The <code>GraphModel</code>.
	 */
	public GraphModel getGraphModel();

	/**
	 * Sets the <code>GraphModel</code>, which is an interface
	 * to the underlying data that is represented by the <code>Graph</code>
	 * @param model The <code>GraphModel</code>
	 */
	public void setGraphModel(GraphModel model);

	/**
	 * Returns the <code>GraphLayoutEngine</code>, which is
	 * capable of sizing and positioning <code>Nodes</code>
	 * and <code>Edges</code>.
	 * @return The GraphLayoutEngine
	 */
	public GraphLayoutEngine getGraphLayoutEngine();

	/**
	 * Sets the <code>GraphLayoutEngine</code>, which is used
	 * to size and position <code>Nodes</code> and <code>Edges</code>
	 * @param layoutEngine The new <code>GraphLayoutEngine</code>
	 */
	public void setGraphLayoutEngine(GraphLayoutEngine layoutEngine);

	/**
	 * Sets the factory used to produce <code>Graphs</code>
	 * @param factory The <code>GraphFactory</code>
	 */
	public void setGraphFactory(GraphFactory factory);

	/**
	 * Gets the factory used to produce <code>Graphs</code>
	 */
	public GraphFactory getGraphFactory();

	/**
	 * Sets the factory used to produce <code>Edges</code>
	 * @param factory The <code>GraphFactory</code>
	 */
	public void setEdgeFactory(EdgeFactory factory);

	/**
	 * Gets the factory that produces <code>Edges</code>
	 */
	public EdgeFactory getEdgeFactory();

	/**
	 * Sets the factory used to produce <code>Nodes</code>.
	 * @param factory The <code>NodeFactory</code>
	 */
	public void setNodeFactory(NodeFactory factory);

	/**
	 * Gets the factory used to produce <code>Nodes</code>.
	 */
	public NodeFactory getNodeFactory();

	/**
	 * Sets the renderer that is responsible for drawing
	 * <code>Nodes</code>
	 * @param renderer The <code>NodeRenderer</code> to be used.
	 */
	public void setNodeRenderer(NodeRenderer renderer);

	/**
	 * Gets the renderer used to paint <code>Nodes</code>
	 */
	public NodeRenderer getNodeRenderer();

	/**
	 * Sets the renderer that is responsible for getting
	 * the label for a given <code>Node</code>.
	 * @param renderer The <code>LabelRenderer</code> to be used.
	 */
	public void setNodeLabelRenderer(NodeLabelRenderer renderer);

	/**
	 * Gets the renderer used to generate labels for <code>Nodes</code>.
	 */
	public NodeLabelRenderer getNodeLabelRenderer();

	/**
	 * Sets the renderer that is responsible for renderering <code>Edges</code>.
	 * @param renderer The <code>EdgeRenderer</code> to be used.
	 */
	public void setEdgeRenderer(EdgeRenderer renderer);

	/**
	 * Gets the renderer that is used to paint <code>Edges</code>
	 */
	public EdgeRenderer getEdgeRenderer();

	/**
	 * Gets the renderer that is used to retrieve the labels
	 * for <code>Edges</code>.
	 * @return The <code>EdgeLabelRenderer</code>.
	 */
	public EdgeLabelRenderer getEdgeLabelRenderer();

	/**
	 * Sets the renderer used to determine the labels
	 * for <code>Edges</code>.
	 * @param edgeLabelRenderer The <code>EdgeLabelRenderer</code>
	 * to be used.
	 */
	public void setEdgeLabelRenderer(EdgeLabelRenderer edgeLabelRenderer);

	/**
	 * Returns the selection model that is responsible for managing user
	 * interaction in the form of <code>Node</code> and <code>Edge</code>
	 * selections.
	 * @return  The <code>DefaultGraphSelectionModel</code>.
	 */
	public GraphSelectionModel getGraphSelectionModel();

	/**
	 * Sets the selection model that is responsible for managing user
	 * interaction in the form of <<code>Node</code> and <code>Edge</code>
	 * selections.
	 * @param model The <code>GraphSelectionModel</code> that should be used.
	 */
	public void setGraphSelectionModel(GraphSelectionModel model);

	/**
	 * Returns the <code>VisualisedObjectManager</code> which is responsible
	 * for keeping track of the objects being visualised (and hence contained
	 * in the <code>Graph</code>).
	 * @return The <code>VisualisedObjectManager</code> that isShown details of the
	 * objects from the <code>GraphModel</code> that are being visualised.
	 */
	public VisualisedObjectManager getVisualisedObjectManager();

	/**
	 * Sets the <code>VisualisedObjectManager</code> that is responsible
	 * for keeping track of objects contained in the <code>Graph</code>
	 */
	public void setVisualisedObjectManager(VisualisedObjectManager manager);

	/**
	 * Returns the <code>GraphGenerator</code> which is responsible for
	 * creating and maintaining the <code>Graph</code> that represents
	 * the list of objects held by the <code>VisualisedObject</code> manager.
	 * @return The <code>GraphGenerator</code>.
	 */
	public GraphGenerator getGraphGenerator();

	/**
	 * Sets the <code>GraphGenerator</code>, which is used to create
	 * a <code>Graph</code> that is based on the contents held by
	 * the <code>VisualisedObjectManager</code>
	 */
	public void setGraphGenerator(GraphGenerator generator);

	/**
	 * Retrieves the <code>GraphView</code> currently attached to the <code>Controller</code>
	 */
	public GraphView getGraphView();

	/**
	 * Sets the view that is used paint the <code>Graph</code> on.
	 */
	public void setGraphView(GraphView view);

	/**
	 * Adds a property change listener, which is informed when properties
	 * such as renderers, factories etc. change.
	 * @param lsnr The listener to be added.
	 */
	public void addPropertyChangeListener(PropertyChangeListener lsnr);

	/**
	 * Removes a previously added <code>PropertyChangeListener</code>.
	 * @param lsnr The listener to be removed.
	 */
	public void removePropertyChangeListener(PropertyChangeListener lsnr);
}
