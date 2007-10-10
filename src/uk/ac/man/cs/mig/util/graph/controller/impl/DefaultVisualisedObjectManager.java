package uk.ac.man.cs.mig.util.graph.controller.impl;

import uk.ac.man.cs.mig.util.graph.controller.VisualisedObjectManager;
import uk.ac.man.cs.mig.util.graph.event.GraphModelEvent;
import uk.ac.man.cs.mig.util.graph.event.GraphModelListener;
import uk.ac.man.cs.mig.util.graph.event.VisualisedObjectManagerEvent;
import uk.ac.man.cs.mig.util.graph.event.VisualisedObjectManagerListener;
import uk.ac.man.cs.mig.util.graph.model.GraphModel;

import java.beans.PropertyChangeEvent;
import java.util.*;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 12, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public class DefaultVisualisedObjectManager implements VisualisedObjectManager
{
	private GraphModel model;
	private VisualisedObjectCache visualisedObjects;

	private ArrayList<VisualisedObjectManagerListener> listeners;


	private static final boolean EVENTDEBUG = false;

	
	public DefaultVisualisedObjectManager(GraphModel model)
	{
		if(model == null)
		{
			throw new NullPointerException("GraphModel must not be null");
		}

		visualisedObjects = new VisualisedObjectCache();

		listeners = new ArrayList<VisualisedObjectManagerListener>();

		this.model = model;

		this.model.addGraphModelListener(new GraphModelListener()
		{
			/**
			 * Called when a node is added to the graph.
			 * @param evt The event, containing info about the
			 * objectAdded action.
			 */
			public void objectAdded(GraphModelEvent evt)
			{
				// Don't add the object automatically,
				// therefore, we don't want to do anything
				// here.
			}

			/**
			 * Called when a node is removed from the graph.
			 * @param evt The event, containing info about the
			 * nodeRemoved action.
			 */
			public void objectRemoved(GraphModelEvent evt)
			{
				ArrayList list = evt.getObjects();

				Iterator it = list.iterator();

				Object obj;

				while(it.hasNext())
				{
					obj = it.next();

					if(visualisedObjects.containsObject(obj))
					{
						hideObject(obj);
					}
				}
			}

			/**
			 * Called when a node is changed.
			 * @param evt The event, containing info about the
			 * nodeChanged action.
			 */
			public void objectChanged(GraphModelEvent evt)
			{
				Iterator it = evt.getObjects().iterator();

				Object obj;

				while(it.hasNext())
				{
					obj = it.next();

					if(visualisedObjects.containsObject(obj))
					{
						fireObjectChangedEvent(evt.getObjects());
					}
				}

			}

			public void parentAdded(GraphModelEvent evt)
			{
				// If the object and the parent object
				// are being visualised an edge needs
				// to be added to the graph.  If only one
				// of the objects is being visualised then
				// the display needs updating
				Object obj = evt.getObjects().get(0);

				Object parentObj = evt.getObjects().get(1);



				if(visualisedObjects.containsObject(obj))
				{
					// Update the info
					visualisedObjects.updateInfo();

					if(visualisedObjects.containsObject(parentObj))
					{
						fireParentObjectAddedEvent(obj, parentObj);
					}
					else
					{
						ArrayList list = new ArrayList(1);

						list.add(obj);

						fireObjectChangedEvent(list);
					}
				}
			}

			public void parentRemoved(GraphModelEvent evt)
			{
				Object obj = evt.getObjects().get(0);

				Object parentObj = evt.getObjects().get(1);



				if(visualisedObjects.containsObject(obj))
				{
					visualisedObjects.updateInfo();

					if(visualisedObjects.containsObject(parentObj))
					{
						fireParentObjectRemovedEvent(obj, parentObj);
					}
					else
					{
						ArrayList list = new ArrayList(1);

						list.add(obj);

						fireObjectChangedEvent(list);
					}
				}
			}

			public void childAdded(GraphModelEvent evt)
			{
				Object obj = evt.getObjects().get(0);

				Object childObj = evt.getObjects().get(1);


				if(visualisedObjects.containsObject(obj))
				{
					visualisedObjects.updateInfo();

					if(visualisedObjects.containsObject(childObj))
					{
						fireChildObjectAddedEvent(obj, childObj);
					}
					else
					{
						ArrayList list = new ArrayList(1);

						list.add(obj);

						fireObjectChangedEvent(list);
					}
				}
			}

			public void childRemoved(GraphModelEvent evt)
			{
				Object obj = evt.getObjects().get(0);

				Object childObj = evt.getObjects().get(1);


				if(visualisedObjects.containsObject(obj))
				{

					visualisedObjects.updateInfo();

					if(visualisedObjects.containsObject(childObj))
					{
						fireChildObjectRemovedEvent(obj, childObj);
					}
					else
					{
						ArrayList list = new ArrayList(1);

						list.add(obj);
					}
				}
			}

            /**
             * Fired when the model has changed drastically.
             *
             * @param evt The event object associated with this
             *            event.
             */
            public void modelChanged(GraphModelEvent evt)
            {
                // We need to remove any objects that are no
                // longer in the model, and then update the info

                ArrayList list = new ArrayList();

                visualisedObjects.removeDanglingObjects(list);

                // Now do an update
                visualisedObjects.updateInfo();

                // Inform our listeners to tell them that objects have been
                // removed.
                fireObjectRemovedEvent(list);
            }

		});
	}

	/**
	 * Gets the GraphModel that the visualised object manager
	 * is responsible for.
	 * @return The <code>GraphModel</code> that the visualised object
	 * manager holds on to.
	 */
	public GraphModel getGraphModel()
	{
		return model;
	}

	/**
	 * Sets the GraphModel that the visualised object manager is
	 * responsible for.  Note that calling this method will probably
	 * cause all visualised objects to be hidden.
	 * @param model The model
	 */
	public void setGraphModel(GraphModel model)
	{
		this.model = model;

		ArrayList list = new ArrayList();

		visualisedObjects.removeDanglingObjects(list);
		
		fireObjectRemovedEvent(list);
	}

	/**
	 * Specifies that the object should be visible
	 * in a visualisation of the graph model.
	 * @param obj  The object to be made visisible
	 */
	public void showObject(Object obj)
	{

		if(visualisedObjects.addObject(obj) == true)
		{
			ArrayList list = new ArrayList(1);

			list.add(obj);

			// Fire Event
			fireObjectAddedEvent(list);
		}
	}

	/**
	 * Specifies that the object should be visible in
	 * a visualisation of the graph model.
	 * @param obj The object to be made visible.
	 * @param parentChildRadius The number of levels of parents and children to show.
	 * Note that a level of 0 is equivalent to the <code>showObject(Object obj)</code>
	 * method.
	 */
	public void showObject(Object obj, int parentChildRadius, Class objClass)
	{
		ArrayList list = new ArrayList();

		if(visualisedObjects.addObject(obj) == true)
		{
			list.add(obj);
		}

		visualisedObjects.addChildren(obj, list, parentChildRadius, objClass);

		visualisedObjects.addParents(obj, list, parentChildRadius, objClass);

		if(list.size() > 0)
		{
			fireObjectAddedEvent(list);
		}
	}

    /**
     * Requests that the specified objects be added to
     * the visualisation.
     *
     * @param objs An array of objects to be added.
     */
    public void showObjects(Object[] objs)
    {
        ArrayList list = new ArrayList(objs.length);

        for(int i = 0; i < objs.length; i++)
        {
            if(visualisedObjects.addObject(objs[i]) == true)
            {
                list.add(objs[i]);
            }
        }

        if(list.size() > 0)
        {
            fireObjectAddedEvent(list);
        }
    }

	/**
	 * Makes the specified object hidden (if visible) is
	 * a visualisation of the graph model.
	 * @param obj
	 */
	public void hideObject(Object obj)
	{
		if(visualisedObjects.removeObject(obj))
		{
			ArrayList list = new ArrayList(1);

			list.add(obj);

			fireObjectRemovedEvent(list);
		}
	}


	/**
	 * Hides objects that are past the specified radius.
	 *
	 * @param obj    The object which the radius should be centred at.
	 * @param radius The radius.  Objects past this radius are hidden.
	 *               For example, if a radius of 1 is specified, then grandchildren
	 *               and grandparents would be hidden.
	 * @param objClass Specified the class of objects to remain visible
	 */
	public void hideObjects(Object obj, int radius, Class objClass)
	{
		ArrayList list = new ArrayList(visualisedObjects.getNumberOfObjects());

		ArrayList remList = new ArrayList(visualisedObjects.getNumberOfObjects());

		visualisedObjects.removeAll(remList);

		visualisedObjects.addObject(obj);

		list.add(obj);

		visualisedObjects.addChildren(obj, list, radius, objClass);

		visualisedObjects.addParents(obj, list, radius, objClass);

		remList.removeAll(list);

		fireObjectRemovedEvent(remList);
	}


    /**
     * Requests that the specified objects should be
     * hidden/removed from the objects being visualised.
     *
     * @param objs The objects to hide.
     */
    public void hideObjects(Object[] objs)
    {
        ArrayList list = new ArrayList(objs.length);

        for(int i = 0; i < objs.length; i++)
        {
            if(visualisedObjects.removeObject(objs[i]) == true)
            {
                list.add(objs[i]);
            }
        }

        if(list.size() > 0)
        {
            fireObjectRemovedEvent(list);
        }
    }

	/**
	 * Shows the children of the specified object.
	 * @param obj The object who's children are to be made visible
	 */
	public void showChildren(Object obj, Class objClass)
	{
		showChildren(obj, 1, objClass);
	}

	/**
	 * Shows the children of the specified object to the number
	 * of levels specified.  For example, a level of 1 will show
	 * the immediate children, a level of 2 will show the immediate
	 * children, and their children, and so on.
	 * @param obj  The object who's children are to be made visible
	 * @param levels The number of child levels to show (>= 1).
	 * @param objClass Specifies the class of object to be added. To
	 * add all objects this should be the class of <code>Object</code>
	 * (i.e. <code>Object.class</code>)
	 */
	public void showChildren(Object obj, int levels, Class objClass)
	{
			ArrayList list = new ArrayList();

			int size = 0;

		if(visualisedObjects.addObject(obj)) {
			list.add(obj);
		}
			visualisedObjects.addChildren(obj, list, levels, objClass);

			if(list.size() != size)
			{
				// Objects have been added
				fireObjectAddedEvent(list);
			}
	}

	/**
	 * Hides the children of the specified object.
	 * @param obj The object who's children are to be hidden
	 */
	public void hideChildren(Object obj)
	{
		ArrayList list = new ArrayList();

		visualisedObjects.removeChildren(obj, list);

		if(list.size() > 0)
		{
			fireObjectRemovedEvent(list);
		}
	}

	/**
	 * Shows the parents of the specified object.
	 * @param obj The object who's parents are to be
	 * made visible.
	 */
	public void showParents(Object obj, Class objClass)
	{
		showParents(obj, 1, objClass);
	}

	/**
	 * Shows the parents of the specified object to the number of levels
	 * specified.  For example, a level of 1 will show the immediate
	 * parents, a level of 2 will show the immediate parents and their
	 * parents and so on.
	 * @param obj The object who's parents are to be shown.
	 * @param levels The number of parent levels to show. (>= 1).
	 * @param objClass Specifies the class of object to be added. To
	 * add all objects this should be the class of <code>Object</code>
	 * (i.e. <code>Object.class</code>)
	 */
	public void showParents(Object obj, int levels, Class objClass)
	{
			ArrayList list = new ArrayList();

		if(visualisedObjects.addObject(obj) == true) {
			list.add(obj);
		}

			int size = 0;

			visualisedObjects.addParents(obj, list, levels, objClass);

			if(list.size() != size)
			{
				// Objects have been added
				fireObjectAddedEvent(list);
			}

	}

	/**
	 * Hides the parents of the speicfied object.
	 * @param obj The object who's parents are to
	 * be hidden.
	 */
	public void hideParents(Object obj)
	{
		ArrayList list = new ArrayList();

		visualisedObjects.removeParents(obj, list);

		if(list.size() > 0)
		{
			fireObjectRemovedEvent(list);
		}
	}

	/**
	 * Hides all currently visible objects.
	 */
	public void hideAll()
	{
		ArrayList list = new ArrayList();

		visualisedObjects.removeAll(list);

		if(list.size() > 0)
		{
			fireObjectRemovedEvent(list);
		}
	}

	/**
	 * Gets the number of objects contained in the list of objects
	 * to be visualised.
	 * @return The number of visualised objects.
	 */
	public int getNumberOfVisualisedObjects()
	{
		return visualisedObjects.getNumberOfObjects();
	}


	/**
	 * Gets all of the objects that are being visualised.
	 *
	 * @return An array of the visualised objects.
	 */
	public Object[] getVisualisedObjects()
	{
		return visualisedObjects.getAllObjects().toArray();
	}


	/**
	 * Determines whether the sepcified object is in the list
	 * of object being visualised.
	 * @param obj The object to test for.
	 * @return <code>true</code> if the object is in the list of objects to be
	 * visualised. <code>false</code> if the object is not in the list of objects
	 * to be visualised.
	 */
	public boolean isShown(Object obj)
	{
		return visualisedObjects.containsObject(obj);
	}

	/**
	 * Retrives an iterator, which can be used to traverse the
	 * list of visualised objects.
	 * @return The Iterator
	 */
	public Iterator iterator()
	{
		return visualisedObjects.iterator();
	}

	/**
	 * Determines how many children of the specified object are shown.
	 * @param obj The object
	 * @return The number of children shown, or -1 if the specified
	 * object is not being visualised.
	 */
	public int getChildrenShownCount(Object obj)
	{
		int val = -1;

		VisualisedObjectWrapper wrapper = visualisedObjects.getObjectInfo(obj);

		if(wrapper != null)
		{
			val = wrapper.getNumberOfChildrenShown();
		}

		return val;
	}

	/**
	 * Determines how many children of the specified object are hidden.
	 * @param obj The object
	 * @return The number of children hidden, or -1 if the specified
	 * object is not being visualised.
	 */
	public int getChildrenHiddenCount(Object obj)
	{
		int val = -1;

		VisualisedObjectWrapper wrapper = visualisedObjects.getObjectInfo(obj);

		if(wrapper != null)
		{
			val = wrapper.getNumberOfChildrenHidden();
		}

		return val;
	}

	/**
	 * Determines how many parents of the specified object are shown.
	 * @param obj The object
	 * @return The number of parents shown, or -1 if the specified object
	 * is not being visualised.
	 */
	public int getParentsShownCount(Object obj)
	{
		int val = -1;

		VisualisedObjectWrapper wrapper = visualisedObjects.getObjectInfo(obj);

		if(wrapper != null)
		{
			val = wrapper.getNumberOfParentsShown();
		}

		return val;
	}

	/**
	 * Determines how many parents of the specified object are hidden.
	 * @param obj The object
	 * @return The number of parents hidden, or -1 if the specified
	 * object is not being visualised.
	 */
	public int getParentsHiddenCount(Object obj)
	{
		int val = -1;

		VisualisedObjectWrapper wrapper = visualisedObjects.getObjectInfo(obj);

		if(wrapper != null)
		{
			val = wrapper.getNumberOfParentsHidden();
		}

		return val;
	}

    /**
     * Forces an update of the information pertaining
     * to whether an object's children and parents
     * are visible.
     */
    public void updateInfo()
    {
        visualisedObjects.updateInfo();
    }

	/**
	 * Adds a <code>VisualisedObjectManagerListener</code>, which is notified of
	 * events relating to visualised object addition/removal and change.
	 * @param lsnr The listener to be added.
	 */
	public void addVisualisedObjectManagerListener(VisualisedObjectManagerListener lsnr)
	{
		listeners.add(lsnr);
	}

	/**
	 * Removes a <code>VisualisedObjectManagerListener</code>, which was previously
	 * added.
	 * @param lsnr The listener to be removed.
	 */
	public void removeVisualisedObjectManagerListener(VisualisedObjectManagerListener lsnr)
	{
		listeners.remove(lsnr);
	}

	/**
	 * Gets an iterator that can be used to traverse the list of listeners.
	 * @return An <code>Iterator</code>, which can be used to add and remove
	 * listeners, as well as traverse the list of listeners.
	 */
	public Iterator<VisualisedObjectManagerListener> getVisualisedObjectManagerListeners()
	{
		return listeners.iterator();
	}

	/**
	 * Propagates an object added event to all listeners.
	 * @param list An <code>ArrayList</code> that contains the objects
	 * that have been added.
	 */
	protected void fireObjectAddedEvent(ArrayList list)
	{
		VisualisedObjectManagerEvent evt = new VisualisedObjectManagerEvent(this, list);

		if(EVENTDEBUG)
		{
			System.out.println("TRACE(DefaultVisualisedObjectManager) firing object added event");

			System.out.println(evt.getObjects());
		}

		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).objectsAdded(evt);

		}
	}

	/**
	 * Propagates an object removed event to all listeners.
	 * @param list An <code>ArrayList</code> that contains the objects
	 * that have been removed.
	 */
	protected void fireObjectRemovedEvent(ArrayList list)
	{
		VisualisedObjectManagerEvent evt = new VisualisedObjectManagerEvent(this, list);

		if(EVENTDEBUG)
		{
			System.out.println("TRACE(DefaultVisualisedObjectManager) firing object removed event.");

			System.out.println(evt.getObjects());
		}

		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).objectsRemoved(evt);

		}
	}

	/**
	 * Propagates an object changed event to all listeners.
	 * @param list An <code>ArrayList</code> that contains
	 * the objects that have been changed.
	 */
	protected void fireObjectChangedEvent(ArrayList list)
	{
		VisualisedObjectManagerEvent evt = new VisualisedObjectManagerEvent(this, list);

		if(EVENTDEBUG)
		{
			System.out.println("TRACE(DefaultVisualisedObjectManager) firing object changed event");

			System.out.println(evt.getObjects());
		}

		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).objectsChanged(evt);

		}
	}

	/**
	 * Propagates a parent object added event to listeners
	 * @param object The object that has had a parent object added to it
	 * @param parentObject The parent object that was added.
	 */
	protected void fireParentObjectAddedEvent(Object object, Object parentObject)
	{
		ArrayList list = new ArrayList(2);

		list.add(object);

		list.add(parentObject);

		VisualisedObjectManagerEvent evt = new VisualisedObjectManagerEvent(this, list);

		if(EVENTDEBUG)
		{
			System.out.println("TRACE(DefaultVisualisedObjectManager) firing parent object added event");

			System.out.println(evt.getObjects());
		}

		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).parentObjectAdded(evt);
		}
	}


	/**
	 * Propagates a parent object removed event to listeners
	 * @param object The object whose parent object has been removed
	 * @param parentObject The parent object that was removed.
	 */
	protected void fireParentObjectRemovedEvent(Object object, Object parentObject)
	{
		ArrayList list = new ArrayList(2);

		list.add(object);

		list.add(parentObject);

		VisualisedObjectManagerEvent evt = new VisualisedObjectManagerEvent(this, list);

		if(EVENTDEBUG)
		{
			System.out.println("TRACE(DefaultVisualisedObjectManager) firing parent object removed event");

			System.out.println(evt.getObjects());
		}

		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).parentObjectRemoved(evt);
		}
	}

	/**
	 * Propagates a child object added event to listeners
	 * @param object The object whose child object has been added
	 * @param childObject The child object that was added
	 */
	protected void fireChildObjectAddedEvent(Object object, Object childObject)
	{
		ArrayList list = new ArrayList(2);

		list.add(object);

		list.add(childObject);

		VisualisedObjectManagerEvent evt = new VisualisedObjectManagerEvent(this, list);

		if(EVENTDEBUG)
		{
			System.out.println("TRACE(DefaultVisualisedObjectManager) firing child object added event");

			System.out.println(evt.getObjects());
		}

		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).childObjectAdded(evt);
		}
	}

	/**
	 * Propagates a child object removed event to listeners
	 * @param object The object whose child object has been removed
	 * @param childObject The child object that was removed
	 */
	protected void fireChildObjectRemovedEvent(Object object, Object childObject)
	{
		ArrayList list = new ArrayList(2);

		list.add(object);

		list.add(childObject);

		VisualisedObjectManagerEvent evt = new VisualisedObjectManagerEvent(this, list);

		if(EVENTDEBUG)
		{
			System.out.println("TRACE(DefaultVisualisedObjectManager) firing child object removed event");

			System.out.println(evt.getObjects());
		}

		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).childObjectRemoved(evt);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Inner class to contain info about the objects being visualised.

	private class VisualisedObjectWrapper
	{
		private Object obj;
		private int numberOfParentsShown;
		private int numberOfChildrenShown;
		private int numberOfChildrenHidden;
		private int numberOfParentsHidden;

		/**
		 * Constructs a <code>VisualisedObjectWrapper</code> for the specified
		 * object.
		 * @param obj
		 */
		public VisualisedObjectWrapper(Object obj)
		{
			this.obj = obj;
		}

		/**
		 * Gets the number of the object's parent objects that are visible.
		 * Note that this is merely a caching mechanism, and the value returned
		 * is the value that was previously set with the <code>setNumberOfParentsShown</code>
		 * method.
		 * @return The number of parents visible.
		 */
		public int getNumberOfParentsShown()
		{
			return numberOfParentsShown;
		}

		/**
		 * Sets the number of the object's parent objects that are shown.
		 * @param numberOfParentsShown The number of parents that are shown.
		 */
		public void setNumberOfParentsShown(int numberOfParentsShown)
		{
			this.numberOfParentsShown = numberOfParentsShown;

			numberOfParentsHidden = model.getParentCount(obj) - numberOfParentsShown;
		}

		/**
		 * Gets the number of the object's children objects that are visible.
		 * Note that this is merely a caching mechanism, and the value returned
		 * is the value that was previously set with the <code>setNumberOfChildrenShown</code>
		 * method.
		 * @return The number of children visible.
		 */
		public int getNumberOfChildrenShown()
		{
			return numberOfChildrenShown;
		}

		/**
		 * Sets the number of the object's children objects that are shown.
		 * @param numberOfChildrenShown The number of parents that are shown.
		 */
		public void setNumberOfChildrenShown(int numberOfChildrenShown)
		{
			this.numberOfChildrenShown = numberOfChildrenShown;

			numberOfChildrenHidden = model.getChildCount(obj) - numberOfChildrenShown;
		}

		/**
		 * Returns the number of children that are hidden.
		 * Note that this method returns the difference of the number of children
		 * and the number of children shown, which should have been set using
		 * the <code>setNumberOfChildrenShown</code> method.
		 * @return The number of children of the object contained in this wrapper
		 * that are hidden.
		 */
		public int getNumberOfChildrenHidden()
		{
			return numberOfChildrenHidden;
		}

		/**
		 * Returns the number of parents that are hidden.
		 * Note that this method returns the difference of the number of parents
		 * and the number of parents shown, which should have been set using
		 * the <code>setNumberOfParentsShown</code> method.
		 * @return The number of parents of the object contained in this wrapper
		 * that are hidden.
		 */
		public int getNumberOfParentsHidden()
		{
			return numberOfParentsHidden;
		}
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Inner class - The cache that holds the visualised objects and various
	 * bits of information about them.  The <code>VisualisedOjectCache</code>
	 * essentially maintains a 'list' of objects being visualised - objects, and
	 * their parents and children may be added or removed from this list.
	 */
	private class VisualisedObjectCache
	{
		Map objects;

		public VisualisedObjectCache()
		{
			objects = new HashMap();
		}

		/**
		 * Adds an object to the list of objects being visualised.
		 * @param obj The object to be added.
		 * @return <code>true</code> if the object was added to the
		 * list of objects being visualised, or <code>false</code> if
		 * the object was not added (because it is already being visualised).
		 */
		public boolean addObject(Object obj)
		{
			if(objects.containsKey(obj) == false)
			{
				objects.put(obj, new VisualisedObjectWrapper(obj));

				// Add related objects

				addRelatedObjects(obj, new ArrayList());

				updateInfo();

				return true;
			}

			return false;
		}

		/**
		 * Gets the wrapper for the specified object.
		 * @param obj The object.
		 * @return A <code>VisualisedObjectWrapper</code> that is used
		 * as a cache for information such as the number of children shown etc.
		 */
		public VisualisedObjectWrapper getObjectInfo(Object obj)
		{
			return (VisualisedObjectWrapper)objects.get(obj);
		}

		/**
		 * Tests to see whether the specified object is being
		 * visualised.
		 * @param obj The object to test for.
		 * @return <code>true</code> if the object is being visualised,
		 * or <code>false</code> is the object is not being visualised.
		 */
		public boolean containsObject(Object obj)
		{
			return objects.containsKey(obj);
		}

		/**
		 * Gets the number of objects being visualised.
		 * @return The number of objects being visualised.
		 */
		public int getNumberOfObjects()
		{
			return objects.keySet().size();
		}

		/**
		 * Gets an iterator which can be used to traverse the list
		 * of object that are being visualised.
		 * @return An <code>Iterator</code>.
		 */
		public Iterator iterator()
		{
			return objects.keySet().iterator();
		}

		/**
		 * Removes the specified object from the list of objects being
		 * visualised.
		 * @param obj The object to be removed
		 * @return <code>true</code> if the object was removed, or <code>false</code>
		 * if the object was not removed (if it was not being visualised).
		 */
		public boolean removeObject(Object obj)
		{
			if(containsObject(obj) == true)
			{
				objects.remove(obj);

				removeRelatedObjects(obj, new ArrayList());

				updateInfo();

				return true;
			}

			return false;
		}

		/**
		 * Adds the children of the specified object to
		 * the list of objects being visualised.
		 * @param obj  The object whose children are to be shown (added to the list).
		 * @param list An <code>ArrayList</code> that is populated with the objects
		 * that have been added.
		 * @param levels The number of levels of children to add.  This method is a
		 * recursive method - if the number of levels is 1, then only the
		 * direct children of the specified object are added.  If the number of
		 * levels is 2, then the direct children of the specified object are added
		 * and their children are added - and so on...
		 * @param objClass The class of the objects being added, this allows
		 * objects to be filtered.
		 */
		public void addChildren(Object obj, ArrayList list, int levels, Class objClass)
		{
			int size = list.size();

			addChildrenRecursively(obj, list, levels, objClass);

			if(list.size() != size)
			{
				updateInfo();
			}
		}

		/**
		 * Adds the parents of the specified object to
		 * the list of objects being visualised.
		 * @param obj  The object whose parents are to be shown (added to the list).
		 * @param list An <code>ArrayList</code> that is populated with the objects
		 * that have been added.
		 * @param levels The number of levels of parents to add.  This method is a
		 * recursive method - if the number of levels is 1, then only the
		 * direct parents of the specified object are added.  If the number of
		 * levels is 2, then the direct parents of the specified object are added
		 * and their parents are added - and so on...
		 */
		public void addParents(Object obj, ArrayList list, int levels, Class objClass)
		{

			int size = list.size();

			addParentsRecursively(obj, list, levels, objClass);


			if(list.size() != size)
			{
				updateInfo();
			}
		}

		/**
		 * Recursive method for adding children of the specified object
		 * @param obj The object whose children are to be added
		 * @param list An array list to store pointers to the obejcts
		 * that have been added
		 * @param levels The number of levels of children to add.
		 */
		private void addChildrenRecursively(Object obj, ArrayList list, int levels, Class objClass)
		{
			if(levels > 0)
			{
				list.ensureCapacity(list.size() + model.getChildCount(obj));

				Iterator childIt = model.getChildren(obj);

				while(childIt.hasNext())
				{
					final Object childObject = childIt.next();

					if(objects.containsKey(childObject) == false && objClass.isInstance(childObject))
					{
						objects.put(childObject, new VisualisedObjectWrapper(childObject));

						list.add(childObject);

						addRelatedObjects(childObject, list); // Added 02/06/04

						addChildrenRecursively(childObject, list, levels - 1, objClass);
					}
				}


			}
		}

		/**
		 * Removes the children of the speicified object from the list
		 * of objects being visualised.  Note that this method results in
		 * the recursive removal of children, so that children's children
		 * are removed etc.
		 * @param obj The object whose children are to be removed.
		 * @param list An  <code>ArrayList</code> to be populated with
		 * the objects that were removed.
		 */
		public void removeChildren(Object obj, ArrayList list)
		{
			int size = list.size();

			removeChildrenRecursively(obj, list);

			if(list.size() != size)
			{
				updateInfo();
			}
		}

		/**
		 * Removes the children of the specified object from the list
		 * of visualised objects.  Note that this method is a recursive
		 * method, so that children of children are removed etc.
		 * @param obj The object whose children are to be removed.
		 * @param list An <code>ArrayList</code> to be populated with the
		 * objects that were removed.
		 */
		private void removeChildrenRecursively(Object obj, ArrayList list)
		{
			list.ensureCapacity(list.size() + model.getChildCount(obj));

			Iterator childIt = model.getChildren(obj);

			Object childObject;


			while (childIt.hasNext())
			{
				childObject = childIt.next();

				if (objects.containsKey(childObject) == true)
				{
					objects.remove(childObject);

					list.add(childObject);

					removeRelatedObjects(childObject, list);

					removeChildrenRecursively(childObject, list);
				}
			}
		}

		/**
		 * Recursive method for adding parents of the specified object
		 * @param obj The object whose parents are to be added
		 * @param list An array list to store pointers to the obejcts
		 * that have been added
		 * @param levels The number of levels of parents to add.
		 */
		private void addParentsRecursively(Object obj, ArrayList list, int levels, Class objClass)
		{
			if(levels > 0)
			{
				list.ensureCapacity(list.size() + model.getParentCount(obj));

				Iterator parIt = model.getParents(obj);

				while(parIt.hasNext())
				{
					final Object parObject = parIt.next();

					if(objects.containsKey(parObject) == false && objClass.isInstance(parObject))
					{
						objects.put(parObject, new VisualisedObjectWrapper(parObject));

						list.add(parObject);

						addRelatedObjects(parObject, list); // Added 02/06/04

						addParentsRecursively(parObject, list, levels - 1, objClass);
					}
				}


			}
		}

		/**
		 * Reomoves the parents of the specified object from the list
		 * of objects being visualised.  This method is NOT recursive i.e.
		 * it only removes the direct parents of the speicifed object.
		 * @param obj The object whose parents are to be removed.
		 * @param list An <code>ArrayList</code> to be populated with the
		 * objects that were removed.
		 */
		public void removeParents(Object obj, ArrayList list)
		{
			boolean update = false;


			list.ensureCapacity(list.size() + model.getParentCount(obj));

			Iterator parIt = model.getParents(obj);

			Object parObject;

			while(parIt.hasNext())
			{
				parObject = parIt.next();

				if(objects.containsKey(parObject))
				{
					objects.remove(parObject);

					list.add(parObject);

					removeRelatedObjects(parObject, list);

					update = true;
				}
			}

			if(update)
			{
				updateInfo();
			}
		}

		/**
		 * Removes all the objects from the list of objects
		 * being visualised.
		 * @param list An <code>ArrayList</code> to be populated with the objects
		 * that were removed.
		 */
		public void removeAll(ArrayList list)
		{
			list.ensureCapacity(objects.keySet().size());

			Iterator it = objects.keySet().iterator();

			while(it.hasNext())
			{
				list.add(it.next());
			}

			objects.keySet().removeAll(objects.keySet());

			updateInfo();
		}

		/**
		 * Removes the objects that are not contained in the model.
		 * @param list A list that is populated with the objects
		 * that have been removed from the visualisation objects list.
		 */
		public void removeDanglingObjects(ArrayList list)
		{
			list.ensureCapacity(objects.keySet().size());

			Iterator it = objects.keySet().iterator();

			Object obj;

			while(it.hasNext())
			{
				obj = it.next();

				if(model.contains(obj) == false)
				{
					list.add(obj);
				}
			}

			objects.keySet().removeAll(list);

			updateInfo();
		}


		/**
		 * Updates the info contained in the <code>VisualisedObjectWrapper</code>.
		 */
		private void updateInfo()
		{
			Iterator it = iterator();

			Iterator parIt, childIt;

			VisualisedObjectWrapper wrapper;

			Object obj;

			int counter;

			while(it.hasNext())
			{
				obj = it.next();

				wrapper = getObjectInfo(obj);

				counter = 0;

				parIt = model.getParents(obj);

				while(parIt.hasNext())
				{
					if(containsObject(parIt.next()))
					{
						counter++;
					}
				}

				wrapper.setNumberOfParentsShown(counter);

				counter = 0;

				childIt = model.getChildren(obj);

				while(childIt.hasNext())
				{
					if(containsObject(childIt.next()))
					{
						counter++;
					}
				}

				wrapper.setNumberOfChildrenShown(counter);

			} // End of while
		} // End of updateInfo

		public void addRelatedObjects(Object obj, ArrayList list)
		{
			Iterator it = model.getRelatedObjectsToAdd(obj);

			Object objRel;

			while(it.hasNext())
			{
				objRel = it.next();

				if(objects.containsKey(objRel) == false)
				{
					objects.put(objRel, new VisualisedObjectWrapper(objRel));

					list.add(objRel);

					// Add recursively
					addRelatedObjects(objRel, list);
				}
			}
		}

		public void removeRelatedObjects(Object obj, ArrayList list)
		{
			Iterator it = model.getRelatedObjectsToRemove(obj);

			Object objRel;

			while(it.hasNext())
			{
				objRel = it.next();

				if(objects.containsKey(objRel) == true)
				{
					objects.remove(objRel);

					list.add(objRel);

					// Add recursively
					removeRelatedObjects(objRel, list);
				}
			}
		}

		/**
		 * Gets all of the objects in the cache
		 */
		public Collection getAllObjects()
		{
			return objects.keySet();
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
	}
}
