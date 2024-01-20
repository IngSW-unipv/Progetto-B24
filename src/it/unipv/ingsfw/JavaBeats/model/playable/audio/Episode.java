package it.unipv.ingsfw.JavaBeats.model.playable.audio;

import it.unipv.ingsfw.JavaBeats.model.playable.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

public class Episode extends JBAudio{

  //CONSTRUCTORS:
  public Episode(int id, String title, Artist artist, JBCollection collection, Blob audioFile, Time duration, Date releaseDate, String[] genres, boolean isFavorite, int numbersOfStreams){
    super(id, title, artist, collection, audioFile, duration, releaseDate, genres, isFavorite, numbersOfStreams);
  }

  public Episode(int id, String title, Artist artist, Blob audioFile){
    this(id, title, artist, null, audioFile, Time.valueOf("00:00:00"), new Date(System.currentTimeMillis()), null, false, 0);
  }


  //METHODS:
  @Override
  public String toString(){
    return "EPISODE   -  Title: "+this.getMetadata().getTitle()+";  Artist Mail: "+this.getMetadata().getArtist().getMail()+".";
  }

  @Override
  public void playFX(){
    try{
      /* Creating temporary file so that It can be played. It's removed once on exit */
      File f=new File("tmp");
      f.deleteOnExit();
      FileUtils.writeByteArrayToFile(f, this.getAudioFileBlob().getBinaryStream().readAllBytes());

      Media episode=new Media(f.toURI().toURL().toString());
      MediaPlayer mediaPlayer=new MediaPlayer(episode);
      mediaPlayer.play();
    }catch(IOException | SQLException e){
      throw new RuntimeException(e);
    }
  }
}
