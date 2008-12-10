package uk.ac.man.cs.mig.coode.owlviz.ui.options;

import org.protege.editor.owl.ui.preferences.OWLPreferencesPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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

    private java.util.List<OWLPreferencesPanel> optionPages = new ArrayList<OWLPreferencesPanel>();

    private JTabbedPane tabPane;

    public static final String DEFAULT_PAGE = "General Options";



    public void applyChanges() {
        for (OWLPreferencesPanel optionPage : optionPages) {
            optionPage.applyChanges();
        }
    }


    public void initialise() throws Exception {
        setLayout(new BorderLayout());

        tabPane = new JTabbedPane();

        addOptions(new DotProcessPathPage(), "Layout Options");
//        addOptions(new LayoutDirectionOptionsPage(assertedGraphComponent.getController(),
//                                                      inferredGraphComponent.getController()), "Layout Options");
        addOptions(new LayoutSpacingOptionsPage(), "Layout Options");
        // optionsDialog.addOptions(new DisplayOptionsPage(), "Display
        // Options");
        // optionsDialog.addOptions(new UIOptionsPage(), "UI Options");

        add(tabPane, BorderLayout.NORTH);
    }


    public void dispose() throws Exception {
        for (OWLPreferencesPanel optionPage : optionPages) {
            optionPage.dispose();
        }
    }


    private void addOptions(OWLPreferencesPanel page, String tabName) throws Exception {
        // If the page does not exist, add it, and add the component
        // to the page.

        Component c = getTab(tabName);
        if(c == null) {
            // Create a new Page
            Box box = new Box(BoxLayout.Y_AXIS);
            box.add(page);
            box.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
            tabPane.add(tabName, box);
            optionPages.add(page);
        }
        else {
            Box box = (Box) c;
            box.add(Box.createVerticalStrut(7));
            box.add(page);
            optionPages.add(page);
        }

        page.initialise();        
    }


    protected Component getTab(String name) {
        for(int i = 0; i < tabPane.getTabCount(); i++) {
            if(tabPane.getTitleAt(i).equals(name)) {
                return tabPane.getComponentAt(i);
            }
        }
        return null;
    }
}
