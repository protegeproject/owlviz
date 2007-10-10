package uk.ac.man.cs.mig.util.graph.export;

import java.util.ArrayList;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 20, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * A Singleton class that is used to store and manage
 * various export formats.
 */
public class ExportFormatManager
{
    private ArrayList<ExportFormat> exportFormats;
    protected static ExportFormatManager instance;

    protected ExportFormatManager()
    {
        exportFormats = new ArrayList<ExportFormat>();
    }

    /**
     * Gets the one and only instance of the
     * ExportFormatManager.
     * @return The Singleton ExportFormatManager
     */
    public static ExportFormatManager getInstance()
    {
        // Create an instance if one has not been created.
        if(instance == null)
        {
            instance = new ExportFormatManager();
        }

        return instance;
    }


    /**
     * Adds an <code>ExportFormat</code> to the list
     * of possible <code>ExportFormat</code>s
     * @param exportFormat The <code>ExportFormat</code> to be added
     */
    public static void addExportFormat(ExportFormat exportFormat)
    {
        if(getInstance().exportFormats.contains(exportFormat) == false)
        {
            getInstance().exportFormats.add(exportFormat);
        }
    }

    /**
     * Removes an <code>ExportFormat</code> that was previously
     * added.
     * @param exportFormat The <code>ExportFormat</code> to remove.
     */
    public static void removeExportFormat(ExportFormat exportFormat)
    {
        getInstance().exportFormats.remove(exportFormat);
    }

    /**
     * Gets an array of the available <code>ExportFormats</code>
     * @return An array of <code>ExportFormats</code>. 
     */
    public static ExportFormat [] getExportFormats()
    {
        ExportFormat [] exportFormatArray = new ExportFormat [getInstance().exportFormats.size()];

        System.arraycopy(getInstance().exportFormats.toArray(), 0, exportFormatArray, 0, getInstance().exportFormats.size());

        return exportFormatArray;
    }

}
