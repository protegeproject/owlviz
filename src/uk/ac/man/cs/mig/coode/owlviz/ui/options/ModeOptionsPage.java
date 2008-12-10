package uk.ac.man.cs.mig.coode.owlviz.ui.options;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class ModeOptionsPage extends OptionsPage {

    private JRadioButton trackerMode;

    private JRadioButton configureMode;

    private JSpinner trackerRadiusSpinner;

    private OWLVizViewOptions options;

    ActionListener l = new ActionListener(){
        public void actionPerformed(ActionEvent event) {
            trackerRadiusSpinner.setEnabled(trackerMode.isSelected());
        }
    };

    public ModeOptionsPage(OWLVizViewOptions options) {
        this.options = options;

        setLayout(new BorderLayout());

        trackerRadiusSpinner = new JSpinner(new SpinnerNumberModel(options.getTrackerRadius(), 1, 100, 1));
        trackerRadiusSpinner.setEnabled(options.isTrackerMode());

        trackerMode = new JRadioButton("track selection", options.isTrackerMode());
        configureMode = new JRadioButton("create graph manually", !options.isTrackerMode());
        configureMode.setAlignmentX(0.0f);

        ButtonGroup bg = new ButtonGroup();
        bg.add(trackerMode);
        bg.add(configureMode);

        JComponent trackerPanel = new Box(BoxLayout.LINE_AXIS);
        trackerPanel.add(trackerMode);
        trackerPanel.add(Box.createHorizontalStrut(20));
        trackerPanel.add(new JLabel("radius"));
        trackerPanel.add(trackerRadiusSpinner);
        trackerPanel.setAlignmentX(0.0f);

        Box box = new Box(BoxLayout.PAGE_AXIS);
        box.add(trackerPanel);
        box.add(configureMode);

        add(box, BorderLayout.NORTH);

        trackerMode.addActionListener(l);
        configureMode.addActionListener(l);
    }


    public void updateInterface() {
        trackerMode.setSelected(options.isTrackerMode());
        trackerRadiusSpinner.setValue(options.getTrackerRadius());
        configureMode.setSelected(!options.isTrackerMode());
    }


    public void validateOptions() {
        // ??
    }


    public void applyOptions() {
        if (options.isTrackerMode() != trackerMode.isSelected()){
            options.setTrackerMode(trackerMode.isSelected());
        }
        if (options.getTrackerRadius() != (Integer) trackerRadiusSpinner.getValue()){
            options.setTrackerRadius((Integer) trackerRadiusSpinner.getValue());
        }
    }
}
