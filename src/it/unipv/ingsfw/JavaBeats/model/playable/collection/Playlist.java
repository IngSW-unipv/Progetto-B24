package it.unipv.ingsfw.JavaBeats.model.playable.collection;

import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import java.sql.Blob;
import java.util.ArrayList;

public class Playlist extends JBCollection {

	//ATTRIBUTES:
	private ArrayList<JBAudio> trackList;
	private boolean isVisible;


	//CONSTRUCTOR:
	public Playlist(int id, String name, JBProfile creator, ArrayList<JBAudio> trackList, Blob picture, boolean isVisible) {
		super(id, name, creator, picture);
		this.trackList=trackList;
		this.isVisible=isVisible;
	}
	public Playlist(int id, String name, JBProfile creator, ArrayList<JBAudio> trackList) {
		this(id, name, creator, trackList, null, true);
	}
	public Playlist(int id, String name, JBProfile creator) {
		this(id, name, creator, null, null, true);
	}


	//GETTERS:
	@Override
	public ArrayList<JBAudio> getTrackList() {
		return trackList;
	}

	@Override
	public JBCollection getCopy() {
		return new Playlist(this.getId(), this.getName(), this.getCreator(), this.trackList, this.getPicture(), this.isVisible);
	}

	public boolean isVisible() {
		return isVisible;
	}


	//SETTERS:
	@Override
	public void setTrackList(ArrayList trackList) {
		this.trackList = trackList;
	}
	public void setVisible(boolean Visible) {
		isVisible = Visible;
	}


	//METHODS:
	@Override
	public String toString() {
		return "PLAYLIST  -  Name: " + this.getName() + ";  Creator Mail: " + this.getCreator().getMail() + ".";
	}

}
