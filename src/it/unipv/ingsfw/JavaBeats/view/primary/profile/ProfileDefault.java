package it.unipv.ingsfw.JavaBeats.view.primary.profile;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.model.profile.User;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioCard;
import it.unipv.ingsfw.JavaBeats.view.presets.scrollpanes.ScrollPanePreset;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class ProfileDefault extends VBox{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final Background bgHome=new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY));
  private static final Font fontFindings=Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20);

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public ProfileDefault(JBProfile activeProfile, JBProfile searchedProfile){
    super();
    initComponents(activeProfile, searchedProfile);
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(JBProfile activeProfile, JBProfile searchedProfile){
    /* Code divided by the nature of searchedProfile: if it's a User we display all his public playlists, otherwise we display all the Artist collections */
    VBox mainVBox=null;
    try{
      /* You searched a user that is not you => we display their playlists */
      if(!activeProfile.equals(searchedProfile)){
        User u=(User)searchedProfile;

        /* Playlists found block, we have a label and a HBox of AudioCards inside a ScrollPanePreset */
        Label foundPlaylistsLabel=new Label("Playlists");
        foundPlaylistsLabel.setFont(fontFindings);
        foundPlaylistsLabel.setTextFill(Color.LIGHTGRAY);
        foundPlaylistsLabel.setPadding(new Insets(40, 0, 40, 0));
        Song s=new Song(1, "Title", new Artist("us", "mail", "psw"), null);
        HBox playlistsHBox=new HBox(50, new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile));
        ScrollPanePreset playlistScroll=new ScrollPanePreset(playlistsHBox);
        playlistScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
        playlistScroll.setHbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
        playlistScroll.setVbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
        VBox.setVgrow(playlistScroll, Priority.ALWAYS);

        mainVBox=new VBox(foundPlaylistsLabel, playlistScroll);
      }else{
        mainVBox=new VBox();
      }//end-if
    }catch(ClassCastException c){
      /* You searched an artist, and he's not you => we display all playlists, albums and podcasts */
      if(!activeProfile.equals(searchedProfile)){
        /* Albums found, we have a label and a HBox of AudioCards inside a ScrollPanePreset */
        Label foundAlbumsLabel=new Label("Albums");
        foundAlbumsLabel.setFont(fontFindings);
        foundAlbumsLabel.setTextFill(Color.LIGHTGRAY);
        foundAlbumsLabel.setPadding(new Insets(40, 0, 40, 0));
        Song s=new Song(1, "Title", new Artist("us", "mail", "psw"), null);
        HBox albumsHBox=new HBox(50, new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile));
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
        HBox podcastsHBox=new HBox(50, new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile));
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
        HBox playlistsHBox=new HBox(50, new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile), new AudioCard(activeProfile));
        ScrollPanePreset playlistScroll=new ScrollPanePreset(playlistsHBox);
        playlistScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
        playlistScroll.setHbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
        playlistScroll.setVbarPolicy(ScrollPanePreset.ScrollBarPolicy.NEVER);
        VBox.setVgrow(playlistScroll, Priority.ALWAYS);

        mainVBox=new VBox(foundAlbumsLabel, albumsScroll, foundPodcastsLabel, podcastScroll, foundPlaylistsLabel, playlistScroll);
      }else{
        mainVBox=new VBox();
      }//end-if
    }//end-try

    getChildren().add(mainVBox);
    getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/scrollbar.css");
  }
  /*---------------------------------------*/
}
