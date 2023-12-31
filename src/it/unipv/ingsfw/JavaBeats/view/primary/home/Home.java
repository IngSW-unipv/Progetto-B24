package it.unipv.ingsfw.JavaBeats.view.primary.home;
import it.unipv.ingsfw.JavaBeats.view.primary.AudioCard;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import java.time.LocalTime;
public class Home extends VBox{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final Font fontWelcome=Font.font("Verdana", FontWeight.BLACK, FontPosture.REGULAR, 25);
  private static final Font fontUser=Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15);
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
    userWelcome.setPadding(new Insets(0, 0, 0, 5));
    userWelcome.setFont(fontWelcome);
    userWelcome.setTextFill(Color.LIGHTGRAY);

    Pane whitePane=new Pane();
    HBox.setHgrow(whitePane, Priority.ALWAYS);

    Image userPic = new Image("it/unipv/ingsfw/JavaBeats/view/icons/DefaultUser.png", true);
    ImageView userPicImageView=new ImageView(userPic);
    userPicImageView.setPreserveRatio(true);
    Button userProfileButton=new Button("Username");
    userProfileButton.setBackground(bgHome);
    userProfileButton.setGraphic(userPicImageView);
    userProfileButton.setCursor(Cursor.HAND);
    userProfileButton.setFont(fontUser);
    userProfileButton.setTextFill(Color.LIGHTGRAY);

    HBox userHBox=new HBox(logoImageView, userWelcome, whitePane, userProfileButton);
    userHBox.setAlignment(Pos.CENTER_LEFT);
    userHBox.setPadding(new Insets(40, 10, 10, 10));

    Label recentlyLabel=new Label("Recently played");
    recentlyLabel.setFont(fontWelcome);
    recentlyLabel.setPadding(new Insets(30, 0, 30, 0));
    HBox songs=new HBox(50, new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard(), new AudioCard());

    ScrollPane songsScroll=new ScrollPane(songs);
    songsScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    setVgrow(songsScroll, Priority.ALWAYS);
    songsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    songsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


    Label recentPlaylistLabel=new Label("Recent playlist");
    recentPlaylistLabel.setFont(fontWelcome);
    recentPlaylistLabel.setPadding(new Insets(30,0, 30, 0));
    ScrollPane playlistScroll=new ScrollPane();
    playlistScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    setVgrow(playlistScroll, Priority.ALWAYS);

    VBox mainContent=new VBox(recentlyLabel, songsScroll, recentPlaylistLabel, playlistScroll);

    ScrollPane contentScroll=new ScrollPane(mainContent);
    setVgrow(contentScroll, Priority.ALWAYS);
    contentScroll.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    contentScroll.setPadding(new Insets(15));
    contentScroll.setFitToWidth(true);

    getChildren().addAll(userHBox, contentScroll);
    setBackground(bgHome);
  }
  /*---------------------------------------*/
}
