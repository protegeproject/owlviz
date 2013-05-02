package uk.ac.man.cs.mig.coode.owlviz.ui.options;

import uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine.DotGraphLayoutEngine;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jun 17, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 * <p/>
 * OWLVizProperties holds all of the options or properties
 * used by OWLViz.
 *
 * @deprecated
 */
public class OWLVizProperties {

	public static final String FILE_ROOT = "plugins/uk.ac.man.cs.mig.coode.owlviz";
	public static final String PROPERTY_FILE_PATH_NAME = FILE_ROOT + "/OWLVizProperties";

	/**
	 * The path to the DOT application.
	 */
	public static final String DOT_PATH = "OWLViz.DOT.DotPath";

	/**
	 * The rank spacing used by DOT
	 */
	public static final String RANK_SPACING = "OWLViz.DOT.RankSpacing";

	/**
	 * The sibling spacing used by DOT
	 */
	public static final String SIBLING_SPACING = "OWLViz.DOT.SiblingSpacing";

	/**
	 * The direction of the graph layout. Should be either "LR" or "TB"
	 */
	public static final String LAYOUT_DIRECTION = "OWLViz.DOT.LayoutDirection";

	/**
	 * The <code>String</code> value of a <code>Boolean</code> that
	 * indicated whether classes that are disjoint to the selected
	 * class should be flagged.
	 */
	public static final String DISPLAY_DISJOINT_INDICATOR = "OWLViz.DisplayDisjointClassIndicator";

	/**
	 * The <code>String</code> value of a <code>Boolean</code> that
	 * indicated whether individuals should be displayed.
	 */
	public static final String DISPLAY_INDIVIDUALS = "OWLViz.DisplayIndividuals";

	/**
	 * The <code>String</code> value of a <code>Boolean</code> that
	 * indicated whether anonymous classes should be displayed.
	 */
	public static final String DISPLAY_ANONYMOUS_CLASSES = "OWLViz.DisplayAnonymousClasses";

	/**
	 * A <code>String</code> value that is the name of the
	 * popup page that is displayed when the mouse hovers
	 * over a node that represents an element from the ontology.
	 */
	public static final String POPUP_DISPLAY_PAGE = "OWLViz.PopupDisplayPage";

	/**
	 * A <code>String</code> representation of a double valuet that
	 * represents the brightness of the edges in the graph.
	 */
	public static final String EDGE_BRIGHTNESS = "OWLViz.EdgeBrightness";

	/**
	 * A <code>String</code> that represents a <code>Boolean</code> value
	 * that indicates whether or not classes should be grouped by namespace.
	 */
	public static final String GROUP_CLASSES_BY_NAMESPACE = "OWLViz.GroupClassesByNamespace";

	/**
	 * A <code>String</code> that represents a <code>Boolean</code> value
	 * that indicates whether or not is-a labels should be displayed.
	 */
	public static final String DISPLAY_IS_A_LABELS = "OWLViz.DisplayIsALabels";

	private static OWLVizProperties owlVizProperties = null;

	private Properties properties;

	private ArrayList<WeakReference<PropertyChangeListener>> listeners;


	private OWLVizProperties() {
		properties = new Properties();
		properties.setProperty(DISPLAY_ANONYMOUS_CLASSES, Boolean.FALSE.toString());
		properties.setProperty(DISPLAY_DISJOINT_INDICATOR, Boolean.TRUE.toString());
		properties.setProperty(DISPLAY_INDIVIDUALS, Boolean.FALSE.toString());
		properties.setProperty(EDGE_BRIGHTNESS, Double.toString(0.5));
		properties.setProperty(POPUP_DISPLAY_PAGE, "Conditions");
		properties.setProperty(GROUP_CLASSES_BY_NAMESPACE, Boolean.FALSE.toString());
		properties.setProperty(LAYOUT_DIRECTION, Integer.toString(DotGraphLayoutEngine.LAYOUT_TOP_TO_BOTTOM));
		properties.setProperty(RANK_SPACING, Double.toString(1.0));
		properties.setProperty(SIBLING_SPACING, Double.toString(0.2));
		listeners = new ArrayList<WeakReference<PropertyChangeListener>>();
	}


	/**
	 * Gets the one and only instance of the OWLViz
	 * properties.
	 *
	 * @return An instance of <code>OWLVizProperties</code>
	 */
	public static OWLVizProperties getProperties() {
		if(owlVizProperties == null) {
			owlVizProperties = new OWLVizProperties();
		}
		return owlVizProperties;
	}


	/**
	 * Gets a value for the property.
	 *
	 * @param propertyName The name of the property.
	 * @param defaultValue The value that should be returned
	 *                     if there is no current value for the property.
	 * @return The value of the property.
	 */
	public String getProperty(String propertyName,
	                          String defaultValue) {
		return properties.getProperty(propertyName, defaultValue);
	}


