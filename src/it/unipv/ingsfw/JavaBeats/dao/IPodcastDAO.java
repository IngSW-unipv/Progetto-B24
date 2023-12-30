package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.model.playable.Podcast;
import it.unipv.ingsfw.JavaBeats.model.user.Artist;
import java.util.ArrayList;

public interface IPodcastDAO {

    //METHODS:
    public void create(Podcast podcast);

    public void remove(Podcast podcast);

    public void edit(Podcast podcast);

    public ArrayList<Podcast> selectByArtist(Artist artist);

}

