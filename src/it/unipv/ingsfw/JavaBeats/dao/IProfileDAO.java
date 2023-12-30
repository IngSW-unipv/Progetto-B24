package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;

public interface IProfileDAO {

    //METHODS:
    public void login(JBProfile profile);

    public void signIn(JBProfile profile);

    public void edit(JBProfile profile);

    public void remove(JBProfile profile);

}
