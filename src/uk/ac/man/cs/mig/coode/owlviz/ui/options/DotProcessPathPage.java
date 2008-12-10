package uk.ac.man.cs.mig.coode.owlviz.ui.options;

import org.protege.editor.core.ui.util.UIUtil;
import org.protege.editor.owl.ui.preferences.OWLPreferencesPanel;
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
public class DotProcessPathPage extends OWLPreferencesPanel {

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

