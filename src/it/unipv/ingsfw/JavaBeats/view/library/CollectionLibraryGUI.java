package it.unipv.ingsfw.JavaBeats.view.library;

import it.unipv.ingsfw.JavaBeats.model.playable.EJBPLAYABLE;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioCard;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import it.unipv.ingsfw.JavaBeats.view.presets.scrollpanes.ScrollPanePreset;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

import java.util.ArrayList;

public class CollectionLibraryGUI{

  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
  private static final Background bg=new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY));
  private static final Font fontCollection=Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25);
  private ArrayList<JBCollection> jbCollectionArrayList;
  private FlowPane collectionFlowPane;
  private Scene scene;


  public CollectionLibraryGUI(JBProfile activeProfile, ArrayList<JBCollection> jbCollectionArrayList, EJBPLAYABLE ejbplayable){
    this.jbCollectionArrayList=jbCollectionArrayList;
    initComponents(activeProfile, jbCollectionArrayList, ejbplayable);
  }


  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  public Scene getScene(){
    return scene;
  }

  public ArrayList<JBCollection> getJbCollectionArrayList(){
    return jbCollectionArrayList;
  }
  public FlowPane getCollectionFlowPane(){
    return collectionFlowPane;
  }
  /*---------------------------------------*/
  //Methods
  /*---------------------------------------*/
  private void initComponents(JBProfile activeProfile, ArrayList<JBCollection> jbCollectionArrayList, EJBPLAYABLE ejbplayable){

    //Label collection title

    Label collectionLabel=null;
    switch(ejbplayable){
      case PLAYLIST:
        collectionLabel=new Label("Your playlists");
        break;
      case ALBUM:
        collectionLabel=new Label("Your albums");
        break;
      case PODCAST:
        collectionLabel=new Label("Your podcasts");
        break;
    }//end-switch
    collectionLabel.setFont(fontCollection);
    collectionLabel.setTextFill(Color.LIGHTGRAY);
    collectionLabel.setAlignment(Pos.CENTER);
    collectionLabel.setPadding(new Insets(20));

    //Hbox title

    HBox titleHBox=new HBox(collectionLabel);

    //Flowpane

    collectionFlowPane=new FlowPane();
    for(JBCollection jb: jbCollectionArrayList){
      collectionFlowPane.getChildren().add(new AudioCard(jb));
    }//end-foreach
    collectionFlowPane.setPrefWrapLength(Double.MAX_VALUE);
    collectionFlowPane.setHgap(50);
    collectionFlowPane.setVgap(70);
    collectionFlowPane.setPadding(new Insets(20));


    //JBScrollPane
    ScrollPanePreset collectionScrollPanePreset=new ScrollPanePreset(collectionFlowPane);
    collectionScrollPanePreset.setFitToWidth(true);
    collectionScrollPanePreset.setFitToHeight(true);
    collectionScrollPanePreset.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    collectionScrollPanePreset.getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/scrollbar.css");

    //Add button
    Image plusImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Plus.png", true);
    ImageView plusImageView=new ImageView(plusImage);
    plusImageView.setPreserveRatio(true);
    plusImageView.setFitHeight(40);
    Button buttonPlus=new Button();
    buttonPlus.setGraphic(plusImageView);
    buttonPlus.setCursor(Cursor.HAND);
    buttonPlus.setStyle("-fx-background-radius: 30; -fx-pref-width: 60; -fx-pref-height: 60; -fx-background-color: #8A2BE2");


    //Hbox button
    HBox buttonHBox=new HBox(buttonPlus);
    buttonHBox.setAlignment(Pos.CENTER_RIGHT);
    buttonHBox.setPadding(new Insets(0, 20, 10, 0));

    //Vbox collection
    VBox collectionVBox=new VBox(25, titleHBox, collectionScrollPanePreset, buttonHBox);
    collectionVBox.setBackground(bg);
    VBox.setVgrow(collectionScrollPanePreset, Priority.ALWAYS);

    /* Setup of left Sidebar, bottom songbar and center collection */
    Sidebar sidebar=Sidebar.getInstance(activeProfile);
    Songbar songbar=Songbar.getInstance();
    GridPane gp=new GridPane();
    gp.addRow(0, sidebar, collectionVBox);
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
