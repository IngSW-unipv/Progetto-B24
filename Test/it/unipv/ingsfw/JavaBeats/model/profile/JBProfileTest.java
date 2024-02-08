package it.unipv.ingsfw.JavaBeats.model.profile;

import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.exceptions.InvalidPasswordException;
import it.unipv.ingsfw.JavaBeats.exceptions.RegexException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JBProfileTest {

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


}