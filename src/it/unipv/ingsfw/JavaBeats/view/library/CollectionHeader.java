package it.unipv.ingsfw.JavaBeats.view.library;

import it.unipv.ingsfw.JavaBeats.model.playable.EJBPLAYABLE;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

public class CollectionHeader extends VBox{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final Font fontUser=Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15);
  private static final Background bgHome=new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY));

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

    //Con case switch
    Label collectionLabel=new Label("Album");

    Label collectionTitle=new Label("Really long title");

    /* Button with user's profile picture and username */
    Image userPic=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/DefaultUser.png", true);
    ImageView userPicImageView=new ImageView(userPic);
    userPicImageView.setPreserveRatio(true);
    Button userProfileButton=new Button("Username");
    userProfileButton.setBackground(bgHome);
    userProfileButton.setGraphic(userPicImageView);
    userProfileButton.setCursor(Cursor.HAND);
    userProfileButton.setFont(fontUser);
    userProfileButton.setTextFill(Color.LIGHTGRAY);

    HBox collectionInformation=new HBox(userProfileButton, new Label("12 songs"), new Label("55min"));

    VBox collectionTitleInfo=new VBox(collectionLabel, collectionTitle, collectionInformation);

    HBox topViewHBox=new HBox(collectionImageView, collectionTitleInfo);

    HBox buttonsHBbox=new HBox();

    getChildren().addAll(topViewHBox, buttonsHBbox);
    setBackground(bgHome);
  }
  /*---------------------------------------*/
}
