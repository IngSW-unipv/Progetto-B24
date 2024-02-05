package it.unipv.ingsfw.JavaBeats.controller.handler.presets.dialogs;

import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditPlaylistDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
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
    private EditPlaylistDialog gui;

    /*---------------------------------------*/
    //Costruttori
    /*---------------------------------------*/
    public EditPlaylistDialogHandler(EditPlaylistDialog gui) {
        this.gui = gui;
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
                byte[] fileContent = new byte[(int) f.length()];
                FileInputStream fileInputStream = null;
                URL url = null;
                try {
                    url = f.toURI().toURL();
                    fileInputStream = new FileInputStream(f);
                    fileInputStream.read(fileContent);
                    fileInputStream.close();
                    gui.getNewPlaylist().setPicture(new SerialBlob(fileContent));
                    gui.getCollectionImageView().setImage(new Image(url.toExternalForm(), true));
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }//end-try
            }
        };
        gui.getInputImageButton().setOnAction(inputImageButtonHandler);
    }
    /*---------------------------------------*/
}
