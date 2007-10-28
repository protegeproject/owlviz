package uk.ac.man.cs.mig.coode.owlviz.ui.exportwizard;

import uk.ac.man.cs.mig.util.wizard.WizardPage;
import uk.ac.man.cs.mig.util.graph.ui.GraphComponent;
import uk.ac.man.cs.mig.coode.owlviz.ui.OWLVizView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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

	private JRadioButton assertedHierarchyButton;
	private JRadioButton inferredHierarchyButton;

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
		ArrayList<String> names = new ArrayList<String>();
		for(int i = 0; i < view.getTabbedPane().getTabCount(); i++) {
			names.add(view.getTabbedPane().getTitleAt(i));
		}
		tabList = new JList(names.toArray());
		tabList.setSelectedIndex(0);
//		Box box = new Box(BoxLayout.Y_AXIS);
//		box.add(assertedHierarchyButton = new JRadioButton("Asserted hierarchy", true));
//		box.add(Box.createVerticalStrut(12));
//		box.add(inferredHierarchyButton = new JRadioButton("Inferred hierarchy"));
//		ButtonGroup buttonGroup = new ButtonGroup();
//		buttonGroup.add(assertedHierarchyButton);
//		buttonGroup.add(inferredHierarchyButton);
//		box.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
//		panel.add(box);
		panel.add(new JScrollPane(tabList));
		return panel;
	}

	public GraphComponent getSelectedGraphComponent() {
		int index = tabList.getSelectedIndex();
		if(index > -1) {
			return (GraphComponent) view.getGraphComponents().toArray()[index];
		}
		return view.getAssertedGraphComponent();
	}
}

