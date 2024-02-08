package it.unipv.ingsfw.JavaBeats.exceptions;

/**
 * Exception to report a problem with the system.
 *
 * @see Exception
 * @see IJBException
 */
public class SystemErrorException extends Exception implements IJBException {

  //CONSTRUCTOR:

  /**
   * Constructor to create an instance of the custom exception.
   */
  public SystemErrorException() {
    super("A system error occurred");
  }

  //METHOD:

  @Override
  public String suggestAlternative() {
    return "Please try again";
  }

}
