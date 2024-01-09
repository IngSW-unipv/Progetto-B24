package it.unipv.ingsfw.JavaBeats.dao.profile;

import it.unipv.ingsfw.JavaBeats.model.profile.*;

public interface IProfileDAO {

    //METHODS:
    void insert(JBProfile profile);
    void remove(JBProfile profile);
    void update(JBProfile profile);
    JBProfile get(JBProfile profile);
    Artist getArtist(JBProfile profile);
    User getUser(JBProfile profile);

}
