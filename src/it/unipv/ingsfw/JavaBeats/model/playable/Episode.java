package it.unipv.ingsfw.JavaBeats.model.playable;

import it.unipv.ingsfw.JavaBeats.model.user.Artist;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;

public class Episode extends JBAudio {

	//CONSTRUCTORS:
	public Episode(String id, String title, Artist artist, JBCollection collection, Blob audioFile, Time duration, Date releaseDate, String[] genres, boolean isFavorite) {
		super(id, title, artist, collection, audioFile, duration, releaseDate, genres, isFavorite);
	}
	public Episode(String id, String title, Artist artist, Blob audioFile) {
		this(id, title, artist, null, audioFile, null, null, null, false);
	}

}
