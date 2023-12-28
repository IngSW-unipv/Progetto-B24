package it.unipv.ingsfw.JavaBeats.controller.handler;

import it.unipv.ingsfw.JavaBeats.view.LoginGUI;
import it.unipv.ingsfw.JavaBeats.view.RegistrationGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.Stage;

public class LoginController{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private LoginGUI gui;
  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public LoginController(LoginGUI gui){
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
        /*
        Profile manager fa query
         */
      }
    };
    EventHandler<ActionEvent> registerButtonHandler=new EventHandler <ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        RegistrationGUI registrationGUI=new RegistrationGUI();
        RegistrationController registrationController=new RegistrationController(registrationGUI);
        stage.setScene(registrationGUI.getScene());
        stage.setTitle("Registration");
        stage.setHeight(1080);
        stage.setWidth(1920);
      }
    };
    gui.getLogin().setOnAction(loginButtonHandler);
    gui.getRegister().setOnAction(registerButtonHandler);
  }
  /*---------------------------------------*/
}
