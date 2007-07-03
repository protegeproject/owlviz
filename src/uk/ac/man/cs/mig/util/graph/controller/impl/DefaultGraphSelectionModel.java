package uk.ac.man.cs.mig.util.graph.controller.impl;

import uk.ac.man.cs.mig.util.graph.controller.GraphSelectionModel;
import uk.ac.man.cs.mig.util.graph.event.GraphSelectionModelEvent;
import uk.ac.man.cs.mig.util.graph.event.GraphSelectionModelListener;

import java.util.ArrayList;
import java.util.Iterator;

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
public class DefaultGraphSelectionModel implements GraphSelectionModel
{

	private Object selObj;

	private ArrayList listeners;
	private GraphSelectionModelEvent evt;

	private static final boolean EVENTDEBUG = false;

	public DefaultGraphSelectionModel()
	{
		listeners = new ArrayList();

		evt = new GraphSelectionModelEvent(this);

		selObj = null;

	}

	/**
	 * Causes the specified <code>Object</code> to be selected.
	 * @param obj The <code>Object</code> to be selected.
	 */
	public void setSelectedObject(Object obj)
	{
		if(obj != selObj)
		{
			selObj = obj;

			fireNodeSelectionChangedEvent();
		}


	}

	/**
	 * Determines whether the model support multiple <code>Node</code>
	 * selection.
	 * @return Returns <code>true</code> if multiple <code>Node</code>
	 * selection is supported.  Returns <code>false</code> if the model
	 * does not support multiple <code>Node</code> selection.
	 */
	public boolean supportsMultipleObjectSelection()
	{
		return false;
	}

	/**
	 * Gets the <i>first</i> selected <code>Object</code>.  In
	 * a single selection model, this will obviously be the only
	 * selected <code>Object</code>.
	 * @return The selected <code>Object</code>.  If no <code>Object</code>s are
	 * selected, then the return value is <code>null</code>.
	 */
	public Object getSelectedObject()
	{
		return selObj;
	}

	/**
	 * Retrieves an array of the selected <code>Object</code>s.  In
	 * a single selection model, this will be an array containing
	 * a maximum of one <code>Object</code>.
	 * @return An array of the selected <code>Object</code>s.  If no <code>Object</code>s
	 * are selected, then the array will be empty.
	 */
	public Object[] getSelectedObjects()
	{
		if(selObj == null)
		{
			return new Object[0];
		}

		return new Object[]{selObj};
	}

	/**
	 * Adds a listener that is informed of changes in the selection.
	 * @param lsnr The <code>GraphSelectionModelListener</code> to be added.
	 */
	public void addGraphSelectionModelListener(GraphSelectionModelListener lsnr)
	{
		listeners.add(lsnr);
	}

	/**
	 * Removes a previously added <code>GraphSelectionModelListener</code>.
	 * @param lsnr The listener to be removed.
	 */
	public void removeGraphSelectionModelListener(GraphSelectionModelListener lsnr)
	{
		listeners.remove(lsnr);
	}

	/**
	 * Retrieves an <code>Iterator</code> that can
	 * be used to traverse the listeners.
	 * @return An <code>Iterator</code>.
	 */
	public Iterator getListeners()
	{
		return listeners.iterator();
	}


	/**
	 * Propagates selection events to listeners.
	 */
	protected void fireNodeSelectionChangedEvent()
	{
		if(EVENTDEBUG)
		{
			System.out.println("TRACE(DefaultGraphSelectionModel) firing node selection changed event");
		}

		for(int i = 0; i < listeners.size(); i++)
		{
			((GraphSelectionModelListener)listeners.get(i)).selectionChanged(evt);
		}
	}


	/**
	 * Returns a string representation of the object. In general, the
	 * <code>toString</code> method returns a string that
	 * "textually represents" this object. The result should
	 * be a concise but informative representation that is easy for a
	 * person to read.
	 * It is recommended that all subclasses override this method.
	 * <p>
	 * The <code>toString</code> method for class <code>Object</code>
	 * returns a string consisting of the name of the class of which the
	 * object is an instance, the at-sign character `<code>@</code>', and
	 * the unsigned hexadecimal representation of the hash code of the
	 * object. In other words, this method returns a string equal to the
	 * value of:
	 * <blockquote>
	 * <pre>
	 * getClass().getName() + '@' + Integer.toHexString(hashCode())
	 * </pre></blockquote>
	 *
	 * @return  a string representation of the object.
	 */
	public String toString()
	{
		String ret;

		ret = "DefaultGraphSelectionListener:\n";

		Iterator it = getListeners();

		while(it.hasNext())
		{
			ret += it.next() + "\n";
		}

		return ret;
	}
}
