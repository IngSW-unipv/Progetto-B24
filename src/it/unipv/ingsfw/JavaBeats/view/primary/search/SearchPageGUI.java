package it.unipv.ingsfw.JavaBeats.view.primary.search;

import it.unipv.ingsfw.JavaBeats.model.EJBENTITY;
import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import it.unipv.ingsfw.JavaBeats.view.presets.scrollpanes.ScrollPanePreset;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;

public class SearchPageGUI{
  /*---------------------------------------*/
  //Attributes
  /*---------------------------------------*/
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
  private static final Font fontUser=Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15);
  private static final Background bgSearchPage=new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY));
  private static final Background bgSearchBar=new Background(new BackgroundFill(Color.rgb(10, 10, 10), new CornerRadii(25), Insets.EMPTY));
  private static final Font fontSearchBar=Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 16);
  private Scene scene;
  private SearchResults searchResults;
  private SearchDefault searchDefault;
  private Button userProfileButton;
  private GridPane gp;
  private TextField searchTextField;

  /*---------------------------------------*/
  //Constructor
  /*---------------------------------------*/
  public SearchPageGUI(JBProfile activeProfile, EnumMap<EJBENTITY, ArrayList<IJBResearchable>> searchedMap, ArrayList<JBCollection> profilePlaylists){
    initComponents(activeProfile, searchedMap, profilePlaylists);
  }

  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public Scene getScene(){
    return scene;
  }

  public TextField getSearchTextField(){
    return searchTextField;
  }

  public SearchResults getSearchResults(){
    return searchResults;
  }

  public GridPane getGp(){
    return gp;
  }

  public Button getUserProfileButton(){
    return userProfileButton;
  }

  /*---------------------------------------*/
  //Methods
  /*---------------------------------------*/
  private void initComponents(JBProfile activeProfile, EnumMap<EJBENTITY, ArrayList<IJBResearchable>> searchedMap, ArrayList<JBCollection> profilePlaylists){

    //Search

    //Hbox searchbar


    //Search textfield
    searchTextField=new TextField();
    searchTextField.setPrefSize(500, 50);
    searchTextField.setFont(fontSearchBar);
    searchTextField.setBackground(bgSearchBar);
    searchTextField.setStyle("-fx-text-fill: #FFFFFFFF; -fx-prompt-text-fill: #DEDEDEAA;");
    searchTextField.setPromptText("Search here");



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
    userProfileButton=new Button(activeProfile.getUsername());
    userProfileButton.setBackground(bgSearchPage);
    userProfileButton.setGraphic(userPicImageView);
    userProfileButton.setCursor(Cursor.HAND);
    userProfileButton.setFont(fontUser);
    userProfileButton.setTextFill(Color.LIGHTGRAY);
    userProfileButton.setAlignment(Pos.CENTER_RIGHT);


    HBox searchBar=new HBox(20, searchTextField, whitePane, userProfileButton);
    searchBar.setPadding(new Insets(10, 0, 10, 5));
    searchBar.setAlignment(Pos.CENTER_LEFT);

    //ScrollPanePreset Search
    //Vbox Search
    VBox searchVBox=new VBox();
    searchVBox.setBackground(bgSearchPage);
    if(searchedMap==null){
      searchDefault=new SearchDefault();
      searchVBox.getChildren().addAll(searchBar, searchDefault);
    }else{
      searchResults=new SearchResults(searchedMap, activeProfile, profilePlaylists);
      searchVBox.getChildren().addAll(searchBar, searchResults);
      searchResults.getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/scrollbar.css");
    }//end-if


    /* Setup of left Sidebar, bottom songbar and center Search */
    Sidebar sidebar=Sidebar.getInstance(activeProfile);
    Songbar songbar=Songbar.getInstance();

    gp=new GridPane();
    gp.addRow(0, sidebar, searchVBox);
    gp.add(songbar, 0, 1, 2, 1);

    ColumnConstraints ccSidebar=new ColumnConstraints();
    ColumnConstraints ccHome=new ColumnConstraints();
    ccSidebar.setPercentWidth(20);
    ccHome.setPercentWidth(80);
    gp.getColumnConstraints().addAll(ccSidebar, ccHome);

    RowConstraints rcSongbar=new RowConstraints();
    RowConstraints rcSideHome=new RowConstraints();
    rcSongbar.setPercentHeight(12);
    rcSideHome.setPercentHeight(88);
    gp.getRowConstraints().addAll(rcSideHome, rcSongbar);

    scene=new Scene(gp, clientWidth, clientHeight);
  }
  /*---------------------------------------*/
}






