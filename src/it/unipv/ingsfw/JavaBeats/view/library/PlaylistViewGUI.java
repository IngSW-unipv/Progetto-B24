package it.unipv.ingsfw.JavaBeats.view.library;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
public class PlaylistViewGUI{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final int clientWidth=(int) Screen.getPrimary().getBounds().getWidth();
  private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
  private Scene scene;
  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public PlaylistViewGUI(){
    initComponents();
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public Scene getScene(){
    return scene;
  }
  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(){
    Image playlistImage= new Image("it/unipv/ingsfw/JavaBeats/view/icons/Plus.png", true);
    ImageView playlistImageView=new ImageView(playlistImage);
    playlistImageView.setPreserveRatio(true);
    playlistImageView.setFitHeight(150);

    Label playlistTitle=new Label("Really long title");

    HBox userHBox=new HBox();
    HBox playlistInformation=new HBox();

    VBox playlistTitleInfo=new VBox(playlistTitle, playlistInformation);

    HBox topViewHBox=new HBox(playlistImageView, playlistTitleInfo);

    HBox buttonsHBbox=new HBox();



    VBox mainVbox=new VBox(topViewHBox, buttonsHBbox);

    /* Setup of left Sidebar, bottom songbar and center collection */
    Sidebar sidebar=Sidebar.getInstance();
    Songbar songbar=Songbar.getInstance();
    GridPane gp=new GridPane();
    gp.addRow(0, sidebar, mainVbox);
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
