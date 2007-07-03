package uk.ac.man.cs.mig.coode.owlviz.command;

import uk.ac.man.cs.mig.coode.owlviz.ui.OWLVizView;
import uk.ac.man.cs.mig.util.graph.ui.GraphComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Iterator;

import org.protege.editor.core.ui.view.DisposableAction;
import org.protege.editor.core.ui.util.Icons;


/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 19, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class ZoomInCommand extends DisposableAction {

    private OWLVizView view;

    public ZoomInCommand(OWLVizView view) {
        super("Zoom In", Icons.getIcon("zoom.in.png"));
        this.putValue(AbstractAction.SHORT_DESCRIPTION, "Zoom In");
        this.view = view;
    }

    public void dispose() {
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
        for(Iterator it = view.getAllGraphComponents().iterator(); it.hasNext(); ) {
            GraphComponent graphComponent = (GraphComponent) it.next();
            int zoomLevel = graphComponent.getGraphView().getZoomLevel();
            zoomLevel += 10;
            graphComponent.getGraphView().setZoomLevel(zoomLevel);
        }
    }
}
