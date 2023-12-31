package it.unipv.ingsfw.JavaBeats.model.profile;

import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;

import java.sql.Blob;
import java.util.ArrayList;

public abstract class JBProfile {

    //ATTRIBUTES:
    private String   username, mail, password, name, surname, biography;
    private Blob profilePicture;
    private ArrayList<JBAudio> listeningHistory;


    //CONSTRUCTORS:
    public JBProfile(String username, String mail, String password, String name, String surname, String biography, Blob profilePicture, ArrayList<JBAudio> listeningHistory) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.biography = biography;
        this.profilePicture = profilePicture;
        this.listeningHistory = listeningHistory;
    }
    protected JBProfile(String username, String mail, String password) {
        this(username, mail, password, null, null, null, null, null);
    }


    //GETTERS:
    public String getUsername() {
        return username;
    }
    public String getMail() {
        return mail;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getBiography() {
        return biography;
    }
    public Blob getProfilePicture() {
        return profilePicture;
    }
    public ArrayList<JBAudio> getListeningHistory() {
        return listeningHistory;
    }


    //SETTERS:
    public void setUsername(String username) {
        this.username = username;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setBiography(String biography) {
        this.biography = biography;
    }
    public void setProfilePicture(Blob profilePicture) {
        this.profilePicture = profilePicture;
    }
    public void setListeningHistory(ArrayList<JBAudio> listeningHistory) {
        this.listeningHistory = listeningHistory;
    }

    //METHODS:
    @Override
    public String toString() {
        if(this instanceof User)
            return "USER      -  Username: " + this.getUsername() + ";  Mail: " + this.getMail() + ".";
        else if(this instanceof Artist)
            return "ARTIST    -  Username: " + this.getUsername() + ";  Mail: " + this.getMail() + ".";
        else return super.toString();
    }
}
