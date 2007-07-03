package uk.ac.man.cs.mig.coode.owlviz.ui;

import uk.ac.man.cs.mig.util.okcanceldialog.OKCancelDialog;

import java.awt.*;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 24, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class ClassRadiusDialog extends OKCancelDialog {

	ClassRadiusDialogPage radiusPage;

	public static final int SUPERS_AND_SUBS = 1;
	public static final int SUPERS_ONLY = 2;
	public static final int SUBS_ONLY = 3;


	public ClassRadiusDialog(Frame owner,
	                         boolean showSuperSubOptions) {
		super(owner, "Radius", "OK", "Cancel");
		setContent(radiusPage = new ClassRadiusDialogPage(showSuperSubOptions));
	}


	/**
	 * This method is called when the OK (or approve button) is
	 * pressed, but before the dialog is closed. Override this
	 * ,method if the dialog data needs to be validated.
	 *
	 * @return Returns <code>true</code> if the dialog data is valid
	 *         and the dialog can close, or <code>false</code> is the data is
	 *         invalid and the dialog should not close.
	 */
	public boolean validateData() {
		return true;
	}


	/**
	 * Retrives the class radius entered by the user.
	 *
	 * @return The class radius.
	 */
	public int getClassRadius() {
		return radiusPage.getClassRadius();
	}


	public int getSupersSubsOption() {
		return radiusPage.getSupersSubsOption();
	}
}

