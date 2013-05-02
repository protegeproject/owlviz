package uk.ac.man.cs.mig.util.graph.renderer;

import uk.ac.man.cs.mig.util.graph.graph.Edge;

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
public interface EdgeLabelRenderer
{
	/**
	 * Retrives the label for the specified <code>Edge</code>.
	 * @param edge The <code>Egde</code> whose label is to be determined.
	 * @return The <code>Edge's</code> label, or <code>null</code> if the
	 * <code>Edge</code> does not have a label.
	 */
	public String getEdgeLabel(Edge edge);
}
