package it.unipv.ingsfw.JavaBeats.controller.handler.library;

import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.model.playable.EJBPLAYABLE;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Podcast;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionLibraryGUI;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionViewGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioCard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

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
        Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();

        JBCollection jbCollection=((JBCollection)((AudioCard)mouseEvent.getSource()).getIjbResearchable());
        CollectionViewGUI collectionViewGUI=new CollectionViewGUI(activeProfile, jbCollection);
        CollectionViewHandler collectionViewHandler=new CollectionViewHandler(collectionViewGUI, activeProfile);

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(collectionViewGUI.getScene());
        stage.setTitle("Collection");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };
    EventHandler<ActionEvent> plusButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        JBCollection newCollection=null;
        switch(collectionLibraryGUI.getEjbplayable()){
          case PLAYLIST:
            newCollection=new Playlist(1, "New playlist", activeProfile);
            break;
          case ALBUM:
            newCollection=new Album(1, "New album", activeProfile, new ArrayList<JBAudio>());
            break;
          case PODCAST:
            newCollection=new Podcast(1, "New podcast", activeProfile, new ArrayList<JBAudio>());
            break;
        }//end-switch
        newCollection=CollectionManagerFactory.getInstance().getCollectionManager().createJBCollection(newCollection);
        ArrayList<JBCollection> jbCollectionArrayList=CollectionManagerFactory.getInstance().getCollectionManager().getCollectionList(activeProfile);

        CollectionLibraryGUI collectionLibraryGUI1=new CollectionLibraryGUI(activeProfile, jbCollectionArrayList, collectionLibraryGUI.getEjbplayable());
        CollectionLibraryHandler collectionLibraryHandler=new CollectionLibraryHandler(activeProfile, collectionLibraryGUI1);

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(collectionLibraryGUI1.getScene());
        stage.setTitle("Collection");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };
    collectionLibraryGUI.getCollectionFlowPane().getChildren().forEach(a -> a.setOnMouseClicked(collectionClickHandler));
    collectionLibraryGUI.getButtonPlus().setOnAction(plusButtonHandler);
  }

}
