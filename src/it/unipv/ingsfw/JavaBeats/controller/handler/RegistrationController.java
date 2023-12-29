package it.unipv.ingsfw.JavaBeats.controller.handler;
import it.unipv.ingsfw.JavaBeats.view.LoginGUI;
import it.unipv.ingsfw.JavaBeats.view.RegistrationGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.Screen;
import javafx.stage.Stage;
public class RegistrationController{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private RegistrationGUI gui;
  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public RegistrationController(RegistrationGUI gui){
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
        LoginController loginController=new LoginController(loginGUI);
        stage.setScene(loginGUI.getScene());
        stage.setTitle("Login");
        stage.setWidth(Screen.getPrimary().getBounds().getWidth());
        stage.setHeight(Screen.getPrimary().getBounds().getHeight());
      }
    };
    EventHandler<ActionEvent> registerButtonHandler=new EventHandler <ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        /*
        Profile Manager fa query
         */
      }
    };
    gui.getLogin().setOnAction(loginButtonHandler);
    gui.getRegister().setOnAction(registerButtonHandler);
  }
  /*---------------------------------------*/
}
