package it.unipv.ingsfw.JavaBeats.view.presets.dialogs;

import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import javafx.geometry.Insets;
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

public class EditPlaylistDialog extends Dialog<JBCollection> {
    /*---------------------------------------*/
    //Attributi
    /*---------------------------------------*/
    private static final Background bgHome = new Background(new BackgroundFill(Color.rgb(20, 20, 20), CornerRadii.EMPTY, Insets.EMPTY));
    private Playlist originalPlaylist;
    private Playlist newPlaylist;
    private ImageView collectionImageView;
    private Button inputImageButton;
    private CheckBox checkBox;
    private Button saveButton;
    private Button cancelButton;
    private TextField nameTextField;

    /*---------------------------------------*/
    //Costruttori
    /*---------------------------------------*/
    public EditPlaylistDialog(Stage stage, Playlist originalPlaylist, Playlist newPlaylist) {
        super();
        this.originalPlaylist = originalPlaylist;
        this.newPlaylist = newPlaylist;
        initOwner(stage);
        initStyle(StageStyle.UNDECORATED); /* Remove unnecessary button to control the dialog */
        initComponents();
    }

    /*---------------------------------------*/
    //Getter/Setter
    /*---------------------------------------*/
    public JBCollection getOriginalPlaylist() {
        return originalPlaylist;
    }

    public Button getInputImageButton() {
        return inputImageButton;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public Playlist getNewPlaylist() {
        return newPlaylist;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public ImageView getCollectionImageView() {
        return collectionImageView;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    /*---------------------------------------*/
    //Metodi
    /*---------------------------------------*/
    private void initComponents() {
        /* Setup of ediLabel */
        Label editLabel = new Label("Edit details");
        editLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        editLabel.setTextFill(Color.LIGHTGRAY);
        HBox editLabelHBox = new HBox(editLabel);
        editLabelHBox.setAlignment(Pos.CENTER_LEFT);

        /* Setup of the collection's image */
        Image collectionImage = new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/RecordBig.png", true);
        collectionImageView = new ImageView(collectionImage);
        collectionImageView.setPreserveRatio(true);
        collectionImageView.setFitHeight(200);
        inputImageButton = new Button();
        inputImageButton.setGraphic(collectionImageView);
        inputImageButton.setBackground(bgHome);
        inputImageButton.setCursor(Cursor.HAND);

        /* Text field containing collection's name */
        nameTextField = new TextField(originalPlaylist.getName());
        nameTextField.setId("TextField");
        nameTextField.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        nameTextField.getStyleClass().add("textField");


        /* Check box to choose whether the playlist is private or public */
        checkBox = new CheckBox();
        if (!originalPlaylist.isVisible()) {
            checkBox.setSelected(true);
        }//endif
        checkBox.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15));
        checkBox.setText("Private");
        checkBox.setTextFill(Color.LIGHTGRAY);
        checkBox.setId("checkBox");

        /* VBox with text field and checkbox */
        VBox namePrivacyVBox = new VBox(100, nameTextField, checkBox);
        namePrivacyVBox.setAlignment(Pos.CENTER_LEFT);

        /* final HBox with all the above components */
        HBox imageTextInputHBox = new HBox(20, inputImageButton, namePrivacyVBox);
        imageTextInputHBox.setAlignment(Pos.CENTER_LEFT);

        /* Chosen layout to display the dialog */
        BorderPane bp = new BorderPane();
        bp.setTop(editLabelHBox);
        bp.setCenter(imageTextInputHBox);
        bp.setPadding(new Insets(20));
        getDialogPane().setContent(bp);

        /* Adding the functionality to save the edit or cancelling it using Button Types */
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.BACK_PREVIOUS);
        getDialogPane().getButtonTypes().addAll(cancelButtonType, saveButtonType);
        ButtonBar buttonBar = (ButtonBar) getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().forEach(b -> b.setCursor(Cursor.HAND));
        buttonBar.getButtons().get(0).setId("buttonCancel");
        buttonBar.getButtons().get(1).setId("buttonSave");

        /* expliciting the buttons to handle the interaction */
        saveButton = (Button) getDialogPane().lookupButton(saveButtonType);
        cancelButton = (Button) getDialogPane().lookupButton(cancelButtonType);
        getDialogPane().getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/dialog.css");
        getDialogPane().getStyleClass().add("myDialog");
        getDialogPane().setPrefSize(600, 400);
    }
    /*---------------------------------------*/
}
