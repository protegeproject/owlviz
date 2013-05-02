package uk.ac.man.cs.mig.util.graph.renderer;

import uk.ac.man.cs.mig.util.graph.graph.Node;

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
 * Renders the graphical content of a node
 * in the graph.
 * @see uk.ac.man.cs.mig.util.graph.renderer.EdgeRenderer
 */
public interface NodeRenderer
{
	/**
	 * Generic shape renderer.  Typically, the shape of the <code>Node</code>
	 * will be a <code>Rectangle</code> or an <code>Ellipse</code>.
	 * @param node The <code>Node</code> being rendered.
	 * @param g2 The Graphics2D object on to which the <code>Node</code> should be rendered.
     * @param forPrinting A flag to indicate if the graphics are being drawn to produce an
     * image for printing, or to draw onto the screen.
	 * @param drawDetail A flag to indicate whether or not to draw detail such as
	 * text - this flag will be set to <code>true</code> when views such as thumbnail
	 * views are drawn and detail such as text is irrelevant.
	 */
	public void renderNode(Graphics2D g2, Node node, boolean forPrinting, boolean drawDetail);

	/**
	 * Gets the preferred size of the specified <code>Node</code>.  This <i>may</i>
	 * be used by the layout engine to size <code>Nodes</code>.
	 * @param node The <code>Node</code> whose size is to be determined.
	 * @param size A <code>Dimension</code> may be passed to this method to
	 * prevent a new <code>Dimension</code> object being created, which will
	 * be populated with the preferred size.  May be <code>null</code> in which
	 * case a new <code>Dimension</code> object will be created and returned.
	 * @return The preferred size of the <code>Node</code>
	 */
	public Dimension getPreferredSize(Node node, Dimension size);
}
