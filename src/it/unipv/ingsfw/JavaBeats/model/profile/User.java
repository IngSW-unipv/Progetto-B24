package it.unipv.ingsfw.JavaBeats.model.profile;

import it.unipv.ingsfw.JavaBeats.exceptions.SystemErrorException;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Class representing the base user capable of listening {@link JBAudio} (both {@link it.unipv.ingsfw.JavaBeats.model.playable.audio.Song} and {@link it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode}) and create {@link Playlist}.
 *
 * @author Giorgio Giacomotti
 * @see JBProfile
 * @see User
 * @see Artist
 * @see JBAudio
 * @see it.unipv.ingsfw.JavaBeats.model.playable.audio.Song
 * @see it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode
 * @see Playlist
 */
public class User extends JBProfile{

  //ATTRIBUTES:
  private boolean isVisible;
  private double totalListeningTime;


  //CONSTRUCTORS:
  /**
   * Complete constructor to initialize all parameters.
   */
  public User(String username, String mail, String password, String name, String surname, String biography, Blob profilePicture, boolean isVisible, double totalListeningTime, ArrayList<JBAudio> listeningHistory, Playlist favorites){
    super(username, mail, password, name, surname, biography, profilePicture, listeningHistory, favorites);
    this.isVisible=isVisible;
    this.totalListeningTime=totalListeningTime;
  }

  /**
   * Minimal constructor to initialize strictly necessary parameters.
   */
  public User(String username, String mail, String password){
    this(username, mail, password, null, null, null, null, true, 0, null, null);
  }

  /**
   * Constructor to initialize registration parameters.
   */
  public User(String username, String mail, String password, String name, String surname){
    this(username, mail, password, name, surname, null, null, true, 0, null, null);
  }


  //GETTERS:
  /**
   * Returns true if User is visible.
   *
   * @return visibility
   */
  public boolean isVisible(){
    return isVisible;
  }

  /**
   * Returns total listening {@link Time}.
   *
   * @return total listening time
   */
  public double getMinuteListened(){
    return totalListeningTime;
  }

  /**
   * Returns a clone of current user as a {@link JBProfile}.
   *
   * @return user clone
   */
  public JBProfile getCopy() throws SystemErrorException{
    try{
      return new User(this.getUsername(), this.getMail(), this.getPassword(), this.getName(), this.getSurname(), this.getBiography(), new SerialBlob(getProfilePicture()), this.isVisible, this.totalListeningTime, this.getListeningHistory(), this.getFavorites());
    }catch(SQLException s){
      throw new SystemErrorException();
    }//end-try
  }


  //SETTERS:
  /**
   * Sets {@link Boolean} as the new visibility.
   *
   * @param visible new visibility
   */
  public void setVisible(boolean visible){
    isVisible=visible;
  }

  /**
   * Sets {@link Time} as new total listening time.
   *
   * @param minuteListened new total listening time
   */
  public void setMinuteListened(double minuteListened){
    this.totalListeningTime=minuteListened;
  }


  //METHODS:
  /**
   * Override of toString to return a {@link String} with characterizing information.
   */
  @Override
  public String toString(){
    return "USER  -  Username: "+this.getUsername()+"; Mail: "+this.getMail()+".";
  }

}
