package it.unipv.ingsfw.JavaBeats.controller.handler.presets;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
public class SongbarHandler{

  private static SongbarHandler instance;
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
    Songbar.getInstance(activeProfile, currentAudio);

    initComponents(activeProfile, currentAudio);
  }

  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public static SongbarHandler getInstance(JBProfile activeProfile, JBAudio currentAudio){
    if(instance==null || SongbarHandler.activeProfile==null || SongbarHandler.currentAudio==null){
      instance=new SongbarHandler(activeProfile, currentAudio);
    }else if(!SongbarHandler.activeProfile.equals(activeProfile) || !SongbarHandler.currentAudio.equals(currentAudio)){
      instance=new SongbarHandler(activeProfile, currentAudio);
    }//end-if
    return instance;
  }
  /*---------------------------------------*/
  //Methods
  /*---------------------------------------*/
  private void initComponents(JBProfile activeProfile, JBAudio currentAudio){
    InvalidationListener mediaPlayerDurationListener=new InvalidationListener(){
      @Override
      public void invalidated(Observable observable){
        Songbar.getInstance(activeProfile, currentAudio).getPlaySlider().setValue(((ReadOnlyObjectProperty<Duration>)observable).getValue().toSeconds());
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
    try{
      currentAudio.getMediaPlayer().currentTimeProperty().addListener(mediaPlayerDurationListener);
      Songbar.getInstance(activeProfile, currentAudio).getPlaySlider().setOnDragDetected(sliderDragHandler);
      Songbar.getInstance(activeProfile, currentAudio).getPlaySlider().setOnMouseReleased(sliderDragHandler);
    }catch(NullPointerException n){

    }
  }
}
