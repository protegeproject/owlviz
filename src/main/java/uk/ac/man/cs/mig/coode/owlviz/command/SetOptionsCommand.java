package uk.ac.man.cs.mig.coode.owlviz.command;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import org.protege.editor.core.ui.view.DisposableAction;

import uk.ac.man.cs.mig.coode.owlviz.ui.OWLVizIcons;
import uk.ac.man.cs.mig.coode.owlviz.ui.OWLVizViewI;
import uk.ac.man.cs.mig.coode.owlviz.ui.options.OptionsDialog;
import uk.ac.man.cs.mig.util.graph.ui.GraphComponent;
import uk.ac.man.cs.mig.util.okcanceldialog.OKCancelDialog;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Mar 2, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class SetOptionsCommand extends DisposableAction {

    /**
     * 
     */
    private static final long serialVersionUID = -4382394381357029549L;

    private OWLVizViewI view;

    private OptionsDialog optionsDialog;

    public SetOptionsCommand(OWLVizViewI view, OptionsDialog optionsDialog) {
        super("Options...", OWLVizIcons.getIcon(OWLVizIcons.OPTIONS_ICON));
        this.view = view;
        this.optionsDialog = optionsDialog;
    }

    public void initialise() throws Exception {

    }

    public void dispose() {
    }




    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
        optionsDialog.updateInterface();
        if(optionsDialog.showDialog() == OKCancelDialog.OPTION_APPROVE) {
            optionsDialog.applyOptions();
            for(Iterator it = view.getGraphComponents().iterator(); it.hasNext();) {
                GraphComponent graphComponent = (GraphComponent) it.next();
                graphComponent.getController().getGraphGenerator().invalidateGraph();
            }
        }
    }
}

