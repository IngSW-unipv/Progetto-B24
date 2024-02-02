package it.unipv.ingsfw.JavaBeats.controller.manager;
import it.unipv.ingsfw.JavaBeats.controller.adapter.FXAdapter;
import it.unipv.ingsfw.JavaBeats.controller.factory.FXAdapterFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SidebarHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SongbarHandler;
import it.unipv.ingsfw.JavaBeats.dao.playable.AudioDAO;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import javafx.scene.media.MediaPlayer;

import java.util.Collections;
import java.util.LinkedList;

public class PlayerManager{
  /*---------------------------------------*/
  //Attributes
  /*---------------------------------------*/
  private static JBProfile activeProfile=null;
  private static JBAudio CURRENT_AUDIO_PLAYING=null;
  private static JBCollection CURRENT_COLLECTION_PLAYING=null;
  private static boolean randomized=false;
  private static final LinkedList<JBAudio> queue=new LinkedList<>();
  private final FXAdapter adapter=FXAdapterFactory.getInstance().getFXAdapter();

  /*---------------------------------------*/
  //Constructors
  /*---------------------------------------*/
  public PlayerManager(){
    activeProfile=ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile();
  }

  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public static LinkedList<JBAudio> getQueue(){
    return queue;
  }

  public static JBAudio getCurrentAudioPlaying(){
    return CURRENT_AUDIO_PLAYING;
  }

  public static boolean isRandomized(){
    return randomized;
  }

  public static void setRandomized(boolean randomized){
    PlayerManager.randomized=randomized;
  }

  /*---------------------------------------*/
  //Methods
  /*---------------------------------------*/
  public void addToQueue(JBAudio ijbPlayable){
    queue.push(ijbPlayable);
  }

  public void play(){
    if(!queue.isEmpty()){
      AudioDAO audioDAO=new AudioDAO();

      if(CURRENT_AUDIO_PLAYING!=null){
        CURRENT_AUDIO_PLAYING.getMediaPlayer().dispose();
      }//end-if

      JBAudio audioToBePlayed=queue.pop();
      CURRENT_AUDIO_PLAYING=audioToBePlayed;
      adapter.play(audioToBePlayed);
      audioDAO.addToListeningHistory(audioToBePlayed, activeProfile);
    }else{
      CURRENT_AUDIO_PLAYING=null;
      CURRENT_COLLECTION_PLAYING=null;
    }//end-if

    SidebarHandler.getInstance(activeProfile, CURRENT_AUDIO_PLAYING);
    SongbarHandler.getInstance(activeProfile, CURRENT_AUDIO_PLAYING);
  }

  public void play(JBAudio jbAudio){
    AudioDAO audioDAO=new AudioDAO();

    queue.clear();
    if(CURRENT_AUDIO_PLAYING!=null){
      CURRENT_AUDIO_PLAYING.getMediaPlayer().dispose();
    }//end-if
    randomized=false;
    CURRENT_COLLECTION_PLAYING=null;
    CURRENT_AUDIO_PLAYING=jbAudio;
    adapter.play(jbAudio);
    audioDAO.addToListeningHistory(jbAudio, activeProfile);

    SidebarHandler.getInstance(activeProfile, CURRENT_AUDIO_PLAYING);
    SongbarHandler.getInstance(activeProfile, CURRENT_AUDIO_PLAYING);
  }

  public void play(JBCollection jbCollection){
    queue.clear();
    if(CURRENT_AUDIO_PLAYING!=null){
      CURRENT_AUDIO_PLAYING.getMediaPlayer().dispose();
    }//end-if
    CURRENT_COLLECTION_PLAYING=jbCollection;
    for(JBAudio jbAudio: CURRENT_COLLECTION_PLAYING.getTrackList()){
      queue.push(jbAudio);
    }//end-foreach
    play();
  }

  public void playPause(){
    if(CURRENT_AUDIO_PLAYING!=null){
      if(CURRENT_AUDIO_PLAYING.getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)){
        CURRENT_AUDIO_PLAYING.getMediaPlayer().pause();
      }else{
        CURRENT_AUDIO_PLAYING.getMediaPlayer().play();
      }//end-if
    }//end-if
  }

  /* Handles SongBar random button */
  public void randomize(){
    if(CURRENT_COLLECTION_PLAYING!=null){
      queue.clear();
      Collections.shuffle(CURRENT_COLLECTION_PLAYING.getTrackList());

      randomized=true;
      play(CURRENT_COLLECTION_PLAYING);
    }
  }

  /* Handles CollectionViewGUI random button */
  public void randomize(JBCollection jbCollection){
    queue.clear();
    Collections.shuffle(jbCollection.getTrackList());

    randomized=true;
    play(jbCollection);
  }

  /* CollectionViewGUI random button if it's queue */
  public void randomize(LinkedList<JBAudio> queueToRandomize){
    queue.clear();
    Collections.shuffle(queueToRandomize);

    randomized=true;
    play();
  }
  /*---------------------------------------*/
}
