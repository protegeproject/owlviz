package uk.ac.man.cs.mig.coode.owlviz.ui.options;

import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine.DotGraphLayoutEngine;

import javax.swing.*;
import java.awt.*;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Mar 1, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class LayoutDirectionOptionsPage extends OptionsPage {

	private Controller assertedController;

	private Controller inferredController;

	private JRadioButton leftToRightButton;

	private JRadioButton topToBottomButton;


	public LayoutDirectionOptionsPage(Controller assertedController,
	                                  Controller inferredController) {
		this.assertedController = assertedController;
		this.inferredController = inferredController;
		setLayout(new BorderLayout(14, 14));
		add(createUI());
	}


	protected JComponent createUI() {
		leftToRightButton = new JRadioButton("Left to Right", true);
		topToBottomButton = new JRadioButton("Top To Bottom", false);
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(leftToRightButton);
		buttonGroup.add(topToBottomButton);
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(leftToRightButton);
		box.add(topToBottomButton);
		box.setBorder(BorderFactory.createTitledBorder("Layout Direction"));
		return box;
	}


	public void validateOptions() {
		// Nothing to do!
	}


	public void applyOptions() {
		int layoutDirection;
		if(leftToRightButton.isSelected()) {
			layoutDirection = DotGraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT;
		}
		else {
			layoutDirection = DotGraphLayoutEngine.LAYOUT_TOP_TO_BOTTOM;
		}
		this.assertedController.getGraphLayoutEngine().setLayoutDirection(layoutDirection);
		this.inferredController.getGraphLayoutEngine().setLayoutDirection(layoutDirection);
	}


	public void updateInterface() {
		int layoutDirection = assertedController.getGraphLayoutEngine().getLayoutDirection();
		if(layoutDirection == DotGraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT) {
			leftToRightButton.setSelected(true);
		}
		else {
			topToBottomButton.setSelected(true);
		}
	}
}

