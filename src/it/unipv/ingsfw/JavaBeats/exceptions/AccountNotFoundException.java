package it.unipv.ingsfw.JavaBeats.exceptions;

/**
 * Exception to report that a specific {@link it.unipv.ingsfw.JavaBeats.model.profile.JBProfile} does not exist in the database.
 *
 * @author Giorgio Giacomotti
 * @see Exception
 * @see it.unipv.ingsfw.JavaBeats.model.profile.JBProfile
 */
public class AccountNotFoundException extends Exception {

    //CONSTRUCTOR:

    /**
     * Constructor to create an instance of the custom exception.
     */
    public AccountNotFoundException() {
        super("Account not found. Please register or use an active profile.");
    }

}
