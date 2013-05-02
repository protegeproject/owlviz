package uk.ac.man.cs.mig.util.graph.model.impl;

import java.util.HashMap;
import java.util.Iterator;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 15, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public class DefaultGraphNode
{
	Object userObject;
	HashMap parents;
	HashMap children;

	public DefaultGraphNode(Object userObject)
	{
		this.userObject = userObject;

		parents = new HashMap();

		children = new HashMap();
	}

	public int getParentCount()
	{
		return parents.size();
	}

	public Iterator getParents()
	{
		return parents.keySet().iterator();
	}

	public Iterator getParentUserObjects()
	{
		return parents.values().iterator();
	}

	public int getChildCount()
	{
		return children.size();
	}

	public Iterator getChildren()
	{
		return children.keySet().iterator();
	}

	public Iterator getChilderenUserObjects()
	{
		return children.values().iterator();
	}


	public boolean addParent(DefaultGraphNode node)
	{
		if(parents.keySet().contains(node) == false)
		{
			parents.put(node, node.getUserObject());

			node.children.put(this, this.getUserObject());

			return true;
		}

		return false;
	}

	public boolean removeParent(DefaultGraphNode node)
	{

		if(parents.remove(node) != null)
		{
			node.removeChild(this);

			return true;
		}

		return false;
	}

	public boolean removeChild(DefaultGraphNode node)
	{
		if(children.remove(node) != null)
		{
			node.removeParent(this);

			return true;
		}

		return false;
	}

	public boolean removeFromChildren()
	{
		boolean ret = false;

		Iterator it = children.keySet().iterator();

		DefaultGraphNode childNode;

		while(it.hasNext())
		{
			childNode = (DefaultGraphNode)it.next();

			// Can't use remove parent, as an excpetion will
			// be thrown - only this iterator can remove things
			// from out children.
			childNode.parents.remove(this);

			ret = true;

			it.remove();
		}

		return ret;
	}

	public boolean removeFromParents()
	{
		boolean ret = false;

		Iterator it = parents.keySet().iterator();

		DefaultGraphNode parentNode;

		while(it.hasNext())
		{
			parentNode = (DefaultGraphNode)it.next();

			parentNode.children.remove(this);

			it.remove();

			ret = true;
		}

		return ret;
	}

	public boolean addChild(DefaultGraphNode node)
	{
		if(children.keySet().contains(node) == false)
		{
			children.put(node, node.getUserObject());

			node.parents.put(this, this.getUserObject());

			return true;
		}

		return false;
	}

	public Object getUserObject()
	{
		return userObject;
	}

}
