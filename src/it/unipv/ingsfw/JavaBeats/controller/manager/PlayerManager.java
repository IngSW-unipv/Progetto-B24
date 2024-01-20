package it.unipv.ingsfw.JavaBeats.controller.manager;
import it.unipv.ingsfw.JavaBeats.controller.adapter.FXAdapter;
import it.unipv.ingsfw.JavaBeats.controller.factory.FXAdapterFactory;
import it.unipv.ingsfw.JavaBeats.model.playable.IJBPlayable;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.JBCollection;

import java.util.LinkedList;

public class PlayerManager{
  //Attributi
  private static LinkedList<JBAudio> queue=new LinkedList<>();
  private FXAdapter adapter=FXAdapterFactory.getInstance().getFXAdapter();

  public PlayerManager(){
  }

  //Metodi
  public void addToQueue(IJBPlayable ijbPlayable){
    try{
      JBCollection jbCollection=(JBCollection)ijbPlayable;

      for(JBAudio jbAudio: jbCollection.getTrackList()){
        queue.push(jbAudio);
      }//end-foreach
    }catch(ClassCastException c){
      queue.push((JBAudio)ijbPlayable);
    }//end-try
  }

  public void play(){
    adapter.play(queue.pop());
  }

  public void play(JBAudio jbAudio){
    adapter.play(jbAudio);
  }
}
