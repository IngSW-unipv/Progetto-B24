package it.unipv.ingsfw.JavaBeats.controller.handler;
import it.unipv.ingsfw.JavaBeats.controller.factory.PlayerManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.manager.PlayerManager;
import javafx.scene.media.MediaPlayer;
public class PlayerHandler implements Runnable{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  MediaPlayer mediaPlayer;
  PlayerManager playerManager;
  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public PlayerHandler(MediaPlayer mediaPlayer){
    this.mediaPlayer=mediaPlayer;
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  @Override
  public void run(){
    playerManager=PlayerManagerFactory.getInstance().getPlayerManager();
    System.out.println("è finita la canzone: "+(String)mediaPlayer.getMedia().getMetadata().get("title"));
    playerManager.play();
  }
  /*---------------------------------------*/
}
