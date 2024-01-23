package it.unipv.ingsfw.JavaBeats.controller.adapter;

import it.unipv.ingsfw.JavaBeats.model.playable.IJBPlayable;
import javafx.scene.media.MediaPlayer;

public class FXAdapter implements IAdapter{
  public FXAdapter(){
    System.out.println("Costruttore fxadapter");
  }

  @Override
  public MediaPlayer play(IJBPlayable ijbPlayable){
    return ijbPlayable.playFX();
  }
}
 