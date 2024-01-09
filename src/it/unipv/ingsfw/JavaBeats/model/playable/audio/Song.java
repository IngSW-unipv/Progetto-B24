package it.unipv.ingsfw.JavaBeats.model.playable.audio;

import it.unipv.ingsfw.JavaBeats.model.playable.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;

public class Song extends JBAudio {

	//CONSTRUCTORS:
	public Song(String id, String title, Artist artist, JBCollection collection, Blob audioFile, Time duration, Date releaseDate, String[] genres, boolean isFavourite, int numbersOfStreams) {
		super(id, title, artist, collection, audioFile, duration, releaseDate, genres, isFavourite, numbersOfStreams);
	}
	public Song(String id, String title, Artist artist, Blob audioFile) {
		this(id, title, artist, null, audioFile, Time.valueOf("00:00:00"), new Date(System.currentTimeMillis()), null, false, 0);
	}

}
