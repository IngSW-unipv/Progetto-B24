package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;

import java.util.ArrayList;

public interface ISearchDAO {

    //METHODS:
    ArrayList<IJBResearchable> search(String field);

    ArrayList<IJBResearchable> search(String field, ERESEARCH mode);
}
