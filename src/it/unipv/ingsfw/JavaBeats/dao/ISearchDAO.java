package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import java.util.ArrayList;

public interface ISearchDAO {

    //METHODS:
    ArrayList<IJBResearchable> search(String field, JBProfile activeProfile);

    ArrayList<IJBResearchable> search(String field, JBProfile activeProfile, ERESEARCH mode);
}
