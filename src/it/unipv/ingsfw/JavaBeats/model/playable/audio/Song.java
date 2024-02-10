package it.unipv.ingsfw.JavaBeats.model.playable.audio;

import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Class representing a Song that can be part of an {@link it.unipv.ingsfw.JavaBeats.model.collection.Album} or a {@link Playlist}.
 *
 * @see JBAudio
 * @see it.unipv.ingsfw.JavaBeats.model.collection.Album
 * @see Playlist
 */
public final class Song extends JBAudio{

  //CONSTRUCTORS:

  /**
   * Complete constructor to initialize all parameters.
   */
  public Song(int id, String title, Artist artist, JBCollection collection, Blob audioFile, double duration, Date releaseDate, String[] genres, boolean isFavourite, int numbersOfStreams){
    super(id, title, artist, collection, audioFile, duration, releaseDate, genres, isFavourite, numbersOfStreams);
  }

  /**
   * Minimal constructor to initialize strictly necessary parameters.
   */
  public Song(int id, String title, Artist artist, Blob audioFile){
    this(id, title, artist, null, audioFile, 00.00, new Date(System.currentTimeMillis()), null, false, 0);
  }


  //METHODS:

  /**
   * Override of toString to return a {@link String} with characterizing information.
   */
  @Override
  public String toString(){
    return "SONG  -  Title: "+this.getMetadata().getTitle()+"; Artist Mail: "+this.getMetadata().getArtist().getMail()+".";
  }

  /**
   * Override of playFX to play audioFile associated with the song.
   */
  @Override
  public void playFX(){
    if(super.mediaPlayer==null || super.mediaPlayer.getStatus().equals(MediaPlayer.Status.DISPOSED)){
      try{
        /* Creating temporary file so that It can be played. It's removed once on exit */
        File f=new File("tmp");
        f.deleteOnExit();
        FileUtils.writeByteArrayToFile(f, this.getAudioFileBlob().getBinaryStream().readAllBytes());

        Media song=new Media(f.toURI().toURL().toString());
        super.mediaPlayer=new MediaPlayer(song);
        super.mediaPlayer.play();
      }catch(IOException | SQLException e){
        throw new RuntimeException(e);
      }
    }else{
      super.mediaPlayer.play();
    }//end-if
  }

  /**
   * Override of pauseFX to pause audioFile associated with the song.
   */
  @Override
  public void pauseFX(){
    if(super.mediaPlayer!=null){
      super.mediaPlayer.pause();
    }//end-if
  }
}
