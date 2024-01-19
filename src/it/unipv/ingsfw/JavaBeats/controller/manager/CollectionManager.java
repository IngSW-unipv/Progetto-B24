package it.unipv.ingsfw.JavaBeats.controller.manager;

import it.unipv.ingsfw.JavaBeats.dao.playable.AudioDAO;
import it.unipv.ingsfw.JavaBeats.dao.playable.CollectionDAO;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Podcast;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CollectionManager{

  public CollectionManager(){

  }

  //Metodi
  public JBCollection createJBCollection(JBCollection jbCollection){
    CollectionDAO c=new CollectionDAO();

    /* Default collection image when inserting */
    BufferedImage bufferedImage=null;
    try{
      bufferedImage=ImageIO.read(new File("src/it/unipv/ingsfw/JavaBeats/view/resources/icons/RecordBig.png"));
      ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
      ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
      byte[] image=byteArrayOutputStream.toByteArray();
      jbCollection.setPicture(new SerialBlob(image));
    }catch(IOException | SQLException e){
      throw new RuntimeException(e);
    }//end-try
    c.insert(jbCollection);
    return jbCollection;
  }

  public ArrayList<JBCollection> getPlaylists(JBProfile activeProfile){
    CollectionDAO c=new CollectionDAO();
    return c.selectPlaylistsByProfile(activeProfile);
  }

  public ArrayList<JBCollection> getAlbums(Artist artist){
    CollectionDAO c=new CollectionDAO();
    return c.selectAlbumsByArtist(artist);
  }

  public ArrayList<JBCollection> getPodcasts(Artist artist){
    CollectionDAO c=new CollectionDAO();
    return c.selectPodcastsByArtist(artist);
  }

  //GetFavorites
  public Playlist getFavorites(JBProfile activeProfile){
    CollectionDAO c=new CollectionDAO();
    Playlist favorites=c.getFavorites(activeProfile);
    return favorites;
  }

  public ArrayList<JBAudio> getCollectionAudios(JBCollection jbCollection){
    AudioDAO audioDAO=new AudioDAO();
    ArrayList<JBAudio> result=new ArrayList<>();

    try{
      Playlist playlist=(Playlist)jbCollection;

      result=audioDAO.selectByPlaylist(playlist);
    }catch(ClassCastException c1){
      try{
        Album album=(Album)jbCollection;

        result.addAll(audioDAO.selectByAlbum(album));
      }catch(ClassCastException c2){
        result.addAll(audioDAO.selectByPodcast((Podcast)jbCollection));
      }//end-try
    }//end-try
    return result;
  }
}
