package org.coode.owlviz.util.popup;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 16, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class PopupComponentEvent {

    private PopupComponent source;

    public PopupComponentEvent(PopupComponent source) {
        this.source = source;
    }

    public PopupComponent getSource() {
        return source;
    }
}
