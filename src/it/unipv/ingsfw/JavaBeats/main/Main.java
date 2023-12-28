package it.unipv.ingsfw.JavaBeats.main;

import it.unipv.ingsfw.JavaBeats.controller.handler.LoginController;
import it.unipv.ingsfw.JavaBeats.view.LoginGUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static Scene loginScene;
  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  public static void main(String[] args){
    LoginGUI gui=new LoginGUI();
    LoginController loginController=new LoginController(gui);
    loginScene=gui.getScene();

    launch(args);
  }
  @Override
  public void start(Stage stage){
    stage.setTitle("Login");
    stage.setScene(loginScene);
    stage.sizeToScene();
    stage.setMaximized(true);
    stage.show();
  }
  /*---------------------------------------*/
}
