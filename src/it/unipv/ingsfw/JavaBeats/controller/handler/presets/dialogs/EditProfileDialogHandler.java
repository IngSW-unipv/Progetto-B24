package it.unipv.ingsfw.JavaBeats.controller.handler.presets.dialogs;

import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.exceptions.UsernameAlreadyTakenException;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditProfileDialog;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.UsernameAlreadyTakenDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class EditProfileDialogHandler {
    /*---------------------------------------*/
    //Attributi
    /*---------------------------------------*/
    private EditProfileDialog profileDialog;
    private byte[] fileContent;

    /*---------------------------------------*/
    //Costruttori
    /*---------------------------------------*/
    public EditProfileDialogHandler(EditProfileDialog profileDialog) {
        this.profileDialog = profileDialog;
        initComponents();
    }
    /*---------------------------------------*/
    //Getter/Setter
    /*---------------------------------------*/

    /*---------------------------------------*/
    //Metodi
    /*---------------------------------------*/
    private void initComponents() {
        EventHandler<ActionEvent> inputImageButtonHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG file", "*png"));
                File f = fileChooser.showOpenDialog(stage);
                fileContent = new byte[(int) f.length()];
                FileInputStream fileInputStream = null;
                URL url = null;
                try {
                    url = f.toURI().toURL();
                    fileInputStream = new FileInputStream(f);
                    fileInputStream.read(fileContent);
                    fileInputStream.close();
                    profileDialog.getProfileImageView().setImage(new Image(url.toExternalForm(), true));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }//end-try
            }
        };
        EventHandler<ActionEvent> saveButtonHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Stage stage = ((Stage) profileDialog.getOwner());

                JBProfile tmpProfile = null;

                try {

                    tmpProfile = new Artist(profileDialog.getUsernameTextField().getText(), null, null);
                    ProfileManagerFactory.getInstance().getProfileManager().checkIfUsernameAlreadyExists(tmpProfile);
                    try {
                        if (fileContent != null)
                            profileDialog.getNewProfile().setProfilePicture(new SerialBlob(fileContent));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }//end-try
                    profileDialog.getNewProfile().setBiography(profileDialog.getBiography().getText());
                    profileDialog.getNewProfile().setName(profileDialog.getNameTextField().getText());
                    profileDialog.getNewProfile().setSurname(profileDialog.getSurnameTextField().getText());

                    profileDialog.getNewProfile().setUsername(profileDialog.getUsernameTextField().getText());
                } catch (UsernameAlreadyTakenException e) {

                    profileDialog.getDialogPane().setEffect(new BoxBlur(10, 10, 10));

                    UsernameAlreadyTakenDialog usernameAlreadyTakenDialog = new UsernameAlreadyTakenDialog(stage, e, tmpProfile);

                    usernameAlreadyTakenDialog.showAndWait();
                    profileDialog.getDialogPane().setEffect(null); /* Removing blur effect */
                }
            }
        };
        EventHandler<ActionEvent> cancelButtonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Premuto cancel");
            }
        };
        profileDialog.getInputImageButton().setOnAction(inputImageButtonHandler);
        profileDialog.getSaveButton().setOnAction(saveButtonHandler);
        profileDialog.getCancelButton().setOnAction(cancelButtonHandler);
    }
    /*---------------------------------------*/
}
