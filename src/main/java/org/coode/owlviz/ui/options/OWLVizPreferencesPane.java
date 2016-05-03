package org.coode.owlviz.ui.options;

import com.google.common.collect.Sets;
import org.coode.owlviz.ui.OWLVizPreferences;
import org.coode.owlviz.util.graph.layout.dotlayoutengine.DotLayoutEngineProperties;
import org.protege.editor.core.ui.preferences.PreferencesLayoutPanel;
import org.protege.editor.core.ui.util.UIUtil;
import org.protege.editor.owl.ui.preferences.OWLPreferencesPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Set;
/*
* Copyright (C) 2007, University of Manchester
*
* Modifications to the initial code base are copyright of their
* respective authors, or their employers as appropriate.  Authorship
* of the modifications may be determined from the ChangeLog placed at
* the end of this file.
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License as published by the Free Software Foundation; either
* version 2.1 of the License, or (at your option) any later version.

* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* Lesser General Public License for more details.

* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/

/**
 * Author: drummond<br>
 * http://www.cs.man.ac.uk/~drummond/<br><br>
 * <p/>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Dec 10, 2008<br><br>
 */
public class OWLVizPreferencesPane extends OWLPreferencesPanel {


    private final JTextField pathField = new JTextField(50);

    private JSpinner rankSpacing;

    private JSpinner siblingSpacing;

    private ModeOptions modeOptions;



    public void applyChanges() {
        DotLayoutEngineProperties.getInstance().setDotProcessPath(pathField.getText());

        Double dRankSpacing = (Double) rankSpacing.getValue();
        Double dSiblingSpacing = (Double) siblingSpacing.getValue();
        DotLayoutEngineProperties dotLayoutEngineProperties = DotLayoutEngineProperties.getInstance();
        dotLayoutEngineProperties.setRankSpacing(dRankSpacing);
        dotLayoutEngineProperties.setSiblingSpacing(dSiblingSpacing);

        OWLVizPreferences.getInstance().setTrackingModeDefault(modeOptions.isTrackerMode());
        OWLVizPreferences.getInstance().setDefaultTrackerRadius(modeOptions.getTrackerRadius());
    }


    public void initialise() throws Exception {
        setLayout(new BorderLayout());
        PreferencesLayoutPanel layoutPanel = new PreferencesLayoutPanel();
        add(layoutPanel, BorderLayout.NORTH);

        pathField.setText(DotLayoutEngineProperties.getInstance().getDotProcessPath());

        JButton browseButton = new JButton("Browse...");
        browseButton.addActionListener(event -> {
            Set<String> exts = Sets.newHashSet("dot", "app", "exe", "bin");
            File file = UIUtil.openFile(new JFrame(), "Dot Application", "Please select the dot application", exts);
            if (file != null) {
                pathField.setText(file.getPath());
            }
        });



        layoutPanel.addGroup("Path to DOT");
        layoutPanel.addGroupComponent(pathField);
        layoutPanel.addGroupComponent(browseButton);

        layoutPanel.addSeparator();



        layoutPanel.addGroup("Rank spacing");
        rankSpacing = new JSpinner(new SpinnerNumberModel(1.0, 0.05, 10.0, 0.05));
        layoutPanel.addGroupComponent(rankSpacing);
        layoutPanel.addVerticalPadding();
        layoutPanel.addGroup("Sibling spacing");
        siblingSpacing = new JSpinner(new SpinnerNumberModel(0.2, 0.01, 10.0, 0.01));
        layoutPanel.addGroupComponent(siblingSpacing);

        layoutPanel.addSeparator();

        DotLayoutEngineProperties dotLayoutEngineProperties = DotLayoutEngineProperties.getInstance();
        Double dRankSpacing = dotLayoutEngineProperties.getRankSpacing();
        Double dSiblingSpacing = dotLayoutEngineProperties.getSiblingSpacing();
        rankSpacing.setValue(dRankSpacing);
        siblingSpacing.setValue(dSiblingSpacing);

        modeOptions = new ModeOptions(layoutPanel);
        modeOptions.setTrackerMode(OWLVizPreferences.getInstance().isTrackingModeDefault());
        modeOptions.setTrackerRadius(OWLVizPreferences.getInstance().getDefaultTrackerRadius());

    }


    public void dispose() throws Exception {
    }



}
