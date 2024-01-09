package it.unipv.ingsfw.JavaBeats.model.profile;

import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;

import java.sql.Blob;
import java.util.ArrayList;

public class Artist extends JBProfile {

	//ATTRIBUTES:
	private int totalListeners = 0;


	//CONSTRUCTORS:
	public Artist(String username, String mail, String password, String name, String surname, String biography, Blob profilePicture, int totalListeners, ArrayList<JBAudio> listeningHistory) {
		super(username, mail, password, name, surname, biography, profilePicture, listeningHistory);
		this.totalListeners=totalListeners;
	}
	public Artist(String username, String mail, String password) {
		this(username, mail, password, null, null, null, null, 0, null);
	}


	//GETTER:
	public int getTotalListeners() {
		return totalListeners;
	}


	//SETTER:
	public void setTotalListeners(int totalListeners) {
		this.totalListeners = totalListeners;
	}


	//METHODS:
	@Override
	public String toString() {
		return "ARTIST    -  Username: " + this.getUsername() + ";  Mail: " + this.getMail() + ".";
	}

}
