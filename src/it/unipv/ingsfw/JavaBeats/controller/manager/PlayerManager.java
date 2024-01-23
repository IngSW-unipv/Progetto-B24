package it.unipv.ingsfw.JavaBeats.controller.manager;
import it.unipv.ingsfw.JavaBeats.controller.adapter.FXAdapter;
import it.unipv.ingsfw.JavaBeats.controller.factory.FXAdapterFactory;
import it.unipv.ingsfw.JavaBeats.model.playable.IJBPlayable;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import javafx.scene.media.MediaPlayer;

import java.util.LinkedList;

public class PlayerManager{
  //Attributi
  public static MediaPlayer CURRENT_MEDIA_PLAYER=null;
  private static LinkedList<JBAudio> queue=new LinkedList<>();
  private FXAdapter adapter=FXAdapterFactory.getInstance().getFXAdapter();

  public PlayerManager(){
  }

  //Metodi
  public void addToQueue(JBCollection jbCollection){
    for(JBAudio jbAudio: jbCollection.getTrackList()){
      queue.push(jbAudio);
    }//end-foreach
  }

  public void addToQueue(JBAudio ijbPlayable){
    queue.push(ijbPlayable);
  }

  public void play(){
    adapter.play(queue.pop());
  }

  public void play(JBAudio jbAudio){
    adapter.play(jbAudio);
  }
}
