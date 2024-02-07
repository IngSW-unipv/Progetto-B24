package it.unipv.ingsfw.JavaBeats.controller.handler.library;

import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.AudioTableHandler;
import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.exceptions.SystemErrorException;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.collection.Podcast;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionLibraryGUI;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionViewGUI;
import it.unipv.ingsfw.JavaBeats.view.library.CreationGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioCard;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioTable;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.ExceptionDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CollectionLibraryHandler{

  //Attributi
  private CollectionLibraryGUI collectionLibraryGUI;


  //Costruttore
  public CollectionLibraryHandler(JBProfile activeProfile, CollectionLibraryGUI collectionLibraryGUI){
    this.collectionLibraryGUI=collectionLibraryGUI;
    initComponents(activeProfile);
  }


  //Metodi
  private void initComponents(JBProfile activeProfile){
    EventHandler<MouseEvent> collectionClickHandler=new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent mouseEvent){
        Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();

        try{
          JBCollection jbCollection=((JBCollection)((AudioCard)mouseEvent.getSource()).getIjbResearchable());
          jbCollection.setTrackList(CollectionManagerFactory.getInstance().getCollectionManager().getCollectionAudios(jbCollection, activeProfile));
          CollectionViewGUI collectionViewGUI=new CollectionViewGUI(activeProfile, jbCollection);
          CollectionViewHandler collectionViewHandler=new CollectionViewHandler(collectionViewGUI, activeProfile);
          AudioTableHandler.getInstance((AudioTable)collectionViewGUI.getAudioTable());
          AudioTableHandler.setQueue(false);

          Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
          stage.setScene(collectionViewGUI.getScene());
          stage.setTitle("Collection");
          stage.setWidth(previousDimension.getWidth());
          stage.setHeight(previousDimension.getHeight());
        }catch(AccountNotFoundException e){
          collectionLibraryGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

          ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
          exceptionDialog.showAndWait();

          collectionLibraryGUI.getGp().setEffect(null);
        }//end-try
      }
    };
    EventHandler<ActionEvent> plusButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());

        JBCollection newCollection=null;
        /* Default collection image when inserting */
        BufferedImage bufferedImage=null;
        byte[] image=null;
        try{
          bufferedImage=ImageIO.read(new File("src/it/unipv/ingsfw/JavaBeats/view/resources/icons/RecordBig.png"));
          ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
          ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
          image=byteArrayOutputStream.toByteArray();
        }catch(IOException e){
          collectionLibraryGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

          ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
          exceptionDialog.showAndWait();

          collectionLibraryGUI.getGp().setEffect(null);
        }//end-try
        switch(collectionLibraryGUI.getEjbentity()){
          case PLAYLIST:
            try{
              newCollection=new Playlist(0, "New playlist", activeProfile);
              CollectionManagerFactory.getInstance().getCollectionManager().createJBCollection(newCollection);
              ArrayList<JBCollection> jbPlaylistsArraylist=null;
              jbPlaylistsArraylist=CollectionManagerFactory.getInstance().getCollectionManager().getPlaylists(activeProfile);
              CollectionLibraryGUI collectionLibraryGUI1=new CollectionLibraryGUI(activeProfile, jbPlaylistsArraylist, collectionLibraryGUI.getEjbentity());
              CollectionLibraryHandler collectionLibraryHandler=new CollectionLibraryHandler(activeProfile, collectionLibraryGUI1);
              stage.setScene(collectionLibraryGUI1.getScene());
            }catch(AccountNotFoundException e){
              collectionLibraryGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

              ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
              exceptionDialog.showAndWait();

              collectionLibraryGUI.getGp().setEffect(null);
            }//end-try
            break;
          case ALBUM:
            try{
              newCollection=new Album(0, "New album", activeProfile, new ArrayList<JBAudio>(), new SerialBlob(image));
              CreationGUI creationGUI=new CreationGUI(activeProfile, newCollection);
              CreationGUIHandler CreationGUIHandler=new CreationGUIHandler(creationGUI, activeProfile);
              stage.setScene(creationGUI.getScene());
              stage.setTitle("Create your album");
            }catch(SQLException e){
              collectionLibraryGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

              ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
              exceptionDialog.showAndWait();

              collectionLibraryGUI.getGp().setEffect(null);
            }//end-try
            break;
          case PODCAST:
            try{
              newCollection=new Podcast(0, "New podcast", activeProfile, new ArrayList<>(), new SerialBlob(image));
              CreationGUI creationGUI=new CreationGUI(activeProfile, newCollection);
              CreationGUIHandler creationGUIHandler=new CreationGUIHandler(creationGUI, activeProfile);
              stage.setScene(creationGUI.getScene());
              stage.setTitle("Create your podcast");
            }catch(SQLException e){
              collectionLibraryGUI.getGp().setEffect(new BoxBlur(10, 10, 10));

              ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
              exceptionDialog.showAndWait();

              collectionLibraryGUI.getGp().setEffect(null);
            }//end-try
            break;
        }//end-switch
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };
    collectionLibraryGUI.getCollectionFlowPane().getChildren().forEach(a -> a.setOnMouseClicked(collectionClickHandler));
    collectionLibraryGUI.getButtonPlus().setOnAction(plusButtonHandler);
  }

}
