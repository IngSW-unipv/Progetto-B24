package it.unipv.ingsfw.JavaBeats.controller.handler.library;

import it.unipv.ingsfw.JavaBeats.model.playable.EJBPLAYABLE;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionLibraryGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioCard;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class CollectionLibraryHandler{

  //Attributi
  private CollectionLibraryGUI collectionLibraryGUI;


  //Costruttore
  public CollectionLibraryHandler(JBProfile activeProfile, CollectionLibraryGUI collectionLibraryGUI){
    this.collectionLibraryGUI=collectionLibraryGUI;
    initComponents(activeProfile);
  }


  //Metodi
  private void initComponents(JBProfile activeProfile){
    EventHandler<MouseEvent> collectionClickHandler=new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent mouseEvent){
        System.out.println(((AudioCard)mouseEvent.getSource()));
      }
    };
    collectionLibraryGUI.getCollectionFlowPane().getChildren().forEach(a -> a.setOnMouseClicked(collectionClickHandler));
  }

}
