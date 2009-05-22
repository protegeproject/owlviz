package uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine.dotparser;

import uk.ac.man.cs.mig.util.graph.graph.Edge;
import uk.ac.man.cs.mig.util.graph.graph.Graph;
import uk.ac.man.cs.mig.util.graph.graph.Node;
import uk.ac.man.cs.mig.util.graph.renderer.NodeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.impl.DefaultNodeLabelRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: May 7, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 * <p/>
 * <p/>
 * The <code>DotParameterSetter</code> sets the various attributes
 * of a <code>Graph</code> and it's <code>Node</code>s and <code>Edge</code>s,
 * using attribute/value <code>String</code> pairs (that come from a dot file).
 */
public class DotParameterSetter
{

	private Graph graph;
	private HashMap nodeMap;
	private HashMap edgeMap;

	private Edge[] edges;
	private Node[] nodes;
	private NodeLabelRenderer labelRen;

	private static int graphHeight = 0;




	public DotParameterSetter()
	{
		labelRen = new DefaultNodeLabelRenderer();
	}




	/**
	 * Sets the <code>Graph</code> whose <code>Node</code>s and
	 * <code>Edge</code>s the attributes will apply to.
	 *
	 * @param g The <code>Graph</code>
	 */
	public void setGraph(Graph g)
	{
		// Set the graph
		graph = g;

		// Reset the graph height
		graphHeight = 0;

		// Extract the Nodes
		nodes = graph.getNodes();

		// Put the Nodes into the NodeMap.  This allows us
		// to retrive a Node based on the Node label, which
		// is generated
		nodeMap = new HashMap(nodes.length);

		for(int i = 0; i < nodes.length; i++)
		{
			nodeMap.put(labelRen.getLabel(nodes[i]), nodes[i]);
		}

		edges = graph.getEdges();

		edgeMap = new HashMap(edges.length);


		String tail;
		String head;

		// Put the edge keys into the edge map
		for(int i = 0; i < edges.length; i++)
		{
			tail = labelRen.getLabel(edges[i].getTailNode());

			head = labelRen.getLabel(edges[i].getHeadNode());

			edgeMap.put(new NodeEdgeKey(tail, head), edges[i]);
		}
	}




	/**
	 * Sets an attribute for the <code>Graph</code>.
	 *
	 * @param name  The name of the attribute, for example,
	 *              "bb" will set the <code>Graph</code> bounding box.
	 * @param value The value of the attribute, for example,
	 *              the value "10,20,300,400" might represent a bounding
	 *              box located at (10, 20), with a width of 300 and height
	 *              of 400.
	 */
	public void setGraphAttribute(String name, String value)
	{
		if(name.equals("bb"))
		{
			Rectangle r = parseRect(value);

			if(r != null)
			{
				graph.setShape(r);

				graphHeight = r.height;
			}
		}
	}




	/**
	 * Sets the attribute for a specified <code>Node</code>.
	 *
	 * @param nodeID The name of the <code>Node</code>.
	 * @param name   The name of the attribute
	 * @param value  The value of the attribute
	 */
	public void setNodeAttribute(String nodeID, String name, String value)
	{
		if(name.equals("pos"))
		{
			Node n = (Node) nodeMap.get(nodeID);

			if(n != null)
			{
				Point p = parsePoint(value);

				if(p != null)
				{
					n.setPosition(p.x, graphHeight - p.y);
				}
			}
		}
	}




	/**
	 * Sets the attribute for a specified <code>Edge</code>.  The <code>Edge</code>
	 * is specified in terms of the <code>Node</code>s that it connects.
	 *
	 * @param tailNodeID The tail <code>Node</code> name.
	 * @param headNodeID The head <code>Node</code> name.
	 * @param name       The name of the attribute
	 * @param value      The value of the attribute
	 */
	public void setEdgeAttribute(String tailNodeID, String headNodeID, String name, String value)
	{
		NodeEdgeKey nek = new NodeEdgeKey(tailNodeID, headNodeID);

		Edge edge = (Edge) edgeMap.get(nek);

		if(edge != null)
		{
			if(name.equals("pos"))
			{
				setEdgePath(edge, value);
			}
			else if(name.equals("lp"))
			{
				Point lp = new Point();

				lp = parsePoint(value);

				edge.setLabelPosition(lp.x, graphHeight - lp.y);
			}
		}

	}




