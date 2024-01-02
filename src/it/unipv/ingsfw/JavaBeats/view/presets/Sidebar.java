package it.unipv.ingsfw.JavaBeats.view.presets;
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

public class Sidebar extends VBox{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
  private static final Background bgSidebar=new Background(new BackgroundFill(Color.rgb(10, 10, 10), CornerRadii.EMPTY, Insets.EMPTY));
  private static final Font fontMenu=Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 17);
  private static final Font fontLibrary=Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20);
  private Button homeButton;
  private Button searchButton;
  private Button favoritesButton;
  private Button playlistsButton;
  private Button albumButton;
  private Button podcastButton;
  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public Sidebar(){
    super();
    initComponents();
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public Button getHomeButton(){
    return homeButton;
  }
  public Button getSearchButton(){
    return searchButton;
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
  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(){
    /* Setup of LEFT VBox -> MenuVbox */
    Image homeImage = new Image("it/unipv/ingsfw/JavaBeats/view/icons/Home.png", true);
    ImageView homeImageView=new ImageView(homeImage);
    homeImageView.setPreserveRatio(true);
    homeButton=new Button("  Home");
    homeButton.setGraphic(homeImageView);
    homeButton.setContentDisplay(ContentDisplay.LEFT);
    homeButton.setFont(fontMenu);
    homeButton.setTextFill(Color.LIGHTGRAY);
    homeButton.setPadding(new Insets(0, 0, 0, 35));
    homeButton.setBorder(new Border(new BorderStroke(Color.BLUEVIOLET, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 4))));
    homeButton.setBackground(bgSidebar);
    homeButton.setCursor(Cursor.HAND);

    Image searchImage = new Image("it/unipv/ingsfw/JavaBeats/view/icons/Search.png", true);
    ImageView searchImageView=new ImageView(searchImage);
    searchImageView.setPreserveRatio(true);
    searchButton=new Button("  Search");
    searchButton.setGraphic(searchImageView);
    searchButton.setContentDisplay(ContentDisplay.LEFT);
    searchButton.setFont(fontMenu);
    searchButton.setTextFill(Color.LIGHTGRAY);
    searchButton.setPadding(new Insets(0, 0, 0, 37));
    searchButton.setBackground(bgSidebar);
    searchButton.setCursor(Cursor.HAND);
    VBox menuVBox=new VBox(homeButton, searchButton);

    /* Pane used just for padding */
    Pane whitePane1=new Pane();
    setVgrow(whitePane1, Priority.ALWAYS);

    /* Setup of LEFT VBox -> LibraryMenu */
    Image libraryImage = new Image("it/unipv/ingsfw/JavaBeats/view/icons/Library.png", true);
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
    Button[] allButtons={favoritesButton, playlistsButton, albumButton, podcastButton};
    for(Button b : allButtons){
      b.setBackground(bgSidebar);
      b.setFont(fontLibrary);
      b.setTextFill(Color.LIGHTGRAY);
      b.setCursor(Cursor.HAND);
      b.setPadding(new Insets(20, 0, 20, 35));
    }//end-for
    VBox libraryVBox=new VBox(libraryHBox, favoritesButton, playlistsButton, albumButton, podcastButton);

    Pane whitePane2=new Pane();
    setVgrow(whitePane2, Priority.ALWAYS);

    getChildren().addAll(menuVBox, whitePane1, libraryVBox, whitePane2);
    setBackground(bgSidebar);

    setMargin(homeButton, new Insets(25, 0, 20, 0));
    setMargin(libraryHBox, new Insets(0, 45, 0, 35));
  }
  /*---------------------------------------*/
}
