package it.unipv.ingsfw.JavaBeats.controller.manager;

import it.unipv.ingsfw.JavaBeats.dao.playable.CollectionDAO;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

public class CollectionManager {

    //Metodi

    //GetFavorites
    public Playlist getFavorites(JBProfile activeProfile){
        CollectionDAO c= new CollectionDAO();
        Playlist favorites=c.getFavorites(activeProfile);
        return favorites;
    }
	
	

}
