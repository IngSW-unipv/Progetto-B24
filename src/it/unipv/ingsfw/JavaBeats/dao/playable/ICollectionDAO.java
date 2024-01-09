package it.unipv.ingsfw.JavaBeats.dao.playable;

import it.unipv.ingsfw.JavaBeats.model.playable.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import java.util.ArrayList;

public interface ICollectionDAO {

    //METHODS:
    public void insert(JBCollection collection);

    public void remove(JBCollection collection);

    public void update(JBCollection collection);

    public JBCollection get(JBCollection collection);

    public ArrayList<JBCollection> selectByProfile(JBProfile profile);
}
