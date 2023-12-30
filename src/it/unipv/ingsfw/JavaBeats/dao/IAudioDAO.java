package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.model.playable.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.Playlist;

import java.util.ArrayList;

public interface IAudioDAO {

    //METHODS:
    public void add(JBAudio audio);

    public void remove(JBAudio audio);

    public ArrayList<JBAudio> selectByPlalist(Playlist playlist);

}
