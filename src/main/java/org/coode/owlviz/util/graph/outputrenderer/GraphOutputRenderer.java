package org.coode.owlviz.util.graph.outputrenderer;

import org.coode.owlviz.util.graph.graph.Graph;

import java.io.OutputStream;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 16, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 * <p/>
 * A renderer is capable of rendering a <code>Graph</code>
 * to an outputrenderer stream.  This could produce a visual
 * representation of a <code>Graph</code>, or a textual representation
 * of a <code>Graph</code> for example.
 */
public interface GraphOutputRenderer {

    public void renderGraph(Graph graph, OutputStream os);

    public void setRendererOption(String attribute, String value);
}
