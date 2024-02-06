package it.unipv.ingsfw.JavaBeats.controller.handler.presets.dialogs;

import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.exceptions.UsernameAlreadyTakenException;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditPlaylistDialog;
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

public class EditPlaylistDialogHandler {
    /*---------------------------------------*/
    //Attributi
    /*---------------------------------------*/
    private EditPlaylistDialog playlistDialog;
    private byte[] fileContent;


    /*---------------------------------------*/
    //Costruttori
    /*---------------------------------------*/
    public EditPlaylistDialogHandler(EditPlaylistDialog playlistDialog) {
        this.playlistDialog = playlistDialog;
        initComponents();
    }
    /*---------------------------------------*/
    //Getter/Setter
    /*---------------------------------------*/

    /*---------------------------------------*/
    //Metodi
    /*---------------------------------------*/
    private void initComponents() {
        EventHandler<ActionEvent> inputImageButtonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select playlist image");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG file", "*png"));
                File f = fileChooser.showOpenDialog(stage);
                if (f != null) {
                    byte[] fileContent = new byte[(int) f.length()];
                    FileInputStream fileInputStream = null;
                    URL url = null;
                    try {
                        url = f.toURI().toURL();
                        fileInputStream = new FileInputStream(f);
                        fileInputStream.read(fileContent);
                        fileInputStream.close();
                        playlistDialog.getNewPlaylist().setPicture(new SerialBlob(fileContent));
                        playlistDialog.getCollectionImageView().setImage(new Image(url.toExternalForm(), true));
                    } catch (IOException | SQLException e) {
                        throw new RuntimeException(e);
                    }//end-try
                }
            }
        };

        EventHandler<ActionEvent> saveButtonHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {


                try {
                    if (fileContent != null)
                        playlistDialog.getNewPlaylist().setPicture(new SerialBlob(fileContent));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }//end-try

                playlistDialog.getNewPlaylist().setName(playlistDialog.getNameTextField().getText());
                playlistDialog.getNewPlaylist().setVisible(!playlistDialog.getCheckBox().isSelected());

            }
        };
        EventHandler<ActionEvent> cancelButtonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Premuto cancel");
            }
        };


        playlistDialog.getInputImageButton().setOnAction(inputImageButtonHandler);
        playlistDialog.getSaveButton().setOnAction(saveButtonHandler);
        playlistDialog.getCancelButton().setOnAction(cancelButtonHandler);
    }
    /*---------------------------------------*/
}
