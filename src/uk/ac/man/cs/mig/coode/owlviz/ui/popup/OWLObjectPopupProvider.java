package uk.ac.man.cs.mig.coode.owlviz.ui.popup;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.protege.editor.owl.OWLEditorKit;
import org.semanticweb.owl.model.OWLObject;

import uk.ac.man.cs.mig.util.graph.ui.PopupProvider;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 27-Aug-2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class OWLObjectPopupProvider implements PopupProvider {

    private String popupManagerName;

    public OWLObjectPopupProvider(String popupManagerName, OWLEditorKit owlEditorKit) {
        this.popupManagerName = popupManagerName;
        OWLObjectPopupPage popupPage = new ConditionsPopupPage(owlEditorKit);
        PopupManager popupManager = PopupManager.getInstance(this.popupManagerName);
        popupManager.addPopupPage(popupPage);
        popupManager.setCurrentPopupPage(popupPage);
    }


    public JComponent getPopup(Object obj) {
        if(obj instanceof OWLObject) {
            PopupManager popupManager = PopupManager.getInstance(popupManagerName);
            return popupManager.getCurrentPopupPage().getPageContent((OWLObject) obj);
        }
        else {
            return new JPanel();
        }
    }

}
