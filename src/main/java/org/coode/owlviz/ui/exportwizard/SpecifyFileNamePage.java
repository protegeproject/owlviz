package org.coode.owlviz.ui.exportwizard;

import org.coode.owlviz.util.wizard.Wizard;
import org.coode.owlviz.util.wizard.WizardPage;
import org.protege.editor.core.ui.util.UIUtil;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collections;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 25, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class SpecifyFileNamePage extends WizardPage {



    private JTextField pathNameField;

    public SpecifyFileNamePage() {
        super("SpecifyFileNamePage");
        add(createUI());
    }


    protected JComponent createUI() {
        JComponent component = new JPanel(new BorderLayout(12, 12));
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        JLabel label = new JLabel("Please specify the location and name of the file to export to.");
        panel.add(label, BorderLayout.NORTH);
        JPanel entryPanel = new JPanel(new BorderLayout(12, 12));
        pathNameField = new JTextField();
        pathNameField.addCaretListener(new CaretListener() {
            /**
             * Called when the caret position is updated.
             *
             * @param e the caret event
             */
            public void caretUpdate(CaretEvent e) {
                setWizardButtonState();
            }
        });
        entryPanel.add(pathNameField, BorderLayout.CENTER);
        JButton browseButton = new JButton(new AbstractAction("Browse") {


            /**
             * Invoked when an action occurs.
             */
            public void actionPerformed(ActionEvent e) {
                browseForFileName();
                setWizardButtonState();
            }
        });
        entryPanel.add(browseButton, BorderLayout.EAST);
        panel.add(entryPanel, BorderLayout.SOUTH);
        component.add(panel, BorderLayout.NORTH);
        return component;
    }


    /**
     * Gets the file name that is set in the Wizard page
     * UI.
     *
     * @return The file name.
     */
    public String getFileName() {
        return pathNameField.getText();
    }


    /**
     * Called when the page is displayed. Override this method to be informed
     * of this event.
     *
     * @param w The <code>Wizard</code> to which the
     *          page belongs.
     */
    public void pageSelected(Wizard w) {
        setWizardButtonState();
    }


    /**
     * Pops up the <code>JFileChooser</code> to allow a pathname
     * to be selected, and a file name entered.  The file chooser
     * will apply the correct file filter and extension if available.
     */
    protected void browseForFileName() {
        final String fileExtension;
        final String formatDescription;
        SelectFormatPage formatPage = (SelectFormatPage) getWizard().getPage("SelectFormatPage");
        if (formatPage != null) {
            fileExtension = formatPage.getSelectedExportFormat().getFormatFileExtension();
            formatDescription = formatPage.getSelectedExportFormat().getFormatDescription();
        }
        else {
            fileExtension = "";
            formatDescription = "";
        }
        File file = UIUtil.saveFile(getWizard(), "Export as", "Export the OWLViz hierarchy", Collections.singleton(fileExtension));

        if (file != null) {
            String pathName = file.getPath();
            if (!pathName.endsWith("." + fileExtension)) {
                // Append extension
                pathName = pathName + "." + fileExtension;
            }
            pathNameField.setText(pathName);
            setWizardButtonState();
        }
    }


    /**
     * Sets the Wizard button state, which depends
     * on the contents of the text field
     * that the file name is entered into.
     */
    protected void setWizardButtonState() {
        String pathName = pathNameField.getText();

        // Strip out leading and trailing white space
        pathName = pathName.trim();
        if (pathName.length() > 0) {
            getWizard().setNextButtonEnabled(true);
        }
        else {
            getWizard().setNextButtonEnabled(false);
        }
    }
}

