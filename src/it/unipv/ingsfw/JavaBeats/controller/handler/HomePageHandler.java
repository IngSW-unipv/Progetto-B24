package it.unipv.ingsfw.JavaBeats.controller.handler;

import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SidebarHandler;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import it.unipv.ingsfw.JavaBeats.view.primary.home.HomePageGUI;
import it.unipv.ingsfw.JavaBeats.view.primary.profile.ProfileViewGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.stage.Stage;

public class HomePageHandler{

  //Attributi
  HomePageGUI homePageGUI;

  //Getters and setters

  //Costruttore
  public HomePageHandler(HomePageGUI homePageGUI, JBProfile activeProfile, JBAudio currentAudio){
    this.homePageGUI=homePageGUI;
    initComponents(activeProfile, currentAudio);

  }

  private void initComponents(JBProfile activeProfile, JBAudio currentAudio){
    EventHandler<ActionEvent> profileButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        ProfileViewGUI profileViewGUI=new ProfileViewGUI(activeProfile, currentAudio, activeProfile);
        ProfileViewHandler profileViewHandler=new ProfileViewHandler(profileViewGUI, activeProfile, currentAudio);
        Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getProfileButton());

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(profileViewGUI.getScene());
        stage.setTitle("Profile");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };
    homePageGUI.getHome().getUserProfileButton().setOnAction(profileButtonHandler);
    Sidebar.getInstance(activeProfile);
    SidebarHandler.getInstance(activeProfile, currentAudio);
    Songbar.getInstance(activeProfile, currentAudio);
  }

}
