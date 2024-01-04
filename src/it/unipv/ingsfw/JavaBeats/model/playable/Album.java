package it.unipv.ingsfw.JavaBeats.model.playable;

import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;
import java.sql.Blob;
import java.util.ArrayList;

public class Album extends JBCollection {

	//ATTRIBUTE:
	private ArrayList<Song> trackList;


	//CONSTRUCTORS:
	public Album(String id, String name, JBProfile creator, Blob picture) {
		super(id, name, creator, picture);
	}
	public Album(String id, String name, JBProfile creator) {
		this(id, name, creator, null);
	}


	//GETTER:
	public ArrayList<Song> getTrackList() {
		return trackList;
	}


	//SETTER:
	public void setTrackArray(ArrayList<Song> trackList) {
		this.trackList = trackList;
	}

}
