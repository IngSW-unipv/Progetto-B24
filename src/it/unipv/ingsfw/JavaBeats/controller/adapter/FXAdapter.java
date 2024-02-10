package it.unipv.ingsfw.JavaBeats.controller.adapter;

import it.unipv.ingsfw.JavaBeats.model.playable.IJBPlayable;

public class FXAdapter implements IAdapter{
  public FXAdapter(){
  }

  @Override
  public void play(IJBPlayable ijbPlayable){
    ijbPlayable.playFX();
  }

  @Override
  public void pause(IJBPlayable ijbPlayable){
    ijbPlayable.pauseFX();
  }
}
 