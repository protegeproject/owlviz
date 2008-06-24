package uk.ac.man.cs.mig.coode.owlviz.ui.exportwizard;

import uk.ac.man.cs.mig.coode.owlviz.ui.OWLVizView;
import uk.ac.man.cs.mig.util.graph.ui.GraphComponent;
import uk.ac.man.cs.mig.util.wizard.WizardPage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Mar 3, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class SpecifyHierarchyPage extends WizardPage {

	private OWLVizView view;
	private JList tabList;

	public static final int ASSERTED_HIERARCHY = 0;
	public static final int INFERRED_HIERARCHY = 1;


	public SpecifyHierarchyPage(OWLVizView view) {
		super("SpecifyHierarchyPage");
		this.view = view;
		setLayout(new BorderLayout());
		add(createUI());
	}


	protected JComponent createUI() {
		JPanel panel = new JPanel(new BorderLayout(12, 12));
		JLabel label = new JLabel("<html><body>Please select the hierarchy that you would<br>" + "like to export.</body></html>");
		panel.add(label, BorderLayout.NORTH);
        List<GraphComponent> sortedGraphs = new ArrayList<GraphComponent>(view.getGraphComponents());
        Collections.sort(sortedGraphs, new Comparator<GraphComponent>(){
            public int compare(GraphComponent graphComponent, GraphComponent graphComponent1) {
                return graphComponent.getName().compareToIgnoreCase(graphComponent1.getName());
            }
        });
        tabList = new JList(sortedGraphs.toArray());
        tabList.setCellRenderer(new DefaultListCellRenderer(){
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean sel, boolean focused) {
                return super.getListCellRendererComponent(list, ((GraphComponent)value).getName(), index, sel, focused);
            }
        });
        tabList.setSelectedIndex(0);
		panel.add(new JScrollPane(tabList));
		return panel;
	}

	public GraphComponent getSelectedGraphComponent() {
		return (GraphComponent)tabList.getSelectedValue();
	}
}

