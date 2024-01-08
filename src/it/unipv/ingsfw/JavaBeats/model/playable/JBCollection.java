package it.unipv.ingsfw.JavaBeats.model.playable;

import it.unipv.ingsfw.JavaBeats.model.EJBMODE;
import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;
import java.sql.Blob;
import java.util.ArrayList;

public abstract class JBCollection implements IJBPlayable{

    //ATTRIBUTES:
    private String id;
    private String name;
    private JBProfile creator;
    private int itemAmount;
    private Blob picture;


    //CONSTRUCTORS:
    protected JBCollection(String id, String name, JBProfile creator, Blob picture) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.picture = picture;
    }
    protected JBCollection(String id, String name, JBProfile creator) {
        this(id, name, creator, null);
    }



    //GETTERS:
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public JBProfile getCreator() {
        return creator;
    }
    public int getItemAmount() {
        return itemAmount;
    }
    public Blob getPicture() {
        return picture;
    }
    public abstract ArrayList getTrackList();
    public abstract JBCollection getCopy(JBCollection collection);


    //SETTERS:
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCreator(JBProfile creator) {
        this.creator = creator;
    }
    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }
    public void setPicture(Blob picture) {
        this.picture = picture;
    }
    public abstract void setTrackList(ArrayList trackList);


    //METHODS:
    public void addItem(JBProfile activeJBProfile, IJBItem item) {
    }
    public void removeItem(JBProfile activeJBProfile, IJBItem item) {
    }
    public void play(EJBMODE mode) {
    }
    public void play() {
    }

    @Override
    public String toString() {
        if(this instanceof Playlist)
            return "PLAYLIST  -  Name: " + this.getName() + ";  Creator: " + this.getCreator() + ".";
        else if(this instanceof Album)
            return "ALBUM     -  Name: " + this.getName() + ";  Creator: " + this.getCreator() + ".";
        else if(this instanceof Podcast)
            return "PODCAST   -  Name: " + this.getName() + ";  Creator: " + this.getCreator() + ".";
        else return super.toString();
    }

}
