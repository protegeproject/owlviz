package org.coode.owlviz.util.graph.layout.dotlayoutengine;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 16, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 * <p/>
 * A wrapper for a native dot process.
 */
public class DotProcess {

    private static final Logger log = LoggerFactory.getLogger(DotProcess.class);

    private Process process;


    /**
     * Contructs a <code>DotProcess</code>, and starts
     * the native dot process. Using the default process
     * path for the particular platform being used.
     */
    public DotProcess() {

    }


    /**
     * Lays out a graph using the dot application
     *
     * @param fileName A file that acts as a 'scratch pad'
     *                 The graph is read from this file, and then export
     *                 to the same file in attributed dot format.
     * @return <code>true</code>  if the process completed without
     * any errors, or <code>false</code> if the process did not
     * complete.
     */
    public boolean startProcess(String fileName) {
        if (process != null) {
            killProcess();
        }

        Runtime r = Runtime.getRuntime();
        DotLayoutEngineProperties properties = DotLayoutEngineProperties.getInstance();

        try {
            log.debug("[OWLViz] Executing dot at {}", properties.getDotProcessPath());
            String[] processPath = new String[]{properties.getDotProcessPath(), fileName, "-q", "-o", fileName};
            process = r.exec(processPath);
            process.waitFor();
            return true;
        } catch (IOException ioEx) {
            String errMsg = "An error related to DOT has occurred. " + "This error was probably because OWLViz could not"
                    + " find the DOT application.  Please ensure that the"
                    + " path to the DOT application is set properly";
            log.error(errMsg, ioEx);
            return false;
        } catch (InterruptedException irEx) {
            log.error("[OWLViz] Error whilst waiting for DOT to finish", irEx);
            return false;
        }
    }


    /**
     * Kills the native dot process (if it was started
     * successfully).
     */
    protected void killProcess() {
        if (process != null) {
            process.destroy();
            process = null;
        }
    }
}
