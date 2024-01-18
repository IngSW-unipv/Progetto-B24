package it.unipv.ingsfw.JavaBeats.view.presets;

import com.pixelduke.control.skin.FXSkins;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

public class Songbar extends GridPane{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static Songbar instance=null;
  private static final Background bgSongbar=new Background(new BackgroundFill(Color.rgb(18, 18, 18), CornerRadii.EMPTY, Insets.EMPTY));
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
  private String minutePassed=new String("00:00");
  private String songLength=new String("03:00");
  double min=00.00;
  double max=3;
  double value=1.30;
  /*---------------------------------------*/
  //Costruttore
  /*---------------------------------------*/
  private Songbar(JBProfile activeProfile){
    super();
    initComponents(activeProfile);
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public static Songbar getInstance(JBProfile activeProfile){
    if(instance==null){
      instance=new Songbar(activeProfile);
    }//end-if
    return instance;
  }
  public static void setInstanceNull(){
    instance=null;
  }
  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(JBProfile activeProfile){
    //SongBar: SongBox, PlayBox, VolumeBox

    //SongHbox

    //RecordImage
    Image recordImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Record.png", true);
    ImageView recordImageView=new ImageView(recordImage);
    recordImageView.setPreserveRatio(true);


    //Vbox
    Label songTitle=new Label("Unknown Title");
    songTitle.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
    songTitle.setTextFill(Color.LIGHTGRAY);

    Label songArtist=new Label("Unknown Artist");
    songArtist.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
    songArtist.setTextFill(Color.LIGHTGRAY);

    Label songGenre=new Label("Unknown Genre");
    songArtist.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 12));
    songArtist.setTextFill(Color.LIGHTGRAY);

    VBox songLabelVbox=new VBox(5, songTitle, songArtist, songGenre);
    songLabelVbox.setAlignment(Pos.CENTER_LEFT);

    //Heart
    Image heartImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyHeart.png", true);
    ImageView heartImageView=new ImageView(heartImage);
    heartImageView.setPreserveRatio(true);
    Button buttonHeart=new Button();
    buttonHeart.setGraphic(heartImageView);
    buttonHeart.setBackground(bgSongbar);
    buttonHeart.setCursor(Cursor.HAND);
    buttonHeart.setTooltip(new Tooltip("Favorite"));

    HBox songHbox=new HBox(20, recordImageView, songLabelVbox, buttonHeart);
    songHbox.setAlignment(Pos.CENTER_LEFT);
    songHbox.setPadding(new Insets(5));

    //PlayVbox

    //Hbox 1

    //Random
    Image randomImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyRandom.png", true);
    ImageView randomImageView=new ImageView(randomImage);
    randomImageView.setPreserveRatio(true);
    Button buttonRandom=new Button();
    buttonRandom.setGraphic(randomImageView);
    buttonRandom.setBackground(bgSongbar);
    buttonRandom.setCursor(Cursor.HAND);
    buttonRandom.setTooltip(new Tooltip("Random"));

    //SkipBack
    Image skipBackImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/SkipBack.png", true);
    ImageView skipBackImageView=new ImageView(skipBackImage);
    skipBackImageView.setPreserveRatio(true);
    Button buttonSkipBack=new Button();
    buttonSkipBack.setGraphic(skipBackImageView);
    buttonSkipBack.setBackground(bgSongbar);
    buttonSkipBack.setCursor(Cursor.HAND);
    buttonSkipBack.setTooltip(new Tooltip("Previous"));

    //PlayPause
    Image playpauseImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Play.png", true);
    ImageView playPauseImageView=new ImageView(playpauseImage);
    playPauseImageView.setPreserveRatio(true);
    Button buttonPlayPause=new Button();
    buttonPlayPause.setGraphic(playPauseImageView);
    buttonPlayPause.setBackground(bgSongbar);
    buttonPlayPause.setCursor(Cursor.HAND);
    buttonPlayPause.setTooltip(new Tooltip("Play/Pause"));

    //SkipForward
    Image skipForwardImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/SkipForward.png", true);
    ImageView skipForawradImageView=new ImageView(skipForwardImage);
    skipForawradImageView.setPreserveRatio(true);
    Button buttonSkipForward=new Button();
    buttonSkipForward.setGraphic(skipForawradImageView);
    buttonSkipForward.setBackground(bgSongbar);
    buttonSkipForward.setCursor(Cursor.HAND);
    buttonSkipForward.setTooltip(new Tooltip("Next"));

    //Loop
    Image loopImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyLoop.png", true);
    ImageView loopImageView=new ImageView(loopImage);
    loopImageView.setPreserveRatio(true);
    Button buttonLoop=new Button();
    buttonLoop.setGraphic(loopImageView);
    buttonLoop.setBackground(bgSongbar);
    buttonLoop.setCursor(Cursor.HAND);
    buttonLoop.setTooltip(new Tooltip("Loop"));


    HBox playHbox=new HBox(10, buttonRandom, buttonSkipBack, buttonPlayPause, buttonSkipForward, buttonLoop);
    playHbox.setAlignment(Pos.CENTER);

    //Hbox 2


    //Slider
    Slider playSlider=new Slider(min, max, value);
    playSlider.setCursor(Cursor.HAND);
    playSlider.getStylesheets().add(FXSkins.getStylesheetURL());
    playSlider.getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/playslider.css");

    //Label
    Label minutePassedLabel=new Label(minutePassed);
    minutePassedLabel.setTextFill(Color.LIGHTGRAY);
    minutePassedLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
    Label songLengthLabel=new Label(songLength);
    songLengthLabel.setTextFill(Color.LIGHTGRAY);
    songLengthLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));


    HBox sliderHbox=new HBox(minutePassedLabel, playSlider, songLengthLabel);
    sliderHbox.setAlignment(Pos.CENTER);
    HBox.setHgrow(playSlider, Priority.ALWAYS);

    VBox playVbox=new VBox(15, playHbox, sliderHbox);
    playVbox.setAlignment(Pos.CENTER);


    //Hbox 3

    //Slider
    Slider volumeSlider=new Slider();
    volumeSlider.getStylesheets().add(FXSkins.getStylesheetURL());
    volumeSlider.setCursor(Cursor.HAND);
    volumeSlider.getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/volumeslider.css");


    //Volume
    Image volumeImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Volume.png", true);
    ImageView volumeImageView=new ImageView(volumeImage);
    volumeImageView.setPreserveRatio(true);


    HBox volumeHbox=new HBox(5, volumeImageView, volumeSlider);
    volumeHbox.setAlignment(Pos.CENTER_RIGHT);
    volumeHbox.setPadding(new Insets(0, 20, 0, 0));

    //Alignemnt del grid pane

    addRow(0, songHbox, playVbox, volumeHbox);
    ColumnConstraints cc=new ColumnConstraints();
    cc.setPercentWidth((double)clientWidth/3);
    getColumnConstraints().add(cc);
    getColumnConstraints().add(cc);
    getColumnConstraints().add(cc);
    setBackground(bgSongbar);
    setAlignment(Pos.CENTER);
  }
  /*---------------------------------------*/
}
