package it.unipv.ingsfw.JavaBeats.exceptions;

/**
 * Exception to report that an {@link it.unipv.ingsfw.JavaBeats.model.collection.Album} cannot be edited once is created.
 * To modify an {@link it.unipv.ingsfw.JavaBeats.model.collection.Album} is necessary to delete it and re-create it.
 * {@link it.unipv.ingsfw.JavaBeats.model.collection.Playlist} and {@link it.unipv.ingsfw.JavaBeats.model.collection.Podcast} are the only collections that can be edited once they are created.
 *
 * @author Giorgio Giacomotti
 * @see Exception
 * @see it.unipv.ingsfw.JavaBeats.model.collection.Album
 * @see it.unipv.ingsfw.JavaBeats.model.collection.Playlist
 * @see it.unipv.ingsfw.JavaBeats.model.collection.Podcast
 */
public class CannotEditAlbumException extends Exception {

    //CONSTRUCTORS:

    /**
     * Constructor to create an instance of the custom exception.
     */
    public CannotEditAlbumException() {
        super("Existing album cannot be edited. Please delete it and insert it from scratch.");
    }

}
