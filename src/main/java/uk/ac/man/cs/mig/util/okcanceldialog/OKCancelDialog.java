package uk.ac.man.cs.mig.util.okcanceldialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: Jan 7, 2004<br><br>
 * 
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 */
public class OKCancelDialog extends JDialog
{
	/**
     * 
     */
    private static final long serialVersionUID = -3155387891462538221L;
    JPanel holderPanel;
	JButton approveButton;
	JButton cancelButton;

	public final static int OPTION_APPROVE = 1;
	public final static int OPTION_CANCEL = 2;

	private int retVal = OPTION_CANCEL;

    private Frame dialogOwner;

	public OKCancelDialog(Frame owner, String title, String approveButtonText, String cancelButtonText)
	{
		super(owner, title, true);

        dialogOwner = owner;

		holderPanel = new JPanel(new BorderLayout(7, 7));

		holderPanel.setBorder(new EmptyBorder(7, 7, 7, 7));

		approveButton = new JButton(approveButtonText);

		approveButton.addActionListener(new ActionListener()
		{
			/**
			 * Invoked when an action occurs.
			 */
			public void actionPerformed(ActionEvent e)
			{
				handleOptionApprove();
			}
		});

		cancelButton = new JButton(cancelButtonText);

		cancelButton.addActionListener(new ActionListener()
		{
			/**
			 * Invoked when an action occurs.
			 */
			public void actionPerformed(ActionEvent e)
			{
				handleOptionCancel();
			}
		});

        setFocusable(true);
        
        addKeyListener(new KeyAdapter()
        {
            /**
             * Invoked when a key has been released.
             */
            public void keyReleased(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    handleOptionApprove();
                }
                else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
                {
                    handleOptionCancel();
                }
            }
        });


		JPanel buttonPanel = new JPanel();

		buttonPanel.setLayout(new GridLayout(1, 0, 7, 7));

		buttonPanel.add(approveButton);

		buttonPanel.add(cancelButton);

		JPanel buttonPanelHolder = new JPanel(new BorderLayout());

		buttonPanelHolder.add(buttonPanel, BorderLayout.EAST);

		holderPanel.add(buttonPanelHolder, BorderLayout.SOUTH);

		getContentPane().add(holderPanel);

		pack();

        setDialogLocation();
	}

    protected void setDialogLocation()
    {
        int ownerCentreX;

        int ownerCentreY;

        if(dialogOwner != null)
        {
            ownerCentreX = (int)dialogOwner.getBounds().getCenterX();

            ownerCentreY = (int)dialogOwner.getBounds().getCenterY();
        }
        else
        {
            ownerCentreX = Toolkit.getDefaultToolkit().getScreenSize().width / 2;

            ownerCentreY = Toolkit.getDefaultToolkit().getScreenSize().height / 2;
        }

        setLocation(ownerCentreX - getSize().width / 2, ownerCentreY - getSize().height / 2);
    }

    protected void handleOptionApprove()
    {
        retVal = OPTION_APPROVE;

		if(validateData() == true)
		{
			dispose();
		}
    }

    protected void handleOptionCancel()
    {
        retVal = OPTION_CANCEL;

		dispose();
    }

	/**
	 * Sets the content that the dialog displays.
	 * @param content A component that represents the content
	 * to be displayed by the dialog.
	 */
	public void setContent(JComponent content)
	{
		holderPanel.add(content);

		pack();
	}

	public int showDialog()
	{
		setVisible(true);

		return retVal;
	}

	/**
	 * This method is called when the OK (or approve button) is
	 * pressed, but before the dialog is closed. Override this
	 * ,method if the dialog data needs to be validated.
	 * @return Returns <code>true</code> if the dialog data is valid
	 * and the dialog can close, or <code>false</code> is the data is
	 * invalid and the dialog should not close.
	 */
	public boolean validateData()
	{
		return true;
	}
}
