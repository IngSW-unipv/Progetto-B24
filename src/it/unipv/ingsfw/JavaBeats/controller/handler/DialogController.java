package it.unipv.ingsfw.JavaBeats.controller.handler;
import it.unipv.ingsfw.JavaBeats.view.presets.CollectionDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
public class DialogController{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private CollectionDialog gui;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public DialogController(CollectionDialog gui){
    this.gui=gui;
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
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG file", "*png"));
        File f=fileChooser.showOpenDialog(stage);
        byte[] fileContent=new byte[(int)f.length()];
        FileInputStream fileInputStream=null;
        try{
          fileInputStream=new FileInputStream(f);
          fileInputStream.read(fileContent);
          fileInputStream.close();
          gui.getNewCollection().setPicture(new SerialBlob(fileContent));
        }catch(IOException | SQLException e){
          throw new RuntimeException(e);
        }//end-try
      }
    };
    gui.getInputImage().setOnAction(inputImageButtonHandler);
  }
  /*---------------------------------------*/
}
