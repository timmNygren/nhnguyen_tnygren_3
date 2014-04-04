package pex3;

import java.sql.*;
import java.util.ArrayList;

/**
 * This class handles all database queries and returns results in the form of
 * either strings or an array of strings.
 * 
 * Because leaving Connections, Statements, PreparedStatements, and ResultSets
 * open this leads to resource leaks and thus each method requests a connection,
 * does some business, then closes the connection.
 */
public class NetflipDatabase {
	private final String DB_CONNECTION_HOST = "jdbc:mysql://localhost:3306/sakila";
	private final String DB_USER = "root";
	private final String DB_PASS = "";
	private Connection connection;
	private Statement commonStatement; // Used for the getAll* methods
	private PreparedStatement statement; // Used for dealing with user input,
											// and parameters through prepared
											// statements
	private ResultSet resultSet;

	public NetflipDatabase() {
		this.connection = null;
		this.statement = null;
		this.resultSet = null;
	}

	/**
	 * Retrieves a movie's details from the database
	 * 
	 * @param movieTitle
	 *            Movie to get information on
	 * @return A MovieItem that is a container of the information
	 */
	public MovieItem getMovieDetails(String movieTitle) {
		MovieItem movieDetails = new MovieItem();
		ArrayList<String> actors = new ArrayList<String>();

		try {
			String movieInfo = "SELECT title, rating, release_year, length, description, category.name as category "
					+ "FROM film, film_category, category "
					+ "WHERE film.film_id = film_category.film_id "
					+ "AND film_category.category_id = category.category_id "
					+ "AND title = ?";

			String actorQuery = "SELECT first_name, last_name "
					+ "FROM film, film_actor, actor "
					+ "WHERE film.film_id = film_actor.film_id "
					+ "AND film_actor.actor_id = actor.actor_id "
					+ "AND title = ?";

			connection = DriverManager.getConnection(DB_CONNECTION_HOST,
					DB_USER, DB_PASS);
			statement = connection.prepareStatement(movieInfo);
			statement.setString(1, movieTitle);
			// Get basic movie info from film table
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				movieDetails.setTitle(resultSet.getString("title"));
				movieDetails.setLength(resultSet.getString("length"));
				movieDetails.setRating(resultSet.getString("rating"));
				movieDetails.setYear(resultSet.getString("release_year"));
				movieDetails.setDescription(resultSet.getString("description"));
				movieDetails.setCategory(resultSet.getString("category"));
			}

			statement = connection.prepareStatement(actorQuery);
			statement.setString(1, movieTitle);
			// Get the list of actors for the given movie
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				actors.add(resultSet.getString("first_name") + " "
						+ resultSet.getString("last_name"));
			}

