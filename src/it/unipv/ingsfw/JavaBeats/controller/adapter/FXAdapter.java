package it.unipv.ingsfw.JavaBeats.controller.adapter;

import it.unipv.ingsfw.JavaBeats.model.playable.IJBPlayable;

public class FXAdapter implements IAdapter{
	public FXAdapter() {
		System.out.println("Costruttore fxadapter");
	}

	@Override
	public void play(IJBPlayable ijbPlayable) {
		ijbPlayable.playFX();
	}
}
 