package it.unipv.ingsfw.JavaBeats.dao.playable;

import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.*;
import it.unipv.ingsfw.JavaBeats.model.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.collection.Podcast;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import java.util.ArrayList;

/**
 * Data Access Object (DAO) interface for managing operations related to the {@link JBAudio} entity in the database.
 * This interface provides methods to perform Insert, Remove, Get operations on {@link Song} and {@link Episode} records.
 * It also allows to select {@link JBAudio} by their {@link it.unipv.ingsfw.JavaBeats.model.collection.JBCollection} and to perform special operations for the Favorites Playlist and the User Listening History.
 *
 * @see JBAudio
 * @see Song
 * @see Episode
 * @see it.unipv.ingsfw.JavaBeats.model.collection.JBCollection
 */
public interface IAudioDAO{

  //METHODS:

  /**
   * Inserts a new {@link JBAudio} record in the database.
   *
   * @param audio audio to add
   * @throws AccountNotFoundException
   */
  void insert(JBAudio audio) throws AccountNotFoundException;

  /**
   * Removes a {@link JBAudio} record from the database.
   *
   * @param audio audio to delete
   */
  void remove(JBAudio audio);

  /**
   * Retrieves the complete information regarding a specific {@link JBAudio} record from the database.
   * Note that it will return a result specific for the user requesting it.
   *
   * @param audio         audio to get
   * @param activeProfile current active profile
   * @return audio with complete and updated info
   * @throws AccountNotFoundException if the active profile is not found in the database
   */
  JBAudio get(JBAudio audio, JBProfile activeProfile) throws AccountNotFoundException;

  /**
   * Retrieves the complete information regarding a specific {@link Song} record from the database.
   * Note that it will return a result specific for the user requesting it.
   *
   * @param audio         song to get
   * @param activeProfile current active profile
   * @return audio with complete and updated info
   * @throws AccountNotFoundException if the active profile is not found in the database
   */
  Song getSong(JBAudio audio, JBProfile activeProfile) throws AccountNotFoundException;

  /**
   * Retrieves the complete information regarding a specific {@link Episode} record from the database.
   * Note that it will return a result specific for the user requesting it.
   *
   * @param audio         episode to get
   * @param activeProfile current active profile
   * @return audio with complete and updated info
   * @throws AccountNotFoundException if the active profile is not found in the database
   */
  Episode getEpisode(JBAudio audio, JBProfile activeProfile) throws AccountNotFoundException;

  /**
   * Updates the Favorites playlist associated with a {@link JBProfile}.
   * Note that it will behave differently based on the user requesting it.
   *
   * @param activeProfile current active profile
   * @throws AccountNotFoundException if the active profile is not found in the database
   */
  void updateIsFavorite(JBProfile activeProfile) throws AccountNotFoundException;

  /**
   * Marks a specific {@link JBAudio} as listened in the database.
   * The database will store the audio id, the mail of the listener and the timestamps at which the method is executed.
   * Note that it will behave differently based on the user requesting it.
   *
   * @param audio         audio to add to listening history
   * @param activeProfile listener
   */
  void addToListeningHistory(JBAudio audio, JBProfile activeProfile);

  /**
   * Retrieves all {@link JBAudio} from a specific {@link Playlist} from the database.
   * Note that it will return a different result based on the user requesting it.
   *
   * @param playlist      collection from which to recover the audios
   * @param activeProfile current active profile
   * @return audios as an {@link ArrayList} of {@link JBAudio}
   * @throws AccountNotFoundException if the active profile is not found in the database
   */
  ArrayList<JBAudio> selectByPlaylist(Playlist playlist, JBProfile activeProfile) throws AccountNotFoundException;

  /**
   * Retrieves all {@link Song} from a specific {@link Album} from the database.
   * Note that it will return a different result based on the user requesting it.
   *
   * @param album         collection from which to recover the audios
   * @param activeProfile current active profile
   * @return audios as an {@link ArrayList} of {@link Song}
   * @throws AccountNotFoundException if the active profile is not found in the database
   */
  ArrayList<Song> selectByAlbum(Album album, JBProfile activeProfile) throws AccountNotFoundException;

  /**
   * Retrieves all {@link Episode} from a specific {@link Podcast} from the database.
   * Note that it will return a different result based on the user requesting it.
   *
   * @param podcast       collection from which to recover the audios
   * @param activeProfile current active profile
   * @return audios as an {@link ArrayList} of {@link Episode}
   * @throws AccountNotFoundException if the active profile is not found in the database
   */
  ArrayList<Episode> selectByPodcast(Podcast podcast, JBProfile activeProfile) throws AccountNotFoundException;

  /**
   * Retrieves all {@link JBAudio} from the favorites playlist.
   * Note that it will return a different result based on the user requesting it.
   *
   * @param activeProfile current active profile
   * @return audios as an {@link ArrayList} of {@link JBAudio}
   * @throws AccountNotFoundException if the active profile is not found in the database
   */
  ArrayList<JBAudio> selectFavorites(JBProfile activeProfile) throws AccountNotFoundException;

}
