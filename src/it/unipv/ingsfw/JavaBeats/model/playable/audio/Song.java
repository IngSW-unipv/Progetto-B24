package it.unipv.ingsfw.JavaBeats.model.playable.audio;

import it.unipv.ingsfw.JavaBeats.model.playable.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import javafx.scene.media.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

public class Song extends JBAudio {

	//CONSTRUCTORS:
	public Song(int id, String title, Artist artist, JBCollection collection, Blob audioFile, Time duration, Date releaseDate, String[] genres, boolean isFavourite, int numbersOfStreams) {
		super(id, title, artist, collection, audioFile, duration, releaseDate, genres, isFavourite, numbersOfStreams);
	}
	public Song(int id, String title, Artist artist, Blob audioFile) {
		this(id, title, artist, null, audioFile, Time.valueOf("00:00:00"), new Date(System.currentTimeMillis()), null, false, 0);
	}


	//METHODS:
	@Override
	public String toString() {
		return "SONG      -  Title: " + this.getMetadata().getTitle() + ";  Artist Mail: " + this.getMetadata().getArtist().getMail() + ".";
	}

	//PlayFX

	@Override
	public void playFX(){
		try{
			File tmp= new File("tmp");
			tmp.deleteOnExit();
			FileOutputStream fileOutputStream= new FileOutputStream(tmp);
			fileOutputStream.write(this.getAudioFileBlob().getBinaryStream().readAllBytes());
			Media song = new Media("tmp");
			MediaPlayer mediaPlayer = new MediaPlayer(song);
			mediaPlayer.play();
			fileOutputStream.close();

		}catch(IOException| SQLException e){
			throw new RuntimeException(e);
		}
    }

}
