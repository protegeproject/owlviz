package uk.ac.man.cs.mig.util.graph.controller.impl;

import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.graph.controller.GraphGenerator;
import uk.ac.man.cs.mig.util.graph.controller.VisualisedObjectManager;
import uk.ac.man.cs.mig.util.graph.event.GraphGeneratorEvent;
import uk.ac.man.cs.mig.util.graph.event.GraphGeneratorListener;
import uk.ac.man.cs.mig.util.graph.event.VisualisedObjectManagerEvent;
import uk.ac.man.cs.mig.util.graph.event.VisualisedObjectManagerListener;
import uk.ac.man.cs.mig.util.graph.factory.EdgeFactory;
import uk.ac.man.cs.mig.util.graph.factory.GraphFactory;
import uk.ac.man.cs.mig.util.graph.factory.NodeFactory;
import uk.ac.man.cs.mig.util.graph.graph.Edge;
import uk.ac.man.cs.mig.util.graph.graph.Graph;
import uk.ac.man.cs.mig.util.graph.graph.Node;
import uk.ac.man.cs.mig.util.graph.model.GraphModel;
import uk.ac.man.cs.mig.util.graph.renderer.NodeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.NodeRenderer;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 13, 2004<br><br>
 *
 * matthew.horridge@cs.visualisedObjectManager.ac.uk<br>
 * www.cs.visualisedObjectManager.ac.uk/~horridgm<br><br>
 *
 *
 *
 */
public class DefaultGraphGenerator implements GraphGenerator
{
	private Graph graph; // The graph that we generate
	private VisualisedObjectManager visualisedObjectManager; // The manager that the graph is generated for
	private VisualisedObjectManagerListener visualisedObjectManagerListener;
	private GraphFactory graphFactory; // The factory responsible for generating the graph
	private NodeFactory nodeFactory; // The factory responsible for generating nodes
	private EdgeFactory edgeFactory; // The factory responsible for generating edges
	private NodeLabelRenderer nodeLabelRenderer;
	private NodeRenderer nodeRenderer;
	private Controller controller;

	private boolean rebuildGraph = true; // A flag to indicate that the graph is out of date and
										 // needs to be rebuilt
	private boolean recreateNodes = false;


	private Map nodeMap; // Maps objects to the nodes that represent the objects

	private ArrayList listeners;


	private GraphGeneratorEvent graphChangedEvent;

	private static final boolean EVENTDEBUG = false;

