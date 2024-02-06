package it.unipv.ingsfw.JavaBeats.model.collection;

import java.sql.Blob;
import java.util.ArrayList;

import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

/**
 * Class representing a Podcast containing {@link it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode} and created by an {@link it.unipv.ingsfw.JavaBeats.model.profile.Artist}.
 *
 * @author Giorgio Giacomotti
 * @see it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode
 * @see it.unipv.ingsfw.JavaBeats.model.profile.Artist
 */
public class Podcast extends JBCollection{

  //ATTRIBUTE:
  private ArrayList<JBAudio> trackList;


  //CONSTRUCTOR:

  /**
   * Complete constructor to initialize all parameters.
   */
  public Podcast(int id, String name, JBProfile creator, ArrayList<JBAudio> trackList, Blob picture){
    super(id, name, creator, picture);
    this.trackList=trackList;
  }

  /**
   * Minimal constructor to initialize strictly necessary parameters.
   */
  public Podcast(int id, String name, JBProfile creator, ArrayList<JBAudio> trackList){
    this(id, name, creator, trackList, null);
  }


  //GETTERS:

  /**
   * Returns the track list of a podcast as an {@link ArrayList} of {@link JBAudio}.
   *
   * @return podcast clone
   */
  @Override
  public ArrayList<JBAudio> getTrackList(){
    return trackList;
  }

  /**
   * Returns a clone of the podcast as a {@link JBCollection}.
   *
   * @return podcast clone
   */
  @Override
  public JBCollection getCopy(){
    return new Podcast(this.getId(), this.getName(), this.getCreator(), new ArrayList<>(this.trackList), this.getPicture());
  }


  //SETTER:

  /**
   * Sets an {@link ArrayList} of {@link JBAudio} as the new podcast trackList.
   *
   * @param trackList new trackList
   */
  @Override
  public void setTrackList(ArrayList<JBAudio> trackList){
    this.trackList=trackList;
  }


  //METHODS:

  /**
   * Override of toString to return a {@link String} with characterizing information.
   */
  @Override
  public String toString(){
    return "PODCAST  -  Name: "+this.getName()+"; Creator Mail: "+this.getCreator().getMail()+".";
  }

}
