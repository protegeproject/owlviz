package uk.ac.man.cs.mig.coode.owlviz.ui.options;

import org.protege.editor.core.ui.preferences.PreferencesDialogPanel;
import org.protege.editor.owl.OWLEditorKit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
public class GlobalOptionsPage extends OptionsPage {

    /**
     * 
     */
    private static final long serialVersionUID = -7118513832753295095L;

    private Action openPrefs = new AbstractAction("Open preferences..."){
            /**
         * 
         */
        private static final long serialVersionUID = -4381561493594548830L;

            public void actionPerformed(ActionEvent event) {
                PreferencesDialogPanel.showPreferencesDialog("OWLViz", eKit);
            }
    };

    private OWLEditorKit eKit;


    public GlobalOptionsPage(OWLEditorKit eKit) {
        this.eKit = eKit;
        setLayout(new BorderLayout(7, 7));
        final JLabel label = new JLabel("<html>OWLViz options that affect all OWLViz views" +
                                        " <br>have been moved to Protege preferences.</html>");
        add(label, BorderLayout.NORTH);
        add(new JButton(openPrefs), BorderLayout.SOUTH);
    }


    public void updateInterface() {
        // do nothing
    }


    public void validateOptions() {
        // do nothing
    }


    public void applyOptions() {
        // do nothing
    }
}
