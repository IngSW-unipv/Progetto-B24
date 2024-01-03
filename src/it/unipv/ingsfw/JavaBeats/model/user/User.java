package it.unipv.ingsfw.JavaBeats.model.user;


import java.sql.Blob;
import java.sql.Time;

public class User extends JBProfile {

	//ATTRIBUTES:
	private boolean isVisible = true;
	private Time minuteListened;


	//CONSTRUCTOR:
	public User(String username, String mail, String password, String name, String surname, String biography, Blob profilePicture, boolean isVisible) {
		super(username, mail, password, name, surname, biography, profilePicture);
		this.isVisible=isVisible;
	}
	public User(String username, String mail, String password) {
		this(username, mail, password, null, null, null, null, true);
	}


	//GETTERS:
	public boolean isVisible() {
		return isVisible;
	}
	public Time getMinuteListened() {
		return minuteListened;
	}


	//SETTERS:
	public void setVisible(boolean visible) {
		isVisible = visible;
	}
	public void setMinuteListened(Time minuteListened) {
		this.minuteListened = minuteListened;
	}


	//METHODS:
	@Override
	public String toString() {
		return "Mail: " + this.getMail() + "; Username: " + this.getUsername() ;
	}
}
