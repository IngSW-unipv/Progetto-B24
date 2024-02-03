package it.unipv.ingsfw.JavaBeats.controller.handler.access;

import it.unipv.ingsfw.JavaBeats.controller.factory.PlayerManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.primary.home.HomePageHandler;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.model.profile.User;
import it.unipv.ingsfw.JavaBeats.view.access.LoginGUI;
import it.unipv.ingsfw.JavaBeats.view.access.RegistrationGUI;
import it.unipv.ingsfw.JavaBeats.view.primary.home.HomePageGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class RegistrationHandler{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private RegistrationGUI gui;
  private JBProfile activeProfile;
  private JBAudio currentAudio;
  private static final String nameRegex=new String("^[A-Z][a-zA-Z0-9_-]{1,50}$");
  private static final String usernameRegex=new String("^[a-zA-Z0-9._+-]{1,30}$");
  private static final String mailRegex=new String("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,100}$");         //mail-allowed characters according to RFC 5322
  private static final String passwordRegex=new String("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,100}$");  //at least: 8 char, one uppercase, one lowercase, one number

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public RegistrationHandler(RegistrationGUI gui){
    this.gui=gui;
    initComponents();
  }

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(){
    activeProfile=ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile();
    currentAudio=PlayerManagerFactory.getInstance().getPlayerManager().getCurrentAudioPlaying();

    EventHandler<ActionEvent> loginButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        LoginGUI loginGUI=new LoginGUI();
        LoginHandler loginHandler=new LoginHandler(loginGUI);

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(loginGUI.getScene());
        stage.setTitle("Login");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };
    EventHandler<ActionEvent> registerButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){

        //Registration Regex:
        if(!(Pattern.matches(nameRegex, gui.getName().getText()) || Pattern.matches(nameRegex, gui.getSurname().getText()))){
          gui.getErrorMessage().setText("Name and Surname fields must start with capital letter.");
        }else if(!(Pattern.matches(mailRegex, gui.getMail().getText()))){
          gui.getErrorMessage().setText("Please enter valid mail.");
        }else if(!(Pattern.matches(usernameRegex, gui.getUsername().getText()))){
          gui.getErrorMessage().setText("Allowed characters in Username are a-z, A-Z, 0-9, ., _, +, -.");
        }else if(!gui.getPassword1().getText().equals(gui.getPassword2().getText())){
          gui.getErrorMessage().setText("Entered passwords do not match.");
        }else if(!(Pattern.matches(passwordRegex, gui.getPassword1().getText()))){
          gui.getErrorMessage().setText("Password must be at least 8 characters long and contain at least one\nlowercase letter, one uppercase letter, one number.");
          gui.getErrorMessage().setAlignment(Pos.CENTER);
        }else{
          gui.getErrorMessage().setText("");

          JBProfile profile=new User(gui.getUsername().getText(), gui.getMail().getText(), gui.getPassword2().getText(), gui.getName().getText(), gui.getSurname().getText());

          //Register the profile exists or handles the exception
          activeProfile=ProfileManagerFactory.getInstance().getProfileManager().registration(profile);

          //Goes to HomePage
          Stage s=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
          HomePageGUI homePageGUI=new HomePageGUI(activeProfile, currentAudio);
          HomePageHandler homePageHandler=new HomePageHandler(homePageGUI, activeProfile, currentAudio);

          //Saving previous dimension and using it for the next page
          Dimension2D previousDimension=new Dimension2D(s.getWidth(), s.getHeight());
          s.setScene(homePageGUI.getScene());
          s.setTitle("HomePage");
          s.setWidth(previousDimension.getWidth());
          s.setHeight(previousDimension.getHeight());
        }
      }
    };
    gui.getLogin().setOnAction(loginButtonHandler);
    gui.getRegister().setOnAction(registerButtonHandler);
  }
  /*---------------------------------------*/
}
