package it.unipv.ingsfw.JavaBeats.controller.handler.primary.search;

import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.SearchManagerFactory;
import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.primary.search.SearchPageGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SearchPageHandler{

  //Attributi
  private SearchPageGUI searchPageGUI;
  private JBProfile activeProfile;
  private JBAudio currentAudio;

  //Getters and setters

  //Costruttore
  public SearchPageHandler(SearchPageGUI searchPageGUI, JBProfile activeProfile, JBAudio currentAudio){
    this.searchPageGUI=searchPageGUI;
    this.activeProfile=activeProfile;
    this.currentAudio=currentAudio;
    initComponents();
  }


  //Metodi

  private void initComponents(){

    EventHandler<KeyEvent> searchTextfieldHandler=new EventHandler<KeyEvent>(){

      @Override
      public void handle(KeyEvent keyEvent){

        if(keyEvent.getCode().equals(KeyCode.ENTER)){

          Stage stage=(Stage)((Node)keyEvent.getSource()).getScene().getWindow();

          //PlayerManager returns array of searched arrays
          ArrayList<ArrayList<IJBResearchable>> searchedList=SearchManagerFactory.getInstance().getSearchManager().search(searchPageGUI.getSearchTextField().getText(), activeProfile);
          ArrayList<JBCollection> profilePlaylists=CollectionManagerFactory.getInstance().getCollectionManager().getPlaylists(activeProfile);
          SearchPageGUI searchPageGUI=new SearchPageGUI(activeProfile, currentAudio, searchedList, profilePlaylists);
          SearchPageHandler searchPageHandler=new SearchPageHandler(searchPageGUI, activeProfile, currentAudio);

          Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
          stage.setScene(searchPageGUI.getScene());
          stage.setTitle("SearchPage");
          stage.setWidth(previousDimension.getWidth());
          stage.setHeight(previousDimension.getHeight());
        }


      }

    };

    EventHandler<ActionEvent> choiceBoxHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        System.out.println(actionEvent.getSource());
      }
    };

    searchPageGUI.getSearchTextField().setOnKeyPressed(searchTextfieldHandler);
    try{
      searchPageGUI.getSearchResults().getChoiceBoxArrayList().getFirst().setOnAction(choiceBoxHandler);
    }catch(NullPointerException n){

    }

  }


}
