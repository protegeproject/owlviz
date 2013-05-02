package uk.ac.man.cs.mig.util.graph.controller;

import uk.ac.man.cs.mig.util.graph.event.GraphSelectionModelListener;
import uk.ac.man.cs.mig.util.graph.graph.Node;

import java.util.Iterator;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Dec 29, 2003<br><br>
 *
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * Defines an interface for a selection model for
 * a <code>Graph</code>.  The interface does not make
 * the assumption that the model is a single selection
 * model.
 *
 * @see uk.ac.man.cs.mig.util.graph.graph.Graph
 * @see Node
 * @see uk.ac.man.cs.mig.util.graph.graph.Edge
 *
 */
public interface GraphSelectionModel
{
	/**
	 * Causes the specified <code>Object</code> to be selected.
	 * @param obj The <code>Object</code> to be selected.
	 */
	public void setSelectedObject(Object obj);

	/**
	 * Determines whether the model support multiple <code>Node</code>
	 * selection.
	 * @return Returns <code>true</code> if multiple <code>Node</code>
	 * selection is supported.  Returns <code>false</code> if the model
	 * does not support multiple <code>Node</code> selection.
	 */
	public boolean supportsMultipleObjectSelection();

	/**
	 * Gets the <i>first</i> selected <code>Object</code>.  In
	 * a single selection model, this will obviously be the only
	 * selected <code>Object</code>.
	 * @return The selected <code>Object</code>.  If no <code>Object</code>s are
	 * selected, then the return value is <code>null</code>.
	 */
	public Object getSelectedObject();

	/**
	 * Retrieves an array of the selected <code>Object</code>s.  In
	 * a single selection model, this will be an array containing
	 * a maximum of one <code>Object</code>.
	 * @return An array of the selected <code>Object</code>s.  If no <code>Object</code>s
	 * are selected, then the array will be empty.
	 */
	public Object [] getSelectedObjects();

	/**
	 * Adds a listener that is informed of changes in the selection.
	 * @param lsnr The <code>GraphSelectionModelListener</code> to be added.
	 */
	public void addGraphSelectionModelListener(GraphSelectionModelListener lsnr);

	/**
	 * Removes a previously added <code>GraphSelectionModelListener</code>.
	 * @param lsnr The listener to be removed.
	 */
	public void removeGraphSelectionModelListener(GraphSelectionModelListener lsnr);

	/**
	 * Retrieves an <code>Iterator</code> that can
	 * be used to traverse the listeners.
	 * @return An <code>Iterator</code> that can be used to add or remove
	 * listeners, as well as traverse the list of listeners.
	 */
	public Iterator getListeners();
}
