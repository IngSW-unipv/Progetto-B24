package it.unipv.ingsfw.JavaBeats.controller.handler.library;

import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.exceptions.InvalidAudioException;
import it.unipv.ingsfw.JavaBeats.exceptions.InvalidJBEntityException;
import it.unipv.ingsfw.JavaBeats.exceptions.SystemErrorException;
import it.unipv.ingsfw.JavaBeats.model.EJBENTITY;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.collection.Podcast;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionLibraryGUI;
import it.unipv.ingsfw.JavaBeats.view.library.CreationGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.ExceptionDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class CreationGUIHandler{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private final CreationGUI creationGUI;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public CreationGUIHandler(CreationGUI creationGUI, JBProfile activeProfile){
    this.creationGUI=creationGUI;
    initComponents(activeProfile);
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(JBProfile activeProfile){
    EventHandler<ActionEvent> inputImageButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Select collection image");
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

            creationGUI.getNewCollection().setPicture(new SerialBlob(bytes));
            creationGUI.getCollectionImageView().setImage(new Image(creationGUI.getNewCollection().getPicture().getBinaryStream()));
            creationGUI.getCollectionImageView().setEffect(null);
          }catch(IOException | SQLException e){
            creationGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

            ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
            exceptionDialog.showAndWait();

            creationGUI.getGp().setEffect(null);
          }//end-try
        }//end-if
      }
    };
    EventHandler<ActionEvent> addAudioButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Add your audios");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 file", "*mp3"));
        List<File> fileList=fileChooser.showOpenMultipleDialog(stage);
        if(fileList!=null){
          for(File f: fileList){
            byte[] fileContent=new byte[(int)f.length()];
            FileInputStream fileInputStream=null;
            URL url=null;
            try{
              Media media=new Media(f.toURI().toURL().toString());
              fileInputStream=new FileInputStream(f);
              ContentHandler handler=new DefaultHandler();
              Metadata metadata=new Metadata();
              Parser parser=new Mp3Parser();
              ParseContext parseContext=new ParseContext();
              parser.parse(fileInputStream, handler, metadata, parseContext);
              fileInputStream.close();

              fileInputStream=new FileInputStream(f);
              fileInputStream.read(fileContent);
              fileInputStream.close();

              try{
                CollectionManagerFactory.getInstance().getCollectionManager().checkMetadata(metadata);

                JBAudio jbAudio=null;
                Blob fileAudio=new SerialBlob(fileContent);
                try{
                  Album a=(Album)creationGUI.getNewCollection();
                  jbAudio=new Song(0, metadata.get("dc:title")==null ? FilenameUtils.removeExtension(f.getName()) : metadata.get("dc:title"), (Artist)a.getCreator(), creationGUI.getNewCollection(), fileAudio, Double.parseDouble(metadata.get("xmpDM:duration"))*1000, new Date(System.currentTimeMillis()), new String[] {metadata.get("xmpDM:genre")}, false, 0);
                }catch(ClassCastException c){
                  Podcast p=(Podcast)creationGUI.getNewCollection();
                  jbAudio=new Episode(0, metadata.get("dc:title")==null ? FilenameUtils.removeExtension(f.getName()) : metadata.get("dc:title"), (Artist)p.getCreator(), creationGUI.getNewCollection(), fileAudio, Double.parseDouble(metadata.get("xmpDM:duration"))*1000, new Date(System.currentTimeMillis()), new String[] {metadata.get("xmpDM:genre")}, false, 0);
                }//end-try
                creationGUI.getNewCollection().getTrackList().add(jbAudio);
              }catch(InvalidAudioException i){
                creationGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

                ExceptionDialog exceptionDialog=new ExceptionDialog(stage, i);
                exceptionDialog.showAndWait();

                creationGUI.getGp().setEffect(null);
              }//end-try
            }catch(IOException | TikaException | SAXException | SQLException e){
              creationGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

              ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
              exceptionDialog.showAndWait();

              creationGUI.getGp().setEffect(null);
            }//end-try
          }//end-foreach
        }//end-if
      }
    };
    EventHandler<ActionEvent> createCollection=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        creationGUI.getNewCollection().setName(creationGUI.getNameTextField().getText());
        JBCollection collection=null;
        try{
          collection=CollectionManagerFactory.getInstance().getCollectionManager().createJBCollection(creationGUI.getNewCollection());
        }catch(AccountNotFoundException e){
          creationGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

          ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
          exceptionDialog.showAndWait();

          creationGUI.getGp().setEffect(null);
        }//end-try

        CollectionLibraryGUI collectionLibraryGUI=null;
        try{
          Album a=(Album)collection;

          //Retrieve albums
          ArrayList<JBCollection> albums=null;
          try{
            albums=CollectionManagerFactory.getInstance().getCollectionManager().getAlbums((Artist)activeProfile);

            collectionLibraryGUI=new CollectionLibraryGUI(activeProfile, albums, EJBENTITY.ALBUM);
            CollectionLibraryHandler collectionLibraryHandler=new CollectionLibraryHandler(activeProfile, collectionLibraryGUI);
            Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getAlbumButton());
            stage.setTitle("Albums");

            Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
            stage.setScene(collectionLibraryGUI.getScene());
            stage.setWidth(previousDimension.getWidth());
            stage.setHeight(previousDimension.getHeight());
          }catch(ClassCastException e){
            creationGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

            ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new InvalidJBEntityException());
            exceptionDialog.showAndWait();

            creationGUI.getGp().setEffect(null);
          }catch(AccountNotFoundException e){
            creationGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

            ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
            exceptionDialog.showAndWait();

            creationGUI.getGp().setEffect(null);
          }//end-try
        }catch(ClassCastException c){
          //Retrieve podcasts
          ArrayList<JBCollection> podcasts=null;
          try{
            podcasts=CollectionManagerFactory.getInstance().getCollectionManager().getPodcasts((Artist)activeProfile);

            collectionLibraryGUI=new CollectionLibraryGUI(activeProfile, podcasts, EJBENTITY.PODCAST);
            CollectionLibraryHandler collectionLibraryHandler=new CollectionLibraryHandler(activeProfile, collectionLibraryGUI);
            Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getPodcastButton());
            stage.setTitle("Podcasts");

            Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
            stage.setScene(collectionLibraryGUI.getScene());
            stage.setWidth(previousDimension.getWidth());
            stage.setHeight(previousDimension.getHeight());
          }catch(ClassCastException e){
            creationGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

            ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new InvalidJBEntityException());
            exceptionDialog.showAndWait();

            creationGUI.getGp().setEffect(null);
          }catch(AccountNotFoundException e){
            creationGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

            ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
            exceptionDialog.showAndWait();

            creationGUI.getGp().setEffect(null);
          }//end-try
        }//end-try
      }
    };
    creationGUI.getCollectionPictureButton().setOnAction(inputImageButtonHandler);
    creationGUI.getAddButton().setOnAction(addAudioButtonHandler);
    creationGUI.getCreate().setOnAction(createCollection);
  }
  /*---------------------------------------*/
}
