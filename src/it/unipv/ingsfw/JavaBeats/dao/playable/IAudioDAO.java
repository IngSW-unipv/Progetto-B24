package it.unipv.ingsfw.JavaBeats.dao.playable;

import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Podcast;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

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
