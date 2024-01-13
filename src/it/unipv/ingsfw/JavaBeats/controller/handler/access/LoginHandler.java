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
public class LoginHandler {
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private LoginGUI gui;
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

        JBProfile profile= new User(gui.getUsername().getText(), gui.getPassword().getText());

        //Checks if the profile exists or handles the exception
        ProfileManagerFactory.getInstance().getProfileManager().login(profile);

        //Login
        Stage s=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        HomePageGUI homePageGUI= new HomePageGUI();
        HomePageHandler homePageHandler = new HomePageHandler();

        //Saving previous dimension and using it for the next page
        Dimension2D previousDimension=new Dimension2D(s.getWidth(), s.getHeight());
        s.setScene(homePageGUI.getScene());
        s.setTitle("HomePage");
        s.setWidth(previousDimension.getWidth());
        s.setHeight(previousDimension.getHeight());

      }
    };
    EventHandler<ActionEvent> registerButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        RegistrationGUI registrationGUI=new RegistrationGUI();
        RegistrationHandler registrationHandler =new RegistrationHandler(registrationGUI);

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
