package uk.ac.man.cs.mig.coode.owlviz.model;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.OWLAxiomVisitorAdapter;
import uk.ac.man.cs.mig.util.graph.model.GraphModel;
import uk.ac.man.cs.mig.util.graph.model.impl.AbstractGraphModel;

import java.util.*;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-May-2007<br>
 * <br>
 */
public class OWLVizAxiomGraphModel extends AbstractGraphModel implements
		OWLAxiomVisitor, OWLClassExpressionVisitor {

	private Set<OWLObject> nodes;

	private Map<OWLObject, Set<OWLObject>> child2ParentMap;

	private Map<OWLObject, Set<OWLObject>> parent2ChildMap;

	private Map<AxiomEdge, OWLObject> edgeMap;

	private EdgeNameGenerator edgeNameGenerator;

	/**
	 * This constructor initializes all maps and sets and invokes the method
	 * addAndClearAxioms with the set of axioms as paramter.
	 * 
	 * @param axioms
	 *            The axioms to visualize
	 */
	public OWLVizAxiomGraphModel(Set<OWLAxiom> axioms) {
		child2ParentMap = new HashMap<OWLObject, Set<OWLObject>>();
		parent2ChildMap = new HashMap<OWLObject, Set<OWLObject>>();
		nodes = new HashSet<OWLObject>();
		edgeMap = new HashMap<AxiomEdge, OWLObject>();
		edgeNameGenerator = new EdgeNameGenerator();
		this.addAndClearAxioms(axioms);
	}

	/**
	 * This method clears all the map (invokes the clearMaps-method) at first.
	 * Then it iterates over all axioms and invokes their accept-Method with
	 * "this" as parameter.
	 * 
	 * @param axioms
	 *            The axioms to visualize
	 */
	public void addAndClearAxioms(Set<OWLAxiom> axioms) {
		this.clearMaps();
		for (OWLAxiom ax : axioms) {
			ax.accept(this);
		}
	}

	/**
	 * Resets the child2ParentMap, the parent2ChildMap, the nodes Set an the
	 * edge map
	 */
	protected void clearMaps() {
		child2ParentMap.clear();
		parent2ChildMap.clear();
		nodes.clear();
		edgeMap.clear();
	}

	/**
	 * Return an edge object for the given parent and child
	 * 
	 * @param parent
	 *            the parent object
	 * @param child
	 *            the child object
	 * @return a new AxiomEdge
	 */
	protected OWLObject getEdgeObject(Object parent, Object child) {
		return edgeMap.get(new AxiomEdge(parent, child));
	}

	private Set<OWLObject> getIndexedSet(OWLObject key,
			Map<OWLObject, Set<OWLObject>> map, boolean create) {
		Set<OWLObject> vals = map.get(key);
		if (vals == null) {
			vals = new HashSet<OWLObject>();
			if (create) {
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
		return getIndexedSet((OWLObject) obj, parent2ChildMap, false)
				.iterator();
	}

	public int getParentCount(Object obj) {
		return getIndexedSet((OWLObject) obj, child2ParentMap, false).size();
	}

	public Iterator getParents(Object obj) {
		return getIndexedSet((OWLObject) obj, child2ParentMap, false)
				.iterator();
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
		if (edgeObject != null) {
			edgeNameGenerator.reset();
			if (edgeObject instanceof OWLAxiom) {
				((OWLAxiom) edgeObject).accept(edgeNameGenerator);
			} else {
				((OWLClassExpression) edgeObject).accept(edgeNameGenerator);
			}
			return edgeNameGenerator.direction;
		}
		return -1; // if no edge found, negative direction for handling in
		// DefaultEdgeFactory
	}

	public Object getRelationshipType(Object parentObject, Object childObject) {
		AxiomEdge edge = new AxiomEdge(childObject, parentObject);
		OWLObject edgeObject = edgeMap.get(edge);
		if (edgeObject != null) {
			edgeNameGenerator.reset();
			if (edgeObject instanceof OWLClassExpression) {
				((OWLClassExpression) edgeObject).accept(edgeNameGenerator);
			} else {
				((OWLAxiom) edgeObject).accept(edgeNameGenerator);
			}
			return edgeNameGenerator.edgeName;
		} else {
			return null;
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

	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Axiom Visitor stuff
	//
	// ////////////////////////////////////////////////////////////////////////////////////////////////////

	public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
	}


	public void visit(OWLClassAssertionAxiom axiom) {
		addChildParent(axiom.getIndividual(), axiom.getClassExpression(), axiom);
		axiom.getClassExpression().accept(this);
	}

	public void visit(OWLDataPropertyAssertionAxiom axiom) {
		addChildParent(axiom.getSubject(), axiom.getObject(), axiom);
	}

	public void visit(OWLDataPropertyDomainAxiom axiom) {
		// TODO: Look for subclass of existentials and add appropriate edge
	}

	public void visit(OWLDataPropertyRangeAxiom axiom) {
	}

	public void visit(OWLSubDataPropertyOfAxiom axiom) {
		addChildParent(axiom.getSubProperty(), axiom.getSuperProperty(), axiom);
	}

	public void visit(OWLDeclarationAxiom axiom) {
	}

	public void visit(OWLDifferentIndividualsAxiom axiom) {
		// TODO: Pairwise?
	}

	public void visit(OWLDisjointClassesAxiom axiom) {
		Set<OWLClassExpression> added = new HashSet<OWLClassExpression>();
		for (OWLClassExpression descA : axiom.getClassExpressions()) {
			for (OWLClassExpression descB : axiom.getClassExpressions()) {
				if (!descA.equals(descB) && !added.contains(descA)
						&& !added.contains(descB)) {
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
		for (OWLDataPropertyExpression propA : axiom.getProperties()) {
			for (OWLDataPropertyExpression propB : axiom.getProperties()) {
				if (!propA.equals(propB)) {
					addChildParent(propA, propB, axiom);
				}
			}
		}
	}

	public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
		for (OWLObjectPropertyExpression propA : axiom.getProperties()) {
			for (OWLObjectPropertyExpression propB : axiom.getProperties()) {
				if (!propA.equals(propB)) {
					addChildParent(propA, propB, axiom);
				}
			}
		}
	}

	public void visit(OWLDisjointUnionAxiom axiom) {
	}


	public void visit(OWLEquivalentClassesAxiom axiom) {
		Set<OWLClassExpression> added = new HashSet<OWLClassExpression>();
		for (OWLClassExpression descA : axiom.getClassExpressions()) {
			descA.accept(this);
			for (OWLClassExpression descB : axiom.getClassExpressions()) {
				if (!descA.equals(descB) && !added.contains(descA)
						&& !added.contains(descB)) {
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
		for (OWLObjectPropertyExpression propA : axiom.getProperties()) {
			for (OWLObjectPropertyExpression propB : axiom.getProperties()) {
				if (!propA.equals(propB)) {
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


    public void visit(OWLHasKeyAxiom owlHasKeyAxiom) {
    }


    public void visit(OWLDatatypeDefinition owlDatatypeDefinition) {
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

	public void visit(OWLSubPropertyChainOfAxiom axiom) {
	}

	public void visit(OWLObjectPropertyDomainAxiom axiom) {

	}

	public void visit(OWLObjectPropertyRangeAxiom axiom) {
	}

	public void visit(OWLSubObjectPropertyOfAxiom axiom) {
		addChildParent(axiom.getSubProperty(), axiom.getSuperProperty(), axiom);
	}


	public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
	}

	public void visit(OWLSameIndividualAxiom axiom) {
	}

	public void visit(OWLSubClassOfAxiom axiom) {
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

	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Description Visitor stuff
	//
	// ////////////////////////////////////////////////////////////////////////////////////////////////////

	public void visit(OWLClass desc) {

	}

	public void visit(OWLDataAllValuesFrom desc) {

	}

	public void visit(OWLDataExactCardinality desc) {
	}

	public void visit(OWLDataMaxCardinality desc) {
	}

	public void visit(OWLDataMinCardinality desc) {
	}

	public void visit(OWLDataSomeValuesFrom desc) {
	}

	public void visit(OWLDataHasValue desc) {
	}

	public void visit(OWLObjectAllValuesFrom desc) {
		addChildParent(desc, desc.getFiller(), desc);
		desc.getFiller().accept(this);
	}

	public void visit(OWLObjectComplementOf desc) {
		addChildParent(desc.getOperand(), desc, desc);
		desc.getOperand().accept(this);
	}

	public void visit(OWLObjectExactCardinality desc) {

	}

	public void visit(OWLObjectIntersectionOf desc) {
		for (OWLClassExpression op : desc.getOperands()) {
			addChildParent(desc, op, desc);
			op.accept(this);
		}
	}

	public void visit(OWLObjectMaxCardinality desc) {
	}

	public void visit(OWLObjectMinCardinality desc) {
		addChildParent(desc, desc.getFiller(), desc);
		desc.getFiller().accept(this);
	}

	public void visit(OWLObjectOneOf desc) {
		for (OWLIndividual ind : desc.getIndividuals()) {
			addChildParent(desc, ind, desc);
		}
	}

	public void visit(OWLObjectHasSelf desc) {
	}

	public void visit(OWLObjectSomeValuesFrom desc) {
		addChildParent(desc, desc.getFiller(), desc);
	}

	public void visit(OWLObjectUnionOf desc) {
		for (OWLClassExpression op : desc.getOperands()) {
			addChildParent(desc, op, desc);
		}
	}

	public void visit(OWLObjectHasValue desc) {
	}


    public void visit(OWLAnnotationAssertionAxiom owlAnnotationAssertionAxiom) {
    }


    public void visit(OWLSubAnnotationPropertyOfAxiom owlSubAnnotationPropertyOfAxiom) {
    }


    public void visit(OWLAnnotationPropertyDomainAxiom owlAnnotationPropertyDomainAxiom) {
    }


    public void visit(OWLAnnotationPropertyRangeAxiom owlAnnotationPropertyRangeAxiom) {
    }


    private class AxiomEdge {

		private Object child;

		private Object parent;

		public AxiomEdge(Object child, Object parent) {
			this.child = child;
			this.parent = parent;
		}

		public boolean equals(Object obj) {
			if (!(obj instanceof AxiomEdge)) {
				return false;
			}
			AxiomEdge other = (AxiomEdge) obj;
			return other.child.equals(child) && other.parent.equals(parent);
		}

		public int hashCode() {
			return 19 * child.hashCode() + 31 * parent.hashCode();
		}
	}

	private class EdgeNameGenerator extends OWLAxiomVisitorAdapter implements OWLClassExpressionVisitor {

		private String edgeName;

		private int direction;

		public void reset() {
			edgeName = "<Don't know>";
			direction = DIRECTION_BACK;
		}

		public void visit(OWLSubClassOfAxiom axiom) {
			direction = GraphModel.DIRECTION_BACK;
			edgeName = "is-a";
		}

		public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
		}

		public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
		}

		public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
		}

		public void visit(OWLDisjointClassesAxiom axiom) {
			direction = GraphModel.DIRECTION_BOTH;
			edgeName = "disjoint-with";
		}

		public void visit(OWLDataPropertyDomainAxiom axiom) {
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

		public void visit(OWLSubObjectPropertyOfAxiom axiom) {
		}

		public void visit(OWLDisjointUnionAxiom axiom) {
		}

		public void visit(OWLDeclarationAxiom axiom) {
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

		public void visit(OWLSubDataPropertyOfAxiom axiom) {
		}

		public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
		}

		public void visit(OWLSameIndividualAxiom axiom) {
		}

		public void visit(OWLSubPropertyChainOfAxiom axiom) {
		}

		public void visit(OWLInverseObjectPropertiesAxiom axiom) {
		}

		public void visit(SWRLRule rule) {
		}

		public void visit(OWLClass desc) {
		}

		public void visit(OWLObjectIntersectionOf desc) {
			edgeName = "intesection";
			direction = GraphModel.DIRECTION_BACK;
		}

		public void visit(OWLObjectUnionOf desc) {
			edgeName = "union";
			direction = GraphModel.DIRECTION_BACK;
		}

		public void visit(OWLObjectComplementOf desc) {
			edgeName = "complementOf";
			direction = GraphModel.DIRECTION_FORWARD;
		}

		public void visit(OWLObjectSomeValuesFrom desc) {
			edgeName = desc.getProperty().toString();
		}

		public void visit(OWLObjectAllValuesFrom desc) {
		}

		public void visit(OWLObjectHasValue desc) {
		}

		public void visit(OWLObjectMinCardinality desc) {
		}

		public void visit(OWLObjectExactCardinality desc) {
		}

		public void visit(OWLObjectMaxCardinality desc) {
		}

		public void visit(OWLObjectHasSelf desc) {
		}

		public void visit(OWLObjectOneOf desc) {
		}

		public void visit(OWLDataSomeValuesFrom desc) {
		}

		public void visit(OWLDataAllValuesFrom desc) {
		}

		public void visit(OWLDataHasValue desc) {
		}

		public void visit(OWLDataMinCardinality desc) {
		}

		public void visit(OWLDataExactCardinality desc) {
		}

		public void visit(OWLDataMaxCardinality desc) {
		}
	}
}
