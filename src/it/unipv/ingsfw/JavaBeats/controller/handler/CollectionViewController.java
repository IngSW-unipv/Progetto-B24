package it.unipv.ingsfw.JavaBeats.controller.handler;
import com.sun.scenario.effect.BoxShadow;
import it.unipv.ingsfw.JavaBeats.model.playable.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.Playlist;
import it.unipv.ingsfw.JavaBeats.model.user.Artist;
import it.unipv.ingsfw.JavaBeats.view.access.LoginGUI;
import it.unipv.ingsfw.JavaBeats.view.access.RegistrationGUI;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionViewGui;
import it.unipv.ingsfw.JavaBeats.view.presets.CollectionDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.BoxBlur;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CollectionViewController{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private CollectionViewGui gui;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public CollectionViewController(CollectionViewGui gui){
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

        CollectionDialog dialog=new CollectionDialog(stage, new Playlist("id", "name", new Artist("mail", "name", "username")));
        DialogController dialogController=new DialogController(dialog);
        dialog.show();
      }
    };
    gui.getCollectionHeader().getEditButton().setOnAction(editButtonHandler);
  }
  /*---------------------------------------*/
}
