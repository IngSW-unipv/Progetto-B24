package it.unipv.ingsfw.JavaBeats.controller.handler.presets;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioTable;

public class AudioTableHandler{
  /*---------------------------------------*/
  //Attributes
  /*---------------------------------------*/
  private static AudioTableHandler instance=null;
  private AudioTable currentAudioTableShowing=null;
  private boolean queue=false;
  /*---------------------------------------*/
  //Constructors
  /*---------------------------------------*/
  private AudioTableHandler(){
    super();
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public static AudioTableHandler getInstance(){
    if(instance==null){
      instance=new AudioTableHandler();
    }//end-if
    return instance;
  }
  public void setCurrentAudioTableShowing(AudioTable currentAudioTableShowing){
    this.currentAudioTableShowing=currentAudioTableShowing;
  }
  public AudioTable getCurrentAudioTableShowing(){
    return currentAudioTableShowing;
  }
  public void setQueue(boolean queue){
    this.queue=queue;
  }
  public boolean isQueue(){
    return queue;
  }
  /*---------------------------------------*/
}
