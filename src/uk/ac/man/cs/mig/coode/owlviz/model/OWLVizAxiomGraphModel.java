package uk.ac.man.cs.mig.coode.owlviz.model;

import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.model.hierarchy.OWLObjectHierarchyProvider;
import org.semanticweb.owl.model.*;
import uk.ac.man.cs.mig.util.graph.model.impl.AbstractGraphModel;
import uk.ac.man.cs.mig.util.graph.model.GraphModel;
import uk.ac.man.cs.mig.util.graph.graph.Graph;

import java.util.*;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-May-2007<br><br>
 */
public class OWLVizAxiomGraphModel extends AbstractGraphModel implements OWLAxiomVisitor, OWLDescriptionVisitor {

    private Set<OWLObject> nodes;

    private Map<OWLObject, Set<OWLObject>> child2ParentMap;

    private Map<OWLObject, Set<OWLObject>> parent2ChildMap;

    private Map<AxiomEdge, OWLObject> edgeMap;

    private EdgeNameGenerator edgeNameGenerator;


    public OWLVizAxiomGraphModel(Set<OWLAxiom> axioms) {
        child2ParentMap = new HashMap<OWLObject, Set<OWLObject>>();
        parent2ChildMap = new HashMap<OWLObject, Set<OWLObject>>();
        nodes = new HashSet<OWLObject>();
        edgeMap = new HashMap<AxiomEdge, OWLObject>();
        for(OWLAxiom ax : axioms) {
            ax.accept(this);
        }
        edgeNameGenerator = new EdgeNameGenerator();
    }

    private Set<OWLObject> getIndexedSet(OWLObject key, Map<OWLObject, Set<OWLObject>> map, boolean create) {
        Set<OWLObject> vals = map.get(key);
        if(vals == null) {
            vals = new HashSet<OWLObject>();
            if(create) {
                map.put(key, vals);
            }
        }
        return vals;
    }


    public boolean contains(Object obj) {
        return nodes.contains(obj);
    }


    public void dispose() {
    }


    public int getChildCount(Object obj) {
        return getIndexedSet((OWLObject) obj, parent2ChildMap, false).size();
    }


    public Iterator getChildren(Object obj) {
        return getIndexedSet((OWLObject) obj, parent2ChildMap, false).iterator();
    }


    public int getParentCount(Object obj) {
        return getIndexedSet((OWLObject) obj, child2ParentMap, false).size();
    }


    public Iterator getParents(Object obj) {
        return getIndexedSet((OWLObject) obj, child2ParentMap, false).iterator();
    }


    public Iterator getRelatedObjectsToAdd(Object obj) {
        return Collections.emptySet().iterator();
    }


    public Iterator getRelatedObjectsToRemove(Object obj) {
        return Collections.emptySet().iterator();
    }


    public int getRelationshipDirection(Object parentObject, Object childObject) {
        AxiomEdge edge = new AxiomEdge(childObject, parentObject);
        OWLObject edgeObject = edgeMap.get(edge);
        if(edgeObject != null) {
            edgeNameGenerator.reset();
            if(edgeObject instanceof OWLAxiom) {
                ((OWLAxiom) edgeObject).accept(edgeNameGenerator);
            }
            else {
                ((OWLDescription) edgeObject).accept(edgeNameGenerator);
            }
            return edgeNameGenerator.direction;
        }
        return GraphModel.DIRECTION_BACK;
    }


    public Object getRelationshipType(Object parentObject, Object childObject) {
        AxiomEdge edge = new AxiomEdge(childObject, parentObject);
        OWLObject edgeObject = edgeMap.get(edge);
        if(edgeObject != null) {
            edgeNameGenerator.reset();
            if (edgeObject instanceof OWLDescription) {
                ((OWLDescription) edgeObject).accept(edgeNameGenerator);
            }
            else {
                ((OWLAxiom) edgeObject).accept(edgeNameGenerator);
            }
            return edgeNameGenerator.edgeName;
        }
        else {
            return "";
        }
    }


    private void addChildParent(OWLObject child, OWLObject parent, OWLObject ax) {
        getIndexedSet(child, child2ParentMap, true).add(parent);
        getIndexedSet(parent, parent2ChildMap, true).add(parent);
        nodes.add(child);
        nodes.add(parent);
        edgeMap.put(new AxiomEdge(child, parent), ax);
        edgeMap.put(new AxiomEdge(parent, child), ax);
    }

