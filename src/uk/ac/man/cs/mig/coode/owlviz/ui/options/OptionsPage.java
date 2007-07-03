package uk.ac.man.cs.mig.coode.owlviz.ui.options;

import javax.swing.*;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Mar 1, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public abstract class OptionsPage extends JPanel {

	public abstract void updateInterface();


	public abstract void validateOptions();


	public abstract void applyOptions();
}
