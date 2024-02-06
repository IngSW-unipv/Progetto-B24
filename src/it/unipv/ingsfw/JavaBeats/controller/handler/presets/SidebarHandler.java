package it.unipv.ingsfw.JavaBeats.controller.handler.presets;

import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.PlayerManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.library.CollectionLibraryHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.library.CollectionViewHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.primary.home.HomePageHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.primary.profile.ProfileViewHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.primary.search.SearchPageHandler;
import it.unipv.ingsfw.JavaBeats.model.EJBENTITY;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionLibraryGUI;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionViewGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioTable;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.primary.home.HomePageGUI;
import it.unipv.ingsfw.JavaBeats.view.primary.profile.ProfileViewGUI;
import it.unipv.ingsfw.JavaBeats.view.primary.search.SearchPageGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Queue;

public class SidebarHandler{

  //Attributi
  private static SidebarHandler instance;
  private static JBProfile activeProfile=null;

  /*---------------------------------------*/
  //Costruttore
  /*---------------------------------------*/
  private SidebarHandler(JBProfile activeProfile){
    super();
    SidebarHandler.activeProfile=activeProfile;

    initComponents(activeProfile);
  }

  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public static SidebarHandler getInstance(JBProfile activeProfile){
    if(instance==null){
      instance=new SidebarHandler(activeProfile);
    }else if(!SidebarHandler.activeProfile.equals(activeProfile)){
      instance=new SidebarHandler(activeProfile);
    }//end-if
    return instance;
  }

  private void initComponents(JBProfile activeProfile){
    //HomeButtonHandler
    EventHandler<ActionEvent> homeButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        HomePageGUI homePageGUI=new HomePageGUI(activeProfile);
        HomePageHandler homePageHandler=new HomePageHandler(homePageGUI, activeProfile);
        AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING=null;
        AudioTableHandler.setQueue(false);
        Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getHomeButton());

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(homePageGUI.getScene());
        stage.setTitle("HomePage");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };
    //SearchButtonHandler
    EventHandler<ActionEvent> searchButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        SearchPageGUI searchPageGUI=new SearchPageGUI(activeProfile, null, null);
        SearchPageHandler searchPageHandler=new SearchPageHandler(searchPageGUI, activeProfile);
        AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING=null;
        AudioTableHandler.setQueue(false);
        Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getSearchButton());

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(searchPageGUI.getScene());
        stage.setTitle("SearchPage");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };
    //ProfileButtonHandler
    EventHandler<ActionEvent> profileButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        ProfileViewGUI profileViewGUI=new ProfileViewGUI(activeProfile, activeProfile);
        ProfileViewHandler profileViewHandler=new ProfileViewHandler(profileViewGUI, activeProfile);
        AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING=null;
        AudioTableHandler.setQueue(false);
        Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getProfileButton());

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(profileViewGUI.getScene());
        stage.setTitle("Profile");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };

    //QueueButtonHandler
    EventHandler<ActionEvent> queueButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Queue<JBAudio> queue=PlayerManagerFactory.getInstance().getPlayerManager().getQueue();

        CollectionViewGUI collectionViewGUI=new CollectionViewGUI(activeProfile, queue);
        CollectionViewHandler collectionViewHandler=new CollectionViewHandler(collectionViewGUI, activeProfile);
        AudioTableHandler.getInstance((AudioTable)collectionViewGUI.getAudioTable());
        AudioTableHandler.setQueue(true);
        Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getQueueButton());

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(collectionViewGUI.getScene());
        stage.setTitle("Favorites");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };

    //FavoritesButtonHandler
    EventHandler<ActionEvent> favoritesButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        CollectionViewGUI collectionViewGUI=new CollectionViewGUI(activeProfile, CollectionManagerFactory.getInstance().getCollectionManager().getFavorites(activeProfile));
        CollectionViewHandler collectionViewHandler=new CollectionViewHandler(collectionViewGUI, activeProfile);
        AudioTableHandler.getInstance((AudioTable)collectionViewGUI.getAudioTable());
        AudioTableHandler.setQueue(false);
        Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getFavoritesButton());

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(collectionViewGUI.getScene());
        stage.setTitle("Favorites");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };

    //PlaylistButtonHandler
    EventHandler<ActionEvent> playlistsButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        ArrayList<JBCollection> playlists=CollectionManagerFactory.getInstance().getCollectionManager().getPlaylists(activeProfile);
        CollectionLibraryGUI collectionLibraryGUI=new CollectionLibraryGUI(activeProfile, playlists, EJBENTITY.PLAYLIST);
        CollectionLibraryHandler collectionLibraryHandler=new CollectionLibraryHandler(activeProfile, collectionLibraryGUI);
        AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING=null;
        AudioTableHandler.setQueue(false);
        Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getPlaylistsButton());

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(collectionLibraryGUI.getScene());
        stage.setTitle("Playlists");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };

    //AlbumButtonHandler
    EventHandler<ActionEvent> albumsButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        //Retrieve albums
        ArrayList<JBCollection> albums=null;
        try{
          albums=CollectionManagerFactory.getInstance().getCollectionManager().getAlbums((Artist)activeProfile);
        }catch(ClassCastException e){
          //popup
        }

        CollectionLibraryGUI collectionLibraryGUI=new CollectionLibraryGUI(activeProfile, albums, EJBENTITY.ALBUM);
        CollectionLibraryHandler collectionLibraryHandler=new CollectionLibraryHandler(activeProfile, collectionLibraryGUI);
        AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING=null;
        AudioTableHandler.setQueue(false);
        Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getAlbumButton());


        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(collectionLibraryGUI.getScene());
        stage.setTitle("Albums");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };

    //PodcastButtonHandler
    EventHandler<ActionEvent> podcastsButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        //Retrieve podcasts
        ArrayList<JBCollection> podcasts=null;
        try{
          podcasts=CollectionManagerFactory.getInstance().getCollectionManager().getPodcasts((Artist)activeProfile);
        }catch(ClassCastException e){
          //popup
        }

        CollectionLibraryGUI collectionLibraryGUI=new CollectionLibraryGUI(activeProfile, podcasts, EJBENTITY.PODCAST);
        CollectionLibraryHandler collectionLibraryHandler=new CollectionLibraryHandler(activeProfile, collectionLibraryGUI);
        AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING=null;
        AudioTableHandler.setQueue(false);
        Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getPodcastButton());


        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(collectionLibraryGUI.getScene());
        stage.setTitle("Podcasts");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };

    Sidebar.getInstance(activeProfile).getHomeButton().setOnAction(homeButtonHandler);
    Sidebar.getInstance(activeProfile).getSearchButton().setOnAction(searchButtonHandler);
    Sidebar.getInstance(activeProfile).getProfileButton().setOnAction(profileButtonHandler);
    Sidebar.getInstance(activeProfile).getQueueButton().setOnAction(queueButtonHandler);
    Sidebar.getInstance(activeProfile).getFavoritesButton().setOnAction(favoritesButtonHandler);
    Sidebar.getInstance(activeProfile).getPlaylistsButton().setOnAction(playlistsButtonHandler);
    Sidebar.getInstance(activeProfile).getAlbumButton().setOnAction(albumsButtonHandler);
    Sidebar.getInstance(activeProfile).getPodcastButton().setOnAction(podcastsButtonHandler);
  }
}
