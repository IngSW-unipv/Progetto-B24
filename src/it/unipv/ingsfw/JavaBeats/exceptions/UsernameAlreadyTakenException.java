package it.unipv.ingsfw.JavaBeats.exceptions;

import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import java.util.Random;

/**
 * Exception to report that a {@link it.unipv.ingsfw.JavaBeats.model.profile.JBProfile} with the desired username already exists in the database.
 * Is expected to be thrown when registering a new profile or editing the username.
 *
 * @author Giorgio Giacomotti
 * @see Exception
 * @see it.unipv.ingsfw.JavaBeats.model.profile.JBProfile
 */
public class UsernameAlreadyTakenException extends Exception {

    //CONSTRUCTORS:

    /**
     * Constructor to create an instance of the custom exception.
     */
    public UsernameAlreadyTakenException() {
        super("Inserted username is already taken.");
    }

    /**
     * Constructor to create an instance of the custom exception with a specific message.
     *
     * @param takenUsername username that is already taken as a {@link String}
     */
    public UsernameAlreadyTakenException(JBProfile jbProfile) {
        super(jbProfile.getUsername() + " is already taken.");
    }


    //METHODS:

    /**
     * Method that returns an alternative username created adding a two digit random number at the unavailable username.
     * Note that it will not check if the suggested username is available.
     *
     * @param takenUsername username that is already taken as a {@link String}
     * @return suggested alternative username as a{@link String}
     */
    public String suggestAlternativeUsername(JBProfile jbProfile) {
        Random random = new Random();
        return "Try with: " + jbProfile.getUsername() + random.nextInt(100) + " or " + jbProfile.getUsername() + "_" + random.nextInt(100);
    }

}
