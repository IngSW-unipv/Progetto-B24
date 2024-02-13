package it.unipv.ingsfw.JavaBeats.controller.handler.library;

import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.PlayerManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.dialogs.EditPlaylistDialogHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.primary.home.HomePageHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.AudioTableHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.primary.profile.ProfileViewHandler;
import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.exceptions.InvalidAudioException;
import it.unipv.ingsfw.JavaBeats.exceptions.SystemErrorException;
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
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.ExceptionDialog;
import it.unipv.ingsfw.JavaBeats.view.primary.home.HomePageGUI;
import it.unipv.ingsfw.JavaBeats.view.primary.profile.ProfileViewGUI;
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

        try{
          gui.getGp().setEffect(new BoxBlur(10, 10, 10));

          Playlist p=(Playlist)gui.getJbCollection();

          EditPlaylistDialog dialog=new EditPlaylistDialog(stage, p, (Playlist)p.getCopy());
          EditPlaylistDialogHandler editPlaylistDialogHandler=new EditPlaylistDialogHandler(dialog);

          dialog.showAndWait();

          gui.getGp().setEffect(null);

          /* Checking if an edit has been made */
          if(!dialog.getOriginalPlaylist().equals(dialog.getNewPlaylist())){

            //Collection manager updates DB
            CollectionManagerFactory.getInstance().getCollectionManager().edit(dialog.getNewPlaylist());

            gui.getCollectionHeader().getCollectionImageView().setImage(new Image(dialog.getNewPlaylist().getPicture().getBinaryStream()));
            gui.getCollectionHeader().getCollectionTitle().setText(dialog.getNewPlaylist().getName());

            CollectionViewGUI collectionViewGUI=new CollectionViewGUI(activeProfile, dialog.getNewPlaylist());
            CollectionViewHandler collectionViewHandler=new CollectionViewHandler(collectionViewGUI, activeProfile);
            Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getPlaylistsButton());

            Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
            stage.setScene(collectionViewGUI.getScene());
            stage.setTitle("Playlist");
            stage.setWidth(previousDimension.getWidth());
            stage.setHeight(previousDimension.getHeight());
          }//end-if
        }catch(SystemErrorException | SQLException | AccountNotFoundException e){
          gui.getGp().setEffect(new BoxBlur(10, 10, 10));

          ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
          exceptionDialog.showAndWait();

          gui.getGp().setEffect(null);
        }//end-try
      }
    };
    EventHandler<ActionEvent> playCollectionButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        try{
          PlayerManagerFactory.getInstance().getPlayerManager().play(gui.getJbCollection());

          PlayerManagerFactory.getInstance().getPlayerManager().setRandomized(false);
          PlayerManagerFactory.getInstance().getPlayerManager().setAudioLooping(false);
          PlayerManagerFactory.getInstance().getPlayerManager().setCollectionLooping(false);


          gui.getCollectionHeader().getButtonRandom().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyRandom.png", true)));
          Songbar.getInstance().getButtonRandom().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyRandom.png", true)));
          gui.getCollectionHeader().getButtonLoop().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyLoop.png", true)));
          Songbar.getInstance().getButtonLoop().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyLoop.png", true)));
        }catch(SystemErrorException e){
          gui.getGp().setEffect(new BoxBlur(10, 10, 10));

          ExceptionDialog exceptionDialog=new ExceptionDialog(stage, e);
          exceptionDialog.showAndWait();

          gui.getGp().setEffect(null);
        }//end-try
      }
    };
    EventHandler<ActionEvent> randomButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        /* Handling random playing the collection */
        if(gui.getJbCollection()!=null){
          try{
            PlayerManagerFactory.getInstance().getPlayerManager().randomize(gui.getJbCollection());


            gui.getCollectionHeader().getButtonRandom().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/FullRandom.png", true)));
            Songbar.getInstance().getButtonRandom().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/FullRandom.png", true)));
            gui.getCollectionHeader().getButtonLoop().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyLoop.png", true)));
            Songbar.getInstance().getButtonLoop().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyLoop.png", true)));
          }catch(SystemErrorException e){
            gui.getGp().setEffect(new BoxBlur(10, 10, 10));

            ExceptionDialog exceptionDialog=new ExceptionDialog(stage, e);
            exceptionDialog.showAndWait();

            gui.getGp().setEffect(null);
          }//end-try

        }//end-if
      }
    };
    EventHandler<ActionEvent> loopButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        /* Handling looping the collection */
        if(gui.getJbCollection()!=null){
          try{
            PlayerManagerFactory.getInstance().getPlayerManager().loop(gui.getJbCollection());

            AudioTableHandler.getInstance().getCurrentAudioTableShowing().setItems(FXCollections.observableArrayList(gui.getJbCollection().getTrackList()));
            AudioTableHandler.getInstance().getCurrentAudioTableShowing().refresh();


            gui.getCollectionHeader().getButtonRandom().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyRandom.png", true)));
            Songbar.getInstance().getButtonRandom().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyRandom.png", true)));
            gui.getCollectionHeader().getButtonLoop().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/FullLoop.png", true)));
          }catch(SystemErrorException e){
            gui.getGp().setEffect(new BoxBlur(10, 10, 10));

            ExceptionDialog exceptionDialog=new ExceptionDialog(stage, e);
            exceptionDialog.showAndWait();

            gui.getGp().setEffect(null);
          }//end-try

        }//end-if
      }
    };
    EventHandler<ActionEvent> binButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        /* Handling pressing the delete button
         *  If we clicked the bin in the queue we simply clear it
         *  If we clicked the bin in a collection then we delete it and its audios from the DB, and we head back to the home
         *  */
        if(gui.getJbCollection()==null){
          PlayerManagerFactory.getInstance().getPlayerManager().deleteQueue();
          gui.getCollectionHeader().getButtonLoop().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyLoop.png", true)));

          AudioTableHandler.getInstance().getCurrentAudioTableShowing().setItems(FXCollections.observableArrayList(PlayerManagerFactory.getInstance().getPlayerManager().getQueue()));
          AudioTableHandler.getInstance().getCurrentAudioTableShowing().refresh();
        }else if(gui.getJbCollection().getCreator().equals(activeProfile)){
          try{
            CollectionManagerFactory.getInstance().getCollectionManager().removeCollection(gui.getJbCollection());

            if(PlayerManagerFactory.getInstance().getPlayerManager().getCurrentCollectionPlaying()!=null && PlayerManagerFactory.getInstance().getPlayerManager().getCurrentCollectionPlaying().equals(gui.getJbCollection())){
              PlayerManagerFactory.getInstance().getPlayerManager().setCollectionLooping(false);
            }//end-if

            HomePageGUI homePageGUI=new HomePageGUI(activeProfile);
            HomePageHandler homePageHandler=new HomePageHandler(homePageGUI, activeProfile);
            Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getHomeButton());

            Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
            stage.setScene(homePageGUI.getScene());
            stage.setTitle("HomePage");
            stage.setWidth(previousDimension.getWidth());
            stage.setHeight(previousDimension.getHeight());
          }catch(AccountNotFoundException e){
            gui.getGp().setEffect(new BoxBlur(10, 10, 10));

            ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
            exceptionDialog.showAndWait();

            gui.getGp().setEffect(null);
          }//end-try
        }//end-if
      }
    };
    EventHandler<ActionEvent> addEpisodeHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Add your episodes");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 file", "*mp3"));
        List<File> fileList=fileChooser.showOpenMultipleDialog(stage);
        if(fileList!=null){
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

              try{
                CollectionManagerFactory.getInstance().getCollectionManager().checkMetadata(metadata);

                Blob fileAudio=new SerialBlob(fileContent);

                Podcast p=(Podcast)gui.getJbCollection();
                JBAudio jbAudio=new Episode(0, metadata.get("dc:title")==null ? FilenameUtils.removeExtension(f.getName()) : metadata.get("dc:title"), (Artist)p.getCreator(), gui.getJbCollection(), fileAudio, Double.parseDouble(metadata.get("xmpDM:duration"))*1000, new Date(System.currentTimeMillis()), new String[]{metadata.get("xmpDM:genre")}, false, 0);

                CollectionManagerFactory.getInstance().getCollectionManager().addToCollection(gui.getJbCollection(), jbAudio);

                CollectionViewGUI collectionViewGUI=new CollectionViewGUI(activeProfile, gui.getJbCollection());
                CollectionViewHandler collectionViewHandler=new CollectionViewHandler(collectionViewGUI, activeProfile);
                AudioTableHandler.getInstance().setCurrentAudioTableShowing((AudioTable)collectionViewGUI.getAudioTable());
                AudioTableHandler.getInstance().setQueue(false);

                Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
                stage.setScene(collectionViewGUI.getScene());
                stage.setTitle("Collection");
                stage.setWidth(previousDimension.getWidth());
                stage.setHeight(previousDimension.getHeight());
              }catch(InvalidAudioException i){
                gui.getGp().setEffect(new BoxBlur(10, 10, 10));

                ExceptionDialog exceptionDialog=new ExceptionDialog(stage, i);
                exceptionDialog.showAndWait();

                gui.getGp().setEffect(null);
              }//end-try
            }catch(IOException | TikaException | SAXException | SQLException | AccountNotFoundException e){
              gui.getGp().setEffect(new BoxBlur(10, 10, 10));

              ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
              exceptionDialog.showAndWait();

              gui.getGp().setEffect(null);
            }//end-try
          }//end-foreach
        }//end-if
      }
    };

    EventHandler<MouseEvent> tableViewClickHandler=new EventHandler<>(){
      @Override
      public void handle(MouseEvent mouseEvent){
        Stage stage=(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();

        if(mouseEvent.getButton()==MouseButton.PRIMARY){
          Node node=mouseEvent.getPickResult().getIntersectedNode();

          /* going up in node hierarchy until a cell is found, or we can be sure no cell was clicked */
          boolean foundPlayButton=false;
          boolean foundIsFavoriteButton=false;
          boolean foundBinButton=false;
          while(node!=gui.getAudioTable() && !foundPlayButton && !foundIsFavoriteButton && !foundBinButton){
            String id=node.getId();
            if(id!=null && id.equals("playButton")){
              foundPlayButton=true;
            }else if(id!=null && id.equals("favoriteButton")){
              foundIsFavoriteButton=true;
            }else if(id!=null && id.equals("binButton")){
              foundBinButton=true;
            }//end-if

            node=node.getParent();
          }//end-while

          /* If I clicked the play button in the AudioTable then we call the Player manager to play the corresponding song.
             If we clicked the HeartButton then we add/remove It from the profile's favorites
             If we clicked the BinButton then we either remove the audio from the queue or from the Playlist
          */
          if(foundPlayButton){
            JBAudio audioClicked=gui.getAudioTable().getItems().get(gui.getAudioTable().getSelectionModel().getSelectedIndex());

            /* If I play from the queue then the queue starts from the song I clicked to the last song. Otherwise, I just play normally */
            if(AudioTableHandler.getInstance().isQueue()){
              try{
                PlayerManagerFactory.getInstance().getPlayerManager().playFromQueue(audioClicked);

                reloadCollectionViewGUI(activeProfile);
              }catch(SystemErrorException e){
                gui.getGp().setEffect(new BoxBlur(10, 10, 10));

                ExceptionDialog exceptionDialog=new ExceptionDialog(stage, e);
                exceptionDialog.showAndWait();

                gui.getGp().setEffect(null);
              }catch(AccountNotFoundException e){
                gui.getGp().setEffect(new BoxBlur(10, 10, 10));

                ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
                exceptionDialog.showAndWait();

                gui.getGp().setEffect(null); /* Removing blur effect */
              }//end-try
            }else{
              PlayerManagerFactory.getInstance().getPlayerManager().play(audioClicked);
            }//end-if
          }else if(foundIsFavoriteButton){
            try{
              JBAudio audioClicked=gui.getAudioTable().getItems().get(gui.getAudioTable().getSelectionModel().getSelectedIndex());

              if(activeProfile.getFavorites().getTrackList().contains(audioClicked)){
                activeProfile.getFavorites().getTrackList().remove(audioClicked);
              }else{
                activeProfile.getFavorites().getTrackList().add(audioClicked);
              }//end-if

              CollectionManagerFactory.getInstance().getCollectionManager().setFavorites(activeProfile);

              if(gui.getJbCollection()!=null && gui.getJbCollection().getName().equals("Favorites")){
                CollectionViewGUI collectionViewGUI=new CollectionViewGUI(activeProfile, activeProfile.getFavorites());
                CollectionViewHandler collectionViewHandler=new CollectionViewHandler(collectionViewGUI, activeProfile);
                ((Stage)AudioTableHandler.getInstance().getCurrentAudioTableShowing().getScene().getWindow()).setScene(collectionViewGUI.getScene());
                AudioTableHandler.getInstance();
                AudioTableHandler.getInstance().setCurrentAudioTableShowing((AudioTable)collectionViewGUI.getAudioTable());
                AudioTableHandler.getInstance().setQueue(false);
              }else{
                if(AudioTableHandler.getInstance().getCurrentAudioTableShowing()!=null){
                  AudioTableHandler.getInstance().getCurrentAudioTableShowing().refresh();
                }//end-if
              }//end-if
            }catch(AccountNotFoundException e){
              gui.getGp().setEffect(new BoxBlur(10, 10, 10));

              ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
              exceptionDialog.showAndWait();

              gui.getGp().setEffect(null);
            }//end-try
          }else if(foundBinButton){
            JBAudio audioClicked=gui.getAudioTable().getItems().get(gui.getAudioTable().getSelectionModel().getSelectedIndex());

            if(AudioTableHandler.getInstance().isQueue()){
              PlayerManagerFactory.getInstance().getPlayerManager().removeFromQueue(audioClicked);

              reloadCollectionViewGUI(activeProfile);
            }else if(gui.getJbCollection().getCreator().equals(activeProfile)){
              try{
                CollectionManagerFactory.getInstance().getCollectionManager().removeFromPlaylist(gui.getJbCollection(), audioClicked);

                CollectionViewGUI collectionViewGUI=new CollectionViewGUI(activeProfile, gui.getJbCollection());
                CollectionViewHandler collectionViewHandler=new CollectionViewHandler(collectionViewGUI, activeProfile);
                ((Stage)AudioTableHandler.getInstance().getCurrentAudioTableShowing().getScene().getWindow()).setScene(collectionViewGUI.getScene());
                AudioTableHandler.getInstance().setCurrentAudioTableShowing((AudioTable)collectionViewGUI.getAudioTable());
                AudioTableHandler.getInstance().setQueue(false);
              }catch(AccountNotFoundException e){
                gui.getGp().setEffect(new BoxBlur(10, 10, 10));

                ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
                exceptionDialog.showAndWait();

                gui.getGp().setEffect(null);
              }//end-try
            }//end-if
          }//end-if
        }//end-if
      }
    };
    EventHandler<ActionEvent> userProfileButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        Stage stage=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        try{

          ProfileViewGUI profileViewGUI=null;
          if(gui.getJbCollection()!=null){
            CollectionManagerFactory.getInstance().getCollectionManager().getCollectionCreator(gui.getJbCollection());
            profileViewGUI=new ProfileViewGUI(activeProfile, gui.getJbCollection().getCreator());
          }else{
            ProfileManagerFactory.getInstance().getProfileManager().refreshProfile(activeProfile);
            profileViewGUI=new ProfileViewGUI(activeProfile, activeProfile);
          }//end-if

          ProfileViewHandler profileViewHandler=new ProfileViewHandler(profileViewGUI, activeProfile);
          Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getProfileButton());

          Dimension2D previousDimension=new Dimension2D(stage.getWidth(), stage.getHeight());
          stage.setScene(profileViewGUI.getScene());
          stage.setTitle("Profile");
          stage.setWidth(previousDimension.getWidth());
          stage.setHeight(previousDimension.getHeight());
        }catch(AccountNotFoundException e){
          gui.getGp().setEffect(new BoxBlur(10, 10, 10));

          ExceptionDialog exceptionDialog=new ExceptionDialog(stage, new SystemErrorException());
          exceptionDialog.showAndWait();

          gui.getGp().setEffect(null);
        }//end-try
      }
    };
    /* If I'm playing this collection randomly, and I'm displaying this very collection, then the random button is activated
     *  Same for looping
     *  */
    if(PlayerManagerFactory.getInstance().getPlayerManager().getCurrentCollectionPlaying()!=null && gui.getJbCollection()!=null && gui.getJbCollection().equals(PlayerManagerFactory.getInstance().getPlayerManager().getCurrentCollectionPlaying())){
      gui.getCollectionHeader().getButtonRandom().setGraphic(PlayerManagerFactory.getInstance().getPlayerManager().isRandomized() ? new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/FullRandom.png", true)) : new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyRandom.png", true)));
      gui.getCollectionHeader().getButtonLoop().setGraphic(PlayerManagerFactory.getInstance().getPlayerManager().isCollectionLooping() ? new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/FullLoop.png", true)) : new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyLoop.png", true)));
    }//end-if

    /* Adding all the eventHandlers */
    gui.getCollectionHeader().getButtonRandom().setOnAction(randomButtonHandler);
    gui.getCollectionHeader().getButtonLoop().setOnAction(loopButtonHandler);
    gui.getCollectionHeader().getEditButton().setOnAction(editButtonHandler);
    gui.getCollectionHeader().getButtonPlayPause().setOnAction(playCollectionButtonHandler);
    gui.getAudioTable().setOnMouseClicked(tableViewClickHandler);
    gui.getCollectionHeader().getButtonBin().setOnAction(binButtonHandler);
    gui.getCollectionHeader().getAddEpisodeButton().setOnAction(addEpisodeHandler);
    gui.getCollectionHeader().getUserProfileButton().setOnAction(userProfileButtonHandler);
  }

  private static void reloadCollectionViewGUI(JBProfile activeProfile){
    CollectionViewGUI collectionViewGUI=new CollectionViewGUI(activeProfile, PlayerManagerFactory.getInstance().getPlayerManager().getQueue());
    CollectionViewHandler collectionViewHandler=new CollectionViewHandler(collectionViewGUI, activeProfile);
    ((Stage)AudioTableHandler.getInstance().getCurrentAudioTableShowing().getScene().getWindow()).setScene(collectionViewGUI.getScene());
    AudioTableHandler.getInstance().setCurrentAudioTableShowing((AudioTable)collectionViewGUI.getAudioTable());
    AudioTableHandler.getInstance().setQueue(true);
  }
  /*---------------------------------------*/
}
