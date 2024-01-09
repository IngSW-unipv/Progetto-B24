package it.unipv.ingsfw.JavaBeats.model.playable.audio;

import it.unipv.ingsfw.JavaBeats.model.playable.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;

public abstract class JBAudio {

    //ATTRIBUTES:
    private String id;
    private boolean isFavorite;
    private Metadata metadata;
    private Blob audioFile;
    private int numberOfStreams;

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
    public JBAudio(String id, String title, Artist artist, JBCollection collection, Blob audioFile, Time duration, Date releaseDate, String[] genres, boolean isFavorite, int numberOfStreams) {
        this.id = id;
        metadata = new Metadata(artist, title, collection, duration, releaseDate, genres);
        this.audioFile=audioFile;
        this.isFavorite=isFavorite;
        this.numberOfStreams=numberOfStreams;
    }
    public JBAudio(String id, String title, Artist artist, Blob audioFile) {
        this(id, title, artist, null, audioFile, Time.valueOf("00:00:00"), new Date(System.currentTimeMillis()), null, false, 0);
    }



    //GETTERS:
    public String getId() {
        return id;
    }
    public boolean isFavorite() {
        return isFavorite;
    }
    public int getNumberOfStreams() {
        return numberOfStreams;
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
    public void setFavorite(boolean isFavorite) {
        this.isFavorite = this.isFavorite;
    }
    public void setNumberOfStreams(int numberOfStreams) {
        this.numberOfStreams=numberOfStreams;
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

    @Override
    public String toString() {
        if(this instanceof Song)
            return "SONG      -  Title: " + this.getMetadata().getTitle() + ";  Artist Mail: " + this.getMetadata().getArtist().getMail() + ".";
        else if(this instanceof Episode)
            return "EPISODE   -  Title: " + this.getMetadata().getTitle() + ";  Artist Mail: " + this.getMetadata().getArtist().getMail() + ".";
        else return super.toString();
    }
}
