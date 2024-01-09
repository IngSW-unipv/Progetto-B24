package it.unipv.ingsfw.JavaBeats.dao.playable;

import it.unipv.ingsfw.JavaBeats.model.playable.collection.*;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import java.util.ArrayList;

public interface ICollectionDAO {

    //METHODS:
    void insert(JBCollection collection);
    void remove(JBCollection collection);
    void update(JBCollection collection);
    JBCollection get(JBCollection collection);
    Playlist getPlaylist(JBCollection collection);
    Album getAlbum(JBCollection collection);
    Podcast getPodcast(JBCollection collection);
    ArrayList<JBCollection> selectByProfile(JBProfile profile);

}
