package it.unipv.ingsfw.JavaBeats.controller.handler;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionViewGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditPlaylistDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.stage.Stage;

public class CollectionViewController{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private CollectionViewGUI gui;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public CollectionViewController(CollectionViewGUI gui){
    this.gui=gui;
    initComponents();
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(){
    EventHandler<ActionEvent> editButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        gui.getGp().setEffect(new BoxBlur(10, 10, 10));

        Playlist originalCollection=new Playlist(1, "name", new Artist("mail", "name", "username"));
        EditPlaylistDialog dialog=new EditPlaylistDialog(stage, originalCollection, (Playlist)originalCollection.getCopy(originalCollection));
        EditPlaylistDialogController editPlaylistDialogController=new EditPlaylistDialogController(dialog);
        dialog.showAndWait();
        gui.getGp().setEffect(null);

      }
    };
    gui.getCollectionHeader().getEditButton().setOnAction(editButtonHandler);
  }
  /*---------------------------------------*/
}
