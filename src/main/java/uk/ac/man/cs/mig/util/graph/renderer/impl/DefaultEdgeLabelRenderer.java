package uk.ac.man.cs.mig.util.graph.renderer.impl;

import uk.ac.man.cs.mig.util.graph.graph.Edge;
import uk.ac.man.cs.mig.util.graph.renderer.EdgeLabelRenderer;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 17, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public class DefaultEdgeLabelRenderer implements EdgeLabelRenderer
{
	/**
	 * Gets the label for an <code>Edge</code> using the <code>toString</code>
	 * method of the <code>Edge</code>'s userObject.
	 * @param edge The <code>Edge</code> whose label is to be determined.
	 * @return The label of the specified <code>Edge</code>, or <code>null</code>
	 * if the specified <code>Edge</code> does not have a label.
	 */
	public String getEdgeLabel(Edge edge)
	{
		Object obj = edge.getUserObject();

		if(obj != null)
		{
			return obj.toString();
		}

		return null;
	}
}