	public DefaultGraphGenerator(Controller controller,
	                             VisualisedObjectManager visualisedObjectManager,
	                             GraphFactory graphFactory,
	                             NodeFactory nodeFactory,
	                             EdgeFactory edgeFactory,
	                             NodeRenderer nodeRenderer)
	{

		if(controller == null)
		{
			throw new NullPointerException("Controller must not be null");
		}

		if(visualisedObjectManager == null)
		{
			throw new NullPointerException("VisualisedObjectManager must not be null");
		}

		if(graphFactory == null)
		{
			throw new NullPointerException("GraphFactory must not be null");
		}

		if(nodeFactory == null)
		{
			throw new NullPointerException("NodeFactory must not be null");
		}

		if(edgeFactory == null)
		{
			throw new NullPointerException("EdgeFactory must not be null");
		}

		if(nodeRenderer == null)
		{
			throw new NullPointerException("NodeRenderer must not be null");
		}

		// Set up instance variables
		this.controller = controller;

		this.visualisedObjectManager = visualisedObjectManager;

		this.graphFactory = graphFactory;

		this.nodeFactory = nodeFactory;

		this.edgeFactory = edgeFactory;

		this.nodeRenderer = nodeRenderer;

		// Add a property change listener, then we will be notified of changes
		// to any of the above instance variables
		controller.addPropertyChangeListener(this);


		nodeMap = new HashMap();
		
		listeners = new ArrayList();

		graphChangedEvent = new GraphGeneratorEvent(this);

		graph = graphFactory.createGraph();



		//TODO: Reimplement listener
		visualisedObjectManager.addVisualisedObjectManagerListener(visualisedObjectManagerListener = new VisualisedObjectManagerListener()
		{
			/**
			 * Called when an object is added to the list of objects
			 * being visualised.
			 * @param evt A <code>VisualisedObjectManagerEvent</code> that isShown
			 * information pertaining to the event.
			 */
			public void objectsAdded(VisualisedObjectManagerEvent evt)
			{
				ArrayList list = evt.getObjects();

				Node node;

				Dimension size = new Dimension();

			//	System.out.println("TRACE(DefaultGraphGenerator): objectsAdded");

				for(int i = 0; i < list.size(); i++)
				{
					node = addNode(list.get(i));

					DefaultGraphGenerator.this.nodeRenderer.getPreferredSize(node, size);

					node.setSize(size.width, size.height);

					//System.out.println("TRACE(DefaultGraphGenerator): object: " + list.get(i));
				}

				for(int i = 0; i < list.size(); i++)
				{
					addChildEdges(list.get(i));

					addParentEdges(list.get(i));
				}


				fireGraphChangedEvent();
			}

			/**
			 * Called when an object that was previously in the list of
			 * objects being visualised was removed.
			 * @param evt A <code>VisualisedObjectManagerEvent</code> that isShown
			 * information pertaining to the event
			 */
			public void objectsRemoved(VisualisedObjectManagerEvent evt)
			{
				ArrayList list = evt.getObjects();

				for(int i = 0; i < list.size(); i++)
				{
					removeNode(list.get(i));
				}

				fireGraphChangedEvent();
			}

			/**
			 * Called when the state of an object that is in the list
			 * of objects being visualised changes it's state (which
			 * might affect the visual appearence of the object.
			 * @param evt A <code>VisualisedObjectManagerEvent</code> that isShown
			 * information pertaining to the event
			 */
			public void objectsChanged(VisualisedObjectManagerEvent evt)
			{
				ArrayList list = evt.getObjects();

				Iterator it = list.iterator();

				Object obj;

				Node node;

				Dimension size = new Dimension(), oldSize;

				while(it.hasNext())
				{
					obj = it.next();

					node = (Node)nodeMap.get(obj);

					oldSize = (Dimension)node.getSize().clone();

					DefaultGraphGenerator.this.nodeRenderer.getPreferredSize(node, size);

					if(oldSize.equals(size) == false)
					{
						node.setSize(size.width, size.height);

						fireGraphChangedEvent();
					}
				}

				fireGraphChangedEvent();

			}

			public void parentObjectAdded(VisualisedObjectManagerEvent evt)
			{
				Node parentNode, childNode;

				parentNode = (Node)nodeMap.get(evt.getObjects().get(1));

				childNode = (Node)nodeMap.get(evt.getObjects().get(0));


				int dir = DefaultGraphGenerator.this.controller.getGraphModel().getRelationshipDirection(parentNode.getUserObject(),
				                                                              childNode.getUserObject());

				Edge edge = DefaultGraphGenerator.this.edgeFactory.createEdge(parentNode, childNode, dir);

				graph.add(edge);

				fireGraphChangedEvent();

			}

			public void parentObjectRemoved(VisualisedObjectManagerEvent evt)
			{
				Node tailNode, headNode;

				tailNode = (Node)nodeMap.get(evt.getObjects().get(1));

				headNode = (Node)nodeMap.get(evt.getObjects().get(0));

				graph.remove(tailNode, headNode);

				fireGraphChangedEvent();
			}

			public void childObjectAdded(VisualisedObjectManagerEvent evt)
			{
				Node parentNode, childNode;

				parentNode = (Node)nodeMap.get(evt.getObjects().get(0));

				childNode = (Node)nodeMap.get(evt.getObjects().get(1));

				int dir = DefaultGraphGenerator.this.controller.getGraphModel().getRelationshipDirection(parentNode.getUserObject(),
								                                                              childNode.getUserObject());



				Edge edge = DefaultGraphGenerator.this.edgeFactory.createEdge(parentNode, childNode, dir);

				graph.add(edge);

				fireGraphChangedEvent();
			}

			public void childObjectRemoved(VisualisedObjectManagerEvent evt)
			{
				Node tailNode, headNode;

				tailNode = (Node)nodeMap.get(evt.getObjects().get(0));

				headNode = (Node)nodeMap.get(evt.getObjects().get(1));

				graph.remove(tailNode, headNode);

				fireGraphChangedEvent();
			}
		});

	}

