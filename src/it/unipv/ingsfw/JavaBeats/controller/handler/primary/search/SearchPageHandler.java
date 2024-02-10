package it.unipv.ingsfw.JavaBeats.controller.handler.primary.search;

import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.PlayerManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.SearchManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.library.CollectionViewHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.AudioTableHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.primary.profile.ProfileViewHandler;
import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.exceptions.SystemErrorException;
import it.unipv.ingsfw.JavaBeats.model.EJBENTITY;
import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.collection.Podcast;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.model.profile.User;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionViewGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioCard;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioTable;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.ExceptionDialog;
import it.unipv.ingsfw.JavaBeats.view.primary.profile.ProfileViewGUI;
import it.unipv.ingsfw.JavaBeats.view.primary.search.SearchPageGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.EnumMap;

public class SearchPageHandler {

    //Attributi
    private SearchPageGUI searchPageGUI;
    private JBProfile activeProfile;

    //Getters and setters

    //Costruttore
    public SearchPageHandler(SearchPageGUI searchPageGUI, JBProfile activeProfile) {
        this.searchPageGUI = searchPageGUI;
        this.activeProfile = activeProfile;
        initComponents();
    }

    //Metodi
    private void initComponents() {
        EventHandler<ActionEvent> profileButtonHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                try {
                    ProfileManagerFactory.getInstance().getProfileManager().refreshProfile(activeProfile);

                    ProfileViewGUI profileViewGUI = new ProfileViewGUI(activeProfile, activeProfile);
                    ProfileViewHandler profileViewHandler = new ProfileViewHandler(profileViewGUI, activeProfile);
                    Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getProfileButton());

                    Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                    stage.setScene(profileViewGUI.getScene());
                    stage.setTitle("Profile");
                    stage.setWidth(previousDimension.getWidth());
                    stage.setHeight(previousDimension.getHeight());

                } catch (AccountNotFoundException e) {
                    searchPageGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

                    ExceptionDialog exceptionDialog = new ExceptionDialog(stage, new SystemErrorException());
                    exceptionDialog.showAndWait();

                    searchPageGUI.getGp().setEffect(null); /* Removing blur effect */
                }


            }
        };

        EventHandler<KeyEvent> searchTextfieldHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    Stage stage = (Stage) ((Node) keyEvent.getSource()).getScene().getWindow();

                    //PlayerManager returns map of searched arrays
                    try {
                        EnumMap<EJBENTITY, ArrayList<IJBResearchable>> searchedMap = SearchManagerFactory.getInstance().getSearchManager().search(searchPageGUI.getSearchTextField().getText(), activeProfile);

                        ArrayList<JBCollection> profilePlaylists = CollectionManagerFactory.getInstance().getCollectionManager().getPlaylists(activeProfile);
                        SearchPageGUI searchPageGUI = new SearchPageGUI(activeProfile, searchedMap, profilePlaylists);
                        SearchPageHandler searchPageHandler = new SearchPageHandler(searchPageGUI, activeProfile);

                        Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                        stage.setScene(searchPageGUI.getScene());
                        stage.setTitle("SearchPage");
                        stage.setWidth(previousDimension.getWidth());
                        stage.setHeight(previousDimension.getHeight());
                    } catch (AccountNotFoundException e) {
                        searchPageGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

                        ExceptionDialog exceptionDialog = new ExceptionDialog(stage, new SystemErrorException());
                        exceptionDialog.showAndWait();

                        searchPageGUI.getGp().setEffect(null);
                    }//end-try
                }//end-if
            }
        };

        EventHandler<ActionEvent> songsChoiceBoxHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                ChoiceBox<Playlist> choiceBoxPressed = searchPageGUI.getSearchResults().getChoiceBoxArrayList().get(searchPageGUI.getSearchResults().getChoiceBoxArrayList().indexOf((ChoiceBox<Playlist>) actionEvent.getSource()));
                Song song = (Song) searchPageGUI.getSearchResults().getSearchedMap().get(EJBENTITY.SONG).get(searchPageGUI.getSearchResults().getChoiceBoxArrayList().indexOf((ChoiceBox<Playlist>) actionEvent.getSource()));

                if (choiceBoxPressed.getValue().toString().equals("Queue")) {
                    PlayerManagerFactory.getInstance().getPlayerManager().addToQueue(song);
                } else if (choiceBoxPressed.getValue().getName().equals("Favorites")) {

                    if (!activeProfile.getFavorites().getTrackList().contains(song)) {
                        try {
                            activeProfile.getFavorites().getTrackList().add(song);

                            CollectionManagerFactory.getInstance().getCollectionManager().setFavorites(activeProfile);
                        } catch (AccountNotFoundException e) {
                            searchPageGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

                            ExceptionDialog exceptionDialog = new ExceptionDialog(stage, new SystemErrorException());
                            exceptionDialog.showAndWait();

                            searchPageGUI.getGp().setEffect(null);
                        }//end-try
                    }//end-if
                } else {

                    if (!choiceBoxPressed.getValue().getTrackList().contains(song)) {
                        try {
                            CollectionManagerFactory.getInstance().getCollectionManager().addToCollection(choiceBoxPressed.getValue(), song);
                        } catch (AccountNotFoundException e) {
                            searchPageGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

                            ExceptionDialog exceptionDialog = new ExceptionDialog(stage, new SystemErrorException());
                            exceptionDialog.showAndWait();

                            searchPageGUI.getGp().setEffect(null);
                        }//end-try
                    }//end-try
                }//end-try
            }
        };

        EventHandler<ActionEvent> episodesChoiceBoxHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                ChoiceBox<Playlist> choiceBoxPressed = searchPageGUI.getSearchResults().getEpisodesChoiceBoxArrayList().get(searchPageGUI.getSearchResults().getEpisodesChoiceBoxArrayList().indexOf((ChoiceBox<Playlist>) actionEvent.getSource()));
                Episode episode = (Episode) searchPageGUI.getSearchResults().getSearchedMap().get(EJBENTITY.EPISODE).get(searchPageGUI.getSearchResults().getEpisodesChoiceBoxArrayList().indexOf((ChoiceBox<Playlist>) actionEvent.getSource()));

                if (choiceBoxPressed.getValue().toString().equals("Queue")) {
                    PlayerManagerFactory.getInstance().getPlayerManager().addToQueue(episode);

                } else if (choiceBoxPressed.getValue().getName().equals("Favorites")) {

                    if (!activeProfile.getFavorites().getTrackList().contains(episode)) {
                        try {
                            activeProfile.getFavorites().getTrackList().add(episode);

                            CollectionManagerFactory.getInstance().getCollectionManager().setFavorites(activeProfile);
                        } catch (AccountNotFoundException e) {
                            searchPageGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

                            ExceptionDialog exceptionDialog = new ExceptionDialog(stage, new SystemErrorException());
                            exceptionDialog.showAndWait();

                            searchPageGUI.getGp().setEffect(null);
                        }//end-try
                    }//end-if
                } else {
                    if (!choiceBoxPressed.getValue().getTrackList().contains(episode)) {
                        try {
                            CollectionManagerFactory.getInstance().getCollectionManager().addToCollection(choiceBoxPressed.getValue(), episode);
                        } catch (AccountNotFoundException e) {
                            searchPageGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

                            ExceptionDialog exceptionDialog = new ExceptionDialog(stage, new SystemErrorException());
                            exceptionDialog.showAndWait();

                            searchPageGUI.getGp().setEffect(null);
                        }//end-try
                    }//end-if
                }//end-if
            }
        };

        EventHandler<MouseEvent> audioCardClickHandler = new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

                IJBResearchable ijbResearchable = (((AudioCard) mouseEvent.getSource()).getIjbResearchable());
                try {
                    Artist artist = (Artist) ijbResearchable;
                    ProfileViewGUI profileViewGUI = new ProfileViewGUI(activeProfile, artist);
                    ProfileViewHandler profileViewHandler = new ProfileViewHandler(profileViewGUI, activeProfile);
                    Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getProfileButton());

                    Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                    stage.setScene(profileViewGUI.getScene());
                    stage.setTitle("Profile");
                    stage.setWidth(previousDimension.getWidth());
                    stage.setHeight(previousDimension.getHeight());
                } catch (ClassCastException c) {
                    try {
                        Album album = (Album) ijbResearchable;
                        try {
                            album.setTrackList(CollectionManagerFactory.getInstance().getCollectionManager().getCollectionAudios(album, activeProfile));
                            CollectionViewGUI collectionViewGUI = new CollectionViewGUI(activeProfile, album);
                            CollectionViewHandler collectionViewHandler = new CollectionViewHandler(collectionViewGUI, activeProfile);
                            AudioTableHandler.getInstance().setCurrentAudioTableShowing((AudioTable) collectionViewGUI.getAudioTable());
                            AudioTableHandler.getInstance().setQueue(false);

                            Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                            stage.setScene(collectionViewGUI.getScene());
                            stage.setTitle("Collection");
                            stage.setWidth(previousDimension.getWidth());
                            stage.setHeight(previousDimension.getHeight());
                        } catch (AccountNotFoundException e) {
                            searchPageGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

                            ExceptionDialog exceptionDialog = new ExceptionDialog(stage, new SystemErrorException());
                            exceptionDialog.showAndWait();

                            searchPageGUI.getGp().setEffect(null);
                        }//end-try
                    } catch (ClassCastException cl) {
                        try {
                            Podcast podcast = (Podcast) ijbResearchable;
                            try {
                                podcast.setTrackList(CollectionManagerFactory.getInstance().getCollectionManager().getCollectionAudios(podcast, activeProfile));
                            } catch (AccountNotFoundException e) {
                                searchPageGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

                                ExceptionDialog exceptionDialog = new ExceptionDialog(stage, new SystemErrorException());
                                exceptionDialog.showAndWait();

                                searchPageGUI.getGp().setEffect(null);
                            }
                            CollectionViewGUI collectionViewGUI = new CollectionViewGUI(activeProfile, podcast);
                            CollectionViewHandler collectionViewHandler = new CollectionViewHandler(collectionViewGUI, activeProfile);
                            AudioTableHandler.getInstance().setCurrentAudioTableShowing((AudioTable) collectionViewGUI.getAudioTable());
                            AudioTableHandler.getInstance().setQueue(false);

                            Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                            stage.setScene(collectionViewGUI.getScene());
                            stage.setTitle("Collection");
                            stage.setWidth(previousDimension.getWidth());
                            stage.setHeight(previousDimension.getHeight());

                        } catch (ClassCastException cla) {
                            try {
                                Playlist playlist = (Playlist) ijbResearchable;
                                try {
                                    playlist.setTrackList(CollectionManagerFactory.getInstance().getCollectionManager().getCollectionAudios(playlist, activeProfile));
                                    CollectionViewGUI collectionViewGUI = new CollectionViewGUI(activeProfile, playlist);
                                    CollectionViewHandler collectionViewHandler = new CollectionViewHandler(collectionViewGUI, activeProfile);
                                    AudioTableHandler.getInstance().setCurrentAudioTableShowing((AudioTable) collectionViewGUI.getAudioTable());
                                    AudioTableHandler.getInstance().setQueue(false);

                                    Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                                    stage.setScene(collectionViewGUI.getScene());
                                    stage.setTitle("Collection");
                                    stage.setWidth(previousDimension.getWidth());
                                    stage.setHeight(previousDimension.getHeight());
                                } catch (AccountNotFoundException e) {
                                    searchPageGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

                                    ExceptionDialog exceptionDialog = new ExceptionDialog(stage, new SystemErrorException());
                                    exceptionDialog.showAndWait();

                                    searchPageGUI.getGp().setEffect(null);
                                }//end-try
                            } catch (ClassCastException clas) {

                                User user = (User) ijbResearchable;
                                ProfileViewGUI profileViewGUI = new ProfileViewGUI(activeProfile, user);
                                ProfileViewHandler profileViewHandler = new ProfileViewHandler(profileViewGUI, activeProfile);
                                Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getProfileButton());

                                Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                                stage.setScene(profileViewGUI.getScene());
                                stage.setTitle("Profile");
                                stage.setWidth(previousDimension.getWidth());
                                stage.setHeight(previousDimension.getHeight());
                            }//end-try
                        }//end-try
                    }//end-try
                }//end-try
            }
        };
        searchPageGUI.getUserProfileButton().setOnAction(profileButtonHandler);
        searchPageGUI.getSearchTextField().setOnKeyPressed(searchTextfieldHandler);
        searchPageGUI.getSearchTextField().setOnKeyPressed(searchTextfieldHandler);
        if (searchPageGUI.getSearchResults() != null) {
            searchPageGUI.getSearchResults().getChoiceBoxArrayList().forEach(b -> b.setOnAction(songsChoiceBoxHandler));
            searchPageGUI.getSearchResults().getChoiceBoxArrayList().forEach(b -> b.setOnAction(songsChoiceBoxHandler));
            searchPageGUI.getSearchResults().getEpisodesChoiceBoxArrayList().forEach(b -> b.setOnAction(episodesChoiceBoxHandler));
            searchPageGUI.getSearchResults().getArtistsHBox().getChildren().forEach(a -> a.setOnMouseClicked(audioCardClickHandler));
            searchPageGUI.getSearchResults().getAlbumsHBox().getChildren().forEach(a -> a.setOnMouseClicked(audioCardClickHandler));
            searchPageGUI.getSearchResults().getPodcastsHBox().getChildren().forEach(a -> a.setOnMouseClicked(audioCardClickHandler));
            searchPageGUI.getSearchResults().getPlaylistsHBox().getChildren().forEach(a -> a.setOnMouseClicked(audioCardClickHandler));
            searchPageGUI.getSearchResults().getUsersHBox().getChildren().forEach(a -> a.setOnMouseClicked(audioCardClickHandler));
        }//end-if

    }
}
