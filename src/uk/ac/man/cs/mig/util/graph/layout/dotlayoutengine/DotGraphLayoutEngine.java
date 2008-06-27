package uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine;

import uk.ac.man.cs.mig.util.graph.graph.Graph;
import uk.ac.man.cs.mig.util.graph.layout.GraphLayoutEngine;
import uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine.dotparser.DotParameterSetter;
import uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine.dotparser.DotParser;
import uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine.dotparser.ParseException;
import uk.ac.man.cs.mig.util.graph.outputrenderer.GraphOutputRenderer;
import uk.ac.man.cs.mig.util.graph.outputrenderer.impl.DotOutputGraphRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.impl.DefaultEdgeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.impl.DefaultNodeLabelRenderer;

import java.io.*;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 16, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public class DotGraphLayoutEngine implements GraphLayoutEngine
{
	private GraphOutputRenderer renderer;

    private int layoutDirection = LAYOUT_LEFT_TO_RIGHT;


    public DotGraphLayoutEngine()
	{
		renderer = new DotOutputGraphRenderer(new DefaultNodeLabelRenderer(), new DefaultEdgeLabelRenderer());

	}

	public void setGraphOutputRenderer(GraphOutputRenderer renderer)
	{
		this.renderer = renderer;
	}

	/**
	 * Lays out the specified <code>Graph</code>
	 * @param g The <code>Graph</code>
	 */
	public synchronized void layoutGraph(Graph g)
	{
		long t0, t1, t2;

		DotProcess process = new DotProcess();

            // Render the graph in DOT and send it to the
            // DOT Process the be laid out
            if(layoutDirection == LAYOUT_LEFT_TO_RIGHT)
            {
                renderer.setRendererOption(DotOutputGraphRenderer.LAYOUT_DIRECTION, "LR");
            }
            else
            {
                renderer.setRendererOption(DotOutputGraphRenderer.LAYOUT_DIRECTION, "TB");
            }

		DotLayoutEngineProperties properties = DotLayoutEngineProperties.getInstance();

            renderer.setRendererOption(DotOutputGraphRenderer.RANK_SPACING, Double.toString(properties.getRankSpacing()));

            renderer.setRendererOption(DotOutputGraphRenderer.SIBLING_SPACING, Double.toString(properties.getSiblingSpacing()));


	        try
	        {
		        File file = File.createTempFile("OWLVizScratch", null);

		        file.deleteOnExit();

		        System.out.println("TRACE(DotGraphLayoutEngine): TempFile: " + file.getAbsolutePath());

	            FileOutputStream fos = new FileOutputStream(file);

		        renderer.renderGraph(g, fos);

		        fos.close();

				if(process.startProcess(file.getAbsolutePath()) == false)
				{
					return;
				}


          // Read outputrenderer from process and parse it

	           InputStream is = null;



	           is = new FileInputStream(file);

		         // InputStream is = process.getReader();

				if(is != null)
				{
					try
					{
						DotParameterSetter paramSetter = new DotParameterSetter();

						paramSetter.setGraph(g);

						DotParser.parse(paramSetter, is);
					}
					catch(ParseException e)
					{
						e.printStackTrace();
					}

					t1 = System.currentTimeMillis();

				//	parser.parse(is);

					process.killProcess();

					process = null;
				}

				t2 = System.currentTimeMillis();
	        }
	        catch(IOException ioEx)
	        {
		        ioEx.printStackTrace();
	        }



	//	System.out.println("TRACE: DotGraphLayoutEngine: TotalLayoutTime: " + (t2 - t0));
	}

    /**
     * Sets the direction of the layout.
     * @param layoutDirection The layout direction. This should be one of
     * the constants <code>DotGraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT</code> or
     * <code>DotGraphLayoutEngine.LAYOUT_TOP_TO_BOTTOM</code>.
     */
    public void setLayoutDirection(int layoutDirection)
    {
        this.layoutDirection = layoutDirection;
    }

    /**
     * Gets the layout direction.
     * @return The direction of the layout. <code>DotGraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT</code> or
     * <code>DotGraphLayoutEngine.LAYOUT_TOP_TO_BOTTOM</code>.
     */
    public int getLayoutDirection()
    {
        return layoutDirection;
    }
}
