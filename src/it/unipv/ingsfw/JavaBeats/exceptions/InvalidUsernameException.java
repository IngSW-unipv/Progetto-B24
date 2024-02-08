package it.unipv.ingsfw.JavaBeats.exceptions;

/**
 * Exception to report that the inserted username is not correct or compliant to the regular expression requirements.
 * Is expected to be thrown when choosing a new username.
 *
 * @see Exception
 * @see RegexException
 */
public class InvalidUsernameException extends RegexException {

  //CONSTRUCTOR:

  /**
   * Constructor to create an instance of the custom exception.
   */
  public InvalidUsernameException() {
    super("Inserter invalid username field");
  }

  //METHOD:

  @Override
  public String suggestAlternative() {
    return "Allowed characters in Username are a-z, A-Z, 0-9, ., _, +, -.";
  }

}
