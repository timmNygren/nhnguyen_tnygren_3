package pex3;

import java.sql.*;
import java.util.ArrayList;




public class NetflipDatabase
{
	// localhost:8889 pass "root"
	// localhost:3306 pass "
	private final String DB_CONNECTION_HOST = "jdbc:mysql://localhost/sakila";
	private final String DB_USER = "root";
	private final String DB_PASS = "";
	private Connection connection;
	private Statement commonStatement;
	private PreparedStatement statement;
	private ResultSet resultSet;

	public NetflipDatabase () {
		this.connection = null;
		this.statement = null;
		this.resultSet = null;
	}

	public MovieItem getMovieDetails(String movieTitle) {
		MovieItem movieDetails = new MovieItem();
		ArrayList<String> actors = new ArrayList<String>();

		try {
			String movieInfo = "SELECT title, rating, release_year, length, description, category.name as category " + 
					"FROM film, film_category, category " + 
					"WHERE film.film_id = film_category.film_id " + 
					"AND film_category.category_id = category.category_id " + 
					"AND title = ?";

			String actorQuery = "SELECT first_name, last_name " + 
					"FROM film, film_actor, actor " + 
					"WHERE film.film_id = film_actor.film_id " + 
					"AND film_actor.actor_id = actor.actor_id " + 
					"AND title = ?";


			connection = DriverManager.getConnection(DB_CONNECTION_HOST, DB_USER, DB_PASS);
			statement = connection.prepareStatement(movieInfo);
			statement.setString(1, movieTitle);				
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				movieDetails.setTitle(resultSet.getString("title"));
				movieDetails.setLength(resultSet.getString("length"));
				movieDetails.setRating(resultSet.getString("rating"));
				movieDetails.setYear(resultSet.getString("release_year"));
				movieDetails.setDescription(resultSet.getString("description"));
				movieDetails.setCategory(resultSet.getString("category"));
			}
			statement = connection.prepareStatement(actorQuery);
			statement.setString(1, movieTitle);
			resultSet = statement.executeQuery();

			while(resultSet.next()) {
				actors.add(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
			}

			movieDetails.setActors(actors);
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
	
	public ActorItem getActorDetails(String actor) {
		ActorItem actorDetails = new ActorItem();
		ArrayList<String> movies = new ArrayList<String>();
		String[] firstLastName = actor.split(" ");
		String firstName = firstLastName[0];
		String lastName = firstLastName[1];
		
		try {
			String movieListQuery = "SELECT title " + 
									"FROM film, film_actor, actor " + 
									"WHERE film.film_id = film_actor.film_id " + 
									"AND film_actor.actor_id = actor.actor_id " + 
									"AND actor.first_name = ? " +
									"AND actor.last_name = ?";
		
			connection = DriverManager.getConnection(DB_CONNECTION_HOST, DB_USER, DB_PASS);
			statement = connection.prepareStatement(movieListQuery);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				movies.add(resultSet.getString("title"));
			}
			
			actorDetails.setMovies(movies);
			actorDetails.setName(actor);
			
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
	
	public ArrayList<String> normalSearch( String toSearch ) {
		ArrayList<String> results = new ArrayList<String>();
		
		try {
			String movieListQuery = "SELECT title " + 
									"FROM film " + 
									"WHERE film.title LIKE ? " + 
									"OR film.description LIKE ?";
		
			connection = DriverManager.getConnection(DB_CONNECTION_HOST, DB_USER, DB_PASS);
			statement = connection.prepareStatement(movieListQuery);
			statement.setString(1, "%" + toSearch + "%");
			statement.setString(2, "%" + toSearch + "%");
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				results.add(resultSet.getString("title"));
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

	public ArrayList<String> getAllMovies() {

		ArrayList<String> movieList = new ArrayList<String>();

		try {
			connection = DriverManager.getConnection(DB_CONNECTION_HOST, DB_USER, DB_PASS);
			commonStatement = connection.createStatement();
			resultSet = commonStatement.executeQuery("SELECT title FROM film ORDER BY title");

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

	public ArrayList<String> getAllCategories() {
		ArrayList<String> categories = new ArrayList<String>();
		try {
			connection = DriverManager.getConnection(DB_CONNECTION_HOST, DB_USER, DB_PASS);
			commonStatement = connection.createStatement();
			resultSet = commonStatement.executeQuery("SELECT name FROM category ORDER BY name");

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

	public ArrayList<String> getAllRatings() {
		ArrayList<String> ratings = new ArrayList<String>();
		try {
			connection = DriverManager.getConnection(DB_CONNECTION_HOST, DB_USER, DB_PASS);
			commonStatement = connection.createStatement();
			resultSet = commonStatement.executeQuery("SELECT rating FROM film GROUP BY rating ORDER BY rating");

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