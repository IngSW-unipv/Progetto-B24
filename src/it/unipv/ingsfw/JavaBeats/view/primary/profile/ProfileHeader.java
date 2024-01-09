package it.unipv.ingsfw.JavaBeats.view.primary.profile;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.User;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.sql.Time;
public class ProfileHeader extends VBox{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final Background bgButton=new Background(new BackgroundFill(Color.BLUEVIOLET, new CornerRadii(15), Insets.EMPTY));
  private static final Font fontLabels=Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14);
  private ImageView profileImageView;
  private Button editButton;
  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public ProfileHeader(JBProfile activeProfile){
    initComponents(activeProfile);
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public ImageView getProfileImageView(){
    return profileImageView;
  }
  public Button getEditButton(){
    return editButton;
  }
  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(JBProfile activeProfile){

    Image profileImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/RecordBig.png", true);
    profileImageView=new ImageView(profileImage);
    profileImageView.setPreserveRatio(true);
    profileImageView.setFitHeight(200);

    Label profileType=null;
    try{
      User u=(User)activeProfile;
      profileType=new Label("User");
    }catch(ClassCastException c){
      profileType=new Label("Artist");
    }//end-try
    profileType.setPadding(new Insets(5, 10, 5, 10));
    profileType.setFont(fontLabels);
    profileType.setTextFill(Color.BLUEVIOLET);
    profileType.setBorder(new Border(new BorderStroke(Color.BLUEVIOLET, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3), Insets.EMPTY)));

    Label usernameLabel=new Label("Really long username");
    usernameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 95));
    usernameLabel.setTextFill(Color.LIGHTGRAY);

    Label nameLabel=new Label("Name");
    nameLabel.setFont(fontLabels);
    nameLabel.setTextFill(Color.LIGHTGRAY);
    nameLabel.setUnderline(true);

    Label surnameLabel=new Label("Surname");
    surnameLabel.setFont(fontLabels);
    surnameLabel.setTextFill(Color.LIGHTGRAY);
    surnameLabel.setUnderline(true);

    Label listenersLabel=null;
    try{
      User u=(User)activeProfile;
      u.setMinuteListened(new Time(4000));
      listenersLabel=new Label(u.getMinuteListened().getTime()+" minutes listened all time");
    }catch(ClassCastException c){
      listenersLabel=new Label(((Artist)activeProfile).getTotalListeners()+" all time total listeners");
    }//end-try
    listenersLabel.setFont(fontLabels);
    listenersLabel.setTextFill(Color.LIGHTGRAY);
    listenersLabel.setUnderline(true);

    HBox nameSurnameMinutesHBox=new HBox(20, nameLabel, surnameLabel, listenersLabel);

    VBox labelsVBox=new VBox(profileType, usernameLabel, nameSurnameMinutesHBox);
    labelsVBox.setAlignment(Pos.BOTTOM_LEFT);

    HBox imageLabelsHBox=new HBox(10, profileImageView, labelsVBox);

    activeProfile.setBiography("Incredible biography on how much i have accomplished during all my career because i'm the best artist in the whole universe, no one will ever catch me. \nAnd even if they did... I guess we'll never know.");
    TextArea biographyText=new TextArea(activeProfile.getBiography());
    biographyText.setEditable(false);
    biographyText.setStyle("-fx-background-color: #0A0A0AFF");
    biographyText.getStyleClass().add("textArea");
    Text t=new Text(activeProfile.getBiography());
    Pane p=new Pane(t);
    p.layout();
    biographyText.setPrefHeight(t.getLayoutBounds().getHeight()+20);

    HBox biographyHBox=new HBox(biographyText);
    HBox.setHgrow(biographyText, Priority.ALWAYS);

    Image editImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Edit.png", true);
    ImageView editImageView=new ImageView(editImage);
    editImageView.setPreserveRatio(true);
    editButton=new Button();
    editButton.setGraphic(editImageView);
    editButton.setBackground(bgButton);
    editButton.setCursor(Cursor.HAND);
    editButton.setPadding(new Insets(20));
    editButton.setTooltip(new Tooltip("Edit"));

    HBox editButtonHBox=new HBox(editButton);

    getChildren().addAll(imageLabelsHBox, biographyHBox, editButtonHBox);
    setAlignment(Pos.CENTER_LEFT);
    setSpacing(30);

  }
  /*---------------------------------------*/
}
