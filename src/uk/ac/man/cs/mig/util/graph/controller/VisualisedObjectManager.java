package uk.ac.man.cs.mig.util.graph.controller;

import uk.ac.man.cs.mig.util.graph.event.VisualisedObjectManagerListener;
import uk.ac.man.cs.mig.util.graph.model.GraphModel;

import java.beans.PropertyChangeListener;
import java.util.Iterator;

/**
 * User: matthewhorridge
 * The Univeristy Of Manchester
 * Medical Informatics Group
 * Date: Dec 27, 2003
 *
 * matthew.horridge@cs.man.ac.uk
 * www.cs.man.ac.uk/~horridgm
 *
 * The visualised object manager is associated with a graph model,
 * and is responsible for keeping
 * track of displayed/visualised objects from the graph model, and whether or not their
 * parents/children are shown.  Listeners may attach themselves to the visualised object
 * manager in order to be informed when the <i>visualised graph</i> changes.
 *
 */
public interface VisualisedObjectManager extends PropertyChangeListener
{
	/**
	 * Gets the GraphModel that the visualised object manager
	 * is responsible for.
	 * @return The <code>GraphModel</code> that the visualised object
	 * manager holds on to.
	 */
	public GraphModel getGraphModel();

	/**
	 * Sets the <code>GraphModel</code> that the <code>VisualisedObject</code>
	 * manager is responsible for.
	 * @param model The <code>GraphModel</code> to use.
	 */
	public void setGraphModel(GraphModel model);

	/**
	 * Specifies that the object should be visible
	 * in a visualisation of the graph model.
	 * @param obj  The object to be made visisible
	 */
	public void showObject(Object obj);

	/**
	 * Specifies that the object should be visible in
	 * a visualisation of the graph model.
	 * @param obj The object to be made visible.
	 * @param parentChildRadius The number of levels of parents and children to show.
	 * Note that a level of 0 is equivalent to the <code>showObject(Object obj)</code>
	 * method.
	 * @param objClass Can be used to filter the type of objects to be shown.
	 */
	public void showObject(Object obj, int parentChildRadius, Class objClass);

	/**
	 * Hides objects that are past the specified radius.
	 * @param obj The object which the radius should be centred at.
	 * @param radius The radius.  Objects past this radius are hidden.
	 * For example, if a radius of 1 is specified, then grandchildren
	 * and grandparents would be hidden.
	 * @param objClass Specifies the class of objects to remain visible.
	 */
	public void hideObjects(Object obj, int radius, Class objClass);

    /**
     * Requests that the specified objects be added to
     * the visualisation.
     * @param objs An array of objects to be added.
     */
    public void showObjects(Object [] objs);


	/**
	 * Makes the specified object hidden (if visible) is
	 * a visualisation of the graph model.
	 * @param obj
	 */
	public void hideObject(Object obj);

	/**
	 * Shows the children of the specified object.
	 * @param obj The object who's children are to be made visible
	 * @param objClass Can be used to filter the type of objects to be shown.
	 */
	public void showChildren(Object obj, Class objClass);

	/**
	 * Shows the children of the specified object to the number
	 * of levels specified.  For example, a level of 1 will show
	 * the immediate children, a level of 2 will show the immediate
	 * children, and their children, and so on.
	 * @param obj  The object who's children are to be made visible
	 * @param levels The number of child levels to show (>= 1).
	 * @param objClass Can be used to filter the type of objects to be shown.
	 */
	public void showChildren(Object obj, int levels, Class objClass);

	/**
	 * Hides the children of the specified object.
	 * @param obj The object who's children are to be hidden
	 */
	public void hideChildren(Object obj);

	/**
	 * Shows the parents of the specified object.
	 * @param obj The object who's parents are to be
	 * made visible.
	 * @param objClass Can be used to filter the type of objects to be shown.
	 */
	public void showParents(Object obj, Class objClass);

	/**
	 * Shows the parents of the specified object to the number of levels
	 * specified.  For example, a level of 1 will show the immediate
	 * parents, a level of 2 will show the immediate parents and their
	 * parents and so on.
	 * @param obj The object who's parents are to be shown.
	 * @param levels The number of parent levels to show. (>= 1).
	 * @param objClass Can be used to filter the type of objects to be shown.
	 */
	public void showParents(Object obj, int levels, Class objClass);

	/**
	 * Hides the parents of the speicfied object.
	 * @param obj The object who's parents are to
	 * be hidden.
	 */
	public void hideParents(Object obj);

    /**
     * Requests that the specified objects should be
     * hidden/removed from the objects being visualised.
     * @param objs The objects to hide.
     */
    public void hideObjects(Object [] objs);

	/**
	 * Hides all currently visible objects.
	 */
	public void hideAll();

	/**
	 * Gets the number of objects contained in the list of objects
	 * to be visualised.
	 * @return The number of visualised objects.
	 */
	public int getNumberOfVisualisedObjects();

	/**
	 * Gets all of the objects that are being visualised.
	 * @return An array of the visualised objects.
	 */
	public Object [] getVisualisedObjects();

	/**
	 * Determines whether the sepcified object is in the list
	 * of object being visualised.
	 * @param obj The object to test for.
	 * @return <code>true</code> if the object is in the list of objects to be
	 * visualised. <code>false</code> if the object is not in the list of objects
	 * to be visualised.
	 */
	public boolean isShown(Object obj);

	/**
	 * Retrives an iterator, which can be used to traverse the
	 * list of visualised objects.
	 * @return The Iterator
	 */
	public Iterator iterator();

	/**
	 * Determines how many children of the specified object are shown.
	 * @param obj The object
	 * @return The number of children shown, or -1 if the specified
	 * object is not being visualised.
	 */
	public int getChildrenShownCount(Object obj);

	/**
	 * Determines how many children of the specified object are hidden.
	 * @param obj The object
	 * @return The number of children hidden, or -1 if the specified
	 * object is not being visualised.
	 */
	public int getChildrenHiddenCount(Object obj);

	/**
	 * Determines how many parents of the specified object are shown.
	 * @param obj The object
	 * @return The number of parents shown, or -1 if the specified
	 * object is not being visualised.
	 */
	public int getParentsShownCount(Object obj);

	/**
	 * Determines how many parents of the specified object are hidden.
	 * @param obj The object
	 * @return The number of parents hidden, or -1 if the specified
	 * object is not being visualised.
	 */
	public int getParentsHiddenCount(Object obj);

	/**
	 * Adds a <code>VisualisedObjectManagerListener</code>, which is notified of
	 * events relating to visualised object addition/removal and change.
	 * @param lsnr The listener to be added.
	 */
	public void addVisualisedObjectManagerListener(VisualisedObjectManagerListener lsnr);

	/**
	 * Removes a <code>VisualisedObjectManagaerListener</code>, which was previously
	 * added.
	 * @param lsnr The listener to be removed.
	 */
	public void removeVisualisedObjectManagerListener(VisualisedObjectManagerListener lsnr);

	/**
	 * Gets an iterator that can be used to traverse the list of listeners.
	 * @return An <code>Iterator</code>, which can be used to add and remove
	 * listeners, as well as traverse the list of listeners.
	 */
	public Iterator getVisualisedObjectManagerListeners();

    /**
     * Forces an update of the information pertaining
     * to whether an object's children and parents
     * are visible.
     */
    public void updateInfo();
}
