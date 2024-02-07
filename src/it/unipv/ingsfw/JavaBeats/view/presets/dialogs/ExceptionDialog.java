package it.unipv.ingsfw.JavaBeats.view.presets.dialogs;

import it.unipv.ingsfw.JavaBeats.exceptions.IJBException;
import it.unipv.ingsfw.JavaBeats.exceptions.UsernameAlreadyTakenException;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ExceptionDialog extends Dialog<JBCollection>{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private Button closeButton;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public ExceptionDialog(Stage stage, IJBException exception){
    super();

    initOwner(stage);
    initStyle(StageStyle.UNDECORATED);
    initComponents(exception);
  }

  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  public Button getCloseButton(){
    return closeButton;
  }


  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(IJBException exception){

    //Label message
    Label messageLabel=new Label(exception.getMessage());
    messageLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
    messageLabel.setTextFill(Color.RED);
    messageLabel.setPadding(new Insets(0, 0, 10, 0));

    //Label alternative username
    Label alternativeUsernameLabel=new Label(exception.suggestAlternative());
    alternativeUsernameLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 20));
    alternativeUsernameLabel.setTextFill(Color.LIGHTGRAY);
    alternativeUsernameLabel.setPadding(new Insets(0, 0, 10, 0));

    VBox vBox=new VBox(messageLabel, alternativeUsernameLabel);
    vBox.setAlignment(Pos.CENTER);


    /* Chosen layout for the dialog */
    BorderPane bp=new BorderPane();
    bp.setCenter(vBox);
    bp.setPadding(new Insets(20));
    getDialogPane().setContent(bp);

    /* Adding the functionality to save or cancel the edits with button types */
    ButtonType closeButtonType=new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

    getDialogPane().getButtonTypes().add(closeButtonType);
    ButtonBar buttonBar=(ButtonBar)getDialogPane().lookup(".button-bar");
    buttonBar.getButtons().forEach(b -> b.setCursor(Cursor.HAND));
    buttonBar.getButtons().getFirst().setId("buttonSave");


    /* expliciting the button to handle the interaction */
    closeButton=(Button)getDialogPane().lookupButton(closeButtonType);

    getDialogPane().getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/dialog.css");
    getDialogPane().getStyleClass().add("myDialog");
    getDialogPane().setPrefSize(600, 400);
  }
  /*---------------------------------------*/
}
