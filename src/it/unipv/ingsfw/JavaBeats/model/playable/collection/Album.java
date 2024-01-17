package it.unipv.ingsfw.JavaBeats.model.playable.collection;

import it.unipv.ingsfw.JavaBeats.dao.playable.AudioDAO;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import java.sql.Blob;
import java.util.ArrayList;

public class Album extends JBCollection{

  //ATTRIBUTE:
  private ArrayList<JBAudio> trackList;


  //CONSTRUCTORS:
  public Album(int id, String name, JBProfile creator, ArrayList<JBAudio> trackList, Blob picture){
    super(id, name, creator, picture);
    this.trackList=trackList;
  }
  public Album(int id, String name, JBProfile creator, ArrayList<JBAudio> trackList){
    this(id, name, creator, trackList, null);
  }


  //GETTER:
  @Override
  public ArrayList<JBAudio> getTrackList(){
    return trackList;
  }

  @Override
  public JBCollection getCopy(){
    return new Album(this.getId(), this.getName(), this.getCreator(), this.trackList, this.getPicture());
  }


  //SETTER:
  @Override
  public void setTrackList(ArrayList<JBAudio> trackList){
    this.trackList=trackList;
  }


  //METHODS:
  @Override
  public String toString(){
    return "ALBUM     -  Name: "+this.getName()+";  Creator Mail: "+this.getCreator().getMail()+".";
  }

  @Override
  public void playFX(){
    AudioDAO a= new AudioDAO();
    ArrayList<Song> album=a.selectByAlbum(this);
    for(Song song: album){
      song.playFX();
    }
  }

}
