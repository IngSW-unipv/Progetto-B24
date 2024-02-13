package it.unipv.ingsfw.JavaBeats.controller.handler.primary.profile;

import it.unipv.ingsfw.JavaBeats.controller.factory.PlayerManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SongbarHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.dialogs.EditProfileDialogHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SidebarHandler;
import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.exceptions.SystemErrorException;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditProfileDialog;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.ExceptionDialog;
import it.unipv.ingsfw.JavaBeats.view.primary.profile.ProfileViewGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ProfileViewHandler{
  /*---------------------------------------*/
  //Attributes
  /*---------------------------------------*/
  private ProfileViewGUI gui;

  /*---------------------------------------*/
  //Constructor
  /*---------------------------------------*/
  public ProfileViewHandler(ProfileViewGUI gui, JBProfile originalProfile){
    this.gui=gui;
    initComponents(originalProfile);
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Methods
  /*---------------------------------------*/
  private void initComponents(JBProfile originalProfile){
    EventHandler<ActionEvent> editButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        try{
          gui.getGp().setEffect(new BoxBlur(10, 10, 10));

          EditProfileDialog dialog=new EditProfileDialog(stage, ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile(), ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile().getCopy());

          EditProfileDialogHandler editProfileDialogHandler=new EditProfileDialogHandler(dialog);
          dialog.showAndWait();
          gui.getGp().setEffect(null);

          /* Checking if an edit has been made */
          if(!dialog.getOriginalProfile().equals(dialog.getNewProfile())){

            //Profile manager updates DB
            ProfileManagerFactory.getInstance().getProfileManager().edit(dialog.getNewProfile());

            /* Re-instantiating Sidebar, SidebarHandler, SongBar and SongBarHandler*/
            Sidebar.getInstance(dialog.getNewProfile());
            SidebarHandler sidebarHandler=new SidebarHandler();
            Songbar.getInstance();
            SongbarHandler.getInstance(dialog.getNewProfile(), PlayerManagerFactory.getInstance().getPlayerManager().getCurrentAudioPlaying());

            gui.getProfileHeader().getProfileImageView().setImage(new Image(dialog.getNewProfile().getProfilePicture().getBinaryStream()));
            gui.getProfileHeader().getNameLabel().setText(dialog.getNewProfile().getName());
            gui.getProfileHeader().getSurnameLabel().setText(dialog.getNewProfile().getSurname());
            gui.getProfileHeader().getBiographyText().setText(dialog.getNewProfile().getBiography());
            gui.getProfileHeader().sizeTextArea(gui.getProfileHeader().getBiographyText(), gui.getProfileHeader().getBiographyText().getText());
            gui.getProfileHeader().getUsernameLabel().setText(dialog.getNewProfile().getUsername());
          }//end-if

        }catch(SystemErrorException e){
          gui.getGp().setEffect(new BoxBlur(10, 10, 10));

          ExceptionDialog exceptionDialog=new ExceptionDialog(stage, e);
          exceptionDialog.showAndWait();

          gui.getGp().setEffect(null);
        }catch(SQLException | AccountNotFoundException e){
          gui.getGp().setEffect(new BoxBlur(10, 10, 10));

          ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
          exceptionDialog.showAndWait();

          gui.getGp().setEffect(null);
        }//end-try
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
          SidebarHandler sidebarHandler=new SidebarHandler();
          Songbar.getInstance();
          SongbarHandler.getInstance(switchedProfile, PlayerManagerFactory.getInstance().getPlayerManager().getCurrentAudioPlaying());

          ProfileViewGUI profileViewGUI=new ProfileViewGUI(switchedProfile, switchedProfile);
          ProfileViewHandler profileViewHandler=new ProfileViewHandler(profileViewGUI, switchedProfile);

          Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
          stage.setScene(profileViewGUI.getScene());
          stage.setTitle("Home");
          stage.setWidth(previousDimension.getWidth());
          stage.setHeight(previousDimension.getHeight());
        }catch(AccountNotFoundException e){
          gui.getGp().setEffect(new BoxBlur(10, 10, 10));

          ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
          exceptionDialog.showAndWait();

          gui.getGp().setEffect(null);
        }//end-try
      }
    };
    try{
      gui.getProfileHeader().getEditButton().setOnAction(editButtonHandler);
      gui.getProfileHeader().getSwitchButton().setOnAction(switchProfileTypehandler);
    }catch(NullPointerException ignored){
    }//end-try
  }
  /*---------------------------------------*/
}
