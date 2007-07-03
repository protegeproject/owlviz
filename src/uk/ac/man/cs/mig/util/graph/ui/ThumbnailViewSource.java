package uk.ac.man.cs.mig.util.graph.ui;

import uk.ac.man.cs.mig.util.graph.event.ThumbnailViewSourceListener;

import java.awt.*;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 12, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * A <code>ThumbnailViewSource</code> draws a minature version
 * of a larger view when only a portion of the larger view
 * can be seen.  The portion of the larger view being viewed
 * is indicated on the <code>ThumbnailView</code>.  Views that
 * wish to be represented by a thumbnail should implement this
 * interface.
 */
public interface ThumbnailViewSource
{
    /**
     * The main view should draw itself on to the
     * graphics context provided, to produce a representation
     * of the main view in the thumbnail view. Note that
     * no scaling should be applied to the graphics context.
     * @param g2
     */
	public void drawThumbnail(Graphics2D g2);

    /**
     * Should return the bounds of the main view.
     * @return A <code>Dimension</code> that specified
     * the bounds of the main view that the thumbnail
     * represents.
     */
	public Dimension getViewBounds();

    /**
     * Should return a <code>Rectangle</code> that represents the
     * portion of the main view that is visible.
     * @return The visible <code>Rectangle</code>.
     */
	public Rectangle getViewVisibleRect();

    /**
     * Allows the thumbnail view to request that the
     * main view be scrolled by a given amount.
     * @param deltaX The horizontal amount.
     * @param deltaY The vertical amount.
     */
	public void scrollView(int deltaX, int deltaY);

    /**
     * Adds a <code>ThumbnailViewSourceListener</code> to the <code>ThumbnailViewSource</code>
     * so that the <code>ThumbnailView</code> is informed of events
     * that it might be interested in.
     * @param lsnr The listener to be added.
     */
    public void addThumbnailViewSourceListener(ThumbnailViewSourceListener lsnr);

    /**
     * Removeds a previously added listener.
     * @param lsnr The listener to removed.
     */
    public void removeThumbnailViewSourceListener(ThumbnailViewSourceListener lsnr);
}
