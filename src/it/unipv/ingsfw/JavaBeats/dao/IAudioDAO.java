package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.model.playable.*;
import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;

import java.util.ArrayList;

public interface IAudioDAO {

    //METHODS:
    public void insert(JBAudio audio);

    public void remove(JBAudio audio);

    public void updateIsFavorite(JBAudio audio, JBProfile activeProfile);

    public JBAudio get(JBAudio audio, JBProfile activeProfile);

    public JBAudio get(JBAudio audio);

    public void addToListeningHistory(JBAudio audio, JBProfile profile);

    public ArrayList<JBAudio> selectByPlalist(Playlist playlist);

    public ArrayList<Song> selectByAlbum(Album album);

    public ArrayList<Episode> selectByPodcast(Podcast podcast);

}
