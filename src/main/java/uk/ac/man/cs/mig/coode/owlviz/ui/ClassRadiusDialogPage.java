package uk.ac.man.cs.mig.coode.owlviz.ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 12, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class ClassRadiusDialogPage extends JPanel {

	/**
     * 
     */
    private static final long serialVersionUID = -1546092672278277364L;
    private static final int MIN_VAL = 0;
	private static final int MAX_VAL = 10;
	private static final int DEFAULT_VALUE = 3;

	private int radius = DEFAULT_VALUE;

	private boolean showSuperSubOption;
	private JRadioButton supersAndSubs;
	private JRadioButton supersOnly;
	private JRadioButton subsOnly;


	public ClassRadiusDialogPage(boolean showSuperSubOptions) {
		this.showSuperSubOption = showSuperSubOptions;
		setLayout(new BorderLayout());
		add(createUI(), BorderLayout.NORTH);
	}


	private JComponent createUI() {
		JPanel holder = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints(0, 0, 2, 5, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
		                                                new Insets(7, 7, 7, 7), 0, 0);
		JLabel instructions = new JLabel();
		instructions.setText("<html><body>Please select a 'class radius'.<br>" + "The class radius specifies the number of<br>levels" + "of parents and/or children.</body></html>");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		holder.add(instructions, gbc);
		JLabel spinnerLabel = new JLabel("Class Radius: ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		holder.add(spinnerLabel, gbc);
		final JSpinner spinner = new JSpinner(new SpinnerNumberModel(DEFAULT_VALUE, MIN_VAL, MAX_VAL, 1));
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		holder.add(spinner, gbc);
		spinner.addChangeListener(new ChangeListener() {
			/**
			 * Invoked when the target of the listener has changed its state.
			 *
			 * @param e a ChangeEvent object
			 */
			public void stateChanged(ChangeEvent e) {
				radius = ((Integer) spinner.getValue()).intValue();
			}
		});
		if(showSuperSubOption == true) {
			// Parent/Child selection panel

			JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 12, 12));
			buttonPanel.setBorder(BorderFactory.createTitledBorder("Apply to:"));
			supersAndSubs = new JRadioButton("Parents and children");
			supersOnly = new JRadioButton("Parents only");
			subsOnly = new JRadioButton("Children only");
			buttonPanel.add(supersAndSubs);
			buttonPanel.add(supersOnly);
			buttonPanel.add(subsOnly);
			ButtonGroup bg = new ButtonGroup();
			bg.add(supersAndSubs);
			bg.add(supersOnly);
			bg.add(subsOnly);
			supersAndSubs.setSelected(true);
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.gridwidth = 2;
			gbc.gridheight = 1;
			holder.add(buttonPanel, gbc);
		}
		return holder;
	}


	public int getClassRadius() {
		return radius;
	}


	public int getSupersSubsOption() {
		if(showSuperSubOption == true) {
			if(supersAndSubs.isSelected()) {
				return ClassRadiusDialog.SUPERS_AND_SUBS;
			}
			else if(supersOnly.isSelected()) {
				return ClassRadiusDialog.SUPERS_ONLY;
			}
			else {
				return ClassRadiusDialog.SUBS_ONLY;
			}
		}
		else {
			return -1;
		}
	}
}
