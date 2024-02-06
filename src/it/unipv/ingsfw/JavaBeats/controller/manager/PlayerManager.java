package it.unipv.ingsfw.JavaBeats.controller.manager;

import it.unipv.ingsfw.JavaBeats.controller.adapter.FXAdapter;
import it.unipv.ingsfw.JavaBeats.controller.factory.FXAdapterFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.AudioTableHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SidebarHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SongbarHandler;
import it.unipv.ingsfw.JavaBeats.dao.playable.AudioDAO;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class PlayerManager{
  /*---------------------------------------*/
  //Attributes
  /*---------------------------------------*/
  private static JBProfile activeProfile=null;
  private static JBAudio CURRENT_AUDIO_PLAYING=null;
  private static JBCollection CURRENT_COLLECTION_PLAYING=null;
  private static double volume=00.50;
  private static boolean randomized=false;
  private static boolean collectionLooping=false;
  private static boolean audioLooping=false;
  private static final LinkedList<JBAudio> queue=new LinkedList<>();
  private static ArrayList<JBAudio> playingAudiosCopy=new ArrayList<>();
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

  public static JBCollection getCurrentCollectionPlaying(){
    return CURRENT_COLLECTION_PLAYING;
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
      CURRENT_AUDIO_PLAYING.getMediaPlayer().setVolume(volume);
      audioDAO.addToListeningHistory(audioToBePlayed, activeProfile);
    }else if(collectionLooping){
      loop(CURRENT_COLLECTION_PLAYING);
    }else if(audioLooping){
      AudioDAO audioDAO=new AudioDAO();

      adapter.play(CURRENT_AUDIO_PLAYING);
      CURRENT_AUDIO_PLAYING.getMediaPlayer().setVolume(volume);
      audioDAO.addToListeningHistory(CURRENT_AUDIO_PLAYING, activeProfile);
    }else{
      playingAudiosCopy.clear();
      CURRENT_AUDIO_PLAYING=null;
      CURRENT_COLLECTION_PLAYING=null;
      randomized=false;
    }//end-if

    SidebarHandler.getInstance(activeProfile);
    SongbarHandler.getInstance(activeProfile, CURRENT_AUDIO_PLAYING);
  }

  public void play(JBAudio jbAudio){
    AudioDAO audioDAO=new AudioDAO();

    queue.clear();
    if(CURRENT_AUDIO_PLAYING!=null){
      CURRENT_AUDIO_PLAYING.getMediaPlayer().dispose();
    }//end-if

    randomized=false;
    collectionLooping=false;
    audioLooping=false;

    CURRENT_COLLECTION_PLAYING=null;
    CURRENT_AUDIO_PLAYING=jbAudio;
    adapter.play(jbAudio);
    CURRENT_AUDIO_PLAYING.getMediaPlayer().setVolume(volume);
    audioDAO.addToListeningHistory(jbAudio, activeProfile);

    SidebarHandler.getInstance(activeProfile);
    SongbarHandler.getInstance(activeProfile, CURRENT_AUDIO_PLAYING);
  }

  public void play(JBCollection jbCollection){
    CURRENT_COLLECTION_PLAYING=jbCollection.getCopy();
    Collections.reverse(CURRENT_COLLECTION_PLAYING.getTrackList());

    queue.clear();
    if(CURRENT_AUDIO_PLAYING!=null){
      CURRENT_AUDIO_PLAYING.getMediaPlayer().dispose();
      CURRENT_AUDIO_PLAYING=null;
    }//end-if
    playingAudiosCopy=new ArrayList<>(CURRENT_COLLECTION_PLAYING.getTrackList());
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

  public void playFromQueue(JBAudio jbAudio){
    LinkedList<JBAudio> newQueue=new LinkedList<>(queue.subList(queue.indexOf(jbAudio), queue.size()));
    queue.clear();
    playingAudiosCopy.clear();
    queue.addAll(newQueue);

    play();
  }

  /* Handles SongBar random button */
  public void randomize(){
    randomized=false;

    if(CURRENT_COLLECTION_PLAYING!=null){
      queue.clear();
      Collections.shuffle(CURRENT_COLLECTION_PLAYING.getTrackList());

      randomized=true;
      collectionLooping=false;
      audioLooping=false;
      playingAudiosCopy.clear();
      play(CURRENT_COLLECTION_PLAYING);
    }//end-if
  }

  /* Handles CollectionViewGUI random button */
  public void randomize(JBCollection jbCollection){
    collectionLooping=false;
    audioLooping=false;
    playingAudiosCopy.clear();

    Collections.shuffle(jbCollection.getTrackList());

    randomized=true;
    play(jbCollection);
  }

  /* Handles CollectionViewGUI loop button */
  public void loop(JBCollection jbCollection){
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

  public void skipForward(){
    if(CURRENT_AUDIO_PLAYING!=null){
      CURRENT_AUDIO_PLAYING.getMediaPlayer().dispose();
    }//end-if
    play();
  }

  public void skipBack(){
    if(CURRENT_AUDIO_PLAYING!=null && CURRENT_COLLECTION_PLAYING!=null){
      queue.push(CURRENT_AUDIO_PLAYING);
      /* If I don't go out of bounds I can skip back */
      if((playingAudiosCopy.indexOf(CURRENT_AUDIO_PLAYING)+1)<playingAudiosCopy.size() && (playingAudiosCopy.indexOf(CURRENT_AUDIO_PLAYING)+1)>0){
        queue.push(playingAudiosCopy.get(playingAudiosCopy.indexOf(CURRENT_AUDIO_PLAYING)+1));
      }//end-if
      play();
    }//end-if
  }
  /*---------------------------------------*/
}
