package it.unipv.ingsfw.JavaBeats.model.playable;

import it.unipv.ingsfw.JavaBeats.model.EJBMODE;
import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;
import javax.sql.rowset.serial.SerialBlob;

public abstract class JBCollection implements IJBPlayable{

    //ATTRIBUTES:
    private String id;
    private String name;
    private JBProfile creator;
    private int itemAmount;
    private SerialBlob picture;


    //CONSTRUCTOR:
    public JBCollection(String id, String name, JBProfile creator) {
        this.id = id;
        this.name = name;
        this.creator = creator;
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
    public SerialBlob getPicture() {
        return picture;
    }


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
    public void setPicture(SerialBlob picture) {
        this.picture = picture;
    }


    //METHODS:
    public void addItem(JBProfile activeJBProfile, IJBItem item) {
    }
    public void removeItem(JBProfile activeJBProfile, IJBItem item) {
    }
    public void play(EJBMODE mode) {
    }
    public void play() {
    }

}
