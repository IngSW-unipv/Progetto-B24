package it.unipv.ingsfw.JavaBeats.dao.profile;

import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.model.profile.*;

/**
 * Data Access Object (DAO) interface for managing operations related to the {@link JBProfile} entity in the database.
 * This interface provides methods to perform Insert, Remove, Update, Get operations on {@link User} and {@link Artist} records.
 *
 * @see JBProfile
 * @see User
 * @see Artist
 */
public interface IProfileDAO{

  //METHODS:

  /**
   * Inserts a new {@link JBProfile} record in the database.
   *
   * @param profile profile to add
   */
  void insert(JBProfile profile);

  /**
   * Removes a {@link JBProfile} record from the database.
   *
   * @param profile profile to delete
   */
  void remove(JBProfile profile);

  /**
   * Updates a {@link JBProfile} record in the database.
   *
   * @param profile profile to update
   */
  void update(JBProfile profile) throws AccountNotFoundException;

  /**
   * Retrieves the complete information regarding a specific {@link JBProfile} record from the database.
   *
   * @param profile profile to get
   * @return profile with complete and updated info
   */
  JBProfile get(JBProfile profile) throws AccountNotFoundException;

  /**
   * Retrieves the complete information regarding a specific {@link Artist} record from the database.
   *
   * @param profile user to get
   * @return profile with complete and updated info
   */
  Artist getArtist(JBProfile profile);

  /**
   * Retrieves the complete information regarding a specific {@link User} record from the database.
   *
   * @param profile user to get
   * @return profile with complete and updated info
   */
  User getUser(JBProfile profile);

}
