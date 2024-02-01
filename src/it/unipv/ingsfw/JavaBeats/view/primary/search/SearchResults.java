package it.unipv.ingsfw.JavaBeats.view.primary.search;

import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.collection.Podcast;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
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
import javafx.util.Duration;

import java.sql.SQLException;
import java.util.ArrayList;

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

  private ArrayList<Song> songs;
  private ArrayList<Artist> artists;
  //private ArrayList<User> users;
  private ArrayList<Album> albums;
  private ArrayList<Podcast> podcasts;
  private ArrayList<Playlist> playlists;
  private ArrayList<Episode> episodes;


  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public SearchResults(ArrayList<ArrayList<IJBResearchable>> searchedList, JBProfile activeProfile, ArrayList<JBCollection> profilePlaylists){
    super();
    initComponents(searchedList, activeProfile, profilePlaylists);
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(ArrayList<ArrayList<IJBResearchable>> searchedList, JBProfile activeProfile, ArrayList<JBCollection> profilePlaylists){
    /*
     *   Setup of Top HBox component, containing a VBox with the top result and a VBox with the 4 best song.
     */

    //Top result

    //Top result label
    Label topResultLabel=new Label("Top result");
    topResultLabel.setFont(fontTopResult);
    topResultLabel.setTextFill(Color.LIGHTGRAY);

    //Top result image
    Image audioPic=null;
    try{
      audioPic=new Image(((Song)searchedList.get(0).get(0)).getMetadata().getCollection().getPicture().getBinaryStream());
    }catch(SQLException e){
      e.printStackTrace();
    }

    ImageView audioPicImageView=new ImageView(audioPic);
    audioPicImageView.setPreserveRatio(true);
    audioPicImageView.setFitWidth(150);

    //Top result title
    Label topResultTitleLabel=new Label(((Song)searchedList.get(0).get(0)).getMetadata().getTitle());
    topResultTitleLabel.setFont(fontTopAudio);
    topResultTitleLabel.setTextFill(Color.LIGHTGRAY);

    //Top result artist
    Label topResultArtistLabel=new Label(((Song)searchedList.get(0).get(0)).getMetadata().getArtist().getUsername());
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
    for(JBCollection collection: profilePlaylists){
      cb.getItems().add(collection.getName());
    }//end-foreach

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


    Label topSongsLabel=new Label("Songs");
    topSongsLabel.setFont(fontTopResult);
    topSongsLabel.setTextFill(Color.LIGHTGRAY);


    //HBox with topSongs
    HBox[] topSongs={new HBox(), new HBox(), new HBox(), new HBox()};
    for(int i=0; i<topSongs.length; i+=1){
      Image songPic=null;
      try{
        songPic=new Image(((Song)searchedList.get(0).get(i+1)).getMetadata().getCollection().getPicture().getBinaryStream());
      }catch(SQLException e){
        e.printStackTrace();
      }

      ImageView songPicImageView=new ImageView(songPic);
      songPicImageView.setPreserveRatio(true);
      songPicImageView.setFitHeight(55);

      Label songTitle=new Label(((Song)searchedList.get(0).get(i+1)).getMetadata().getTitle());
      songTitle.setFont(fontTitles);
      songTitle.setTextFill(Color.LIGHTGRAY);

      Label songArtist=new Label(((Song)searchedList.get(0).get(i+1)).getMetadata().getArtist().getUsername());
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

      for(JBCollection collection: profilePlaylists){
        c.getItems().add(collection.getName());
      }

      c.getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/choicebox.css");

      Button choiceButton=new Button();
      choiceButton.setGraphic(c);
      choiceButton.setStyle("-fx-background-color: transparent");
      HBox.setMargin(choiceButton, new Insets(0, 20, 0, 0));


      Label songDuration=new Label(Duration.minutes(((Song)searchedList.get(0).get(i)).getMetadata().getDuration()).toString());
      songDuration.setTextFill(Color.LIGHTGRAY);

      topSongs[i].getChildren().addAll(songPicImageView, songTitleArtistVBox, whitePane, addToLabel, choiceButton, songDuration);
      topSongs[i].setAlignment(Pos.CENTER_LEFT);
      topSongs[i].getStyleClass().add("HBox");
      topSongs[i].getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/topSongsHbox.css");
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

    HBox artistsHBox=new HBox(50);

    for(IJBResearchable ijbResearchable: searchedList.get(1)){
      artistsHBox.getChildren().add(new AudioCard(ijbResearchable));
    }

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

    HBox albumsHBox=new HBox(50);

    for(IJBResearchable ijbResearchable: searchedList.get(2)){
      albumsHBox.getChildren().add(new AudioCard(ijbResearchable));
    }

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

    HBox podcastsHBox=new HBox(50);

    for(IJBResearchable ijbResearchable: searchedList.get(3)){
      podcastsHBox.getChildren().add(new AudioCard(ijbResearchable));
    }

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

    HBox playlistsHBox=new HBox(50);

    for(IJBResearchable ijbResearchable: searchedList.get(4)){
      playlistsHBox.getChildren().add(new AudioCard(ijbResearchable));
    }

    ScrollPanePreset playlistScroll=new ScrollPanePreset(playlistsHBox);
    playlistScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    playlistScroll.setHbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    playlistScroll.setVbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    VBox.setVgrow(playlistScroll, Priority.ALWAYS);

    /* Episodes found block, we have a label and a HBox of AudioCards inside a ScrollPanePreset */
    Label foundEpisodesLabel=new Label("Episodes");
    foundEpisodesLabel.setFont(fontFindings);
    foundEpisodesLabel.setTextFill(Color.LIGHTGRAY);
    foundEpisodesLabel.setPadding(new Insets(40, 0, 40, 0));

    HBox episodesHBox=new HBox(50);

    for(IJBResearchable ijbResearchable: searchedList.get(5)){
      episodesHBox.getChildren().add(new AudioCard(ijbResearchable));
    }

    ScrollPanePreset episodesScroll=new ScrollPanePreset(episodesHBox);
    episodesScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    episodesScroll.setHbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    episodesScroll.setVbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    VBox.setVgrow(episodesScroll, Priority.ALWAYS);

    /* Users found block, we have a label and a HBox of AudioCards inside a ScrollPanePreset */
    Label foundProfilesLabel=new Label("Users");
    foundProfilesLabel.setFont(fontFindings);
    foundProfilesLabel.setTextFill(Color.LIGHTGRAY);
    foundProfilesLabel.setPadding(new Insets(40, 0, 40, 0));

    HBox profilesHBox=new HBox(50);

    for(IJBResearchable ijbResearchable: searchedList.get(6)){
      profilesHBox.getChildren().add(new AudioCard(ijbResearchable));
    }

    ScrollPanePreset profilesScroll=new ScrollPanePreset(profilesHBox);
    profilesScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    profilesScroll.setHbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    profilesScroll.setVbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
    VBox.setVgrow(profilesScroll, Priority.ALWAYS);

    VBox mainVBox=new VBox(bestResultsHBox, foundArtistsLabel, artistScroll, foundAlbumsLabel, albumsScroll, foundPodcastsLabel, podcastScroll, foundPlaylistsLabel, playlistScroll, foundEpisodesLabel, episodesScroll, foundProfilesLabel, profilesScroll);
    mainVBox.setPadding(new Insets(0, 0, 0, 20));

    setContent(mainVBox);
    setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF;");
    getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/scrollbar.css");
    setFitToWidth(true);
    setPadding(new Insets(10));
  }
  /*---------------------------------------*/
}
