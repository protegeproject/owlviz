package uk.ac.man.cs.mig.coode.owlviz.export;

import uk.ac.man.cs.mig.coode.owlviz.ui.renderer.DotSubGraphOutputRenderer;
import uk.ac.man.cs.mig.coode.owlviz.ui.renderer.OWLClsNodeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.graph.export.ExportFormat;
import uk.ac.man.cs.mig.util.graph.renderer.impl.DefaultEdgeLabelRenderer;

import java.io.OutputStream;

import org.protege.editor.owl.model.OWLModelManager;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: May 27, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class DotExportFormat implements ExportFormat {

    private static final String FORMAT_NAME = "DOT";
    private static final String FORMAT_FILE_EXTENSION = "dot";
    private static final String FORMAT_DESCRIPTION = "AT&T Graphviz DOT Format";

    private OWLModelManager model;


    public DotExportFormat(OWLModelManager model) {
        this.model = model;
    }


    /**
     * Exports the graphics to the specified file.
     *
     * @param controller The controller, which allows access to the
     *                   <code>GraphView</code> etc.
     * @param os         The <code>OutputStream</code> that the exported format should
     *                   be written to.
     */
    public void export(Controller controller,
                       OutputStream os) {
        // Write out format

        DotSubGraphOutputRenderer renderer = new DotSubGraphOutputRenderer(model, new OWLClsNodeLabelRenderer(model),
                                                                           new DefaultEdgeLabelRenderer());
        renderer.renderGraph(controller.getGraphGenerator().getGraph(), os);
    }


    /**
     * If the export supports antialiasing, this method
     * ,ay be used to determine whether antialiasing
     * will be applied.
     *
     * @return <code>true</code> if antialiasing is applied,
     *         <code>false</code> if antialiasing is not applied.
     */
    public boolean getAntialiased() {
        return false;
    }


    /**
     * Gets a description of the export type, which is typically
     * displayed in the user interface when selecting an export
     * format.
     *
     * @return A description of the export type.
     */
    public String getFormatDescription() {
        return "DOT (AT&T Dot Format)";
    }


    /**
     * Gets the file extension for the export type.
     *
     * @return The export type file extensions (e.g. svg)
     */
    public String getFormatFileExtension() {
        return "dot";
    }


    /**
     * Gets the name of the export format.  This is
     * typically displayed in the user interface when selecting
     * an export format.
     *
     * @return The name of the export format (e.g. Scalable Vector Graphics)
     */
    public String getFormatName() {
        return "DOT";
    }


    /**
     * If the export format supports scaling, this
     * method can be used to obtain the scaling
     * applied to the export.
     *
     * @return The scaling applied to the export.  100.0
     *         is equivalent to 100 percent.
     */
    public double getScale() {
        return 100.0;
    }


    /**
     * Determines if the export is a raster format or not.
     *
     * @return <code>true</code> if the image is a raster format
     *         such as png, jpeg, or <code>false</code> if the image is
     *         not a raster format e.g. eps, svg.
     */
    public boolean isRasterFormat() {
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
    public void setAntialiased(boolean b) {
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
    public void setScale(double percentage) {
    }


    /**
     * Determine whether or not the format supports
     * antialiasing.
     *
     * @return <code>true</code> if the export format does
     *         support antialiasing, or <code>false</code> if the
     *         export format does not support antialiasing.
     */
    public boolean supportsAntialiasing() {
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
    public boolean supportsScaledOutput() {
        return false;
    }
}

