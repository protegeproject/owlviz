package uk.ac.man.cs.mig.coode.owlviz.command;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.semanticweb.owl.model.OWLDescription;

import uk.ac.man.cs.mig.util.graph.controller.Controller;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Sep 23, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class ShowAnonymousClassesCommand extends AbstractAction {

    private Controller assertedController;
    private Controller inferredController;


    public ShowAnonymousClassesCommand(Controller assertedController,
                                       Controller inferredController) {
      //  super("Show Restrictions", OWLIcons.getAllRestrictionIcon());
        this.assertedController = assertedController;
        this.inferredController = inferredController;
    }


    public void actionPerformed(ActionEvent e) {
        Object selObj = assertedController.getGraphSelectionModel().getSelectedObject();
        if(selObj != null) {
            assertedController.getVisualisedObjectManager().showParents(selObj, 1, OWLDescription.class);
            inferredController.getVisualisedObjectManager().showParents(selObj, 1, OWLDescription.class);
        }
    }
}

