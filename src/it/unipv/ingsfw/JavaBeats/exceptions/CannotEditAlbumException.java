package it.unipv.ingsfw.JavaBeats.exceptions;

public class CannotEditAlbumException extends Exception {

    //CONSTRUCTORS:
    public CannotEditAlbumException() {
        super("Existing album cannot be edited. Please delete it and insert it from scratch.");
    }

}
