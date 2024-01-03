package it.unipv.ingsfw.JavaBeats.model.playable;

import it.unipv.ingsfw.JavaBeats.model.user.Artist;
import java.sql.Date;
import java.sql.Time;

public abstract class JBAudio {
    //ATTRIBUTES:
    private String id;
    private boolean isFavorite;
    private Metadata metadata;
    public class Metadata {
        private Artist artist;
        private String title;
        private JBCollection collection;
        private Time duration;
        private Date releaseDate;
        private String[] genres;

        public Metadata(Artist artist, String title, JBCollection collection, Time duration, Date releaseDate, String[] genres) {
            this.artist = artist;
            this.title = title;
            this.collection = collection;
            this.duration = duration;
            this.releaseDate = releaseDate;
            this.genres = genres;
        }

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


    //CONSTRUCTOR:
    protected JBAudio(String id) {
        this.id = id;
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

    //METHODS:
    public void playFX() {

    }

    public void pause(){

    }
}
