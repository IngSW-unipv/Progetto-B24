package it.unipv.ingsfw.JavaBeats.controller.adapter;

import it.unipv.ingsfw.JavaBeats.model.playable.IJBPlayable;
import javafx.scene.media.MediaPlayer;

public interface IAdapter{
  void play(IJBPlayable ijbPlayable);
}
