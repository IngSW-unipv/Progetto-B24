package it.unipv.ingsfw.JavaBeats.model.user;

import java.sql.Blob;

public class Artist extends JBProfile {

	//ATTRIBUTES:
	private int totalListeners = 0;


	//CONSTRUCTORS:
	public Artist(String username, String mail, String password, String name, String surname, String biography, Blob profilePicture, int totalListeners) {
		super(username, mail, password, name, surname, biography, profilePicture);
		this.totalListeners=totalListeners;
	}
	public Artist(String username, String mail, String password) {
		this(username, mail, password, null, null, null, null, 0);
	}


	//GETTER:
	public int getTotalListeners() {
		return totalListeners;
	}


	//SETTER:
	public void setTotalListeners(int totalListeners) {
		this.totalListeners = totalListeners;
	}
}