    public Set<OWLObject> getAllNodes() {
        return nodes;
    }




    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Axiom Visitor stuff
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
    }


    public void visit(OWLAxiomAnnotationAxiom axiom) {
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        addChildParent(axiom.getIndividual(), axiom.getDescription(), axiom);
        axiom.getDescription().accept(this);
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        addChildParent(axiom.getSubject(), axiom.getObject(), axiom);
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        // TODO: Look for subclass of existentials and add appropriate edge
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
    }


    public void visit(OWLDataSubPropertyAxiom axiom) {
        addChildParent(axiom.getSubProperty(), axiom.getSuperProperty(), axiom);
    }


    public void visit(OWLDeclarationAxiom axiom) {
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        // TODO: Pairwise?
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        Set<OWLDescription> added = new HashSet<OWLDescription>();
        for(OWLDescription descA : axiom.getDescriptions()) {
            for(OWLDescription descB : axiom.getDescriptions()) {
                if(!descA.equals(descB) && !added.contains(descA) && !added.contains(descB)) {
                    addChildParent(descA, descB, axiom);
                    added.add(descA);
                    added.add(descB);
                    descA.accept(this);
                    descB.accept(this);
                }
            }
        }
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        for(OWLDataPropertyExpression propA : axiom.getProperties()) {
            for(OWLDataPropertyExpression propB : axiom.getProperties()) {
                if(!propA.equals(propB)) {
                    addChildParent(propA, propB, axiom);
                }
            }
        }
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        for(OWLObjectPropertyExpression propA : axiom.getProperties()) {
            for(OWLObjectPropertyExpression propB : axiom.getProperties()) {
                if(!propA.equals(propB)) {
                    addChildParent(propA, propB, axiom);
                }
            }
        }
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
    }


    public void visit(OWLEntityAnnotationAxiom axiom) {
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        Set<OWLDescription> added = new HashSet<OWLDescription>();
        for(OWLDescription descA : axiom.getDescriptions()) {
            descA.accept(this);
            for(OWLDescription descB : axiom.getDescriptions()) {
                if(!descA.equals(descB) && !added.contains(descA) && !added.contains(descB)) {
                    addChildParent(descA, descB, axiom);
                    descA.accept(this);
                    descB.accept(this);
                    added.add(descA);
                    added.add(descB);
                }
            }
        }
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        for(OWLObjectPropertyExpression propA : axiom.getProperties()) {
            for(OWLObjectPropertyExpression propB : axiom.getProperties()) {
                if(!propA.equals(propB)) {
                    addChildParent(propA, propB, axiom);
                }
            }
        }
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
    }


    public void visit(OWLImportsDeclaration axiom) {
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        addChildParent(axiom.getSubject(), axiom.getObject(), axiom);
    }


    public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {

    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
    }


    public void visit(OWLObjectSubPropertyAxiom axiom) {
        addChildParent(axiom.getSubProperty(), axiom.getSuperProperty(), axiom);
    }


    public void visit(OWLOntologyAnnotationAxiom axiom) {
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
    }


    public void visit(OWLSameIndividualsAxiom axiom) {
    }


    public void visit(OWLSubClassAxiom axiom) {
        addChildParent(axiom.getSubClass(), axiom.getSuperClass(), axiom);
        axiom.getSubClass().accept(this);
        axiom.getSuperClass().accept(this);
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
    }


    public void visit(SWRLRule rule) {
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Description Visitor stuff
    //
    //////////////////////////////////////////////////////////////////////////////////////////////////////


    public void visit(OWLClass desc) {

    }


    public void visit(OWLDataAllRestriction desc) {

    }


    public void visit(OWLDataExactCardinalityRestriction desc) {
    }


    public void visit(OWLDataMaxCardinalityRestriction desc) {
    }


    public void visit(OWLDataMinCardinalityRestriction desc) {
    }


    public void visit(OWLDataSomeRestriction desc) {
    }


    public void visit(OWLDataValueRestriction desc) {
    }


    public void visit(OWLObjectAllRestriction desc) {
        addChildParent(desc, desc.getFiller(), desc);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectComplementOf desc) {
        addChildParent(desc.getOperand(), desc, desc);
        desc.getOperand().accept(this);
    }


    public void visit(OWLObjectExactCardinalityRestriction desc) {

    }


    public void visit(OWLObjectIntersectionOf desc) {
        for(OWLDescription op : desc.getOperands()) {
            addChildParent(desc, op, desc);
            op.accept(this);
        }
    }


    public void visit(OWLObjectMaxCardinalityRestriction desc) {
    }


    public void visit(OWLObjectMinCardinalityRestriction desc) {
        addChildParent(desc, desc.getFiller(), desc);
        desc.getFiller().accept(this);
    }


    public void visit(OWLObjectOneOf desc) {
        for(OWLIndividual ind : desc.getIndividuals()) {
            addChildParent(desc, ind, desc);
        }
    }


    public void visit(OWLObjectSelfRestriction desc) {
    }


    public void visit(OWLObjectSomeRestriction desc) {
        addChildParent(desc, desc.getFiller(), desc);
    }


    public void visit(OWLObjectUnionOf desc) {
        for(OWLDescription op : desc.getOperands()) {
            addChildParent(desc, op, desc);
        }
    }


    public void visit(OWLObjectValueRestriction desc) {
    }


    private class AxiomEdge {

        private Object child;

        private Object parent;


        public AxiomEdge(Object child, Object parent) {
            this.child = child;
            this.parent = parent;
        }


        public boolean equals(Object obj) {
            if(!(obj instanceof AxiomEdge)) {
                return false;
            }
            AxiomEdge other = (AxiomEdge) obj;
            return  other.child.equals(child) &&
                    other.parent.equals(parent);
        }


        public int hashCode() {
            return  19 * child.hashCode() +
                    31 * parent.hashCode();
        }
    }

    private class EdgeNameGenerator implements OWLAxiomVisitor, OWLDescriptionVisitor {

        private String edgeName;

        private int direction;

        public void reset() {
            edgeName = "<Don't know>";
            direction = DIRECTION_BACK;
        }

        public void visit(OWLSubClassAxiom axiom) {
            direction = GraphModel.DIRECTION_BACK;
            edgeName = "is-a";
        }


        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        }


        public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
        }


        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        }


        public void visit(OWLDisjointClassesAxiom axiom) {
            direction = GraphModel.DIRECTION_BOTH;
            edgeName = "disjoint-with";
        }


        public void visit(OWLDataPropertyDomainAxiom axiom) {
        }


        public void visit(OWLImportsDeclaration axiom) {
        }


        public void visit(OWLAxiomAnnotationAxiom axiom) {
        }


        public void visit(OWLObjectPropertyDomainAxiom axiom) {
        }


        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        }


        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        }


        public void visit(OWLDifferentIndividualsAxiom axiom) {
        }


        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        }


        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        }


        public void visit(OWLObjectPropertyRangeAxiom axiom) {
        }


        public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        }


        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        }


        public void visit(OWLObjectSubPropertyAxiom axiom) {
        }


        public void visit(OWLDisjointUnionAxiom axiom) {
        }


        public void visit(OWLDeclarationAxiom axiom) {
        }


        public void visit(OWLEntityAnnotationAxiom axiom) {
        }


        public void visit(OWLOntologyAnnotationAxiom axiom) {
        }


        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        }


        public void visit(OWLDataPropertyRangeAxiom axiom) {
        }


        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        }


        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        }


        public void visit(OWLClassAssertionAxiom axiom) {
            direction = GraphModel.DIRECTION_BACK;
            edgeName = "instance-of";
        }


        public void visit(OWLEquivalentClassesAxiom axiom) {
            direction = GraphModel.DIRECTION_BOTH;
            edgeName = "equivalent-to";
        }


        public void visit(OWLDataPropertyAssertionAxiom axiom) {
        }


        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        }


        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        }


        public void visit(OWLDataSubPropertyAxiom axiom) {
        }


        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        }


        public void visit(OWLSameIndividualsAxiom axiom) {
        }


        public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
        }


        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        }


        public void visit(SWRLRule rule) {
        }


        public void visit(OWLClass desc) {
        }


        public void visit(OWLObjectIntersectionOf desc) {
            edgeName = "";
            direction = GraphModel.DIRECTION_BACK;
        }


        public void visit(OWLObjectUnionOf desc) {
        }


        public void visit(OWLObjectComplementOf desc) {
            edgeName = "complementOf";
            direction = GraphModel.DIRECTION_FORWARD;
        }


        public void visit(OWLObjectSomeRestriction desc) {
            edgeName = desc.getProperty().toString();
        }


        public void visit(OWLObjectAllRestriction desc) {
        }


        public void visit(OWLObjectValueRestriction desc) {
        }


        public void visit(OWLObjectMinCardinalityRestriction desc) {
        }


        public void visit(OWLObjectExactCardinalityRestriction desc) {
        }


        public void visit(OWLObjectMaxCardinalityRestriction desc) {
        }


        public void visit(OWLObjectSelfRestriction desc) {
        }


        public void visit(OWLObjectOneOf desc) {
        }


        public void visit(OWLDataSomeRestriction desc) {
        }


        public void visit(OWLDataAllRestriction desc) {
        }


        public void visit(OWLDataValueRestriction desc) {
        }


        public void visit(OWLDataMinCardinalityRestriction desc) {
        }


        public void visit(OWLDataExactCardinalityRestriction desc) {
        }


        public void visit(OWLDataMaxCardinalityRestriction desc) {
        }
    }
}
