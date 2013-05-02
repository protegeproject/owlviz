package uk.ac.man.cs.mig.util.graph.event;

import uk.ac.man.cs.mig.util.graph.graph.Node;

import java.awt.event.MouseEvent;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jun 1, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class NodeClickedEvent
{
	private Node node;
	private MouseEvent mouseEvent;

	/**
	 * Constructs a new <code>NodeClickedEvent</code>
	 * @param node The <code>Node</code> associated with the event.
	 * @param mouseEvent The <code>MouseEvent</code> associated with
	 * the event.
	 */
	public NodeClickedEvent(Node node, MouseEvent mouseEvent)
	{
		this.node = node;

		this.mouseEvent = mouseEvent;
	}

	/**
	 * Gets the mouse event that is associated
	 * with the node click
	 * @return The <code>MouseEvent</code>
	 */
	public MouseEvent getMouseEvent()
	{
		return mouseEvent;
	}

	/**
	 * Gets the <code>Node</code> associated
	 * with the event.
	 * @return The <code>Node</code>
	 */
	public Node getNode()
	{
		return node;
	}




}

