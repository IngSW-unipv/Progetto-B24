package it.unipv.ingsfw.JavaBeats.view.primary.home;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
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

public class Home extends VBox{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final Font fontWelcome=Font.font("Verdana", FontWeight.BLACK, FontPosture.REGULAR, 25);
  private static final Font fontRecents=Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25);
  private static final Font fontUser=Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15);
  private static final Background bgHome=new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY));
  private static final LocalTime time=LocalTime.now();

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public Home(JBProfile activeProfile){
    super();
    initComponents(activeProfile);
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(JBProfile activeProfile){
    /*
     *  Setup of top HBox component containing Logo, a warm welcome for the user and the profile image with its username
     */
    /* Logo */
    Image logo=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Logo.png", true);
    ImageView logoImageView=new ImageView(logo);
    logoImageView.setPreserveRatio(true);

    /* Welcome */
    Label userWelcome;
    userWelcome=time.getHour()>17 ? new Label("Good evening, "+activeProfile.getUsername()) : new Label("Good morning, "+activeProfile.getUsername());
    userWelcome.setPadding(new Insets(0, 0, 0, 10));
    userWelcome.setFont(fontWelcome);
    userWelcome.setTextFill(Color.LIGHTGRAY);

    /* Pane for spacing */
    Pane whitePane=new Pane();
    HBox.setHgrow(whitePane, Priority.ALWAYS);

    /* Button with user's profile picture and username */
    ImageView userPicImageView=null;
    try{
      userPicImageView=new ImageView(new Image(activeProfile.getProfilePicture().getBinaryStream()));
    }catch(SQLException e){
      throw new RuntimeException(e);
    }//end-try
    userPicImageView.setPreserveRatio(true);
    userPicImageView.setFitHeight(60);
    Button userProfileButton=new Button(activeProfile.getUsername());
    userProfileButton.setBackground(bgHome);
    userProfileButton.setGraphic(userPicImageView);
    userProfileButton.setCursor(Cursor.HAND);
    userProfileButton.setFont(fontUser);
    userProfileButton.setTextFill(Color.LIGHTGRAY);

    /* Hbox containing all above components */
    HBox userHBox=new HBox(logoImageView, userWelcome, whitePane, userProfileButton);
    userHBox.setAlignment(Pos.CENTER_LEFT);
    userHBox.setPadding(new Insets(20, 10, 10, 10));

    /*
     *   Setup of the Main visual content, all the recent types of listening. Songs, playlists and artists.
     */
    /* Recent songs block, we have a label and a HBox of AudioCards inside a JBScrollPane */
    Label recentSongsLabel=new Label("Recent songs");
    recentSongsLabel.setFont(fontRecents);
    recentSongsLabel.setTextFill(Color.LIGHTGRAY);
    recentSongsLabel.setPadding(new Insets(30, 0, 40, 0));
    HBox songsHBox=new HBox(50, new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile));
    ScrollPanePreset songsScroll=new ScrollPanePreset(songsHBox);
    songsScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    songsScroll.setVbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    setVgrow(songsScroll, Priority.ALWAYS);

    /* Recent songs block, we have a label and a HBox of AudioCards inside a JBScrollPane */
    Label recentPlaylistLabel=new Label("Recent playlists");
    recentPlaylistLabel.setFont(fontRecents);
    recentPlaylistLabel.setTextFill(Color.LIGHTGRAY);
    recentPlaylistLabel.setPadding(new Insets(40, 0, 40, 0));
    HBox playlistsHBox=new HBox(50, new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile));
    ScrollPanePreset playlistScroll=new ScrollPanePreset(playlistsHBox);
    playlistScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    playlistScroll.setVbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    setVgrow(playlistScroll, Priority.ALWAYS);

    /* Recent songs block, we have a label and a HBox of AudioCards inside a JBScrollPane */
    Label recentArtists=new Label("Recent artists");
    recentArtists.setFont(fontRecents);
    recentArtists.setTextFill(Color.LIGHTGRAY);
    recentArtists.setPadding(new Insets(40, 0, 40, 0));
    HBox artistsHBox=new HBox(50, new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile));
    ScrollPanePreset artistScroll=new ScrollPanePreset(artistsHBox);
    artistScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    artistScroll.setVbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    setVgrow(artistScroll, Priority.ALWAYS);

    /* VBox with all the above components, inside a JBScrollPane */
    VBox mainContent=new VBox(recentSongsLabel, songsScroll, recentPlaylistLabel, playlistScroll, recentArtists, artistScroll);
    ScrollPanePreset contentScroll=new ScrollPanePreset(mainContent);
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
