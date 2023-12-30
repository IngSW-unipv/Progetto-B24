package it.unipv.ingsfw.JavaBeats.view.primary;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
  private static final Font fontMenu=Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 16);
  private VBox menuVBox;
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
  public VBox getMenuVBox(){
    return menuVBox;
  }
  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(){
    Image homeImage = new Image("it/unipv/ingsfw/JavaBeats/view/icons/Home.png", true);
    ImageView homeImageView=new ImageView(homeImage);
    homeImageView.setPreserveRatio(true);
    Label homeLabel=new Label("Home");
    homeLabel.setFont(fontMenu);
    homeLabel.setTextFill(Color.WHITE);
    HBox homeHBox=new HBox(15, homeImageView, homeLabel);
    homeHBox.setPadding(new Insets(0, 0, 0, 35));
    homeHBox.setAlignment(Pos.CENTER_LEFT);
    homeHBox.setCursor(Cursor.HAND);
    homeHBox.setBorder(new Border(new BorderStroke(Color.BLUEVIOLET, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 0, 4))));

    Image searchImage = new Image("it/unipv/ingsfw/JavaBeats/view/icons/Search.png", true);
    ImageView searchImageView=new ImageView(searchImage);
    searchImageView.setPreserveRatio(true);
    Label searchLabel=new Label("Search");
    searchLabel.setFont(fontMenu);
    searchLabel.setTextFill(Color.WHITE);
    HBox searchHBox=new HBox(15, searchImageView, searchLabel);
    searchHBox.setPadding(new Insets(20, 0, 0, 35));
    searchHBox.setAlignment(Pos.CENTER_LEFT);
    searchHBox.setCursor(Cursor.HAND);
    menuVBox=new VBox(homeHBox, searchHBox);

    Pane whitePane=new Pane();

    /* Setup of LEFT VBox -> LibraryMenu */
    VBox libraryVBox=new VBox();

    getChildren().addAll(menuVBox, whitePane, libraryVBox);
    setBackground(bgSidebar);
    setPrefWidth((double)clientWidth/5);
    setMargin(homeHBox, new Insets(25, 0, 0, 0));
  }
  /*---------------------------------------*/
}
