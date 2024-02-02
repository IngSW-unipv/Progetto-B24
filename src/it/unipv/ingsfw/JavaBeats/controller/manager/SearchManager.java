package it.unipv.ingsfw.JavaBeats.controller.manager;

import it.unipv.ingsfw.JavaBeats.dao.SearchDAO;
import it.unipv.ingsfw.JavaBeats.model.EJBENTITY;
import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import java.util.ArrayList;

public class SearchManager {

    //Costruttore
    public SearchManager() {

    }


    //Metodi

    public ArrayList<ArrayList<IJBResearchable>> search(String searchText, JBProfile activeProfile) {
        SearchDAO s = new SearchDAO();

        EJBENTITY[] ejbEntities = new EJBENTITY[]{EJBENTITY.SONG, EJBENTITY.EPISODE, EJBENTITY.PLAYLIST, EJBENTITY.ALBUM, EJBENTITY.PODCAST, EJBENTITY.USER, EJBENTITY.ARTIST};

        //Filling in the array
        ArrayList<ArrayList<IJBResearchable>> searchedList = new ArrayList<>();
        for (EJBENTITY ejbentity : ejbEntities) {
            searchedList.add(s.search(searchText, activeProfile, ejbentity));
        }

        return searchedList;
    }


}
