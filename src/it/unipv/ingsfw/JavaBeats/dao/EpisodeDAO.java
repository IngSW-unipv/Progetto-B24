package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.model.playable.Album;
import it.unipv.ingsfw.JavaBeats.model.user.Artist;

public class EpisodeDAO implements IAlbumDAO {

    //METHODS:
    @Override
    public void create(Album album) {

    }

    @Override
    public void remove(Album album) {

    }

    @Override
    public Album[] selectByArtist(Artist artist) {
        return new Album[0];
    }
}
