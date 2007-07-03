package uk.ac.man.cs.mig.util.graph.layout;

import uk.ac.man.cs.mig.util.graph.graph.Graph;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Dec 27, 2003<br><br>
 *
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public interface GraphLayoutEngine
{
    public static final int LAYOUT_LEFT_TO_RIGHT = 0;
    public static final int LAYOUT_TOP_TO_BOTTOM = 1;

	/**
	 * Lays out the specified <code>Graph</code>
	 * @param g The <code>Graph</code>
	 */
	public void layoutGraph(Graph g);

    /**
     * Sets the direction of the layout.
     * @param layoutDirection The layout direction. This should be one of
     * the constants <code>GraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT</code> or
     * <code>GraphLayoutEngine.LAYOUT_TOP_TO_BOTTOM</code>.
     */
    public void setLayoutDirection(int layoutDirection);

    /**
     * Gets the layout direction.
     * @return The direction of the layout. <code>DotGraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT</code> or
     * <code>DotGraphLayoutEngine.LAYOUT_TOP_TO_BOTTOM</code>.
     */
    public int getLayoutDirection();
}
