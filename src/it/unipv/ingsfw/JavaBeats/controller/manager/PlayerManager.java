package it.unipv.ingsfw.JavaBeats.controller.manager;
import it.unipv.ingsfw.JavaBeats.controller.adapter.FXAdapter;
import it.unipv.ingsfw.JavaBeats.controller.factory.FXAdapterFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.ProfileManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.AudioTableHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SidebarHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.SongbarHandler;
import it.unipv.ingsfw.JavaBeats.dao.playable.AudioDAO;
import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.exceptions.SystemErrorException;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class PlayerManager{
  /*---------------------------------------*/
  //Attributes
  /*---------------------------------------*/
  private static JBAudio currentAudioPlaying=null;
  private static JBCollection currentCollectionPlaying=null;
  private static double volume=00.50;
  private static boolean randomized=false;
  private static boolean collectionLooping=false;
  private static boolean audioLooping=false;
  private static final LinkedList<JBAudio> queue=new LinkedList<>();
  private static ArrayList<JBAudio> playingAudiosCopy=new ArrayList<>();
  private final FXAdapter adapter=FXAdapterFactory.getInstance().getFXAdapter();

  /*---------------------------------------*/
  //Constructors
  /*---------------------------------------*/
  public PlayerManager(){
  }

  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public static LinkedList<JBAudio> getQueue(){
    return queue;
  }

  public static JBAudio getCurrentAudioPlaying(){
    return currentAudioPlaying;
  }

  public static JBCollection getCurrentCollectionPlaying(){
    return currentCollectionPlaying;
  }

  public static boolean isRandomized(){
    return randomized;
  }

  public static void setRandomized(boolean randomized){
    PlayerManager.randomized=randomized;
  }

  public static boolean isCollectionLooping(){
    return collectionLooping;
  }

  public static void setCollectionLooping(boolean collectionLooping){
    PlayerManager.collectionLooping=collectionLooping;
  }

  public static boolean isAudioLooping(){
    return audioLooping;
  }

  public static void setAudioLooping(boolean audioLooping){
    PlayerManager.audioLooping=audioLooping;
  }

  public static double getVolume(){
    return volume;
  }

  public static void setVolume(double volume){
    PlayerManager.volume=volume;
  }

  /*---------------------------------------*/
  //Methods
  /*---------------------------------------*/
  /*
   * General playing, here I decide what to do next
   * */
  public void play() throws SystemErrorException{
    /* If queue is not empty I go to the next Audio */
    if(!queue.isEmpty()){
      AudioDAO audioDAO=new AudioDAO();

      /* Blocking audio if it's currently playing */
      if(currentAudioPlaying!=null){
        currentAudioPlaying.getMediaPlayer().dispose();
      }//end-if

      /* Playing the audio from adapter */
      JBAudio audioToBePlayed=queue.pop();
      currentAudioPlaying=audioToBePlayed;
      adapter.play(audioToBePlayed);

      /* Setting the volume */
      currentAudioPlaying.getMediaPlayer().setVolume(volume);

      /* Updating Profile's listening history */
      audioDAO.addToListeningHistory(audioToBePlayed, ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());

      /* Refreshing AudioTable to capture variations of Audios information */
      if(AudioTableHandler.getInstance().getCurrentAudioTableShowing()!=null){
        AudioTableHandler.getInstance().getCurrentAudioTableShowing().refresh();
      }//end-if
    }else if(collectionLooping){
      /* If I'm Looping the collection I call loop() */

      loop(currentCollectionPlaying);
    }else if(audioLooping){
      /* If I'm Looping the single audio, I play directly the audio */
      AudioDAO audioDAO=new AudioDAO();

      /* Blocking song */
      currentAudioPlaying.getMediaPlayer().dispose();

      /* Playing from adapter */
      adapter.play(currentAudioPlaying);

      /* Setting the volume */
      currentAudioPlaying.getMediaPlayer().setVolume(volume);

      /* Updating profile's listening history */
      audioDAO.addToListeningHistory(currentAudioPlaying, ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());

      /* Refreshing AudioTable to capture variations of Audios information */
      if(AudioTableHandler.getInstance().getCurrentAudioTableShowing()!=null){
        AudioTableHandler.getInstance().getCurrentAudioTableShowing().refresh();
      }//end-if
    }else{
      /* Resetting flags if I don't have to play anything */

      playingAudiosCopy.clear();
      currentAudioPlaying=null;
      currentCollectionPlaying=null;
      randomized=false;
    }//end-if

    /* Updating instances of Sidebar and SongBar */
    SidebarHandler sidebarHandler=new SidebarHandler();
    SongbarHandler.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile(), currentAudioPlaying);
  }

  /*
   *  Playing single Audio
   * */
  public void play(JBAudio jbAudio){
    AudioDAO audioDAO=new AudioDAO();

    /* Resetting Queue and currentAudio */
    queue.clear();
    if(currentAudioPlaying!=null){
      currentAudioPlaying.getMediaPlayer().dispose();
    }//end-if

    /* Resetting flags */
    randomized=false;
    collectionLooping=false;
    audioLooping=false;

    /* ResettingCollection */
    currentCollectionPlaying=null;

    /* Playing via adapter */
    currentAudioPlaying=jbAudio;
    adapter.play(jbAudio);

    /* SettingUp volume */
    currentAudioPlaying.getMediaPlayer().setVolume(volume);

    /* Updating Profile's listening history */
    audioDAO.addToListeningHistory(jbAudio, ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile());

    /* Refreshing AudioTable to capture variations of Audios information */
    if(AudioTableHandler.getInstance().getCurrentAudioTableShowing()!=null){
      AudioTableHandler.getInstance().getCurrentAudioTableShowing().refresh();
    }//end-if

    /* Updating instances of SideBar and SongBar handlers */
    SidebarHandler sidebarHandler=new SidebarHandler();
    SongbarHandler.getInstance(ProfileManagerFactory.getInstance().getProfileManager().getActiveProfile(), currentAudioPlaying);
  }

  /*
   * Playing a collection directly
   * */
  public void play(JBCollection jbCollection) throws SystemErrorException{
    /* Keeping track of the collection I'm playing */
    currentCollectionPlaying=jbCollection.getCopy();

    /* Reversing the track list to use the queue */
    Collections.reverse(currentCollectionPlaying.getTrackList());

    /* Stopping everything that was playing */
    queue.clear();
    if(currentAudioPlaying!=null){
      currentAudioPlaying.getMediaPlayer().dispose();
      currentAudioPlaying=null;
    }//end-if

    /* Keeping track of the order of tracks, so that skipping is not a problem */
    playingAudiosCopy=new ArrayList<>(currentCollectionPlaying.getTrackList());

    /* Populating the queue */
    for(JBAudio jbAudio: currentCollectionPlaying.getTrackList()){
      queue.push(jbAudio);
    }//end-foreach

    /* Calling play() to figure what to do next */
    play();
  }

  /*
   * Handling the play/pause functionality
   * */
  public void playPause(){
    /* Calling the adapter to either pause or play the audio */
    if(currentAudioPlaying!=null){
      if(currentAudioPlaying.getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)){
        adapter.pause(currentAudioPlaying);
      }else{
        adapter.play(currentAudioPlaying);
      }//end-if
    }//end-if
  }

  /*
   * Handling playing directly from the queue
   * */
  public void playFromQueue(JBAudio jbAudio) throws SystemErrorException, AccountNotFoundException{
    /* Creating temporary queue from the audio playing onward */
    LinkedList<JBAudio> newQueue=new LinkedList<>(queue.subList(queue.indexOf(jbAudio), queue.size()));

    /* Re-populating the queue with newQueue */
    queue.clear();
    playingAudiosCopy.clear();
    queue.addAll(newQueue);

    /* Calling play() to figure what to do next */
    play();
  }

  /*
   * Adding audio to queue
   * */
  public void addToQueue(JBAudio ijbPlayable){
    queue.push(ijbPlayable);
  }

  /*
   * Removing audio to queue
   * */
  public void removeFromQueue(JBAudio jbAudio){
    queue.remove(jbAudio);
  }

  /*
   * Erasing queue
   * */
  public void deleteQueue(){
    queue.clear();

    /* Resetting flags */
    collectionLooping=false;
    audioLooping=false;
  }

  /*
   * Handles SongBar random button: If a collection is being played and I click random -> I randomize the collection playing
   * */
  public void randomize() throws SystemErrorException{
    /* Resetting flags */
    randomized=false;

    /* If I'm playing a collection */
    if(currentCollectionPlaying!=null){
      queue.clear();

      /* Randomizing the order of tracks */
      Collections.shuffle(currentCollectionPlaying.getTrackList());

      /* Resetting flags */
      randomized=true;
      collectionLooping=false;
      audioLooping=false;
      playingAudiosCopy.clear();

      /* Playing the randomized collection */
      play(currentCollectionPlaying);
    }//end-if
  }

  /*
   * Handles CollectionViewGUI random button
   * */
  public void randomize(JBCollection jbCollection) throws SystemErrorException{
    /* Resetting flags */
    collectionLooping=false;
    audioLooping=false;
    playingAudiosCopy.clear();

    /* Randomizing the order of tracks */
    Collections.shuffle(jbCollection.getTrackList());

    /* Updating flag and playing the randomized collection */
    randomized=true;
    play(jbCollection);
  }

  /* Handles CollectionViewGUI loop button */
  public void loop(JBCollection jbCollection) throws SystemErrorException{
    /* Resetting Flags */
    randomized=false;
    audioLooping=false;
    playingAudiosCopy.clear();

    /* Updating flag and playing the looping collection */
    collectionLooping=true;
    play(jbCollection);
  }

  /*
   * Handles SongBar loop button: reset all flags, and set audioLooping=true
   * */
  public void loop(){
    /* Resetting all flags */
    queue.clear();
    randomized=false;
    collectionLooping=false;
    playingAudiosCopy.clear();

    /* Setting flag */
    audioLooping=true;
  }

  /*
   * Handles audio skip forward
   * */
  public void skipForward() throws SystemErrorException{
    /* Skip forward available only if audio is looping */
    audioLooping=false;

    /* Stopping current audio */
    if(currentAudioPlaying!=null){
      currentAudioPlaying.getMediaPlayer().dispose();
    }//end-if

    /* calling play() to figure what to do next */
    play();
  }

  /*
   * Handles audio skip back
   * */
  public void skipBack() throws SystemErrorException{
    /* skip back available only if I'm playing */
    if(currentAudioPlaying!=null && currentCollectionPlaying!=null){
      audioLooping=false;

      /* Pushing current audio so the next song is the one I'm playing */
      queue.push(currentAudioPlaying);

      /* If I don't go out of bounds I can skip back */
      if((playingAudiosCopy.indexOf(currentAudioPlaying)+1)<playingAudiosCopy.size() && (playingAudiosCopy.indexOf(currentAudioPlaying)+1)>0){
        queue.push(playingAudiosCopy.get(playingAudiosCopy.indexOf(currentAudioPlaying)+1));
      }//end-if

      /* Calling play() to figure what to do next */
      play();
    }//end-if
  }
  /*---------------------------------------*/
}
