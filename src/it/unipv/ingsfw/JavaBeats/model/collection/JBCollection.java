package it.unipv.ingsfw.JavaBeats.model.collection;

import it.unipv.ingsfw.JavaBeats.model.EJBMODE;
import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.playable.IJBItem;
import it.unipv.ingsfw.JavaBeats.model.playable.IJBPlayable;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import java.sql.Blob;
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
  public void addItem(JBProfile activeJBProfile, IJBItem item){
  }

  public void removeItem(JBProfile activeJBProfile, IJBItem item){
  }
}
