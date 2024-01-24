package it.unipv.ingsfw.JavaBeats.controller.manager;

import it.unipv.ingsfw.JavaBeats.dao.ERESEARCH;
import it.unipv.ingsfw.JavaBeats.dao.SearchDAO;
import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchManager {


    //Metodi

    public ArrayList<ArrayList<IJBResearchable>> search(String searchText,JBProfile activeProfile){
        SearchDAO s= new SearchDAO();

        ERESEARCH[] eResearch= new ERESEARCH[]{ERESEARCH.SONG, ERESEARCH.ARTIST, ERESEARCH.ALBUM, ERESEARCH.PODCAST, ERESEARCH.PLAYLIST, ERESEARCH.USER};

        //Filling in the array
        ArrayList<ArrayList<IJBResearchable>> searchedList= new ArrayList<>();
        for(ERESEARCH eresearch: eResearch){
            searchedList.add(s.search(searchText, activeProfile, eresearch));
        }

        return searchedList;
    }


}
