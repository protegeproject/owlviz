package uk.ac.man.cs.mig.coode.owlviz.ui.exportwizard;

import uk.ac.man.cs.mig.util.graph.export.ExportFormat;
import uk.ac.man.cs.mig.util.graph.export.ExportFormatManager;
import uk.ac.man.cs.mig.util.wizard.Wizard;
import uk.ac.man.cs.mig.util.wizard.WizardPage;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 25, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class SelectFormatPage extends WizardPage {

	ExportFormat selectedFormat;

	JComboBox comboBox;
	JCheckBox antialiasedCheckBox;
	JCheckBox scaledCheckBox;
	JSpinner scaleSpinner;


	public SelectFormatPage() {
		super("SelectFormatPage");
		add(createUI());
	}


	/**
	 * Creates the UI for the format page.
	 *
	 * @return
	 */
	protected JComponent createUI() {
		JComponent component = new Box(BoxLayout.Y_AXIS);
		component.setAlignmentX(Box.LEFT_ALIGNMENT);
		int SEP_SIZE = 14;

		// Create and add the format combo panel
		component.add(createFormatComboPanel());


		// Add a separator
		component.add(Box.createVerticalStrut(SEP_SIZE));


		// Create and add the antialiased option panel
		component.add(createAntialiasedOptionPanel());


		// Add a sparator
		component.add(Box.createVerticalStrut(SEP_SIZE));


		// Create the scaling options panel
		component.add(createScalingOptionPanel());
		setControlState();
		return component;
	}


	/**
	 * Creates a panel that contains a combo box with the available
	 * export formats in it, and a label, which prompts the user
	 * to select a format.
	 *
	 * @return The component with the requisite components in it.
	 */
	protected JComponent createFormatComboPanel() {
		// Set up a panel with the label and combo box on it

		JPanel comboBoxPanel = new JPanel(new BorderLayout(14, 14));
		comboBoxPanel.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		JLabel label = new JLabel("<html><body>Please select the export format that" + " you want to use.");
		comboBoxPanel.add(label, BorderLayout.NORTH);
		ExportFormat[] exportFormats = ExportFormatManager.getExportFormats();
		comboBox = new JComboBox(exportFormats);
		comboBox.setRenderer(new ExportFormatRenderer());
		comboBox.setSelectedIndex(0);
		selectedFormat = exportFormats[0];
		comboBox.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 */
			public void actionPerformed(ActionEvent e) {
				selectedFormat = (ExportFormat) comboBox.getSelectedItem();
				setControlState();
				if(getWizard() != null) {
					setButtonState(getWizard());
				}
			}
		});
		comboBoxPanel.add(comboBox, BorderLayout.SOUTH);
		return comboBoxPanel;
	}


	/**
	 * Creates a panel that provides the option to decide
	 * whether the export should be antialiased or not.
	 *
	 * @return A component with the requisite components in it.
	 */
	protected JComponent createAntialiasedOptionPanel() {
		// Panel with the antialiased checkbox on it.

		JPanel antialiasedPanel = new JPanel(new BorderLayout());
		antialiasedCheckBox = new JCheckBox("Antialiased");
		antialiasedCheckBox.setToolTipText("Antialiasing can make the image look smoother.");
		antialiasedCheckBox.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 */
			public void actionPerformed(ActionEvent e) {
				if(selectedFormat != null) {
					selectedFormat.setAntialiased(antialiasedCheckBox.isSelected());
				}
			}
		});
		antialiasedPanel.add(antialiasedCheckBox, BorderLayout.WEST);
		return antialiasedPanel;
	}


	/**
	 * Creates a panel that allows scaling options to be set
	 * for the export format.
	 *
	 * @return A panel with the requisite components in it.
	 */
	protected JComponent createScalingOptionPanel() {
		// Create a panel with the scaling control on it

		JComponent scalingPanel = new JPanel(new BorderLayout(14, 14));
		scaledCheckBox = new JCheckBox(new AbstractAction("Scale export") {
			/**
			 * Invoked when an action occurs.
			 */
			public void actionPerformed(ActionEvent e) {
				setControlState();
			}
		});
		scaledCheckBox.setToolTipText("The percentage that the export will be scaled to.");
		scalingPanel.add(scaledCheckBox, BorderLayout.WEST);
		scaleSpinner = new JSpinner(new SpinnerNumberModel(100, 10, 1000, 10));
		scaleSpinner.addChangeListener(new ChangeListener() {
			/**
			 * Invoked when the target of the listener has changed its state.
			 *
			 * @param e a ChangeEvent object
			 */
			public void stateChanged(ChangeEvent e) {
				if(selectedFormat != null) {
					selectedFormat.setScale(((Integer) scaleSpinner.getValue()).intValue());
				}
			}
		});
		scalingPanel.add(scaleSpinner);
		return scalingPanel;
	}


	/**
	 * Sets the control state, which depends on the format selected,
	 * and the options for the selected format.
	 */
	protected void setControlState() {
		// Check that a format has been selected.
		if(selectedFormat == null) {
			antialiasedCheckBox.setEnabled(false);
			scaledCheckBox.setEnabled(false);
		}
		else {
			// Check to see if scaled export is supported
			// if not, disable the scale checkbox and spinner,
			// otherwise enable these controls.
			if(selectedFormat.supportsScaledOutput() == true) {
				scaledCheckBox.setEnabled(true);

				// Update the scaling percentage to reflect that
				// set in the ExportFormat.
				scaleSpinner.setValue(new Integer((int) selectedFormat.getScale()));

				// Enable/Disable the spinner depending upon
				// whether or not export is being scaled.
				if(scaledCheckBox.isSelected()) {
					scaleSpinner.setEnabled(true);
				}
				else {
					scaleSpinner.setEnabled(false);
				}
			}
			else {
				scaledCheckBox.setEnabled(false);
				scaleSpinner.setEnabled(false);
			}

			// Check to see if the format is a raster format
			// If so, it might support antialiasing.
			if(selectedFormat.isRasterFormat() == true) {
				// If antialiasing is supported, enable the
				// antialias check box and update it's state,
				// otherwise disable the checkbox.
				if(selectedFormat.supportsAntialiasing() == true) {
					antialiasedCheckBox.setSelected(selectedFormat.getAntialiased());
					antialiasedCheckBox.setEnabled(true);
				}
				else {
					antialiasedCheckBox.setSelected(false);
					antialiasedCheckBox.setEnabled(false);
				}
			}
			else {
				antialiasedCheckBox.setSelected(false);
				antialiasedCheckBox.setEnabled(false);
			}
		}
	}


	/**
	 * Retrieves the selected export format (the
	 * format that is selected in the combobox)
	 *
	 * @return The selected <code>ExportFormat</code>
	 */
	public ExportFormat getSelectedExportFormat() {
		return selectedFormat;
	}


	/**
	 * When this <code>WizardPage</code> is selected,
	 * we need to update the state of the controls to
	 * repflect the data that the page is displaying
	 *
	 * @param w The <code>Wizard</code>
	 */
	public void pageSelected(Wizard w) {
		setControlState();
		setButtonState(w);
	}


	/**
	 * Sets the button state on the <code>Wizard</code>
	 * which enables/disables the Next button depending
	 * upon whether a format is selected or not.
	 *
	 * @param w The <code>Wizard</code>
	 */
	protected void setButtonState(Wizard w) {
		if(selectedFormat != null) {
			w.setNextButtonEnabled(true);
		}
		else {
			w.setNextButtonEnabled(true);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////
	//
	// Inner class that provides a renderer which is capable of
	// rendering ExportFormats
	//
	//////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////

	/**
	 * A class that extends the DefaultListCellRenderer to render
	 * an <code>ExportFormat</code> in a list or combobox.
	 */
	private class ExportFormatRenderer extends DefaultListCellRenderer {

		public Component getListCellRendererComponent(JList list,
		                                              Object value,
		                                              int index,
		                                              boolean isSelected,
		                                              boolean cellHasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			label.setText(((ExportFormat) value).getFormatName() + " [" + ((ExportFormat) value).getFormatDescription() + "]");
			return label;
		}
	}
}

