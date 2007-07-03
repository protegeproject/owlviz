package uk.ac.man.cs.mig.coode.owlviz.model;

import org.protege.editor.owl.model.OWLModelManager;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 08-Jun-2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class OWLClassGraphAssertedModel extends AbstractOWLClassGraphModel {

    public OWLClassGraphAssertedModel(OWLModelManager owlModelManager) {
        super(owlModelManager, owlModelManager.getOWLClassHierarchyProvider());
    }
}
