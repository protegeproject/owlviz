package uk.ac.man.cs.mig.coode.owlviz.ui.options;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
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
public class UIOptionsPage extends OptionsPage {

	/**
     * 
     */
    private static final long serialVersionUID = 4037503268140748846L;
    JCheckBox displayPopupCheckBox;
	JComboBox popupCombo;
	JSlider edgeBrightness;


	public UIOptionsPage() {
		setLayout(new BorderLayout());
		add(createUI());
	}


	protected JComponent createUI() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
		                                                GridBagConstraints.BOTH, new Insets(7, 7, 7, 7), 0, 0);
		panel.setBorder(BorderFactory.createTitledBorder("Popup"));
		displayPopupCheckBox = new JCheckBox("Display popup information");
		displayPopupCheckBox.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setComponentState();
			}
		});
		panel.add(displayPopupCheckBox, gbc);
		//PopupManager popupManager = PopupManager.getInstance("Asserted");
		popupCombo = new JComboBox();//popupManager.getPopupPages());
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(popupCombo, gbc);
		JComponent component = new JPanel();
		component.setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		component.add(panel, gbc);
		edgeBrightness = new JSlider(0, 100);
		edgeBrightness.setBorder(BorderFactory.createTitledBorder("Edge brightness"));
		edgeBrightness.setMajorTickSpacing(50);
		edgeBrightness.setMinorTickSpacing(10);
		edgeBrightness.setPaintTicks(true);
		edgeBrightness.setPaintTrack(true);
		edgeBrightness.setPaintLabels(true);
		gbc.gridx = 0;
		gbc.gridy = 1;
		component.add(edgeBrightness, gbc);
		return component;
	}


	public void setComponentState() {
		if(displayPopupCheckBox.isSelected()) {
			popupCombo.setEnabled(true);
		}
		else {
			popupCombo.setEnabled(false);
		}
	}


	public void applyOptions() {
//		PopupManager popupManager = PopupManager.getInstance("Asserted");
//		if(displayPopupCheckBox.isSelected() == false) {
//			popupManager.setCurrentPopupPage(null);
//		}
//		else {
//			OWLObjectPopupPage popupPage = (OWLObjectPopupPage) popupCombo.getSelectedItem();
//			popupManager.setCurrentPopupPage(popupPage);
//		}
	}


	public void updateInterface() {
		//PopupManager popupManager = PopupManager.getInstance("Asserted");
		Object curPopupPage = null;//popupManager.getCurrentPopupPage();
		if(curPopupPage == null) {
			displayPopupCheckBox.setSelected(false);
			popupCombo.setEnabled(false);
		}
		else {
			displayPopupCheckBox.setSelected(true);
			popupCombo.setEnabled(true);
			popupCombo.setSelectedItem(curPopupPage);
		}
		edgeBrightness.setValue((int) (100 * OWLVizProperties.getProperties().getDoubleProperty(
		        OWLVizProperties.EDGE_BRIGHTNESS, 0.5)));
		setComponentState();
	}


	public void validateOptions() {
	}
}

