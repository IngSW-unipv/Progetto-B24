package it.unipv.ingsfw.JavaBeats.controller.handler.presets;

import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.HomePageHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.primary.search.SearchPageHandler;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.primary.home.HomePageGUI;
import it.unipv.ingsfw.JavaBeats.view.primary.search.SearchPageGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SidebarHandler{

  //Attributi
  private static SidebarHandler instance;
  private JBProfile activeProfile;

  /*---------------------------------------*/
  //Costruttore
  /*---------------------------------------*/
  private SidebarHandler(){
    super();
    initComponents();
  }

  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public static SidebarHandler getInstance(){
    if(instance==null){
      instance=new SidebarHandler();
    }//end-if
    return instance;
  }

  private void initComponents(){
    activeProfile=ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile();
    //HomeButtonHandler
    EventHandler<ActionEvent> homeButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        HomePageGUI homePageGUI=new HomePageGUI(activeProfile);
        HomePageHandler homePageHandler=new HomePageHandler();
        //Sidebar.getInstance().setActive(Sidebar.getInstance().getHomeButton());

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(homePageGUI.getScene());
        stage.setTitle("HomePage");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };
    //SearchButtonHandler
    EventHandler<ActionEvent> searchButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        SearchPageGUI searchPageGUI=new SearchPageGUI(true, activeProfile);
        SearchPageHandler searchPageHandler=new SearchPageHandler();
        //Sidebar.getInstance().setActive(Sidebar.getInstance().getSearchButton());

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(searchPageGUI.getScene());
        stage.setTitle("SearchPage");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };
    Sidebar.getInstance(activeProfile).getHomeButton().setOnAction(homeButtonHandler);
    Sidebar.getInstance(activeProfile).getHomeButton().setOnAction(searchButtonHandler);
  }


}
