package it.unipv.ingsfw.JavaBeats.view.primary.home;

import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioCard;
import it.unipv.ingsfw.JavaBeats.view.presets.scrollpanes.ScrollPanePreset;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Home extends VBox {
    /*---------------------------------------*/
    //Attributi
    /*---------------------------------------*/
    private static final Font fontWelcome = Font.font("Verdana", FontWeight.BLACK, FontPosture.REGULAR, 25);
    private static final Font fontRecents = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25);
    private static final Font fontUser = Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15);
    private static final Background bgHome = new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY));
    private static final LocalTime time = LocalTime.now();
    private Button userProfileButton;
    private HBox songsHBox;
    private HBox collectionsHBox;
    private HBox artistsHBox;

    /*---------------------------------------*/
    //Costruttori
    /*---------------------------------------*/
    public Home(JBProfile activeProfile) {
        super();
        initComponents(activeProfile);
    }

    /*---------------------------------------*/
    //Getter/Setter
    /*---------------------------------------*/
    public Button getUserProfileButton() {
        return userProfileButton;
    }

    public HBox getSongsHBox() {
        return songsHBox;
    }

    public HBox getCollectionsHBox() {
        return collectionsHBox;
    }

    public HBox getArtistsHBox() {
        return artistsHBox;
    }

    /*---------------------------------------*/
    //Metodi
    /*---------------------------------------*/
    private void initComponents(JBProfile activeProfile) {
        System.out.println(activeProfile.getListeningHistory());
        /*
         *  Setup of top HBox component containing Logo, a warm welcome for the user and the profile image with its username
         */
        /* Logo */
        Image logo = new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Logo.png", true);
        ImageView logoImageView = new ImageView(logo);
        logoImageView.setPreserveRatio(true);

        /* Welcome */
        Label userWelcome;
        userWelcome = time.getHour() > 17 ? new Label("Good evening, " + activeProfile.getUsername()) : new Label("Good morning, " + activeProfile.getUsername());
        userWelcome.setPadding(new Insets(0, 0, 0, 10));
        userWelcome.setFont(fontWelcome);
        userWelcome.setTextFill(Color.LIGHTGRAY);

        /* Pane for spacing */
        Pane whitePane = new Pane();
        HBox.setHgrow(whitePane, Priority.ALWAYS);

        /* Button with user's profile picture and username */
        ImageView userPicImageView = null;
        try {
            userPicImageView = new ImageView(new Image(activeProfile.getProfilePicture().getBinaryStream()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }//end-try
        userPicImageView.setPreserveRatio(true);
        userPicImageView.setFitHeight(60);
        userProfileButton = new Button(activeProfile.getUsername());
        userProfileButton.setBackground(bgHome);
        userProfileButton.setGraphic(userPicImageView);
        userProfileButton.setCursor(Cursor.HAND);
        userProfileButton.setFont(fontUser);
        userProfileButton.setTextFill(Color.LIGHTGRAY);

        /* Hbox containing all above components */
        HBox userHBox = new HBox(logoImageView, userWelcome, whitePane, userProfileButton);
        userHBox.setAlignment(Pos.CENTER_LEFT);
        userHBox.setPadding(new Insets(20, 10, 10, 10));

        /*
         *   Setup of the Main visual content, all the recent types of listening. Songs, playlists and artists.
         */
        /* Recent songs block, we have a label and a HBox of AudioCards inside a JBScrollPane */
        Label recentAudiosLabel = new Label("Recent audios");
        recentAudiosLabel.setFont(fontRecents);
        recentAudiosLabel.setTextFill(Color.LIGHTGRAY);
        recentAudiosLabel.setPadding(new Insets(30, 0, 40, 0));
        /* songsHBox contains distinct values of audios recently listened with a maximum of 15 elements */
        songsHBox = new HBox(50);

        int i;
        int count;
        try {
            songsHBox.getChildren().add(new AudioCard(activeProfile.getListeningHistory().getFirst()));
            i = 1;
            count = 1;
            while (i < activeProfile.getListeningHistory().size() && count < 15) {
                if (!activeProfile.getListeningHistory().subList(0, i).contains(activeProfile.getListeningHistory().get(i))) {
                    songsHBox.getChildren().add(new AudioCard(activeProfile.getListeningHistory().get(i)));
                    count += 1;
                }//end-if
                i += 1;
            }//end-while
        } catch (NoSuchElementException n) {
            /* empty listening hystory */
        }//end-try
        ScrollPanePreset audiosScroll = new ScrollPanePreset(songsHBox);
        audiosScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
        audiosScroll.setVbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
        setVgrow(audiosScroll, Priority.ALWAYS);

        /* Recent songs block, we have a label and a HBox of AudioCards inside a JBScrollPane */
        Label recentCollectionLabel = new Label("Recent collections");
        recentCollectionLabel.setFont(fontRecents);
        recentCollectionLabel.setTextFill(Color.LIGHTGRAY);
        recentCollectionLabel.setPadding(new Insets(40, 0, 40, 0));
        collectionsHBox = new HBox(50);
        /* Extracting all Collections from ListeningHistory  */
        ArrayList<JBCollection> recentCollections = new ArrayList<>();
        for (JBAudio jbAudio : activeProfile.getListeningHistory()) {
            recentCollections.add(jbAudio.getMetadata().getCollection());
        }//end-foreach
        try {
            collectionsHBox.getChildren().add(new AudioCard(recentCollections.getFirst()));
            i = 1;
            count = 1;
            while (i < recentCollections.size() && count < 15) {
                if (!recentCollections.subList(0, i).contains(recentCollections.get(i))) {
                    collectionsHBox.getChildren().add(new AudioCard(recentCollections.get(i)));
                    count += 1;
                }//end-if
                i += 1;
            }//end-while
        } catch (IndexOutOfBoundsException | NoSuchElementException in) {
            /* No audios */
        }//end-try
        ScrollPanePreset collectionScroll = new ScrollPanePreset(collectionsHBox);
        collectionScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
        collectionScroll.setVbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
        setVgrow(collectionScroll, Priority.ALWAYS);

        /* Recent songs block, we have a label and a HBox of AudioCards inside a JBScrollPane */
        Label recentArtists = new Label("Recent artists");
        recentArtists.setFont(fontRecents);
        recentArtists.setTextFill(Color.LIGHTGRAY);
        recentArtists.setPadding(new Insets(40, 0, 40, 0));
        artistsHBox = new HBox(50);
        /* Extracting all Artist from ListeningHistory  */
        ArrayList<JBProfile> recentProfiles = new ArrayList<>();
        for (JBAudio jbAudio : activeProfile.getListeningHistory()) {
            recentProfiles.add(jbAudio.getMetadata().getCollection().getCreator());
        }//end-foreach
        try {
            artistsHBox.getChildren().add(new AudioCard(recentProfiles.getFirst()));
            i = 1;
            count = 1;
            while (i < recentProfiles.size() && count < 15) {
                if (!recentProfiles.subList(0, i).contains(recentProfiles.get(i))) {
                    artistsHBox.getChildren().add(new AudioCard(recentProfiles.get(i)));
                    count += 1;
                }//end-fi
                i += 1;
            }//end-while
        } catch (IndexOutOfBoundsException | NoSuchElementException in) {
            /* No audios */
        }//end-try
        ScrollPanePreset artistScroll = new ScrollPanePreset(artistsHBox);
        artistScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
        artistScroll.setVbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
        setVgrow(artistScroll, Priority.ALWAYS);

        /* VBox with all the above components, inside a JBScrollPane */
        VBox mainContent = new VBox(recentAudiosLabel, audiosScroll, recentCollectionLabel, collectionScroll, recentArtists, artistScroll);
        ScrollPanePreset contentScroll = new ScrollPanePreset(mainContent);
        contentScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
        contentScroll.setPadding(new Insets(15));
        contentScroll.setFitToWidth(true);
        setVgrow(contentScroll, Priority.ALWAYS);

        /* adding Top HBox and main JBScrollPane to the Home VBox */
        getChildren().addAll(userHBox, contentScroll);
        getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/scrollbar.css");
        setBackground(bgHome);
    }
    /*---------------------------------------*/
}
