package it.unipv.ingsfw.JavaBeats.controller.manager;

import it.unipv.ingsfw.JavaBeats.controller.adapter.FXAdapter;
import it.unipv.ingsfw.JavaBeats.controller.factory.FXAdapterFactory;
import it.unipv.ingsfw.JavaBeats.model.playable.IJBPlayable;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;

public class PlayerManager {

   //Attributi
    FXAdapter adapter=FXAdapterFactory.getInstance().getFXAdapter();




    //Metodi
    public void play(IJBPlayable ijbPlayable){

        adapter.play(ijbPlayable);
    }
	
	

}
