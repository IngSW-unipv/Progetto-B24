package it.unipv.ingsfw.JavaBeats.model.user;

import java.sql.Time;

public class User extends JBProfile {

	//ATTRIBUTES:
	private boolean isVisible = true;
	private Time minuteListened;


	//CONSTRUCTOR:
	public User(String username, String mail, String password) {
		super(username, mail, password);
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
}
