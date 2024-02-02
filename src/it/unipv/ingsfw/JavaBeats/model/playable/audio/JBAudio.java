package it.unipv.ingsfw.JavaBeats.model.playable.audio;

import it.unipv.ingsfw.JavaBeats.model.playable.IJBItem;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import javafx.scene.media.MediaPlayer;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Time;

public abstract class JBAudio implements IJBItem{

  //ATTRIBUTES:
  private int id;
  private boolean isFavorite;
  private Metadata metadata;
  private Blob audioFile;
  private int numberOfStreams;
  protected MediaPlayer mediaPlayer;

  public class Metadata{

    //NESTED CLASS ATTRIBUTES:
    private Artist artist;
    private String title;
    private JBCollection collection;
    private double duration;
    private Date releaseDate;
    private String[] genres;

    //NESTED CLASS CONSTRUCTOR:
    public Metadata(Artist artist, String title, JBCollection collection, double duration, Date releaseDate, String[] genres){
      this.artist=artist;
      this.title=title;
      this.collection=collection;
      this.duration=duration;
      this.releaseDate=releaseDate;
      this.genres=genres;
    }

    //NESTED CLASS GETTERS & SETTERS:
    public Artist getArtist(){
      return artist;
    }

    public void setArtist(Artist artist){
      this.artist=artist;
    }

    public String getTitle(){
      return title;
    }

    public void setTitle(String title){
      this.title=title;
    }

    public double getDuration(){
      return duration;
    }

    public void setDuration(double duration){
      this.duration=duration;
    }

    public Date getReleaseDate(){
      return releaseDate;
    }

    public void setReleaseDate(Date releaseDate){
      this.releaseDate=releaseDate;
    }

    public String[] getGenres(){
      return genres;
    }

    public void setGenres(String[] genres){
      this.genres=genres;
    }

    public JBCollection getCollection(){
      return collection;
    }

    public void setCollection(JBCollection collection){
      this.collection=collection;
    }
  }


  //CONSTRUCTORS:
  public JBAudio(int id, String title, Artist artist, JBCollection collection, Blob audioFile, double duration, Date releaseDate, String[] genres, boolean isFavorite, int numberOfStreams){
    this.id=id;
    metadata=new Metadata(artist, title, collection, duration, releaseDate, genres);
    this.audioFile=audioFile;
    this.isFavorite=isFavorite;
    this.numberOfStreams=numberOfStreams;
    mediaPlayer=null;
  }

  public JBAudio(int id, String title, Artist artist, Blob audioFile){
    this(id, title, artist, null, audioFile, 00.00, new Date(System.currentTimeMillis()), null, false, 0);
  }


  //GETTERS:
  public int getId(){
    return id;
  }

  public boolean isFavorite(){
    return isFavorite;
  }

  public int getNumberOfStreams(){
    return numberOfStreams;
  }

  public Metadata getMetadata(){
    return metadata;
  }

  public Blob getAudioFileBlob(){
    return audioFile;
  }

  public MediaPlayer getMediaPlayer(){
    return mediaPlayer;
  }

  //SETTER:
  public void setId(int id){
    this.id=id;
  }

  public void setFavorite(boolean isFavorite){
    this.isFavorite=this.isFavorite;
  }

  public void setNumberOfStreams(int numberOfStreams){
    this.numberOfStreams=numberOfStreams;
  }

  public void setMetadata(Metadata metadata){
    this.metadata=metadata;
  }

  public void setAudioFileBlob(Blob audioFile){
    this.audioFile=audioFile;
  }

  @Override
  public boolean equals(Object obj){
    if(obj==null){
      return false;
    }//end-if
    
    JBAudio jbAudio=(JBAudio)obj;

    if(this.id==jbAudio.getId()){
      return true;
    }//end-if
    return false;
  }

  public static String convertToMinutesAndSeconds(double milliSeconds){
    long totalSeconds=(long)(milliSeconds/1000);
    int minutes=(int)(totalSeconds/60);
    int seconds=(int)(totalSeconds%60);

    return String.format("%02d", minutes)+":"+String.format("%02d", seconds);
  }
}
