package it.unipv.ingsfw.JavaBeats.controller.handler.primary.search;

import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.PlayerManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.SearchManagerFactory;
import it.unipv.ingsfw.JavaBeats.model.EJBENTITY;
import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.primary.search.SearchPageGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

        EventHandler<KeyEvent> searchTextfieldHandler = new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent keyEvent) {

                if (keyEvent.getCode().equals(KeyCode.ENTER)) {

                    Stage stage = (Stage) ((Node) keyEvent.getSource()).getScene().getWindow();

                    //PlayerManager returns map of searched arrays
                    EnumMap<EJBENTITY, ArrayList<IJBResearchable>> searchedMap = SearchManagerFactory.getInstance().getSearchManager().search(searchPageGUI.getSearchTextField().getText(), activeProfile);
                    ArrayList<JBCollection> profilePlaylists = CollectionManagerFactory.getInstance().getCollectionManager().getPlaylists(activeProfile);
                    SearchPageGUI searchPageGUI = new SearchPageGUI(activeProfile, searchedMap, profilePlaylists);
                    SearchPageHandler searchPageHandler = new SearchPageHandler(searchPageGUI, activeProfile);

                    Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                    stage.setScene(searchPageGUI.getScene());
                    stage.setTitle("SearchPage");
                    stage.setWidth(previousDimension.getWidth());
                    stage.setHeight(previousDimension.getHeight());
                }


            }

        };

        EventHandler<ActionEvent> songsChoiceBoxHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(actionEvent);

                ChoiceBox<Playlist> choiceBoxPressed = searchPageGUI.getSearchResults().getChoiceBoxArrayList().get(searchPageGUI.getSearchResults().getChoiceBoxArrayList().indexOf((ChoiceBox<Playlist>) actionEvent.getSource()));
                System.out.println(choiceBoxPressed.getValue());

                Song song = (Song) searchPageGUI.getSearchResults().getSearchedMap().get(EJBENTITY.SONG).get(searchPageGUI.getSearchResults().getChoiceBoxArrayList().indexOf((ChoiceBox<Playlist>) actionEvent.getSource()));

                if (choiceBoxPressed.getValue().toString().equals("Queue")) {
                    PlayerManagerFactory.getInstance().getPlayerManager().addToQueue(song);

                } else if (choiceBoxPressed.getValue().getName().equals("Favorites")) {

                    if (!activeProfile.getFavorites().getTrackList().contains(song)) {
                        activeProfile.getFavorites().getTrackList().add(song);
                        CollectionManagerFactory.getInstance().getCollectionManager().setFavorites(activeProfile);
                    }
                } else {

                    if (!choiceBoxPressed.getValue().getTrackList().contains(song)) {
                        CollectionManagerFactory.getInstance().getCollectionManager().addToCollection(choiceBoxPressed.getValue(), song);
                    }
                }

            }
        };

        EventHandler<ActionEvent> episodesChoiceBoxHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(actionEvent);

                ChoiceBox<Playlist> choiceBoxPressed = searchPageGUI.getSearchResults().getEpisodesChoiceBoxArrayList().get(searchPageGUI.getSearchResults().getEpisodesChoiceBoxArrayList().indexOf((ChoiceBox<Playlist>) actionEvent.getSource()));
                System.out.println(choiceBoxPressed.getValue());

                Episode episode = (Episode) searchPageGUI.getSearchResults().getSearchedMap().get(EJBENTITY.EPISODE).get(searchPageGUI.getSearchResults().getEpisodesChoiceBoxArrayList().indexOf((ChoiceBox<Playlist>) actionEvent.getSource()));

                if (choiceBoxPressed.getValue().toString().equals("Queue")) {
                    PlayerManagerFactory.getInstance().getPlayerManager().addToQueue(episode);

                } else if (choiceBoxPressed.getValue().getName().equals("Favorites")) {

                    if (!activeProfile.getFavorites().getTrackList().contains(episode)) {
                        activeProfile.getFavorites().getTrackList().add(episode);
                        CollectionManagerFactory.getInstance().getCollectionManager().setFavorites(activeProfile);
                    }
                } else {

                    if (!choiceBoxPressed.getValue().getTrackList().contains(episode)) {
                        CollectionManagerFactory.getInstance().getCollectionManager().addToCollection(choiceBoxPressed.getValue(), episode);
                    }
                }

            }
        };


        searchPageGUI.getSearchTextField().setOnKeyPressed(searchTextfieldHandler);
        try {
            searchPageGUI.getSearchResults().getChoiceBoxArrayList().forEach(b -> b.setOnAction(songsChoiceBoxHandler));
        } catch (NullPointerException n) {

        }

        try {
            searchPageGUI.getSearchResults().getEpisodesChoiceBoxArrayList().forEach(b -> b.setOnAction(episodesChoiceBoxHandler));
        } catch (NullPointerException n) {

        }

    }


}
