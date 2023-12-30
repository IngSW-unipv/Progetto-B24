package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.model.playable.Album;
import it.unipv.ingsfw.JavaBeats.model.playable.Song;

public class SongDAO implements ISongDAO {

    //METHODS:
    @Override
    public Song[] selectByAlbum(Album album) {
        return new Song[0];
    }

}
