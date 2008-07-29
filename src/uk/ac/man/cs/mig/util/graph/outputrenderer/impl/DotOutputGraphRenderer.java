package uk.ac.man.cs.mig.util.graph.outputrenderer.impl;

import org.apache.log4j.Logger;
import uk.ac.man.cs.mig.util.graph.graph.Edge;
import uk.ac.man.cs.mig.util.graph.graph.Graph;
import uk.ac.man.cs.mig.util.graph.graph.Node;
import uk.ac.man.cs.mig.util.graph.graph.impl.DefaultNode;
import uk.ac.man.cs.mig.util.graph.graph.impl.EllipticalNode;
import uk.ac.man.cs.mig.util.graph.model.GraphModel;
import uk.ac.man.cs.mig.util.graph.outputrenderer.GraphOutputRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.EdgeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.NodeLabelRenderer;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 16, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class DotOutputGraphRenderer implements GraphOutputRenderer
{
    private static Logger log = Logger.getLogger(DotOutputGraphRenderer.class);
    private static HashMap shapeMap;
    protected BufferedWriter writer;
    private NodeLabelRenderer labelRen;
    private EdgeLabelRenderer edgeLabelRen;
    private HashMap attributeMap;

    public static final String LAYOUT_DIRECTION = "rankdir";
    public static final String RANK_SPACING = "ranksep";
    public static final String SIBLING_SPACING = "nodesep";


    public DotOutputGraphRenderer(NodeLabelRenderer labelRen, EdgeLabelRenderer edgeLabelRen)
    {
        this.labelRen = labelRen;

        this.edgeLabelRen = edgeLabelRen;

        shapeMap = new HashMap();

        registerShapeMapping(DefaultNode.class, "box");

        registerShapeMapping(EllipticalNode.class, "ellipse");

        attributeMap = new HashMap();

        attributeMap.put(LAYOUT_DIRECTION, "LR");

        attributeMap.put(RANK_SPACING, "1.0");

        attributeMap.put(SIBLING_SPACING, "0.2");
    }

    /**
     * Adds a map to convert a class of node to a dot
     * shape name. (e.g. by default, Rectangle.class maps to "box")
     * @param nodeClass The class of the node to map to a dot shape name
     * @param dotShapeName The name of the dot shape.
     */
    public static void registerShapeMapping(Class nodeClass, String dotShapeName)
    {
        shapeMap.put(nodeClass, dotShapeName);
    }


    public void setRendererOption(String attribute, String value)
    {
        attributeMap.remove(attribute);

        attributeMap.put(attribute, value);
    }


    public synchronized void renderGraph(Graph graph, OutputStream os)
    {
        try {
            // dot likes UTF-8
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");

            writer = new BufferedWriter(osw);

            writeHeader(graph);

            // Write Nodes
            Node[] nodes = graph.getNodes();

            for(int i = 0; i < nodes.length; i++)
            {
                renderNode(nodes[i]);
            }

            // Write edges
            Edge[] edges = graph.getEdges();

            for(int i = 0; i < edges.length; i++)
            {
                renderEdge(edges[i]);
            }

            closeGraph();

            writer.flush();
        }
        catch (UnsupportedEncodingException e) {
            log.error(e);
        }
        catch(IOException e)    {
            log.error(e);
        }
    }


    protected void renderNode(Node node) throws IOException
    {
        writer.write('"');

        writer.write(labelRen.getLabel(node));

        writer.write('"');

        String shape = (String) shapeMap.get(node.getClass());

        if(shape != null)
        {
            writer.write(" [shape=");

            writer.write(shape);

            writer.write(", fixedsize=true, width=\"");

            writer.write(Double.toString(node.getSize().width / 72.0));

            writer.write("\", height=\"");

            writer.write(Double.toString(node.getSize().height / 72.0));

            writer.write("\"]");
        }

        writer.write(';');

        writer.newLine();
    }


    protected void renderEdge(Edge edge) throws IOException
    {
        writer.write('"');

        writer.write(labelRen.getLabel(edge.getTailNode()));

        writer.write('"');

        writer.write("->");

        writer.write('"');

        writer.write(labelRen.getLabel(edge.getHeadNode()));

        writer.write('"');

        String direction = "forward";

        int edgeDirection = edge.getDirection();

        if(edgeDirection == GraphModel.DIRECTION_NONE)
        {
            direction = "none";
        }
        else if(edgeDirection == GraphModel.DIRECTION_FORWARD)
        {
            direction = "forward";
        }
        else if(edgeDirection == GraphModel.DIRECTION_BACK)
        {
            direction = "back";
        }
        else if(edgeDirection == GraphModel.DIRECTION_BOTH)
        {
            direction = "both";
        }

        writer.write(" [dir=");

        writer.write(direction);

        String label = edgeLabelRen.getEdgeLabel(edge);

        if(label != null)
        {
            writer.write(", fontsize=\"10\", floatlabel=true, label=\"" + label + "\"");
        }

        writer.write("];");

        writer.newLine();
    }


    protected void writeHeader(Graph graph) throws IOException
    {
        writer.write("digraph g");

        writer.newLine();

        writer.write("{");

        // Check map for any specifiction of layout direction

        Iterator it = attributeMap.keySet().iterator();

        if (it.hasNext()){
            writer.newLine();

            writer.write("graph [");
            Object key;

            Object value;

            while(it.hasNext())
            {
                key = it.next();

                writer.write(key.toString());

                writer.write('=');

                value = attributeMap.get(key);

                writer.write(value.toString());

                writer.write(" ");

            }
            writer.write("]");
        }

        writer.newLine();

    }


    protected void closeGraph() throws IOException
    {
        writer.write("}");

        writer.newLine();
    }
}
