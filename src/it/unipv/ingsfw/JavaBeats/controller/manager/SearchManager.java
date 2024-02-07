package it.unipv.ingsfw.JavaBeats.controller.manager;

import it.unipv.ingsfw.JavaBeats.dao.SearchDAO;
import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.model.EJBENTITY;
import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

public class SearchManager{

  //Costruttore
  public SearchManager(){

  }


  //Metodi

  public EnumMap<EJBENTITY, ArrayList<IJBResearchable>> search(String searchText, JBProfile activeProfile) throws AccountNotFoundException{
    SearchDAO s=new SearchDAO();

    EJBENTITY[] ejbEntities=new EJBENTITY[]{EJBENTITY.SONG, EJBENTITY.EPISODE, EJBENTITY.PLAYLIST, EJBENTITY.ALBUM, EJBENTITY.PODCAST, EJBENTITY.USER, EJBENTITY.ARTIST};

    //Filling in the map
    EnumMap<EJBENTITY, ArrayList<IJBResearchable>> searchedMap=new EnumMap<>(EJBENTITY.class);
    for(EJBENTITY ejbentity: ejbEntities){
      searchedMap.put(ejbentity, s.search(searchText, activeProfile, ejbentity));

    }

    return searchedMap;
  }


}
