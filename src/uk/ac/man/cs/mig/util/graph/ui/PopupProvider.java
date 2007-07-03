package uk.ac.man.cs.mig.util.graph.ui;

import javax.swing.*;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 12, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public interface PopupProvider
{
	/**
	 * Gets the content used to display a tooltip style popup
	 * when the mouse hovers over a <code>Node</code> in the <code>GraphView</code>.
	 * @param obj The <code>Object</code> for which the popup will be displayed.
	 * Note that this object may be <code>null</code>, in which case, a component
	 * that represents a null tooltip should be returned.
	 * @return The component that represents the popup content, must not be <code>null</code> 
	 */
	public JComponent getPopup(Object obj);
}
