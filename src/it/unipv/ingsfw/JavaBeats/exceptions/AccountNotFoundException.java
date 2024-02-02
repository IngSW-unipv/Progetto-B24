package it.unipv.ingsfw.JavaBeats.exceptions;


public class AccountNotFoundException extends Exception {

    //CONSTRUCTOR:
    public AccountNotFoundException () {
        super("Account not found. Please register or use an active profile.");
    }

}
