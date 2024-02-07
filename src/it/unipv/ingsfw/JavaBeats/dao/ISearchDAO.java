package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.model.EJBENTITY;
import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import java.util.ArrayList;

/**
 * Data Access Object (DAO) interface for managing operations related to the {@link IJBResearchable} entity in the database.
 * This interface provides methods to search for {@link IJBResearchable} items with a search field.
 *
 * @author Giorgio Giacomotti
 * @see IJBResearchable
 * @see EJBENTITY
 */
public interface ISearchDAO{

  //METHODS:

  /**
   * Retrieves the complete information regarding all {@link IJBResearchable} items that match the search field.
   * Note that it will return a result specific for the user requesting it.
   *
   * @param field         search field as a {@link String}
   * @param activeProfile current active profile
   * @return results of the search organized in an {@link ArrayList} of {@link IJBResearchable}
   */
  ArrayList<IJBResearchable> search(String field, JBProfile activeProfile) throws AccountNotFoundException;

  /**
   * Retrieves the complete information of a specific {@link IJBResearchable} JavaBeats entity that match the search field.
   * Note that it will return a result specific for the user requesting it.
   *
   * @param field         search field as a {@link String}
   * @param activeProfile current active profile
   * @param mode          JavaBeats entity to search for
   * @return results of the search organized in an {@link ArrayList} of {@link IJBResearchable}
   */
  ArrayList<IJBResearchable> search(String field, JBProfile activeProfile, EJBENTITY mode) throws AccountNotFoundException;
}
