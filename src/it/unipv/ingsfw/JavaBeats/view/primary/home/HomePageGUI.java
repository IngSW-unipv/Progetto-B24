package it.unipv.ingsfw.JavaBeats.view.primary.home;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
public class HomePageGUI{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
  private Scene scene;
  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public HomePageGUI(){
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
    /* Setup of left Sidebar, bottom songbar and center home */
    Sidebar sidebar=new Sidebar();
    Songbar songbar= new Songbar();
    Home home=new Home();

    GridPane gp=new GridPane();
    gp.addRow(0, sidebar, home);
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
