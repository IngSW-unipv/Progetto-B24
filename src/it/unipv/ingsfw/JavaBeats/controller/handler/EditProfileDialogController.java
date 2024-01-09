package it.unipv.ingsfw.JavaBeats.controller.handler;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditPlaylistDialog;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditProfileDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
public class EditProfileDialogController{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private EditProfileDialog gui;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public EditProfileDialogController(EditProfileDialog gui){
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
    EventHandler<ActionEvent> inputImageButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG file", "*png"));
        File f=fileChooser.showOpenDialog(stage);
        byte[] fileContent=new byte[(int)f.length()];
        FileInputStream fileInputStream=null;
        URL url=null;
        try{
          url=f.toURI().toURL();
          fileInputStream=new FileInputStream(f);
          fileInputStream.read(fileContent);
          fileInputStream.close();
          gui.getNewProfile().setProfilePicture(new SerialBlob(fileContent));
          gui.getCollectionImageView().setImage(new Image(url.toExternalForm(), true));
        }catch(IOException | SQLException e){
          throw new RuntimeException(e);
        }//end-try
      }
    };
    EventHandler<ActionEvent> saveButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        System.out.println("save clicked");
      }
    };
    EventHandler<ActionEvent> cancelButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        System.out.println("cancel clicked");
      }
    };
    gui.getInputImageButton().setOnAction(inputImageButtonHandler);
    gui.getSaveButton().setOnAction(saveButtonHandler);
    gui.getCancelButton().setOnAction(cancelButtonHandler);
  }
  /*---------------------------------------*/
}
