package it.unipv.ingsfw.JavaBeats.controller.handler.library;

import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.PlayerManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.dialogs.EditPlaylistDialogHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.primary.home.HomePageHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.AudioTableHandler;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.collection.Podcast;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionViewGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioTable;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditPlaylistDialog;
import it.unipv.ingsfw.JavaBeats.view.primary.home.HomePageGUI;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class CollectionViewHandler{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private CollectionViewGUI gui;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public CollectionViewHandler(CollectionViewGUI gui, JBProfile activeProfile){
    this.gui=gui;
    initComponents(activeProfile);
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(JBProfile activeProfile){
    EventHandler<ActionEvent> editButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        gui.getGp().setEffect(new BoxBlur(10, 10, 10));

        Playlist p=(Playlist)gui.getJbCollection();
        EditPlaylistDialog dialog=new EditPlaylistDialog(stage, p, (Playlist)p.getCopy());
        EditPlaylistDialogHandler editPlaylistDialogHandler=new EditPlaylistDialogHandler(dialog);
        dialog.showAndWait();
        gui.getGp().setEffect(null);
      }
    };
    EventHandler<ActionEvent> playCollectionButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        PlayerManagerFactory.getInstance().getPlayerManager().play(gui.getJbCollection());
        PlayerManagerFactory.getInstance().getPlayerManager().setRandomized(false);
        gui.getCollectionHeader().getButtonRandom().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyRandom.png", true)));
        Songbar.getInstance().getButtonRandom().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyRandom.png", true)));
      }
    };
    EventHandler<ActionEvent> randomButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        /* Handling random playing the collection */
        if(gui.getJbCollection()!=null){
          PlayerManagerFactory.getInstance().getPlayerManager().randomize(gui.getJbCollection());
          gui.getCollectionHeader().getButtonRandom().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/FullRandom.png", true)));
          Songbar.getInstance().getButtonRandom().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/FullRandom.png", true)));
        }//end-if
      }
    };
    EventHandler<ActionEvent> binButtonHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        if(gui.getJbCollection()==null){
          PlayerManagerFactory.getInstance().getPlayerManager().deleteQueue();

          AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING.setItems(FXCollections.observableArrayList(PlayerManagerFactory.getInstance().getPlayerManager().getQueue()));
          AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING.refresh();
        }else{
          CollectionManagerFactory.getInstance().getCollectionManager().removeCollection(gui.getJbCollection());
          HomePageGUI homePageGUI=new HomePageGUI(activeProfile);
          HomePageHandler homePageHandler=new HomePageHandler(homePageGUI, activeProfile);
          Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getHomeButton());

          Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
          stage.setScene(homePageGUI.getScene());
          stage.setTitle("HomePage");
          stage.setWidth(previousDimension.getWidth());
          stage.setHeight(previousDimension.getHeight());
        }//end-if
      }
    };
    EventHandler<ActionEvent> addEpisodeHandler=new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Add your episodes");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 file", "*mp3"));
        List<File> fileList=fileChooser.showOpenMultipleDialog(stage);
        for(File f: fileList){
          byte[] fileContent=new byte[(int)f.length()];
          FileInputStream fileInputStream=null;
          URL url=null;
          try{
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

            Blob fileAudio=new SerialBlob(fileContent);

            Podcast p=(Podcast)gui.getJbCollection();
            JBAudio jbAudio=new Episode(0, metadata.get("dc:title")==null ? FilenameUtils.removeExtension(f.getName()) : metadata.get("dc:title"), (Artist)p.getCreator(), gui.getJbCollection(), fileAudio, Double.parseDouble(metadata.get("xmpDM:duration"))*1000, new Date(System.currentTimeMillis()), new String[]{metadata.get("xmpDM:genre")}, false, 0);

            System.out.println("Prima dell'aggiunta: "+CollectionManagerFactory.getInstance().getCollectionManager().getCollectionAudios(gui.getJbCollection(), activeProfile));
            CollectionManagerFactory.getInstance().getCollectionManager().addToCollection(gui.getJbCollection(), jbAudio);
            System.out.println("Dopo l'aggiunta: "+CollectionManagerFactory.getInstance().getCollectionManager().getCollectionAudios(gui.getJbCollection(), activeProfile));


          }catch(IOException | TikaException | SAXException | SQLException e){
            throw new RuntimeException(e);
          }
        }//end-foreach

        CollectionViewGUI collectionViewGUI=new CollectionViewGUI(activeProfile, gui.getJbCollection());
        CollectionViewHandler collectionViewHandler=new CollectionViewHandler(collectionViewGUI, activeProfile);
        AudioTableHandler.getInstance((AudioTable)collectionViewGUI.getAudioTable());

        Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
        stage.setScene(collectionViewGUI.getScene());
        stage.setTitle("Collection");
        stage.setWidth(previousDimension.getWidth());
        stage.setHeight(previousDimension.getHeight());
      }
    };

    EventHandler<MouseEvent> tableViewClickHandler=new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent mouseEvent){
        if(mouseEvent.getButton()==MouseButton.PRIMARY){
          Node node=mouseEvent.getPickResult().getIntersectedNode();

          /* going up in node hierarchy until a cell is found, or we can be sure no cell was clicked */
          boolean foundPlayButton=false;
          boolean foundIsFavoriteButton=false;
          while(node!=gui.getAudioTable() && !foundPlayButton && !foundIsFavoriteButton){
            String id=node.getId();
            if(id!=null && id.equals("playButton")){
              foundPlayButton=true;
            }else if(id!=null && id.equals("favoriteButton")){
              foundIsFavoriteButton=true;
            }//end-if

            node=node.getParent();
          }//end-while

          /* If I clicked the play button in the AudioTable then we call the Player manager to play the corresponding song. Otherwise, If we clicked the HeartButton then we add/remove It from the profile's favorites */
          if(foundPlayButton){
            JBAudio audioClicked=gui.getAudioTable().getItems().get(gui.getAudioTable().getSelectionModel().getSelectedIndex());

            /* If I play from the queue then the queue starts from the song I clicked to the last song. Otherwise, I just play normally */
            if(AudioTableHandler.isQueue()){
              PlayerManagerFactory.getInstance().getPlayerManager().playFromQueue(audioClicked);

              CollectionViewGUI collectionViewGUI=new CollectionViewGUI(activeProfile, PlayerManagerFactory.getInstance().getPlayerManager().getQueue());
              CollectionViewHandler collectionViewHandler=new CollectionViewHandler(collectionViewGUI, activeProfile);
              ((Stage)AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING.getScene().getWindow()).setScene(collectionViewGUI.getScene());
              AudioTableHandler.getInstance((AudioTable)collectionViewGUI.getAudioTable());
            }else{
              PlayerManagerFactory.getInstance().getPlayerManager().play(audioClicked);
            }//end-if
          }else if(foundIsFavoriteButton){
            JBAudio audioClicked=gui.getAudioTable().getItems().get(gui.getAudioTable().getSelectionModel().getSelectedIndex());

            if(activeProfile.getFavorites().getTrackList().contains(audioClicked)){
              activeProfile.getFavorites().getTrackList().remove(audioClicked);
            }else{
              activeProfile.getFavorites().getTrackList().add(audioClicked);
            }//end-if
            CollectionManagerFactory.getInstance().getCollectionManager().setFavorites(activeProfile);
            if(AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING!=null){
              AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING.refresh();
            }//end-if
          }//end-if
        }//end-if
      }
    };
    /* If I'm playing this collection randomly, and I'm displaying this very collection, then the random button is activated  */
    if(PlayerManagerFactory.getInstance().getPlayerManager().getCurrentCollectionPlaying()!=null && gui.getJbCollection()!=null && gui.getJbCollection().equals(PlayerManagerFactory.getInstance().getPlayerManager().getCurrentCollectionPlaying())){
      gui.getCollectionHeader().getButtonRandom().setGraphic(PlayerManagerFactory.getInstance().getPlayerManager().isRandomized() ? new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/FullRandom.png", true)) : new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyRandom.png", true)));
    }//end-if

    /* Adding all the eventHandlers */
    gui.getCollectionHeader().getButtonRandom().setOnAction(randomButtonHandler);
    gui.getCollectionHeader().getEditButton().setOnAction(editButtonHandler);
    gui.getCollectionHeader().getButtonPlayPause().setOnAction(playCollectionButtonHandler);
    gui.getAudioTable().setOnMouseClicked(tableViewClickHandler);
    gui.getCollectionHeader().getButtonBin().setOnAction(binButtonHandler);
    gui.getCollectionHeader().getAddEpisodeButton().setOnAction(addEpisodeHandler);
  }
  /*---------------------------------------*/
}
