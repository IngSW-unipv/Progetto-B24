package it.unipv.ingsfw.JavaBeats.exceptions;

/**
 * Exception to report that the inserted password is not correct.
 * Is expected to be thrown at log-in.
 *
 * @author Giorgio Giacomotti
 * @see Exception
 */
public class WrongPasswordException extends Exception {

    //CONSTRUCTOR:

    /**
     * Constructor to create an instance of the custom exception.
     */
    public WrongPasswordException() {
        super("Password entered is wrong.");
    }

}
