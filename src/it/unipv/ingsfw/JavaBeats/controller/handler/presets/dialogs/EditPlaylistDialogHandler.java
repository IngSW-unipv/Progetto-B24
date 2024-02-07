package it.unipv.ingsfw.JavaBeats.controller.handler.presets.dialogs;

import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditPlaylistDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class EditPlaylistDialogHandler{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private EditPlaylistDialog playlistDialog;
  private byte[] fileContent;


  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public EditPlaylistDialogHandler(EditPlaylistDialog playlistDialog){
    this.playlistDialog=playlistDialog;
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
        fileChooser.setTitle("Select playlist image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG file", "*png"));
        File f=fileChooser.showOpenDialog(stage);
        if(f!=null){
          byte[] fileContent=new byte[(int)f.length()];
          FileInputStream fileInputStream=null;
          URL url=null;
          try{
            url=f.toURI().toURL();
            fileInputStream=new FileInputStream(f);
            fileInputStream.read(fileContent);
            fileInputStream.close();

            BufferedImage bufferedImage=ImageIO.read(url);
            java.awt.Image resultingImage=bufferedImage.getScaledInstance(250, 250, java.awt.Image.SCALE_DEFAULT);
            BufferedImage outputImage=new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);
            outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);

            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            ImageIO.write(outputImage, "png", baos);
            byte[] bytes=baos.toByteArray();

            playlistDialog.getNewPlaylist().setPicture(new SerialBlob(bytes));
            playlistDialog.getCollectionImageView().setImage(new Image(playlistDialog.getNewPlaylist().getPicture().getBinaryStream()));
          }catch(IOException | SQLException e){
            throw new RuntimeException(e);
          }//end-try
        }
      }
    };
    EventHandler<ActionEvent> saveButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){


        try{
          if(fileContent!=null)
            playlistDialog.getNewPlaylist().setPicture(new SerialBlob(fileContent));
        }catch(SQLException e){
          throw new RuntimeException(e);
        }//end-try

        playlistDialog.getNewPlaylist().setName(playlistDialog.getNameTextField().getText());
        playlistDialog.getNewPlaylist().setVisible(!playlistDialog.getCheckBox().isSelected());

      }
    };
    EventHandler<ActionEvent> cancelButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        System.out.println("Premuto cancel");
      }
    };


    playlistDialog.getInputImageButton().setOnAction(inputImageButtonHandler);
    playlistDialog.getSaveButton().setOnAction(saveButtonHandler);
    playlistDialog.getCancelButton().setOnAction(cancelButtonHandler);
  }
  /*---------------------------------------*/
}
