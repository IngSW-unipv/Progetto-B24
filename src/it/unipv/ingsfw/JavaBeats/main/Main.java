package it.unipv.ingsfw.JavaBeats.main;

import it.unipv.ingsfw.JavaBeats.model.playable.EJBPLAYABLE;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionViewGui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static Scene loginScene;

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  public static void main(String[] args){
//    LoginGUI gui=new LoginGUI();
//    LoginController loginController=new LoginController(gui);
//    loginScene=gui.getScene();

//    SearchPageGUI gui=new SearchPageGUI(false);
//    loginScene=gui.getScene();

    CollectionViewGui gui=new CollectionViewGui(EJBPLAYABLE.PLAYLIST);
    loginScene=gui.getScene();

//    HomePageGUI gui=new HomePageGUI();
//    loginScene=gui.getScene();

    launch(args);
  }

  @Override
  public void start(Stage stage){
    stage.setTitle("Home");
    stage.setScene(loginScene);
    stage.sizeToScene();
    stage.setMaximized(true);
    stage.getIcons().add(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Logo.png"));
    stage.initStyle(StageStyle.UNDECORATED);
    stage.show();
  }
  /*---------------------------------------*/
}
