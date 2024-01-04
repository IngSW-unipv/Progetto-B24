package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.model.playable.*;

import java.util.ArrayList;

public interface IAudioDAO {

    //METHODS:
    public void insert(JBAudio audio);

    public void remove(JBAudio audio);

    public ArrayList<JBAudio> selectByPlalist(Playlist playlist);

    public ArrayList<Song> selectByAlbum(Album album);

    public ArrayList<Episode> selectByPodcast(Podcast podcast);

}
