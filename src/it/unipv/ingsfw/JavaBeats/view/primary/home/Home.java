package it.unipv.ingsfw.JavaBeats.view.primary.home;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import java.time.LocalTime;
public class Home extends VBox{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
  private static final Background bgHome=new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY));
  private static final LocalTime time=LocalTime.now();
  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public Home(){
    super();
    initComponents();
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(){
    Image logo = new Image("it/unipv/ingsfw/JavaBeats/view/icons/Logo.png", true);
    ImageView logoImageView=new ImageView(logo);
    logoImageView.setPreserveRatio(true);

    Label userWelcome;
    userWelcome=time.getHour()>17 ? new Label("Good evening, Username") : new Label("Good morning, Username");

    Pane whitePane=new Pane();
    HBox.setHgrow(whitePane, Priority.ALWAYS);

    Image userPic = new Image("it/unipv/ingsfw/JavaBeats/view/icons/DefaultUser.png", true);
    ImageView userPicImageView=new ImageView(userPic);
    userPicImageView.setPreserveRatio(true);
    Button userProfileButton=new Button("Username");
    userProfileButton.setBackground(bgHome);
    userProfileButton.setBorder(new Border(new BorderStroke(Color.BLUEVIOLET, BorderStrokeStyle.SOLID,new CornerRadii(25), new BorderWidths(1))));
    userProfileButton.setGraphic(userPicImageView);
    userProfileButton.setCursor(Cursor.HAND);

    HBox userHBox=new HBox(logoImageView, userWelcome, whitePane, userProfileButton);
    userHBox.setAlignment(Pos.CENTER_LEFT);

    ScrollPane contentScroll=new ScrollPane();
    setVgrow(contentScroll, Priority.ALWAYS);
    contentScroll.setBackground(bgHome);

    getChildren().addAll(userHBox, contentScroll);
    setBackground(bgHome);
  }
  /*---------------------------------------*/
}
