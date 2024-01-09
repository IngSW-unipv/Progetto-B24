package it.unipv.ingsfw.JavaBeats.view.presets.dialogs;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Playlist;
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

public class EditPlaylistDialog extends Dialog<JBCollection>{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final Background bgHome=new Background(new BackgroundFill(Color.rgb(20, 20, 20), CornerRadii.EMPTY, Insets.EMPTY));
  private Playlist originalCollection;
  private Playlist newCollection;
  private ImageView collectionImageView;
  private Button inputImageButton;
  private CheckBox checkBox;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public EditPlaylistDialog(Stage stage, Playlist originalCollection, Playlist newCollection){
    super();
    this.originalCollection=originalCollection;
    this.newCollection=newCollection;
    initOwner(stage);
    initStyle(StageStyle.UNDECORATED);
    initComponents();
  }

  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public JBCollection getOriginalCollection(){
    return originalCollection;
  }

  public Button getInputImageButton(){
    return inputImageButton;
  }

  public JBCollection getNewCollection(){
    return newCollection;
  }

  public CheckBox getCheckBox(){
    return checkBox;
  }

  public ImageView getCollectionImageView(){
    return collectionImageView;
  }

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(){
    Label editLabel=new Label("Edit details");
    editLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
    editLabel.setTextFill(Color.LIGHTGRAY);

    HBox editLabelCloseButtonHBox=new HBox(editLabel);
    editLabelCloseButtonHBox.setAlignment(Pos.CENTER_LEFT);

    Image collectionImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/RecordBig.png", true);
    collectionImageView=new ImageView(collectionImage);
    collectionImageView.setPreserveRatio(true);
    collectionImageView.setFitHeight(200);
    inputImageButton=new Button();
    inputImageButton.setGraphic(collectionImageView);
    inputImageButton.setBackground(bgHome);
    inputImageButton.setCursor(Cursor.HAND);

    TextField textField=new TextField(originalCollection.getName());
    textField.setId("TextField");
    textField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));

    checkBox=new CheckBox();
    Playlist p=(Playlist)originalCollection;
    p.setVisible(false); /* just trying the logic, IT MUST BE CHANGED */
    if(!p.isVisible()){
      checkBox.setSelected(true);
    }//endif
    checkBox.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15));
    checkBox.setText("Private");
    checkBox.setTextFill(Color.LIGHTGRAY);
    checkBox.setId("checkBox");


    VBox namePrivacyVBox=new VBox(100, textField, checkBox);
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

//    Button startStopBtn=(Button)getDialogPane().lookupButton(resetBtnType);
//    Button closeBtn=(Button)getDialogPane().lookupButton(closeBtnType);
    getDialogPane().getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/Dialog.css");
    getDialogPane().getStyleClass().add("myDialog");
    getDialogPane().setPrefSize(600, 400);
  }
  /*---------------------------------------*/
}
