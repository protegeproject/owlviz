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
public class WizardEvent
{
	private Wizard source; // The Wizard associated with this event.

	/**
	 * Constructs a new Wizard event, that is associated with
	 * the specified <code>Wizard</code>.
	 * @param source The <code>Wizard</code> that is the source of the event.
	 */
	public WizardEvent(Wizard source)
	{
		this.source = source;
	}

	/**
	 * Gets the source of the <code>Wizard</code> event.
	 * @return The <code>Wizard</code> that is the source of the event.
	 */
	public Wizard getSource()
	{
		return source;
	}


}
