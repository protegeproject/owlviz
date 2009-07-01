package uk.ac.man.cs.mig.coode.owlviz.ui;

import org.semanticweb.owlapi.model.OWLClass;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jul 7, 2005<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class OWLVizSelectionModel {

    private OWLClass selectedClass;

    private ArrayList<OWLVizSelectionListener> listeners;


    public OWLVizSelectionModel() {
        listeners = new ArrayList<OWLVizSelectionListener>();
    }


    public OWLClass getSelectedClass() {
        return selectedClass;

    }


    public void setSelectedClass(OWLClass selectedClass) {
        boolean changed = false;
        if(selectedClass != null) {
            if(this.selectedClass == null) {
                changed = true;
            }
            else {
                if(selectedClass.equals(this.selectedClass) == false) {
                    changed = true;
                }
            }
        }
        else {
            if(this.selectedClass != null) {
                changed = true;
            }
        }
        this.selectedClass = selectedClass;
        if(changed) {
            fireSelectionChanged();
        }
    }

    public void addSelectionListener(OWLVizSelectionListener listener) {
        listeners.add(listener);
    }

    public void removeSelectionListener(OWLVizSelectionListener listener) {
        listeners.remove(listener);
    }

    private void fireSelectionChanged() {
        for(Iterator<OWLVizSelectionListener> it = listeners.iterator(); it.hasNext(); ) {
            OWLVizSelectionListener lsnr = it.next();
            lsnr.selectionChanged(this);
        }
    }


}

