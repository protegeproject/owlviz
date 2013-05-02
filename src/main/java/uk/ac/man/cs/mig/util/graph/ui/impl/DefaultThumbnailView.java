package uk.ac.man.cs.mig.util.graph.ui.impl;

import uk.ac.man.cs.mig.util.graph.event.ThumbnailViewSourceEvent;
import uk.ac.man.cs.mig.util.graph.event.ThumbnailViewSourceListener;
import uk.ac.man.cs.mig.util.graph.ui.ThumbnailView;
import uk.ac.man.cs.mig.util.graph.ui.ThumbnailViewSource;

import java.awt.*;
import java.awt.event.*;

/**
 * Created by IntelliJ IDEA.
 * User: matthewhorridge
 * Date: Feb 19, 2004
 * Time: 7:21:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultThumbnailView extends ThumbnailView implements ThumbnailViewSourceListener
{
    /**
     * 
     */
    private static final long serialVersionUID = -8613789722311809917L;

    private ThumbnailViewSource viewSrc;

    private Rectangle sourceViewBounds;
    private double scale; // The magnitude of scaling for the thumbnailview (1.0 = 100%)

    // Mouse Drag Event Variables
    Point prevMousePos;

    public DefaultThumbnailView(ThumbnailViewSource viewSrc)
    {
        this.viewSrc = viewSrc;

        this.viewSrc.addThumbnailViewSourceListener(this);

        sourceViewBounds = new Rectangle(viewSrc.getViewBounds());

        setPreferredSize(new Dimension(150, 80)); // Some Random Size!


        addComponentListener(new ComponentAdapter()
        {
            /**
             * Invoked when the component's size changes.
             */
            public void componentResized(ComponentEvent e)
            {
                updateScale();

                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionListener()
        {
            public void mouseDragged(MouseEvent e)
            {
                processMouseDragged(e);
            }

            public void mouseMoved(MouseEvent e)
            {
                // Don't need to do anything here
            }
        });

        addMouseListener(new MouseAdapter()
        {
            /**
             * Invoked when a mouse button has been pressed on a component.
             */
            public void mousePressed(MouseEvent e)
            {
                // Set the position of the mouse, incase a drag event is imminent.
                prevMousePos = e.getPoint();
            }
        });

        updateScale();
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.scale(scale, scale);

        viewSrc.drawThumbnail(g2);

        g2.setColor(Color.LIGHT_GRAY);

        g2.draw(sourceViewBounds);

        g2.setColor(Color.RED);

        g2.draw(viewSrc.getViewVisibleRect());
    }

    protected void updateScale()
    {
        double horiScale = getWidth() / viewSrc.getViewBounds().getWidth();

        double vertScale = getHeight() / viewSrc.getViewBounds().getHeight();

        scale = Math.min(horiScale, vertScale);
    }

    //////////////////////////////////////////////////////////////////////////
    //
    // Implementation of ThumbnailViewSourceListener
    //
    //////////////////////////////////////////////////////////////////////////

    /**
     * Called when the <code>ThumbnailViewSource</code> visible rectangle
     * changes size.
     *
     * @param evt The associated <code>ThumbnailViewSourceEvent</code>
     */
    public void sourceViewVisibleRectChanged(ThumbnailViewSourceEvent evt)
    {
        repaint();
    }

    /**
     * Called when the <code>ThumbnailViewSource</code> contents have
     * changed and therefore the <code>ThumbnailView</code> needs updating.
     *
     * @param evt The associated <code>ThumbnailViewSourceEvent</code>.
     */
    public void sourceViewContentsChanged(ThumbnailViewSourceEvent evt)
    {
        updateScale();

        repaint();
    }

    /**
     * Called when the bounds of the <code>ThumbnailViewSource</code> have
     * changed.
     *
     * @param evt The associated <code>ThumbnailViewSourceEvent</code>.
     */
    public void sourceViewBoundsChanged(ThumbnailViewSourceEvent evt)
    {
        sourceViewBounds.width = viewSrc.getViewBounds().width;

        sourceViewBounds.height = viewSrc.getViewBounds().height;

        updateScale();

        repaint();
    }



    protected void processMouseDragged(MouseEvent e)
    {
        // Get the current mouse point
        Point curMousePos = e.getPoint();

        Dimension vector = new Dimension(curMousePos.x - prevMousePos.x, curMousePos.y - prevMousePos.y);

        // Store curMousePos as prevMousePos;

        prevMousePos = curMousePos;

        // Scale the vector to account for the thumbnail scaling
        vector.width = (int)(vector.width * 1.0 / scale);

        vector.height = (int)(vector.height * 1.0 / scale);

        viewSrc.scrollView(vector.width, vector.height);

    }

}
