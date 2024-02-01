package it.unipv.ingsfw.JavaBeats.view.library;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioTable;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import it.unipv.ingsfw.JavaBeats.view.presets.scrollpanes.ScrollPanePreset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.stage.Screen;

public class CollectionViewGUI{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
  private CollectionHeader collectionHeader;
  private JBCollection jbCollection;
  private TableView<JBAudio> audioTable;
  private GridPane gp;
  private Scene scene;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public CollectionViewGUI(JBProfile jbProfile, JBAudio currentAudio, JBCollection jbCollection){
    super();
    this.jbCollection=jbCollection;
    initComponents(jbProfile, currentAudio);
  }

  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public Scene getScene(){
    return scene;
  }

  public CollectionHeader getCollectionHeader(){
    return collectionHeader;
  }

  public JBCollection getJbCollection(){
    return jbCollection;
  }
  public TableView<JBAudio> getAudioTable(){
    return audioTable;
  }
  public GridPane getGp(){
    return gp;
  }

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(JBProfile activeProfile, JBAudio currentAudio){
    /* Setup of collectionHeader view */
    collectionHeader=new CollectionHeader(activeProfile, jbCollection);

    /* Setting up the ObservableList of JBAudios as parameter for AudioTable */
    ObservableList<JBAudio> audioList=FXCollections.observableArrayList();
    audioList.addAll(jbCollection.getTrackList());

    /* Creation of audioTable containing the list of all the playable audios */
    audioTable=new AudioTable(audioList, activeProfile, jbCollection);

    /* VBox containing all the main content */
    VBox mainVBox=new VBox(collectionHeader, audioTable);
    mainVBox.setPadding(new Insets(0, 50, 0, 50));

    /* ScrollPane for scrolling the audioTable */
    ScrollPanePreset ScrollPanePreset=new ScrollPanePreset(mainVBox);
    ScrollPanePreset.setFitToWidth(true);
    ScrollPanePreset.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    ScrollPanePreset.getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/scrollbar.css");
    VBox scrollableScrollPanePreset=new VBox(ScrollPanePreset);
    VBox.setVgrow(ScrollPanePreset, Priority.ALWAYS);

    /* Setup of left Sidebar, bottom songbar and center mainVBox */
    gp=new GridPane();
    gp.addRow(0, Sidebar.getInstance(activeProfile), scrollableScrollPanePreset);
    gp.add(Songbar.getInstance(activeProfile, currentAudio), 0, 1, 2, 1);

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
