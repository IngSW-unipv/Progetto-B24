package it.unipv.ingsfw.JavaBeats.exceptions;

import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;

/**
 * Exception to report that a JavaBeats entity is not correctly recognized or is used improperly.
 *
 * @see Exception
 * @see IJBException
 * @see IJBResearchable
 * @see it.unipv.ingsfw.JavaBeats.model.EJBENTITY
 */
public class InvalidJBEntityException extends Exception implements IJBException {

  //CONSTRUCTOR:

  /**
   * Constructor to create an instance of the custom exception.
   */
  public InvalidJBEntityException() {
    super("Invalid or not recognized JB object type.");
  }


  //METHODS:

  /**
   * Method that uses Java Reflection to get the class name of the invalid entity.
   *
   * @param JBEntity the invalid/unrecognized JavaBeats entity
   * @return class name as a {@link String}
   */
  private String getClassName(IJBResearchable JBEntity) {
    return JBEntity.getClass().getName();
  }

  @Override
  public String suggestAlternative() {
    return "Please insert something different.";
  }

}
