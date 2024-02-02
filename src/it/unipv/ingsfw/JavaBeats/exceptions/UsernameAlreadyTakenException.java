package it.unipv.ingsfw.JavaBeats.exceptions;

import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import java.util.Random;

public class UsernameAlreadyTakenException extends Exception {

    private String username;

    //CONSTRUCTORS:
    public UsernameAlreadyTakenException() {
        super("Inserted username is already taken.");
    }

    public UsernameAlreadyTakenException(JBProfile profile) {
        super(profile.getUsername() + " is already taken.");
        this.username = profile.getUsername();
    }


    //METHODS:
    public String suggestAlternativeUsername() {
        Random random = new Random();
        String result;

        if (username != null) {
            result = "Try with: " + username + random.nextInt(100) + " or " + username + "_" + random.nextInt(100);
        } else {
            try {
                throw new IllegalArgumentException("Original username is necessary to suggest an alternative username.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.toString());
                result = null;
            }
        }

        return result;
    }

}
