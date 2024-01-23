package it.unipv.ingsfw.JavaBeats.model.profile;

import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;

import java.sql.Blob;
import java.util.ArrayList;

public class Artist extends JBProfile{

  //ATTRIBUTES:
  private int totalListeners;


  //CONSTRUCTORS:
  public Artist(String username, String mail, String password, String name, String surname, String biography, Blob profilePicture, int totalListeners, ArrayList<JBAudio> listeningHistory, Playlist favorites){
    super(username, mail, password, name, surname, biography, profilePicture, listeningHistory, favorites);
    this.totalListeners=totalListeners;
  }

  public Artist(String username, String mail, String password){
    this(username, mail, password, null, null, null, null, 0, null, null);
  }


  //GETTERS:
  public int getTotalListeners(){
    return totalListeners;
  }

  public JBProfile getCopy(){
    return new Artist(this.getUsername(), this.getMail(), this.getPassword(), this.getName(), this.getSurname(), this.getBiography(), this.getProfilePicture(), this.totalListeners, this.getListeningHistory(), this.getFavorites());
  }


  //SETTER:
  public void setTotalListeners(int totalListeners){
    this.totalListeners=totalListeners;
  }


  //METHODS:
  @Override
  public String toString(){
    return "ARTIST    -  Username: "+this.getUsername()+";  Mail: "+this.getMail()+".";
  }

}
