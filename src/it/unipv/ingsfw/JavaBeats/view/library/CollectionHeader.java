package it.unipv.ingsfw.JavaBeats.view.library;

import it.unipv.ingsfw.JavaBeats.model.collection.Podcast;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.sql.SQLException;
import java.sql.Time;
import java.util.Queue;

public class CollectionHeader extends VBox {
    /*---------------------------------------*/
    //Attributi
    /*---------------------------------------*/
    private static final Font fontUser = Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15);
    private static final Background bgHome = new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY));
    private static final Background bgPills = new Background(new BackgroundFill(Color.rgb(18, 18, 18), new CornerRadii(25), Insets.EMPTY));
    private static final Font fontTitle = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 90);
    private static final Font fontCollectionInfo = Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15);
    private Button editButton;
    private Button buttonPlayPause;
    private Button buttonLoop;
    private Button buttonRandom;

    private Button buttonBin;
    private Button addEpisodeButton;
    private Label collectionTitle;
    private ImageView collectionImageView;

    /*---------------------------------------*/
    //Costruttori
    /*---------------------------------------*/
    public CollectionHeader(JBProfile activeProfile, JBCollection jbCollection) {
        initComponents(activeProfile, jbCollection);
    }

    public CollectionHeader(JBProfile activeProfile, Queue<JBAudio> queue) {
        initComponentsQueue(activeProfile, queue);

    }

    /*---------------------------------------*/
    //Getter/Setter
    /*---------------------------------------*/
    public Button getEditButton() {
        return editButton;
    }

    public Button getButtonPlayPause() {
        return buttonPlayPause;
    }

    public Button getButtonBin() {
        return buttonBin;
    }

    public Button getButtonRandom() {
        return buttonRandom;
    }

    public Button getButtonLoop() {
        return buttonLoop;
    }

    public Button getAddEpisodeButton() {
        return addEpisodeButton;
    }

    public Label getCollectionTitle() {
        return collectionTitle;
    }

    public ImageView getCollectionImageView() {
        return collectionImageView;
    }

    /*---------------------------------------*/
    //Metodi
    /*---------------------------------------*/


    //Get all the buttons
    private void getButtons() {
        //Hbox buttons

        //PlayPause
        Image playpauseImage = new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Play.png", true);
        ImageView playPauseImageView = new ImageView(playpauseImage);
        playPauseImageView.setPreserveRatio(true);
        buttonPlayPause = new Button();
        buttonPlayPause.setGraphic(playPauseImageView);
        buttonPlayPause.setStyle("-fx-background-color: #0F0F0FFF;");
        buttonPlayPause.setCursor(Cursor.HAND);
        buttonPlayPause.setTooltip(new Tooltip("Play/Pause"));

        //Random
        Image randomImage = new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyRandom.png", true);
        ImageView randomImageView = new ImageView(randomImage);
        randomImageView.setPreserveRatio(true);
        buttonRandom = new Button();
        buttonRandom.setGraphic(randomImageView);
        buttonRandom.setStyle("-fx-background-color: #0F0F0FFF;");
        buttonRandom.setCursor(Cursor.HAND);
        buttonRandom.setTooltip(new Tooltip("Random"));

        //Loop
        Image loopImage = new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyLoop.png", true);
        ImageView loopImageView = new ImageView(loopImage);
        loopImageView.setPreserveRatio(true);
        buttonLoop = new Button();
        buttonLoop.setGraphic(loopImageView);
        buttonLoop.setStyle("-fx-background-color: #0F0F0FFF;");
        buttonLoop.setCursor(Cursor.HAND);
        buttonLoop.setTooltip(new Tooltip("Loop"));

        //Edit
        Image editImage = new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Edit.png", true);
        ImageView editImageView = new ImageView(editImage);
        editImageView.setPreserveRatio(true);
        editButton = new Button();
        editButton.setGraphic(editImageView);
        editButton.setStyle("-fx-background-color: #0F0F0FFF;");
        editButton.setCursor(Cursor.HAND);
        editButton.setTooltip(new Tooltip("Edit"));

        //Bin
        Image binImage = new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Bin.png", true);
        ImageView binImageView = new ImageView(binImage);
        binImageView.setPreserveRatio(true);
        buttonBin = new Button();
        buttonBin.setGraphic(binImageView);
        buttonBin.setStyle("-fx-background-color: #0F0F0FFF;");
        buttonBin.setCursor(Cursor.HAND);
        buttonBin.setTooltip(new Tooltip("Delete"));

        //Add episode
        Image addEpisodeImage = new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Plus.png", true);
        ImageView addEpisodeImageView = new ImageView(addEpisodeImage);
        addEpisodeImageView.setPreserveRatio(true);
        addEpisodeImageView.setFitHeight(30);
        addEpisodeButton = new Button();
        addEpisodeButton.setGraphic(addEpisodeImageView);
        addEpisodeButton.setStyle("-fx-background-color: #8A2BE2;");
        addEpisodeButton.setCursor(Cursor.HAND);
        addEpisodeButton.setTooltip(new Tooltip("Add episode"));

    }

    private void initComponents(JBProfile activeProfile, JBCollection jbCollection) {

        collectionImageView = null;
        try {
            if (jbCollection.equals(activeProfile.getFavorites())) {
                collectionImageView = new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/RecordBig.png", true));
            } else {
                collectionImageView = new ImageView(new Image(jbCollection.getPicture().getBinaryStream()));

            }
        } catch (SQLException s) {
        }//end-try

        //Dynamically chooses the label
        Label collectionLabel = null;
        try {
            Playlist playlist = (Playlist) jbCollection;
            if (playlist.equals(activeProfile.getFavorites())) {
                collectionLabel = new Label("Favorites");
            } else {
                collectionLabel = new Label("Playlist");
            }

        } catch (ClassCastException e) {
            try {
                Album album = (Album) jbCollection;
                collectionLabel = new Label("Album");
            } catch (ClassCastException e1) {
                collectionLabel = new Label("Podcast");
            }//end-try
        }//end-try
        collectionLabel.setBackground(bgPills);
        collectionLabel.setPadding(new Insets(3));
        collectionLabel.setTextFill(Color.LIGHTGRAY);
        collectionLabel.setFont(fontCollectionInfo);

        collectionTitle = new Label(jbCollection.getName());
        collectionTitle.setFont(fontTitle);
        collectionTitle.setTextFill(Color.LIGHTGRAY);

        /* Button with user's profile picture and username */
        Image userPic = new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/DefaultUser.png", true);
        ImageView userPicImageView = null;
        try {
            userPicImageView = new ImageView(new Image(activeProfile.getProfilePicture().getBinaryStream()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }//end-try
        userPicImageView.setPreserveRatio(true);
        userPicImageView.setFitHeight(20);
        Button userProfileButton = new Button(jbCollection.getCreator().getUsername());
        userProfileButton.setGraphic(userPicImageView);
        userProfileButton.setStyle("-fx-background-color: #121212FF; -fx-background-radius: 10");
        userProfileButton.setFont(fontUser);
        userProfileButton.setTextFill(Color.LIGHTGRAY);

        Label numberOfAudiosLabel = new Label(jbCollection.getTrackList().size() + " audios");
        numberOfAudiosLabel.setFont(fontCollectionInfo);
        numberOfAudiosLabel.setTextFill(Color.LIGHTGRAY);
        numberOfAudiosLabel.setUnderline(true);

        double totalTime = 0;
        for (JBAudio a : jbCollection.getTrackList()) {
            totalTime += a.getMetadata().getDuration();
        }//end-foreach
        Label totalDurationLabel = new Label(JBAudio.convertToMinutesAndSeconds(totalTime) + " mins");
        totalDurationLabel.setFont(fontCollectionInfo);
        totalDurationLabel.setTextFill(Color.LIGHTGRAY);
        totalDurationLabel.setUnderline(true);

        HBox collectionInformation = new HBox(10, userProfileButton, numberOfAudiosLabel, totalDurationLabel);
        collectionInformation.setAlignment(Pos.CENTER_LEFT);

        VBox collectionTitleInfo = new VBox(5, collectionLabel, collectionTitle, collectionInformation);
        collectionTitleInfo.setAlignment(Pos.BOTTOM_LEFT);

        HBox topViewHBox = new HBox(15, collectionImageView, collectionTitleInfo);

        getButtons();

        HBox buttonsHBbox = null;
        if (!activeProfile.equals(jbCollection.getCreator())) {
            buttonsHBbox = new HBox(buttonPlayPause, buttonRandom, buttonLoop);
        } else {
            try {
                Playlist playlist = (Playlist) jbCollection;
                if (playlist.equals(activeProfile.getFavorites())) {
                    buttonsHBbox = new HBox(buttonPlayPause, buttonRandom, buttonLoop);
                } else {
                    buttonsHBbox = new HBox(buttonPlayPause, buttonRandom, buttonLoop, editButton, buttonBin);
                }

            } catch (ClassCastException e) {
                try {
                    Podcast podcast = (Podcast) jbCollection;
                    buttonsHBbox = new HBox(buttonPlayPause, buttonRandom, buttonLoop, buttonBin, addEpisodeButton);

                } catch (ClassCastException c) {
                    buttonsHBbox = new HBox(buttonPlayPause, buttonRandom, buttonLoop, buttonBin);

                }
            }//end-try
        }//end-if

        getChildren().addAll(topViewHBox, buttonsHBbox);
        buttonsHBbox.setPadding(new Insets(40, 0, 0, 0));

        setPadding(new Insets(50, 0, 30, 0));
        setBackground(bgHome);
    }

    private void initComponentsQueue(JBProfile activeProfile, Queue<JBAudio> queue) {

        Image queueImage = new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/QueueBig.png", true);
        ImageView queueImageView = new ImageView(queueImage);

        Label queueLabel = new Label("Queue");

        queueLabel.setBackground(bgPills);
        queueLabel.setPadding(new Insets(3));
        queueLabel.setTextFill(Color.LIGHTGRAY);
        queueLabel.setFont(fontCollectionInfo);

        Label queueTitle = new Label("Your queue");
        queueTitle.setFont(fontTitle);
        queueTitle.setTextFill(Color.LIGHTGRAY);

        /* Button with user's profile picture and username */
        Image userPic = new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/DefaultUser.png", true);
        ImageView userPicImageView = null;
        try {
            userPicImageView = new ImageView(new Image(activeProfile.getProfilePicture().getBinaryStream()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }//end-try
        userPicImageView.setPreserveRatio(true);
        userPicImageView.setFitHeight(20);
        Button userProfileButton = new Button(activeProfile.getUsername());
        userProfileButton.setGraphic(userPicImageView);
        userProfileButton.setStyle("-fx-background-color: #121212FF; -fx-background-radius: 10");
        userProfileButton.setFont(fontUser);
        userProfileButton.setTextFill(Color.LIGHTGRAY);

        Label numberOfAudiosLabel = new Label(queue.size() + " audios");
        numberOfAudiosLabel.setFont(fontCollectionInfo);
        numberOfAudiosLabel.setTextFill(Color.LIGHTGRAY);
        numberOfAudiosLabel.setUnderline(true);

        double totalTime = 0;
        for (JBAudio jbAudio : queue) {
            totalTime += jbAudio.getMetadata().getDuration();
        }//end-foreach
        Label totalDurationLabel = new Label(JBAudio.convertToMinutesAndSeconds(totalTime) + " mins");
        totalDurationLabel.setFont(fontCollectionInfo);
        totalDurationLabel.setTextFill(Color.LIGHTGRAY);
        totalDurationLabel.setUnderline(true);

        HBox queueInformation = new HBox(10, userProfileButton, numberOfAudiosLabel, totalDurationLabel);
        queueInformation.setAlignment(Pos.CENTER_LEFT);

        VBox queueTitleInfo = new VBox(5, queueLabel, queueTitle, queueInformation);
        queueTitleInfo.setAlignment(Pos.BOTTOM_LEFT);

        HBox topViewHBox = new HBox(15, queueImageView, queueTitleInfo);

        getButtons();

        HBox buttonsHBbox = new HBox(buttonLoop, buttonBin);

        buttonsHBbox.setPadding(new Insets(40, 0, 0, 0));
        getChildren().addAll(topViewHBox, buttonsHBbox);

        setPadding(new Insets(50, 0, 30, 0));
        setBackground(bgHome);

    }



    /*---------------------------------------*/
}
