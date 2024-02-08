package it.unipv.ingsfw.JavaBeats.exceptions;

/**
 * Exception to report that the inserted password is not correct or compliant to the regular expression requirements.
 * Is expected to be thrown when choosing a new password.
 *
 * @see Exception
 * @see RegexException
 */
public class InvalidPasswordException extends RegexException {

    //CONSTRUCTOR:

    /**
     * Constructor to create an instance of the custom exception.
     */
    public InvalidPasswordException() {
        super("Invalid Password field.");
    }

    //METHOD:

    @Override
    public String suggestAlternative() {
        return "Password must be at least 8 characters long and contain at least one\nlowercase letter, one uppercase letter, one number.";
    }

}
