package it.unipv.ingsfw.JavaBeats.controller.handler.presets;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioTable;

public class AudioTableHandler{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static AudioTableHandler instance=null;
  public static AudioTable CURRENT_AUDIOTABLE_SHOWING=null;
  public static boolean queue=false;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  private AudioTableHandler(AudioTable currentAudioTable){
    super();
    AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING=currentAudioTable;
  }

  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public static AudioTableHandler getInstance(AudioTable currentAudioTable){
    if(instance==null){
      instance=new AudioTableHandler(currentAudioTable);
    }else if(!AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING.equals(currentAudioTable)){
      instance=new AudioTableHandler(currentAudioTable);
    }//end-if
    return instance;
  }
  public static void setQueue(boolean queue){
    AudioTableHandler.queue=queue;
  }
  public static boolean isQueue(){
    return queue;
  }
  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/

  /*---------------------------------------*/
}