			movieDetails.setActors(actors);
			// Close the connections
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.print("Error has occurred with the Database");
		}
		return movieDetails;
	}

	/**
	 * Retrieves the given actor's details which is just the actor's first and
	 * last name and the list of movies they acted in
	 * 
	 * @param actor
	 *            Actor to get the list of movies
	 * @return An actor container that has the first and last name as one string
	 *         and a list of movies
	 */
	public ActorItem getActorDetails(String actor) {
		ActorItem actorDetails = new ActorItem();
		ArrayList<String> movies = new ArrayList<String>();
		// The actor has been selected from a list
		// Split the string up into first and last
		// name to query the database
		String[] firstLastName = actor.split(" ");
		String firstName = firstLastName[0];
		String lastName = firstLastName[1];

		try {
			String movieListQuery = "SELECT title "
					+ "FROM film, film_actor, actor "
					+ "WHERE film.film_id = film_actor.film_id "
					+ "AND film_actor.actor_id = actor.actor_id "
					+ "AND actor.first_name = ? " + "AND actor.last_name = ?";

			connection = DriverManager.getConnection(DB_CONNECTION_HOST,
					DB_USER, DB_PASS);
			statement = connection.prepareStatement(movieListQuery);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				movies.add(resultSet.getString("title"));
			}

			actorDetails.setMovies(movies);
			actorDetails.setName(actor);

			// Close connections
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.print("Error with the database");
		}
		return actorDetails;
	}

	/**
	 * Performs a normal search, using the given string to search the movie
	 * titles or the description for any matches.
	 * 
	 * @param toSearch
	 *            The string to search with
	 * @return An arraylist of strings with all results found (if any)
	 */
	public ArrayList<String> normalSearch(String toSearch) {
		ArrayList<String> results = new ArrayList<String>();

		try {
			String movieListQuery = "SELECT title " + "FROM film "
					+ "WHERE film.title LIKE ? " + "OR film.description LIKE ?";

			connection = DriverManager.getConnection(DB_CONNECTION_HOST,
					DB_USER, DB_PASS);
			statement = connection.prepareStatement(movieListQuery);
			statement.setString(1, "%" + toSearch + "%"); // Percents(%) are
															// wild cards for
															// the LIKE keyword
			statement.setString(2, "%" + toSearch + "%");
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				results.add(resultSet.getString("title"));
			}

			// Close connections
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.print("Error with the database");
		}
		return results;
	}

	/**
	 * Performs an advanced search with parameters given
	 * 
	 * @param toSearch
	 *            A string to search title or actor, if given
	 * @param titleActor
	 *            Contains which option was chosen from the drop down menu
	 *            Title/Actor
	 * @param ratings
	 *            Contains which option was chosen from the drop down menu
	 *            Ratings
	 * @param categories
	 *            Contains which option was chosen from the drop down menu
	 *            Categories
	 * @return A list of results from the query
	 */
	public ArrayList<String> advancedSearch(String toSearch, String titleActor,
			String ratings, String categories) {
		ArrayList<String> results = new ArrayList<String>();

		String resultQuery = "";
		String whereQuery = "";
		String ratingsQuery = null;
		String categorieQuery = null;
		String first = null;
		String last = null;

		// Check if actor was selected
		if (titleActor.equals("Actor")) {
			resultQuery += "SELECT first_name, last_name FROM actor";

			// See if there is two words to represent a first and last name
			if (toSearch.indexOf(" ") != -1 && toSearch.split(" ").length == 2) {
				first = toSearch.split(" ")[0];
				last = toSearch.split(" ")[1];

				whereQuery += " WHERE actor.first_name LIKE ? AND actor.last_name LIKE ?";

				// Check if there is only one word, search for first name or
				// last name
			} else if (toSearch.indexOf(" ") == -1) {
				whereQuery += " WHERE actor.first_name LIKE ?";
			} else {
				// Either empty or contains more than two words
				results.add("No Results were found");
				return results;
			}
		} else if (titleActor.equals("Title")) {
			// Search by film title
			resultQuery += "SELECT title FROM film";
			whereQuery += " WHERE film.title LIKE ?";
		}

		// Check if a rating is selected
		if (!ratings.equals("Ratings") && !titleActor.equals("Actor")) {
			ratingsQuery = " AND film.rating = '" + ratings + "'";
		}
		// Check if a category is selected
		if (!categories.equals("Categories") && !titleActor.equals("Actor")) {
			resultQuery += ", category, film_category";
			categorieQuery = " AND category.name = '" + categories
					+ "' AND category.category_id = film_category.category_id"
					+ " AND film.film_id = film_category.film_id";
		}
		// Add the rest of the query
		resultQuery += whereQuery;

		if (ratingsQuery != null && !titleActor.equals("Actor")) {
			resultQuery += ratingsQuery;
		}

		if (categorieQuery != null && !titleActor.equals("Actor")) {
			resultQuery += categorieQuery;
		}

		// If we have only one word to search for actors
		if (toSearch.indexOf(" ") == -1 && titleActor.equals("Actor")) {
			resultQuery += " GROUP BY actor.first_name, actor.last_name";
		}

		// If we encounter an empty string, just get all actors
		if (titleActor.equals("Actor") && toSearch.length() == 0) {
			resultQuery = "SELECT first_name, last_name from actor";
			// Likewise for an empty string and not searching for actors
		} else if (toSearch.length() == 0) {
			resultQuery = "SELECT title FROM film";
		}

		try {

			connection = DriverManager.getConnection(DB_CONNECTION_HOST,
					DB_USER, DB_PASS);
			statement = connection.prepareStatement(resultQuery);

			if (first != null && last != null) { 
				// Two word search for actors,
				// assuming
			    // "firstName lastName"
				statement.setString(1, "%" + first + "%");
				statement.setString(2, "%" + last + "%");
				
				// One word for actors
			} else if (titleActor.equals("Actor") && toSearch.length() != 0) {
				statement.setString(1, "%" + toSearch + "%");
				
				// Searching for a title / default action for no selected
				// title/actor
			} else if ((titleActor.equals("Title") || titleActor
					.equals("Title/Actor")) && toSearch.length() != 0) {
				statement.setString(1, "%" + toSearch + "%");
			}
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				if (titleActor.equals("Actor")) {
					String firstName = resultSet.getString("first_name");
					String lastName = resultSet.getString("last_name");
					results.add(firstName + " " + lastName);
				} else {
					results.add(resultSet.getString("title"));
				}
			}

			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.print("Error with the database");
		}
		return results;
	}

	/**
	 * Retrieves a list of all movies in the database
	 * 
	 * @return				List of all movies
	 */
	public ArrayList<String> getAllMovies() {

		ArrayList<String> movieList = new ArrayList<String>();

		try {
			connection = DriverManager.getConnection(DB_CONNECTION_HOST,
					DB_USER, DB_PASS);
			commonStatement = connection.createStatement();
			resultSet = commonStatement
					.executeQuery("SELECT title FROM film ORDER BY title");

			while (resultSet.next()) {
				movieList.add(resultSet.getString("title"));
			}

			if (resultSet != null) {
				resultSet.close();
			}
			if (commonStatement != null) {
				commonStatement.close();
			}
			if (connection != null) {
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.print("Error has occurred with the Database");
		}
		return movieList;
	}

	/**
	 * Retrieves all the categories of movies
	 * 
	 * @return				List of all categories
	 */
	public ArrayList<String> getAllCategories() {
		ArrayList<String> categories = new ArrayList<String>();
		try {
			connection = DriverManager.getConnection(DB_CONNECTION_HOST,
					DB_USER, DB_PASS);
			commonStatement = connection.createStatement();
			resultSet = commonStatement
					.executeQuery("SELECT name FROM category ORDER BY name");

			while (resultSet.next()) {
				categories.add(resultSet.getString("name"));
			}

			if (resultSet != null) {
				resultSet.close();
			}
			if (commonStatement != null) {
				commonStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.print("Error has occurred with the Database");
		}
		return categories;
	}

	/**
	 * Retrieves a list of all the ratings
	 * 
	 * @return				List of all ratings
	 */
	public ArrayList<String> getAllRatings() {
		ArrayList<String> ratings = new ArrayList<String>();
		
		try {
			connection = DriverManager.getConnection(DB_CONNECTION_HOST,
					DB_USER, DB_PASS);
			commonStatement = connection.createStatement();
			resultSet = commonStatement
					.executeQuery("SELECT rating FROM film GROUP BY rating ORDER BY rating");

			while (resultSet.next()) {
				ratings.add(resultSet.getString("rating"));
			}
			
			if (resultSet != null) {
				resultSet.close();
			}
			if (commonStatement != null) {
				commonStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.print("Error has occurred with the Database");
		}
		return ratings;
	}
}