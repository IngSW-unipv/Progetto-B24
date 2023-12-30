package it.unipv.ingsfw.JavaBeats.controller.handler;
import it.unipv.ingsfw.JavaBeats.view.access.LoginGUI;
import it.unipv.ingsfw.JavaBeats.view.access.RegistrationGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
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
