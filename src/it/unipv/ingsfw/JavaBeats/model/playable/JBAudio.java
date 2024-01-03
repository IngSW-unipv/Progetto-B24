package it.unipv.ingsfw.JavaBeats.model.playable;

import it.unipv.ingsfw.JavaBeats.model.user.Artist;
import java.sql.Date;
import java.sql.Time;

public abstract class JBAudio {

    //ATTRIBUTES:
    private String id;
    private boolean isFavorite;
    private class Metadata {
        private Artist artist;
        private String title;
        private Time duration;
        private Date releaseDate;
        private String[] genres;
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


    //SETTER:
    public void setId(String id) {
        this.id = id;
    }
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }


    //METHODS:
    public void playFX() {

    }

    public void pause(){

    }
}
