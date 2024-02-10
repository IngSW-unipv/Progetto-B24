package it.unipv.ingsfw.JavaBeats.controller.handler.presets;

import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.PlayerManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.library.CollectionLibraryHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.library.CollectionViewHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.primary.home.HomePageHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.primary.profile.ProfileViewHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.primary.search.SearchPageHandler;
import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.exceptions.InvalidJBEntityException;
import it.unipv.ingsfw.JavaBeats.exceptions.SystemErrorException;
import it.unipv.ingsfw.JavaBeats.model.EJBENTITY;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionLibraryGUI;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionViewGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioTable;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.ExceptionDialog;
import it.unipv.ingsfw.JavaBeats.view.primary.home.HomePageGUI;
import it.unipv.ingsfw.JavaBeats.view.primary.profile.ProfileViewGUI;
import it.unipv.ingsfw.JavaBeats.view.primary.search.SearchPageGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Queue;

public class SidebarHandler {
    /*---------------------------------------*/
    //Constructor
    /*---------------------------------------*/
    public SidebarHandler() {
        super();
        initComponents();
    }
    /*---------------------------------------*/
    //Getter/Setter
    /*---------------------------------------*/

    /*---------------------------------------*/
    //Methods
    /*---------------------------------------*/
    private void initComponents() {
        //HomeButtonHandler
        EventHandler<ActionEvent> homeButtonHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                HomePageGUI homePageGUI = new HomePageGUI(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());
                HomePageHandler homePageHandler = new HomePageHandler(homePageGUI, ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());
                AudioTableHandler.getInstance().setCurrentAudioTableShowing(null);
                AudioTableHandler.getInstance().setQueue(false);
                Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).setActive(Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).getHomeButton());

                Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                stage.setScene(homePageGUI.getScene());
                stage.setTitle("HomePage");
                stage.setWidth(previousDimension.getWidth());
                stage.setHeight(previousDimension.getHeight());
            }
        };
        //SearchButtonHandler
        EventHandler<ActionEvent> searchButtonHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                SearchPageGUI searchPageGUI = new SearchPageGUI(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile(), null, null);
                SearchPageHandler searchPageHandler = new SearchPageHandler(searchPageGUI, ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());
                AudioTableHandler.getInstance().setCurrentAudioTableShowing(null);
                AudioTableHandler.getInstance().setQueue(false);
                Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).setActive(Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).getSearchButton());

                Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                stage.setScene(searchPageGUI.getScene());
                stage.setTitle("SearchPage");
                stage.setWidth(previousDimension.getWidth());
                stage.setHeight(previousDimension.getHeight());
            }
        };
        //ProfileButtonHandler
        EventHandler<ActionEvent> profileButtonHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                try {
                    ProfileManagerFactory.getInstance().getProfileManager().refreshProfile(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());

                    ProfileViewGUI profileViewGUI = new ProfileViewGUI(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile(), ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());
                    ProfileViewHandler profileViewHandler = new ProfileViewHandler(profileViewGUI, ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());
                    AudioTableHandler.getInstance().setCurrentAudioTableShowing(null);
                    AudioTableHandler.getInstance().setQueue(false);
                    Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).setActive(Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).getProfileButton());

                    Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                    stage.setScene(profileViewGUI.getScene());
                    stage.setTitle("Profile");
                    stage.setWidth(previousDimension.getWidth());
                    stage.setHeight(previousDimension.getHeight());

                } catch (AccountNotFoundException e) {
                    ExceptionDialog exceptionDialog = new ExceptionDialog(((Stage) AudioTableHandler.getInstance().getCurrentAudioTableShowing().getScene().getWindow()), e);
                    exceptionDialog.showAndWait();
                }


            }
        };

        //QueueButtonHandler
        EventHandler<ActionEvent> queueButtonHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Queue<JBAudio> queue = PlayerManagerFactory.getInstance().getPlayerManager().getQueue();

                CollectionViewGUI collectionViewGUI = new CollectionViewGUI(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile(), queue);
                CollectionViewHandler collectionViewHandler = new CollectionViewHandler(collectionViewGUI, ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());
                AudioTableHandler.getInstance().setCurrentAudioTableShowing((AudioTable) collectionViewGUI.getAudioTable());
                AudioTableHandler.getInstance().setQueue(true);
                Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).setActive(Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).getQueueButton());

                Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                stage.setScene(collectionViewGUI.getScene());
                stage.setTitle("Favorites");
                stage.setWidth(previousDimension.getWidth());
                stage.setHeight(previousDimension.getHeight());
            }
        };

        //FavoritesButtonHandler
        EventHandler<ActionEvent> favoritesButtonHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                try {
                    CollectionViewGUI collectionViewGUI = new CollectionViewGUI(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile(), CollectionManagerFactory.getInstance().getCollectionManager().getFavorites(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()));
                    CollectionViewHandler collectionViewHandler = new CollectionViewHandler(collectionViewGUI, ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());
                    AudioTableHandler.getInstance().setCurrentAudioTableShowing((AudioTable) collectionViewGUI.getAudioTable());
                    AudioTableHandler.getInstance().setQueue(false);
                    Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).setActive(Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).getFavoritesButton());

                    Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                    stage.setScene(collectionViewGUI.getScene());
                    stage.setTitle("Favorites");
                    stage.setWidth(previousDimension.getWidth());
                    stage.setHeight(previousDimension.getHeight());
                } catch (AccountNotFoundException e) {
                    ExceptionDialog exceptionDialog = new ExceptionDialog(stage, new SystemErrorException());
                    exceptionDialog.showAndWait();
                }//end-try
            }
        };

        //PlaylistButtonHandler
        EventHandler<ActionEvent> playlistsButtonHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                try {
                    ArrayList<JBCollection> playlists = CollectionManagerFactory.getInstance().getCollectionManager().getPlaylists(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());
                    CollectionLibraryGUI collectionLibraryGUI = new CollectionLibraryGUI(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile(), playlists, EJBENTITY.PLAYLIST);
                    CollectionLibraryHandler collectionLibraryHandler = new CollectionLibraryHandler(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile(), collectionLibraryGUI);
                    AudioTableHandler.getInstance().setCurrentAudioTableShowing(null);
                    AudioTableHandler.getInstance().setQueue(false);
                    Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).setActive(Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).getPlaylistsButton());

                    Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                    stage.setScene(collectionLibraryGUI.getScene());
                    stage.setTitle("Playlists");
                    stage.setWidth(previousDimension.getWidth());
                    stage.setHeight(previousDimension.getHeight());
                } catch (AccountNotFoundException e) {
                    ExceptionDialog exceptionDialog = new ExceptionDialog(stage, new SystemErrorException());
                    exceptionDialog.showAndWait();
                }//end-try
            }
        };

        //AlbumButtonHandler
        EventHandler<ActionEvent> albumsButtonHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                //Retrieve albums
                try {
                    ArrayList<JBCollection> albums = CollectionManagerFactory.getInstance().getCollectionManager().getAlbums((Artist) ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());
                    CollectionLibraryGUI collectionLibraryGUI = new CollectionLibraryGUI(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile(), albums, EJBENTITY.ALBUM);
                    CollectionLibraryHandler collectionLibraryHandler = new CollectionLibraryHandler(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile(), collectionLibraryGUI);
                    AudioTableHandler.getInstance().setCurrentAudioTableShowing(null);
                    AudioTableHandler.getInstance().setQueue(false);
                    Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).setActive(Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).getAlbumButton());


                    Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                    stage.setScene(collectionLibraryGUI.getScene());
                    stage.setTitle("Albums");
                    stage.setWidth(previousDimension.getWidth());
                    stage.setHeight(previousDimension.getHeight());
                } catch (ClassCastException e) {
                    ExceptionDialog exceptionDialog = new ExceptionDialog(stage, new InvalidJBEntityException());
                    exceptionDialog.showAndWait();
                } catch (AccountNotFoundException e) {
                    ExceptionDialog exceptionDialog = new ExceptionDialog(stage, new SystemErrorException());
                    exceptionDialog.showAndWait();
                }//end-try
            }
        };

        //PodcastButtonHandler
        EventHandler<ActionEvent> podcastsButtonHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                //Retrieve podcasts
                try {
                    ArrayList<JBCollection> podcasts = CollectionManagerFactory.getInstance().getCollectionManager().getPodcasts((Artist) ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());
                    CollectionLibraryGUI collectionLibraryGUI = new CollectionLibraryGUI(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile(), podcasts, EJBENTITY.PODCAST);
                    CollectionLibraryHandler collectionLibraryHandler = new CollectionLibraryHandler(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile(), collectionLibraryGUI);
                    AudioTableHandler.getInstance().setCurrentAudioTableShowing(null);
                    AudioTableHandler.getInstance().setQueue(false);
                    Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).setActive(Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).getPodcastButton());

                    Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                    stage.setScene(collectionLibraryGUI.getScene());
                    stage.setTitle("Podcasts");
                    stage.setWidth(previousDimension.getWidth());
                    stage.setHeight(previousDimension.getHeight());
                } catch (ClassCastException e) {
                    ExceptionDialog exceptionDialog = new ExceptionDialog(stage, new InvalidJBEntityException());
                    exceptionDialog.showAndWait();
                } catch (AccountNotFoundException e) {
                    ExceptionDialog exceptionDialog = new ExceptionDialog(stage, new SystemErrorException());
                    exceptionDialog.showAndWait();
                }//end-try
            }
        };
        Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).getHomeButton().setOnAction(homeButtonHandler);
        Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).getSearchButton().setOnAction(searchButtonHandler);
        Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).getProfileButton().setOnAction(profileButtonHandler);
        Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).getQueueButton().setOnAction(queueButtonHandler);
        Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).getFavoritesButton().setOnAction(favoritesButtonHandler);
        Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).getPlaylistsButton().setOnAction(playlistsButtonHandler);
        Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).getAlbumButton().setOnAction(albumsButtonHandler);
        Sidebar.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile()).getPodcastButton().setOnAction(podcastsButtonHandler);
    }
}
