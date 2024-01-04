package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.model.playable.Album;
import it.unipv.ingsfw.JavaBeats.model.playable.Song;

import java.util.ArrayList;

public interface ISongDAO {

    //METHODS:
    public ArrayList<Song> selectByAlbum(Album album);

}
