package it.unipv.ingsfw.JavaBeats.main;
import it.unipv.ingsfw.JavaBeats.view.primary.home.HomePageGUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
    /*LoginGUI gui=new LoginGUI();
    LoginController loginController=new LoginController(gui);
    loginScene=gui.getScene();*/

    HomePageGUI gui=new HomePageGUI();
    loginScene=gui.getScene();

    launch(args);
  }
  @Override
  public void start(Stage stage){
    stage.setTitle("Login");
    stage.setScene(loginScene);
    stage.sizeToScene();
    stage.setMaximized(true);
    stage.getIcons().add(new Image("it/unipv/ingsfw/JavaBeats/view/icons/Logo.png"));
    stage.show();
  }
  /*---------------------------------------*/
}
