package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.controller.factory.DBManagerFactory;
import it.unipv.ingsfw.JavaBeats.dao.playable.AudioDAO;
import it.unipv.ingsfw.JavaBeats.dao.collection.CollectionDAO;
import it.unipv.ingsfw.JavaBeats.dao.playable.IAudioDAO;
import it.unipv.ingsfw.JavaBeats.dao.profile.ProfileDAO;
import it.unipv.ingsfw.JavaBeats.model.EJBENTITY;
import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.collection.Podcast;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.model.profile.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Implementation of the methods declared in {@link IAudioDAO}.
 * It encapsulates database interactions, providing a clean and reusable interface for accessing {@link JBAudio} data.
 *
 * @author Giorgio Giacomotti
 * @see JBAudio
 * @see Song
 * @see Episode
 * @see it.unipv.ingsfw.JavaBeats.model.collection.JBCollection
 */
public class SearchDAO implements ISearchDAO {

  //ATTRIBUTES:
  private static final String schema = "JavaBeats_DB";
  private Connection connection;


  //CONTRUCTOR:
  public SearchDAO() {
    super();
  }


  //PUBLIC METHODS:
  @Override
  public ArrayList<IJBResearchable> search(String field, JBProfile activeProfile) {
    return search(field, activeProfile, null);
  }

  @Override
  public ArrayList<IJBResearchable> search(String field, JBProfile activeProfile, EJBENTITY mode) {
    ArrayList<IJBResearchable> result = new ArrayList<>();

    switch (mode) {
      case SONG:
        result.addAll(searchSongs(field, activeProfile));
        break;

      case EPISODE:
        result.addAll(searchEpisodes(field, activeProfile));
        break;

      case PLAYLIST:
        result.addAll(searchPlaylists(field));
        break;

      case ALBUM:
        result.addAll(searchAlbums(field));
        break;

      case PODCAST:
        result.addAll(searchPodcasts(field));
        break;

      case USER:
        result.addAll(searchUsers(field));
        break;

      case ARTIST:
        result.addAll(searchArtists(field));
        break;

      default:
        result.addAll(searchSongs(field, activeProfile));
        result.addAll(searchEpisodes(field, activeProfile));
        result.addAll(searchPlaylists(field));
        result.addAll(searchAlbums(field));
        result.addAll(searchPodcasts(field));
        result.addAll(searchUsers(field));
        result.addAll(searchArtists(field));
    }

    return result;
  }


