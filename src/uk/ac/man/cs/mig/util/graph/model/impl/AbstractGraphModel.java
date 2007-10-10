package uk.ac.man.cs.mig.util.graph.model.impl;

import uk.ac.man.cs.mig.util.graph.event.GraphModelEvent;
import uk.ac.man.cs.mig.util.graph.event.GraphModelListener;
import uk.ac.man.cs.mig.util.graph.model.GraphModel;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 6, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * The <code>AbstractGraphModel</code> provides implementations of methods to add/remove
 * listeners and fire <code>GraphModelEvent</code>s.
 * @see GraphModelEvent
 * @see GraphModelListener
 */
public abstract class AbstractGraphModel implements GraphModel
{
	protected ArrayList<GraphModelListener> listeners;

	private static final boolean EVENTDEBUG = false;

	public AbstractGraphModel()
	{
		listeners = new ArrayList<GraphModelListener>();
	}

	/**
	 * Adds a listener to the model that is notified when events such a <code>Node</code>
	 * addition and removal take place.
	 * @param lsnr The listener to be added.
	 */
	public void addGraphModelListener(GraphModelListener lsnr)
	{
		listeners.add(lsnr);
	}

	/**
	 * Removes a previously added <code>GraphModelListener</code>.
	 * @param lsnr The listener to be removed.
	 */
	public void removeGraphModelListener(GraphModelListener lsnr)
	{
		listeners.remove(lsnr);
	}

	public void fireObjectsAddedEvent(ArrayList objects)
	{
		if(EVENTDEBUG)
		{
			System.out.println("TRACE(DefaultGraphModel) firing object added event " + objects);
		}

		GraphModelEvent evt = new GraphModelEvent(this, objects);

		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).objectAdded(evt);
		}
	}

	/**
	 * Propagates an object removed event to registered listeners.
	 * @param objects An <code>ArrayList</code> containing the objects
	 * that were removed
	 */
	protected void fireObjectsRemovedEvent(ArrayList objects)
	{
		if(EVENTDEBUG)
		{
			System.out.println("TRACE(DefaultGraphModel) firing object removed event " + objects);
		}

		GraphModelEvent evt = new GraphModelEvent(this, objects);

		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).objectRemoved(evt);
		}
	}

	/**
	 * Propagates an object changed event to registered listeners.
	 * @param objects An <code>ArrayList</code> containing the
	 * objects that were changed.
	 */
	protected void fireObjectsChangedEvent(ArrayList objects)
	{
		if(EVENTDEBUG)
		{
			System.out.println("TRACE(DefaultGraphModel) firing object changed event " + objects);
		}

		GraphModelEvent evt = new GraphModelEvent(this, objects);

		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).objectChanged(evt);
		}
	}

	/**
	 * Propagates a parent object added event to registered listeners.
	 * @param object The object which has had a parent added to it.
	 * @param parentObject The parent object that was added.
	 */
	protected void fireParentAddedEvent(Object object, Object parentObject)
	{
		ArrayList objects = new ArrayList();

		objects.add(object);

		objects.add(parentObject);

		if(EVENTDEBUG)
		{
			System.out.println("TRACE(DefaultGraphModel) firing parent added event " + objects);
		}

		GraphModelEvent evt = new GraphModelEvent(this, objects);

		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).parentAdded(evt);
		}
	}

	/**
	 * Propagates a parent object removed event to registered listeners.
	 * @param object The object which has had a parent removed from it.
	 * @param parentObject The parent object that was removed.
	 */
	protected void fireParentRemovedEvent(Object object, Object parentObject)
	{
		ArrayList objects = new ArrayList();

		objects.add(object);

		objects.add(parentObject);

		if(EVENTDEBUG)
		{
			System.out.println("TRACE(DefaultGraphModel) firing parent removed event " + objects);
		}

		GraphModelEvent evt = new GraphModelEvent(this, objects);

		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).parentRemoved(evt);
		}
	}

	/**
	 * Propagates a child object added event to registered listeners.
	 * @param object The object which has had a child added to it.
	 * @param childObject The child object that was added.
	 */
	protected void fireChildAddedEvent(Object object, Object childObject)
	{
		ArrayList objects = new ArrayList();

		objects.add(object);

		objects.add(childObject);

		GraphModelEvent evt = new GraphModelEvent(this, objects);

		if(EVENTDEBUG)
		{
			System.out.println("TRACE(DefaultGraphModel) firing child added event " + objects);
		}

		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).childAdded(evt);
		}
	}

	/**
	 * Propagates a child object removed event to registered listeners.
	 * @param object The object which has had a child removed it.
	 * @param childObject The child object that was removed.
	 */
	protected void fireChildRemovedEvent(Object object, Object childObject)
	{
		ArrayList objects = new ArrayList();

		objects.add(object);

		objects.add(childObject);

		if(EVENTDEBUG)
		{
			System.out.println("TRACE(DefaultGraphModel) firing child removed event " + objects);
		}

		GraphModelEvent evt = new GraphModelEvent(this, objects);

		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).childRemoved(evt);
		}
	}

    /**
     * Propagates a model changed event to all registered listeners.
     * A model changed event usually signifies that the model has
     * changed in a drastic way, or multiple changes have occurred
     * at once and have been coasleced into a single change.
     */
    protected void fireModelChangedEvent()
    {
        ArrayList objects = new ArrayList();

        if(EVENTDEBUG)
        {
	        System.out.println("TRACE(DefaultGraphModel) firing child removed event " + objects);
        }
            GraphModelEvent evt = new GraphModelEvent(this, objects);

            for(int i = 0; i < listeners.size(); i++)
            {
                listeners.get(i).modelChanged(evt);
            }


    }

	/**
	 * This method can be called to inform any listeners that
	 * an object has changed.
	 * @param obj The object that has changed.
	 */
	public void objectChanged(Object obj)
	{
		ArrayList objects = new ArrayList(1);

		objects.add(obj);

		fireObjectsChangedEvent(objects);
	}

	/**
	 * Gets an <code>Iterator</code> that can be used to
	 * traverse and remove listeners.
	 */
	public Iterator<GraphModelListener> getListeners()
	{
		return listeners.iterator();
	}

}
