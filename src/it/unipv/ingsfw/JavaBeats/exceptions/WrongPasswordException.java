package it.unipv.ingsfw.JavaBeats.exceptions;


public class WrongPasswordException extends Exception {

    //CONSTRUCTOR:
    public WrongPasswordException() {
        super("Password entered is wrong.");
    }

}
