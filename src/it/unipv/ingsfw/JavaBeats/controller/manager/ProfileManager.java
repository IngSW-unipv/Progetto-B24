package it.unipv.ingsfw.JavaBeats.controller.manager;

import it.unipv.ingsfw.JavaBeats.dao.playable.AudioDAO;
import it.unipv.ingsfw.JavaBeats.dao.collection.CollectionDAO;
import it.unipv.ingsfw.JavaBeats.dao.profile.ProfileDAO;
import it.unipv.ingsfw.JavaBeats.exceptions.UsernameAlreadyTakenException;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.model.profile.User;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class ProfileManager {

    //Attributi

    private static JBProfile activeProfile;


    //Getters and Setters

    public static JBProfile getActiveProfile() {
        return activeProfile;
    }


    //Metodi


    //Login
    //Propagates exception from dao
    public JBProfile login(JBProfile profile) throws IllegalArgumentException {
        ProfileDAO p = new ProfileDAO();
        activeProfile = p.get(profile);
        if (!profile.getPassword().equals(activeProfile.getPassword())) {
            throw new IllegalArgumentException();
        }//end-if
        return activeProfile;
    }

    //Registration
    //Propagates exception from dao
    public JBProfile registration(JBProfile profile) {
        ProfileDAO p = new ProfileDAO();
        /* Default profile image when inserting */
        try {
            BufferedImage bufferedImage = ImageIO.read(new File("src/it/unipv/ingsfw/JavaBeats/view/resources/icons/DefaultUser.png"));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            byte[] image = byteArrayOutputStream.toByteArray();
            profile.setProfilePicture(new SerialBlob(image));
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
        profile.setBiography("");

        p.insert(profile);
        activeProfile = p.get(profile);
        return activeProfile;
    }


    public JBProfile switchProfileType(JBProfile jbProfile) throws ClassCastException {
        ProfileDAO profileDAO = new ProfileDAO();
        AudioDAO audioDAO = new AudioDAO();
        CollectionDAO collectionDAO = new CollectionDAO();
        ArrayList<JBCollection> oldProfilePlaylists = collectionDAO.selectPlaylistsByProfile(jbProfile);

        /* If the profile passed is a user then is switched to an artist and vice-versa */
        try {
            User user = (User) jbProfile;
            Artist artist = new Artist(user.getUsername(), user.getMail(), user.getPassword(), user.getName(), user.getSurname(), user.getBiography(), user.getProfilePicture(), 0, user.getListeningHistory(), user.getFavorites());
            profileDAO.remove(user);
            profileDAO.insert(artist);

            audioDAO.updateIsFavorite(artist);
            for (JBAudio jbAudio : artist.getListeningHistory()) {
                audioDAO.addToListeningHistory(jbAudio, artist);
            }//end-foreach

            activeProfile = profileDAO.get(artist);
        } catch (ClassCastException c) {
            Artist artist = (Artist) jbProfile;
            User user = new User(artist.getUsername(), artist.getMail(), artist.getPassword(), artist.getName(), artist.getSurname(), artist.getBiography(), artist.getProfilePicture(), true, new Time(0), artist.getListeningHistory(), artist.getFavorites());
            profileDAO.remove(artist);
            profileDAO.insert(user);

            audioDAO.updateIsFavorite(user);
            for (JBAudio jbAudio : user.getListeningHistory()) {
                audioDAO.addToListeningHistory(jbAudio, user);
            }//end-foreach

            activeProfile = profileDAO.get(user);
        }//end-try

        /* Passing the playlist from old profile to new profile when switching */
        for (JBCollection p : oldProfilePlaylists) {
            p.setCreator(activeProfile);
            collectionDAO.insert(p);
        }//end-foreach

        return activeProfile;
    }

    public void edit(JBProfile newProfile) {

        ProfileDAO p = new ProfileDAO();
        p.update(newProfile);

    }

    public void checkIfUsernameAlreadyExists(String newUsername) throws UsernameAlreadyTakenException {
        JBProfile tmpProfile = new Artist(newUsername, null, null);
        ProfileDAO p = new ProfileDAO();
        if (p.get(tmpProfile) != null) {
            UsernameAlreadyTakenException e = new UsernameAlreadyTakenException();
            throw e;
        }
    }


}
