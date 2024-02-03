package it.unipv.ingsfw.JavaBeats.model.collection;

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

public abstract class JBCollection implements IJBResearchable{

  //ATTRIBUTES:
  private int id;
  private String name;
  private JBProfile creator;
  private Blob picture;


  //CONSTRUCTORS:
  protected JBCollection(int id, String name, JBProfile creator, Blob picture){
    this.id=id;
    this.name=name;
    this.creator=creator;
    this.picture=picture;
  }

  protected JBCollection(int id, String name, JBProfile creator){
    this(id, name, creator, null);
  }


  //GETTERS:
  public int getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public JBProfile getCreator(){
    return creator;
  }

  public Blob getPicture(){
    return picture;
  }

  public abstract ArrayList<JBAudio> getTrackList();

  public abstract JBCollection getCopy();


  //SETTERS:
  public void setId(int id){
    this.id=id;
  }

  public void setName(String name){
    this.name=name;
  }

  public void setCreator(JBProfile creator){
    this.creator=creator;
  }

  public void setPicture(Blob picture){
    this.picture=picture;
  }

  public abstract void setTrackList(ArrayList<JBAudio> trackList);


  //METHODS:
  @Override
  public boolean equals(Object obj){
    JBCollection jbCollection=(JBCollection)obj;

    if(this.id==jbCollection.getId()){
      return true;
    }//end-if
    return false;
  }
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
