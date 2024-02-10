package it.unipv.ingsfw.JavaBeats.controller.manager;
import it.unipv.ingsfw.JavaBeats.controller.adapter.FXAdapter;
import it.unipv.ingsfw.JavaBeats.controller.adapter.IAdapter;
import it.unipv.ingsfw.JavaBeats.controller.factory.FXAdapterFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.AudioTableHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SidebarHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SongbarHandler;
import it.unipv.ingsfw.JavaBeats.dao.playable.AudioDAO;
import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.exceptions.SystemErrorException;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
public class PlayerManager{
  /*---------------------------------------*/
  //Attributes
  /*---------------------------------------*/
  private static JBAudio currentAudioPlaying=null;
  private static JBCollection currentCollectionPlaying=null;
  private static double volume=00.50;
  private static boolean randomized=false;
  private static boolean collectionLooping=false;
  private static boolean audioLooping=false;
  private static final LinkedList<JBAudio> queue=new LinkedList<>();
  private static ArrayList<JBAudio> playingAudiosCopy=new ArrayList<>();
  private final IAdapter adapter=FXAdapterFactory.getInstance().getFXAdapter();
  /*---------------------------------------*/
  //Constructors
  /*---------------------------------------*/
  public PlayerManager(){
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public static LinkedList<JBAudio> getQueue(){
    return queue;
  }
  public static JBAudio getCurrentAudioPlaying(){
    return currentAudioPlaying;
  }
  public static JBCollection getCurrentCollectionPlaying(){
    return currentCollectionPlaying;
  }
  public static boolean isRandomized(){
    return randomized;
  }
  public static void setRandomized(boolean randomized){
    PlayerManager.randomized=randomized;
  }
  public static boolean isCollectionLooping(){
    return collectionLooping;
  }
  public static void setCollectionLooping(boolean collectionLooping){
    PlayerManager.collectionLooping=collectionLooping;
  }
  public static boolean isAudioLooping(){
    return audioLooping;
  }
  public static void setAudioLooping(boolean audioLooping){
    PlayerManager.audioLooping=audioLooping;
  }
  public static double getVolume(){
    return volume;
  }
  public static void setVolume(double volume){
    PlayerManager.volume=volume;
  }
  /*---------------------------------------*/
  //Methods
  /*---------------------------------------*/
  public void addToQueue(JBAudio ijbPlayable){
    queue.push(ijbPlayable);
  }

  public void removeFromQueue(JBAudio jbAudio){
    queue.remove(jbAudio);
  }

  public void deleteQueue(){
    queue.clear();

    collectionLooping=false;
    audioLooping=false;
  }

  public void play() throws SystemErrorException{
    if(!queue.isEmpty()){
      AudioDAO audioDAO=new AudioDAO();

      if(currentAudioPlaying!=null){
        currentAudioPlaying.getMediaPlayer().dispose();
      }//end-if

      JBAudio audioToBePlayed=queue.pop();
      currentAudioPlaying=audioToBePlayed;
      adapter.play(audioToBePlayed);
      currentAudioPlaying.getMediaPlayer().setVolume(volume);
      audioDAO.addToListeningHistory(audioToBePlayed, ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());
      if(AudioTableHandler.getInstance().getCurrentAudioTableShowing()!=null){
        AudioTableHandler.getInstance().getCurrentAudioTableShowing().refresh();
      }//end-if
    }else if(collectionLooping){
      loop(currentCollectionPlaying);
    }else if(audioLooping){
      AudioDAO audioDAO=new AudioDAO();

      currentAudioPlaying.getMediaPlayer().dispose();
      adapter.play(currentAudioPlaying);
      currentAudioPlaying.getMediaPlayer().setVolume(volume);
      audioDAO.addToListeningHistory(currentAudioPlaying, ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());
      if(AudioTableHandler.getInstance().getCurrentAudioTableShowing()!=null){
        AudioTableHandler.getInstance().getCurrentAudioTableShowing().refresh();
      }//end-if
    }else{
      playingAudiosCopy.clear();
      currentAudioPlaying=null;
      currentCollectionPlaying=null;
      randomized=false;
    }//end-if

    /* Updating instances of Sidebar and Songbar */
    SidebarHandler sidebarHandler=new SidebarHandler();
    SongbarHandler.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile(), currentAudioPlaying);
  }

