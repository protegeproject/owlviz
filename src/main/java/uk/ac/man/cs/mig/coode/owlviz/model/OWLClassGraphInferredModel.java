package uk.ac.man.cs.mig.coode.owlviz.model;

import org.protege.editor.owl.model.OWLModelManager;

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

//    private OWLClass nothing;

    public OWLClassGraphInferredModel(OWLModelManager owlModelManager) {
        super(owlModelManager, owlModelManager.getOWLHierarchyManager().getInferredOWLClassHierarchyProvider());
//        nothing = owlModelManager.getOWLOntologyManager().getOWLDataFactory().getOWLNothing();
    }
}
