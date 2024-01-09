package it.unipv.ingsfw.JavaBeats.controller.handler;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.model.profile.User;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditPlaylistDialog;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditProfileDialog;
import it.unipv.ingsfw.JavaBeats.view.primary.profile.ProfileViewGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.Stage;
public class ProfileViewController{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private ProfileViewGUI gui;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public ProfileViewController(ProfileViewGUI gui, JBProfile originalProfile){
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
//        gui.getGp().setEffect(new BoxBlur(10, 10, 10));

        EditProfileDialog dialog=null;
        try{
//          dialog=new EditProfileDialog(stage, originalProfile, (User)originalProfile, (User)originalProfile.getCopy(originalProfile));
          dialog=new EditProfileDialog(stage, (User)originalProfile, new User("us", "mail", "psw"));
        }catch(ClassCastException c){
//          dialog=new EditProfileDialog(stage, originalProfile, (Artist)originalProfile, (Artist)originalProfile.getCopy(originalProfile));
          dialog=new EditProfileDialog(stage, (Artist)originalProfile, new Artist("us", "mail", "psw"));
        }//end-try
        EditProfileDialogController editProfileDialogController=new EditProfileDialogController(dialog);
        dialog.show();
      }
    };
    gui.getProfileHeader().getEditButton().setOnAction(editButtonHandler);
  }
  /*---------------------------------------*/
}
