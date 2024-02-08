package it.unipv.ingsfw.JavaBeats.dao.collection;

import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.model.collection.*;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import java.util.ArrayList;

/**
 * Data Access Object (DAO) interface for managing operations related to the {@link JBCollection} entity in the database.
 * This interface provides methods to perform Insert, Remove, Update, Get operations on {@link Album}, {@link Playlist} and {@link Podcast} records.
 * It also allows to select {@link JBCollection} by their creators.
 *
 * @see JBCollection
 * @see Album
 * @see Playlist
 * @see Podcast
 */
public interface ICollectionDAO{

  //METHODS:

  /**
   * Inserts a new {@link JBCollection} record in the database.
   *
   * @param collection collection to add
   * @throws AccountNotFoundException
   */
  JBCollection insert(JBCollection collection) throws AccountNotFoundException;

  /**
   * Removes a {@link JBCollection} record from the database.
   *
   * @param collection collection to delete
   */
  void remove(JBCollection collection);

  /**
   * Updates a {@link JBCollection} record in the database.
   * Note that an {@link Album} cannot be updated.
   *
   * @param collection collection to update
   * @throws AccountNotFoundException
   */
  void update(JBCollection collection) throws AccountNotFoundException;

  /**
   * Retrieves the complete information regarding a specific {@link JBCollection} record from the database.
   *
   * @param collection collection to get
   * @return collection with complete and updated info
   * @throws AccountNotFoundException
   */
  JBCollection get(JBCollection collection) throws AccountNotFoundException;

  /**
   * Retrieves the complete information regarding a specific {@link Playlist} record from the database.
   *
   * @param collection playlist to get
   * @return collection with complete and updated info
   * @throws AccountNotFoundException
   */
  Playlist getPlaylist(JBCollection collection) throws AccountNotFoundException;

  /**
   * Retrieves the complete information regarding a specific {@link Album} record from the database.
   *
   * @param collection album to get
   * @return collection with complete and updated info
   */
  Album getAlbum(JBCollection collection);

  /**
   * Retrieves the complete information regarding a specific {@link Podcast} record from the database.
   *
   * @param collection podcast to get
   * @return collection with complete and updated info
   */
  Podcast getPodcast(JBCollection collection);

  /**
   * Retrieves the complete information regarding the Favorites Playlist of a specific {@link JBProfile} from the database.
   *
   * @param activeProfile current active profile
   * @return collection with complete and updated info
   * @throws AccountNotFoundException
   */
  Playlist getFavorites(JBProfile activeProfile) throws AccountNotFoundException;

  /**
   * Retrieves all {@link Playlist} created by a specific {@link JBProfile} from the database.
   *
   * @param profile playlist creator
   * @return playlists as an {@link ArrayList} of {@link JBCollection}
   * @throws AccountNotFoundException
   */
  ArrayList<JBCollection> selectPlaylistsByProfile(JBProfile profile) throws AccountNotFoundException;

  /**
   * Retrieves all {@link Album} created by a specific {@link Artist} from the database.
   *
   * @param artist album creator
   * @return albums as an {@link ArrayList} of {@link JBCollection}
   * @throws AccountNotFoundException
   */
  ArrayList<JBCollection> selectAlbumsByArtist(Artist artist) throws AccountNotFoundException;

  /**
   * Retrieves all {@link Podcast} created by a specific {@link Artist} from the database.
   *
   * @param artist podcast creator
   * @return podcasts as an {@link ArrayList} of {@link JBCollection}
   * @throws AccountNotFoundException
   */
  ArrayList<JBCollection> selectPodcastsByArtist(Artist artist) throws AccountNotFoundException;

}
