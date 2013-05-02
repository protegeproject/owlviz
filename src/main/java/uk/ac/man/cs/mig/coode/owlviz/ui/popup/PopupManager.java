package uk.ac.man.cs.mig.coode.owlviz.ui.popup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Sep 23, 2004<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 */
public class PopupManager {

	private HashSet popupPages;

	private OWLObjectPopupPage currentPopupPage;

	private static HashMap managerMap = new HashMap();


	protected PopupManager() {
		popupPages = new HashSet();
	}


	public static synchronized PopupManager getInstance(String name) {
		PopupManager popupManager = (PopupManager) managerMap.get(name);
		if(popupManager == null) {
			popupManager = new PopupManager();
			managerMap.put(name, popupManager);
		}
		return popupManager;
	}


	public void addPopupPage(OWLObjectPopupPage popupPage) {
		popupPages.add(popupPage);
	}


	public OWLObjectPopupPage[] getPopupPages() {
		Iterator it = popupPages.iterator();
		OWLObjectPopupPage[] array = new OWLObjectPopupPage[popupPages.size()];
		int counter = 0;
		while(it.hasNext()) {
			OWLObjectPopupPage curPage = (OWLObjectPopupPage) it.next();
			array[counter] = curPage;
			counter++;
		}
		return array;
	}


	public void setCurrentPopupPage(OWLObjectPopupPage popupPage) {
		currentPopupPage = popupPage;
	}


	public OWLObjectPopupPage getCurrentPopupPage() {
		return currentPopupPage;
	}
}

