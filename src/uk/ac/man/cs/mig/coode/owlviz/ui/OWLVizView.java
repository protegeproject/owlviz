package uk.ac.man.cs.mig.coode.owlviz.ui;

import org.apache.log4j.Logger;
import org.protege.editor.owl.ui.transfer.OWLObjectDataFlavor;
import org.protege.editor.owl.ui.view.cls.AbstractOWLClassViewComponent;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLObject;
import uk.ac.man.cs.mig.coode.owlviz.command.*;
import uk.ac.man.cs.mig.coode.owlviz.model.OWLClassGraphAssertedModel;
import uk.ac.man.cs.mig.coode.owlviz.model.OWLClassGraphInferredModel;
import uk.ac.man.cs.mig.coode.owlviz.ui.options.*;
import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.graph.event.GraphSelectionModelEvent;
import uk.ac.man.cs.mig.util.graph.event.GraphSelectionModelListener;
import uk.ac.man.cs.mig.util.graph.event.NodeClickedEvent;
import uk.ac.man.cs.mig.util.graph.event.NodeClickedListener;
import uk.ac.man.cs.mig.util.graph.export.ExportFormatManager;
import uk.ac.man.cs.mig.util.graph.export.impl.JPEGExportFormat;
import uk.ac.man.cs.mig.util.graph.export.impl.PNGExportFormat;
import uk.ac.man.cs.mig.util.graph.ui.GraphComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 5, 2004<br>
 * <br>
 * <p/> matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br>
 * <br>
 */
public class OWLVizView extends AbstractOWLClassViewComponent implements DropTargetListener, OWLVizViewI, ConfigurableOWLVizView {

    private static Logger logger = Logger.getLogger(OWLVizView.class);

    private GraphComponent assertedGraphComponent;
    private GraphComponent inferredGraphComponent;

    private OWLVizSelectionModel selectionModel;

    private HashSet closableTabs;

    private JTabbedPane tabbedPane;

    private Map<OWLVizGraphPanel, List<GraphComponent>> componentGroupMap;

    private HashSet<GraphComponent> graphComponents;

    public static final String DOT_PATH_PROPERTIES_KEY = "OWLViz.Dot.Path";

    private OWLClassGraphInferredModel inferredGraphModel;

    private OWLClassGraphAssertedModel assertedGraphModel;

    private OWLVizViewOptions options;


    public void initialiseClassView() throws Exception {
        loadProperties();

        options = new OWLVizViewOptions(); // options specific to this view

        selectionModel = new OWLVizSelectionModel();
        setupExportFormats();
        closableTabs = new HashSet();
        componentGroupMap = new HashMap<OWLVizGraphPanel, List<GraphComponent>>();
        graphComponents = new HashSet<GraphComponent>();
        createOWLVizTabUI();

        DropTarget dt = new DropTarget(this, this);
        dt.setActive(true);
    }

    protected OWLClass updateView(OWLClass selectedClass) {
        selectionModel.setSelectedClass(selectedClass);
        return selectedClass;
    }

