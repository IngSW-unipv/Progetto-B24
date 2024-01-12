package it.unipv.ingsfw.JavaBeats.model.profile;


import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;

import java.sql.Blob;
import java.sql.Time;
import java.util.ArrayList;

public class User extends JBProfile {

	//ATTRIBUTES:
	private boolean isVisible = true;
	private Time totalListeningTime;


	//CONSTRUCTOR:
	public User(String username, String mail, String password, String name, String surname, String biography, Blob profilePicture, boolean isVisible, Time totalListeningTime, ArrayList<JBAudio> listeningHistory) {
		super(username, mail, password, name, surname, biography, profilePicture, listeningHistory);
		this.isVisible = isVisible;
		this.totalListeningTime = totalListeningTime;
	}
	public User(String username, String mail, String password) {
		this(username, mail, password, null, null, null, null, true, null, null);
	}

	public User(String username, String mail, String password, String name, String surname) {
		this(username, mail, password, name, surname, null, null, true, null, null);
	}

	public User(String username, String password) {
		this(username, null, password, null, null, null, null, true, null, null);
	}


	//GETTERS:
	public boolean isVisible() {
		return isVisible;
	}
	public Time getMinuteListened() {
		return totalListeningTime;
	}


	//SETTERS:
	public void setVisible(boolean visible) {
		isVisible = visible;
	}
	public void setMinuteListened(Time minuteListened) {
		this.totalListeningTime = minuteListened;
	}


	//METHODS:
	@Override
	public String toString() {
		return "USER      -  Username: " + this.getUsername() + ";  Mail: " + this.getMail() + ".";
	}

}
