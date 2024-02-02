package it.unipv.ingsfw.JavaBeats.main;

import it.unipv.ingsfw.JavaBeats.controller.handler.access.LoginHandler;
import it.unipv.ingsfw.JavaBeats.model.EJBENTITY;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.view.access.LoginGUI;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionLibraryGUI;
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
    LoginGUI gui=new LoginGUI();
    LoginHandler loginHandler =new LoginHandler(gui);
    loginScene=gui.getScene();

//    SearchPageGUI gui=new SearchPageGUI(false);
//    loginScene=gui.getScene();
////
//    CreationGUI gui=new CreationGUI(EJBPLAYABLE.ALBUM);
//    loginScene=gui.getScene();
//    Artist a= new Artist("i", "ii", "iii");
//    CollectionLibraryGUI gui=new CollectionLibraryGUI(EJBPLAYABLE.PLAYLIST, a);
//    loginScene=gui.getScene();
//


//    Artist b= new Artist("i", "ii", "iii");
//
//
//    CollectionViewGUI gui=new CollectionViewGUI(EJBPLAYABLE.PLAYLIST, a, new Playlist(10, "ii",b));
//    CollectionViewController controller= new CollectionViewController(gui);
//    loginScene=gui.getScene();

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
