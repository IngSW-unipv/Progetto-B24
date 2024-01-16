package it.unipv.ingsfw.JavaBeats.dao.playable;

import it.unipv.ingsfw.JavaBeats.model.playable.collection.*;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import java.util.ArrayList;

public interface ICollectionDAO{

  //METHODS:
  JBCollection insert(JBCollection collection);

  void remove(JBCollection collection);

  void update(JBCollection collection);

  JBCollection get(JBCollection collection);

  Playlist getPlaylist(JBCollection collection);

  Album getAlbum(JBCollection collection);

  Podcast getPodcast(JBCollection collection);

  Playlist getFavorites(JBProfile activeProfile);

  ArrayList<JBCollection> selectPlaylistsByProfile(JBProfile profile);
  ArrayList<JBCollection> selectAlbumsByArtist(Artist artist);
  ArrayList<JBCollection> selectPodcastsByArtist(Artist artist);




}
