package it.unipv.ingsfw.JavaBeats.controller.manager;

import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.dao.playable.AudioDAO;
import it.unipv.ingsfw.JavaBeats.dao.collection.CollectionDAO;
import it.unipv.ingsfw.JavaBeats.dao.profile.ProfileDAO;
import it.unipv.ingsfw.JavaBeats.exceptions.*;
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
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ProfileManager {

    //Attributi

    private static JBProfile activeProfile;
    private static final String nameRegex = "^[A-Z][a-zA-Z0-9_-]{1,50}$";
    private static final String usernameRegex = "^[a-zA-Z0-9._+-]{1,30}$";
    private static final String passwordRegex = ("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,100}$");  //at least: 8 char, one uppercase, one lowercase, one number


    //Getters and Setters

    public static JBProfile getActiveProfile() {
        return activeProfile;
    }


    //Metodi


    //Login
    //Propagates exception from dao
    public JBProfile login(JBProfile profile) throws WrongPasswordException, AccountNotFoundException {
        ProfileDAO p = new ProfileDAO();
        activeProfile = p.get(profile);
        if (!profile.getPassword().equals(activeProfile.getPassword())) {
            throw new WrongPasswordException();
        }//end-if
        return activeProfile;
    }

    //Registration
    //Propagates exception from dao
    public JBProfile registration(JBProfile profile) throws AccountNotFoundException {
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


    public JBProfile switchProfileType(JBProfile jbProfile) throws AccountNotFoundException {
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
            User user = new User(artist.getUsername(), artist.getMail(), artist.getPassword(), artist.getName(), artist.getSurname(), artist.getBiography(), artist.getProfilePicture(), true, 0, artist.getListeningHistory(), artist.getFavorites());

            profileDAO.remove(artist);

            for (JBCollection jbCollection : CollectionManagerFactory.getInstance().getCollectionManager().getAlbums(artist)) {
                CollectionManagerFactory.getInstance().getCollectionManager().removeCollection(jbCollection);
            }//end-foreach
            for (JBCollection jbCollection : CollectionManagerFactory.getInstance().getCollectionManager().getPodcasts(artist)) {
                CollectionManagerFactory.getInstance().getCollectionManager().removeCollection(jbCollection);
            }//end-foreach

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

    public void edit(JBProfile newProfile) throws AccountNotFoundException {

        ProfileDAO p = new ProfileDAO();
        p.update(newProfile);

        activeProfile = p.get(newProfile);
    }

    public void checkIfUsernameAlreadyExists(JBProfile jbProfile) throws UsernameAlreadyTakenException {

        ProfileDAO p = new ProfileDAO();
        try {
            p.get(jbProfile);
            UsernameAlreadyTakenException e = new UsernameAlreadyTakenException(jbProfile);
            throw e;
        } catch (AccountNotFoundException ignored) {

        }//end-try
    }

    public void checkIfAccountAlreadyExists(JBProfile jbProfile) throws AccountAlreadyExistsException {

        ProfileDAO p = new ProfileDAO();
        try {
            p.get(jbProfile);
            throw new AccountAlreadyExistsException();
        } catch (AccountNotFoundException ignored) {

        }//end-try
    }

    public void checkRegex(JBProfile jbProfile) throws RegexException {
        if (!(Pattern.matches(nameRegex, jbProfile.getName()) || Pattern.matches(nameRegex, jbProfile.getSurname()))) {
            throw new InvalidNameException();
        } else if (!(Pattern.matches(usernameRegex, jbProfile.getUsername()))) {
            throw new InvalidUsernameException();
        } else if (!(Pattern.matches(passwordRegex, jbProfile.getPassword()))) {
            throw new InvalidPasswordException();
        }
    }
}
