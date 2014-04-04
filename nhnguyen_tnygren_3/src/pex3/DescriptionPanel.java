package pex3;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * A panel that deals with displaying the information about an actor or movie
 * when selected from either the list in the Search list panel or the list
 * embedded in this description panel for movies or actors
 * 
 */
public class DescriptionPanel extends JPanel implements ListSelectionListener,
		ActionListener {

	private final int J_TEXT_FIELD_SIZE = 20;

	// The db to request information
	private NetflipDatabase db;

	// Check for event Triggers, solved a multiple trigger
	// problem when switching panels
	private boolean eventTrigger = false;

	// Movie panel items
	private JPanel movieInfo;
	private JTextField movieTitle;
	private JTextField movieRating;
	private JTextField movieReleaseYear;
	private JTextField movieCategory;
	private JTextField movieLength;
	private JTextArea movieDescription;
	private DefaultListModel<String> actorListModel;
	private JList<String> actorList;
	private JButton addtoQueue;
	private JButton moviesinQueue;

	// Actor panel items
	private JPanel actorInfo;
	private JTextField actorName;
	private DefaultListModel<String> movieListModel;
	private JList<String> movieList;

	public DescriptionPanel(NetflipDatabase db) {
		super(new BorderLayout());
		this.db = db;
		setBorder(new TitledBorder(new EtchedBorder(), "Description"));

		createMovieDescriptionPanel();
		createActorDescriptionPanel();

	}

	/**
	 * Creates the movie information panel
	 */
	public void createMovieDescriptionPanel() {
		// Make the panel
		movieInfo = new JPanel(new BorderLayout());

		// Make the info Area consisting of the movie specs and list of actors
		JPanel infoArea = new JPanel(new BorderLayout());

		// Main movie specs
		JPanel movieSpecs = new JPanel(new GridLayout(5, 1));

		// Create text fields and set editable to false
		movieTitle = new JTextField(J_TEXT_FIELD_SIZE);
		movieTitle.setEditable(false);
		movieRating = new JTextField(J_TEXT_FIELD_SIZE);
		movieRating.setEditable(false);
		movieReleaseYear = new JTextField(J_TEXT_FIELD_SIZE);
		movieReleaseYear.setEditable(false);
		movieCategory = new JTextField(J_TEXT_FIELD_SIZE);
		movieCategory.setEditable(false);
		movieLength = new JTextField(J_TEXT_FIELD_SIZE);
		movieLength.setEditable(false);

		// Add the text fields
		movieSpecs.add(movieTitle);
		movieSpecs.add(movieRating);
		movieSpecs.add(movieReleaseYear);
		movieSpecs.add(movieCategory);
		movieSpecs.add(movieLength);

		// Create the description area
		movieDescription = new JTextArea(30, 10);
		movieDescription.setEditable(false);
		movieDescription.setLineWrap(true);

		// Create the list model
		this.actorListModel = new DefaultListModel<String>();

		this.actorList = new JList<String>(this.actorListModel);
		this.actorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.actorList.addListSelectionListener(this);

		// Add the three components to the infoArea

		infoArea.add(movieDescription, BorderLayout.CENTER);
		infoArea.add(movieSpecs, BorderLayout.WEST);
		infoArea.add(new JScrollPane(actorList), BorderLayout.EAST);

		// Create buttons to view or add movies to the queue
		this.addtoQueue = new JButton("Add to Queue");
		this.moviesinQueue = new JButton("Movies in Queue");

		// Create a grid layout for our buttons
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		buttonPanel.add(moviesinQueue);
		buttonPanel.add(addtoQueue);

		// Center the main information box
		// Add the buttons south of the info area
		movieInfo.add(infoArea, BorderLayout.CENTER);
		movieInfo.add(buttonPanel, BorderLayout.SOUTH);

		// Add action listeners to the buttons
		moviesinQueue.addActionListener(new QueueButtonListener());
		addtoQueue.addActionListener(this);
	}

	/**
	 * Creates the actor information panel
	 */
	public void createActorDescriptionPanel() {
		actorInfo = new JPanel(new BorderLayout());

		// Create name text field
		actorName = new JTextField(J_TEXT_FIELD_SIZE);
		actorName.setEditable(false);

		// Create the list model
		this.movieListModel = new DefaultListModel<String>();

		this.movieList = new JList<String>(this.movieListModel);
		this.movieList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.movieList.addListSelectionListener(this);

		// Add components to actorInfo
		actorInfo.add(actorName, BorderLayout.WEST);
		actorInfo.add(new JScrollPane(movieList), BorderLayout.EAST);

	}

	/**
	 * Displays info from a name and a searchByActor check
	 * 
	 * @param name
	 *            Name of the actor or movie
	 * @param searchByActor
	 *            Check if we search for actors or movie info
	 * @param doingSearch
	 *            If we are doing a search, do not display any info
	 */
	public void displayInfo(String name, boolean searchByActor,
			boolean doingSearch) {

		if (doingSearch) {
			// Do nothing, messes up panels
		} else if (searchByActor) {
			this.add(actorInfo);
			this.revalidate();
			this.repaint();
			displayActorInfo(db.getActorDetails(name));
		} else {
			// A check if user has clicked on a movie in the actor list
			if (actorInfo.isShowing()) {
				this.remove(actorInfo);
			}
			this.add(movieInfo);
			this.revalidate();
			this.repaint();
			displayMovieInfo(db.getMovieDetails(name));
		}
	}

	/**
	 * Displays the movie information
	 * 
	 * @param movie
	 *            The movie container
	 */
	public void displayMovieInfo(MovieItem movie) {

		movieTitle.setText("Title: " + movie.getTitle());
		movieCategory.setText("Category: " + movie.getCategory());
		movieRating.setText("Rating: " + movie.getRating());
		movieReleaseYear.setText("Year Released: " + movie.getYear());
		movieLength.setText("Running time: " + movie.getLength() + " mins");
		movieDescription.setText(movie.getDescription());

		actorListModel.clear();
		for (String actor : movie.getActors()) {
			actorListModel.addElement(actor);
		}
		eventTrigger = false;
	}

	/**
	 * Displays the information of the selected actor
	 * 
	 * @param actor
	 *            The Actor container of name and movie list
	 */
	public void displayActorInfo(ActorItem actor) {
		actorName.setText("Name: " + actor.getName());
		// Make sure to clear the list before adding more movies
		actorListModel.clear();
		for (String movie : actor.getMovies()) {
			movieListModel.addElement(movie);
		}
		// Extra events were triggering from same mouse click
		// as panels switched, this seems to have solved the
		// problem
		eventTrigger = false;
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
		if (!evt.getValueIsAdjusting() && !eventTrigger) {
			// We are displaying an Actor's info with a movie list
			if (evt.getSource() == movieList) {
				eventTrigger = true;
				// Get the name of the movie selected
				String name = movieList.getSelectedValue();
				// Remove the actor info panel to replace with the movie info
				// panel
				this.remove(actorInfo);
				// Pass the name of the movie and false for searching by movie
				displayInfo(name, false, false);

			}
			// We are displaying a Movie's info with an actor list
			else if (evt.getSource() == actorList) {
				eventTrigger = true;
				// Get the name of the actor selected from the current movie's
				// actor list
				String name = actorList.getSelectedValue();
				// Remove the movie info panel to replace with the actor info
				// panel
				this.remove(movieInfo);
				// Pass the name of the actor and true for displaying actor
				// information
				displayInfo(name, true, false);
			}
		}
	}

	/**
	 * Used to bring up a separate window to view the queue
	 */
	public class QueueButtonListener implements ActionListener {
		QueueLog qLog = new QueueLog();

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == moviesinQueue) {
				qLog.setVisible(true);

			}
		}
	}

	/**
	 * Was going to be used to add movies to the queue
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
