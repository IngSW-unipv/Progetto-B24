package it.unipv.ingsfw.JavaBeats.controller.handler;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.model.profile.User;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditProfileDialog;
import it.unipv.ingsfw.JavaBeats.view.primary.profile.ProfileViewGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ProfileViewHandler{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private ProfileViewGUI gui;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public ProfileViewHandler(ProfileViewGUI gui, JBProfile originalProfile){
    this.gui=gui;
    initComponents(originalProfile);
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(JBProfile originalProfile){
    EventHandler<ActionEvent> editButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        gui.getGp().setEffect(new BoxBlur(10, 10, 10));

        EditProfileDialog dialog=new EditProfileDialog(stage, originalProfile, originalProfile.getCopy());
        EditProfileDialogController editProfileDialogController=new EditProfileDialogController(dialog);
        dialog.showAndWait();
        gui.getGp().setEffect(null); /* Removing blur effect */

        /* Checking if an edit has been made */
        if(!dialog.getOriginalProfile().equals(dialog.getNewProfile())){
          try{
            gui.getProfileHeader().getProfileImageView().setImage(new Image(dialog.getNewProfile().getProfilePicture().getBinaryStream()));
          }catch(SQLException e){
            throw new RuntimeException(e);
          }//end-try
          gui.getProfileHeader().getNameLabel().setText(dialog.getNewProfile().getName());
          gui.getProfileHeader().getSurnameLabel().setText(dialog.getNewProfile().getSurname());
          gui.getProfileHeader().getBiographyText().setText(dialog.getNewProfile().getBiography());
          gui.getProfileHeader().sizeTextArea(gui.getProfileHeader().getBiographyText(), gui.getProfileHeader().getBiographyText().getText());
          gui.getProfileHeader().getUsernameLabel().setText(dialog.getNewProfile().getUsername());
        }//end-if
      }
    };
    try{
      gui.getProfileHeader().getEditButton().setOnAction(editButtonHandler);
    }catch(NullPointerException n){

    }//end-try
  }
  /*---------------------------------------*/
}
