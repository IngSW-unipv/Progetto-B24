package it.unipv.ingsfw.JavaBeats.controller.handler.library;
import it.unipv.ingsfw.JavaBeats.controller.factory.PlayerManagerFactory;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionViewGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditPlaylistDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
  public CollectionViewHandler(CollectionViewGUI gui, JBProfile activeProfile){
    this.gui=gui;
    initComponents(activeProfile);
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(JBProfile activeProfile){
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
      }
    };
    EventHandler<MouseEvent> tableViewClickHandler=new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent mouseEvent){
        if(mouseEvent.getButton()==MouseButton.PRIMARY){
          Node node=mouseEvent.getPickResult().getIntersectedNode();
          JBAudio audioClicked=gui.getAudioTable().getItems().get(gui.getAudioTable().getSelectionModel().getSelectedIndex());

          // go up in node hierarchy until a cell is found, or we can be sure no cell was clicked
          boolean foundPlayButton=false;
          boolean fuondIsFavoriteButton=false;
          while(node!=gui.getAudioTable() && !foundPlayButton && !fuondIsFavoriteButton){
            String id=node.getId();
            if(id!=null && id.equals("PlayButton")){
              foundPlayButton=true;
              System.out.println("ButtonClicked");
            }//end-if

            node=node.getParent();
          }//end-while

          if(foundPlayButton){
            PlayerManagerFactory.getInstance().getPlayerManager().play(audioClicked);
          }else if(fuondIsFavoriteButton){

          }//end-if

        }//end-if
      }
    };
    gui.getCollectionHeader().getEditButton().setOnAction(editButtonHandler);
    gui.getCollectionHeader().getButtonPlayPause().setOnAction(playPauseCollectionButtonHandler);
    gui.getAudioTable().setOnMouseClicked(tableViewClickHandler);
  }
  /*---------------------------------------*/
}
