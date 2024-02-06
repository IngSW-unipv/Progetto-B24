package it.unipv.ingsfw.JavaBeats.model.profile;

import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Abstract class representing a generic JavaBeats profile.
 * Contains attributes and methods common both to {@link User} and {@link Artist}.
 *
 * @author Giorgio Giacomotti
 * @see IJBResearchable
 * @see User
 * @see Artist
 */
public abstract class JBProfile implements IJBResearchable{

  //ATTRIBUTES:
  private String username, mail, password, name, surname, biography;
  private Blob profilePicture;
  private ArrayList<JBAudio> listeningHistory;
  private Playlist favorites;


  //CONSTRUCTORS:
  /**
   * Complete constructor to initialize all parameters.
   * Note that an abstract class cannot be instantiated, this constructor will be used by classes that extend JBProfile to initialize parameters.
   */
  protected JBProfile(String username, String mail, String password, String name, String surname, String biography, Blob profilePicture, ArrayList<JBAudio> listeningHistory, Playlist favorites){
    this.username=username;
    this.mail=mail;
    this.password=password;
    this.name=name;
    this.surname=surname;
    this.biography=biography;
    this.profilePicture=profilePicture;
    this.listeningHistory=listeningHistory;
    this.favorites=favorites;
  }


  //GETTERS:
  /**
   * Returns profile username as a {@link String}.
   *
   * @return username
   */
  public String getUsername(){
    return username;
  }

  /**
   * Returns profile mail as a {@link String}.
   *
   * @return mail
   */
  public String getMail(){
    return mail;
  }

  /**
   * Returns profile password as a {@link String}.
   *
   * @return password
   */
  public String getPassword(){
    return password;
  }

  /**
   * Returns profile name as a {@link String}.
   *
   * @return name
   */
  public String getName(){
    return name;
  }

  /**
   * Returns profile surname as a {@link String}.
   *
   * @return surname
   */
  public String getSurname(){
    return surname;
  }

  /**
   * Returns profile biography as a {@link String}.
   *
   * @return biography
   */
  public String getBiography(){
    return biography;
  }

  /**
   * Returns profile picture as a {@link Blob}.
   *
   * @return profile picture
   */
  public Blob getProfilePicture(){
    return profilePicture;
  }

  /**
   * Returns listening history as an {@link ArrayList} of {@link JBAudio}.
   *
   * @return listening history
   */
  public ArrayList<JBAudio> getListeningHistory(){
    return listeningHistory;
  }

  /**
   * Returns favorites playlist as a {@link Playlist}.
   *
   * @return listening history
   */
  public Playlist getFavorites(){
    return favorites;
  }

  /**
   * Abstract method to be implemented.
   * Is expected to return a clone of the profile as a {@link JBProfile}.
   *
   * @return profile clone
   */
  public abstract JBProfile getCopy();


  //SETTERS:
  /**
   * Sets {@link String} as the new username.
   *
   * @param username new username
   */
  public void setUsername(String username){
    this.username=username;
  }

  /**
   * Sets {@link String} as the new mail.
   *
   * @param mail new mail
   */
  public void setMail(String mail){
    this.mail=mail;
  }

  /**
   * Sets {@link String} as the new password.
   *
   * @param password new password
   */
  public void setPassword(String password){
    this.password=password;
  }

  /**
   * Sets {@link String} as the new name.
   *
   * @param name new name
   */
  public void setName(String name){
    this.name=name;
  }

  /**
   * Sets {@link String} as the new surname.
   *
   * @param surname new surname
   */
  public void setSurname(String surname){
    this.surname=surname;
  }

  /**
   * Sets {@link String} as the new biography.
   *
   * @param biography new biography
   */
  public void setBiography(String biography){
    this.biography=biography;
  }

  /**
   * Sets {@link Blob} as the new profile picture.
   *
   * @param profilePicture new profile picture
   */
  public void setProfilePicture(Blob profilePicture){
    this.profilePicture=profilePicture;
  }

  /**
   * Sets {@link ArrayList} of {@link JBAudio} as the new listening history.
   *
   * @param listeningHistory new listening history
   */
  public void setListeningHistory(ArrayList<JBAudio> listeningHistory){
    this.listeningHistory=listeningHistory;
  }

  /**
   * Sets {@link Playlist} as the new favorites playlist.
   *
   * @param favorites new favorite playlist
   */
  public void setFavorites(Playlist favorites){
    this.favorites=favorites;
  }

  /**
   * Override of equals to compare {@link JBProfile}.
   */
  @Override
  public boolean equals(Object obj){
    JBProfile profile=(JBProfile)obj;
    try{
      if(this.username.equals(profile.getUsername()) && this.mail.equals(profile.getMail()) && this.password.equals(profile.getPassword()) && this.name.equals(profile.getName()) && this.surname.equals(profile.getSurname()) && this.biography.equals(profile.getBiography()) && Arrays.equals(this.profilePicture.getBinaryStream().readAllBytes(), profile.getProfilePicture().getBinaryStream().readAllBytes()) && this.getClass().equals(profile.getClass())){
        return true;
      }else{
        return false;
      }//end-if
    }catch(IOException | SQLException e){
      throw new RuntimeException(e);
    }//end-try
  }

  /**
   * Returns the profile picture as an {@link Image} of the required size expressed as an int.
   *
   * @param size desired picture size
   * @return scaled collection picture
   */
  public Image scalePicture(int size){
    /*
    Downscaling the collection image to a size square so that it fits
    * */
    //Creating a buffered image from collection picture
    BufferedImage bufferedImage=null;
    try{
      bufferedImage=ImageIO.read(new ByteArrayInputStream(this.profilePicture.getBinaryStream().readAllBytes()));
      //Downscaling
      BufferedImage outputImage=new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
      outputImage.getGraphics().drawImage(bufferedImage.getScaledInstance(size, size, java.awt.Image.SCALE_DEFAULT), 0, 0, null);

      //Creating an output stream for reading the byte[]
      ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
      ImageIO.write(outputImage, "png", byteArrayOutputStream);

      return new Image(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
    }catch(IOException | SQLException e){
      throw new RuntimeException(e);
    }//end-try
  }
}


