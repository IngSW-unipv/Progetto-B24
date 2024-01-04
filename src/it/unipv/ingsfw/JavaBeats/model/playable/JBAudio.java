package it.unipv.ingsfw.JavaBeats.model.playable;

import it.unipv.ingsfw.JavaBeats.model.user.Artist;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;

public abstract class JBAudio {

    //ATTRIBUTES:
    private String id;
    private boolean isFavorite = false;
    private Metadata metadata;
    private Blob audioFile;

    public class Metadata {

        //NESTED CLASS ATTRIBUTES:
        private Artist artist;
        private String title;
        private JBCollection collection;
        private Time duration;
        private Date releaseDate;
        private String[] genres;

        //NESTED CLASS CONSTRUCTOR:
        public Metadata(Artist artist, String title, JBCollection collection, Time duration, Date releaseDate, String[] genres) {
            this.artist = artist;
            this.title = title;
            this.collection = collection;
            this.duration = duration;
            this.releaseDate = releaseDate;
            this.genres = genres;
        }

        //NESTED CLASS GETTERS & SETTERS:
        public Artist getArtist() {
            return artist;
        }
        public void setArtist(Artist artist) {
            this.artist = artist;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public Time getDuration() {
            return duration;
        }
        public void setDuration(Time duration) {
            this.duration = duration;
        }
        public Date getReleaseDate() {
            return releaseDate;
        }
        public void setReleaseDate(Date releaseDate) {
            this.releaseDate = releaseDate;
        }
        public String[] getGenres() {
            return genres;
        }
        public void setGenres(String[] genres) {
            this.genres = genres;
        }
        public JBCollection getCollection() {
            return collection;
        }
        public void setCollection(JBCollection collection) {
            this.collection = collection;
        }
    }



    //CONSTRUCTORS:
    public JBAudio(String id, String title, Artist artist, JBCollection collection, Blob audioFile, Time duration, Date releaseDate, String[] genres, boolean isFavorite) {
        this.id = id;
        metadata = new Metadata(artist, title, collection, duration, releaseDate, genres);
        this.audioFile=audioFile;
        this.isFavorite=isFavorite;
    }
    public JBAudio(String id, String title, Artist artist, Blob audioFile) {
        this(id, title, artist, null, audioFile, null, null, null, false);
    }



    //GETTERS:
    public String getId() {
        return id;
    }
    public boolean isFavorite() {
        return isFavorite;
    }
    public Metadata getMetadata() {
        return metadata;
    }
    public Blob getAudioFileBlob() {
        return audioFile;
    }



    //SETTER:
    public void setId(String id) {
        this.id = id;
    }
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
    public void setAudioFileBlob(Blob audioFile) {
        this.audioFile = audioFile;
    }



    //METHODS:
    public void playFX() {

    }

    public void pause(){

    }
}
