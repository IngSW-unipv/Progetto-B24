package it.unipv.ingsfw.JavaBeats.controller.handler.presets;

import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.library.CollectionLibraryHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.library.CollectionViewHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.HomePageHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.ProfileViewHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.primary.search.SearchPageHandler;
import it.unipv.ingsfw.JavaBeats.model.playable.EJBPLAYABLE;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionLibraryGUI;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionViewGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.primary.home.HomePageGUI;
import it.unipv.ingsfw.JavaBeats.view.primary.profile.ProfileViewGUI;
import it.unipv.ingsfw.JavaBeats.view.primary.search.SearchPageGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SidebarHandler{

  //Attributi
  private static SidebarHandler instance;
  private JBProfile activeProfile;

  /*---------------------------------------*/
  //Costruttore
  /*---------------------------------------*/
  private SidebarHandler(){
    super();
    Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getHomeButton());
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
        HomePageHandler homePageHandler=new HomePageHandler(homePageGUI, activeProfile);
        Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getHomeButton());

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
        Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getSearchButton());

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(searchPageGUI.getScene());
        stage.setTitle("SearchPage");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };
    //ProfileButtonHandler
    EventHandler<ActionEvent> profileButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        ProfileViewGUI profileViewGUI=new ProfileViewGUI(activeProfile, activeProfile);
        ProfileViewHandler profileViewHandler=new ProfileViewHandler(profileViewGUI, activeProfile);
        Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getProfileButton());

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(profileViewGUI.getScene());
        stage.setTitle("Profile");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };

    //FavoritesButtonHandler
    EventHandler<ActionEvent> favoritesButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Playlist favorites=CollectionManagerFactory.getInstance().getCollectionManager().getFavorites(activeProfile);
        CollectionViewGUI collectionViewGUI=new CollectionViewGUI(activeProfile, favorites);
        CollectionViewHandler collectionViewHandler=new CollectionViewHandler(collectionViewGUI, activeProfile);
        Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getFavoritesButton());

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(collectionViewGUI.getScene());
        stage.setTitle("Favorites");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };

    //FavoritesButtonHandler
    EventHandler<ActionEvent> playlistsButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        ArrayList<JBCollection> playlists=new ArrayList<>();
        playlists.add(new Playlist(1, "really long", new Artist("username", "mail", "pass")));
        BufferedImage bufferedImage=null;
        try{
          bufferedImage=ImageIO.read(new File("src/it/unipv/ingsfw/JavaBeats/view/resources/icons/Record.png"));
          ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
          ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
          byte[] image=byteArrayOutputStream.toByteArray();
          playlists.getFirst().setPicture(new SerialBlob(image));
        }catch(IOException | SQLException e){
          throw new RuntimeException(e);
        }//end-try
        CollectionLibraryGUI collectionLibraryGUI=new CollectionLibraryGUI(activeProfile, playlists, EJBPLAYABLE.PLAYLIST);
        CollectionLibraryHandler collectionLibraryHandler=new CollectionLibraryHandler(activeProfile, collectionLibraryGUI);
        Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getPlaylistsButton());

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(collectionLibraryGUI.getScene());
        stage.setTitle("Playlists");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };

    Sidebar.getInstance(activeProfile).getHomeButton().setOnAction(homeButtonHandler);
    Sidebar.getInstance(activeProfile).getSearchButton().setOnAction(searchButtonHandler);
    Sidebar.getInstance(activeProfile).getProfileButton().setOnAction(profileButtonHandler);
    Sidebar.getInstance(activeProfile).getFavoritesButton().setOnAction(favoritesButtonHandler);
    Sidebar.getInstance(activeProfile).getPlaylistsButton().setOnAction(playlistsButtonHandler);


  }


}
