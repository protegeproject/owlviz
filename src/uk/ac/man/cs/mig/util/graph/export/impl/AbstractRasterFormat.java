package uk.ac.man.cs.mig.util.graph.export.impl;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.log4j.Logger;

import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.graph.export.ExportFormat;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 20, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public abstract class AbstractRasterFormat implements ExportFormat {
    private static Logger log = Logger.getLogger(AbstractRasterFormat.class);

	private String format;
	private boolean antialiased = true;
	private double scale = 100.0;


	public AbstractRasterFormat(String format) {
		this.format = format;
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
		Iterator it = ImageIO.getImageWritersByFormatName(format);
		ImageWriter imageWriter = null;
		Dimension size = controller.getGraphGenerator().getGraph().getShape().getBounds().getSize();
		int width = size.width + 10;
		int height = size.height + 10;
		int scaledWidth = (int) (width * scale / 100.0);
		int scaledHeight = (int) (height * scale / 100.0);
		if(width > 0 && height > 0) {
			if(it.hasNext()) {
				imageWriter = (ImageWriter) it.next();
			}
			if(imageWriter != null) {
				// Get the width and height of the graph view
				try {
					// Create an image export stream that wraps the export stream
					ImageOutputStream ios = ImageIO.createImageOutputStream(os);
					imageWriter.setOutput(ios);

					// We want to write the graphview to a buffered image
					BufferedImage bufferedImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
					Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();

					// Clip the image at the proper bounds
					g2.setClip(0, 0, width, height);
					if(scale != 100.0) {
						g2.scale(scale / 100.0, scale / 100.0);
					}
					controller.getGraphView().draw(g2, false, false, antialiased, true);
					imageWriter.write(bufferedImage);
					ios.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
			else {
				log.error("No image writer available for the " + format + " format.");
			}
		}
	}


	/**
	 * Determines if the export is a raster format or not.
	 *
	 * @return <code>true</code> if the image is a raster format
	 *         such as png, jpeg, or <code>false</code> if the image is
	 *         not a raster format e.g. eps, svg.
	 */
	public boolean isRasterFormat() {
		return true;
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
		return true;
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
		antialiased = b;
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
		return antialiased;
	}


	/**
	 * Determines whether or not the export format supports
	 * the notion of scaling or not.  Raster formats such as png
	 * jpeg etc. typically can be scaled - vector formats such
	 * as eps and svg can be rescaled without loss in quality,
	 * and therefore do not support the notion of a fixed
	 * export scale.
	 *
	 * @return <code>true</code> if the export format supports
	 *         the notion of scaling, <code>false</code> if the export
	 *         format does not support scaling, or the scaling option
	 *         does not make sense.
	 */
	public boolean supportsScaledOutput() {
		return true;
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
		scale = percentage;
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
		return scale;
	}
}

