package it.unipv.ingsfw.JavaBeats.model.playable;

import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;

import java.sql.Blob;
import java.util.ArrayList;

public class Playlist extends JBCollection {

	//ATTRIBUTES:
	private ArrayList<JBAudio> trackList;
	private boolean isVisible = true;


	//CONSTRUCTOR:
	public Playlist(String id, String name, JBProfile creator, ArrayList<JBAudio> trackList, Blob picture, boolean isVisible) {
		super(id, name, creator, picture);
		this.trackList=trackList;
		this.isVisible=isVisible;
	}
	public Playlist(String id, String name, JBProfile creator, ArrayList<JBAudio> trackList) {
		this(id, name, creator, trackList, null, true);
	}
	public Playlist(String id, String name, JBProfile creator) {
		this(id, name, creator, null, null, true);
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
