package it.unipv.ingsfw.JavaBeats.controller.handler.primary.home;

import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.PlayerManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.primary.profile.ProfileViewHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.library.CollectionViewHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.AudioTableHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SidebarHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SongbarHandler;
import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.exceptions.SystemErrorException;
import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionViewGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioCard;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioTable;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.ExceptionDialog;
import it.unipv.ingsfw.JavaBeats.view.primary.home.HomePageGUI;
import it.unipv.ingsfw.JavaBeats.view.primary.profile.ProfileViewGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HomePageHandler {

    //Attributi
    HomePageGUI homePageGUI;

    //Getters and setters

    //Costruttore
    public HomePageHandler(HomePageGUI homePageGUI, JBProfile activeProfile) {
        this.homePageGUI = homePageGUI;
        initComponents(activeProfile);
    }

    private void initComponents(JBProfile activeProfile) {
        EventHandler<ActionEvent> profileButtonHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                ProfileViewGUI profileViewGUI = new ProfileViewGUI(activeProfile, activeProfile);
                ProfileViewHandler profileViewHandler = new ProfileViewHandler(profileViewGUI, activeProfile);
                Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getProfileButton());

                Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                stage.setScene(profileViewGUI.getScene());
                stage.setTitle("Profile");
                stage.setWidth(previousDimension.getWidth());
                stage.setHeight(previousDimension.getHeight());
            }
        };

        EventHandler<MouseEvent> audioCardClickHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

                IJBResearchable ijbResearchable = (((AudioCard) mouseEvent.getSource()).getIjbResearchable());

                try {
                    JBAudio jbAudio = (JBAudio) ijbResearchable;

                    PlayerManagerFactory.getInstance().getPlayerManager().play(jbAudio);
                } catch (ClassCastException e) {
                    try {
                        JBCollection jbCollection = (JBCollection) ijbResearchable;
                        jbCollection.setTrackList(CollectionManagerFactory.getInstance().getCollectionManager().getCollectionAudios(jbCollection, activeProfile));
                        CollectionViewGUI collectionViewGUI = new CollectionViewGUI(activeProfile, jbCollection);
                        CollectionViewHandler collectionViewHandler = new CollectionViewHandler(collectionViewGUI, activeProfile);
                        AudioTableHandler.getInstance().setCurrentAudioTableShowing((AudioTable) collectionViewGUI.getAudioTable());
                        AudioTableHandler.getInstance().setQueue(false);

                        Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                        stage.setScene(collectionViewGUI.getScene());
                        stage.setTitle("Collection");
                        stage.setWidth(previousDimension.getWidth());
                        stage.setHeight(previousDimension.getHeight());

                    } catch (ClassCastException c) {

                        try {
                            Artist artist = (Artist) ijbResearchable;

                            ProfileManagerFactory.getInstance().getProfileManager().refreshProfile(artist);
                            ProfileViewGUI profileViewGUI = new ProfileViewGUI(activeProfile, artist);
                            ProfileViewHandler profileViewHandler = new ProfileViewHandler(profileViewGUI, activeProfile);
                            Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getProfileButton());

                            Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                            stage.setScene(profileViewGUI.getScene());
                            stage.setTitle("Profile");
                            stage.setWidth(previousDimension.getWidth());
                            stage.setHeight(previousDimension.getHeight());
                            
                        } catch (AccountNotFoundException ex) {
                            homePageGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

                            ExceptionDialog exceptionDialog = new ExceptionDialog(stage, new SystemErrorException());
                            exceptionDialog.showAndWait();

                            homePageGUI.getGp().setEffect(null); /* Removing blur effect */
                        }


                    } catch (AccountNotFoundException ex) {
                        homePageGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

                        ExceptionDialog exceptionDialog = new ExceptionDialog(stage, new SystemErrorException());
                        exceptionDialog.showAndWait();

                        homePageGUI.getGp().setEffect(null); /* Removing blur effect */
                    }//end-try
                }
            }
        };
        homePageGUI.getHome().getUserProfileButton().setOnAction(profileButtonHandler);
        homePageGUI.getHome().getSongsHBox().getChildren().forEach(a -> a.setOnMouseClicked(audioCardClickHandler));
        homePageGUI.getHome().getCollectionsHBox().getChildren().forEach(a -> a.setOnMouseClicked(audioCardClickHandler));
        homePageGUI.getHome().getArtistsHBox().getChildren().forEach(a -> a.setOnMouseClicked(audioCardClickHandler));

        SidebarHandler sidebarHandler = new SidebarHandler();
        Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getHomeButton());

        SongbarHandler.getInstance(activeProfile, PlayerManagerFactory.getInstance().getPlayerManager().getCurrentAudioPlaying());
    }

}
