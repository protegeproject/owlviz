package uk.ac.man.cs.mig.coode.owlviz.model;

import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.hierarchy.OWLObjectHierarchyProvider;
import org.semanticweb.owl.model.OWLObject;
import org.semanticweb.owl.model.OWLClass;

import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 27-Aug-2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class OWLClassGraphInferredModel extends AbstractOWLClassGraphModel {

    private OWLClass nothing;

    public OWLClassGraphInferredModel(OWLModelManager owlModelManager) {
        super(owlModelManager, owlModelManager.getInferredOWLClassHierarchyProvider());
        nothing = owlModelManager.getOWLOntologyManager().getOWLDataFactory().getOWLNothing();
    }
}
