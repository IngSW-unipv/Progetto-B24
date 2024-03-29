package it.unipv.ingsfw.JavaBeats.view.primary.home;

import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Screen;

public class HomePageGUI{
  /*---------------------------------------*/
  //Attributes
  /*---------------------------------------*/
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
  private Home home;
  private GridPane gp;
  private Scene scene;

  /*---------------------------------------*/
  //Constructor
  /*---------------------------------------*/
  public HomePageGUI(JBProfile activeProfile){
    initComponents(activeProfile);
  }

  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public Home getHome(){
    return home;
  }

  public Scene getScene(){
    return scene;
  }

  public GridPane getGp(){
    return gp;
  }

  /*---------------------------------------*/
  //Methods
  /*---------------------------------------*/
  private void initComponents(JBProfile activeProfile){
    /* Setup of left Sidebar, bottom songbar and center home */
    Sidebar sidebar=Sidebar.getInstance(activeProfile);
    Songbar songbar=Songbar.getInstance();
    home=new Home(activeProfile);

    gp=new GridPane();
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
