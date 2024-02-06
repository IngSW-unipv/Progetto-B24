package it.unipv.ingsfw.JavaBeats.controller.manager;

import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.dao.playable.AudioDAO;
import it.unipv.ingsfw.JavaBeats.dao.collection.CollectionDAO;
import it.unipv.ingsfw.JavaBeats.dao.profile.ProfileDAO;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.collection.Podcast;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
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

    if(jbCollection.getPicture()==null){
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
    }//end-if
    c.insert(jbCollection);
    return jbCollection;
  }

  public void removeCollection(JBCollection jbCollection){
    CollectionDAO c=new CollectionDAO();
    AudioDAO a=new AudioDAO();
    ProfileDAO p=new ProfileDAO();

    c.remove(jbCollection);
    for(JBAudio audio: jbCollection.getTrackList()){
      a.remove(audio);
    }//end-foreach

    p.refreshProfileInfo(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());
  }

  public void addToCollection(JBCollection jbCollection, JBAudio jbAudio){
    CollectionDAO c=new CollectionDAO();
    jbCollection.getTrackList().add(jbAudio);
    System.out.println(jbCollection.getTrackList());
    c.update(jbCollection);

  }

  public void removeFromPlaylist(JBCollection jbCollection, JBAudio jbAudio){
    CollectionDAO c=new CollectionDAO();
    jbCollection.getTrackList().remove(jbAudio);
    c.update(jbCollection);

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

  //SetFavorites
  public void setFavorites(JBProfile activeProfile){
    AudioDAO audioDAO=new AudioDAO();
    audioDAO.updateIsFavorite(activeProfile);
  }

  public ArrayList<JBAudio> getCollectionAudios(JBCollection jbCollection, JBProfile activeProfile){
    AudioDAO audioDAO=new AudioDAO();
    ArrayList<JBAudio> result=new ArrayList<>();

    try{
      Playlist playlist=(Playlist)jbCollection;

      result=audioDAO.selectByPlaylist(playlist, activeProfile);
    }catch(ClassCastException c1){
      try{
        Album album=(Album)jbCollection;

        result.addAll(audioDAO.selectByAlbum(album, activeProfile));
      }catch(ClassCastException c2){
        result.addAll(audioDAO.selectByPodcast((Podcast)jbCollection, activeProfile));
      }//end-try
    }//end-try
    return result;
  }

  public JBCollection getCollection(JBCollection jbCollection){
    CollectionDAO collectionDAO=new CollectionDAO();
    return collectionDAO.get(jbCollection);
  }
}
