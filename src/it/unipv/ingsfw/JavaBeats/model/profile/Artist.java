package it.unipv.ingsfw.JavaBeats.model.profile;

import it.unipv.ingsfw.JavaBeats.exceptions.SystemErrorException;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class representing an artist capable of uploading {@link JBAudio} (both {@link it.unipv.ingsfw.JavaBeats.model.playable.audio.Song} and {@link it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode}) and create {@link it.unipv.ingsfw.JavaBeats.model.collection.JBCollection} ({@link it.unipv.ingsfw.JavaBeats.model.collection.Album}, {@link Playlist}, {@link it.unipv.ingsfw.JavaBeats.model.collection.Podcast}).
 * Contains attributes and methods common both to {@link User} and {@link Artist}.
 *
 * @see JBProfile
 * @see User
 * @see Artist
 * @see JBAudio
 * @see it.unipv.ingsfw.JavaBeats.model.playable.audio.Song
 * @see it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode
 * @see it.unipv.ingsfw.JavaBeats.model.collection.JBCollection
 * @see it.unipv.ingsfw.JavaBeats.model.collection.Album
 * @see it.unipv.ingsfw.JavaBeats.model.collection.Playlist
 * @see it.unipv.ingsfw.JavaBeats.model.collection.Podcast
 */
public class Artist extends JBProfile{

  //ATTRIBUTES:
  private int totalListeners;


  //CONSTRUCTORS:

  /**
   * Complete constructor to initialize all parameters.
   */
  public Artist(String username, String mail, String password, String name, String surname, String biography, Blob profilePicture, int totalListeners, ArrayList<JBAudio> listeningHistory, Playlist favorites){
    super(username, mail, password, name, surname, biography, profilePicture, listeningHistory, favorites);
    this.totalListeners=totalListeners;
  }

  /**
   * Minimal constructor to initialize strictly necessary parameters.
   */
  public Artist(String username, String mail, String password){
    this(username, mail, password, null, null, null, null, 0, null, null);
  }


  //GETTERS:

  /**
   * Returns number of total listeners as an int.
   *
   * @return totalListeners
   */
  public int getTotalListeners(){
    return totalListeners;
  }

  /**
   * Returns a clone of current artist as a {@link JBProfile}.
   *
   * @return artist clone
   */
  public JBProfile getCopy() throws SystemErrorException{
    try{
      return new Artist(this.getUsername(), this.getMail(), this.getPassword(), this.getName(), this.getSurname(), this.getBiography(), new SerialBlob(getProfilePicture()), this.totalListeners, this.getListeningHistory(), this.getFavorites());
    }catch(SQLException s){
      throw new SystemErrorException();
    }//end-try
  }


  //SETTER:
  /**
   * Sets int as the new total listeners count.
   *
   * @param totalListeners new total listeners count
   */
  public void setTotalListeners(int totalListeners){
    this.totalListeners=totalListeners;
  }


  //METHODS:
  /**
   * Override of toString to return a {@link String} with characterizing information.
   */
  @Override
  public String toString(){
    return "ARTIST  -  Username: "+this.getUsername()+"; Mail: "+this.getMail()+".";
  }

}
