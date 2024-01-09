package it.unipv.ingsfw.JavaBeats.model.playable.collection;

import java.sql.Blob;
import java.util.ArrayList;

import it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

public class Podcast extends JBCollection {

	//ATTRIBUTE:
	private ArrayList<Episode> trackList;


	//CONSTRUCTOR:
	public Podcast(int id, String name, JBProfile creator, ArrayList<Episode> trackList, Blob picture) {
		super(id, name, creator, picture);
		this.trackList=trackList;
	}
	public Podcast(int id, String name, JBProfile creator, ArrayList<Episode> trackList) {
		this(id, name, creator, trackList, null);
	}


	//GETTERS:
	@Override
	public ArrayList<Episode> getTrackList() {
		return trackList;
	}

	@Override
	public JBCollection getCopy(JBCollection collection) {

		return new Podcast(collection.getId(), collection.getName(), collection.getCreator(), ((Podcast)collection).getTrackList(), collection.getPicture());
	}



	//SETTER:
	@Override
	public void setTrackList(ArrayList trackList) {
		this.trackList = trackList;
	}


	//METHODS:
	@Override
	public String toString() {
		return "PODCAST   -  Name: " + this.getName() + ";  Creator Mail: " + this.getCreator().getMail() + ".";
	}

}
