package it.unipv.ingsfw.JavaBeats.model.user;

public class Artist extends JBProfile {

	//ATTRIBUTES:
	private int totalListeners;


	//CONSTRUCTOR:
	public Artist(String username, String mail, String password) {
		super(username, mail, password);
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
