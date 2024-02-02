package it.unipv.ingsfw.JavaBeats.exceptions;

import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

public class AccountAlreadyExistsException extends Exception {

    //CONSTRUCTORS:
    public AccountAlreadyExistsException () {
        super("Inserted mail is already taken.");
    }

    public AccountAlreadyExistsException (JBProfile profile) {
        super(profile.getMail() + " is already taken.");
    }
}
