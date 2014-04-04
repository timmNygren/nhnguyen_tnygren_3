package pex3;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * This panel deals with displaying the results of the search panel Displays
 * either a list of movies by title or actors by FIRST LAST name
 * 
 */

public class SearchListPanel extends JPanel implements ListSelectionListener {

	private NetflipDatabase db;
	private DefaultListModel<String> listModel; // Used to add/remove items
	private JList<String> list; // Updates according to actions performed on
								// listModel
	private DescriptionPanel descriptionPanel;

	// Flags
	private boolean searchByActor = false;
	private boolean doingSearch = false;

	public SearchListPanel(NetflipDatabase db) {
		super(new BorderLayout());
		this.db = db;

		// Create the list model and add elements to it.
		this.listModel = new DefaultListModel<String>();

		// Populates with all movies
		addInitialList();

		setBorder(new TitledBorder(new EtchedBorder(), "Search Results"));
		this.list = new JList<String>(this.listModel);
		this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.list.addListSelectionListener(this);
		this.add(new JScrollPane(list), BorderLayout.CENTER);

	}

	/**
	 * Initializes the Search list panel with all the movies in the database
	 */
	public void addInitialList() {
		ArrayList<String> movies = this.db.getAllMovies();
		// Clear the list so there are no duplicates
		this.listModel.clear();
		for (String movie : movies) {
			this.listModel.addElement(movie);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event
	 * .ListSelectionEvent)
	 */
	@Override
	public void valueChanged(ListSelectionEvent evt) {
		// There are three selections that are applied during a value change
		// 1. Initial button press, getValueIsAdjusting = true
		// 2. Mouse dragging, getValueIsAdjusting = true for each list value the
		// mouse drags over
		// 3. When the mouse is released getValueIsAdjusting = false
		// Using the third event to determine what is selected from the search
		// list
		if (!evt.getValueIsAdjusting()) {
			this.descriptionPanel.displayInfo(list.getSelectedValue(),
					searchByActor, doingSearch);

		}
	}

	/**
	 * Receives the string to search for from the Search Panel
	 * and searches the database using a normal search
	 * 
	 * @param toSearch				String to search for
	 */
	public void search(String toSearch) {
		doingSearch = true;
		searchByActor = false;
		
		this.listModel.clear();
		ArrayList<String> results = db.normalSearch(toSearch);
		for (String result : results) {
			this.listModel.addElement(result);
		}
		doingSearch = false;
	}

	/**
	 * Performs an advanced search
	 * 
	 * @param toSearch				String to search for can be title or actor name
	 * @param titleActor			Value from drop down, Default: Title/Actor, Actor, Title
	 * @param ratings				Value from drop down of ratings
	 * @param categories			Value from drop down of categories
	 */
	public void advancedSearch(String toSearch, String titleActor,
			String ratings, String categories) {
		
		doingSearch = true;
		this.listModel.clear();
		
		if (titleActor.equals("Actor")) {
			searchByActor = true;
		} else {
			searchByActor = false;
		}
		
		ArrayList<String> results = db.advancedSearch(toSearch, titleActor,
				ratings, categories);
		
		for (String result : results) {
			this.listModel.addElement(result);
		}
		doingSearch = false;
	}

	/*
	 * Setter
	 */
	public void setDescriptionLink(DescriptionPanel descriptionPanel) {
		this.descriptionPanel = descriptionPanel;
	}

	/*
	 *
	 *
	 */
	private static final long serialVersionUID = 1L;
}
