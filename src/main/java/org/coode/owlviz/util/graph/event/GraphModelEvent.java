package org.coode.owlviz.util.graph.event;

import org.coode.owlviz.util.graph.model.GraphModel;

import java.util.ArrayList;

/**
 * User: matthewhorridge
 * The Univeristy Of Manchester
 * Medical Informatics Group
 * Date: Dec 27, 2003
 * <p/>
 * matthew.horridge@cs.man.ac.uk
 * www.cs.man.ac.uk/~horridgm
 * <p/>
 * The GraphModelEvent class isShown information associated with
 * the various events/changes that can take place within the GraphModel.
 */
public class GraphModelEvent {

    private GraphModel source;

    private ArrayList objects;


    public GraphModelEvent(GraphModel model, ArrayList objects) {
        this.source = model;

        this.objects = objects;
    }

    /**
     * Retrieves the event source.  This should be the GraphModel
     * that generated the event.
     *
     * @return The source of the event
     */
    public GraphModel getSource() {
        return source;
    }

    /**
     * Gets the objects associated with the event.
     *
     * @return The objects associated with the event.
     */
    public ArrayList getObjects() {
        return objects;
    }
}