	/**
	 * Gets the <code>Graph</code> based upon the
	 * visible objects in the <code>VisualisedObjectManager</code>.
	 * @return A Graph representing visible objects.  Note that this
	 * <code>Graph</code> may or may not be newly created.
	 */
	public Graph getGraph()
	{
		if(rebuildGraph == true)
		{
			generateGraph();
		}

		return graph;
	}

	/**
	 * Retrieves the <code>VisualisedObjectManager</code>, which graphs
	 * are based upon.
	 * @return The <code>VisualisedObjectManager</code>
	 */
	public VisualisedObjectManager getVisualisedObjectManager()
	{
		return visualisedObjectManager;
	}

	/**
	 * Sets the <code>VisualisedObjectManager</code>, which the <code>GraphGenerator</code>
	 * bases the <code>Graph</code> on.
	 * @param manager The <code>VisualisedObjectManager</code> to use.
	 */
	public void setVisualisedObjectManager(VisualisedObjectManager manager)
	{
		this.visualisedObjectManager = manager;

		invalidateGraph();
	}

	/**
	 * Regenrates the graph
	 */
	protected void generateGraph()
	{
		if(graph == null)
		{
			graph = graphFactory.createGraph();
		}
		else
		{
			graph.removeAll();
		}

		// Remove any nodes from the node map
		// that should no longer be in the graph.
		cleanNodeMap();


		Iterator objIt;

		Object obj;

		// Add the nodes to the graph
		objIt = visualisedObjectManager.iterator();

		while(objIt.hasNext())
		{
			obj = objIt.next();

			addNode(obj);
		}

		// Reset the flag (might not have been true anyway but...)
		recreateNodes = false;

		// Now create and add the edges to the graph
		// Go through all the nodes, add add the child edges
		// for each node

		objIt = visualisedObjectManager.iterator();

		while(objIt.hasNext())
		{
			obj = objIt.next();

			addChildEdges(obj);

			addParentEdges(obj);
		}

		// Reset the flag
		rebuildGraph = false;
	}

	/**
	 * Adds a <code>Node/code> to the graph that represents
	 * the sepcified object.
	 * @param obj The object to be represented as a <code>Node</code>.
	 */
	protected Node addNode(Object obj)
	{
		Node node = null;

		if(recreateNodes == false)
		{
			node = (Node)nodeMap.get(obj);
		}

		if(node == null)
		{
			// Create a Node for the object
			node = nodeFactory.createNode(obj);

			// Map the object to the node
			nodeMap.put(obj, node);
		}

		// Add the node to the graph
		graph.add(node);

		return node;
	}

	protected void removeNode(Object obj)
	{
		graph.remove((Node)(nodeMap.get(obj)));

		nodeMap.remove(obj);
	}

	/**
	 * Creates the <code>Edges</code> that link the <code>Node</code>
	 * which represents the specified object to the <code>Nodes</code> that
	 * represent the parents of the specified object. For the edge to be
	 * added, both the headNode and tailNode must be alreay in the graph.
	 * @param obj The object whose parent <code>Edges</code> should be created.
	 */
	protected void addParentEdges(Object obj)
	{
		// Get the parents

		Iterator parIt;

		Object par;

		Node node, parNode;

		Edge edge;

		GraphModel model = visualisedObjectManager.getGraphModel();

		parIt = model.getParents(obj);

		node = (Node)nodeMap.get(obj);

		while (parIt.hasNext())
		{
			par = parIt.next();

			parNode = (Node) nodeMap.get(par);

			if (parNode != null)
			{
				int dir = controller.getGraphModel().getRelationshipDirection(parNode.getUserObject(),
				                                                              node.getUserObject());
				edge = edgeFactory.createEdge(parNode, node, dir);

				graph.add(edge);
			}
		}
	}

	/**
	 * Creates the <code>Edges</code> that link the <code>Node</code>
	 * which represents the specified object to the <code>Nodes</code> that
	 * represent the children of the specified object.  For the edge to be
	 * added, both the headNode and tailNode must be alreay in the graph.
	 * @param obj The object whose parent <code>Edges</code> should be created.
	 */
	protected void addChildEdges(Object obj)
	{
		Iterator childIt;

		Object child;

		Node node, childNode;

		Edge edge;

		GraphModel model = visualisedObjectManager.getGraphModel();

		childIt = model.getChildren(obj);

		node = (Node)nodeMap.get(obj);

		while(childIt.hasNext())
		{
			child = childIt.next();

			childNode = (Node)nodeMap.get(child);

			if(childNode != null)
			{
				int dir = controller.getGraphModel().getRelationshipDirection(node.getUserObject(),
				                                                              childNode.getUserObject());
				edge = edgeFactory.createEdge(node, childNode, dir);

				graph.add(edge);
			}
		}
	}



