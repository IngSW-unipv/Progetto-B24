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
  private static MediaPlayer CURRENT_MEDIA_PLAYER=null;
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

  /*---------------------------------------*/
  //Methods
  /*---------------------------------------*/
  public void addToQueue(JBAudio ijbPlayable){
    queue.push(ijbPlayable);
  }

  public void play(){
    if(!queue.isEmpty()){
      AudioDAO audioDAO=new AudioDAO();

      JBAudio audioToBePlayed=queue.pop();
      CURRENT_MEDIA_PLAYER=adapter.play(audioToBePlayed);
      audioDAO.addToListeningHistory(audioToBePlayed, activeProfile);
    }//end-if
  }

  public void play(JBAudio jbAudio){
    AudioDAO audioDAO=new AudioDAO();

    queue.clear();
    CURRENT_MEDIA_PLAYER=adapter.play(jbAudio);
    audioDAO.addToListeningHistory(jbAudio, activeProfile);
  }

  public void play(JBCollection jbCollection){
    queue.clear();
    for(JBAudio jbAudio: jbCollection.getTrackList()){
      queue.push(jbAudio);
    }//end-foreach
    play();
  }

  public void Pause(){
    if(CURRENT_MEDIA_PLAYER!=null){
      CURRENT_MEDIA_PLAYER.pause();
    }//end-if
  }
  /*---------------------------------------*/
}
