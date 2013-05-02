package uk.ac.man.cs.mig.util.graph.event;

import uk.ac.man.cs.mig.util.graph.ui.ThumbnailViewSource;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 19, 2004<br><br>
 *
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public class ThumbnailViewSourceEvent
{
    private ThumbnailViewSource source;

    /**
     * Constructs a <code>ThumbnailViewSourceEvent</code>
     * @param source The <code>ThumbnailViewSource</code> that is the source
     * of the event.
     */
    public ThumbnailViewSourceEvent(ThumbnailViewSource source)
    {
        this.source = source;
    }

    /**
     * Gets the source of the event.
     * @return The <code>ThumbnailViewSource</code> event source.
     */
    public ThumbnailViewSource getSource()
    {
        return source;
    }
}