	/**
	 * Removes any nodes in the nodeMap that represent
	 * objects that aren't in the list of objects held by the
	 * <code>VisualisedObjectManager</code>.
	 */
	protected void cleanNodeMap()
	{
		Iterator it = nodeMap.keySet().iterator();

		Object obj;

		while(it.hasNext())
		{
			obj = it.next();

			if(visualisedObjectManager.isShown(obj) == false)
			{
				nodeMap.remove(obj);
			}
		}
	}



	/**
	 * Causes the graph to be invalidated, which causes the graph
	 * to be rebuilt when asked for.
	 */
	public void invalidateGraph()
	{
		rebuildGraph = true;

		doChangeBroadcast();
	}

	/**
	 * Returns the <code>Node<code> that represents the given object.
	 * @param obj The object.
	 * @return The <code>Node</code> that represents the object, or
	 * <code>null</code> if the <code>Graph</code> does not contain
	 * a <code>Node</code> that represents the given object.
	 */
	public Node getNodeForObject(Object obj)
	{
		return (Node)nodeMap.get(obj);
	}


	/**
	 * Adds a listener that is informed of events generated by the
	 * <code>GraphGenerator</code>, such as a 'graph changed' event.
	 * @param lsnr The listener to be added.
	 */
	public void addGraphGeneratorListener(GraphGeneratorListener lsnr)
	{
		listeners.add(lsnr);
	}

	/**
	 * Removes a previously added <code>GraphGeneratedListener</code>
	 * @param lsnr The listener to be removed.
	 */
	public void removeGraphGeneratorListener(GraphGeneratorListener lsnr)
	{
		listeners.remove(lsnr);
	}

	/**
	 * Gets an <code>Iterator</code> that can be used to traverse the
	 * listeners.
	 * @return The <code>Iterator</code>, which can also be used to
	 * add and remove listeners.
	 */
	public Iterator getGraphGeneratorListeners()
	{
		return listeners.iterator();
	}

	private void doChangeBroadcast()
	{
		fireGraphChangedEvent();
	}

	protected void fireGraphChangedEvent()
	{
		if(EVENTDEBUG)
		{
			System.out.println("TRACE(DefaultGraphGenerator) firing graph changed event");
		}

		for(int i = 0; i < listeners.size(); i++)
		{
			((GraphGeneratorListener)listeners.get(i)).graphChanged(graphChangedEvent);
		}
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Implementation of PropertyChangeListener
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * This method gets called when a bound property is changed.
	 * @param evt A PropertyChangeEvent object describing the event source
	 *   	and the property that has changed.
	 */

	public void propertyChange(PropertyChangeEvent evt)
	{
		if(evt.getPropertyName().equals(Controller.GRAPH_FACTORY_PROPERTY))
		{
			graphFactory = (GraphFactory) evt.getNewValue();
		}
		else if(evt.getPropertyName().equals(Controller.NODE_FACTORY_PROPERTY))
		{
			nodeFactory = (NodeFactory)evt.getNewValue();
		}
		else if(evt.getPropertyName().equals(Controller.EDGE_FACTORY_PROPERTY))
		{
			edgeFactory = (EdgeFactory)evt.getNewValue();
		}
		else if(evt.getPropertyName().equals(Controller.VISUALISED_OBJECT_MANAGER_PROPERTY))
		{
			visualisedObjectManager.removeVisualisedObjectManagerListener(visualisedObjectManagerListener);

			visualisedObjectManager = (VisualisedObjectManager)evt.getNewValue();

			visualisedObjectManager.addVisualisedObjectManagerListener(visualisedObjectManagerListener);

		}
		else if(evt.getPropertyName().equals(Controller.NODE_RENDERER_PROPERTY))
		{
			nodeRenderer = (NodeRenderer)evt.getNewValue();
		}

	}
}
