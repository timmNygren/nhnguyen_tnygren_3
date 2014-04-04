package pex3;

import java.util.ArrayList;

/**
 * Actor container for information such as the actor's name and list of movies
 * they were part of
 */
public class ActorItem {

	private String name; // Name of the actor
	private ArrayList<String> movies; // List of movies this actor was part of

	public ActorItem() {

	}

	/*
	 * Getters and Setters below
	 */

	public void setName(String name) {
		this.name = name;
	}

	public void setMovies(ArrayList<String> movies) {
		this.movies = movies;
	}

	public String getName() {
		return this.name;
	}

	public ArrayList<String> getMovies() {
		return this.movies;
	}

}
