package it.unipv.ingsfw.JavaBeats.controller.handler;

import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SidebarHandler;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import it.unipv.ingsfw.JavaBeats.view.primary.home.HomePageGUI;
import javafx.geometry.Side;

public class HomePageHandler{

  //Attributi
  HomePageGUI homePageGUI;

  //Getters and setters

  //Costruttore
  public HomePageHandler(HomePageGUI homePageGUI, JBProfile activeProfile){
    this.homePageGUI=homePageGUI;
    initComponents(activeProfile);

  }

  private void initComponents(JBProfile activeProfile){
    Sidebar.getInstance(activeProfile);
    SidebarHandler.getInstance(activeProfile);
    Songbar.getInstance(activeProfile);

  }

}
