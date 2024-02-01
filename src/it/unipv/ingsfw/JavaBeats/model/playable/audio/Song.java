package it.unipv.ingsfw.JavaBeats.model.playable.audio;

import it.unipv.ingsfw.JavaBeats.controller.handler.PlayerHandler;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import javafx.scene.media.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

public class Song extends JBAudio {

    //CONSTRUCTORS:
    public Song(int id, String title, Artist artist, JBCollection collection, Blob audioFile, double duration, Date releaseDate, String[] genres, boolean isFavourite, int numbersOfStreams) {
        super(id, title, artist, collection, audioFile, duration, releaseDate, genres, isFavourite, numbersOfStreams);
    }

    public Song(int id, String title, Artist artist, Blob audioFile) {
        this(id, title, artist, null, audioFile, 00.00, new Date(System.currentTimeMillis()), null, false, 0);
    }


    //METHODS:
    @Override
    public String toString() {
        return "SONG      -  Title: " + this.getMetadata().getTitle() + ";  Artist Mail: " + this.getMetadata().getArtist().getMail() + ".";
    }

    //PlayFX

    @Override
    public void playFX() {
        try {
            /* Creating temporary file so that It can be played. It's removed once on exit */
            File f = new File("tmp");
            f.deleteOnExit();
            FileUtils.writeByteArrayToFile(f, this.getAudioFileBlob().getBinaryStream().readAllBytes());

            Media song = new Media(f.toURI().toURL().toString());
            super.mediaPlayer = new MediaPlayer(song);
            mediaPlayer.play();
            mediaPlayer.setOnEndOfMedia(new PlayerHandler());
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
