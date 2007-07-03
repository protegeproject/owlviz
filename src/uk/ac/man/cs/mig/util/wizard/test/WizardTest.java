package uk.ac.man.cs.mig.util.wizard.test;

import uk.ac.man.cs.mig.util.wizard.Wizard;
import uk.ac.man.cs.mig.util.wizard.WizardPage;
import uk.ac.man.cs.mig.util.wizard.WizardEventListener;
import uk.ac.man.cs.mig.util.wizard.WizardEvent;
import uk.ac.man.cs.mig.util.wizard.test.WizardPage1;

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
 */
public class WizardTest
{
	public static void main(String [] args)
	{
		WizardPage p = new WizardPage("My Page!");

		p.setLayout(new BorderLayout());

		p.add(new JTextArea(10, 30));

		Wizard w = new Wizard(null, "My Wizard!");

		WizardPage [] wpa = new WizardPage [] {new WizardPage1(w), new WizardPage("Page 1"), new WizardPage("Page 2"), p};

		w.setPages(wpa);
		//, "Back", "Forward", "Submit", "Stop");

		w.addWizardListener(new WizardEventListener()
		{
			public void cancelPressed(WizardEvent e)
			{
				System.out.println("Cancel pressed");
			}

			public void finishPressed(WizardEvent e)
			{
				System.out.println("Finish pressed");
			}

			public void nextPressed(WizardEvent e)
			{
				System.out.println("Next pressed");
			}

			public void pageChanged(WizardEvent e)
			{
				System.out.println("Page changed");
			}

			public void prevPressed(WizardEvent e)
			{
				System.out.println("Prev Pressed");

				if(e.getSource().getCurrentPage().getName().equals("My Page!"))
				{

				};
			}
		});
		try
		{
		//	UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

		//	SwingUtilities.updateComponentTreeUI(SwingUtilities.getRoot(w));

		}
		catch(Exception e)
		{
			// Very lazy!!

			e.printStackTrace();
		}

		w.showWizard();
	}
}
