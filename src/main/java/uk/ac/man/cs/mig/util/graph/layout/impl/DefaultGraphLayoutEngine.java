package uk.ac.man.cs.mig.util.graph.layout.impl;

import uk.ac.man.cs.mig.util.graph.graph.Graph;
import uk.ac.man.cs.mig.util.graph.layout.GraphLayoutEngine;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 15, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public class DefaultGraphLayoutEngine implements GraphLayoutEngine
{
	/**
	 * Lays out the specified <code>Graph</code>
	 * @param g The <code>Graph</code>
	 */
	public void layoutGraph(Graph g)
	{
		// Test


	}

    /**
     * Sets the direction of the layout.
     *
     * @param layoutDirection The layout direction. This should be one of
     *                        the constants <code>GraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT</code> or
     *                        <code>GraphLayoutEngine.LAYOUT_TOP_TO_BOTTOM</code>.
     */
    public void setLayoutDirection(int layoutDirection)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets the layout direction.
     *
     * @return The direction of the layout. <code>DotGraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT</code> or
     *         <code>DotGraphLayoutEngine.LAYOUT_TOP_TO_BOTTOM</code>.
     */
    public int getLayoutDirection()
    {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
