package uk.ac.man.cs.mig.util.graph.export.impl;

import uk.ac.man.cs.mig.util.graph.export.ExportFormat;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 27, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public abstract class AbstractVectorFormat implements ExportFormat
{
    private double scale = 100.0;

    /**
     * Determines if the export is a raster format or not.
     *
     * @return <code>true</code> if the image is a raster format
     *         such as png, jpeg, or <code>false</code> if the image is
     *         not a raster format e.g. eps, svg.
     */
    public boolean isRasterFormat()
    {
        return false;
    }

    /**
     * Determine whether or not the format supports
     * antialiasing.
     *
     * @return <code>true</code> if the export format does
     *         support antialiasing, or <code>false</code> if the
     *         export format does not support antialiasing.
     */
    public boolean supportsAntialiasing()
    {
        return false;
    }

    /**
     * If the export is a raster format it may support
     * antialiasing.  This method can be used to specifiy
     * whether anti alisaing should be used or not.
     *
     * @param b <code>true</code> if antialiasing should be
     *          used, or <code>false</code> if antialiasing should not
     *          be used.
     */
    public void setAntialiased(boolean b)
    {
        // Don't do anything here
    }

    /**
     * If the export supports antialiasing, this method
     * ,ay be used to determine whether antialiasing
     * will be applied.
     *
     * @return <code>true</code> if antialiasing is applied,
     *         <code>false</code> if antialiasing is not applied.
     */
    public boolean getAntialiased()
    {
        return false;
    }

    /**
     * Determines whether or not the export format supports
     * the notion of scaling or not.  Raster formats such as png
     * jpeg etc. typically can be scaled - vector formats such
     * as eps and svg can be rescaled without loss in quality,
     * and therefore do not typically support the notion of a fixed
     * export scale.
     *
     * @return <code>true</code> if the export format supports
     *         the notion of scaling, <code>false</code> if the export
     *         format does not support scaling, or the scaling option
     *         does not make sense.
     */
    public boolean supportsScaledOutput()
    {
        // Default to true
        return false;
    }

    /**
     * If the export format supports the notion of outputting
     * to a fixed scale, then this method may be used to
     * set the scale of the export.
     *
     * @param percentage The percentage of original size that
     *                   the export should be scaled to.  100.0 equates to one
     *                   hundred percent i.e. original size.
     */
    public void setScale(double percentage)
    {
        //scale = percentage;
    }

    /**
     * If the export format supports scaling, this
     * method can be used to obtain the scaling
     * applied to the export.
     *
     * @return The scaling applied to the export.  100.0
     *         is equivalent to 100 percent.
     */
    public double getScale()
    {
        return scale;
    }
}

