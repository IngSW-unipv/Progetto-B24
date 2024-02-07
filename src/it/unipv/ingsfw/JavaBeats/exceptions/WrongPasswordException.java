package it.unipv.ingsfw.JavaBeats.exceptions;

/**
 * Exception to report that the inserted password is not correct.
 * Is expected to be thrown at log-in.
 *
 * @author Giorgio Giacomotti
 * @see Exception
 */
public class WrongPasswordException extends Exception implements IJBException{

  //CONSTRUCTOR:

  /**
   * Constructor to create an instance of the custom exception.
   */
  public WrongPasswordException(){
    super("Password entered is wrong.");
  }
  @Override
  public String suggestAlternative(){
    return "Password must be at least 8 characters long and contain at least one\nlowercase letter, one uppercase letter, one number.";
  }
}
