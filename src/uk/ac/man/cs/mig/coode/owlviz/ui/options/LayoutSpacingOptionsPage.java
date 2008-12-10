package uk.ac.man.cs.mig.coode.owlviz.ui.options;

import uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine.DotGraphLayoutEngine;
import uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine.DotLayoutEngineProperties;

import javax.swing.*;
import java.awt.*;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Mar 2, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * @deprecated
 */
public class LayoutSpacingOptionsPage extends OptionsPage {

	DotGraphLayoutEngine assertedLayoutEngine;
	DotGraphLayoutEngine inferredLayoutEngine;

	JSpinner rankSpacing;
	JSpinner siblingSpacing;


	public LayoutSpacingOptionsPage(DotGraphLayoutEngine assertedLayoutEngine,
	                                DotGraphLayoutEngine inferredLayoutEngine) {
		this.assertedLayoutEngine = assertedLayoutEngine;
		this.inferredLayoutEngine = inferredLayoutEngine;
		setLayout(new BorderLayout(12, 12));
		add(createUI());
	}


	protected JComponent createUI() {
		JPanel holder = new JPanel(new BorderLayout(12, 12));
		JPanel component = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		component.setLayout(layout);
		JLabel rankSpacingLabel = new JLabel("Rank spacing: ");
		JLabel siblingSpacingLabel = new JLabel("Sibling spacing: ");
		rankSpacing = new JSpinner(new SpinnerNumberModel(1.0, 0.05, 10.0, 0.05));
		siblingSpacing = new JSpinner(new SpinnerNumberModel(0.2, 0.01, 10.0, 0.01));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.insets = new Insets(6, 6, 6, 6);
		gbc.weightx = 0;
		gbc.weighty = 0;
		addComponent(component, rankSpacingLabel, gbc, 0, 0, 1, 1);
		addComponent(component, rankSpacing, gbc, 1, 0, 1, 1);
		addComponent(component, siblingSpacingLabel, gbc, 0, 1, 1, 1);
		addComponent(component, siblingSpacing, gbc, 1, 1, 1, 1);
		holder.setBorder(BorderFactory.createTitledBorder("Spacing"));
		holder.add(component, BorderLayout.WEST);
		return holder;
	}


	protected void addComponent(JComponent container,
	                            JComponent c,
	                            GridBagConstraints gbc,
	                            int x,
	                            int y,
	                            int width,
	                            int height) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		container.add(c, gbc);
	}


	public void updateInterface() {
		DotLayoutEngineProperties dotLayoutEngineProperties = DotLayoutEngineProperties.getInstance();
		Double dRankSpacing = new Double(dotLayoutEngineProperties.getRankSpacing());
		Double dSiblingSpacing = new Double(dotLayoutEngineProperties.getSiblingSpacing());
		rankSpacing.setValue(dRankSpacing);
		siblingSpacing.setValue(dSiblingSpacing);
	}


	public void validateOptions() {
	}


	public void applyOptions() {
		Double dRankSpacing = (Double) rankSpacing.getValue();
		Double dSiblingSpacing = (Double) siblingSpacing.getValue();
		DotLayoutEngineProperties dotLayoutEngineProperties = DotLayoutEngineProperties.getInstance();
		dotLayoutEngineProperties.setRankSpacing(dRankSpacing.doubleValue());
		dotLayoutEngineProperties.setSiblingSpacing(dSiblingSpacing.doubleValue());
	}
}