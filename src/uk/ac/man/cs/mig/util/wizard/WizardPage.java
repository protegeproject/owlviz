package uk.ac.man.cs.mig.util.wizard;

import javax.swing.*;
import java.awt.*;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 6, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * Represents a page held by a <code>Wizard</code>
 * @see Wizard
 */
public class WizardPage extends JComponent
{
	private String name;

    // Package
    Wizard wizard;

    /**
     * Constructs a <code>WizardPage</code> using the
     * specified name.
     * @param name The name of the <code>WizardPage</code>
     */
	public WizardPage(String name)
	{
		setLayout(new BorderLayout());

		this.name = name;
	}

    /**
     * Package level visibility.  Sets the <code>Wizard</code> that
     * this page belongs to.
     * @param wizard The <code>Wizard</code>
     */
    void setWizard(Wizard wizard)
    {
        this.wizard = wizard;
    }

    /**
     * Gets the <code>Wizard</code> that this page belongs to.
     * @return The parent <code>Wizard</code>
     */
    public Wizard getWizard()
    {
        return wizard;
    }


    /**
     * Gets the name of this <code>WizardPage</code> .
     * @return The name of this <code>WizardPage</code>
     */
	public String getName()
	{
		return name;
	}

    /**
     * Called when this page is being displayed
     * and the next button was pressed.  Override this
     * method to be informed of this event.
     * @param w The <code>Wizard</code> that the
     * page belongs to.
     */
	public void nextButtonPressed(Wizard w)
	{

	}

    /**
     * Called when this page is being displayed
     * and the previous button was pressed.Override this
     * method to be informed of this event.
     * @param w The <code>Wizard</code> that the
     * page belongs to.
     */
	public void prevButtonPressed(Wizard w)
	{

	}

    /**
     * Called when the page is displayed. Override this method to be informed
     * of this event.
     * @param w The <code>Wizard</code> to which the
     * page belongs.
     */
	public void pageSelected(Wizard w)
	{
		
	}

    /**
     * Called when the page has been added to a
     * <code>Wizard</code>, and the <code>Wizard</code>
     * has set itself as the 'owner' of the page.  Override
     * this method to be informed of this event.
     */
    public void wizardSet(Wizard w)
    {

    }
}
