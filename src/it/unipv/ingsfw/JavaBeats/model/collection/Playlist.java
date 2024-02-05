package it.unipv.ingsfw.JavaBeats.model.collection;

import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import java.sql.Blob;
import java.util.ArrayList;

/**
 * Class representing a Playlist containing {@link it.unipv.ingsfw.JavaBeats.model.playable.audio.Song} and {@link it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode} and created by a {@link it.unipv.ingsfw.JavaBeats.model.profile.User} or an {@link it.unipv.ingsfw.JavaBeats.model.profile.Artist}.
 *
 * @author Giorgio Giacomotti
 * @see it.unipv.ingsfw.JavaBeats.model.playable.audio.Song
 * @see it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode
 * @see it.unipv.ingsfw.JavaBeats.model.profile.User
 * @see it.unipv.ingsfw.JavaBeats.model.profile.Artist
 */
public class Playlist extends JBCollection {

  //ATTRIBUTES:
  private ArrayList<JBAudio> trackList;
  private boolean isVisible;


  //CONSTRUCTOR:

  /**
   * Complete constructor to initialize all parameters.
   */
  public Playlist(int id, String name, JBProfile creator, ArrayList<JBAudio> trackList, Blob picture, boolean isVisible) {
    super(id, name, creator, picture);
    this.trackList = trackList;
    this.isVisible = isVisible;
  }

  /**
   * Minimal constructor to initialize strictly necessary parameters.
   */
  public Playlist(int id, String name, JBProfile creator, ArrayList<JBAudio> trackList) {
    this(id, name, creator, trackList, null, true);
  }

  /**
   * Minimal constructor to initialize strictly necessary parameters. Note that a playlist can exist even if the track list is empty.
   */
  public Playlist(int id, String name, JBProfile creator) {
    this(id, name, creator, null, null, true);
  }


  //GETTERS:

  /**
   * Returns the track list of a Playlist as an {@link ArrayList} of {@link JBAudio}.
   *
   * @return playlist clone
   */
  @Override
  public ArrayList<JBAudio> getTrackList() {
    return trackList;
  }

  /**
   * Returns a clone of the playlist as a {@link JBCollection}.
   *
   * @return playlist clone
   */
  @Override
  public JBCollection getCopy() {
    return new Playlist(this.getId(), this.getName(), this.getCreator(), this.trackList, this.getPicture(), this.isVisible);
  }

  public boolean isVisible() {
    return isVisible;
  }


  //SETTERS:

  /**
   * Sets an {@link ArrayList} of {@link JBAudio} as the new playlist trackList.
   *
   * @param trackList new trackList
   */
  @Override
  public void setTrackList(ArrayList<JBAudio> trackList) {
    this.trackList = trackList;
  }

  /**
   * Sets {@link Boolean} as the new visibility.
   *
   * @param visible new visibility
   */
  public void setVisible(boolean visible) {
    isVisible = visible;
  }


  //METHODS:

  /**
   * Override of toString to return a {@link String} with characterizing information.
   */
  @Override
  public String toString() {
    return "PLAYLIST  -  Name: " + this.getName() + ";  Creator Mail: " + this.getCreator().getMail() + ".";
  }

}
