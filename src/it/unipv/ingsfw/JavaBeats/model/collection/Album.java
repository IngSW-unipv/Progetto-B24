package it.unipv.ingsfw.JavaBeats.model.collection;

import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import java.sql.Blob;
import java.util.ArrayList;

/**
 * Class representing an Album containing {@link it.unipv.ingsfw.JavaBeats.model.playable.audio.Song} and created by an {@link it.unipv.ingsfw.JavaBeats.model.profile.Artist}.
 *
 * @author Giorgio Giacomotti
 * @see it.unipv.ingsfw.JavaBeats.model.playable.audio.Song
 * @see it.unipv.ingsfw.JavaBeats.model.profile.Artist
 */
public class Album extends JBCollection{

  //ATTRIBUTE:
  private ArrayList<JBAudio> trackList;


  //CONSTRUCTORS:

  /**
   * Complete constructor to initialize all parameters.
   */
  public Album(int id, String name, JBProfile creator, ArrayList<JBAudio> trackList, Blob picture){
    super(id, name, creator, picture);
    this.trackList=trackList;
  }

  /**
   * Minimal constructor to initialize strictly necessary parameters.
   */
  public Album(int id, String name, JBProfile creator, ArrayList<JBAudio> trackList){
    this(id, name, creator, trackList, null);
  }


  //GETTER:

  /**
   * Returns the track list of an album as an {@link ArrayList} of {@link JBAudio}.
   *
   * @return album clone
   */
  @Override
  public ArrayList<JBAudio> getTrackList(){
    return trackList;
  }

  /**
   * Returns a clone of the album as a {@link JBCollection}.
   *
   * @return album clone
   */
  @Override
  public JBCollection getCopy(){
    return new Album(this.getId(), this.getName(), this.getCreator(), new ArrayList<>(this.trackList), this.getPicture());
  }


  //SETTER:

  /**
   * Sets an {@link ArrayList} of {@link JBAudio} as the new album trackList.
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
    return "ALBUM  -  Name: "+this.getName()+"; Creator Mail: "+this.getCreator().getMail()+".";
  }

}