  public void play(JBAudio jbAudio){
    AudioDAO audioDAO=new AudioDAO();

    queue.clear();
    if(currentAudioPlaying!=null){
      currentAudioPlaying.getMediaPlayer().dispose();
    }//end-if

    randomized=false;
    collectionLooping=false;
    audioLooping=false;

    currentCollectionPlaying=null;
    currentAudioPlaying=jbAudio;
    adapter.play(jbAudio);
    currentAudioPlaying.getMediaPlayer().setVolume(volume);
    audioDAO.addToListeningHistory(jbAudio, ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());
    if(AudioTableHandler.getInstance().getCurrentAudioTableShowing()!=null){
      AudioTableHandler.getInstance().getCurrentAudioTableShowing().refresh();
    }//end-if

    /* Updating instances of Sidebar and Songbar */
    SidebarHandler sidebarHandler=new SidebarHandler();
    SongbarHandler.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile(), currentAudioPlaying);
  }

  public void play(JBCollection jbCollection) throws SystemErrorException{
    currentCollectionPlaying=jbCollection.getCopy();
    Collections.reverse(currentCollectionPlaying.getTrackList());

    queue.clear();
    if(currentAudioPlaying!=null){
      currentAudioPlaying.getMediaPlayer().dispose();
      currentAudioPlaying=null;
    }//end-if
    playingAudiosCopy=new ArrayList<>(currentCollectionPlaying.getTrackList());
    for(JBAudio jbAudio: currentCollectionPlaying.getTrackList()){
      queue.push(jbAudio);
    }//end-foreach
    play();
  }

  public void playPause(){
    if(currentAudioPlaying!=null){
      if(currentAudioPlaying.getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)){
        adapter.pause(currentAudioPlaying);
      }else{
        adapter.play(currentAudioPlaying);
      }//end-if
    }//end-if
  }

  public void playFromQueue(JBAudio jbAudio) throws SystemErrorException, AccountNotFoundException{
    LinkedList<JBAudio> newQueue=new LinkedList<>(queue.subList(queue.indexOf(jbAudio), queue.size()));
    queue.clear();
    playingAudiosCopy.clear();
    queue.addAll(newQueue);

    play();
  }

  /* Handles SongBar random button */
  public void randomize() throws SystemErrorException{
    randomized=false;

    if(currentCollectionPlaying!=null){
      queue.clear();
      Collections.shuffle(currentCollectionPlaying.getTrackList());

      randomized=true;
      collectionLooping=false;
      audioLooping=false;
      playingAudiosCopy.clear();
      play(currentCollectionPlaying);
    }//end-if
  }

  /* Handles CollectionViewGUI random button */
  public void randomize(JBCollection jbCollection) throws SystemErrorException{
    collectionLooping=false;
    audioLooping=false;
    playingAudiosCopy.clear();

    Collections.shuffle(jbCollection.getTrackList());

    randomized=true;
    play(jbCollection);
  }

  /* Handles CollectionViewGUI loop button */
  public void loop(JBCollection jbCollection) throws SystemErrorException{
    collectionLooping=true;
    randomized=false;
    audioLooping=false;
    playingAudiosCopy.clear();

    play(jbCollection);
  }

  /* Handles SongBar loop button */
  public void loop(){
    queue.clear();
    audioLooping=true;
    randomized=false;
    collectionLooping=false;
    playingAudiosCopy.clear();
  }

  public void skipForward() throws SystemErrorException{
    audioLooping=false;

    if(currentAudioPlaying!=null){
      currentAudioPlaying.getMediaPlayer().dispose();
    }//end-if
    play();
  }

  public void skipBack() throws SystemErrorException{
    if(currentAudioPlaying!=null && currentCollectionPlaying!=null){
      audioLooping=false;

      queue.push(currentAudioPlaying);
      /* If I don't go out of bounds I can skip back */
      if((playingAudiosCopy.indexOf(currentAudioPlaying)+1)<playingAudiosCopy.size() && (playingAudiosCopy.indexOf(currentAudioPlaying)+1)>0){
        queue.push(playingAudiosCopy.get(playingAudiosCopy.indexOf(currentAudioPlaying)+1));
      }//end-if
      play();
    }//end-if
  }
  /*---------------------------------------*/
}
