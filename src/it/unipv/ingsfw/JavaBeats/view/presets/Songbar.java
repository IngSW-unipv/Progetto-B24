package it.unipv.ingsfw.JavaBeats.view.presets;
import com.pixelduke.control.skin.FXSkins;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
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
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.util.Arrays;

public class Songbar extends GridPane{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static Songbar instance=null;
  private static final Background bgSongbar=new Background(new BackgroundFill(Color.rgb(18, 18, 18), CornerRadii.EMPTY, Insets.EMPTY));
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private ImageView recordImageView;
  private Label songTitle;
  private Label songArtist;
  private Label songGenre;
  private Button buttonHeart;
  private Button buttonRandom;
  private Button buttonSkipBack;
  private Button buttonPlayPause;
  private Button buttonSkipForward;
  private Button buttonLoop;
  private Slider playSlider;
  private Label minutePassedLabel;
  private Label songLengthLabel;
  private Slider volumeSlider;

  /*---------------------------------------*/
  //Costruttore
  /*---------------------------------------*/
  private Songbar(){
    super();
    initComponents();
  }

  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public static Songbar getInstance(){
    if(instance==null){
      instance=new Songbar();
    } //end-if
    return instance;
  }

  public ImageView getRecordImageView(){
    return recordImageView;
  }

  public Label getSongTitle(){
    return songTitle;
  }

  public Label getSongArtist(){
    return songArtist;
  }

  public Label getSongGenre(){
    return songGenre;
  }

  public Button getButtonHeart(){
    return buttonHeart;
  }

  public Button getButtonRandom(){
    return buttonRandom;
  }

  public Button getButtonSkipBack(){
    return buttonSkipBack;
  }

  public Button getButtonPlayPause(){
    return buttonPlayPause;
  }

  public Button getButtonSkipForward(){
    return buttonSkipForward;
  }

  public Button getButtonLoop(){
    return buttonLoop;
  }

  public Slider getPlaySlider(){
    return playSlider;
  }

  public Label getMinutePassedLabel(){
    return minutePassedLabel;
  }

  public Label getSongLengthLabel(){
    return songLengthLabel;
  }

  public Slider getVolumeSlider(){
    return volumeSlider;
  }

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(){
    //SongBar: SongBox, PlayBox, VolumeBox

    //SongHbox

    //RecordImage
    recordImageView=new ImageView();
    recordImageView.setPreserveRatio(true);

    //Vbox
    songTitle=new Label();
    songTitle.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
    songTitle.setTextFill(Color.LIGHTGRAY);

    songArtist=new Label();
    songArtist.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
    songArtist.setTextFill(Color.LIGHTGRAY);

    songGenre=new Label();
    songGenre.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 12));
    songGenre.setTextFill(Color.LIGHTGRAY);

    VBox songLabelVbox=new VBox(5, songTitle, songArtist, songGenre);
    songLabelVbox.setAlignment(Pos.CENTER_LEFT);

    //Heart
    buttonHeart=new Button();
    buttonHeart.setBackground(bgSongbar);
    buttonHeart.setCursor(Cursor.HAND);
    buttonHeart.setTooltip(new Tooltip("Favorite"));

    HBox songHbox=new HBox(20, recordImageView, songLabelVbox, buttonHeart);
    songHbox.setAlignment(Pos.CENTER_LEFT);
    songHbox.setPadding(new Insets(5));

    //PlayVbox

    //Hbox 1

    //Random
    buttonRandom=new Button();
    buttonRandom.setBackground(bgSongbar);
    buttonRandom.setCursor(Cursor.HAND);
    buttonRandom.setTooltip(new Tooltip("Random"));

    //SkipBack
    buttonSkipBack=new Button();
    buttonSkipBack.setBackground(bgSongbar);
    buttonSkipBack.setCursor(Cursor.HAND);
    buttonSkipBack.setTooltip(new Tooltip("Previous"));

    //PlayPause
    buttonPlayPause=new Button();
    buttonPlayPause.setBackground(bgSongbar);
    buttonPlayPause.setCursor(Cursor.HAND);
    buttonPlayPause.setTooltip(new Tooltip("Play/Pause"));

    //SkipForward
    buttonSkipForward=new Button();
    buttonSkipForward.setBackground(bgSongbar);
    buttonSkipForward.setCursor(Cursor.HAND);
    buttonSkipForward.setTooltip(new Tooltip("Next"));

    //Loop
    buttonLoop=new Button();
    buttonLoop.setBackground(bgSongbar);
    buttonLoop.setCursor(Cursor.HAND);
    buttonLoop.setTooltip(new Tooltip("Loop"));


    HBox playHbox=new HBox(10, buttonRandom, buttonSkipBack, buttonPlayPause, buttonSkipForward, buttonLoop);
    playHbox.setAlignment(Pos.CENTER);

    //Hbox 2
    
    //Slider
    playSlider=new Slider();
    playSlider.setCursor(Cursor.HAND);
    playSlider.getStylesheets().add(FXSkins.getStylesheetURL());
    playSlider.getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/playslider.css");

    //Label
    minutePassedLabel=new Label();
    minutePassedLabel.setTextFill(Color.LIGHTGRAY);
    minutePassedLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));

    songLengthLabel=new Label();
    songLengthLabel.setTextFill(Color.LIGHTGRAY);
    songLengthLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));

    HBox sliderHbox=new HBox(minutePassedLabel, playSlider, songLengthLabel);
    sliderHbox.setAlignment(Pos.CENTER);
    HBox.setHgrow(playSlider, Priority.ALWAYS);

    VBox playVbox=new VBox(15, playHbox, sliderHbox);
    playVbox.setAlignment(Pos.CENTER);

    //Hbox 3

    //Slider
    volumeSlider=new Slider();
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
