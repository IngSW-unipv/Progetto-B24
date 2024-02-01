package it.unipv.ingsfw.JavaBeats.controller.handler.library;

import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.PlayerManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.HomePageHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SidebarHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SongbarHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.primary.search.SearchPageHandler;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionLibraryGUI;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionViewGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditPlaylistDialog;
import it.unipv.ingsfw.JavaBeats.view.primary.home.HomePageGUI;
import it.unipv.ingsfw.JavaBeats.view.primary.search.SearchPageGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

public class CollectionViewHandler{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private CollectionViewGUI gui;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public CollectionViewHandler(CollectionViewGUI gui, JBProfile activeProfile, JBAudio currentAudio){
    this.gui=gui;
    initComponents(activeProfile, currentAudio);
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(JBProfile activeProfile, JBAudio currentAudio){
    EventHandler<ActionEvent> editButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        gui.getGp().setEffect(new BoxBlur(10, 10, 10));

        Playlist p=(Playlist)gui.getJbCollection();
        EditPlaylistDialog dialog=new EditPlaylistDialog(stage, p, (Playlist)p.getCopy());
        EditPlaylistDialogController editPlaylistDialogController=new EditPlaylistDialogController(dialog);
        dialog.showAndWait();
        gui.getGp().setEffect(null);
      }
    };
    EventHandler<ActionEvent> playPauseCollectionButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        PlayerManagerFactory.getInstance().getPlayerManager().play(gui.getJbCollection());
        stage.setScene(gui.update(activeProfile, PlayerManagerFactory.getInstance().getPlayerManager().getCurrentAudioPlaying()));

      }
    };

    EventHandler<ActionEvent> binButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        CollectionManagerFactory.getInstance().getCollectionManager().removeCollection(gui.getJbCollection());
        HomePageGUI homePageGUI=new HomePageGUI(activeProfile, currentAudio);
        HomePageHandler homePageHandler=new HomePageHandler(homePageGUI, activeProfile, currentAudio);
        Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getHomeButton());

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(homePageGUI.getScene());
        stage.setTitle("HomePage");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };
    EventHandler<MouseEvent> tableViewClickHandler=new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent mouseEvent){
        Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();

        if(mouseEvent.getButton()==MouseButton.PRIMARY){
          Node node=mouseEvent.getPickResult().getIntersectedNode();

          // go up in node hierarchy until a cell is found, or we can be sure no cell was clicked
          boolean foundPlayButton=false;
          boolean foundIsFavoriteButton=false;
          while(node!=gui.getAudioTable() && !foundPlayButton && !foundIsFavoriteButton){
            String id=node.getId();
            if(id!=null && id.equals("PlayButton")){
              foundPlayButton=true;
              System.out.println("ButtonClicked");
            }//end-if

            node=node.getParent();
          }//end-while

          if(foundPlayButton){
            JBAudio audioClicked=gui.getAudioTable().getItems().get(gui.getAudioTable().getSelectionModel().getSelectedIndex());

            PlayerManagerFactory.getInstance().getPlayerManager().play(audioClicked);
            stage.setScene(gui.update(activeProfile, PlayerManagerFactory.getInstance().getPlayerManager().getCurrentAudioPlaying()));

//            SongbarHandler.getInstance(activeProfile, PlayerManagerFactory.getInstance().getPlayerManager().getCurrentAudioPlaying());
//            SidebarHandler.getInstance(activeProfile, PlayerManagerFactory.getInstance().getPlayerManager().getCurrentAudioPlaying());
          }else if(foundIsFavoriteButton){

          }//end-if

        }//end-if
      }
    };
    gui.getCollectionHeader().getEditButton().setOnAction(editButtonHandler);
    gui.getCollectionHeader().getButtonPlayPause().setOnAction(playPauseCollectionButtonHandler);
    gui.getAudioTable().setOnMouseClicked(tableViewClickHandler);
    gui.getCollectionHeader().getButtonBin().setOnAction(binButtonHandler);
  }
  /*---------------------------------------*/
}
