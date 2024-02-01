package it.unipv.ingsfw.JavaBeats.controller.manager;
import it.unipv.ingsfw.JavaBeats.controller.adapter.FXAdapter;
import it.unipv.ingsfw.JavaBeats.controller.factory.FXAdapterFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.dao.playable.AudioDAO;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import javafx.scene.media.MediaPlayer;

import java.util.LinkedList;

public class PlayerManager{
  /*---------------------------------------*/
  //Attributes
  /*---------------------------------------*/
  private static JBProfile activeProfile=null;
  private static JBAudio CURRENT_AUDIO_PLAYING=null;
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
  /*---------------------------------------*/
  //Methods
  /*---------------------------------------*/
  public void addToQueue(JBAudio ijbPlayable){
    queue.push(ijbPlayable);
  }

  public void play(){
    if(!queue.isEmpty()){
      AudioDAO audioDAO=new AudioDAO();

      if(CURRENT_AUDIO_PLAYING!=null && CURRENT_AUDIO_PLAYING.getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)){
        CURRENT_AUDIO_PLAYING.getMediaPlayer().dispose();
      }//end-if

      JBAudio audioToBePlayed=queue.pop();
      CURRENT_AUDIO_PLAYING=audioToBePlayed;
      adapter.play(audioToBePlayed);
      audioDAO.addToListeningHistory(audioToBePlayed, activeProfile);
    }//end-if
  }

  public void play(JBAudio jbAudio){
    AudioDAO audioDAO=new AudioDAO();

    queue.clear();
    if(CURRENT_AUDIO_PLAYING!=null && CURRENT_AUDIO_PLAYING.getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)){
      CURRENT_AUDIO_PLAYING.getMediaPlayer().dispose();
    }//end-if
    CURRENT_AUDIO_PLAYING=jbAudio;
    adapter.play(jbAudio);
    audioDAO.addToListeningHistory(jbAudio, activeProfile);
  }

  public void play(JBCollection jbCollection){
    queue.clear();
    if(CURRENT_AUDIO_PLAYING!=null && CURRENT_AUDIO_PLAYING.getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)){
      CURRENT_AUDIO_PLAYING.getMediaPlayer().dispose();
    }//end-if
    for(JBAudio jbAudio: jbCollection.getTrackList()){
      queue.push(jbAudio);
    }//end-foreach
    play();
  }

  public void Pause(){
    if(CURRENT_AUDIO_PLAYING!=null){
      CURRENT_AUDIO_PLAYING.getMediaPlayer().pause();
    }//end-if
  }
  /*---------------------------------------*/
}
