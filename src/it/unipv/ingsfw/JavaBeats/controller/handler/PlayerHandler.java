package it.unipv.ingsfw.JavaBeats.controller.handler;
import it.unipv.ingsfw.JavaBeats.controller.factory.PlayerManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.manager.PlayerManager;
import javafx.scene.media.MediaPlayer;

public class PlayerHandler implements Runnable{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  PlayerManager playerManager;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public PlayerHandler(){

  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  @Override
  public void run(){
    /* I let the playerManager figure what to do next */
    PlayerManagerFactory.getInstance().getPlayerManager().play();
  }
  /*---------------------------------------*/
}
