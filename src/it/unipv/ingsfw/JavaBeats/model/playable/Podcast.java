package it.unipv.ingsfw.JavaBeats.model.playable;

import java.util.List;
import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;

public class Podcast extends JBCollection {

	//ATTRIBUTE:
	private List<Episode> trackList;


	//CONSTRUCTOR:
	public Podcast(String id, String name, JBProfile creator) {
		super(id, name, creator);
	}


	//GETTER:
	public List<Episode> getTrackList() {
		return trackList;
	}


	//SETTER:
	public void setTrackList(List<Episode> trackList) {
		this.trackList = trackList;
	}

}
