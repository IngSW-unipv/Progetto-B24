package it.unipv.ingsfw.JavaBeats.view.presets.dialogs;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.PipedOutputStream;

public class EditProfileDialog extends Dialog<JBCollection>{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final Background bgHome=new Background(new BackgroundFill(Color.rgb(20, 20, 20), CornerRadii.EMPTY, Insets.EMPTY));
  private JBProfile originalProfile;
  private JBProfile newProfile;
  private ImageView collectionImageView;
  private TextField usernameTextField;
  private TextField nameTextField;
  private TextField surnameTextField;
  private TextArea biography;
  private Button inputImageButton;
  private Button saveButton;
  private Button cancelButton;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public EditProfileDialog(Stage stage, JBProfile originalProfile, JBProfile newProfile){
    super();
    this.originalProfile=originalProfile;
    this.newProfile=newProfile;
    initOwner(stage);
    initStyle(StageStyle.UNDECORATED);
    initComponents();
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public Button getInputImageButton(){
    return inputImageButton;
  }

  public ImageView getCollectionImageView(){
    return collectionImageView;
  }
  public JBProfile getOriginalProfile(){
    return originalProfile;
  }
  public JBProfile getNewProfile(){
    return newProfile;
  }
  public Button getSaveButton(){
    return saveButton;
  }
  public Button getCancelButton(){
    return cancelButton;
  }
  public TextField getUsernameTextField(){
    return usernameTextField;
  }
  public TextField getNameTextField(){
    return nameTextField;
  }
  public TextField getSurnameTextField(){
    return surnameTextField;
  }
  public TextArea getBiography(){
    return biography;
  }
  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(){
    Label editLabel=new Label("Edit details");
    editLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
    editLabel.setTextFill(Color.LIGHTGRAY);
    editLabel.setPadding(new Insets(0, 0, 10, 0));

    HBox editLabelCloseButtonHBox=new HBox(editLabel);
    editLabelCloseButtonHBox.setAlignment(Pos.CENTER_LEFT);

    Image collectionImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/RecordBig.png", true);
    collectionImageView=new ImageView(collectionImage);
    collectionImageView.setPreserveRatio(true);
    collectionImageView.setFitHeight(250);
    inputImageButton=new Button();
    inputImageButton.setGraphic(collectionImageView);
    inputImageButton.setBackground(bgHome);
    inputImageButton.setCursor(Cursor.HAND);

    usernameTextField=new TextField(originalProfile.getUsername());
    usernameTextField.getStyleClass().add("textField");
    usernameTextField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));

    nameTextField=new TextField(originalProfile.getName());
    nameTextField.getStyleClass().add("textField");
    nameTextField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));

    surnameTextField=new TextField(originalProfile.getSurname());
    surnameTextField.getStyleClass().add("textField");
    surnameTextField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));

    HBox nameSurnameHBox=new HBox(10, nameTextField, surnameTextField);
    HBox.setHgrow(nameTextField, Priority.ALWAYS);
    HBox.setHgrow(surnameTextField, Priority.ALWAYS);

    biography=new TextArea(originalProfile.getBiography());
    biography.getStyleClass().add("textArea");

    VBox namePrivacyVBox=new VBox(10, usernameTextField, nameSurnameHBox, biography);
    namePrivacyVBox.setAlignment(Pos.CENTER_LEFT);

    HBox imageTextInputHBox=new HBox(20, inputImageButton, namePrivacyVBox);
    imageTextInputHBox.setAlignment(Pos.CENTER_LEFT);

    BorderPane bp=new BorderPane();
    bp.setTop(editLabelCloseButtonHBox);
    bp.setCenter(imageTextInputHBox);
    bp.setPadding(new Insets(20));
    getDialogPane().setContent(bp);

    ButtonType saveButtonType=new ButtonType("Save", ButtonBar.ButtonData.CANCEL_CLOSE);
    ButtonType cancelButtonType=new ButtonType("Cancel", ButtonBar.ButtonData.BACK_PREVIOUS);
    getDialogPane().getButtonTypes().addAll(cancelButtonType, saveButtonType);
    ButtonBar buttonBar=(ButtonBar)getDialogPane().lookup(".button-bar");
    buttonBar.getButtons().forEach(b -> b.setCursor(Cursor.HAND));
    buttonBar.getButtons().get(0).setId("buttonCancel");
    buttonBar.getButtons().get(1).setId("buttonSave");

    saveButton=(Button)getDialogPane().lookupButton(saveButtonType);
    cancelButton=(Button)getDialogPane().lookupButton(cancelButtonType);
    getDialogPane().getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/Dialog.css");
    getDialogPane().getStyleClass().add("myDialog");
    getDialogPane().setPrefSize(600, 400);
  }
  /*---------------------------------------*/
}
