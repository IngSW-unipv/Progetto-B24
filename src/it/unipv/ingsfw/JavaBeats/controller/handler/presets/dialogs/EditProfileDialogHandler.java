package it.unipv.ingsfw.JavaBeats.controller.handler.presets.dialogs;

import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.exceptions.RegexException;
import it.unipv.ingsfw.JavaBeats.exceptions.SystemErrorException;
import it.unipv.ingsfw.JavaBeats.exceptions.UsernameAlreadyTakenException;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditProfileDialog;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.ExceptionDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
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

public class EditProfileDialogHandler{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private EditProfileDialog profileDialog;
  private byte[] fileContent=null;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public EditProfileDialogHandler(EditProfileDialog profileDialog){
    this.profileDialog=profileDialog;
    initComponents();
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(){
    EventHandler<ActionEvent> inputImageButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        FileChooser fileChooser=new FileChooser();
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

            profileDialog.getNewProfile().setProfilePicture(new SerialBlob(bytes));
            profileDialog.getProfileImageView().setImage(new Image(profileDialog.getNewProfile().getProfilePicture().getBinaryStream()));
          }catch(IOException | SQLException e){
            throw new RuntimeException(e);
          }//end-try
        }
      }
    };


    EventHandler<ActionEvent> saveButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=((Stage)profileDialog.getOwner());

        try{
          /* profile picture is already handled when adding it */
          profileDialog.getNewProfile().setBiography(profileDialog.getBiography().getText());
          profileDialog.getNewProfile().setName(profileDialog.getNameTextField().getText());
          profileDialog.getNewProfile().setSurname(profileDialog.getSurnameTextField().getText());
          profileDialog.getNewProfile().setUsername(profileDialog.getUsernameTextField().getText());

          /* Checking format of inserted parameters */
          ProfileManagerFactory.getInstance().getProfileManager().checkRegex(profileDialog.getNewProfile());

          /* Checking if the username is already in the DB only if it's different from the original */
          if(!profileDialog.getUsernameTextField().getText().equals(profileDialog.getOriginalProfile().getUsername())){
            ProfileManagerFactory.getInstance().getProfileManager().checkIfUsernameAlreadyExists(profileDialog.getNewProfile());
          }//end-if
        }catch(RegexException | UsernameAlreadyTakenException e){

          System.out.println("BROSKI SONO ENTRATO");

          /* Reverting to original profile */
          profileDialog.getNewProfile().setProfilePicture(profileDialog.getOriginalProfile().getProfilePicture());
          profileDialog.getNewProfile().setUsername(profileDialog.getOriginalProfile().getUsername());
          profileDialog.getNewProfile().setName(profileDialog.getOriginalProfile().getName());
          profileDialog.getNewProfile().setSurname(profileDialog.getOriginalProfile().getSurname());
          profileDialog.getNewProfile().setBiography(profileDialog.getOriginalProfile().getBiography());

          profileDialog.getDialogPane().setEffect(new BoxBlur(10, 10, 10));

          ExceptionDialog exceptionDialog=new ExceptionDialog(stage, e);
          exceptionDialog.showAndWait();

          profileDialog.getDialogPane().setEffect(null); /* Removing blur effect */
        }//end-try
      }
    };
    EventHandler<ActionEvent> cancelButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
      }
    };
    profileDialog.getInputImageButton().setOnAction(inputImageButtonHandler);
    profileDialog.getSaveButton().setOnAction(saveButtonHandler);
    profileDialog.getCancelButton().setOnAction(cancelButtonHandler);
  }
  /*---------------------------------------*/
}
