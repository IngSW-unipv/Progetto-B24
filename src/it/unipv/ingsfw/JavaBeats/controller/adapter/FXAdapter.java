package it.unipv.ingsfw.JavaBeats.controller.adapter;

import it.unipv.ingsfw.JavaBeats.model.playable.IJBPlayable;
import javafx.scene.media.MediaPlayer;

public class FXAdapter implements IAdapter{
  public FXAdapter(){
  }

  @Override
  public void play(IJBPlayable ijbPlayable){
    ijbPlayable.playFX();
  }
}
 