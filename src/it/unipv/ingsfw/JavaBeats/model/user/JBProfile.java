package it.unipv.ingsfw.JavaBeats.model.user;

import javax.sql.rowset.serial.SerialBlob;

public abstract class JBProfile {

    //ATTRIBUTES:
    private String   username, mail, password, name, surname, biography;
    private SerialBlob profilePicture;


    //CONSTRUCTOR:
    protected JBProfile(String username, String mail, String password) {
        this.username = username;
        this.mail = mail;
        this.password = password;
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
    public SerialBlob getProfilePicture() {
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
    public void setProfilePicture(SerialBlob profilePicture) {
        this.profilePicture = profilePicture;
    }
}
