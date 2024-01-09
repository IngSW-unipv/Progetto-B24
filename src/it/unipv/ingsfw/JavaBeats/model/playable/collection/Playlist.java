package it.unipv.ingsfw.JavaBeats.model.playable.collection;

import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

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
	@Override
	public ArrayList<JBAudio> getTrackList() {
		return trackList;
	}

	public JBCollection getCopy(JBCollection collection) {

		return new Playlist(collection.getId(), collection.getName(), collection.getCreator(), ((Playlist)collection).getTrackList(), collection.getPicture(), ((Playlist)collection).isVisible);
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

}
