package uk.ac.man.cs.mig.coode.owlviz.command;

import uk.ac.man.cs.mig.coode.owlviz.ui.OWLVizView;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 28, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class ShowProjectInfo extends AbstractAction {

    private OWLVizView view;


    public ShowProjectInfo(OWLVizView view) {
        super("Show Info");
        this.view = view;
    }


    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
        Object selObj = view.getSelectionModel().getSelectedClass();
        if(selObj != null) {
//            if(selObj instanceof Instance) {
//                view.getProject().show((Instance) selObj);
//            }
        }
    }
}

