package it.unipv.ingsfw.JavaBeats.controller.manager;

import it.unipv.ingsfw.JavaBeats.dao.SearchDAO;
import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.model.EJBENTITY;
import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

public class SearchManager {

    //Costruttore
    public SearchManager() {

    }


    //Metodi

    public EnumMap<EJBENTITY, ArrayList<IJBResearchable>> search(String searchText, JBProfile activeProfile) throws AccountNotFoundException {
        SearchDAO s = new SearchDAO();

        //Filling in the map
        EnumMap<EJBENTITY, ArrayList<IJBResearchable>> searchedMap = new EnumMap<>(EJBENTITY.class);
        for (EJBENTITY ejbentity : EJBENTITY.values()) {
            searchedMap.put(ejbentity, s.search(searchText, activeProfile, ejbentity));

        }

        //Returns the map with the results
        return searchedMap;
    }


}
