package uk.ac.man.cs.mig.coode.owlviz.ui.renderer;

import org.apache.log4j.Logger;
import org.protege.editor.owl.model.OWLModelManager;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLEntity;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLRuntimeException;
import uk.ac.man.cs.mig.coode.owlviz.ui.OWLVizIcons;
import uk.ac.man.cs.mig.coode.owlviz.ui.options.OWLVizOptions;
import uk.ac.man.cs.mig.util.graph.controller.Controller;
import uk.ac.man.cs.mig.util.graph.controller.VisualisedObjectManager;
import uk.ac.man.cs.mig.util.graph.graph.Node;
import uk.ac.man.cs.mig.util.graph.layout.GraphLayoutEngine;
import uk.ac.man.cs.mig.util.graph.renderer.NodeLabelRenderer;
import uk.ac.man.cs.mig.util.graph.renderer.NodeRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

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

    private static final Logger logger = Logger.getLogger(OWLClsNodeRenderer.class);


    private NodeLabelRenderer labelRenderer;

    private static Color fillColor;
    private static Color lineColor;
    private static Controller controller;
    private VisualisedObjectManager visualisedObjectManager;
    private Polygon leftArrow = new Polygon();
    private Polygon rightArrow = new Polygon();
    private FontMetrics fontMetrics;
    private static final int ARROW_SIZE = 5;
    private static final int HORIZONTAL_PADDING = ARROW_SIZE * 2 + 10;
    private static final int VERTICAL_PADDING = 15;
    private Font labelFont;
    private int layoutDirection = GraphLayoutEngine.LAYOUT_LEFT_TO_RIGHT;

    public static final String SOMECLS_NAME = "\u2203";
    public static final String ALLCLS_NAME = "\u2200";
    public static final String MINCARDI_NAME = "\u2264";
    public static final String MAXCARDI_NAME = "\u2265";
    public static final String CARDI_NAME = "=";
    public static final String INTERSECTION_NAME = "\u2293";
    public static final String UNION_NAME = "\u2294";
    public static final String COMPLEMENT_NAME = "\u00AC";
    public static final String HAS_NAME = "\u220B";
    public static final String ENUM_NAME = "{ }";

    OWLVizOptions options = OWLVizOptions.getInstance();

    // Colors

    private static final double NON_EDITABLE_FACTOR = 1.5;

    Color clrMetaClsFill = new Color(140, 255, 120);
    Color clrMetaClsOutline = Color.GRAY;
    Color clrMetaClsFillNonEditable = new Color(182, 252, 131);

    Color clrPrimitiveClsFill = new Color(255, 255, 153);
    Color clrPrimitiveClsOutline = Color.GRAY;
    Color clrPrimitiveClsFillNonEditable = new Color(255, 255, 204);

    Color clrDefinedClsFill = new Color(255, 200, 128);
    Color clrDefinedClsOutline = Color.GRAY;
    Color clrDefinedClsFillNonEditable = clrDefinedClsFill.brighter();

    Color clrLogicalClsFill = new Color(236, 210, 185);
    Color clrLogicalClsOutline = Color.GRAY;

    Color clrEnumClsFill = new Color(255, 204, 255);
    Color clrEnumClsOutline = Color.GRAY;

    Color clrHasRestrictionFill = new Color(255, 192, 152);
    Color clrHasRestrictionOutline = Color.GRAY;

    Color clrText = Color.BLACK;
    Color clrTextNonEditable = Color.GRAY;
    Color clrInconsistentClsColor = Color.RED;

    private Color clrConsistentAndChangedColor = Color.BLUE;

    private Icon disjointClassIcon;

    Stroke probeClassStroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 2.0f,
                                              new float[]{2.0f, 2.0f}, 2.0f);

    Color clrInstance = new Color(255, 204, 255);

    private static final int NOT_DISJOINT = 0;
    private static final int DIRECTLY_DISJOINT = 1;
    private static final int INHERITED_DIRECT_DISJOINT = 2;
    private static final int INHERITED_INDIRECT_DISJOINT = 3;


    private OWLModelManager owlModelManager;

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
        visualisedObjectManager = manager;
        this.controller = controller;
        disjointClassIcon = OWLVizIcons.getIcon(OWLVizIcons.DISJOINT_CLASS_INDICATOR_ICON);
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
        if (visualisedObjectManager == null) {
            throw new NullPointerException("DefaultNode renderer constructed before" + "VisualisedObjectManager");
        }
        this.labelRenderer = labelRenderer;
        leftArrow = new Polygon();
        rightArrow = new Polygon();
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

            if (drawDetail == true) {
                drawArrows(g2, sh, userObject);
                Object selObj = controller.getGraphSelectionModel().getSelectedObject();
                if (options.isDisplayDisjointClassIndicator() == true && selObj != null) {
                    if (selObj.equals(userObject) == false) {
                        if (selObj instanceof OWLClass && userObject instanceof OWLClass) {
                            int disjointStatus = areClassesDisjoint((OWLClass) userObject, (OWLClass) selObj);
                            if (disjointStatus != NOT_DISJOINT) {
                                //   g2.setColor(Color.MAGENTA);

                                Rectangle rect = sh.getBounds();

                                //   g2.drawString("D", rect.x, rect.y);

                                disjointClassIcon.paintIcon(null, g2, rect.x, rect.y);

                                //g2.draw(rect);
                            }
                        }
                    }
                }

                // Draw text

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
        Color color = Color.GRAY;
        if (obj instanceof OWLClass) {
            OWLClass cls = (OWLClass) obj;
            for (OWLOntology ont : owlModelManager.getActiveOntologies()) {
                if ((cls.isDefined(ont))) {
                    return this.clrDefinedClsFill;
                }
            }
            return this.clrPrimitiveClsFillNonEditable;
        }
        return color;
    }


    protected Color getLineColor(Object obj) {
        try {
            Color color = Color.GRAY;
            if (obj instanceof OWLClass) {
                    boolean consistent = owlModelManager.getReasoner().isSatisfiable((OWLClass) obj);
                    if (!consistent) {
                        color = clrInconsistentClsColor;
                    }

            }
            return color;
        }
        catch (OWLReasonerException e) {
            throw new OWLRuntimeException(e);
        }
    }


    protected Color getTextColor(Object obj) {
        try {
            Color color = Color.BLACK;
            if (obj instanceof OWLClass) {
                    boolean consistent = owlModelManager.getReasoner().isSatisfiable((OWLClass) obj);
                    if (!consistent) {
                        color = clrInconsistentClsColor;
                    }
            }
            return color;
        }
        catch (OWLReasonerException e) {
            throw new OWLRuntimeException(e);
        }
    }


    /**
     * Determines if a class is disjoint from another class
     *
     * @param cls1 The class to which we want to know if another class is disjoint
     * @param cls2 The class we want to test is disjoint to <code>cls1</code>
     * @return <code>true</code> if <code>cls2</code>  is disjoint to <code>cls1</code> ,
     *         <code>false</code> if <code>cls2</code>  is not disjoint to <code>cls1</code>.
     */
    protected int areClassesDisjoint(OWLClass cls1,
                                     OWLClass cls2) {
//        boolean ret = false;
//        Collection disjointClses;
//        Iterator disjointClsesIt;
//
//        // First Check to see if the classes are explicitly disjoint
//
//        disjointClses = cls1.getDisjointClasses();
//        if(disjointClses.contains(cls2)) {
//            return DIRECTLY_DISJOINT;
//        }
//
//
//        // Next check to see if one of cls1's disjoint classes is a super class of cls2
//
//        Collection cls2Supers = cls2.getSuperclasses();
//        disjointClsesIt = disjointClses.iterator();
//        while(disjointClsesIt.hasNext()) {
//            if(cls2Supers.contains(disjointClsesIt.next())) {
//                return INHERITED_DIRECT_DISJOINT;
//            }
//        }
//
//        // Next check the parents of cls1 to see if any disjoints are specified.
//
//        Collection cls1Supers = cls1.getSuperclasses(true);
//        Iterator superClsIt = cls1Supers.iterator();
//        while(superClsIt.hasNext()) // Iterate through super clses
//        {
//            Object obj = superClsIt.next();
//            if(obj instanceof OWLNamedClass) // If a superclass is a named class then it might have disjoints specified
//            {
//                OWLNamedClass namedCls = (OWLNamedClass) obj;
//                disjointClses = namedCls.getDisjointClasses();
//                disjointClsesIt = disjointClses.iterator();
//                while(disjointClsesIt.hasNext()) // Go through the disjoints
//                {
//                    Object disjointObj = disjointClsesIt.next();
//                    if(disjointObj instanceof OWLNamedClass) {
//                        OWLNamedClass disNamedCls = (OWLNamedClass) disjointObj;
//                        if(disNamedCls.equals(cls2)) {
//                            return INHERITED_INDIRECT_DISJOINT;
//                        }
//                        else if(cls2Supers.contains(disNamedCls)) {
//                            return INHERITED_INDIRECT_DISJOINT;
//                        }
//                    }
//                }
//            }
//        }
        return NOT_DISJOINT;
    }

}
