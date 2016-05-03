package org.coode.owlviz.ui.options;

import org.coode.owlviz.util.graph.controller.Controller;
import org.coode.owlviz.util.graph.layout.dotlayoutengine.DotGraphLayoutEngine;
import org.coode.owlviz.util.graph.layout.dotlayoutengine.DotLayoutEngineProperties;
import org.protege.editor.core.ui.preferences.PreferencesLayoutPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


    private static final Logger logger = LoggerFactory.getLogger(LayoutDirectionOptionsPage.class);

    private Controller assertedController;

    private Controller inferredController;

    private JRadioButton leftToRightButton;

    private JRadioButton topToBottomButton;


    public LayoutDirectionOptionsPage(Controller assertedController,
                                      Controller inferredController) {
        this.assertedController = assertedController;
        this.inferredController = inferredController;
        setLayout(new BorderLayout(14, 14));
        add(createUI(), BorderLayout.NORTH);
        updateInterface();
    }


    protected JComponent createUI() {
        PreferencesLayoutPanel layoutPanel = new PreferencesLayoutPanel();
        leftToRightButton = new JRadioButton("Left to Right");
        topToBottomButton = new JRadioButton("Top To Bottom");
        ButtonGroup bg = new ButtonGroup();
        bg.add(leftToRightButton);
        bg.add(topToBottomButton);
        layoutPanel.addGroup("Layout direction");
        layoutPanel.addGroupComponent(leftToRightButton);
        layoutPanel.addGroupComponent(topToBottomButton);
        return layoutPanel;
    }


    public void validateOptions() {
        // Nothing to do!
    }


    public void applyOptions() {
        int layoutDirection;
        if (leftToRightButton.isSelected()) {
            layoutDirection = DotGraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT;
            logger.debug("[OWLViz] Layout Direction set LEFT to RIGHT");
        }
        else {
            layoutDirection = DotGraphLayoutEngine.LAYOUT_TOP_TO_BOTTOM;
            logger.debug("[OWLViz] Layout Direction set TOP to BOTTOM");
        }
        this.assertedController.getGraphLayoutEngine().setLayoutDirection(layoutDirection);
        this.inferredController.getGraphLayoutEngine().setLayoutDirection(layoutDirection);
    }


    public void updateInterface() {
        int layoutDirection = assertedController.getGraphLayoutEngine().getLayoutDirection();
        if (layoutDirection == DotGraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT) {
            leftToRightButton.setSelected(true);
        }
        else {
            topToBottomButton.setSelected(true);
        }
    }
}

