package it.unipv.ingsfw.JavaBeats.view.primary.search;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioCard;
import it.unipv.ingsfw.JavaBeats.view.presets.scrollpanes.ScrollPanePreset;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class SearchResults extends ScrollPanePreset{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final Font fontTopResult=Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20);
  private static final Font fontTopAudio=Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 24);
  private static final Font fontTitles=Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15);
  private static final Font fontArtists=Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 13);
  private static final Font fontAddTo=Font.font("Verdana", FontWeight.LIGHT, FontPosture.ITALIC, 12);
  private static final Font fontFindings=Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20);

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
    /*
     *   Setup of Top HBox component, containing a VBox with the top result and a VBox with the 4 best song.
     */
    Label topResultLabel=new Label("Top result");
    topResultLabel.setFont(fontTopResult);
    topResultLabel.setTextFill(Color.LIGHTGRAY);

    Image audioPic=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Record.png", true);
    ImageView audioPicImageView=new ImageView(audioPic);
    audioPicImageView.setPreserveRatio(true);
    audioPicImageView.setFitWidth(150);

    Label topResultTitleLabel=new Label("Really long title");
    topResultTitleLabel.setFont(fontTopAudio);
    topResultTitleLabel.setTextFill(Color.LIGHTGRAY);

    Label topResultArtistLabel=new Label("Artist");
    topResultArtistLabel.setTextFill(Color.LIGHTGRAY);

    VBox imageTitleArtistVBox=new VBox(10, audioPicImageView, topResultTitleLabel, topResultArtistLabel);
    imageTitleArtistVBox.setAlignment(Pos.CENTER_LEFT);
    imageTitleArtistVBox.setPadding(new Insets(40, 100, 0, 15));

    //Choicebox
    Label cblabel=new Label("Add to");
    cblabel.setFont(fontAddTo);
    cblabel.setTextFill(Color.LIGHTGRAY);
    cblabel.setPadding(new Insets(10, 0, 0, 0));

    ChoiceBox<String> cb=new ChoiceBox<String>();
    cb.getItems().add("Favorites");
    cb.getItems().add("Playlist 1");
    cb.getItems().add("Playlist 2");
    cb.getItems().add("Playlist 3");
    cb.getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/choicebox.css");

    Button cbButton=new Button();
    cbButton.setGraphic(cb);
    cbButton.setStyle("-fx-background-color: transparent");
    cbButton.setAlignment(Pos.CENTER);

    HBox cbHBox=new HBox(cblabel, cbButton);
    cbHBox.setPadding(new Insets(15, 0, 0, 15));

    /* VBox with the Top result */
    VBox topResultVbox=new VBox(topResultLabel, imageTitleArtistVBox, cbHBox);
    topResultVbox.setPadding(new Insets(20, 20, 20, 0));


    Label topSongsLabel=new Label("Songs and Episodes");
    topSongsLabel.setFont(fontTopResult);
    topSongsLabel.setTextFill(Color.LIGHTGRAY);


    HBox[] topSongs={new HBox(), new HBox(), new HBox(), new HBox()};
    for(HBox h: topSongs){
      Image songPic=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Record.png", true);
      ImageView songPicImageView=new ImageView(songPic);
      songPicImageView.setPreserveRatio(true);
      songPicImageView.setFitHeight(55);

      Label songTitle=new Label("Title");
      songTitle.setFont(fontTitles);
      songTitle.setTextFill(Color.LIGHTGRAY);

      Label songArtist=new Label("Artist");
      songArtist.setFont(fontArtists);
      songArtist.setTextFill(Color.LIGHTGRAY);

      VBox songTitleArtistVBox=new VBox(songTitle, songArtist);
      songTitleArtistVBox.setAlignment(Pos.CENTER_LEFT);
      songTitleArtistVBox.setPadding(new Insets(0, 0, 0, 10));

      Pane whitePane=new Pane();

      HBox.setHgrow(whitePane, Priority.ALWAYS);


      //Choicebox
      Label addToLabel=new Label("Add to");
      addToLabel.setFont(fontAddTo);
      addToLabel.setTextFill(Color.LIGHTGRAY);

      ChoiceBox<String> c=new ChoiceBox<String>();
      c.getItems().add("Favorites");
      c.getItems().add("Playlist 1");
      c.getItems().add("Playlist 2");
      c.getItems().add("Playlist 3");
      c.getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/choicebox.css");

      Button choiceButton=new Button();
      choiceButton.setGraphic(c);
      choiceButton.setStyle("-fx-background-color: transparent");
      HBox.setMargin(choiceButton, new Insets(0, 20, 0, 0));


      Label songDuration=new Label("03:55");
      songDuration.setTextFill(Color.LIGHTGRAY);

      h.getChildren().addAll(songPicImageView, songTitleArtistVBox, whitePane, addToLabel, choiceButton, songDuration);
      h.setAlignment(Pos.CENTER_LEFT);
      h.getStyleClass().add("HBox");
      h.getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/topSongsHbox.css");
    }//end-for

    /* VBox with the best 4 songs */
    VBox topSongsResultVbox=new VBox(20, topSongsLabel, topSongs[0], topSongs[1], topSongs[2], topSongs[3]);
    topSongsResultVbox.setPadding(new Insets(20, 20, 20, 0));



    /* final HBox with the VBoxes above */
    HBox bestResultsHBox=new HBox(topResultVbox, topSongsResultVbox);
    HBox.setHgrow(topSongsResultVbox, Priority.ALWAYS);

    /*
     *   Setup of all the other possible things searchable, in order, artists, albums, podcasts, playlists and episodes.
     */
    /* Artists found, we have a label and a HBox of AudioCards inside a ScrollPanePreset */
    Label foundArtistsLabel=new Label("Artists");
    foundArtistsLabel.setFont(fontFindings);
    foundArtistsLabel.setTextFill(Color.LIGHTGRAY);
    foundArtistsLabel.setPadding(new Insets(40, 0, 40, 0));
    Song s=new Song(1, "Title", new Artist("us", "mail", "psw"), null);
    HBox artistsHBox=new HBox(50, new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s));
    ScrollPanePreset artistScroll=new ScrollPanePreset(artistsHBox);
    artistScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    artistScroll.setHbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    artistScroll.setVbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    VBox.setVgrow(artistScroll, Priority.ALWAYS);

    /* Albums found, we have a label and a HBox of AudioCards inside a ScrollPanePreset */
    Label foundAlbumsLabel=new Label("Albums");
    foundAlbumsLabel.setFont(fontFindings);
    foundAlbumsLabel.setTextFill(Color.LIGHTGRAY);
    foundAlbumsLabel.setPadding(new Insets(40, 0, 40, 0));
    HBox albumsHBox=new HBox(50, new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s));
    ScrollPanePreset albumsScroll=new ScrollPanePreset(albumsHBox);
    albumsScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    albumsScroll.setHbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    albumsScroll.setVbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    VBox.setVgrow(albumsScroll, Priority.ALWAYS);

    /* Podcasts found block, we have a label and a HBox of AudioCards inside a ScrollPanePreset */
    Label foundPodcastsLabel=new Label("Podcasts");
    foundPodcastsLabel.setFont(fontFindings);
    foundPodcastsLabel.setTextFill(Color.LIGHTGRAY);
    foundPodcastsLabel.setPadding(new Insets(40, 0, 40, 0));
    HBox podcastsHBox=new HBox(50, new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s));
    ScrollPanePreset podcastScroll=new ScrollPanePreset(podcastsHBox);
    podcastScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    podcastScroll.setHbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    podcastScroll.setVbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    VBox.setVgrow(podcastScroll, Priority.ALWAYS);

    /* Playlists found block, we have a label and a HBox of AudioCards inside a ScrollPanePreset */
    Label foundPlaylistsLabel=new Label("Playlists");
    foundPlaylistsLabel.setFont(fontFindings);
    foundPlaylistsLabel.setTextFill(Color.LIGHTGRAY);
    foundPlaylistsLabel.setPadding(new Insets(40, 0, 40, 0));
    HBox playlistsHBox=new HBox(50, new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s));
    ScrollPanePreset playlistScroll=new ScrollPanePreset(playlistsHBox);
    playlistScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    playlistScroll.setHbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    playlistScroll.setVbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    VBox.setVgrow(playlistScroll, Priority.ALWAYS);

    /* Episodes found block, we have a label and a HBox of AudioCards inside a ScrollPanePreset */
    Label foundProfilesLabel=new Label("Profiles");
    foundProfilesLabel.setFont(fontFindings);
    foundProfilesLabel.setTextFill(Color.LIGHTGRAY);
    foundProfilesLabel.setPadding(new Insets(40, 0, 40, 0));
    HBox profilesHBox=new HBox(50, new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s), new AudioCard(s));
    ScrollPanePreset profilesScroll=new ScrollPanePreset(profilesHBox);
    profilesScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    profilesScroll.setHbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    profilesScroll.setVbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    VBox.setVgrow(profilesScroll, Priority.ALWAYS);

    VBox mainVBox=new VBox(bestResultsHBox, foundArtistsLabel, artistScroll, foundAlbumsLabel, albumsScroll, foundPodcastsLabel, podcastScroll, foundPlaylistsLabel, playlistScroll, foundProfilesLabel, profilesScroll);
    mainVBox.setPadding(new Insets(0, 0, 0, 20));

    setContent(mainVBox);
    setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF;");
    getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/scrollbar.css");
    setFitToWidth(true);
    setPadding(new Insets(10));
  }
  /*---------------------------------------*/
}
