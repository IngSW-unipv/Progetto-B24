package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;

public interface ISearchDAO {

    //METHODS:
    public IJBResearchable search(String field);

    public IJBResearchable searchBy(String field, ERESEARCH mode);
}
