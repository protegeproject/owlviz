package uk.ac.man.cs.mig.coode.owlviz.ui.popup;

import javax.swing.*;

import org.semanticweb.owl.model.OWLObject;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jun 25, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public abstract class OWLObjectPopupPage {

    private String name;


    public OWLObjectPopupPage(String name) {
        this.name = name;
    }


    public String toString() {
        return name;
    }


    public abstract JComponent getPageContent(OWLObject instance);
}

