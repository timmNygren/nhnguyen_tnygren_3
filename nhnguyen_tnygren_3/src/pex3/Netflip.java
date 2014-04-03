package pex3;



/**
 * A simple applet to demonstrate the JList.
 * 
 * Various other components are available here:
 *   http://docs.oracle.com/javase/tutorial/uiswing/components/componentlist.html
 * 
 * @author Randy Bower
 */
import java.awt.BorderLayout;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


public class Netflip extends JApplet
{
	// Called when this applet is loaded into the browser.
	public void init()
	{
		// Execute a job on the event-dispatching thread; creating this applet's GUI.
		try
		{
			SwingUtilities.invokeAndWait( new Runnable()
			{
				public void run()
				{
					createGUI();
				}
			} );
		}
		catch( Exception e )
		{
			e.printStackTrace();
			System.err.println( "ERROR: createGUI() did not complete successfully." );
		}
	}

	/**
	 * Create the GUI. For thread safety, this method should be invoked from the event-dispatching thread.
	 */
	private void createGUI()
	{
		// Create and set up the content pane.
		SearchPanel spanel = new SearchPanel();
		NetflipGUI newContentPane = new NetflipGUI();
		DescriptionPanel dpanel = new DescriptionPanel();
		
		setSize(900, 500);
//		newContentPane.setOpaque( true );
//		setContentPane( newContentPane );
		add(newContentPane, BorderLayout.LINE_START);
		add(spanel, BorderLayout.PAGE_START);
		add(dpanel, BorderLayout.CENTER);

		
		//add(spanel);
		
	}

	// Some arbitrary data to put into the JList.

}