package org.coode.owlviz.ui.renderer;

import org.coode.owlviz.util.graph.graph.Node;
import org.coode.owlviz.util.graph.renderer.NodeLabelRenderer;
import org.protege.editor.owl.model.OWLModelManager;
import org.semanticweb.owlapi.model.OWLEntity;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 5, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class OWLClsNodeLabelRenderer implements NodeLabelRenderer {

    private OWLModelManager owlModelManager;

    public OWLClsNodeLabelRenderer(OWLModelManager owlModelManager) {
        this.owlModelManager = owlModelManager;
    }

    /**
     * Gets the label for the specified <code>Node</code>.
     *
     * @param node The <code>Node</code>.
     * @return The <code>Nodes</code> label.
     */
    public String getLabel(Node node) {
        Object obj = node.getUserObject();
        if (obj instanceof OWLEntity) {
            return owlModelManager.getRendering((OWLEntity) obj);
        }
        return "";
    }
}
