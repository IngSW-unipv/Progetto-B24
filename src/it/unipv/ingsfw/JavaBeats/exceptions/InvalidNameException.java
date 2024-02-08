package it.unipv.ingsfw.JavaBeats.exceptions;

/**
 * Exception to report that the inserted name is not correct or compliant to the regular expression requirements.
 * Is expected to be thrown when choosing a new name.
 *
 * @see Exception
 * @see RegexException
 */
public class InvalidNameException extends RegexException {

  //CONSTRUCTOR:

  /**
   * Constructor to create an instance of the custom exception.
   */
  public InvalidNameException() {
    super("Invalid Name or Surname fields.");
  }

  //METHOD:

  @Override
  public String suggestAlternative() {
    return "Name and Surname fields must start with capital letter.";
  }

}