	/**
	 * Sets the positions of a path origin, and the ctrl points
	 * of the path.
	 *
	 * @param edge  The <code>Edge</code> that will have it's
	 *              points set.
	 * @param value The <code>String</code> containing the edge
	 *              start point and ctrl points.
	 */
	public void setEdgePath(Edge edge, String value)
	{
		// Points are separated by spaced
		// Points be be preceded by the letter
		// s or the letter e, indicating  the arrowhead point
		// at the start or the arrowhead point at the end.

		// Extract the edge points from the the string - a list of points separated by spaces

		StringTokenizer tokenizer = new StringTokenizer(value);

		String token;

		// Create a list to hold the points in
		ArrayList<Point> edgePoints = new ArrayList<Point>(10); // A size of around 10 should be enough

		Point edgePoint;

		Point startPoint = null;

		Point endPoint = null;

		while(tokenizer.hasMoreTokens())
		{
			token = tokenizer.nextToken();

			if(token.charAt(0) == 's')
			{
				startPoint = parsePoint(token.substring(2, token.length()));

				startPoint.y = graphHeight - startPoint.y;
			}
			else if(token.charAt(0) == 'e')
			{
				endPoint = parsePoint(token.substring(2, token.length()));

				endPoint.y = graphHeight - endPoint.y;
			}
			else
			{
				edgePoint = parsePoint(token);

				edgePoint.y = graphHeight - edgePoint.y;

				edgePoints.add(edgePoint);
			}
		}


		// Put the information into the edge
		// Clear any information that is in the
		// edge.
		edge.resetPath();

		// Set the start of the edge path
		edgePoint = edgePoints.get(0);

		edge.setPathOrigin(edgePoint.x, edgePoint.y);


		// Set the positions of the control points.

		int numberOfCtrlPoints = (edgePoints.size() - 1) / 3;

		// Control points for bezier curve
		Point cp1, cp2, cp3;

		int ctrlPointIndex;
		// Remember, the first point in the list is
		// the arrowhead tip.  The second point is
		// the first point on the edge path.
		for(int i = 0; i < numberOfCtrlPoints; i++)
		{
			ctrlPointIndex = i * 3 + 1;

			cp1 =  edgePoints.get(ctrlPointIndex);

			cp2 =  edgePoints.get(ctrlPointIndex + 1);

			cp3 =  edgePoints.get(ctrlPointIndex + 2);

			edge.pathTo(cp1.x, cp1.y, cp2.x, cp2.y, cp3.x, cp3.y);
		}

		Point arrowheadBase;

		if(startPoint != null)
		{
			// Arrowhead base should be first point in the list
			arrowheadBase =  edgePoints.get(0);

			// Set the arrowhead, which goes from tip to base points
			edge.setArrowTail(arrowheadBase.x, arrowheadBase.y, startPoint.x, startPoint.y);

		}

		if(endPoint != null)
		{
			// Arrowhead base should be the last point in the list
			arrowheadBase = edgePoints.get(edgePoints.size() - 1);

			// Set the arrowhead, which goes from tip to base points
			edge.setArrowTail(arrowheadBase.x, arrowheadBase.y, endPoint.x, endPoint.y);
		}



	}




	/**
	 * Parses a point in the form of "x,y".
	 *
	 * @param data The <code>String</code> containing the point (in
	 *             format "x,y").
	 * @return A point that is located at (x, y)
	 */
	public Point parsePoint(String data)
	{
		Point p = null;

		int commaPos = data.indexOf(",");

		if(commaPos != -1)
		{
			int x = (int) Float.parseFloat(data.substring(0, commaPos));
			int y = (int) Float.parseFloat(data.substring(commaPos + 1, data.length()));

			p = new Point(x, y);
		}

		return p;
	}




	/**
	 * Parses a <code>String</code> that describes a <code>Rectangle</code>
	 *
	 * @param data The <code>String</code> in the format "x,y,w,h"
	 * @return A rectangle that is located at (x, y), has a width w and
	 *         a height h.
	 */
	public Rectangle parseRect(String data)
	{
		Rectangle rect = null;

		int start = 0;
		int commaPos = 0;

		commaPos = data.indexOf(',', start);

		rect = new Rectangle();

		rect.x = Integer.parseInt(data.substring(start, commaPos));

		start = commaPos + 1;

		commaPos = data.indexOf(',', start);

		rect.y = Integer.parseInt(data.substring(start, commaPos));

		start = commaPos + 1;

		commaPos = data.indexOf(',', start);

		rect.width = Integer.parseInt(data.substring(start, commaPos));

		start = commaPos + 1;

		rect.height = Integer.parseInt(data.substring(start, data.length()));


		return rect;
	}




	/**
	 * An inner class that is used to key the names of two
	 * <code>Nodes</code> (tail node and head node)
	 * to an edge.
	 */
	private class NodeEdgeKey
	{

		private String tail;
		private String head;

		public NodeEdgeKey(String tail, String head)
		{
			this.tail = tail;

			this.head = head;
		}

		public int hashCode()
		{
			int hashCode = tail.hashCode() * 37 + head.hashCode() * 17;

			return hashCode;
		}

		public boolean equals(Object obj)
		{
			if(obj == this)
			{
				return true;
			}

			if(getClass() != obj.getClass())
			{
				return false;
			}

			DotParameterSetter.NodeEdgeKey nek = (DotParameterSetter.NodeEdgeKey) obj;

			return nek.head.equals(this.head) && nek.tail.equals(this.tail);
		}

		public String toString()
		{
			return "NodeEdgeKey(" + tail + " -> " + head + "   hashCode: " + this.hashCode() + ")";
		}
	}


}

