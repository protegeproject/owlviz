package uk.ac.man.cs.mig.coode.owlviz.model;

import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.event.EventType;
import org.protege.editor.owl.model.event.OWLModelManagerChangeEvent;
import org.protege.editor.owl.model.event.OWLModelManagerListener;
import org.protege.editor.owl.model.hierarchy.OWLObjectHierarchyProvider;
import org.protege.editor.owl.model.hierarchy.OWLObjectHierarchyProviderListener;
import org.semanticweb.owlapi.model.*;
import uk.ac.man.cs.mig.util.graph.model.GraphModel;
import uk.ac.man.cs.mig.util.graph.model.impl.AbstractGraphModel;

import java.util.*;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 08-Jun-2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class AbstractOWLClassGraphModel extends AbstractGraphModel {

    private OWLModelManager owlModelManager;

    private OWLObjectHierarchyProvider provider;

    private OWLObjectHierarchyProviderListener listener;

    private OWLOntologyChangeListener changeListener;

    private OWLModelManagerListener owlModelManagerListener;

    public AbstractOWLClassGraphModel(OWLModelManager owlModelManager,
                                      OWLObjectHierarchyProvider provider) {
        this.owlModelManager = owlModelManager;
        listener = new OWLObjectHierarchyProviderListener() {

            public void nodeChanged(OWLObject node) {
                // TODO: Sync!
            }


            public void childParentAdded(OWLObject child, OWLObject parent) {
                fireChildAddedEvent(parent, child);
                fireParentAddedEvent(child, parent);
            }

            public void childParentRemoved(OWLObject child, OWLObject parent) {
                fireChildRemovedEvent(parent, child);
                fireParentRemovedEvent(child, parent);
            }

            public void rootAdded(OWLObject root) {

            }

            public void rootRemoved(OWLObject root) {
            }

            public void hierarchyChanged() {
            }
        };
        provider.addListener(listener);
        this.provider = provider;
        changeListener = new OWLOntologyChangeListener() {
            public void ontologiesChanged(List<? extends OWLOntologyChange> changes) {
            }
        };
        owlModelManager.addOntologyChangeListener(changeListener);
        owlModelManagerListener = new OWLModelManagerListener() {
            public void handleChange(OWLModelManagerChangeEvent event) {
                if(event.isType(EventType.ACTIVE_ONTOLOGY_CHANGED)) {
                    // Clear
                    fireModelChangedEvent();
                }
            }
        };
        owlModelManager.addListener(owlModelManagerListener);
    }

    public void dispose() {
        provider.removeListener(listener);
        owlModelManager.removeOntologyChangeListener(changeListener);
        owlModelManager.removeListener(owlModelManagerListener);
    }

    protected Set<OWLObject> getChildren(OWLObject obj) {
        Set<OWLObject> children = new HashSet<OWLObject>();
            children.addAll(provider.getChildren(obj));
            children.addAll(provider.getEquivalents(obj));
        return children;
    }


    protected Set<OWLObject> getParents(OWLObject obj) {
        Set<OWLObject> parents = new HashSet<OWLObject>();
            parents.addAll(provider.getParents(obj));
            parents.addAll(provider.getEquivalents(obj));
        return parents;
    }

    public int getChildCount(Object obj) {
        return getChildren((OWLObject) obj).size();
    }

    public Iterator getChildren(Object obj) {
        return getChildren((OWLObject) obj).iterator();
    }

    public int getParentCount(Object obj) {
        return getParents((OWLObject) obj).size();
    }

    public Iterator getParents(Object obj) {
        return getParents((OWLObject) obj).iterator();
    }

    public boolean contains(Object obj) {
            if(obj instanceof OWLClass) {
                for(OWLOntology ont : owlModelManager.getActiveOntologies()) {
                    if(ont.containsClassInSignature(((OWLClass) obj).getIRI())) {
                        return true;
                    }
                }
            }
        return false;
    }

    public Object getRelationshipType(Object parentObject, Object childObject) {
        return " is-a ";
    }

    public int getRelationshipDirection(Object parentObject, Object childObject) {
        return GraphModel.DIRECTION_BACK;
    }

    public Iterator getRelatedObjectsToAdd(Object obj) {
        return Collections.EMPTY_LIST.iterator();
    }

    public Iterator getRelatedObjectsToRemove(Object obj) {
        return Collections.EMPTY_LIST.iterator();
    }

}
