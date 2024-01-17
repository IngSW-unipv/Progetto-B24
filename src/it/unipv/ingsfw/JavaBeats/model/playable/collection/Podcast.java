package it.unipv.ingsfw.JavaBeats.model.playable.collection;

import java.sql.Blob;
import java.util.ArrayList;

import it.unipv.ingsfw.JavaBeats.dao.playable.AudioDAO;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

public class Podcast extends JBCollection{

  //ATTRIBUTE:
  private ArrayList<JBAudio> trackList;


  //CONSTRUCTOR:
  public Podcast(int id, String name, JBProfile creator, ArrayList<JBAudio> trackList, Blob picture){
    super(id, name, creator, picture);
    this.trackList=trackList;
  }
  public Podcast(int id, String name, JBProfile creator, ArrayList<JBAudio> trackList){
    this(id, name, creator, trackList, null);
  }


  //GETTERS:
  @Override
  public ArrayList<JBAudio> getTrackList(){
    return trackList;
  }

  @Override
  public JBCollection getCopy(){
    return new Podcast(this.getId(), this.getName(), this.getCreator(), this.trackList, this.getPicture());
  }


  //SETTER:
  @Override
  public void setTrackList(ArrayList<JBAudio> trackList){
    this.trackList=trackList;
  }


  //METHODS:
  @Override
  public String toString(){
    return "PODCAST   -  Name: "+this.getName()+";  Creator Mail: "+this.getCreator().getMail()+".";
  }

  @Override
  public void playFX(){
    AudioDAO a= new AudioDAO();
    ArrayList<Episode> podcast=a.selectByPodcast(this);
    for(Episode episode: podcast){
      episode.playFX();
    }
  }


}
