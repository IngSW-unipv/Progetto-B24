package it.unipv.ingsfw.JavaBeats.controller.handler.library;
import it.unipv.ingsfw.JavaBeats.controller.factory.PlayerManagerFactory;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionViewGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditPlaylistDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.stage.Stage;

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

        PlayerManagerFactory.getInstance().getPlayerManager().addToQueue(gui.getJbCollection());
        PlayerManagerFactory.getInstance().getPlayerManager().play();
      }
    };
    gui.getCollectionHeader().getEditButton().setOnAction(editButtonHandler);
    gui.getCollectionHeader().getButtonPlayPause().setOnAction(playPauseCollectionButtonHandler);
  }
  /*---------------------------------------*/
}
