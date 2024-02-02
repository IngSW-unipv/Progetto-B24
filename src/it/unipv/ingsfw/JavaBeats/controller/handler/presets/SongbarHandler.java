package it.unipv.ingsfw.JavaBeats.controller.handler.presets;
import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.PlayerManagerFactory;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.Arrays;

public class SongbarHandler{

  private static SongbarHandler instance=null;
  private static JBProfile activeProfile=null;
  private static JBAudio currentAudio=null;
  private Boolean isDragged=false;

  /*---------------------------------------*/
  //Costruttore
  /*---------------------------------------*/
  private SongbarHandler(JBProfile activeProfile, JBAudio currentAudio){
    super();
    SongbarHandler.activeProfile=activeProfile;
    SongbarHandler.currentAudio=currentAudio;

    initComponents();
    initHandlers();
  }

  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public static SongbarHandler getInstance(JBProfile activeProfile, JBAudio currentAudio){
    if(instance==null){
      instance=new SongbarHandler(activeProfile, currentAudio);
    }else if(!SongbarHandler.activeProfile.equals(activeProfile) && !SongbarHandler.currentAudio.equals(currentAudio)){
      instance=new SongbarHandler(activeProfile, currentAudio);
    }else{
      instance=new SongbarHandler(activeProfile, currentAudio);
    }//end-if
    return instance;
  }

  /*---------------------------------------*/
  //Methods
  /*---------------------------------------*/
  private void initComponents(){
    Songbar.getInstance().getRecordImageView().setImage(currentAudio==null ? new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Record.png", true) : currentAudio.getMetadata().getCollection().scalePicture(80));
    Songbar.getInstance().getSongTitle().setText(currentAudio==null ? "Unknown title" : currentAudio.getMetadata().getTitle());
    Songbar.getInstance().getSongArtist().setText(currentAudio==null ? "Unknown artist" : currentAudio.getMetadata().getArtist().getUsername());
    Songbar.getInstance().getSongGenre().setText(currentAudio==null ? "Unknown genre" : Arrays.toString(currentAudio.getMetadata().getGenres()));
    Songbar.getInstance().getButtonHeart().setGraphic(new ImageView(activeProfile.getFavorites().getTrackList().contains(currentAudio) ? new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/FullHeart.png", true) : new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyHeart.png", true)));
    Songbar.getInstance().getButtonRandom().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyRandom.png", true)));
    Songbar.getInstance().getButtonSkipBack().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/SkipBack.png", true)));

    Songbar.getInstance().getButtonPlayPause().setGraphic(new ImageView(currentAudio==null ? new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Play.png", true) : new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Pause.png", true)));

    Songbar.getInstance().getButtonSkipForward().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/SkipForward.png", true)));
    Songbar.getInstance().getButtonLoop().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyLoop.png", true)));

    Songbar.getInstance().getPlaySlider().setMin(00.00);
    Songbar.getInstance().getPlaySlider().setMax(currentAudio==null ? 0 : Duration.millis(currentAudio.getMetadata().getDuration()).toSeconds());
    Songbar.getInstance().getPlaySlider().setValue(00.00);

    Songbar.getInstance().getMinutePassedLabel().setText("00:00");
    Songbar.getInstance().getSongLengthLabel().setText(currentAudio==null ? "00:00" : JBAudio.convertToMinutesAndSeconds(currentAudio.getMetadata().getDuration()));

