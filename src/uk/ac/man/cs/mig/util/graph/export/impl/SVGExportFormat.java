package uk.ac.man.cs.mig.util.graph.export.impl;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import uk.ac.man.cs.mig.util.graph.controller.Controller;

import java.awt.*;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;


/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 20, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class SVGExportFormat extends AbstractVectorFormat
{
    private static final String FORMAT_NAME = "SVG";
    private static final String FORMAT_FILE_EXTENSION = "svg";
    private static final String FORMAT_DESCRIPTION = "Scalable Vector Graphics (SVG)";

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

    /**
     * Exports the graphics to the specified file.
     * @param controller The controller, which allows access to the
     * <code>GraphView</code> etc.
     * @param os The <code>OutputStream</code> that the exported format should
     * be written to.
     */
    public void export(Controller controller, OutputStream os)
    {
        Dimension size = controller.getGraphGenerator().getGraph().getShape().getBounds().getSize();

        int width = size.width;

        int height = size.height;

//        if(width > 0 && height > 0)
//        {
//            DOMImplementation domImpl = getDOMImpl();
//
//            Document document = domImpl.createDocument("www.cs.man.ac.uk/~horridgm", "svg", null);
//
//            // Get an instance of the svg generator
////            SVGGraphics2D graphics2D = new SVGGraphics2D(document);
////
////            graphics2D.getGeneratorContext().setPrecision(4);
////
////            graphics2D.setClip(0, 0, width + 10, height + 10);
////
////            if(getScale() != 100.0)
////            {
////                graphics2D.scale(getScale() / 100.0, getScale() / 100.0);
////            }
////
////            controller.getGraphView().draw(graphics2D, false, false, false, true);
////
////            try
////            {
////
////                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
////
////                try
////                {
////                    graphics2D.stream(osw, true);
////                }
////                catch(SVGGraphics2DIOException e)
////                {
////                    e.printStackTrace();
////                }
////            }
////            catch(UnsupportedEncodingException ueEx)
////            {
////                ueEx.printStackTrace();
////            }
////        }
//    }

//    /**
//     * Gets the DOMImlementation that we want to used.
//     * @return
//     */
//    protected DOMImplementation getDOMImpl()
//    {
////        return GenericDOMImplementation.getDOMImplementation();
//        return null;
//    }
    }
}

