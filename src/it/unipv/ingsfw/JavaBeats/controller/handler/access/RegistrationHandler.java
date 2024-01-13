package it.unipv.ingsfw.JavaBeats.controller.handler.access;
import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.HomePageHandler;
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

import java.util.StringTokenizer;

public class RegistrationHandler {
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private RegistrationGUI gui;
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
    EventHandler<ActionEvent> loginButtonHandler=new EventHandler <ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        LoginGUI loginGUI=new LoginGUI();
        LoginHandler loginHandler =new LoginHandler(loginGUI);

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(loginGUI.getScene());
        stage.setTitle("Login");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };
    EventHandler<ActionEvent> registerButtonHandler=new EventHandler <ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){

        StringTokenizer stringTokenizer= new StringTokenizer(gui.getNameSurname().getText());
        String name= new String(stringTokenizer.nextToken());
        String surname= new String(stringTokenizer.nextToken());

        if(!gui.getPassword1().getText().equals(gui.getPassword2().getText())){
          gui.getErrorMessage().setText("Your passwords don't correspond");
        } else {
          JBProfile profile = new User(gui.getUsername().getText(), gui.getMail().getText(), gui.getPassword2().getText(), name, surname);

          //Register the profile exists or handles the exception
          ProfileManagerFactory.getInstance().getProfileManager().registration(profile);

          //Goes to HomePage
          Stage s = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
          HomePageGUI homePageGUI = new HomePageGUI();
          HomePageHandler homePageHandler = new HomePageHandler();

          //Saving previous dimension and using it for the next page
          Dimension2D previousDimension = new Dimension2D(s.getWidth(), s.getHeight());
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
