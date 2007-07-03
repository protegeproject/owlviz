package uk.ac.man.cs.mig.coode.owlviz.ui.renderer;

//import edu.stanford.smi.protegex.owl.model.OWLIndividual;
//import edu.stanford.smi.protegex.owl.model.OWLNamedClass;
//import edu.stanford.smi.protegex.owl.model.RDFSNamedClass;
import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.graph.graph.Edge;
import uk.ac.man.cs.mig.util.graph.renderer.impl.DefaultEdgeRenderer;

import java.awt.*;

import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLIndividual;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jun 1, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class OWLClsEdgeRenderer extends DefaultEdgeRenderer {

    BasicStroke stroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, new float[]{4.0f, 2.0f},
                                         1.0f);

    BasicStroke instanceOfStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f,
                                                   new float[]{10.0f, 2.0f}, 1.0f);


    public OWLClsEdgeRenderer(Controller controller) {
        super(controller);
    }


    protected Stroke getEdgeStroke(Edge edge,
                                   boolean forPrinting) {
        Object parObj = edge.getTailNode().getUserObject();
        Object childObj = edge.getHeadNode().getUserObject();
        if(parObj instanceof OWLClass && childObj instanceof OWLIndividual) {
            return instanceOfStroke;
        }
        else {
            if(parObj instanceof OWLClass == false || childObj instanceof OWLClass == false) {
                return stroke;
            }
            else {
                return super.getEdgeStroke(edge, forPrinting);
            }
        }
    }

}

