package it.unipv.ingsfw.JavaBeats.model.profile;

import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.exceptions.InvalidPasswordException;
import it.unipv.ingsfw.JavaBeats.exceptions.InvalidUsernameException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JBProfileRegexTest {

    //Attributes
    private Artist artist;


    @Before
    public void intTest() {
        artist = new Artist("", "", "");


    }

    @Test
    public void testPasswordOne() {

        String[] password = {"Alessia1", "_Ale300502_", "3AleProtti"};

        boolean result = true;

        for (String s : password) {

            artist.setPassword(s);
            try {
                ProfileManagerFactory.getInstance().getProfileManager().checkPasswordRegex(artist);
            } catch (InvalidPasswordException e) {
                result = false;
            }
        }
        assertTrue(result);

    }

    @Test
    public void testPasswordTwo() {

        String[] password = {"ale", "Alessiaaa", "AP3", "33"};

        boolean result = true;

        for (String s : password) {

            artist.setPassword(s);
            try {
                ProfileManagerFactory.getInstance().getProfileManager().checkPasswordRegex(artist);
            } catch (InvalidPasswordException e) {
                result = false;
            }
        }
        assertFalse(result);

    }

    @Test
    public void testPasswordThree() {

        String[] password = {"", "      "};

        boolean result = true;

        for (String s : password) {

            artist.setPassword(s);
            try {
                ProfileManagerFactory.getInstance().getProfileManager().checkPasswordRegex(artist);
            } catch (InvalidPasswordException e) {
                result = false;
            }
        }
        assertFalse(result);

    }

    @Test
    public void testUsernameOne() {

        String[] username = {"giaco", "GIAC0_01", "3giaco.2GIACO.1giaco"};

        boolean result = true;

        for (String s : username) {

            artist.setUsername(s);
            try {
                ProfileManagerFactory.getInstance().getProfileManager().checkUsernameRegex(artist);
            } catch (InvalidUsernameException e) {
                result = false;
            }
        }
        assertTrue(result);

    }

    @Test
    public void testUsernameTwo() {

        String[] username = {"", "    ", "TrentatreTrentiniEntraronoInTrentoTrotterellando", "g g"};

        boolean result = true;

        for (String s : username) {

            artist.setUsername(s);
            try {
                ProfileManagerFactory.getInstance().getProfileManager().checkUsernameRegex(artist);
            } catch (InvalidUsernameException e) {
                result = false;
            }
        }
        assertFalse(result);

    }

    @Test
    public void testUsernameThree() {

        String[] username = {"CIAO!", "❤", "cì@ò"};

        boolean result = true;

        for (String s : username) {

            artist.setUsername(s);
            try {
                ProfileManagerFactory.getInstance().getProfileManager().checkUsernameRegex(artist);
            } catch (InvalidUsernameException e) {
                result = false;
            }
        }
        assertFalse(result);

    }


}