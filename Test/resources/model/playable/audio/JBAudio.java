package it.unipv.ingsfw.JavaBeats.model.playable.audio;

import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.playable.IJBPlayable;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import javafx.scene.media.MediaPlayer;

import java.sql.Blob;
import java.sql.Date;

/**
 * Abstract class representing a generic JavaBeats audio track.
 * Contains attributes and methods common both to {@link Song} and {@link Episode}.
 *
 * @author Giorgio Giacomotti
 * @see IJBResearchable
 * @see Song
 * @see Episode
 */
public abstract class JBAudio implements IJBPlayable {

  //ATTRIBUTES:
  private int id;
  private boolean isFavorite;
  private Metadata metadata;
  private Blob audioFile;
  private int numberOfStreams;
  protected MediaPlayer mediaPlayer;

  /**
   * Nested class representing the metadata associated with every {@link JBAudio}.
   *
   * @author Giorgio Giacomotti
   * @see JBAudio
   * @see Song
   * @see Episode
   */
  public class Metadata {

    //NESTED CLASS ATTRIBUTES:
    private Artist artist;
    private String title;
    private JBCollection collection;
    private double duration;
    private Date releaseDate;
    private String[] genres;

    //NESTED CLASS CONSTRUCTOR:

    /**
     * Complete constructor to initialize all nested class parameters.
     */
    public Metadata(Artist artist, String title, JBCollection collection, double duration, Date releaseDate, String[] genres) {
      this.artist = artist;
      this.title = title;
      this.collection = collection;
      this.duration = duration;
      this.releaseDate = releaseDate;
      this.genres = genres;
    }

    //NESTED CLASS GETTERS & SETTERS:

    /**
     * Returns the {@link Artist} who created the audio.
     *
     * @return artist
     */
    public Artist getArtist() {
      return artist;
    }

    /**
     * Sets {@link Artist} as the new creator of the audio.
     *
     * @param artist new artist
     */
    public void setArtist(Artist artist) {
      this.artist = artist;
    }

    /**
     * Returns the title of the audio as a {@link String}.
     *
     * @return title
     */
    public String getTitle() {
      return title;
    }

    /**
     * Sets {@link String} as the audio title.
     *
     * @param title new title
     */
    public void setTitle(String title) {
      this.title = title;
    }

    /**
     * Returns the duration of the audio as a double.
     *
     * @return duration
     */
    public double getDuration() {
      return duration;
    }

    /**
     * Sets double as the new audio duration.
     *
     * @param duration new duration
     */
    public void setDuration(double duration) {
      this.duration = duration;
    }

    /**
     * Returns the audio release date as a {@link Date}.
     *
     * @return release date
     */
    public Date getReleaseDate() {
      return releaseDate;
    }

    /**
     * Sets {@link Date} as the new release date.
     *
     * @param releaseDate new release date
     */
    public void setReleaseDate(Date releaseDate) {
      this.releaseDate = releaseDate;
    }

    /**
     * Returns the audio genres as an array of {@link String}.
     *
     * @return array of genres
     */
    public String[] getGenres() {
      return genres;
    }

    /**
     * Sets an array of {@link String} as the genres associated with the audio.
     *
     * @param genres new genres array
     */
    public void setGenres(String[] genres) {
      this.genres = genres;
    }

    /**
     * Returns the {@link JBCollection} it belongs to.
     *
     * @return collection
     */
    public JBCollection getCollection() {
      return collection;
    }

    /**
     * Sets {@link JBCollection} to which it belongs.
     *
     * @param collection new collection
     */
    public void setCollection(JBCollection collection) {
      this.collection = collection;
    }
  }


  //CONSTRUCTOR:

  /**
   * Complete constructor to initialize all parameters.
   * Note that an abstract class cannot be instantiated, this constructor will be used by classes that extend JBAudio to initialize parameters.
   */
  protected JBAudio(int id, String title, Artist artist, JBCollection collection, Blob audioFile, double duration, Date releaseDate, String[] genres, boolean isFavorite, int numberOfStreams) {
    this.id = id;
    metadata = new Metadata(artist, title, collection, duration, releaseDate, genres);
    this.audioFile = audioFile;
    this.isFavorite = isFavorite;
    this.numberOfStreams = numberOfStreams;
    mediaPlayer = null;
  }


  //GETTERS:

  /**
   * Returns profile id as an int.
   *
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * Returns true if {@link JBAudio} is marked as favorite.
   *
   * @return is favorite
   */
  public boolean isFavorite() {
    return isFavorite;
  }

  /**
   * Returns total number of streams as an int.
   *
   * @return total number of streams
   */
  public int getNumberOfStreams() {
    return numberOfStreams;
  }

  /**
   * Returns {@link Metadata} associated with this audio.
   *
   * @return metadata
   */
  public Metadata getMetadata() {
    return metadata;
  }

  /**
   * Returns the audioFile as a {@link Blob}.
   *
   * @return audio file
   */
  public Blob getAudioFileBlob() {
    return audioFile;
  }

  /**
   * Returns current {@link MediaPlayer} associated with this audio.
   *
   * @return media player
   */
  public MediaPlayer getMediaPlayer() {
    return mediaPlayer;
  }

  //SETTER:

  /**
   * Sets int as the new id.
   *
   * @param id new id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Sets {@link Boolean} as the new favorite marker.
   *
   * @param isFavorite is favorite
   */
  public void setFavorite(boolean isFavorite) {
    this.isFavorite = this.isFavorite;
  }

  /**
   * Sets int as the new total number of streams.
   *
   * @param numberOfStreams new total number of streams
   */
  public void setNumberOfStreams(int numberOfStreams) {
    this.numberOfStreams = numberOfStreams;
  }

  /**
   * Sets {@link Metadata} as the new metadata associated with this audio.
   *
   * @param metadata new metadata
   */
  public void setMetadata(Metadata metadata) {
    this.metadata = metadata;
  }

  /**
   * Sets {@link Blob} as the audio file.
   *
   * @param audioFile new audio file
   */
  public void setAudioFileBlob(Blob audioFile) {
    this.audioFile = audioFile;
  }

  /**
   * Override of equals to compare {@link JBAudio}.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }//end-if

    JBAudio jbAudio = (JBAudio) obj;

    if (this.id == jbAudio.getId()) {
      return true;
    }//end-if
    return false;
  }

  /**
   * Converts milliseconds passed as double into minutes and seconds as a {@link String} formatted as MM:SS.
   *
   * @param milliSeconds milliseconds to convert
   * @return equivalent time in minutes and second
   */
  public static String convertToMinutesAndSeconds(double milliSeconds) {
    long totalSeconds = (long) (milliSeconds / 1000);
    int minutes = (int) (totalSeconds / 60);
    int seconds = (int) (totalSeconds % 60);

    return String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
  }

}