    Songbar.getInstance().getVolumeSlider().setMin(00.00);
    Songbar.getInstance().getVolumeSlider().setMax(100.00);
    Songbar.getInstance().getVolumeSlider().setValue(50.00);
  }

  private void initHandlers(){
    Runnable mediaPlayingRunnable=new Runnable(){
      @Override
      public void run(){
        Songbar.getInstance().getButtonPlayPause().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Pause.png", true)));
      }
    };
    Runnable mediaPausedRunnable=new Runnable(){
      @Override
      public void run(){
        Songbar.getInstance().getButtonPlayPause().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Play.png", true)));
      }
    };
    Runnable mediaEndOfPlayingRunnable=new Runnable(){
      @Override
      public void run(){
        /* I let the playerManager figure what to do next */
        PlayerManagerFactory.getInstance().getPlayerManager().play();
      }
    };
    InvalidationListener mediaPlayerDurationListener=new InvalidationListener(){
      @Override
      public void invalidated(Observable observable){
        Songbar.getInstance().getPlaySlider().setValue(((ReadOnlyObjectProperty<Duration>)observable).getValue().toSeconds());
        Songbar.getInstance().getMinutePassedLabel().setText(JBAudio.convertToMinutesAndSeconds(((ReadOnlyObjectProperty<Duration>)observable).getValue().toMillis()));
      }
    };
    EventHandler<MouseEvent> sliderDragHandler=new EventHandler<>(){
      @Override
      public void handle(MouseEvent mouseEvent){
        if(mouseEvent.getButton()==MouseButton.PRIMARY){
          if(mouseEvent.getEventType().getName().equals("DRAG_DETECTED")){
            isDragged=true;
            currentAudio.getMediaPlayer().pause();
          }else if(isDragged){
            currentAudio.getMediaPlayer().seek(Duration.seconds(((Slider)mouseEvent.getSource()).getValue()));
            currentAudio.getMediaPlayer().play();
            isDragged=false;
          }//end-if
        }//end-if
      }
    };
    EventHandler<ActionEvent> buttonPlayPauseHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        PlayerManagerFactory.getInstance().getPlayerManager().playPause();
      }
    };
    EventHandler<MouseEvent> volumeSliderChangeHandler=new EventHandler<MouseEvent>(){
      @Override
      public void handle(MouseEvent mouseEvent){
        if(mouseEvent.getButton()==MouseButton.PRIMARY){
          currentAudio.getMediaPlayer().setVolume(((Slider)mouseEvent.getSource()).getValue()/100);
        }//end-if
      }
    };
    EventHandler<ActionEvent> addToFavoriteButtonHandler=new EventHandler<>(){
      @Override
      public void handle(ActionEvent actionEvent){
        /* If is already a favorite I have to remove it, otherwise add it */
        if(currentAudio!=null){
          if(activeProfile.getFavorites().getTrackList().contains(currentAudio)){
            activeProfile.getFavorites().getTrackList().remove(currentAudio);

            Songbar.getInstance().getButtonHeart().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyHeart.png", true)));
          }else{
            activeProfile.getFavorites().getTrackList().add(currentAudio);

            Songbar.getInstance().getButtonHeart().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/FullHeart.png", true)));
          }//end-if
          CollectionManagerFactory.getInstance().getCollectionManager().setFavorites(activeProfile);
          if(AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING!=null){
            AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING.refresh();
          }//end-if
        }//end-if
      }
    };
    try{
      currentAudio.getMediaPlayer().setOnPlaying(mediaPlayingRunnable);
      currentAudio.getMediaPlayer().setOnPaused(mediaPausedRunnable);
      currentAudio.getMediaPlayer().setOnEndOfMedia(mediaEndOfPlayingRunnable);
      currentAudio.getMediaPlayer().currentTimeProperty().addListener(mediaPlayerDurationListener);
      Songbar.getInstance().getPlaySlider().setOnDragDetected(sliderDragHandler);
      Songbar.getInstance().getPlaySlider().setOnMouseReleased(sliderDragHandler);
      Songbar.getInstance().getButtonPlayPause().setOnAction(buttonPlayPauseHandler);
      Songbar.getInstance().getVolumeSlider().setOnMouseReleased(volumeSliderChangeHandler);
      Songbar.getInstance().getButtonHeart().setOnAction(addToFavoriteButtonHandler);
    }catch(NullPointerException n){

    }
  }
}
