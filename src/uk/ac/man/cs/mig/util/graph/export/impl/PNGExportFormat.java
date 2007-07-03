package uk.ac.man.cs.mig.util.graph.export.impl;



/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 20, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class PNGExportFormat extends AbstractRasterFormat
{
    private static final String FORMAT_NAME = "PNG";
    private static final String FORMAT_FILE_EXTENSION = "png";
    private static final String FORMAT_DESCRIPTION = "Portable Network Graphics (PNG)";


    public PNGExportFormat()
    {
        super("PNG");
    }

    /**
     * Gets the name of the export format.  This is
     * typically displayed in the user interface when selecting
     * an export format.
     *
     * @return The name of the export format (e.g. Scalable Vector Graphics)
     */
    public String getFormatName()
    {
        return FORMAT_NAME;
    }

    /**
     * Gets the file extension for the export type.
     *
     * @return The export type file extensions (e.g. svg)
     */
    public String getFormatFileExtension()
    {
        return FORMAT_FILE_EXTENSION;
    }

    /**
     * Gets a description of the export type, which is typically
     * displayed in the user interface when selecting an export
     * format.
     *
     * @return A description of the export type.
     */
    public String getFormatDescription()
    {
        return FORMAT_DESCRIPTION;
    }
}

