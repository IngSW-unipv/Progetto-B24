package it.unipv.ingsfw.JavaBeats.model.playable.collection;

import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import java.sql.Blob;
import java.util.ArrayList;

public class Album extends JBCollection {

	//ATTRIBUTE:
	private ArrayList<Song> trackList;


	//CONSTRUCTORS:
	public Album(String id, String name, JBProfile creator, ArrayList<Song> trackList, Blob picture) {
		super(id, name, creator, picture);
		this.trackList=trackList;
	}
	public Album(String id, String name, JBProfile creator, ArrayList<Song> trackList) {
		this(id, name, creator, trackList, null);
	}


	//GETTER:
	@Override
	public ArrayList<Song> getTrackList() {
		return trackList;
	}

	@Override
	public JBCollection getCopy(JBCollection collection) {

		return new Album(collection.getId(), collection.getName(), collection.getCreator(), ((Album)collection).getTrackList(), collection.getPicture());
	}


	//SETTER:
	@Override
	public void setTrackList(ArrayList trackList) {
		this.trackList = trackList;
	}

}
