package it.unipv.ingsfw.JavaBeats.controller.handler;
import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SidebarHandler;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.model.profile.User;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditProfileDialog;
import it.unipv.ingsfw.JavaBeats.view.primary.home.HomePageGUI;
import it.unipv.ingsfw.JavaBeats.view.primary.profile.ProfileViewGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
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
  public ProfileViewHandler(ProfileViewGUI gui, JBProfile originalProfile, JBAudio currentAudio){
    this.gui=gui;
    initComponents(originalProfile, currentAudio);
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(JBProfile originalProfile, JBAudio currentAudio){
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
    EventHandler<ActionEvent> switchProfileTypehandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        try{
          JBProfile switchedProfile=ProfileManagerFactory.getInstance().getProfileManager().switchProfileType(originalProfile);
          /* Re-instantiating Sidebar, SidebarHandler, Songbar and SongbarHandler*/
          Sidebar.getInstance(switchedProfile);
          SidebarHandler.getInstance(switchedProfile, currentAudio);
          Songbar.getInstance(switchedProfile, currentAudio);
//          SongbarHandler.getInstance(switchedProfile);

          ProfileViewGUI profileViewGUI=new ProfileViewGUI(switchedProfile, currentAudio, switchedProfile);
          ProfileViewHandler profileViewHandler=new ProfileViewHandler(profileViewGUI, switchedProfile, currentAudio);

          Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
          stage.setScene(profileViewGUI.getScene());
          stage.setTitle("Home");
          stage.setWidth(previousDimension.getWidth());
          stage.setHeight(previousDimension.getHeight());
        }catch(ClassCastException c){
          /* do something */
        }//end-try
      }
    };
    try{
      gui.getProfileHeader().getEditButton().setOnAction(editButtonHandler);
      gui.getProfileHeader().getSwitchButton().setOnAction(switchProfileTypehandler);
    }catch(NullPointerException n){

    }//end-try
  }
  /*---------------------------------------*/
}
