package pex3;

import java.awt.BorderLayout;
import javax.swing.*;

/**
 * Main class that initializes the applet. This class initializes the database
 * and creates the GUI.
 * 
 */
public class Netflip extends JApplet {

	private NetflipDatabase db;

	// Called when this applet is loaded into the browser.
	public void init() {
		// Execute a job on the event-dispatching thread; creating this applet's
		// GUI.
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					initDB();
					createGUI();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			System.err
					.println("ERROR: createGUI() did not complete successfully.");
		}
	}

	private void initDB() {
		this.db = new NetflipDatabase();
	}

	/**
	 * Create the GUI. For thread safety, this method should be invoked from the
	 * event-dispatching thread.
	 */
	private void createGUI() {

		// Create and set up the panels.
		SearchPanel searchPanel = new SearchPanel(db.getAllRatings(),
				db.getAllCategories());
		SearchListPanel searchListPanel = new SearchListPanel(db);
		DescriptionPanel descriptionPanel = new DescriptionPanel(db);
		setSize(1000, 500);

		// Create necessary links between panels to communicate
		searchListPanel.setDescriptionLink(descriptionPanel);
		searchPanel.setSearchListLink(searchListPanel);

		// Add panels to the GUI
		add(searchListPanel, BorderLayout.LINE_START);
		add(searchPanel, BorderLayout.PAGE_START);
		add(descriptionPanel, BorderLayout.CENTER);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}