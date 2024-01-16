package it.unipv.ingsfw.JavaBeats.controller.handler.library;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.view.library.CreationGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditPlaylistDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
public class CreationGUIHandler{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private final CreationGUI creationGUI;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public CreationGUIHandler(CreationGUI creationGUI){
    this.creationGUI=creationGUI;
    initComponents();
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(){
    EventHandler<ActionEvent> inputImageButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Select collection image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG file", "*png"));
        File f=fileChooser.showOpenDialog(stage);
        byte[] fileContent=new byte[(int)f.length()];
        FileInputStream fileInputStream=null;
        URL url=null;
        try{
          url=f.toURI().toURL();
          fileInputStream=new FileInputStream(f);
          fileInputStream.read(fileContent);
          fileInputStream.close();
          creationGUI.getNewCollection().setPicture(new SerialBlob(fileContent));
          creationGUI.getCollectionImageView().setImage(new Image(url.toExternalForm(), true));
        }catch(IOException | SQLException e){
          throw new RuntimeException(e);
        }//end-try
      }
    };
    EventHandler<ActionEvent> addAudioButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Add your audios");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 file", "*mp3"));
        List<File> fileList=fileChooser.showOpenMultipleDialog(stage);
        for(File f: fileList){
          byte[] fileContent=new byte[(int)f.length()];
          FileInputStream fileInputStream=null;
          URL url=null;
          try{
            url=f.toURI().toURL();
            fileInputStream=new FileInputStream(f);
            fileInputStream.read(fileContent);
            fileInputStream.close();
            Media music=new Media(f.toURI().toString());
//            music.getMetadata().get();
//            creationGUI.getNewCollection().getTrackList().add(new SerialBlob(fileContent));
            creationGUI.getCollectionImageView().setImage(new Image(url.toExternalForm(), true));
          }catch(IOException e){
            throw new RuntimeException(e);
          }//end-try
        }//end-foreach
      }
    };
    creationGUI.getCollectionPictureButton().setOnAction(inputImageButtonHandler);
    creationGUI.getAddButton().setOnAction(addAudioButtonHandler);
  }
  /*---------------------------------------*/
}
