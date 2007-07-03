package uk.ac.man.cs.mig.util.graph.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

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
public class DefaultGraphModel extends AbstractGraphModel
{
	private HashMap nodes;

	public DefaultGraphModel()
	{
		super();
		
		nodes = new HashMap();
	}

    public void dispose() {
        nodes.clear();
    }

    /**
	 * Adds an object to the model.
	 * @param obj The object to be added.
	 */
	public void add(Object obj)
	{
		nodes.put(obj, new DefaultGraphNode(obj));

		ArrayList objects = new ArrayList(1);

		objects.add(obj);

		fireObjectsAddedEvent(objects);
	}

	/**
	 * Adds parent-child relationship/edge to the model.  If the specified
	 * parent and child are not already present in the model, then they
	 * are automatically added.  The specified objects are wrapped as userObjects
	 * in a <code>DefaultGraphNode</code>.
	 * @param parentObject The parent object.  If the parentObject is not
	 * present in the model, it will be added.
	 * @param childObject The child object.  If the childObject is not present
	 * in the model, it will be added.
	 */
	public void add(Object parentObject, Object childObject)
	{
		DefaultGraphNode childNode = (DefaultGraphNode) nodes.get(childObject);

		ArrayList list = new ArrayList(2);

		if(childNode == null)
		{
			childNode = new DefaultGraphNode(childObject);

			nodes.put(childObject, childNode);

			list.add(childObject);

		}

		DefaultGraphNode parentNode = (DefaultGraphNode)nodes.get(parentObject);

		if(parentNode == null)
		{
			parentNode = new DefaultGraphNode(parentObject);

			nodes.put(parentObject, parentNode);

			list.add(parentObject);
		}

		boolean edgeAdded = parentNode.addChild(childNode);

		if(list.size() > 0)
		{
			fireObjectsAddedEvent(list);
		}

		if(edgeAdded == true)
		{	
			fireChildAddedEvent(parentObject, childObject);
		}


	}

	public void remove(Object obj)
	{
		DefaultGraphNode node = (DefaultGraphNode)nodes.get(obj);

		if(node != null)
		{
			node.removeFromChildren();

			node.removeFromParents();

			nodes.remove(obj);

			ArrayList list = new ArrayList(1);

			list.add(obj);

			fireObjectsRemovedEvent(list);
		}
	}

	/**
	 * Retrives the number of children that the specified
	 * object has.
	 * @param obj The object
	 * @return The number of children.
	 */
	public int getChildCount(Object obj)
	{
		int count = 0;

		DefaultGraphNode node = (DefaultGraphNode)nodes.get(obj);

		if(node != null)
		{
			count = node.getChildCount();
		}

		return count;
	}

	/**
	 * Gets the children of the specified object
	 * @return An iterator, which can be used to traverse
	 * the children.
	 */
	public Iterator getChildren(Object obj)
	{
		Iterator it = null;

		DefaultGraphNode node = (DefaultGraphNode)nodes.get(obj);

		if(node != null)
		{
			it = node.getChilderenUserObjects();
		}

		return it;
	}

	/**
	 * Retrives the number of parents that the specified
	 * object has.
	 * @param obj The object
	 * @return The number of parents
	 */
	public int getParentCount(Object obj)
	{
		int count = 0;

		DefaultGraphNode node = (DefaultGraphNode)nodes.get(obj);

		if(node != null)
		{
			count = node.getParentCount();
		}

		return count;
	}

	/**
	 * Retrieves the parents of the specified object
	 * @param obj The object
	 * @return The parents of the specified object.
	 */
	public Iterator getParents(Object obj)
	{
		Iterator it = null;

		DefaultGraphNode node = (DefaultGraphNode)nodes.get(obj);


		if(node != null)
		{
			it = node.getParentUserObjects();
		}

		return it;
	}

	/**
	 * Tests whether or not the specified object is
	 * contained within the graph model.
	 * @param obj The object to test for.
	 * @return <code>true</code> if the specified object is contained in the model,
	 * or <code>false</code> if the specified object is not contained in the model.
	 */
	public boolean contains(Object obj)
	{
		return nodes.containsKey(obj);
	}

    /**
     * Retrieves the type of relationship between the specified objects.
     *
     * @param parentObject The parent object.
     * @param childObject  The child object.
     * @return An object representing the relationship between the child
     *         object and parent object.
     */
    public Object getRelationshipType(Object parentObject, Object childObject)
    {
        return "is-a";
    }




	/**
	 * In some situations when an object is displayed in a graph
	 * it is necessary to display other (related) objects automatically.
	 * This method will usually be called so that objects that are
	 * related to the specified object (but not necessarily parent/child
	 * objects) can be retrieved.
	 *
	 * @param obj The object for which related objects are to be
	 *            retrieved.
	 * @return An iterator that can be used to traverse the related objects.
	 */
	public Iterator getRelatedObjectsToAdd(Object obj)
	{
		return Collections.EMPTY_LIST.iterator();
	}


	/**
	 * In some situations when an object displayed in a graph
	 * is made invisible it might be useful to make other
	 * related objects invisible.  This method will be
	 * called to determine if there are any other objects to
	 * be made invisible.
	 *
	 * @param obj The object being made invisible.
	 * @return Any objects that are related to <code>obj</code>
	 *         that should also be made invisible.
	 */
	public Iterator getRelatedObjectsToRemove(Object obj)
	{
		return Collections.EMPTY_LIST.iterator();
	}


	/**
	 * Gets the direction of the relationship
	 *
	 * @param parentObject The parent object.
	 * @param childObject  The child object.
	 * @return The direction of the edge that connects the objects.  Directions
	 *         are defined in the <code>Edge</code> interface.  A forward direction
	 *         represents and parent-to-child relationship.  A back direction represents
	 *         a child-to-parent relationship.
	 */
	public int getRelationshipDirection(Object parentObject, Object childObject)
	{
		return DIRECTION_FORWARD;
	}
}
