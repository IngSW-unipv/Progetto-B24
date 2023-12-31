package it.unipv.ingsfw.JavaBeats.view.library;
import it.unipv.ingsfw.JavaBeats.model.playable.EJBPLAYABLE;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class CollectionHeader extends VBox{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final Font fontUser=Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15);
  private static final Background bgHome=new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY));
  private static final Background bgPills=new Background(new BackgroundFill(Color.rgb(18, 18, 18), new CornerRadii(25), Insets.EMPTY));
  private static final Font fontTitle=Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 90);
  private static final Font fontCollectionInfo=Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15);
  private Button editButton;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public CollectionHeader(EJBPLAYABLE collectionType){
    initComponents(collectionType);
  }

  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public Button getEditButton(){
    return editButton;
  }

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(EJBPLAYABLE collectionType){
    Image collectionImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/RecordBig.png", true);
    ImageView collectionImageView=new ImageView(collectionImage);
    collectionImageView.setPreserveRatio(true);
    collectionImageView.setFitHeight(250);

    //Con case switch
    Label collectionLabel=null;
    switch(collectionType){
      case PLAYLIST -> collectionLabel=new Label("Playlist");
      case ALBUM -> collectionLabel=new Label("Album");
      case PODCAST -> collectionLabel=new Label("Podcast");
      default -> collectionLabel=new Label("Error!");
    }//end-switch
    collectionLabel.setBackground(bgPills);
    collectionLabel.setPadding(new Insets(3));
    collectionLabel.setTextFill(Color.LIGHTGRAY);
    collectionLabel.setFont(fontCollectionInfo);

    Label collectionTitle=new Label("Really long title");
    collectionTitle.setFont(fontTitle);
    collectionTitle.setTextFill(Color.LIGHTGRAY);

    /* Button with user's profile picture and username */
    Image userPic=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/DefaultUser.png", true);
    ImageView userPicImageView=new ImageView(userPic);
    userPicImageView.setPreserveRatio(true);
    userPicImageView.setFitHeight(20);
    Button userProfileButton=new Button("Creator");
    userProfileButton.setGraphic(userPicImageView);
    userProfileButton.setStyle("-fx-background-color: #121212FF; -fx-background-radius: 10");
    userProfileButton.setFont(fontUser);
    userProfileButton.setTextFill(Color.LIGHTGRAY);

    Label numberOfAudiosLabel=new Label("12"+"songs");
    numberOfAudiosLabel.setFont(fontCollectionInfo);
    numberOfAudiosLabel.setTextFill(Color.LIGHTGRAY);
    numberOfAudiosLabel.setUnderline(true);

    Label totalDurationLabel=new Label("55"+"mins");
    totalDurationLabel.setFont(fontCollectionInfo);
    totalDurationLabel.setTextFill(Color.LIGHTGRAY);
    totalDurationLabel.setUnderline(true);

    HBox collectionInformation=new HBox(10, userProfileButton, numberOfAudiosLabel, totalDurationLabel);
    collectionInformation.setAlignment(Pos.CENTER_LEFT);

    VBox collectionTitleInfo=new VBox(5, collectionLabel, collectionTitle, collectionInformation);
    collectionTitleInfo.setAlignment(Pos.BOTTOM_LEFT);

    HBox topViewHBox=new HBox(15, collectionImageView, collectionTitleInfo);


    //Hbox buttons

    //PlayPause
    Image playpauseImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Play.png", true);
    ImageView playPauseImageView=new ImageView(playpauseImage);
    playPauseImageView.setPreserveRatio(true);
    Button buttonPlayPause=new Button();
    buttonPlayPause.setGraphic(playPauseImageView);
    buttonPlayPause.setStyle("-fx-background-color: #0F0F0FFF;");
    buttonPlayPause.setCursor(Cursor.HAND);
    buttonPlayPause.setTooltip(new Tooltip("Play/Pause"));


    //Random
    Image randomImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyRandom.png", true);
    ImageView randomImageView=new ImageView(randomImage);
    randomImageView.setPreserveRatio(true);
    Button buttonRandom=new Button();
    buttonRandom.setGraphic(randomImageView);
    buttonRandom.setStyle("-fx-background-color: #0F0F0FFF;");
    buttonRandom.setCursor(Cursor.HAND);
    buttonRandom.setTooltip(new Tooltip("Random"));

    //Loop
    Image loopImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyLoop.png", true);
    ImageView loopImageView=new ImageView(loopImage);
    loopImageView.setPreserveRatio(true);
    Button buttonLoop=new Button();
    buttonLoop.setGraphic(loopImageView);
    buttonLoop.setStyle("-fx-background-color: #0F0F0FFF;");
    buttonLoop.setCursor(Cursor.HAND);
    buttonLoop.setTooltip(new Tooltip("Loop"));

    //Edit
    Image editImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Edit.png", true);
    ImageView editImageView=new ImageView(editImage);
    editImageView.setPreserveRatio(true);
    editButton=new Button();
    editButton.setGraphic(editImageView);
    editButton.setStyle("-fx-background-color: #0F0F0FFF;");
    editButton.setCursor(Cursor.HAND);
    editButton.setTooltip(new Tooltip("Edit"));

    //Bin
    Image binImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Bin.png", true);
    ImageView binImageView=new ImageView(binImage);
    binImageView.setPreserveRatio(true);
    Button buttonBin=new Button();
    buttonBin.setGraphic(binImageView);
    buttonBin.setStyle("-fx-background-color: #0F0F0FFF;");
    buttonBin.setCursor(Cursor.HAND);
    buttonBin.setTooltip(new Tooltip("Delete"));


    HBox buttonsHBbox=new HBox(buttonPlayPause, buttonRandom, buttonLoop, editButton, buttonBin);
    buttonsHBbox.setPadding(new Insets(40, 0, 0, 0));

    getChildren().addAll(topViewHBox, buttonsHBbox);
    setPadding(new Insets(50, 0, 30, 0));
    setBackground(bgHome);
  }
  /*---------------------------------------*/
}
