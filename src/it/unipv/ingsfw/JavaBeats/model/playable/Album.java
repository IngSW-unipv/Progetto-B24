package it.unipv.ingsfw.JavaBeats.model.playable;

import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;

import java.sql.Blob;

public class Album extends JBCollection {

	//ATTRIBUTE:
	private Song[] trackArray;


	//CONSTRUCTORS:
	public Album(String id, String name, JBProfile creator, Blob picture) {
		super(id, name, creator, picture);
	}
	public Album(String id, String name, JBProfile creator) {
		this(id, name, creator, null);
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
