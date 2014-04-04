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
import java.util.ArrayList;

import javax.swing.*;

public class Netflip extends JApplet
{
	
	private NetflipDatabase db;
	
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
					initDB();
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
	
	private void initDB() {
		this.db = new NetflipDatabase();
	}
	
	/**
	 * Create the GUI. For thread safety, this method should be invoked from the event-dispatching thread.
	 */
	private void createGUI() 
	{
		
		// Create and set up the panels.
		SearchPanel searchPanel = new SearchPanel(db.getAllRatings(), db.getAllCategories());
		SearchListPanel searchListPanel = new SearchListPanel(db);
		DescriptionPanel descriptionPanel = new DescriptionPanel(db);
		setSize(1000, 500);
		
		// Create necessary links between panels to communicate
		searchListPanel.setDescriptionLink(descriptionPanel);
		
		add(searchListPanel, BorderLayout.LINE_START);
		add(searchPanel, BorderLayout.PAGE_START);
		add(descriptionPanel, BorderLayout.CENTER);
	}

	// Some arbitrary data to put into the JList.

}