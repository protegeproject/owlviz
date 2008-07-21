package uk.ac.man.cs.mig.util.popup.test;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import uk.ac.man.cs.mig.util.popup.PopupComponent;
import uk.ac.man.cs.mig.util.popup.PopupComponentEvent;
import uk.ac.man.cs.mig.util.popup.PopupComponentListener;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Feb 16, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public class PopupComponentTest extends JFrame
{
    private static Logger log = Logger.getLogger(PopupComponentTest.class);

	private PopupComponent popup;

	private JComponent comp;
	private JList list;
    private JPanel panl;
	private JButton button;

	public PopupComponentTest()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setTitle("Popup Test");

		setSize(800, 600);

		getContentPane().setLayout(new BorderLayout());

		final JPanel pan = new JPanel();

		getContentPane().add(pan);

		list = new JList(new Object [] {"One", "Two", "Three", "Four"});

        panl = new JPanel(new BorderLayout());

        panl.add(list);

		list.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				if(e.getClickCount() == 2)
				{
					log.info("TRACE(PopupComonentTest): Setting list ");

					list.setListData(new Object [] {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight"});
				}
			}
		});


		list.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

		button = new JButton();

		popup = new PopupComponent(panl, false, 5000, false);

		popup.addPopupComponentListener(new PopupComponentListener()
		{
			public void popupClosed(PopupComponentEvent e)
			{
				log.info("Popup Closed");
			}
		});

		pan.setLayout(new BorderLayout());


		pan.add(comp = new JButton("Test button 1......."), BorderLayout.WEST);

		pan.add(new JButton("Test button 2......."), BorderLayout.EAST);

		pan.add(new JButton("Test button 3"), BorderLayout.NORTH);

		pan.add(new JButton("Test button 4"), BorderLayout.SOUTH);

		pan.add(new JPanel());


		comp.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				if(e.isPopupTrigger())
				{
					log.info("TRACE(PopupComponentTest): Show popup at " + e.getPoint());

					popup.showPopup(comp, e.getPoint().x, e.getPoint().y);
				}
			}
		});

		JMenuBar menuBar = new JMenuBar();

		setJMenuBar(menuBar);

		JMenu menu = new JMenu("File");

		menuBar.add(menu);

		menu.add(new AbstractAction("Set button component")
		{
			public void actionPerformed(ActionEvent e)
			{
				popup.setContent(button);
			}
		});

		menu.add(new AbstractAction("Set list component")
		{
			public void actionPerformed(ActionEvent e)
			{
				popup.setContent(panl);
			}
		});



		setVisible(true);
	}

	public static void main(String [] args)
	{
		PopupComponentTest t = new PopupComponentTest();
	}
}
