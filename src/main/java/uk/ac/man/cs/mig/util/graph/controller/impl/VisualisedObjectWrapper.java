package uk.ac.man.cs.mig.util.graph.controller.impl;



/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 12, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public class VisualisedObjectWrapper
{
	private Object obj;
	private boolean allParentsVisible;
	private boolean allChildrenVisible;

	public VisualisedObjectWrapper(Object obj)
	{
		this.obj = obj;
	}

	public boolean isAllChildrenVisible()
	{
		return allChildrenVisible;
	}

	public void setAllChildrenVisible(boolean allChildrenVisible)
	{
		this.allChildrenVisible = allChildrenVisible;
	}

	public boolean isAllParentsVisible()
	{
		return allParentsVisible;
	}

	public void setAllParentsVisible(boolean allParentsVisible)
	{
		this.allParentsVisible = allParentsVisible;
	}

	public Object getObject()
	{
		return obj;
	}

}
