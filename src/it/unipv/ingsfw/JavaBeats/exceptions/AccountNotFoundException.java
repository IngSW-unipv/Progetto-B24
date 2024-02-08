package it.unipv.ingsfw.JavaBeats.exceptions;

/**
 * Exception to report that a specific {@link it.unipv.ingsfw.JavaBeats.model.profile.JBProfile} does not exist in the database.
 *
 * @see Exception
 * @see IJBException
 * @see it.unipv.ingsfw.JavaBeats.model.profile.JBProfile
 */
public class AccountNotFoundException extends Exception implements IJBException {

  //CONSTRUCTOR:

  /**
   * Constructor to create an instance of the custom exception.
   */
  public AccountNotFoundException() {
    super("Account not found.");
  }

  //METHOD:

  @Override
  public String suggestAlternative() {
    return "Please register or use an active profile.";
  }

}
