package pex3;

import java.util.ArrayList;

public class ActorItem {
	
	private String name;
	private ArrayList<String> movies;
	
	public ActorItem() {
		
	}
	
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
