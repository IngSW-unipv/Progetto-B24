package it.unipv.ingsfw.JavaBeats.view.presets;
import it.unipv.ingsfw.JavaBeats.model.playable.JBCollection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.xml.stream.events.EndDocument;

public class CollectionDialog extends Dialog<JBCollection>{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final Background bgHome=new Background(new BackgroundFill(Color.rgb(18, 18, 18), CornerRadii.EMPTY, Insets.EMPTY));
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
  private JBCollection originalCollection;
  private JBCollection newCollection;
  private Button inputImage;
  private CheckBox checkBox;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public CollectionDialog(Stage stage, JBCollection originalCollection){
    super();
    this.originalCollection=originalCollection;
//    newCollection=originalCollection.getCopy();
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
  public Button getInputImage(){
    return inputImage;
  }
  public JBCollection getNewCollection(){
    return newCollection;
  }
  public CheckBox getCheckBox(){
    return checkBox;
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
    ImageView collectionImageView=new ImageView(collectionImage);
    collectionImageView.setPreserveRatio(true);
    collectionImageView.setFitHeight(200);
    inputImage=new Button();
    inputImage.setGraphic(collectionImageView);
    inputImage.setBackground(bgHome);
    inputImage.setCursor(Cursor.HAND);

    TextField textField=new TextField(originalCollection.getName());
    textField.setId("TextField");
    textField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));

    checkBox=new CheckBox();
    checkBox.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15));
    checkBox.setText("Private");
    checkBox.setTextFill(Color.LIGHTGRAY);
    checkBox.setId("checkBox");

    VBox namePrivacyVBox=new VBox(100, textField, checkBox);
    namePrivacyVBox.setAlignment(Pos.CENTER_LEFT);

    HBox imageTextInputHBox=new HBox(20, inputImage, namePrivacyVBox);
    imageTextInputHBox.setAlignment(Pos.CENTER_LEFT);

    BorderPane bp=new BorderPane();
    bp.setTop(editLabelCloseButtonHBox);
    bp.setCenter(imageTextInputHBox);
    bp.setPadding(new Insets(20));
    getDialogPane().setContent(bp);

    ButtonType cancelButtonType=new ButtonType("Cancel", ButtonBar.ButtonData.BACK_PREVIOUS);
    ButtonType closeButtonType=new ButtonType("Save", ButtonBar.ButtonData.CANCEL_CLOSE);
    getDialogPane().getButtonTypes().addAll(cancelButtonType, closeButtonType);
    ButtonBar buttonBar=(ButtonBar)getDialogPane().lookup(".button-bar");
    buttonBar.getButtons().forEach(b -> b.setId("buttonTypes"));

//    Button startStopBtn=(Button)getDialogPane().lookupButton(resetBtnType);
//    Button closeBtn=(Button)getDialogPane().lookupButton(closeBtnType);
    getDialogPane().getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/Dialog.css");
    getDialogPane().getStyleClass().add("myDialog");
    getDialogPane().setPrefSize(600, 400);
  }
  /*---------------------------------------*/
}
