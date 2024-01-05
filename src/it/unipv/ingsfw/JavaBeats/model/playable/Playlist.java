package it.unipv.ingsfw.JavaBeats.model.playable;

import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;

import java.sql.Blob;
import java.util.ArrayList;

public class Playlist extends JBCollection {

	//ATTRIBUTES:
	private ArrayList<JBAudio> trackList;
	private boolean isVisible = true;


	//CONSTRUCTOR:
	public Playlist(String id, String name, JBProfile creator, Blob picture, boolean isVisible) {
		super(id, name, creator, picture);
		this.isVisible=isVisible;
	}
	public Playlist(String id, String name, JBProfile creator) {
		this(id, name, creator, null, true);
	}


	//GETTERS:
	public ArrayList<JBAudio> getTrackList() {
		return trackList;
	}
	public boolean isVisible() {
		return isVisible;
	}


	//SETTERS:
	public void setTrackList(ArrayList<JBAudio> trackList) {
		this.trackList = trackList;
	}
	public void setVisible(boolean Visible) {
		isVisible = Visible;
	}

}
