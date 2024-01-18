package it.unipv.ingsfw.JavaBeats.view.presets;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SidebarHandler;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.model.profile.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

import java.util.*;

public class Sidebar extends VBox{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static Sidebar instance=null;
  private static JBProfile activeProfile=null;
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private static final Background bgSidebar=new Background(new BackgroundFill(Color.rgb(10, 10, 10), CornerRadii.EMPTY, Insets.EMPTY));
  private static final Font fontMenu=Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 17);
  private static final Font fontLibrary=Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20);
  private ArrayList<Button> allButtons=new ArrayList<>();
  private Button homeButton;
  private Button searchButton;
  private Button profileButton;
  private Button queueButton;
  private Button favoritesButton;
  private Button playlistsButton;
  private Button albumButton;
  private Button podcastButton;

  /*---------------------------------------*/
  //Costruttore
  /*---------------------------------------*/
  private Sidebar(JBProfile activeProfile){
    super();
    Sidebar.activeProfile=activeProfile;
    initComponents(activeProfile);
  }

  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public static Sidebar getInstance(JBProfile activeProfile){
    if(instance==null || Sidebar.activeProfile==null){
      instance=new Sidebar(activeProfile);
    }else if(!Sidebar.activeProfile.equals(activeProfile)){
      instance=new Sidebar(activeProfile);
    } //end-if
    return instance;
  }

  public Button getHomeButton(){
    return homeButton;
  }

  public Button getSearchButton(){
    return searchButton;
  }

  public Button getProfileButton(){
    return profileButton;
  }

  public Button getFavoritesButton(){
    return favoritesButton;
  }

  public Button getPlaylistsButton(){
    return playlistsButton;
  }

  public Button getAlbumButton(){
    return albumButton;
  }

  public Button getPodcastButton(){
    return podcastButton;
  }

  public Button getQueueButton(){
    return queueButton;
  }

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(JBProfile activeProfile){
    /* Setup of LEFT VBox -> MenuVbox */
    Image homeImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Home.png", true);
    ImageView homeImageView=new ImageView(homeImage);
    homeImageView.setPreserveRatio(true);
    homeButton=new Button("  Home");
    homeButton.setGraphic(homeImageView);
    homeButton.setContentDisplay(ContentDisplay.LEFT);
    homeButton.setFont(fontMenu);
    homeButton.setTextFill(Color.LIGHTGRAY);
    homeButton.setCursor(Cursor.HAND);
    Pane homePane=new Pane();
    HBox homeButtonHBox=new HBox(homeButton, homePane);
    HBox.setHgrow(homePane, Priority.ALWAYS);
    homeButtonHBox.setPadding(new Insets(5, 0, 5, 0));
    homeButtonHBox.getStyleClass().add("hbox");

    Image searchImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Search.png", true);
    ImageView searchImageView=new ImageView(searchImage);
    searchImageView.setPreserveRatio(true);
    searchButton=new Button("  Search");
    searchButton.setGraphic(searchImageView);
    searchButton.setContentDisplay(ContentDisplay.LEFT);
    searchButton.setFont(fontMenu);
    searchButton.setTextFill(Color.LIGHTGRAY);
    searchButton.setCursor(Cursor.HAND);
    Pane searchPane=new Pane();
    HBox searchButtonHBox=new HBox(searchButton, searchPane);
    HBox.setHgrow(searchPane, Priority.ALWAYS);
    searchButtonHBox.setPadding(new Insets(5, 0, 5, 0));
    searchButtonHBox.getStyleClass().add("hbox");

    Image profileImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/DefaultUser.png", true);
    ImageView profileImageView=new ImageView(profileImage);
    profileImageView.setPreserveRatio(true);
    profileImageView.setFitHeight(30);
    profileButton=new Button("  Profile");
    profileButton.setGraphic(profileImageView);
    profileButton.setContentDisplay(ContentDisplay.LEFT);
    profileButton.setFont(fontMenu);
    profileButton.setTextFill(Color.LIGHTGRAY);
    profileButton.setCursor(Cursor.HAND);
    Pane profilePane=new Pane();
    HBox profileButtonHBox=new HBox(profileButton, profilePane);
    HBox.setHgrow(profilePane, Priority.ALWAYS);
    profileButtonHBox.setPadding(new Insets(5, 0, 5, 0));
    profileButtonHBox.getStyleClass().add("hbox");


    Image queueImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Queue.png", true);
    ImageView queueImageView=new ImageView(queueImage);
    queueImageView.setPreserveRatio(true);
    queueImageView.setFitHeight(30);
    queueButton=new Button("  Queue");
    queueButton.setGraphic(queueImageView);
    queueButton.setContentDisplay(ContentDisplay.LEFT);
    queueButton.setFont(fontMenu);
    queueButton.setTextFill(Color.LIGHTGRAY);
    queueButton.setCursor(Cursor.HAND);
    Pane queuePane=new Pane();
    HBox queueButtonHBox=new HBox(queueButton, queuePane);
    HBox.setHgrow(queuePane, Priority.ALWAYS);
    queueButtonHBox.setPadding(new Insets(5, 0, 5, 0));
    queueButtonHBox.getStyleClass().add("hbox");

    VBox menuVBox=new VBox(homeButtonHBox, searchButtonHBox, profileButtonHBox, queueButtonHBox);
    VBox.setMargin(searchButtonHBox, new Insets(3, 0, 3, 0));
    VBox.setMargin(queueButtonHBox, new Insets(3, 0, 0, 0));

    /* Pane used just for padding */
    Pane whitePane1=new Pane();
    setVgrow(whitePane1, Priority.ALWAYS);

    /* Setup of LEFT VBox -> LibraryMenu */
    Image libraryImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Library.png", true);
    ImageView libraryImageView=new ImageView(libraryImage);
    searchImageView.setPreserveRatio(true);
    Label libraryTitle=new Label("Your library");
    libraryTitle.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
    libraryTitle.setTextFill(Color.LIGHTGRAY);
    HBox libraryHBox=new HBox(15, libraryImageView, libraryTitle);
    libraryHBox.setAlignment(Pos.CENTER_LEFT);
    libraryHBox.setBorder(new Border(new BorderStroke(Color.BLUEVIOLET, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 2, 0))));

    favoritesButton=new Button("Favorites");
    playlistsButton=new Button("Playlists");
    albumButton=new Button("Albums");
    podcastButton=new Button("Podcasts");
    ArrayList<Button> libraryButtons=new ArrayList<>();
    try{
      User u=(User)activeProfile;
      Collections.addAll(libraryButtons, favoritesButton, playlistsButton);
      Collections.addAll(allButtons, homeButton, searchButton, profileButton, queueButton, favoritesButton, playlistsButton);
    }catch(ClassCastException c){
      Collections.addAll(libraryButtons, favoritesButton, playlistsButton, albumButton, podcastButton);
      Collections.addAll(allButtons, homeButton, searchButton, profileButton, queueButton, favoritesButton, playlistsButton, albumButton, podcastButton);
    }//end-try
    VBox libraryVBox=new VBox(libraryHBox);
    for(Button b: libraryButtons){
      b.setFont(fontMenu);
      b.setTextFill(Color.LIGHTGRAY);
      b.setCursor(Cursor.HAND);
      Pane libraryPane=new Pane();
      HBox libraryButtonHBox=new HBox(b, libraryPane);
      HBox.setHgrow(libraryPane, Priority.ALWAYS);
      libraryButtonHBox.setPadding(new Insets(10, 0, 10, 0));
      libraryButtonHBox.getStyleClass().add("hbox");
      libraryVBox.getChildren().add(libraryButtonHBox);
      VBox.setMargin(libraryButtonHBox, new Insets(3, 0, 2, 0));
    }//end-for

    Pane whitePane2=new Pane();
    setVgrow(whitePane2, Priority.ALWAYS);

    getChildren().addAll(menuVBox, whitePane1, libraryVBox, whitePane2);
    setBackground(bgSidebar);

    setMargin(menuVBox, new Insets(10, 35, 0, 20));
    setMargin(libraryVBox, new Insets(0, 25, 0, 20));
    getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/sidebar.css");
  }

  public void setActive(Button b){
    for(Button button: allButtons){
      button.getParent().getStyleClass().remove("active");
      button.getStyleClass().remove("active");
    }//end-for-each

    b.getParent().getStyleClass().add("active");
    b.getStyleClass().add("active");
  }
  /*---------------------------------------*/
}