  //PRIVATE METHODS
  private ArrayList<User> searchUsers(String field) {
    connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    ArrayList<User> result = new ArrayList<>();

    try {
      String query = "SELECT Profile.mail FROM Profile JOIN User ON Profile.mail = User.mail " +
              "WHERE (name LIKE ? OR surname LIKE ? OR username LIKE ?) AND isVisible=true;";

      st = connection.prepareStatement(query);
      st.setString(1, "%" + field + "%");
      st.setString(2, "%" + field + "%");
      st.setString(3, "%" + field + "%");

      rs = st.executeQuery();

      while (rs.next()) {
        result.add(new User(null, rs.getString("mail"), null));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    ProfileDAO pDAO = new ProfileDAO();
    ArrayList<User> userList = new ArrayList<>();
    Iterator<User> resultIT = result.iterator();
    while (resultIT.hasNext()) {
      userList.add(pDAO.getUser(resultIT.next()));
    }

    return userList;
  }


  private ArrayList<Artist> searchArtists(String field) {
    connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    ArrayList<Artist> result = new ArrayList<>();

    try {
      String query = "SELECT Profile.mail AS mail FROM Profile JOIN Artist ON Profile.mail = Artist.mail " +
              "WHERE name LIKE ? OR surname LIKE ? OR username LIKE ?;";

      st = connection.prepareStatement(query);
      st.setString(1, "%" + field + "%");
      st.setString(2, "%" + field + "%");
      st.setString(3, "%" + field + "%");

      rs = st.executeQuery();

      while (rs.next()) {
        result.add(new Artist(null, rs.getString("mail"), null));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    ProfileDAO pDAO = new ProfileDAO();
    ArrayList<Artist> artistList = new ArrayList<>();
    Iterator<Artist> resultIT = result.iterator();
    while (resultIT.hasNext()) {
      artistList.add(pDAO.getArtist(resultIT.next()));
    }

    return artistList;
  }


  private ArrayList<Song> searchSongs(String field, JBProfile activeProfile) {
    connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    ArrayList<Song> result = new ArrayList<>();
    ArrayList<Song> tmp = new ArrayList<>();


    try {
      String query = "SELECT Audio.id FROM Audio " +
              "JOIN Song ON Audio.id = Song.id " +
              "JOIN ArtistAudios ON Audio.id = ArtistAudios.idAudio " +
              "JOIN Profile ON ArtistAudios.artistMail = Profile.mail " +
              "JOIN AlbumSongs ON Audio.id = AlbumSongs.idSong " +
              "JOIN Collection ON AlbumSongs.idAlbum = Collection.id " +
              "WHERE Audio.title LIKE ? OR Profile.username LIKE ? OR Collection.name LIKE ?;";

      st = connection.prepareStatement(query);
      st.setString(1, "%" + field + "%");
      st.setString(2, "%" + field + "%");
      st.setString(3, "%" + field + "%");

      rs = st.executeQuery();

      while (rs.next()) {
        tmp.add(new Song(rs.getInt("id"), null, null, null));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    AudioDAO aDAO = new AudioDAO();

    for (Song song : tmp) {
      result.add(aDAO.getSong(song, activeProfile));      //get complete info of song
    }

    return result;
  }


  private ArrayList<Episode> searchEpisodes(String field, JBProfile activeProfile) {
    connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    ArrayList<Episode> result = new ArrayList<>();

    try {
      String query = "SELECT Audio.id FROM Audio " +
              "JOIN Episode ON Audio.id = Episode.id " +
              "JOIN ArtistAudios ON Audio.id = ArtistAudios.idAudio " +
              "JOIN Profile ON ArtistAudios.artistMail = Profile.mail " +
              "JOIN PodcastEpisodes ON Audio.id = PodcastEpisodes.idEpisode " +
              "JOIN Collection ON PodcastEpisodes.idPodcast = Collection.id " +
              "WHERE Audio.title LIKE ? OR Profile.username LIKE ? OR Collection.name LIKE ?;";

      st = connection.prepareStatement(query);
      st.setString(1, "%" + field + "%");
      st.setString(2, "%" + field + "%");
      st.setString(3, "%" + field + "%");

      rs = st.executeQuery();

      while (rs.next()) {
        result.add(new Episode(rs.getInt("id"), null, null, null));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    AudioDAO aDAO = new AudioDAO();
    for (Episode episode : result) {
      episode = aDAO.getEpisode(episode, activeProfile);      //get complete info of episode
    }

    return result;
  }


  private ArrayList<Playlist> searchPlaylists(String field) {
    connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    ArrayList<Playlist> result = new ArrayList<>();

    try {
      String query = "SELECT Collection.id FROM Collection " +
              "JOIN Playlist ON Collection.id = Playlist.id " +
              "JOIN ProfilePlaylists ON Collection.id = ProfilePlaylists.idPlaylist " +
              "JOIN Profile ON ProfilePlaylists.profileMail = Profile.mail " +
              "WHERE (Collection.name LIKE ? OR Profile.username LIKE ?) AND Playlist.isVisible=true;";

      st = connection.prepareStatement(query);
      st.setString(1, "%" + field + "%");
      st.setString(2, "%" + field + "%");

      rs = st.executeQuery();

      while (rs.next()) {
        result.add(new Playlist(rs.getInt("id"), null, null));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    CollectionDAO cDAO = new CollectionDAO();
    ArrayList<Playlist> playlistList = new ArrayList<>();
    Iterator<Playlist> resultIT = result.iterator();
    while (resultIT.hasNext()) {
      playlistList.add(cDAO.getPlaylist(resultIT.next()));
    }

    return playlistList;
  }


  private ArrayList<Album> searchAlbums(String field) {
    connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    ArrayList<Album> result = new ArrayList<>();

    try {
      String query = "SELECT Collection.id FROM Collection " +
              "JOIN Album ON Collection.id = Album.id " +
              "JOIN ArtistAlbums ON Collection.id = ArtistAlbums.idAlbum " +
              "JOIN Profile ON ArtistAlbums.ArtistMail = Profile.mail " +
              "WHERE Collection.name LIKE ? OR Profile.username LIKE ?;";

      st = connection.prepareStatement(query);
      st.setString(1, "%" + field + "%");
      st.setString(2, "%" + field + "%");

      rs = st.executeQuery();

      while (rs.next()) {
        result.add(new Album(rs.getInt("id"), null, null, null));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    CollectionDAO cDAO = new CollectionDAO();
    ArrayList<Album> albumList = new ArrayList<>();
    Iterator<Album> resultIT = result.iterator();
    while (resultIT.hasNext()) {
      albumList.add(cDAO.getAlbum(resultIT.next()));
    }

    return albumList;
  }


  private ArrayList<Podcast> searchPodcasts(String field) {
    connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    ArrayList<Podcast> result = new ArrayList<>();

    try {
      String query = "SELECT Collection.id FROM Collection " +
              "JOIN Podcast ON Collection.id = Podcast.id " +
              "JOIN ArtistPodcasts ON Collection.id = ArtistPodcasts.idPodcast " +
              "JOIN Profile ON ArtistPodcasts.ArtistMail = Profile.mail " +
              "WHERE Collection.name LIKE ? OR Profile.username LIKE ?;";

      st = connection.prepareStatement(query);
      st.setString(1, "%" + field + "%");
      st.setString(2, "%" + field + "%");

      rs = st.executeQuery();

      while (rs.next()) {
        result.add(new Podcast(rs.getInt("id"), null, null, null));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    CollectionDAO cDAO = new CollectionDAO();
    ArrayList<Podcast> podcastList = new ArrayList<>();
    Iterator<Podcast> resultIT = result.iterator();
    while (resultIT.hasNext()) {
      podcastList.add(cDAO.getPodcast(resultIT.next()));
    }

    return podcastList;
  }

}