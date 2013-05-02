package uk.ac.man.cs.mig.util.graph.ui;

import java.awt.*;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 30, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public interface Zoomable
{
	/**
	 * Requests that the zoom level is set to the specified
	 * value.
	 * @param zoomLevel The required zoom level as a percentage -
	 * A zoom level of 100 (percent) is a 1:1 ratio.
	 */
	public void setZoomLevel(int zoomLevel);

	/**
	 * Gets the zoom level as a percentage.
	 */
	public int getZoomLevel();

    /**
     * Gets the maximum allowed zoom level.
     * @return The maximum allowed zoom level as a percentage.
     */
    public int getMaximumZoomLevel();

    /**
     * Gets the minimum allowed zoom level.
     * @return The minimum allowed zoom level as a percentage.
     */
    public int getMinimumZoomLevel();

     /**
      * Converts a <code>Point</code> to a 'zoomed' <code>Point</code>.
      * e.g. (100, 200) zoomed 200 percent would be converted to
      * (200, 400).
      * @param pt The <code>Point</code>  to be converted
      * @return The converted <code>Point</code>
      */
    public Point pointToZoomedPoint(Point pt);

    /**
      * Converts a <code>Point</code> from a 'zoomed' <code>Point</code>.
      * e.g. (100, 200) zoomed 200 percent would be converted to
      * (50, 100).
      * @param pt The <code>Point</code>  to be converted
      * @return The converted <code>Point</code>
      */
    public Point pointFromZoomedPoint(Point pt);


     /**
      * Converts a <code>Dimension</code> to a 'zoomed' <code>Dimension</code>.
      * e.g. (100, 200) zoomed 200 percent would be converted to
      * (200, 400).
      * @param dim The <code>Dimension</code>  to be converted
      * @return The converted <code>Dimension</code>
      */
    public Dimension dimensionToZoomedDimension(Dimension dim);

    /**
      * Converts a <code>Dimension</code> from a 'zoomed' <code>Dimension</code>.
      * e.g. (100, 200) zoomed 200 percent would be converted to
      * (50, 100).
      * @param dim The <code>Dimension</code>  to be converted
      * @return The converted <code>Dimension</code>
      */
    public Dimension dimensionFromZoomedDimension(Dimension dim);

     /**
      * Converts a <code>Rectangle</code> to a 'zoomed' <code>Rectangle</code>.
      * e.g. (100, 200) zoomed 200 percent would be converted to
      * (200, 400).
      * @param rect The <code>Rectangle</code>  to be converted
      * @return The converted <code>Rectangle</code>
      */
    public Rectangle rectangleToZoomedRectangle(Rectangle rect);

    /**
      * Converts a <code>Rectangle</code> from a 'zoomed' <code>Rectangle</code>.
      * e.g. (100, 200) zoomed 200 percent would be converted to
      * (50, 100).
      * @param rect The <code>Rectangle</code>  to be converted
      * @return The converted <code>Rectangle</code>
      */
    public Rectangle rectangleFromZoomedRectangle(Rectangle rect);

}
