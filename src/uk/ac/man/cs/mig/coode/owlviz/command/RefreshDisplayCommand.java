package uk.ac.man.cs.mig.coode.owlviz.command;

import uk.ac.man.cs.mig.coode.owlviz.ui.OWLVizIcons;
import uk.ac.man.cs.mig.coode.owlviz.ui.OWLVizView;
import uk.ac.man.cs.mig.util.graph.ui.GraphComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Sep 19, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class RefreshDisplayCommand extends AbstractAction {

    private JTree inferredHierarchy;

    private OWLVizView view;

    public RefreshDisplayCommand(OWLVizView view,
                                 JTree inferredHierarchy) {
        super("Refresh inferred hierarchy", OWLVizIcons.getIcon(OWLVizIcons.REFRESH_ICON));
        putValue(AbstractAction.SHORT_DESCRIPTION, "Refresh inferred hierarchy");
        this.view = view;
        this.inferredHierarchy = inferredHierarchy;
    }


    public void actionPerformed(ActionEvent e) {
        Object[] objs = view.getAssertedGraphComponent().getVisualisedObjectManager().getVisualisedObjects();
        GraphComponent graphComponent = view.getInferredGraphComponent();
        graphComponent.getVisualisedObjectManager().hideAll();
        graphComponent.getVisualisedObjectManager().showObjects(objs);
        // Make sure that we refresh the inferred hierarchy
//		LazyTreeNode node = (LazyTreeNode) inferredHierarchy.getModel().getRoot();
//		node.reload();
    }
}

