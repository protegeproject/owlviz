package org.coode.owlviz.util.graph.layout.dotlayoutengine;

import org.coode.owlviz.util.graph.controller.Controller;
import org.coode.owlviz.util.graph.graph.Graph;
import org.coode.owlviz.util.graph.layout.GraphLayoutEngine;
import org.coode.owlviz.util.graph.layout.dotlayoutengine.dotparser.DotParameterSetter;
import org.coode.owlviz.util.graph.layout.dotlayoutengine.dotparser.DotParser;
import org.coode.owlviz.util.graph.layout.dotlayoutengine.dotparser.ParseException;
import org.coode.owlviz.util.graph.outputrenderer.GraphOutputRenderer;
import org.coode.owlviz.util.graph.outputrenderer.impl.DotOutputGraphRenderer;
import org.coode.owlviz.util.graph.renderer.NodeLabelRenderer;
import org.coode.owlviz.util.graph.renderer.impl.DefaultEdgeLabelRenderer;
import org.coode.owlviz.util.graph.renderer.impl.DefaultNodeLabelRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 16, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class DotGraphLayoutEngine implements GraphLayoutEngine {

    private static Logger log = LoggerFactory.getLogger(DotGraphLayoutEngine.class);

    private GraphOutputRenderer renderer;

    private int layoutDirection = LAYOUT_LEFT_TO_RIGHT;

    private final NodeLabelRenderer labelRenderer;

    public DotGraphLayoutEngine(DotOutputGraphRenderer graphRenderer, NodeLabelRenderer labelRenderer) {
        renderer = checkNotNull(graphRenderer);
        this.labelRenderer = labelRenderer;
    }

    public void setGraphOutputRenderer(GraphOutputRenderer renderer) {
        this.renderer = renderer;
    }

    /**
     * Lays out the specified <code>Graph</code>
     *
     * @param g The <code>Graph</code>
     */
    public synchronized void layoutGraph(Graph g) {

        DotProcess process = new DotProcess();

        // Render the graph in DOT and send it to the
        // DOT Process the be laid out
        if (layoutDirection == LAYOUT_LEFT_TO_RIGHT) {
            renderer.setRendererOption(DotOutputGraphRenderer.LAYOUT_DIRECTION, "LR");
        }
        else {
            renderer.setRendererOption(DotOutputGraphRenderer.LAYOUT_DIRECTION, "TB");
        }

        DotLayoutEngineProperties properties = DotLayoutEngineProperties.getInstance();

        renderer.setRendererOption(DotOutputGraphRenderer.RANK_SPACING, Double.toString(properties.getRankSpacing()));

        renderer.setRendererOption(DotOutputGraphRenderer.SIBLING_SPACING, Double.toString(properties.getSiblingSpacing()));


        try {
            File file = File.createTempFile("OWLVizScratch", null);

            file.deleteOnExit();

            if (log.isDebugEnabled()) {
                log.debug("[DotGraphLayoutEngine] TempFile: " + file.getAbsolutePath());
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                renderer.renderGraph(g, fos);
            }

            if (!process.startProcess(file.getAbsolutePath())) {
                return;
            }

            logRendering(file);

            // Read output renderer from process and parse it
            InputStream is = new FileInputStream(file);
            try {
                DotParameterSetter paramSetter = new DotParameterSetter(labelRenderer);
                paramSetter.setGraph(g);
                DotParser.parse(paramSetter, is);
            } catch (ParseException e) {
                log.error("[DotGraphLayoutEngine] An error occurred whilst parsing the DOT file", e);
            }
            process.killProcess();
        } catch (IOException ioEx) {
            log.error("[DotGraphLayoutEngine] An error occurred whilst performing graph layout", ioEx);
        }


    }

    private void logRendering(File f) {
        if (log.isDebugEnabled()) {
            BufferedReader reader = null;
            try {
                StringBuilder sb = new StringBuilder();
                try {
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                        sb.append(line);
                        sb.append('\n');
                    }
                    log.debug("[DotGraphLayoutEngine] {}", sb.toString());
                } finally {
                    if (reader != null) {
                        reader.close();
                    }
                }
            } catch (IOException ioe) {
                log.debug("Could not log contents of file {}", f);
            }
        }
    }

    /**
     * Sets the direction of the layout.
     *
     * @param layoutDirection The layout direction. This should be one of
     *                        the constants <code>DotGraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT</code> or
     *                        <code>DotGraphLayoutEngine.LAYOUT_TOP_TO_BOTTOM</code>.
     */
    public void setLayoutDirection(int layoutDirection) {
        this.layoutDirection = layoutDirection;
    }

    /**
     * Gets the layout direction.
     *
     * @return The direction of the layout. <code>DotGraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT</code> or
     * <code>DotGraphLayoutEngine.LAYOUT_TOP_TO_BOTTOM</code>.
     */
    public int getLayoutDirection() {
        return layoutDirection;
    }
}
