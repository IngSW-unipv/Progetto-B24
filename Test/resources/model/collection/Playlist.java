package it.unipv.ingsfw.JavaBeats.model.collection;

import it.unipv.ingsfw.JavaBeats.exceptions.SystemErrorException;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.model.profile.User;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class representing a Playlist containing {@link Song} and {@link Episode} and created by a {@link User} or an {@link Artist}.
 *
 * @author Giorgio Giacomotti
 * @see Song
 * @see Episode
 * @see User
 * @see Artist
 */
public class Playlist extends JBCollection {

    //ATTRIBUTES:
    private ArrayList<JBAudio> trackList;
    private boolean isVisible;


    //CONSTRUCTOR:

    /**
     * Complete constructor to initialize all parameters.
     */
    public Playlist(int id, String name, JBProfile creator, ArrayList<JBAudio> trackList, Blob picture, boolean isVisible) {
        super(id, name, creator, picture);
        this.trackList = trackList;
        this.isVisible = isVisible;
    }

    /**
     * Minimal constructor to initialize strictly necessary parameters.
     */
    public Playlist(int id, String name, JBProfile creator, ArrayList<JBAudio> trackList) {
        this(id, name, creator, trackList, null, true);
    }

    /**
     * Minimal constructor to initialize strictly necessary parameters. Note that a playlist can exist even if the track list is empty.
     */
    public Playlist(int id, String name, JBProfile creator) {
        this(id, name, creator, null, null, true);
    }


    //GETTERS:

    /**
     * Returns the track list of a Playlist as an {@link ArrayList} of {@link JBAudio}.
     *
     * @return playlist clone
     */
    @Override
    public ArrayList<JBAudio> getTrackList() {
        return trackList;
    }

    /**
     * Returns a clone of the playlist as a {@link JBCollection}.
     *
     * @return playlist clone
     */
    @Override
    public JBCollection getCopy() throws SystemErrorException {
        try {
            return new Playlist(this.getId(), this.getName(), this.getCreator(), new ArrayList<>(this.trackList), new SerialBlob(getPicture()), this.isVisible);
        } catch (SQLException s) {
            throw new SystemErrorException();
        }//end-try
    }

    public boolean isVisible() {
        return isVisible;
    }


    //SETTERS:

    /**
     * Sets an {@link ArrayList} of {@link JBAudio} as the new playlist trackList.
     *
     * @param trackList new trackList
     */
    @Override
    public void setTrackList(ArrayList<JBAudio> trackList) {
        this.trackList = trackList;
    }

    /**
     * Sets {@link Boolean} as the new visibility.
     *
     * @param visible new visibility
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }


    //METHODS:

    /**
     * Override of toString to return a {@link String} with characterizing information.
     */
    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(Object obj) {
        try {
            Playlist playlist = (Playlist) obj;
            if (playlist != null && super.getId() == playlist.getId() && super.getName().equals(playlist.getName()) && super.getCreator().equals(playlist.getCreator()) && Arrays.equals(super.getPicture().getBinaryStream().readAllBytes(), playlist.getPicture().getBinaryStream().readAllBytes()) && this.trackList.equals(playlist.getTrackList()) && this.isVisible == playlist.isVisible) {
                return true;
            } else {
                return false;
            }//end-if
        } catch (ClassCastException | SQLException | IOException c) {
            return false;
        }//end-try
    }
}
