package it.unipv.ingsfw.JavaBeats.model.profile;

import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.dao.profile.ProfileDAO;
import it.unipv.ingsfw.JavaBeats.exceptions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Tests profile access and registration.
 * This test verifies whether the user can successfully register and access with test credentials.
 */
public class JBProfileAccessTest {

    //Attributes
    private User user;
    private User userTest;
    private ProfileDAO p;


    @Test
    public void testRegistrationOne() {

        //Manually registering testUser
        userTest = new User("Maria", "maria@gmail.com", "Maria1");
        p = new ProfileDAO();
        p.insert(userTest);

        user = new User("", "", "");


        user.setUsername("Mario");
        user.setMail("mario@gmail.com");
        user.setPassword("Mario1");

        boolean result = true;

        try {
            ProfileManagerFactory.getInstance().getProfileManager().registration(user);

        } catch (AccountAlreadyExistsException e) {
            result = false;
        } catch (UsernameAlreadyTakenException u) {
            result = false;
        } catch (AccountNotFoundException ignored) {

        }
        assertTrue(result);
        p.remove(user);
        p.remove(userTest);

    }

    @Test
    public void testRegistrationTwo() {

        userTest = new User("Maria", "maria@gmail.com", "Maria1");
        p = new ProfileDAO();
        p.insert(userTest);

        user = new User("", "", "");

        user.setUsername("Maria");
        user.setMail("maria@gmail.com");
        user.setPassword("Maria1");

        boolean result = true;

        try {
            ProfileManagerFactory.getInstance().getProfileManager().registration(user);

        } catch (AccountAlreadyExistsException e) {
            result = false;
        } catch (UsernameAlreadyTakenException u) {
            result = false;
        } catch (AccountNotFoundException ignored) {

        }
        assertFalse(result);

        p.remove(userTest);
    }

    @Test
    public void testRegistrationThree() {

        userTest = new User("Maria", "maria@gmail.com", "Maria1");
        p = new ProfileDAO();
        p.insert(userTest);

        user = new User("", "", "");

        user.setUsername("Maria");
        user.setMail("Maria@gmail.com");
        user.setPassword("Maria1");

        boolean result = true;

        try {
            ProfileManagerFactory.getInstance().getProfileManager().registration(user);

        } catch (AccountAlreadyExistsException e) {
            result = false;
        } catch (UsernameAlreadyTakenException u) {
            result = false;
        } catch (AccountNotFoundException ignored) {

        }
        assertFalse(result);

        p.remove(userTest);
    }

    @Test
    public void testLoginOne() {

        userTest = new User("Maria", "maria@gmail.com", "Maria1");
        p = new ProfileDAO();
        p.insert(userTest);

        user = new User("", "", "");


        user.setUsername("Maria");
        user.setMail("maria@gmail.com");
        user.setPassword("Maria1");


        boolean result = true;

        try {
            ProfileManagerFactory.getInstance().getProfileManager().login(user);


        } catch (AccountNotFoundException a) {
            result = false;

        } catch (WrongPasswordException e) {
            result = false;
        }
        assertTrue(result);

        p.remove(userTest);
    }


    @Test
    public void testLoginTwo() {


        user = new User("", "", "");

        user.setUsername("Maria");
        user.setMail("Maria@gmail.com");
        user.setPassword("Maria1");

        boolean result = true;

        try {
            ProfileManagerFactory.getInstance().getProfileManager().login(user);


        } catch (AccountNotFoundException a) {
            result = false;

        } catch (WrongPasswordException ignored) {

        }
        assertFalse(result);

    }

    @Test
    public void testLoginThree() {

        userTest = new User("Maria", "maria@gmail.com", "Maria1");
        p = new ProfileDAO();
        p.insert(userTest);


        user = new User("", "", "");

        user.setUsername("Maria");
        user.setMail("Maria@gmail.com");
        user.setPassword("Maria");

        boolean result = true;

        try {
            ProfileManagerFactory.getInstance().getProfileManager().login(user);


        } catch (AccountNotFoundException ignored) {

        } catch (WrongPasswordException e) {
            result = false;
        }
        assertFalse(result);
        p.remove(userTest);


    }


}