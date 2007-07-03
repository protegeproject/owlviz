package uk.ac.man.cs.mig.coode.owlviz.ui.options;

import uk.ac.man.cs.mig.util.okcanceldialog.OKCancelDialog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Mar 1, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class OptionsDialog extends OKCancelDialog {

	private ArrayList optionPages;
	private JTabbedPane tabPane;

	public static final String DEFAULT_PAGE = "General Options";


	public OptionsDialog(Frame owner) {
		super(owner, "Options", "OK", "Cancel");
		optionPages = new ArrayList();
		tabPane = new JTabbedPane();
		setContent(tabPane);
	}


	public void addOptionsPage(OptionsPage page,
	                           String tabName) {
		// If the page does not exist, add it, and add the component
		// to the page.

		Component c = getTab(tabName);
		if(c == null) {
			// Create a new Page
			Box box = new Box(BoxLayout.Y_AXIS);
			box.add(page);
			box.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
			tabPane.add(tabName, box);
			optionPages.add(page);
		}
		else {
			Box box = (Box) c;
			box.add(Box.createVerticalStrut(7));
			box.add(page);
			optionPages.add(page);
		}
		pack();
	}


	protected Component getTab(String name) {
		for(int i = 0; i < tabPane.getTabCount(); i++) {
			if(tabPane.getTitleAt(i).equals(name)) {
				return tabPane.getComponentAt(i);
			}
		}
		return null;
	}


	public void applyOptions() {
		for(int i = 0; i < optionPages.size(); i++) {
			((OptionsPage) optionPages.get(i)).applyOptions();
		}
	}


	public void updateInterface() {
		for(int i = 0; i < optionPages.size(); i++) {
			((OptionsPage) optionPages.get(i)).updateInterface();
		}
	}
}

