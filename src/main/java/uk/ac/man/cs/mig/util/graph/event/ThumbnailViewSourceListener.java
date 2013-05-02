package uk.ac.man.cs.mig.util.graph.event;

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
public interface ThumbnailViewSourceListener
{
    /**
     * Called when the <code>ThumbnailViewSource</code> visible rectangle
     * changes size.
     * @param evt The associated <code>ThumbnailViewSourceEvent</code>
     */
    public void sourceViewVisibleRectChanged(ThumbnailViewSourceEvent evt);

    /**
     * Called when the <code>ThumbnailViewSource</code> contents have
     * changed and therefore the <code>ThumbnailView</code> needs updating.
     * @param evt The associated <code>ThumbnailViewSourceEvent</code>.
     */
    public void sourceViewContentsChanged(ThumbnailViewSourceEvent evt);

    /**
     * Called when the bounds of the <code>ThumbnailViewSource</code> have
     * changed.
     * @param evt The associated <code>ThumbnailViewSourceEvent</code>.
     */
    public void sourceViewBoundsChanged(ThumbnailViewSourceEvent evt);
}
