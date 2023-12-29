package it.unipv.ingsfw.JavaBeats.model.playable;

import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;

public class Album extends JBCollection {

	//ATTRIBUTE:
	private Song[] trackArray;


	//CONSTRUCTOR:
	public Album(String id, String name, JBProfile creator) {
		super(id, name, creator);
	}


	//GETTER:
	public Song[] getTrackArray() {
		return trackArray;
	}


	//SETTER:
	public void setTrackArray(Song[] trackArray) {
		this.trackArray = trackArray;
	}

}
