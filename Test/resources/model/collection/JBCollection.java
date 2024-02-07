package it.unipv.ingsfw.JavaBeats.model.collection;

import it.unipv.ingsfw.JavaBeats.exceptions.SystemErrorException;
import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Abstract class representing a generic JavaBeats collection.
 * Contains attributes and methods common to {@link Album}, {@link Playlist} and {@link Podcast}.
 *
 * @author Giorgio Giacomotti
 * @see Album
 * @see Playlist
 * @see Podcast
 */
public abstract class JBCollection implements IJBResearchable{

  //ATTRIBUTES:
  private int id;
  private String name;
  private JBProfile creator;
  private Blob picture;


  //CONSTRUCTOR:

  /**
   * Complete constructor to initialize all parameters.
   * Note that an abstract class cannot be instantiated, this constructor will be used by classes that extend JBCollection to initialize parameters.
   */
  protected JBCollection(int id, String name, JBProfile creator, Blob picture){
    this.id=id;
    this.name=name;
    this.creator=creator;
    this.picture=picture;
  }


  //GETTERS:

  /**
   * Returns collection id as an int.
   *
   * @return id
   */
  public int getId(){
    return id;
  }

  /**
   * Returns collection name as a {@link String}.
   *
   * @return name
   */
  public String getName(){
    return name;
  }

  /**
   * Returns collection creator as a {@link JBProfile}.
   *
   * @return creator
   */
  public JBProfile getCreator(){
    return creator;
  }

  /**
   * Returns profile picture as a {@link Blob}.
   *
   * @return profile picture
   */
  public Blob getPicture(){
    return picture;
  }

  /**
   * Abstract method to be implemented.
   * Is expected to return the track list of a collection as an {@link ArrayList} of {@link JBAudio}.
   *
   * @return track list
   */
  public abstract ArrayList<JBAudio> getTrackList();

  /**
   * Abstract method to be implemented.
   * Is expected to return a clone of the collection as a {@link JBCollection}.
   *
   * @return collection clone
   */
  public abstract JBCollection getCopy() throws SystemErrorException;


  //SETTERS:

  /**
   * Sets int as the new collection id.
   *
   * @param id new id
   */
  public void setId(int id){
    this.id=id;
  }

  /**
   * Sets {@link String} as the new collection name.
   *
   * @param name new name
   */
  public void setName(String name){
    this.name=name;
  }

  /**
   * Sets {@link JBProfile} as the new collection creator.
   *
   * @param creator new creator
   */
  public void setCreator(JBProfile creator){
    this.creator=creator;
  }

  /**
   * Sets {@link Blob} as the new collection picture.
   *
   * @param picture new picture
   */
  public void setPicture(Blob picture){
    this.picture=picture;
  }

  /**
   * Abstract method to be implemented.
   * Is expected set an {@link ArrayList} of {@link JBAudio} as the new collection trackList.
   *
   * @param trackList new trackList
   */
  public abstract void setTrackList(ArrayList<JBAudio> trackList);


  //METHODS:

  /**
   * Override of equals to compare {@link JBCollection}.
   */
  @Override
  public boolean equals(Object obj){
    JBCollection jbCollection=(JBCollection)obj;

    if(jbCollection!=null && this.id==jbCollection.getId()){
      return true;
    }//end-if
    return false;
  }

  /**
   * Returns the collection picture as an {@link Image} of the required size expressed as an int.
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
      bufferedImage=ImageIO.read(new ByteArrayInputStream(this.picture.getBinaryStream().readAllBytes()));
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
