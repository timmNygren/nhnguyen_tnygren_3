package pex3;

import java.util.ArrayList;

/**
 * A movie item container class to move information from the database to the
 * panel that requested the information
 */
public class MovieItem {
	private String title;
	private String length;
	private String rating;
	private String year;
	private String description;
	private String category;
	private ArrayList<String> actors;

	public MovieItem() {

	}

	/*
	 * Getters and Setters
	 */

	public void setActors(ArrayList<String> actors) {
		this.actors = actors;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return this.title;
	}

	public String getLength() {
		return this.length;
	}

	public String getRating() {
		return this.rating;
	}

	public String getYear() {
		return this.year;
	}

	public String getDescription() {
		return this.description;
	}

	public String getCategory() {
		return this.category;
	}

	public ArrayList<String> getActors() {
		return this.actors;
	}
}
