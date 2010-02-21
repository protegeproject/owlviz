package uk.ac.man.cs.mig.coode.owlviz.ui.options;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Mar 2, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class DisplayOptionsPage extends OptionsPage {

	/**
     * 
     */
    private static final long serialVersionUID = -3598573214199342902L;
    private JCheckBox displayDisjointInfo;
	private JCheckBox groupByNameSpace;
	private JCheckBox displayAnonymousClasses;
	private JCheckBox displayIndividuals;
	private JCheckBox displayIsALabels;

    private OWLVizViewOptions options;


    public DisplayOptionsPage(OWLVizViewOptions options) {
        this.options = options;

        setLayout(new BorderLayout(12, 12));
		add(createUI());
		displayAnonymousClasses.setEnabled(false);
		displayIndividuals.setEnabled(false);
	}


	protected JComponent createUI() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0;
		gbc.weighty = 100;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(7, 7, 7, 7);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		groupByNameSpace = new JCheckBox("Group classes by namespace");
		panel.add(groupByNameSpace, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		displayDisjointInfo = new JCheckBox("Display disjoint class information");
		panel.add(displayDisjointInfo, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		displayAnonymousClasses = new JCheckBox("Display restrictions (and other anonymous classes)");
		displayAnonymousClasses.addChangeListener(new ChangeListener() {
			/**
			 * Invoked when the target of the listener has changed its state.
			 *
			 * @param e a ChangeEvent object
			 */
			public void stateChanged(ChangeEvent e) {
				if(displayAnonymousClasses.isSelected()) {
					displayIndividuals.setSelected(true);
				}
			}
		});
		panel.add(displayAnonymousClasses, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		displayIndividuals = new JCheckBox("Display individuals");
		panel.add(displayIndividuals, gbc);
		gbc.gridx = 0;
		gbc.gridy = 4;
		displayIsALabels = new JCheckBox("Display is-a labels");
		panel.add(displayIsALabels, gbc);
		JPanel component = new JPanel(new BorderLayout());
		component.add(panel, BorderLayout.NORTH);
		return component;
	}


	public void updateInterface() {
		displayDisjointInfo.setSelected(options.isDisplayDisjointClassIndicator());
		displayIndividuals.setSelected(options.isDisplayIndividuals());
		displayAnonymousClasses.setSelected(options.isDisplayAnonymousClasses());
		groupByNameSpace.setSelected(options.isGroupClassesByNameSpace());
		displayIsALabels.setSelected(options.isDisplayIsALabels());
	}


	public void validateOptions() {
	}


	public void applyOptions() {
		options.setDisplayDisjointClassIndicator(displayDisjointInfo.isSelected());
		options.setDisplayAnonymousClasses(displayAnonymousClasses.isSelected());
		options.setGroupClassesByNameSpace(groupByNameSpace.isSelected());
		options.setDisplayIndividuals(displayIndividuals.isSelected());
		options.setDisplayIsALabels(displayIsALabels.isSelected());
	}
}

