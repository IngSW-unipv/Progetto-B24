package it.unipv.ingsfw.JavaBeats.model.playable;

import java.sql.Blob;
import java.util.ArrayList;
import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;

public class Podcast extends JBCollection {

	//ATTRIBUTE:
	private ArrayList<Episode> trackList;


	//CONSTRUCTOR:
	public Podcast(String id, String name, JBProfile creator, ArrayList<Episode> trackList, Blob picture) {
		super(id, name, creator, picture);
		this.trackList=trackList;
	}
	public Podcast(String id, String name, JBProfile creator, ArrayList<Episode> trackList) {
		this(id, name, creator, trackList, null);
	}


	//GETTER:
	public ArrayList<Episode> getTrackList() {
		return trackList;
	}


	//SETTER:
	public void setTrackList(ArrayList<Episode> trackList) {
		this.trackList = trackList;
	}

}