	/**
	 * Sets the value for a property.  If the property does
	 * not exist then it is creaeted.
	 *
	 * @param propertyName The name of the property.
	 * @param newValue     The value of the property.
	 */
	public void setProperty(String propertyName,
	                        String newValue) {
		properties.put(propertyName, newValue);
	}


	/**
	 * Gets a value for a boolean property.
	 *
	 * @param propertyName The name of the property
	 * @param defaultValue The default value which is returned
	 *                     if the property does not exist.
	 * @return The boolean value of the property.
	 */
	public boolean getBooleanProperty(String propertyName,
	                                  boolean defaultValue) {
		String value = getProperty(propertyName, null);
		if(value == null) {
			return defaultValue;
		}
		else {
			boolean booleanValue = Boolean.getBoolean(value);
			return booleanValue;
		}
	}


	/**
	 * Sets a boolean property
	 *
	 * @param propertyName The name of the property
	 * @param newValue     The value to be set.
	 */
	public void setBooleanProperty(String propertyName,
	                               boolean newValue) {
		setProperty(propertyName, Boolean.toString(newValue));
	}


	/**
	 * Gets a value for an integer property.
	 *
	 * @param propertyName The name of the property
	 * @param defaultValue The default value which is returned
	 *                     if the property does not exist.
	 * @return The integer value of the property.
	 */
	public int getIntProperty(String propertyName,
	                          int defaultValue) {
		String value = getProperty(propertyName, null);
		if(value == null) {
			return defaultValue;
		}
		else {
			int intValue = Integer.parseInt(value);
			return intValue;
		}
	}


	/**
	 * Sets an integer property
	 *
	 * @param propertyName The name of the property
	 * @param newValue     The value to be set.
	 */
	public void setIntProperty(String propertyName,
	                           int newValue) {
		setProperty(propertyName, Integer.toString(newValue));
	}


	/**
	 * Gets a value for a double valued property.
	 *
	 * @param propertyName The name of the property
	 * @param defaultValue The default value which is returned
	 *                     if the property does not exist.
	 * @return The double value of the property.
	 */
	public double getDoubleProperty(String propertyName,
	                                double defaultValue) {
		String value = getProperty(propertyName, null);
		if(value == null) {
			return defaultValue;
		}
		else {
			double doubleValue = Double.parseDouble(value);
			return doubleValue;
		}
	}


	/**
	 * Sets an integer property
	 *
	 * @param propertyName The name of the property
	 * @param newValue     The value to be set.
	 */
	public void setDoubleProperty(String propertyName,
	                              double newValue) {
		setProperty(propertyName, Double.toString(newValue));
	}


	/**
	 * Loads the properties from file.
	 */
	public void loadProperties() {
		try {
			FileInputStream fis = new FileInputStream(PROPERTY_FILE_PATH_NAME);
			properties.load(fis);
		}
		catch(FileNotFoundException fnfEx) {
			// Properties file not found - DO NOTHING!
		}
		catch(IOException ioEx) {
			ioEx.printStackTrace();
		}
	}


	/**
	 * Saves the properties to file.
	 */
	public void saveProperties() {
		try {
			FileOutputStream fos = new FileOutputStream(PROPERTY_FILE_PATH_NAME);
			properties.store(fos, "OWLViz Properties");
		}
		catch(FileNotFoundException fnfEx) {
			// Don't do anything
		}
		catch(IOException ioEx) {
			ioEx.printStackTrace();
		}
	}


	/**
	 * Adds a property changes listener that is informed
	 * of changes in properties.
	 *
	 * @param lsnr The listener to be added.
	 */
	public void addPropertyChangeListener(PropertyChangeListener lsnr) {
		listeners.add(new WeakReference(lsnr));
	}


	/**
	 * Removes are previously added property change listener
	 *
	 * @param lsnr
	 */
	public void removePropertyChangeListener(PropertyChangeListener lsnr) {
		Iterator<WeakReference<PropertyChangeListener>> it = listeners.iterator();
		WeakReference<PropertyChangeListener> ref;
		while(it.hasNext()) {
			ref = it.next();
			if(ref.get() == lsnr) {
				it.remove();
				break;
			}
		}
	}


	/**
	 * Causes a <code>PropertyChangeEvent</code> to be sent to all listeners
	 *
	 * @param propertyName The property name.
	 * @param oldValue     The old value of the property.
	 * @param newValue     The new value of the property.
	 */
	protected void firePropertyChangeEvent(String propertyName,
	                                       String oldValue,
	                                       String newValue) {
		Iterator<WeakReference<PropertyChangeListener>> it = listeners.iterator();
		PropertyChangeEvent evt = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
		WeakReference<PropertyChangeListener> ref;
		while(it.hasNext()) {
			ref = it.next();
			if(ref.get() != null) {
				ref.get().propertyChange(evt);
			}
			else {
				// If there is no longer a refernece to the
				// listener remove the listener from
				// this list.
				it.remove();
			}
		}
	}

}

