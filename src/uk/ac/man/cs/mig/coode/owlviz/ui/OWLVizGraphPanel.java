package uk.ac.man.cs.mig.coode.owlviz.ui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.protege.editor.owl.OWLEditorKit;
import org.protege.editor.owl.model.OWLModelManager;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLEntity;

import uk.ac.man.cs.mig.coode.owlviz.command.HideAllClassesCommand;
import uk.ac.man.cs.mig.coode.owlviz.command.HideClassCommand;
import uk.ac.man.cs.mig.coode.owlviz.command.HideSubclassesCommand;
import uk.ac.man.cs.mig.coode.owlviz.command.ShowClassCommand;
import uk.ac.man.cs.mig.coode.owlviz.command.ShowSubclassesCommand;
import uk.ac.man.cs.mig.coode.owlviz.command.ShowSuperclassesCommand;
import uk.ac.man.cs.mig.coode.owlviz.ui.popup.OWLObjectPopupProvider;
import uk.ac.man.cs.mig.coode.owlviz.ui.renderer.OWLClsEdgeRenderer;
import uk.ac.man.cs.mig.coode.owlviz.ui.renderer.OWLClsNodeLabelRenderer;
import uk.ac.man.cs.mig.coode.owlviz.ui.renderer.OWLClsNodeRenderer;
import uk.ac.man.cs.mig.util.graph.event.GraphSelectionModelEvent;
import uk.ac.man.cs.mig.util.graph.event.GraphSelectionModelListener;
import uk.ac.man.cs.mig.util.graph.event.NodeClickedEvent;
import uk.ac.man.cs.mig.util.graph.event.NodeClickedListener;
import uk.ac.man.cs.mig.util.graph.model.GraphModel;
import uk.ac.man.cs.mig.util.graph.ui.GraphComponent;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jul 7, 2005<br>
 * <br>
 * <p/> matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br>
 * <br>
 */
public class OWLVizGraphPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2768708431965401557L;

	private GraphComponent graphComponent;

	private OWLVizViewI view;

	private JPopupMenu popupMenu;

	private OWLEditorKit owlEditorKit;

	private OWLVizSelectionListener owlVizSelectionListener;

	public OWLVizGraphPanel(OWLVizViewI view, OWLEditorKit owlEditorKit,
			GraphModel graphModel) {
		this("Unnamed", view, owlEditorKit, graphModel);
	}

	public OWLVizGraphPanel(String name, OWLVizViewI view,
			OWLEditorKit owlEditorKit, GraphModel graphModel) {
		this.view = view;
		this.owlEditorKit = owlEditorKit;
		graphComponent = new GraphComponent(name);
		graphComponent.setGraphModel(graphModel);
		graphComponent.setNodeLabelRenderer(new OWLClsNodeLabelRenderer(
				getOWLModelManager()));
		graphComponent.setNodeRenderer(new OWLClsNodeRenderer(graphComponent
				.getController(), graphComponent.getVisualisedObjectManager(),
				new OWLClsNodeLabelRenderer(getOWLModelManager()),
				getOWLModelManager()));
		graphComponent.setEdgeRenderer(new OWLClsEdgeRenderer(graphComponent
				.getController()));
		JPanel panel = new JPanel(new BorderLayout());

		// Create the thumbnail splitter that contains the treePanel
		// and thumbnail
		// JSplitPane thumbnailSplitter = new
		// JSplitPane(JSplitPane.VERTICAL_SPLIT, false);
		// thumbnailSplitter.add(treePanel, JSplitPane.TOP);
		// thumbnailSplitter.add(new DefaultThumbnailView(graphComponent),
		// JSplitPane.BOTTOM);
		// JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
		// false);
		// splitter.add(graphComponent, JSplitPane.RIGHT);
		// splitter.add(thumbnailSplitter, JSplitPane.LEFT);
		panel.add(graphComponent);
		OWLObjectPopupProvider popupProvider = new OWLObjectPopupProvider(name,
				owlEditorKit);
		graphComponent.setPopupProvider(popupProvider);
		createPopupMenu();
		setupListeners();

		setLayout(new BorderLayout());
		add(panel);
	}

	protected void createPopupMenu() {
		popupMenu = new JPopupMenu();
		popupMenu.add(new ShowClassCommand(view, getOWLModelManager(),
				(Frame) SwingUtilities.getRoot(this)));
		popupMenu.add(new ShowSubclassesCommand(view));
		popupMenu.add(new ShowSuperclassesCommand(view));
		popupMenu.add(new HideClassCommand(view));
		popupMenu.add(new HideSubclassesCommand(view));
		popupMenu.add(new HideAllClassesCommand(view));
		popupMenu.addSeparator();
	}

	public String getName() {
		return graphComponent.getName();
	}

	public GraphComponent getGraphComponent() {
		return graphComponent;
	}

	protected void setupListeners() {

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
						if (evt.getMouseEvent().getClickCount() == 2) {
							Object selObj = graphComponent.getSelectedObject();
							if (selObj != null) {
								if (selObj instanceof OWLEntity) {
									// view.showInstance((Instance) selObj);
								}
							}
						}
					}
				});
		graphComponent
				.addGraphSelectionModelListener(new GraphSelectionModelListener() {
					public void selectionChanged(GraphSelectionModelEvent event) {
						view.getSelectionModel().setSelectedClass(
								(OWLClass) event.getSource()
										.getSelectedObject());
					}
				});

		owlVizSelectionListener = new OWLVizSelectionListener() {
			public void selectionChanged(OWLVizSelectionModel model) {
				if (model.getSelectedClass() != null) {
					getGraphComponent().getGraphSelectionModel()
							.setSelectedObject(model.getSelectedClass());
				}
			}
		};

		view.getSelectionModel().addSelectionListener(owlVizSelectionListener);

		getGraphComponent().getGraphView().addMouseListener(new MouseAdapter() {
			/**
			 * Invoked when a mouse button has been pressed on a component.
			 */
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showPopupMenu(e);
				}
			}

			/**
			 * Invoked when a mouse button has been released on a component.
			 */
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showPopupMenu(e);
				}
			}

			private void showPopupMenu(MouseEvent e) {
				popupMenu.show(getGraphComponent().getGraphView(),
						e.getPoint().x, e.getPoint().y);
			}
		});
	}

	public void dispose() {
		view.getSelectionModel().removeSelectionListener(
				owlVizSelectionListener);
	}

	protected OWLModelManager getOWLModelManager() {
		return owlEditorKit.getModelManager();
	}
}
