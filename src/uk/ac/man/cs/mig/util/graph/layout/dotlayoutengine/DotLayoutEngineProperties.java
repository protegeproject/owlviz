package uk.ac.man.cs.mig.util.graph.layout.dotlayoutengine;

import org.protege.editor.core.prefs.Preferences;
import org.protege.editor.core.prefs.PreferencesManager;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Sep 19, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class DotLayoutEngineProperties {

    public static final String PREFERENCES_SET_KEY = "uk.ac.man.cs.mig.coode.owlviz";

    public static final String PREFERENCES_KEY = "OWLVizPrefs";

    public static final String PROCESS_PATH_KEY = "ProcessPath";

    public static final String RANK_SPACING_KEY = "RankSpacing";

    public static final String SIBLING_SPACING_KEY = "SiblingSpacing";

    private static DotLayoutEngineProperties instance;

    public static final String DEFAULT_MAC_PATH = "/Applications/Graphviz.app/Contents/MacOS/dot";//"/usr/local/bin/dot";
    public static final String DEFAULT_WINDOWS_PATH = "C:\\Program Files\\GraphViz\\bin\\Dot"; // was "C:\\Program Files\\ATT\\GraphViz\\bin\\Dot" until Mar 08
    public static final String DEFAULT_LINUX_PATH = "/usr/bin/dot";


    private static final double DEFAULT_SIBLING_SPACING = 0.2;
    public static final double DEFAULT_RANK_SPACING = 0.5;

    private static String FILE_NAME = "DotScratch";


    private double rankSpacing;
    private double siblingSpacing;

    private String processPath;


    protected DotLayoutEngineProperties() {
        loadFromPrefs();
    }

    private static String getDefaultPath() {
        // Setup the default path for the platform
        String platform = System.getProperty("os.name");

        if(platform.indexOf("OS X") != -1) {
            // On Mac platform
            return DEFAULT_MAC_PATH;
        }
        else if(platform.indexOf("Windows") != -1) {
            // On Windows
            return DEFAULT_WINDOWS_PATH;
        }
        else {
            // Linux or Unix or whatever.
            return DEFAULT_LINUX_PATH;
        }
    }


    public static synchronized DotLayoutEngineProperties getInstance() {
        if(instance == null) {
            instance = new DotLayoutEngineProperties();
        }

        return instance;
    }

    private static Preferences getPreferences() {
        return PreferencesManager.getInstance().getPreferencesForSet(PREFERENCES_SET_KEY, PREFERENCES_KEY);
    }

    private void loadFromPrefs() {
        processPath = getPreferences().getString(PROCESS_PATH_KEY, getDefaultPath());
        rankSpacing = getPreferences().getDouble(RANK_SPACING_KEY, DEFAULT_RANK_SPACING);
        siblingSpacing = getPreferences().getDouble(SIBLING_SPACING_KEY, DEFAULT_SIBLING_SPACING);
    }

    private void savePrefs() {
        getPreferences().putString(PROCESS_PATH_KEY, processPath);
        getPreferences().putDouble(RANK_SPACING_KEY, rankSpacing);
        getPreferences().putDouble(SIBLING_SPACING_KEY, siblingSpacing);
    }


    public String getDotProcessPath() {
        return processPath;
    }


    public void setDotProcessPath(String path) {
        if(System.getProperty("os.name").indexOf("OS X") != -1) {
            // On Mac
            if(path.endsWith(".app")) {
                // Graphviz Pixel Glow bundle - go inside bundle
                // Append Contents/MacOs/dot
                path += "/Contents/MacOS/dot";
            }
        }
        processPath = path;
        savePrefs();
    }


    public double getRankSpacing() {
        return rankSpacing;
    }


    public void setRankSpacing(double rankSpacing) {
        this.rankSpacing = rankSpacing;
        savePrefs();
    }


    public double getSiblingSpacing() {
        return siblingSpacing;
    }


    public void setSiblingSpacing(double siblingSpacing) {
        this.siblingSpacing = siblingSpacing;
        savePrefs();
    }
}

