package uk.ac.man.cs.mig.coode.owlviz.ui.options;

import org.protege.editor.core.ui.util.UIUtil;
import uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine.DotLayoutEngineProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.HashSet;
import java.util.Set;


/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Mar 3, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class DotProcessPathPage extends OptionsPage {

    private JTextField pathField;

    public DotProcessPathPage() {
        setLayout(new BorderLayout());
        add(createUI());
    }


    protected JComponent createUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JLabel label = new JLabel("Path:");
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(label, gbc);
        pathField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 100;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        panel.add(pathField, gbc);
        JButton browseButton = new JButton(new AbstractAction("Browse") {
            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {
                browseForPath();
            }
        });
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        panel.add(browseButton, gbc);
        panel.setBorder(BorderFactory.createTitledBorder("Dot Application Path"));
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


    public void updateInterface() {
        pathField.setText(DotLayoutEngineProperties.getInstance().getDotProcessPath());
    }


    public void validateOptions() {
    }


    public void applyOptions() {
        DotLayoutEngineProperties.getInstance().setDotProcessPath(pathField.getText());
    }
}

