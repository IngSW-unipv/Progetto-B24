package it.unipv.ingsfw.JavaBeats.view.primary.home;
import it.unipv.ingsfw.JavaBeats.view.primary.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.primary.Songbar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
  private static final Background bgSidebar=new Background(new BackgroundFill(Color.rgb(10, 10, 10), CornerRadii.EMPTY, Insets.EMPTY));
  private static final Font fontMenu=Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 16);
  private VBox menuVBox;
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
    /* Setup of LEFT VBox -> Menu */
    Sidebar sb=new Sidebar();
    Songbar sob= new Songbar();

    BorderPane bp=new BorderPane();
    bp.setLeft(sb);
    bp.setBottom(sob);
    bp.setBackground(new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY)));

    scene=new Scene(bp, clientWidth, clientHeight);
  }
  /*---------------------------------------*/
}
