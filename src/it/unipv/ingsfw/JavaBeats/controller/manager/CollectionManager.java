package it.unipv.ingsfw.JavaBeats.controller.manager;

import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.dao.playable.AudioDAO;
import it.unipv.ingsfw.JavaBeats.dao.collection.CollectionDAO;
import it.unipv.ingsfw.JavaBeats.dao.profile.ProfileDAO;
import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.exceptions.InvalidAudioException;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.collection.Podcast;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import org.apache.tika.metadata.Metadata;

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

  //Methods
  public JBCollection createJBCollection(JBCollection jbCollection) throws AccountNotFoundException{
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

    //Removing collection
    public void removeCollection(JBCollection jbCollection) throws AccountNotFoundException {
        CollectionDAO c = new CollectionDAO();
        AudioDAO a = new AudioDAO();
        ProfileDAO p = new ProfileDAO();

    c.remove(jbCollection);

    /* If the collection removed is an Album or a Podcast then we need to remove also its audios */
    try{
      Playlist playlist=(Playlist)jbCollection;
    }catch(ClassCastException classCastException){
      for(JBAudio audio: jbCollection.getTrackList()){
        a.remove(audio);
      }//end-foreach
    }//end-try

    p.refreshProfileInfo(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());
  }

    //Adding to collection
    public void addToCollection(JBCollection jbCollection, JBAudio jbAudio) throws AccountNotFoundException {
        CollectionDAO c = new CollectionDAO();
        jbCollection.getTrackList().add(jbAudio);
        c.update(jbCollection);

  }

    //Removing audio from playlist
    public void removeFromPlaylist(JBCollection jbCollection, JBAudio jbAudio) throws AccountNotFoundException {
        CollectionDAO c = new CollectionDAO();
        jbCollection.getTrackList().remove(jbAudio);
        c.update(jbCollection);

  }

    //Getting profile's playlist
    public ArrayList<JBCollection> getPlaylists(JBProfile activeProfile) throws AccountNotFoundException {
        CollectionDAO c = new CollectionDAO();
        return c.selectPlaylistsByProfile(activeProfile);
    }

    //Geetting artist's album
    public ArrayList<JBCollection> getAlbums(Artist artist) throws AccountNotFoundException {
        CollectionDAO c = new CollectionDAO();
        return c.selectAlbumsByArtist(artist);
    }

    //Getting artist's podcast
    public ArrayList<JBCollection> getPodcasts(Artist artist) throws AccountNotFoundException {
        CollectionDAO c = new CollectionDAO();
        return c.selectPodcastsByArtist(artist);
    }

    //Getting profile's favorites
    public Playlist getFavorites(JBProfile activeProfile) throws AccountNotFoundException {
        CollectionDAO c = new CollectionDAO();
        Playlist favorites = c.getFavorites(activeProfile);
        return favorites;
    }

    //Setting profile's favorites
    public void setFavorites(JBProfile activeProfile) throws AccountNotFoundException {
        AudioDAO audioDAO = new AudioDAO();
        audioDAO.updateIsFavorite(activeProfile);
    }

    //Getting collection albums
    public ArrayList<JBAudio> getCollectionAudios(JBCollection jbCollection, JBProfile activeProfile) throws AccountNotFoundException {
        AudioDAO audioDAO = new AudioDAO();
        ArrayList<JBAudio> result = new ArrayList<>();

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

    //Getting collection creator
    public void getCollectionCreator(JBCollection jbCollection) throws AccountNotFoundException {
        ProfileDAO profileDAO = new ProfileDAO();

    profileDAO.refreshProfileInfo(jbCollection.getCreator());
  }

    //Getting collection
    public JBCollection getCollection(JBCollection jbCollection) throws AccountNotFoundException {
        CollectionDAO collectionDAO = new CollectionDAO();
        return collectionDAO.get(jbCollection);

  }

    //Editing collection
    public void edit(JBCollection jbCollection) throws AccountNotFoundException {

    CollectionDAO c=new CollectionDAO();
    c.update(jbCollection);

  }

    //Checking Meatadata
    public void checkMetadata(Metadata metadata) throws InvalidAudioException {
        if (metadata.get("xmpDM:genre") == null) {
            throw new InvalidAudioException();
        } else if (metadata.get("xmpDM:duration") == null) {
            throw new InvalidAudioException();
        }//end-if
    }
}
