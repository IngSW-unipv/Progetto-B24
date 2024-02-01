package it.unipv.ingsfw.JavaBeats.controller.handler.access;
import it.unipv.ingsfw.JavaBeats.controller.factory.PlayerManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.HomePageHandler;
import it.unipv.ingsfw.JavaBeats.controller.manager.ProfileManager;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.model.profile.User;
import it.unipv.ingsfw.JavaBeats.view.access.LoginGUI;
import it.unipv.ingsfw.JavaBeats.view.access.RegistrationGUI;
import it.unipv.ingsfw.JavaBeats.view.primary.home.HomePageGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class LoginHandler{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private LoginGUI gui;
  private JBProfile activeProfile;
  private JBAudio currentAudio;
  private static final String usernameRegex=new String("^[a-zA-Z0-9._+-]{1,30}$");
  private static final String mailRegex=new String("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,100}$");         //mail-allowed characters according to RFC 5322
  private static final String passwordRegex=new String("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,100}$");  //at least: 8 char, one uppercase, one lowercase, one number

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public LoginHandler(LoginGUI gui){
    this.gui=gui;
    initComponents();
  }

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(){
    EventHandler<ActionEvent> loginButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage s=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        //Login Regex:
        if(!(Pattern.matches(mailRegex, gui.getUsername().getText()) || Pattern.matches(usernameRegex, gui.getUsername().getText()))){
          gui.getErrorMessage().setText("Please enter valid mail or username.");
        }else if(!(Pattern.matches(passwordRegex, gui.getPassword().getText()))){
          gui.getErrorMessage().setText("Please enter valid password.");
        }else{
          gui.getErrorMessage().setText("");

          JBProfile profile=new User(gui.getUsername().getText(), gui.getUsername().getText(), gui.getPassword().getText());

          //Checks if the profile exists or handles the exception
          try{
            activeProfile=ProfileManagerFactory.getInstance().getProfileManager().login(profile);
            currentAudio=PlayerManagerFactory.getInstance().getPlayerManager().getCurrentAudioPlaying();
            //Login
            HomePageGUI homePageGUI=new HomePageGUI(activeProfile, currentAudio);
            HomePageHandler homePageHandler=new HomePageHandler(homePageGUI, activeProfile, currentAudio);

            //Saving previous dimension and using it for the next page
            Dimension2D previousDimension=new Dimension2D(s.getWidth(), s.getHeight());
            s.setScene(homePageGUI.getScene());
            s.setTitle("HomePage");
            s.setWidth(previousDimension.getWidth());
            s.setHeight(previousDimension.getHeight());
          }catch(IllegalArgumentException e){
            gui.getErrorMessage().setText("Login Error!");
          }//end-try
        }
      }
    };
    EventHandler<ActionEvent> registerButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        RegistrationGUI registrationGUI=new RegistrationGUI();
        RegistrationHandler registrationHandler=new RegistrationHandler(registrationGUI);

        //Saving previous dimension and using it for the next page
        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(registrationGUI.getScene());
        stage.setTitle("Registration");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };
    gui.getLogin().setOnAction(loginButtonHandler);
    gui.getRegister().setOnAction(registerButtonHandler);
  }
  /*---------------------------------------*/
}
