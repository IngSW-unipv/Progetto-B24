package it.unipv.ingsfw.JavaBeats.view.primary.search;
import it.unipv.ingsfw.JavaBeats.view.primary.AudioCard;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class SearchResults extends ScrollPane{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final Font fontTopResult=Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20);
  private static final Font fontTopAudio=Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 24);
  private static final Font fontTitles=Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15);
  private static final Font fontArtists=Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 13);
  private static final Font fontFindings=Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25);

  private static final Background bgHome=new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY));
  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public SearchResults(){
    super();
    initComponents();
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(){

    Label topResultLabel=new Label("Top result");
    topResultLabel.setFont(fontTopResult);
    Image audioPic = new Image("it/unipv/ingsfw/JavaBeats/view/icons/Record.png", true);
    ImageView audioPicImageView=new ImageView(audioPic);
    audioPicImageView.setPreserveRatio(true);
    audioPicImageView.setFitWidth(150);
    Label topResultTitleLabel=new Label("Really long title");
    topResultTitleLabel.setFont(fontTopAudio);
    Label topResultArtistLabel=new Label("Artist");
    VBox imageTitleArtistVBox=new VBox(10, audioPicImageView, topResultTitleLabel, topResultArtistLabel);
    imageTitleArtistVBox.setAlignment(Pos.CENTER_LEFT);
    imageTitleArtistVBox.setPadding(new Insets(40, 100, 0, 15));
    VBox topResultVbox=new VBox(topResultLabel, imageTitleArtistVBox);
    topResultVbox.setPadding(new Insets(20));

    Label topSongsLabel=new Label("Songs");
    topSongsLabel.setFont(fontTopResult);
    HBox[] topSongs={new HBox(), new HBox(), new HBox(), new HBox()};
    for(HBox h : topSongs){
      Image songPic = new Image("it/unipv/ingsfw/JavaBeats/view/icons/Record.png", true);
      ImageView songPicImageView=new ImageView(songPic);
      songPicImageView.setPreserveRatio(true);
      songPicImageView.setFitHeight(55);
      Label songTitle=new Label("Title");
      songTitle.setFont(fontTitles);
      Label songArtist=new Label("Artist");
      songArtist.setFont(fontArtists);
      VBox songTitleArtistVBox=new VBox(songTitle, songArtist);
      songTitleArtistVBox.setAlignment(Pos.CENTER_LEFT);
      Pane whitePane=new Pane();
      HBox.setHgrow(whitePane, Priority.ALWAYS);

      /*
      Image heartImage= new Image("it/unipv/ingsfw/JavaBeats/view/icons/EmptyHeart.png", true);
      ImageView heartImageView=new ImageView(heartImage);
      heartImageView.setPreserveRatio(true);
      Button buttonHeart=new Button();
      buttonHeart.setBackground(bgHome);
      buttonHeart.setGraphic(heartImageView);
      buttonHeart.setCursor(Cursor.HAND);
      */

      Label songDuration=new Label("03:55");

      h.getChildren().addAll(songPicImageView, songTitleArtistVBox, whitePane, songDuration);
      h.setAlignment(Pos.CENTER_LEFT);
    }
    VBox topSongsResultVbox=new VBox(15, topSongsLabel, topSongs[0], topSongs[1], topSongs[2], topSongs[3]);
    topSongsResultVbox.setPadding(new Insets(20));
    HBox bestResultsHBox=new HBox(topResultVbox, topSongsResultVbox);
    HBox.setHgrow(topSongsResultVbox, Priority.ALWAYS);

    /* Artists found, we have a label and a HBox of AudioCards inside a ScrollPane */
    Label foundArtistsLabel=new Label("Artists");
    foundArtistsLabel.setFont(fontFindings);
    foundArtistsLabel.setTextFill(Color.LIGHTGRAY);
    foundArtistsLabel.setPadding(new Insets(40,0, 40, 0));
    HBox artistsHBox=new HBox(50, new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard());
    ScrollPane artistScroll=new ScrollPane(artistsHBox);
    artistScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    artistScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    artistScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    VBox.setVgrow(artistScroll, Priority.ALWAYS);

    /* Albums found, we have a label and a HBox of AudioCards inside a ScrollPane */
    Label foundAlbumsLabel=new Label("Albums");
    foundAlbumsLabel.setFont(fontFindings);
    foundAlbumsLabel.setTextFill(Color.LIGHTGRAY);
    foundAlbumsLabel.setPadding(new Insets(40,0, 40, 0));
    HBox albumsHBox=new HBox(50, new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard());
    ScrollPane albumsScroll=new ScrollPane(albumsHBox);
    albumsScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    albumsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    albumsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    VBox.setVgrow(albumsScroll, Priority.ALWAYS);

    /* Podcasts found block, we have a label and a HBox of AudioCards inside a ScrollPane */
    Label foundPodcastsLabel=new Label("Podcasts");
    foundPodcastsLabel.setFont(fontFindings);
    foundPodcastsLabel.setTextFill(Color.LIGHTGRAY);
    foundPodcastsLabel.setPadding(new Insets(40,0, 40, 0));
    HBox podcastsHBox=new HBox(50, new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard());
    ScrollPane podcastScroll=new ScrollPane(podcastsHBox);
    podcastScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    podcastScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    podcastScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    VBox.setVgrow(podcastScroll, Priority.ALWAYS);

    /* Podcasts found block, we have a label and a HBox of AudioCards inside a ScrollPane */
    Label foundPlaylistsLabel=new Label("Playlists");
    foundPlaylistsLabel.setFont(fontFindings);
    foundPlaylistsLabel.setTextFill(Color.LIGHTGRAY);
    foundPlaylistsLabel.setPadding(new Insets(40,0, 40, 0));
    HBox playlistsHBox=new HBox(50, new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard());
    ScrollPane playlistScroll=new ScrollPane(playlistsHBox);
    playlistScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    playlistScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    playlistScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    VBox.setVgrow(playlistScroll, Priority.ALWAYS);

    /* Episodes found block, we have a label and a HBox of AudioCards inside a ScrollPane */
    Label foundEpisodesLabel=new Label("Episodes");
    foundEpisodesLabel.setFont(fontFindings);
    foundEpisodesLabel.setTextFill(Color.LIGHTGRAY);
    foundEpisodesLabel.setPadding(new Insets(40,0, 40, 0));
    HBox episodesHBox=new HBox(50, new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard());
    ScrollPane episodesScroll=new ScrollPane(episodesHBox);
    episodesScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    episodesScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    episodesScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    VBox.setVgrow(episodesScroll, Priority.ALWAYS);

    /* Episodes found block, we have a label and a HBox of AudioCards inside a ScrollPane */
    Label foundProfilesLabel=new Label("Profiles");
    foundProfilesLabel.setFont(fontFindings);
    foundProfilesLabel.setTextFill(Color.LIGHTGRAY);
    foundProfilesLabel.setPadding(new Insets(40,0, 40, 0));
    HBox profilesHBox=new HBox(50, new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard());
    ScrollPane profilesScroll=new ScrollPane(profilesHBox);
    profilesScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    profilesScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    profilesScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    VBox.setVgrow(profilesScroll, Priority.ALWAYS);

    VBox mainVBox=new VBox(bestResultsHBox, foundArtistsLabel, artistScroll, foundAlbumsLabel, albumsScroll, foundPodcastsLabel, podcastScroll, foundPlaylistsLabel, playlistScroll, foundEpisodesLabel, episodesScroll, foundProfilesLabel, profilesScroll);

    setContent(mainVBox);
    setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    setFitToWidth(true);
    setPadding(new Insets(10));
  }
  /*---------------------------------------*/
}
