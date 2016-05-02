package org.coode.owlviz.util.graph.renderer.impl;

import org.coode.owlviz.util.graph.graph.Node;
import org.coode.owlviz.util.graph.renderer.NodeLabelRenderer;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 16, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class DefaultNodeLabelRenderer implements NodeLabelRenderer {

    /**
     * Gets the label for the specified <code>Node</code>.
     *
     * @param node The <code>Node</code>.
     * @return The <code>Nodes</code> label.
     */
    public String getLabel(Node node) {
        return (node.getUserObject()).toString();
    }
}
