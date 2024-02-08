package it.unipv.ingsfw.JavaBeats.model.profile;

import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.dao.profile.ProfileDAO;
import it.unipv.ingsfw.JavaBeats.exceptions.InvalidPasswordException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JBProfileAccessTest {

    //Attributes
    private User user;
    private User testUser;
    private ProfileDAO p;

    @Before
    public void intTest() {

        testUser = new User("Maria", "maria@gmail.com", "Maria1");
        user = new User("Mario", "mario@gmail.com", "Mario1");
        p = new ProfileDAO();
        p.insert(testUser);


    }

    @Test
    public void testPasswordOne() {


        String[] password = {"Alessia1", "_Ale300502_", "3AleProtti"};

        boolean result = true;

        for (String s : password) {

            user.setPassword(s);
            try {
                ProfileManagerFactory.getInstance().getProfileManager().checkPasswordRegex(user);
            } catch (InvalidPasswordException e) {
                result = false;
            }
        }
        assertTrue(result);

    }


}