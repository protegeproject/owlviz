package uk.ac.man.cs.mig.util.graph.model;

import uk.ac.man.cs.mig.util.graph.event.GraphModelListener;

import java.util.Iterator;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Dec 27, 2003<br><br>
 *
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */

/**
 * The GraphModel provides an interface to the
 * data visualised by OWLViz, without assuming what the
 * data is/represents.  It provides methods to
 * access parent and child objects of any given object.
 */
public interface GraphModel
{
	/**
	 * The relationship does not have a direction.
	 */
	public static final int DIRECTION_NONE = 1;

	/**
	 * The direction of the relationship is from
	 * parent to child.
	 */
	public static final int DIRECTION_FORWARD = 2;

	/**
	 * The direction of the relationship is from
	 * child to parent
	 */
	public static final int DIRECTION_BACK = 3;

	/**
	 * The relationship is bidirectional - i.e. it
	 * holds from parent to child and from child
	 * to parent.
	 */
	public static final int DIRECTION_BOTH = 4;



	/**
	 * Retrives the number of children that the specified
	 * object has.
	 * @param obj The object
	 * @return The number of children.
	 */
	public int getChildCount(Object obj);

	/**
	 * Gets the children of the specified object
	 * @return An iterator, which can be used to traverse
	 * the children.
	 */
	public Iterator getChildren(Object obj);

	/**
	 * Retrives the number of parents that the specified
	 * object has.
	 * @param obj The object
	 * @return The number of parents
	 */
	public int getParentCount(Object obj);

	/**
	 * Retrieves the parents of the specified object
	 * @param obj The object
	 * @return The parents of the specified object.
	 */
	public Iterator getParents(Object obj);

	/**
	 * Tests whether or not the specified object is
	 * contained within the graph model.
	 * @param obj The object to test for.
	 * @return <code>true</code> if the specified object is contained in the model,
	 * or <code>false</code> if the specified object is not contained in the model.
	 */
	public boolean contains(Object obj);

    /**
     * Retrieves the type of relationship between the specified objects.
     * @param parentObject The parent object.
     * @param childObject The child object.
     * @return An object representing the relationship between the child
     * object and parent object. For example, this could be <code>String</code>
     * that represents the name of the relationship, such as "is-a".
     */
    public Object getRelationshipType(Object parentObject, Object childObject);

	/**
	 * Gets the direction of the relationship.  In some relationships the
	 * edge direction is in an opposite direction to the parent/child relationship.
	 * For example, a superclass/subclass relationship - the parent is the
	 * superclass and the child is the subclass, but the direction of the edge
	 * is from subclass to superclass - i.e. child to parent.  This method
	 * allows the direction of the <code>Edge</code> between two objects to
	 * be customised.
	 * @param parentObject The parent object.
	 * @param childObject The child object.
	 * @return The direction of the edge that connects the objects.  Directions
	 * are defined in this <code>GraphModel</code> interface.  A forward direction
	 * represents and parent-to-child relationship.  A back direction represents
	 * a child-to-parent relationship. 
	 */
	public int getRelationshipDirection(Object parentObject, Object childObject);

	/**
	 * In some situations when an object is displayed in a graph
	 * it is necessary to display other (related) objects automatically.
	 * This method will usually be called so that objects that are
	 * related to the specified object (but not necessarily parent/child
	 * objects) can be retrieved.
	 * @param obj The object for which related objects are to be
	 * retrieved.
	 * @return An iterator that can be used to traverse the related objects.
	 */
	public Iterator getRelatedObjectsToAdd(Object obj);


	/**
	 * In some situations when an object displayed in a graph
	 * is made invisible it might be useful to make other
	 * related objects invisible.  This method will be
	 * called to determine if there are any other objects to
	 * be made invisible.
	 * @param obj The object being made invisible.
	 * @return Any objects that are related to <code>obj</code>
	 * that should also be made invisible. 
	 */
	public Iterator getRelatedObjectsToRemove(Object obj);


	/**
	 * Adds a listener to the model that is notified when events such a <code>Node</code>
	 * addition and removal take place.
	 * @param lsnr The listener to be added.
	 */
	public void addGraphModelListener(GraphModelListener lsnr);

	/**
	 * Removes a previously added <code>GraphModelListener</code>.
	 * @param lsnr The listener to be removed.
	 */
	public void removeGraphModelListener(GraphModelListener lsnr);

	/**
	 * Gets an <code>Iterator</code> that can be used to
	 * traverse and remove listeners.
	 */
	public Iterator<GraphModelListener> getListeners();

    /**
     * Disposed of the graph model.  This clears any cached information
     * and removes listeners.
     */
    public void dispose();
}
