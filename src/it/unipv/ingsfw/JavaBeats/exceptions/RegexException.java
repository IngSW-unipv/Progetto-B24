package it.unipv.ingsfw.JavaBeats.exceptions;

/**
 * Exception to report that a string is not complying a regular expression requirement.
 * Is expected to be thrown wherever a string is compared to a regex.
 *
 * @see Exception
 * @see IJBException
 */
public abstract class RegexException extends Exception implements IJBException {

  //CONSTRUCTOR:

  /**
   * Constructor to create an instance of the custom exception.
   */
  public RegexException(String s) {
    super(s);
  }

}
