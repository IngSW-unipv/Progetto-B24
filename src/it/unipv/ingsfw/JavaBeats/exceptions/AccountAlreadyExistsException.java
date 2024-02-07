package it.unipv.ingsfw.JavaBeats.exceptions;

import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

/**
 * Exception to report that a specific {@link JBProfile} already exists in the database.
 *
 * @author Giorgio Giacomotti
 * @see Exception
 * @see JBProfile
 */
public class AccountAlreadyExistsException extends Exception implements IJBException{

  //CONSTRUCTORS:

  /**
   * Constructor to create an instance of the custom exception.
   */
  public AccountAlreadyExistsException(){
    super("You already have an account");
  }

  /**
   * Constructor to create an instance of the custom exception with a specific message.
   *
   * @param profile profile that already exists
   */
  public AccountAlreadyExistsException(JBProfile profile){
    super(profile.getMail()+" is already taken.");
  }
  @Override
  public String suggestAlternative(){
    return "Please proceed with login.";
  }
}
