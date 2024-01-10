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
  private EditProfileDialog profileDialog;
  private byte[] fileContent;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public EditProfileDialogController(EditProfileDialog profileDialog){
    this.profileDialog=profileDialog;
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
        fileContent=new byte[(int)f.length()];
        FileInputStream fileInputStream=null;
        URL url=null;
        try{
          url=f.toURI().toURL();
          fileInputStream=new FileInputStream(f);
          fileInputStream.read(fileContent);
          fileInputStream.close();
          profileDialog.getProfileImageView().setImage(new Image(url.toExternalForm(), true));
        }catch(IOException e){
          throw new RuntimeException(e);
        }//end-try
      }
    };
    EventHandler<ActionEvent> saveButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        try{
          profileDialog.getNewProfile().setProfilePicture(new SerialBlob(fileContent));
        }catch(SQLException e){
          throw new RuntimeException(e);
        }//end-try
        profileDialog.getNewProfile().setBiography(profileDialog.getBiography().getText());
        profileDialog.getNewProfile().setName(profileDialog.getNameTextField().getText());
        profileDialog.getNewProfile().setSurname(profileDialog.getSurnameTextField().getText());
        /* Here we need to check whether the username is already present, for we assume is not */
        profileDialog.getNewProfile().setUsername(profileDialog.getUsernameTextField().getText());
      }
    };
    EventHandler<ActionEvent> cancelButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){

      }
    };
    profileDialog.getInputImageButton().setOnAction(inputImageButtonHandler);
    profileDialog.getSaveButton().setOnAction(saveButtonHandler);
    profileDialog.getCancelButton().setOnAction(cancelButtonHandler);
  }
  /*---------------------------------------*/
}
