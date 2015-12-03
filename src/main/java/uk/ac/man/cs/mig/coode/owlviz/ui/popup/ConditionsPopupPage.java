package uk.ac.man.cs.mig.coode.owlviz.ui.popup;

import org.protege.editor.owl.OWLEditorKit;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Sep 23, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class ConditionsPopupPage extends OWLObjectPopupPage {

    private static final Logger logger = LoggerFactory.getLogger(ConditionsPopupPage.class);


//    private OWLClassDescriptionTable table;


    public ConditionsPopupPage(OWLEditorKit owlEditorKit) {
        super("Conditions");
//        table = new OWLClassDescriptionTable(owlEditorKit);
    }


    public JComponent getPageContent(OWLObject instance) {
        if(instance instanceof OWLClass) {
            return createConditionsTableComponent((OWLClass) instance);
        }
        else {
            return new JPanel();
        }
    }


    private JComponent createConditionsTableComponent(OWLClass cls) {
//        try {
//            table.setOWLObject(cls);
            JPanel panel = new JPanel(new BorderLayout());
//            panel.add(table, BorderLayout.NORTH);
//            Dimension prefSize = table.getPreferredSize();
//            panel.setPreferredSize(new Dimension(450, prefSize.height + 20));
            panel.setBackground(Color.WHITE);
            return panel;
//        } catch (OWLException e) {
//            logger.error(e);
//            return new JLabel("<Error! " + e.getClass().getSimpleName() + ": " + e.getMessage() + ">");
//        }
    }
}

