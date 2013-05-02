package uk.ac.man.cs.mig.coode.owlviz.ui;

import javax.swing.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 24, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class OWLVizIcons {

	// A hashmap to map icon names to ImageIcons
	private static Map iconMap = new HashMap();

	public static final String SHOW_CLASS_ICON = "ShowClassIcon.png";
	public static final String SHOW_SUBCLASSES_ICON = "ShowSubclassesIcon.png";
	public static final String SHOW_SUPERCLASSES_ICON = "ShowSuperclassesIcon.png";
	public static final String HIDE_CLASS_ICON = "HideClassIcon.png";
	public static final String HIDE_SUBCLASSES_ICON = "HideSubclassesIcon.png";
	public static final String HIDE_SUPERCLASSES_ICON = "HideSuperclassesIcon.png";
	public static final String HIDE_ALL_CLASSES_ICON = "HideAllClassesIcon.png";
	public static final String OWLVIZ_ICON = "ShowSubclassesIcon.png";
	public static final String ZOOM_OUT_ICON = "ZoomOut.gif";
	public static final String ZOOM_IN_ICON = "ZoomIn.gif";
	public static final String EXPORT_ICON = "ExportIcon.gif";
	public static final String OPTIONS_ICON = "OptionsIcon.gif";
	public static final String SHOW_PRIMITIVE_CLASSES_ONLY = "ShowPrimitiveClassesOnlyIcon.gif";
	public static final String SHOW_PRIMITIVE_CLASSES_ONLY_SELECTED = "ShowPrimitiveClassesOnlyIconSelected.gif";
	public static final String POPUP_ADVANCE_ICON = "PopupAdvanceIcon.gif";
	public static final String HIDE_CLASSES_PAST_RADIUS_ICON = "HideClassesPastRadiusIcon.png";
	public static final String DISJOINT_CLASS_INDICATOR_ICON = "DisjointClassIndicatorIcon.gif";
	public static final String SHOW_ALL_CLASSES_ICON = "ShowAllClassesIcon.png";
	public static final String REFRESH_ICON = "RefreshIcon.gif";

	public static final String UGLY_ICON = "Ugly.gif";

	public static final String RELATIVE_PATH = "image/";


	/**
	 * A static block, which loads the default icon.
	 * If other icons cannot be loaded then this icon
	 * is used.
	 */
	static {
		ImageIcon uglyIcon = loadIcon(UGLY_ICON);
		iconMap.put(UGLY_ICON, uglyIcon);
	}


	/**
	 * Gets the <code>ImageIcon</code> with the specified
	 * name.
	 *
	 * @param iconName The name of the icon to load.  This is
	 *                 typically the name of the .gif image file.
	 * @return The <code>ImageIcon</code> that corresponds to the
	 *         specified name.  Note that if the icon cannot be loaded
	 *         then the default icon will be returned.
	 */
	public static ImageIcon getIcon(String iconName) {
		ImageIcon imageIcon = (ImageIcon) iconMap.get(iconName);
		if(imageIcon == null) {
			imageIcon = loadIcon(iconName);
			iconMap.put(iconName, imageIcon);
		}
		return imageIcon;
	}


	/**
	 * Loads and caches an <code>ImageIcon</code> that represents
	 * the icon that corresponds to the specified file name.
	 *
	 * @param iconName The icon name.
	 * @return The <code>ImageIcon</code> that corresponds
	 *         to the name specified.
	 */
	protected static ImageIcon loadIcon(String iconName) {
		ImageIcon imageIcon = null;
		URL iconURL = OWLVizIcons.class.getResource(RELATIVE_PATH + iconName);
		if(iconURL != null) {
			imageIcon = new ImageIcon(iconURL);
		}

		// If the icon cannot be loaded then return the default icon.
		if(imageIcon == null) {
			imageIcon = (ImageIcon) iconMap.get(UGLY_ICON);
		}
		return imageIcon;
	}

}

