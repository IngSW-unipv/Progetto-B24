package it.unipv.ingsfw.JavaBeats.model.playable;

import it.unipv.ingsfw.JavaBeats.model.user.Artist;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;

public class Song extends JBAudio {

	//CONSTRUCTORS:
	public Song(String id, String title, Artist artist, JBCollection collection, Blob audioFile, Time duration, Date releaseDate, String[] genres, boolean isFavourite) {
		super(id, title, artist, collection, audioFile, duration, releaseDate, genres, isFavourite);
	}
	public Song(String id, Metadata metadata, Blob audioFile, boolean isFavorite) {
		this(id, metadata.getTitle(), metadata.getArtist(), metadata.getCollection(), audioFile, metadata.getDuration(), metadata.getReleaseDate(), metadata.getGenres(), isFavorite);
	}
	public Song(String id, String title, Artist artist, Blob audioFile) {
		this(id, title, artist, null, audioFile, null, null, null, false);
	}

}
