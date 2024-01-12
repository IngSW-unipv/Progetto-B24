package it.unipv.ingsfw.JavaBeats.controller.manager;

import it.unipv.ingsfw.JavaBeats.dao.profile.ProfileDAO;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

public class ProfileManager {

    //Attributi

    private static JBProfile activeProfile;


    //Getters and Setters


    //Metodi


    //Login
    //Propagates exception from dao
    public JBProfile login(JBProfile profile){
        ProfileDAO p= new ProfileDAO();
        activeProfile=p.get(profile);
        return activeProfile;
    }

    //Registration
    //Propagates exception from dao
    public void registration(JBProfile profile){
        ProfileDAO p= new ProfileDAO();
        p.insert(profile);
        activeProfile= profile;
    }

	
	
	
}
