package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.model.playable.Playlist;
import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;

import java.util.ArrayList;

public interface IPlaylistDAO {

    //METHODS:
    public void create(Playlist playlist);

    public void remove(Playlist playlist);

    public void edit(Playlist playlist);

    public ArrayList<Playlist> selectByJBProfile(JBProfile profile);

}
