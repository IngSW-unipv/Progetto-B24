package it.unipv.ingsfw.JavaBeats.controller.handler.library;
import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.model.playable.EJBPLAYABLE;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionLibraryGUI;
import it.unipv.ingsfw.JavaBeats.view.library.CreationGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.checkerframework.checker.units.qual.A;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
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

            System.out.println("----------------------------------------------");
            System.out.println("Title: "+metadata.get("dc:title")+"  "+FilenameUtils.removeExtension(f.getName()));
            System.out.println("Genre : "+metadata.get("xmpDM:genre"));
            System.out.println("Duration: "+metadata.get("xmpDM:duration"));
            System.out.println("----------------------------------------------");

            JBAudio jbAudio=null;
            try{
              Album a=(Album)creationGUI.getNewCollection();
              jbAudio=new Song(1, metadata.get("dc:title")==null ? FilenameUtils.removeExtension(f.getName()) : metadata.get("dc:title"), (Artist)a.getCreator(), creationGUI.getNewCollection(), new SerialBlob(fileContent), new Time((long)media.getDuration().toMillis()), new Date(System.currentTimeMillis()), new String[] {metadata.get("xmpDM:genre")}, false, 0);
            }catch(ClassCastException | SQLException c){

            }//end-try
            creationGUI.getNewCollection().getTrackList().add(jbAudio);
          }catch(IOException | TikaException | SAXException e){
            throw new RuntimeException(e);
          }//end-try


        }//end-foreach
      }
    };
    EventHandler<ActionEvent> createCollection=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        JBCollection collection=CollectionManagerFactory.getInstance().getCollectionManager().createJBCollection(creationGUI.getNewCollection());

        CollectionLibraryGUI collectionLibraryGUI=null;
        try{
          Album a=(Album)collection;

          //Retrieve albums
          ArrayList<JBCollection> albums=null;
          try{
            albums=CollectionManagerFactory.getInstance().getCollectionManager().getAlbums((Artist)activeProfile);
          }catch(ClassCastException e){
            //popup
          }

          collectionLibraryGUI=new CollectionLibraryGUI(activeProfile, albums, EJBPLAYABLE.ALBUM);
          CollectionLibraryHandler collectionLibraryHandler=new CollectionLibraryHandler(activeProfile, collectionLibraryGUI);
          Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getAlbumButton());
          stage.setTitle("Albums");
        }catch(ClassCastException c){
          //Retrieve podcasts
          ArrayList<JBCollection> podcasts=null;
          try{
            podcasts=CollectionManagerFactory.getInstance().getCollectionManager().getPodcasts((Artist)activeProfile);
          }catch(ClassCastException e){
            //popup
          }

          collectionLibraryGUI=new CollectionLibraryGUI(activeProfile, podcasts, EJBPLAYABLE.PODCAST);
          CollectionLibraryHandler collectionLibraryHandler=new CollectionLibraryHandler(activeProfile, collectionLibraryGUI);
          Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getPodcastButton());
          stage.setTitle("Podcasts");
        }//end-try

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(collectionLibraryGUI.getScene());
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());

      }
    };
    creationGUI.getCollectionPictureButton().setOnAction(inputImageButtonHandler);
    creationGUI.getAddButton().setOnAction(addAudioButtonHandler);
    creationGUI.getCreate().setOnAction(createCollection);
  }
  /*---------------------------------------*/
}
