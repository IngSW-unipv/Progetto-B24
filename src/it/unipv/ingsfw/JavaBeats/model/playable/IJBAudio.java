package it.unipv.ingsfw.JavaBeats.model.playable;

import it.unipv.ingsfw.JavaBeats.model.user.Artist;
import java.sql.Date;
import java.sql.Time;

public interface IJBAudio extends IJBItem {
	public boolean isFavourite = false;
	public class Metadata {
		private Artist artist;
		private String title;
		private Time duration;
		private Date releaseDate;
		private String[] genres;
	}
	
	public void playFX();
	public void pause();
}