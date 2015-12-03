package uk.ac.man.cs.mig.coode.owlviz.ui.renderer;

import org.protege.editor.owl.model.OWLModelManager;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.graph.controller.VisualisedObjectManager;
import uk.ac.man.cs.mig.util.graph.graph.Node;
import uk.ac.man.cs.mig.util.graph.layout.GraphLayoutEngine;
import uk.ac.man.cs.mig.util.graph.renderer.NodeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.NodeRenderer;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 5, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class OWLClsNodeRenderer implements NodeRenderer {

    private static final Logger logger = LoggerFactory.getLogger(OWLClsNodeRenderer.class);

    private static final int ARROW_SIZE = 5;

    private static final int HORIZONTAL_PADDING = ARROW_SIZE * 2 + 10;

    private static final int VERTICAL_PADDING = 15;

    private static final Color PRIMITIVE_CLASS_FILL = new Color(255, 255, 204);

    private static final Color DEFINED_CLASS_FILL = new Color(255, 200, 128);

    private static final Color UNSATISFIABLE_CLASS_COLOR = Color.RED;


    private final Polygon leftArrow = new Polygon();

    private final Polygon rightArrow = new Polygon();



    private final Controller controller;

    private final VisualisedObjectManager visualisedObjectManager;

    private final FontMetrics fontMetrics;

    private final Font labelFont;

    private int layoutDirection = GraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT;


    private final OWLModelManager owlModelManager;

    public OWLClsNodeRenderer(Controller controller,
                              VisualisedObjectManager manager,
                              NodeLabelRenderer labelRenderer,
                              OWLModelManager owlModelManager) {
        this.owlModelManager = owlModelManager;
        if (manager == null) {
            throw new NullPointerException("VisualisedObjectManager must not be null");
        }
        if (labelRenderer == null) {
            throw new NullPointerException("NodeLabelRenderer must not be null");
        }
        this.controller = controller;
        visualisedObjectManager = manager;
        JPanel pan = new JPanel();
        Font font = pan.getFont();
        labelFont = font.deriveFont(10.0f);
        if (labelFont == null) {
            logger.warn("Font is NULL!");
        }
        fontMetrics = pan.getFontMetrics(labelFont);
        if (fontMetrics == null) {
            logger.warn("Font metrics is NULL!");
        }
        setupArrows();
    }


    protected void setupArrows() {
        if (controller.getGraphLayoutEngine().getLayoutDirection() == GraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT) {
            leftArrow.reset();
            leftArrow.addPoint(ARROW_SIZE, -ARROW_SIZE);
            leftArrow.addPoint(0, 0);
            leftArrow.addPoint(ARROW_SIZE, ARROW_SIZE);
            rightArrow.reset();
            rightArrow.addPoint(-ARROW_SIZE, -ARROW_SIZE);
            rightArrow.addPoint(0, 0);
            rightArrow.addPoint(-ARROW_SIZE, ARROW_SIZE);
        } else {
            leftArrow.reset();

            // Up Arrow
            leftArrow.addPoint(-ARROW_SIZE, ARROW_SIZE);
            leftArrow.addPoint(0, 0);
            leftArrow.addPoint(ARROW_SIZE, ARROW_SIZE);
            rightArrow.reset();
            // Down arrow
            rightArrow.addPoint(-ARROW_SIZE, -ARROW_SIZE);
            rightArrow.addPoint(0, 0);
            rightArrow.addPoint(ARROW_SIZE, -ARROW_SIZE);
        }
    }


    protected void drawArrows(Graphics2D g2,
                              Shape nodeShape,
                              Object userObject) {
        if (controller.getGraphLayoutEngine().getLayoutDirection() != layoutDirection) {
            layoutDirection = controller.getGraphLayoutEngine().getLayoutDirection();
            setupArrows();
        }
        if (layoutDirection == GraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT) {
            if (visualisedObjectManager.getChildrenHiddenCount(userObject) > 0) {
                Rectangle rect = nodeShape.getBounds();
                g2.translate(rect.x + rect.width, rect.y + rect.height / 2);
                g2.fill(rightArrow);
                g2.translate(-rect.x - rect.width, -rect.y - rect.height / 2);
            }
            if (visualisedObjectManager.getParentsHiddenCount(userObject) > 0) {
                Rectangle rect = nodeShape.getBounds();
                g2.translate(rect.x, rect.y + rect.height / 2);
                g2.fill(leftArrow);
                g2.translate(-rect.x, -rect.y - rect.height / 2);
            }
        } else {
            if (visualisedObjectManager.getChildrenHiddenCount(userObject) > 0) {
                Rectangle rect = nodeShape.getBounds();
                g2.translate(rect.x + rect.width / 2, rect.y + rect.height);
                g2.fill(rightArrow);
                g2.translate(-rect.x - rect.width / 2, -rect.y - rect.height);
            }
            if (visualisedObjectManager.getParentsHiddenCount(userObject) > 0) {
                Rectangle rect = nodeShape.getBounds();
                g2.translate(rect.x + rect.width / 2, rect.y);
                g2.fill(leftArrow);
                g2.translate(-rect.x - rect.width / 2, -rect.y);
            }
        }
    }


    /**
     * Generic shape renderer.  Typically, the shape of the <code>Node</code>
     * will be a <code>Rectangle</code> or an <code>Ellipse</code>.
     *
     * @param node        The <code>Node</code> being rendered.
     * @param g2          The Graphics2D object on to which the <code>Node</code> should be rendered.
     * @param forPrinting A flag to indicate if the graphics are being drawn to produce an
     *                    image for printing, or to draw onto the screen.
     */
    public void renderNode(Graphics2D g2,
                           Node node,
                           boolean forPrinting,
                           boolean drawDetail) {
        Shape sh = node.getShape();

        // Only render if we are within the clip bounds

        Rectangle clipBounds = g2.getClipBounds();
        clipBounds.grow(10, 10);
        if (sh.intersects(clipBounds)) {
            // Fill the node

            Object userObject = node.getUserObject();

            // Fill the node with the correct colour
            g2.setColor(getFillColor(userObject));
            g2.fill(sh);

            // Line color

            g2.setColor(getLineColor(userObject));
            g2.draw(sh);
            g2.setColor(Color.BLACK);

            // Draw expansion arrows

            if (drawDetail) {
                drawArrows(g2, sh, userObject);


                Point pos = node.getPosition();
                String label = " ";
                label = owlModelManager.getRendering((OWLEntity) userObject);
                Font f = g2.getFont();
                g2.setFont(labelFont);
                g2.setColor(getTextColor(userObject));
                Rectangle2D labelBounds2D = g2.getFontMetrics().getStringBounds(label, g2);
                Rectangle labelBounds = labelBounds2D.getBounds();
                g2.drawString(label, pos.x - labelBounds.width / 2, pos.y + labelBounds.height / 3);
                g2.setFont(f);
            }
        }
    }


    /**
     * Compute the size of the <code>Node</code>
     */
    public Dimension getPreferredSize(Node node,
                                      Dimension size) {
        if (node.getUserObject() instanceof OWLClass) {
            String label = owlModelManager.getRendering(((OWLClass) node.getUserObject()));
            int width = SwingUtilities.computeStringWidth(fontMetrics, label);
            int height = fontMetrics.getHeight();
            if (size != null) {
                size.width = width + HORIZONTAL_PADDING;
                size.height = height + VERTICAL_PADDING;
                return size;
            } else {
                return new Dimension(width, height);
            }
        } else {
            size.width = 20;
            size.height = 20;
            return size;
        }
    }


    protected Color getFillColor(Object obj) {
        if (obj instanceof OWLClass) {
            OWLClass cls = (OWLClass) obj;
            if ((EntitySearcher.isDefined(cls, owlModelManager.getActiveOntologies()))) {
                return DEFINED_CLASS_FILL;
            }
            return PRIMITIVE_CLASS_FILL;
        }
        return Color.GRAY;
    }


    protected Color getLineColor(Object obj) {
        if (obj instanceof OWLClass) {
            if (getReasoner().isConsistent()) {
                boolean sat = getReasoner().isSatisfiable((OWLClass) obj);
                if (!sat) {
                    return UNSATISFIABLE_CLASS_COLOR;
                }
            }
        }
        return Color.GRAY;
    }

    private OWLReasoner getReasoner() {
        return owlModelManager.getReasoner();
    }


    protected Color getTextColor(Object obj) {
        if (obj instanceof OWLClass) {
            if (getReasoner().isConsistent()) {
                boolean sat = getReasoner().isSatisfiable((OWLClass) obj);
                if (!sat) {
                    return UNSATISFIABLE_CLASS_COLOR;
                }
            }
        }
        return Color.BLACK;
    }
}
