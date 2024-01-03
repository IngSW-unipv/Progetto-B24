package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;

public interface IProfileDAO {

    //METHODS:
    public void insert(JBProfile profile);

    public void remove(JBProfile profile);

    public void update(JBProfile profile);

    public JBProfile get(JBProfile profile);

}
