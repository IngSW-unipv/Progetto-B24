package it.unipv.ingsfw.JavaBeats.exceptions;

/**
 * Interface that every custom JavaBeats exception must implement.
 */
public interface IJBException{

  /**
   * Suggests a possible solution for the problem.
   */
  String suggestAlternative();

  /**
   * Returns a message with more information regarding the exception.
   */
  String getMessage();
}
