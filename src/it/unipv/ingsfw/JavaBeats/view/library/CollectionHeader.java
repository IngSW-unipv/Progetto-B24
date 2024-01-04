package it.unipv.ingsfw.JavaBeats.view.library;

import it.unipv.ingsfw.JavaBeats.model.playable.EJBPLAYABLE;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public CollectionHeader(EJBPLAYABLE collectionType){
    initComponents(collectionType);
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

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
    collectionTitle.setPadding(new Insets(0, 0, 30, 0));

    /* Button with user's profile picture and username */
    Image userPic=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/DefaultUser.png", true);
    ImageView userPicImageView=new ImageView(userPic);
    userPicImageView.setPreserveRatio(true);
    userPicImageView.setFitHeight(20);
    Button userProfileButton=new Button("Creator");
    userProfileButton.setGraphic(userPicImageView);
    userProfileButton.setBackground(bgPills);
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

    HBox buttonsHBbox=new HBox();

    getChildren().addAll(topViewHBox, buttonsHBbox);
    setPadding(new Insets(50, 0, 50, 50));
    setBackground(bgHome);
  }
  /*---------------------------------------*/
}
