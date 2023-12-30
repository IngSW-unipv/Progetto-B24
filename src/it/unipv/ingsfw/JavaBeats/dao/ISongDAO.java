package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.model.playable.Album;
import it.unipv.ingsfw.JavaBeats.model.playable.Song;

public interface ISongDAO {

    //METHODS:
    public Song[] selectByAlbum(Album album);

}
