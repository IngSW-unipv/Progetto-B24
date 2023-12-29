package it.unipv.ingsfw.JavaBeats.model.playable;

import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;
import java.util.List;

public class Playlist extends JBCollection {

	//ATTRIBUTES:
	private List<JBAudio> trackList;
	private boolean isVisible = false;


	//CONSTRUCTOR:
	public Playlist(String id, String name, JBProfile creator) {
		super(id, name, creator);
	}


	//GETTERS:
	public List<JBAudio> getTrackList() {
		return trackList;
	}
	public boolean isVisible() {
		return isVisible;
	}


	//SETTERS:
	public void setTrackList(List<JBAudio> trackList) {
		this.trackList = trackList;
	}
	public void setVisible(boolean Visible) {
		isVisible = Visible;
	}

}
