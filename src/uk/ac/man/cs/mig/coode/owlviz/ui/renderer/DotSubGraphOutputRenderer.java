package uk.ac.man.cs.mig.coode.owlviz.ui.renderer;

import uk.ac.man.cs.mig.util.graph.graph.Edge;
import uk.ac.man.cs.mig.util.graph.graph.Graph;
import uk.ac.man.cs.mig.util.graph.graph.Node;
import uk.ac.man.cs.mig.util.graph.outputrenderer.impl.DotOutputGraphRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.EdgeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.NodeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.impl.DefaultEdgeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.impl.DefaultNodeLabelRenderer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.protege.editor.owl.model.OWLModelManager;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Apr 2, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 * <p/>
 * <p/>
 * This renderer is similar to the default renderer, except
 * it is specific to OWLClasses.  If the option to separate
 * nodes into subgroups depending on their namespace is actuve
 * then a graph containing multiple subgraphs is rendered.
 */
public class DotSubGraphOutputRenderer extends DotOutputGraphRenderer {

    private OWLModelManager model;
    private static boolean groupByNameSpace = false;


    public DotSubGraphOutputRenderer(OWLModelManager model,
                                     NodeLabelRenderer nodeLabelRen,
                                     EdgeLabelRenderer edgeLabelRen) {
        super(nodeLabelRen, edgeLabelRen);
        this.model = model;
    }


    public DotSubGraphOutputRenderer(OWLModelManager model) {
        super(new DefaultNodeLabelRenderer(), new DefaultEdgeLabelRenderer());
        this.model = model;
    }


    public static void setGroupByNameSpace(boolean b) {
        groupByNameSpace = b;
    }


    public static boolean getGroupByNameSpace() {
        return groupByNameSpace;
    }


    public synchronized void renderGraph(Graph graph,
                                         OutputStream os) {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        writer = new BufferedWriter(osw);
        try {
            writeHeader(graph);
            //Iterator it = model.getNamespaceManager().getPrefixes().iterator();

            // Need multiple passes to separate the nodes into different
            // subgraphs depending on their namespace
            // Write Nodes


            Node[] nodes = graph.getNodes();
            if(groupByNameSpace == true) {
//                String curPrefix;
//                String curInstPrefix;
//                int subgraphCounter = 0;
//                Node node;
//                Collection curNameSpaceNodes = new ArrayList(nodes.length);
//                while(it.hasNext()) {
//                    curPrefix = (String) it.next();
//                    for(int i = 0; i < nodes.length; i++) {
//                        node = nodes[i];
//                        if(node.getUserObject() instanceof RDFResource) {
//                            curInstPrefix = ((RDFResource) node.getUserObject()).getNamespacePrefix();
//                            if(curInstPrefix != null) {
//                                if(curInstPrefix.equals(curPrefix)) {
//                                    curNameSpaceNodes.add(node);
//                                }
//                            }
//                        }
//                    }
//                    if(curNameSpaceNodes.size() > 0) {
//                        writer.write("\nsubgraph cluster" + subgraphCounter);
//                        writer.write(" {\n");
//                        Iterator curNameSpaceNodeIt = curNameSpaceNodes.iterator();
//                        while(curNameSpaceNodeIt.hasNext()) {
//                            renderNode((Node) curNameSpaceNodeIt.next());
//                        }
//                        writer.write("\n}\n");
//                    }
//                    subgraphCounter++;
//                }
//                curNameSpaceNodes.removeAll(curNameSpaceNodes);
//
//                // Add nodes that don't have a namespace prefix to a new
//                // cluster
//
//                for(int i = 0; i < nodes.length; i++) {
//                    node = nodes[i];
//                    if(node.getUserObject() instanceof RDFResource) {
//                        curInstPrefix = ((RDFResource) node.getUserObject()).getNamespacePrefix();
//                        if(curInstPrefix == null) {
//                            curNameSpaceNodes.add(node);
//                        }
//                    }
//                }
//                if(curNameSpaceNodes.size() > 0) {
//                    writer.write("\nsubgraph cluster" + subgraphCounter);
//                    writer.write(" {\n");
//                    Iterator curNameSpaceNodeIt = curNameSpaceNodes.iterator();
//                    while(curNameSpaceNodeIt.hasNext()) {
//                        renderNode((Node) curNameSpaceNodeIt.next());
//                    }
//                    writer.write("\n}\n");
//                }
            }
            else {
                // Write nodes without sub graph

                for(int i = 0; i < nodes.length; i++) {
                    renderNode(nodes[i]);
                }
            }

            // Write edges

            Edge[] edges = graph.getEdges();
            for(int i = 0; i < edges.length; i++) {
                renderEdge(edges[i]);
            }
            closeGraph();
            writer.flush();
        }
        catch(IOException ioEx) {
            ioEx.printStackTrace();
        }
    }
}

