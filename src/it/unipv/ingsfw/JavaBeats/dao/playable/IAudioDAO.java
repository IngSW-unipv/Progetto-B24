package it.unipv.ingsfw.JavaBeats.dao.playable;

import it.unipv.ingsfw.JavaBeats.model.playable.audio.*;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Podcast;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import java.util.ArrayList;

public interface IAudioDAO{

  //METHODS:
  void insert(JBAudio audio);

  void remove(JBAudio audio);

  JBAudio get(JBAudio audio, JBProfile activeProfile);

  JBAudio get(JBAudio audio);

  Song getSong(JBAudio audio, JBProfile activeProfile);

  Song getSong(JBAudio audio);

  Episode getEpisode(JBAudio audio, JBProfile activeProfile);

  Episode getEpisode(JBAudio audio);

  void updateIsFavorite(JBProfile activeProfile);

  void addToListeningHistory(JBAudio audio, JBProfile profile);

  ArrayList<JBAudio> selectByPlaylist(Playlist playlist);

  ArrayList<Song> selectByAlbum(Album album);

  ArrayList<Episode> selectByPodcast(Podcast podcast);

  ArrayList<JBAudio> selectFavorites(JBProfile activeProfile);

}
