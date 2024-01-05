package it.unipv.ingsfw.JavaBeats.model.user;

import java.sql.Blob;

public abstract class JBProfile {

    //ATTRIBUTES:
    private String   username, mail, password, name, surname, biography;
    private Blob profilePicture;


    //CONSTRUCTORS:
    public JBProfile(String username, String mail, String password, String name, String surname, String biography, Blob profilePicture) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.biography = biography;
        this.profilePicture = profilePicture;
    }
    protected JBProfile(String username, String mail, String password) {
        this(username, mail, password, null, null, null, null);
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


    //METHODS:
    @Override
    public String toString() {
        if(this instanceof User)
            return "USER    -  Mail: " + this.getMail() + ";  Username: " + this.getUsername();
        if(this instanceof Artist)
            return "ARTIST  -  Mail: " + this.getMail() + ";  Username: " + this.getUsername();
        else
            return "PROFILE -  Mail: " + this.getMail() + ";  Username: " + this.getUsername();
    }
}
