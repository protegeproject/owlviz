package uk.ac.man.cs.mig.util.graph.renderer;

import uk.ac.man.cs.mig.util.graph.graph.Edge;

import java.awt.*;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Dec 29, 2003<br><br>
 *
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * The interface for a renderer that paints edges
 * in the graph.
 * @see uk.ac.man.cs.mig.util.graph.renderer.NodeRenderer
 */
public interface EdgeRenderer
{
	/**
	 * Called to render an <code>Edge</code>.  Typically, the <code>Shape</code>
	 * will be a <code>GeneralPath</code>.
	 * @param edge The <code>Edge</code> to be rendered.
	 * @param g2 The Graphics2D object on to which the <code>Edge</code> should be rendered.
     * @param forPrinting A flag to indicate if the graphics are being drawn to produce an
     * image for printing, or to draw onto the screen.
	 * @param drawDetail A flag to indicate whether or not to draw detail such as
	 * text - this flag will be set to <code>true</code> when views such as thumbnail
	 * views are drawn and detail such as text is irrelevant.
	 */
	public void renderEdge(Graphics2D g2, Edge edge, boolean forPrinting, boolean drawDetail);
}
