package uk.ac.man.cs.mig.util.wizard;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 6, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public interface WizardEventListener
{
	public void nextPressed(WizardEvent e);

	public void prevPressed(WizardEvent e);

	public void pageChanged(WizardEvent e);

	public void finishPressed(WizardEvent e);

	public void cancelPressed(WizardEvent e);
}
