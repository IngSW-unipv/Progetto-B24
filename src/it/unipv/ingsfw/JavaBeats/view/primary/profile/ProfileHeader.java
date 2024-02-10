package it.unipv.ingsfw.JavaBeats.view.primary.profile;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.User;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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

import java.sql.SQLException;
import java.sql.Time;

public class ProfileHeader extends VBox{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final Font fontLabels=Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14);
  private static final Background bgHome=new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY));
  private ImageView profileImageView;
  private Label usernameLabel;
  private Label nameLabel;
  private Label surnameLabel;
  private TextArea biographyText;
  private Button switchButton;
  private Button editButton;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public ProfileHeader(JBProfile currentProfile, JBProfile searchedProfile){
    super();
    initComponents(currentProfile, searchedProfile);
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

  public Label getUsernameLabel(){
    return usernameLabel;
  }

  public Label getNameLabel(){
    return nameLabel;
  }

  public Label getSurnameLabel(){
    return surnameLabel;
  }

  public TextArea getBiographyText(){
    return biographyText;
  }

  public Button getSwitchButton(){
    return switchButton;
  }

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(JBProfile currentProfile, JBProfile searchedProfile){
    /* Dynamically choosign between user or artist label */
    Label profileType=null;
    try{
      User u=(User)searchedProfile;
      profileType=new Label("User");
    }catch(ClassCastException c){
      profileType=new Label("Artist");
    }//end-try
    profileType.setPadding(new Insets(5, 10, 5, 10));
    profileType.setFont(fontLabels);
    profileType.setTextFill(Color.BLUEVIOLET);
    profileType.setBorder(new Border(new BorderStroke(Color.BLUEVIOLET, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3), Insets.EMPTY)));

    /* Setup of username, name and surname labels */
    usernameLabel=new Label(searchedProfile.getUsername());
    usernameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 95));
    usernameLabel.setTextFill(Color.LIGHTGRAY);

    nameLabel=new Label(searchedProfile.getName());
    nameLabel.setFont(fontLabels);
    nameLabel.setTextFill(Color.LIGHTGRAY);
    nameLabel.setUnderline(true);

    surnameLabel=new Label(searchedProfile.getSurname());
    surnameLabel.setFont(fontLabels);
    surnameLabel.setTextFill(Color.LIGHTGRAY);
    surnameLabel.setUnderline(true);

    /* Dyanmically chosees between: minutes listened for users; all time total listeners for artists */
    Label listenersLabel=null;
    try{
      User u=(User)searchedProfile;
      listenersLabel=new Label(JBProfile.convertToHoursMinutesAndSeconds(u.getMinuteListened())+" total listening time");
    }catch(ClassCastException c){
      listenersLabel=new Label(((Artist)searchedProfile).getTotalListeners()+" total number of streams");
    }//end-try
    listenersLabel.setFont(fontLabels);
    listenersLabel.setTextFill(Color.LIGHTGRAY);
    listenersLabel.setUnderline(true);

    /* Hbox containing base info of profile */
    HBox nameSurnameMinutesHBox=new HBox(20, nameLabel, surnameLabel, listenersLabel);
    nameSurnameMinutesHBox.setPadding(new Insets(5, 0, 0, 0));

    /* VBox containing all the labels */
    VBox labelsVBox=new VBox(profileType, usernameLabel, nameSurnameMinutesHBox);
    labelsVBox.setAlignment(Pos.BOTTOM_LEFT);

    /* Setup of profile picture, put inside a HBox with labels */
    try{
      profileImageView=new ImageView(new Image(searchedProfile.getProfilePicture().getBinaryStream()));
    }catch(SQLException e){
      throw new RuntimeException(e);
    }//end-try
    profileImageView.setFitHeight(200);
    profileImageView.setPreserveRatio(true);
    HBox imageLabelsHBox=new HBox(10, profileImageView, labelsVBox);

    /* Biography text area setup */
    biographyText=new TextArea(searchedProfile.getBiography());
    biographyText.setEditable(false);
//    biographyText.setStyle("-fx-background-color: #0A0A0AFF");
    biographyText.getStyleClass().add("textArea");
    sizeTextArea(biographyText, searchedProfile.getBiography());

    HBox biographyHBox=new HBox(biographyText);
    HBox.setHgrow(biographyText, Priority.ALWAYS);

    /* Setup of button used for editing profile details but only if the searchedProfile is equals to the activeProfile */
    if(currentProfile.equals(searchedProfile)){
      Image editImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Edit.png", true);
      ImageView editImageView=new ImageView(editImage);
      editImageView.setPreserveRatio(true);
      editButton=new Button();
      editButton.setGraphic(editImageView);
      editButton.setStyle("-fx-background-color: blueviolet; -fx-background-radius: 15");
      editButton.setCursor(Cursor.HAND);
      editButton.setPadding(new Insets(20));
      editButton.setTooltip(new Tooltip("Edit"));

      /* Adding button to switch from user to artist and vice-versa */
      try{
        User u=(User)currentProfile;
        switchButton=new Button("Switch to artist");
      }catch(ClassCastException c){
        switchButton=new Button("Switch to user");
      }//end-try
      switchButton.setUnderline(true);
      switchButton.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 14));
      switchButton.setTextFill(Color.LIGHTGRAY);
      switchButton.setStyle("-fx-background-color: rgb(15, 15, 15)");
      switchButton.setCursor(Cursor.HAND);

      HBox editSwitchButtonHBox=new HBox(30, editButton, switchButton);
      editSwitchButtonHBox.setAlignment(Pos.BOTTOM_LEFT);

      getChildren().addAll(imageLabelsHBox, biographyHBox, editSwitchButtonHBox);
    }else{
      getChildren().addAll(imageLabelsHBox, biographyHBox);
    }//end-if

    /* adding everything to VBox */
    setAlignment(Pos.CENTER_LEFT);
    setSpacing(30);
  }

  /* Method used to calculate the size that the text will occupy inside the TextArea so that it can be resized accordingly */
  public void sizeTextArea(TextArea biographyText, String s){
    Text t=new Text(s);
    Pane p=new Pane(t);
    p.setPadding(new Insets(10));
    p.layout();
    biographyText.setPrefHeight(t.getLayoutBounds().getHeight()+20);
  }
  /*---------------------------------------*/
}