    public void disposeView() {
        assertedGraphModel.dispose();
        inferredGraphModel.dispose();
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public OWLVizSelectionModel getSelectionModel() {
        return selectionModel;
    }

    public Collection<GraphComponent> getGraphComponents() {
        return getAllGraphComponents();
    }

    public Collection<GraphComponent> getAllGraphComponents() {
        return new ArrayList<GraphComponent>(graphComponents);
    }

    public GraphComponent getAssertedGraphComponent() {
        return assertedGraphComponent;
    }

    public GraphComponent getInferredGraphComponent() {
        return inferredGraphComponent;
    }

    // public void addExistentialPanel(OWLObjectProperty property) {
    // OWLModel model = (OWLModel) getKnowledgeBase();
    // OWLVizGraphPanel panel = new OWLVizGraphPanel(this,
    // new ExistentialGraphModel(model, property),
    // new AssertedClsHierarchyDisplayPanel(model));
    // tabbedPane.add(property.getBrowserText() + " hierarchy", panel);
    // closableTabs.add(panel);
    // componentGroupMap.put(panel,
    // Collections.singleton(panel.getGraphComponent()));
    // graphComponents.add(panel.getGraphComponent());
    // }

    protected void createOWLVizTabUI() {
        setLayout(new BorderLayout());
        // Create the tabbed pane
        tabbedPane = new JTabbedPane();
        add(tabbedPane);

        // /////////////////////////////////////////////////////////////////////
        //
        // Build the asserted subclass hierarchy tabs
        //
        // ////////////////////////////////////////////////////////////////////
        assertedGraphModel = new OWLClassGraphAssertedModel(
                getOWLModelManager());
        OWLVizGraphPanel assertedPanel = new OWLVizGraphPanel("Asserted model",
                                                              this, getOWLEditorKit(), assertedGraphModel);
        assertedGraphComponent = assertedPanel.getGraphComponent();
        setupListeners(assertedGraphComponent);
        tabbedPane.add(assertedPanel.getName(), assertedPanel);
        graphComponents.add(assertedGraphComponent);

        // /////////////////////////////////////////////////////////////////////
        //
        // Build the inferred subclass hierarchy tabs
        //
        // ////////////////////////////////////////////////////////////////////
        inferredGraphModel = new OWLClassGraphInferredModel(
                getOWLModelManager());
        OWLVizGraphPanel inferredPanel = new OWLVizGraphPanel("Inferred model",
                                                              this, getOWLEditorKit(), inferredGraphModel);
        inferredGraphComponent = inferredPanel.getGraphComponent();
        tabbedPane.add(inferredPanel.getName(), inferredPanel);
        graphComponents.add(inferredGraphComponent);

        // Group the asserted and inferred hierarchy tabs, so that operations
        // such as showing subclasses etc. are applied to both tabs.
        ArrayList<GraphComponent> list = new ArrayList<GraphComponent>();
        list.add(assertedGraphComponent);
        list.add(inferredGraphComponent);
        componentGroupMap.put(assertedPanel, list);
        componentGroupMap.put(inferredPanel, list);

        // Create the toolbar
        createToolBar(assertedGraphComponent.getController(),
                      assertedGraphComponent.getController());
    }

    protected void forceRepaint() {
        for (Iterator<GraphComponent> it = getGraphComponents().iterator(); it
                .hasNext();) {
            GraphComponent curGraphComponent = it.next();
            curGraphComponent.getGraphView().repaint();
        }
    }

    /**
     * Sets up the main listeners that are required by the tab, including
     * selection listeners, and knowledgebase listeners.
     */
    protected void setupListeners(final GraphComponent graphComponent) {
        /*
           * Listen to node clicks so that if there is a double click we can
           * display the Protege Info View
           */
        graphComponent.getGraphView().addNodeClickedListener(
                new NodeClickedListener() {
                    /**
                     * Invoked when a <code>Node</code> has been clicked by
                     * the mouse in the <code>GraphView</code>
                     *
                     * @param evt
                     *            The event associated with this action.
                     */
                    public void nodeClicked(NodeClickedEvent evt) {
                        // if(evt.getMouseEvent().getClickCount() == 2) {
                        // Object selObj = graphComponent.getSelectedObject();
                        // if(selObj != null) {
                        // if(selObj instanceof Instance) {
                        // showInstance((Instance) selObj);
                        // }
                        // }
                        // }
                    }
                });

        graphComponent
                .addGraphSelectionModelListener(new GraphSelectionModelListener() {
                    public void selectionChanged(GraphSelectionModelEvent event) {
                        Object selObj = event.getSource().getSelectedObject();
                        if (selObj instanceof OWLClass) {
                            selectionModel.setSelectedClass((OWLClass) selObj);
                            setSelectedEntity((OWLClass) selObj);
                        }
                    }
                });

        tabbedPane.addContainerListener(new ContainerListener() {
            public void componentAdded(ContainerEvent e) {
                // Do nothing
            }

            public void componentRemoved(ContainerEvent e) {
                // Remove components from hash sets etc.
                componentGroupMap.remove(e.getChild());
                closableTabs.remove(e.getChild());
                graphComponents.remove(e.getChild());
                OWLVizGraphPanel panel = (OWLVizGraphPanel) e.getChild();
                panel.dispose();
            }
        });
    }

    protected void createToolBar(Controller assertedController, Controller inferredController) {

        addAction(new ShowClassCommand(this, getOWLModelManager(),
                                       (Frame) SwingUtilities.getAncestorOfClass(Frame.class, this)),
                  "A", "A");
        addAction(new ShowSubclassesCommand(this), "A", "B");
        addAction(new ShowSuperclassesCommand(this), "A", "C");
        addAction(new ShowAllClassesCommand(this, getOWLModelManager()), "A",
                  "D");
        addAction(new HideClassCommand(this), "A", "E");
        addAction(new HideSubclassesCommand(this), "A", "F");
        addAction(new HideClassesPastRadiusCommand(assertedController,
                                                   inferredController), "A", "G");
        addAction(new HideAllClassesCommand(this), "A", "H");

        addAction(new ZoomOutCommand(this), "B", "A");
        addAction(new ZoomInCommand(this), "B", "B");
        addAction(new ExportCommand(this), "C", "A");
        addAction(new SetOptionsCommand(this, setupOptionsDialog()), "D", "A");
    }

    protected void setupExportFormats() {
        ExportFormatManager.addExportFormat(new PNGExportFormat());
        ExportFormatManager.addExportFormat(new JPEGExportFormat());
        // ExportFormatManager.addExportFormat(new SVGExportFormat());
        // ExportFormatManager.addExportFormat(new DotExportFormat((OWLModel)
        // getKnowledgeBase()));
    }

    protected OptionsDialog setupOptionsDialog() {
        Frame frame = null;
        OptionsDialog optionsDialog = new OptionsDialog(frame);
        optionsDialog.addOptionsPage(new ModeOptionsPage(getOptions()), "Mode");
        optionsDialog.addOptionsPage(new LayoutDirectionOptionsPage(
                assertedGraphComponent.getController(),
                inferredGraphComponent.getController()), "Layout");
        optionsDialog.addOptionsPage(new GlobalOptionsPage(getOWLEditorKit()), "Global");
        // optionsDialog.addOptionsPage(new DisplayOptionsPage(), "Display
        // Options");
        // optionsDialog.addOptionsPage(new UIOptionsPage(), "UI Options");
        return optionsDialog;
    }

    public void loadProperties() {
        // String path =
        // DotLayoutEngineProperties.getInstance().getDotProcessPath();
        // DotLayoutEngineProperties.getInstance().setDotProcessPath(
        // ApplicationProperties.getString(DOT_PATH_PROPERTIES_KEY, path));
    }

    public void saveProperties() {
        // ApplicationProperties.setString(DOT_PATH_PROPERTIES_KEY,
        // DotLayoutEngineProperties.getInstance().getDotProcessPath());
    }

    public void close() {
        // Save properties file
        // saveProperties();
        // super.close();
    }

    public void dragEnter(DropTargetDragEvent dtde) {
    }

    public void dragOver(DropTargetDragEvent dtde) {
    }

    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    public void dragExit(DropTargetEvent dte) {
    }

    public void drop(DropTargetDropEvent dtde) {
        logger.info("Drop: " + dtde);
        if (dtde
                .isDataFlavorSupported(OWLObjectDataFlavor.OWL_OBJECT_DATA_FLAVOR)) {
            try {
                List<OWLObject> objects = (List<OWLObject>) dtde
                        .getTransferable().getTransferData(
                        OWLObjectDataFlavor.OWL_OBJECT_DATA_FLAVOR);
                List<OWLClass> clses = new ArrayList<OWLClass>();
                for (OWLObject obj : objects) {
                    if (obj instanceof OWLClass) {
                        clses.add((OWLClass) obj);
                    }
                }

                if (!clses.isEmpty()) {
                    getAssertedGraphComponent().getVisualisedObjectManager()
                            .showObjects(clses.toArray());
                    dtde.dropComplete(true);
                }
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public OWLVizViewOptions getOptions() {
        return options;
    }
}
