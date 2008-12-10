package uk.ac.man.cs.mig.coode.owlviz.ui.options;

import org.protege.editor.core.ui.util.UIUtil;
import org.protege.editor.owl.ui.preferences.OWLPreferencesPanel;
import uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine.DotLayoutEngineProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
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

        addOptions(new DotProcessPathPanel(), "Layout Options");
//        addOptions(new LayoutDirectionOptionsPage(assertedGraphComponent.getController(),
//                                                      inferredGraphComponent.getController()), "Layout Options");
        addOptions(new LayoutSpacingPanel(), "Layout Options");
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

    class DotProcessPathPanel extends OWLPreferencesPanel {

        private JTextField pathField;

        public void initialise() throws Exception {
            setLayout(new BorderLayout(12, 12));
            setBorder(BorderFactory.createTitledBorder("Dot Application Path"));
            add(createUI(), BorderLayout.NORTH);
        }

        public void dispose() throws Exception {
            // do nothing
        }


        protected JComponent createUI() {
            Box panel = new Box(BoxLayout.LINE_AXIS);

            pathField = new JTextField(15);
            pathField.setText(DotLayoutEngineProperties.getInstance().getDotProcessPath());

            JButton browseButton = new JButton(new AbstractAction("Browse") {
                /**
                 * Invoked when an action occurs.
                 */
                public void actionPerformed(ActionEvent e) {
                    browseForPath();
                }
            });

            panel.add(new JLabel("Path:"));
            panel.add(pathField);
            panel.add(browseButton);

            return panel;
        }


        protected void browseForPath() {
            Set<String> exts = new HashSet<String>();
            exts.add("dot");
            exts.add("app");
            exts.add("exe");
            exts.add("bin");
            File file = UIUtil.openFile(new JFrame(), "Please select the dot application", exts);
            if(file != null) {
                pathField.setText(file.getPath());
            }
        }


        public void applyChanges() {
            DotLayoutEngineProperties.getInstance().setDotProcessPath(pathField.getText());
        }
    }

    class LayoutSpacingPanel extends OWLPreferencesPanel {

        JSpinner rankSpacing;
        JSpinner siblingSpacing;


        public void initialise() {
            setLayout(new BorderLayout(12, 12));
            add(createUI());

            DotLayoutEngineProperties dotLayoutEngineProperties = DotLayoutEngineProperties.getInstance();
            Double dRankSpacing = new Double(dotLayoutEngineProperties.getRankSpacing());
            Double dSiblingSpacing = new Double(dotLayoutEngineProperties.getSiblingSpacing());
            rankSpacing.setValue(dRankSpacing);
            siblingSpacing.setValue(dSiblingSpacing);
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


        public void applyChanges() {
            Double dRankSpacing = (Double) rankSpacing.getValue();
            Double dSiblingSpacing = (Double) siblingSpacing.getValue();
            DotLayoutEngineProperties dotLayoutEngineProperties = DotLayoutEngineProperties.getInstance();
            dotLayoutEngineProperties.setRankSpacing(dRankSpacing.doubleValue());
            dotLayoutEngineProperties.setSiblingSpacing(dSiblingSpacing.doubleValue());
        }


        public void dispose() throws Exception {
            // do nothing
        }
    }
}
