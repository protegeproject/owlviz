package org.coode.owlviz.util.graph.factory.impl;

import org.coode.owlviz.util.graph.factory.GraphFactory;
import org.coode.owlviz.util.graph.graph.Graph;
import org.coode.owlviz.util.graph.graph.impl.DefaultGraph;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 14, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class DefaultGraphFactory implements GraphFactory {

    /**
     * Creates a <code>Graph</code>
     *
     * @return The newly created <code>Graph</code>
     */
    public Graph createGraph() {
        return new DefaultGraph();
    }
}
