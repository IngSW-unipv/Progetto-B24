package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.model.playable.Album;
import it.unipv.ingsfw.JavaBeats.model.user.Artist;

public interface IAlbumDAO {

    //METHODS:
    public void create(Album album);

    public void remove(Album album);

    public Album[] selectByArtist(Artist artist);

}
