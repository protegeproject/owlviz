package uk.ac.man.cs.mig.util.popup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 16, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public class PopupComponent extends JComponent
{
    private boolean POPUP_IS_MODAL = true; // By default, the popup is modal
	private boolean HIDE_ON_CLICK = true;   // Determines if a click outside of the popup should hide it
	private boolean HIDE_ON_TIMER = false;  // Determines if the popup should hide after a given amount of time
	private int HIDE_TIMER_DELAY = 0;
	private boolean FORWARD_MOUSE_MOTION_EVENTS = true;

    private Timer timer;

	private JComponent content; // The content that represents the popup
	private JComponent glassPane; // The glass pane that will contain the popup

	private ArrayList<PopupComponentListener> listeners; // A list of listeners to be informed of popup events
    private boolean mouseOverGlassPane;


    /**
	 * Constructs a popup using the specified content, which will automatically
     * hide if a mouse click occurs outside of the popup area, and will behave
     * in a modal fashion - i.e. no other components (in the content pane) will
     * receive mouse events until the popup has been hidden.
	 * @param content A component that represents the
	 * popup content.
	 */
	public PopupComponent(JComponent content)
	{
		this(content, true, 0, false);
	}

	/**
	 * Constructs a popup using the specified settings.
	 * @param content The component that represents the popup
	 * content.
	 * @param hideOnClick If <code>true</code> then the popup
	 * will hide when any area outside of it is clicked (rather
	 * like a popup menu).  If <code>false</code> the popup will not
	 * hide when any area outside of it is clicked.
	 * @param timerDelay The number of milliseconds after which the
	 * the popup will automatically hide itself.  If a value of zero is
	 * specified, then the popup will remain displayed indefinitely.
     * @param isModal If <code>true</code>, mouse listener events
     * that occur outside the component area are forwarded to the appropriate
     * component in the content pane i.e other components may be interacted with whilst
     * the popup is displayed.  If <code>false</code> then the popup
     * behaves rather like a modal dialog, so that no other components can receive
     * mouse events until the component has closed.
	 */
	public PopupComponent(JComponent content, boolean hideOnClick, int timerDelay, boolean isModal)
	{
		glassPane = new JPanel(null);

		glassPane.setLayout(null);

		glassPane.add(content);

		glassPane.setOpaque(false);

		listeners = new ArrayList<PopupComponentListener>();

		setupListeners();

		setContent(content);

		this.HIDE_ON_CLICK = hideOnClick;

        this.POPUP_IS_MODAL = isModal;

		// Check to see if a timer delay has been
		// specified.  If so, set up the timer.
		if(timerDelay > 0)
		{
			HIDE_ON_TIMER = true;

			HIDE_TIMER_DELAY = timerDelay;

			timer = new Timer(HIDE_TIMER_DELAY, new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
                    // Hide the popup if the mouse is outside
                    // the content
                    if(mouseOverGlassPane == true)
                    {
                        hidePopup();

                        timer.stop();
                    }
                    else
                    {
                        timer.stop();

                        timer.start();
                    }
				}
			});
		}
	}

	/**
	 * Causes the popup to be displayed at the specified
	 * location.
	 * @param component The component whose cooredinate
	 * system is to be used.
	 * @param x The horizontal location of the popup
	 * @param y The vertical location of the popup.
	 */
	public void showPopup(JComponent component, int x, int y)
	{
        // Get the root (JFrame probably)
        Component rootComp = SwingUtilities.getRoot(component);


        // Determine where to place the popup
        // (Left or right, top or bottom of the mouse cursor
        // We want to fit the popup within the top level frame

        int componentMaxX = (int)rootComp.getSize().width;

        int componentMaxY = (int)rootComp.getSize().height;

        int xPosOnRoot = SwingUtilities.convertPoint(component, x, y, rootComp).x;

        int yPosOnRoot = SwingUtilities.convertPoint(component, x, y, rootComp).y;


        // Display to the right of the mouse cursor, unless
        // there is not enough room
        int deltaX = xPosOnRoot + content.getWidth() - componentMaxX;

        if(deltaX > 0)
        {
            xPosOnRoot -= deltaX;

            if(xPosOnRoot < 0)
            {
                xPosOnRoot = 0;
            }
        }

        int deltaY = yPosOnRoot + content.getHeight() - componentMaxY;

        if(deltaY > 0)
        {
            yPosOnRoot -= deltaY;

            if(yPosOnRoot < 0)
            {
                yPosOnRoot = 0;
            }
        }

        // Convert root pos back to component pos

        int xPos = SwingUtilities.convertPoint(rootComp, xPosOnRoot, yPosOnRoot, component).x;

        int yPos = SwingUtilities.convertPoint(rootComp, xPosOnRoot, yPosOnRoot, component).y;

       
		JRootPane rootPane = component.getRootPane();

		rootPane.setGlassPane(glassPane);

        //Convert the mouse point from the invoking component coordinate
        // system to the glassPane coordinate system
        Point pt = SwingUtilities.convertPoint(component, xPos, yPos, glassPane);

        // Set the location of the popup in the glass pane
        content.setLocation(pt);

        // Show the glass pane.
        glassPane.setVisible(true);

        // If the popup is set to hide automatically after a specified
        // amount of time, then reset the timer.
		if(HIDE_ON_TIMER == true)
		{
			timer.stop();

			timer.start();
		}
	}

	/**
	 * Hides the popup
	 */
	public void hidePopup()
	{
        // Only hide the popup if the glass pane is
        // visible (i.e. if the popup is being displayed)
        if(glassPane.isVisible() == true)
        {
		    glassPane.setVisible(false);

		    firePopupClosedEvent();
        }
	}

	/**
	 * Sets the content that the popup displays
	 * @param content A JComponent that represents the content to be displayed.
	 */
	public void setContent(JComponent content)
	{
        if(content == null)
        {
            throw new NullPointerException("Popup content must not be null");
        }

        // Remove the previous popup content
        if(this.content != null)
        {
		    glassPane.remove(this.content);
        }

        // Set this content to the new content
		this.content = content;

        // Set the size of the content
		this.content.setSize(this.content.getPreferredSize());

        // Put the content into the glass pane
		glassPane.add(this.content);


	}


	/**
	 * Adds a <code>PopupComponentListener</code>, which is informed
	 * when the popup closes.
	 * @param lsnr The listener to be added.
	 */
	public void addPopupComponentListener(PopupComponentListener lsnr)
	{
		listeners.add(lsnr);
	}

	/**
	 * Removes a previously added listener.
	 * @param lsnr The listener to be removed.
	 */
	public void removePopupComponentListener(PopupComponentListener lsnr)
	{
		listeners.remove(lsnr);
	}

	/**
	 * Sets up listeners
	 */
	protected void setupListeners()
	{
		// Check to see if we need to close
		// the popup when a click outside of
		// it's area has been detected. Also,
        // we may need to propagte the events to
        // components below the glass pane
		glassPane.addMouseListener(new MouseListener()
		{
            /**
             * Invoked when the mouse button has been clicked (pressed
             * and released) on a component.
             */
            public void mouseClicked(MouseEvent e)
            {
                 propagateMouseListenerEvent(e);
            }

            /**
             * Invoked when a mouse button has been pressed on a component.
             */
            public void mousePressed(MouseEvent e)
            {
                // Might need to hide the popup if the mouse
                // has been pressed on the glass pane
                handleHidePopup(e);

                propagateMouseListenerEvent(e);
            }

            /**
             * Invoked when a mouse button has been released on a component.
             */
            public void mouseReleased(MouseEvent e)
            {
                propagateMouseListenerEvent(e);
            }

            /**
             * Invoked when the mouse enters a component.
             */
            public void mouseEntered(MouseEvent e)
            {
                // Don't propagate here
                mouseOverGlassPane = true;
            }


            /**
             * Invoked when the mouse exits a component.
             */
            public void mouseExited(MouseEvent e)
            {
                // Don't propagate
                mouseOverGlassPane = false;
            }

            /**
             * Checks to see if a mouse event on the glass
             * pane should cause the popup to be hidden, and
             * if so, hides it.
             * @param e The mouse event.
             */
            private void handleHidePopup(MouseEvent e)
            {
				if(HIDE_ON_CLICK == true)
				{
					hidePopup();
				}
            }
		});

        glassPane.addMouseMotionListener(new MouseMotionListener()
        {
            /**
             * Invoked when a mouse button is pressed on a component and then
             * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
             * delivered to the component where the drag originated until the
             * mouse button is released (regardless of whether the mouse position
             * is within the bounds of the component).
             * <p/>
             * Due to platform-dependent Drag&Drop implementations,
             * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
             * Drag&Drop operation.
             */
            public void mouseDragged(MouseEvent e)
            {
                propagateMouseMotionListenerEvents(e);
            }

            /**
             * Invoked when the mouse cursor has been moved onto a component
             * but no buttons have been pushed.
             */
            public void mouseMoved(MouseEvent e)
            {
                propagateMouseMotionListenerEvents(e);
            }
        });
	}

	/**
	 * Fires an event that informs registered listeners
	 * that the popup has closed.
	 */
	protected void firePopupClosedEvent()
	{
		PopupComponentEvent evt = new PopupComponentEvent(this);

        // Just iterate through the list of listeners
        // and fire the events to them
		Iterator<PopupComponentListener> it = listeners.iterator();

		while(it.hasNext())
		{
			it.next().popupClosed(evt);
		}
	}

    /**
     * Obtains the deepest component below the glass pane
     * at the specified point.
     * @param pt The point.
     * @return The deepest component, or <code>null</code> if
     * there is no component at the specified point.
     */
    protected Component getDeepestComponent(Point pt)
    {
        // Get hold of the content pane, since this contains the components
        // that we want to relay mouse events to
        SwingUtilities.getRoot(glassPane);

        Container contentPane = glassPane.getRootPane().getContentPane();

        // Convert the mouse point from the glass pane coordinate system
        // into the coordinate system of the content pane.
        Point contentPanePt = SwingUtilities.convertPoint(glassPane, pt, contentPane);

        // Find the deepest component - i.e. the one that should get the mouse
        // event.
        Component deepestComponent = SwingUtilities.getDeepestComponentAt(contentPane, contentPanePt.x, contentPanePt.y);

        return deepestComponent;
    }

    /**
     * Propagates certain mouse events, such as MOUSE_CLICKED, MOUSE_RELEASED
     * etc. to the deepest component.
     * @param e The MouseEvent to be propagated.
     */
    protected void propagateMouseListenerEvent(MouseEvent e)
    {
        if(POPUP_IS_MODAL == false)
        {

            Component deepestComponent = getDeepestComponent(e.getPoint());

            if(deepestComponent != null)
            {
                MouseListener [] mouseListeners = deepestComponent.getMouseListeners();

                int eventID;

                // Get the event type
                eventID = e.getID();

                Point pt = e.getPoint();

                Point convertedPt = SwingUtilities.convertPoint(glassPane, e.getPoint(), deepestComponent);


                MouseEvent evt = new MouseEvent(deepestComponent,
                                                    e.getID(),
                                                    System.currentTimeMillis(),
                                                    e.getModifiers(),
                                                    convertedPt.x,
                                                    convertedPt.y,
                                                    e.getClickCount(),
                                                    e.isPopupTrigger(),
                                                    e.getButton());

                // Distibute the event to the component's listeners.
                for(int i = 0; i < mouseListeners.length; i++)
                {

                    // Forward the appropriate mouse event
                    if(eventID == MouseEvent.MOUSE_PRESSED)
                    {
                        mouseListeners[i].mousePressed(evt);
                    }
                    else if(eventID == MouseEvent.MOUSE_RELEASED)
                    {
                        mouseListeners[i].mouseReleased(evt);
                    }
                    else if(eventID == MouseEvent.MOUSE_CLICKED)
                    {
                        mouseListeners[i].mouseClicked(evt);
                    }

                }
            }
        }
    }

    protected void propagateMouseMotionListenerEvents(MouseEvent e)
    {
        if(FORWARD_MOUSE_MOTION_EVENTS == true)
        {
            // Get the correct component
            Component deepestComponent = getDeepestComponent(e.getPoint());

            if(deepestComponent != null)
            {
                // Distribute the event to the components listeners
                MouseMotionListener [] mouseMotionListeners = deepestComponent.getMouseMotionListeners();

                // Get the event type
                int eventID = e.getID();

                Point pt = e.getPoint();

                Point convertedPt = SwingUtilities.convertPoint(glassPane, e.getPoint(), deepestComponent);

                MouseEvent evt = new MouseEvent(deepestComponent,
                                                    e.getID(),
                                                    System.currentTimeMillis(),
                                                    e.getModifiers(),
                                                    convertedPt.x,
                                                    convertedPt.y,
                                                    e.getClickCount(),
                                                    e.isPopupTrigger(),
                                                    e.getButton());


                for(int i = 0; i < mouseMotionListeners.length; i++)
                {
                    if(eventID == MouseEvent.MOUSE_MOVED)
                    {
                        mouseMotionListeners[i].mouseMoved(e);
                    }
                    else if(eventID == MouseEvent.MOUSE_DRAGGED)
                    {
                        mouseMotionListeners[i].mouseDragged(e);
                    }
                }
            }
        }
    }

    public boolean popupIsDisplayed()
    {
        return glassPane.isVisible();
    }

}
