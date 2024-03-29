package it.unipv.ingsfw.JavaBeats.dao.playable;

import it.unipv.ingsfw.JavaBeats.controller.factory.DBManagerFactory;
import it.unipv.ingsfw.JavaBeats.dao.collection.CollectionDAO;
import it.unipv.ingsfw.JavaBeats.dao.profile.ProfileDAO;
import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.*;
import it.unipv.ingsfw.JavaBeats.model.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.collection.Podcast;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.model.profile.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Implementation of the methods declared in {@link IAudioDAO}.
 * It encapsulates database interactions, providing a clean and reusable interface for accessing {@link JBAudio} data.
 *
 * @see JBAudio
 * @see Song
 * @see Episode
 * @see it.unipv.ingsfw.JavaBeats.model.collection.JBCollection
 */
public class AudioDAO implements IAudioDAO{

  //ATTRIBUTES:
  private static final String schema="JavaBeats_DB";
  private Connection connection;


  //CONSTRUCTOR:
  public AudioDAO(){
    super();
  }


  //PUBLIC METHODS:
  @Override
  public void insert(JBAudio audio) throws AccountNotFoundException{

    insertAudio(audio);             //insert audio to Audio table

    linkAudioToArtist(audio);       //link audio (already present in db) to artist (from audio metadata)

    linkAudioToCollection(audio);   //link audio to Album or Podcast (from audio metadata)

    linkAudioToGenres(audio);       //link audio to Genres (from audio metadata)

  }

  @Override
  public void remove(JBAudio audio){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;

    try{
      String query="DELETE FROM Audio WHERE id=?;";

      st=connection.prepareStatement(query);
      st.setInt(1, audio.getId());

      st.executeUpdate();

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  @Override
  public void updateIsFavorite(JBProfile activeProfile) throws AccountNotFoundException{
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;

    CollectionDAO collectionDAO=new CollectionDAO();
    Playlist currentDBFavorites=collectionDAO.getFavorites(activeProfile);

    try{
      String query;
      /* For each Audio currently in the DB I check if it's in the updated Favorites playlist */
      for(JBAudio jbAudio: currentDBFavorites.getTrackList()){
        /* If the audio is not in the new Favorites then I have to remove it from the DB */
        if(!activeProfile.getFavorites().getTrackList().contains(jbAudio)){
          query="DELETE FROM Favorites WHERE (profileMail=? AND idAudio=?);";
          st=connection.prepareStatement(query);
          st.setString(1, activeProfile.getMail());
          st.setInt(2, jbAudio.getId());
          st.executeUpdate();
        }//end-if
      }//end-foreach
      /* For each Audio in the updated Favorites playlist I check if it's currently in the DB */
      for(JBAudio jbAudio: activeProfile.getFavorites().getTrackList()){
        /* If the audio is not in the currentDB ones then I have to add it in the DB */
        if(!currentDBFavorites.getTrackList().contains(jbAudio)){
          query="INSERT INTO Favorites(profileMail, idAudio) VALUES(?, ?);";
          st=connection.prepareStatement(query);
          st.setString(1, activeProfile.getMail());
          st.setInt(2, jbAudio.getId());
          st.executeUpdate();
        }//end-if
      }//end-foreach
    }catch(SQLException e){
      e.printStackTrace();
    }//end-try

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  @Override
  public JBAudio get(JBAudio audio, JBProfile activeProfile) throws AccountNotFoundException{

    JBAudio audioOut=getSong(audio, activeProfile);
    if(audioOut==null)
      audioOut=getEpisode(audio, activeProfile);

    return audioOut;
  }

  @Override
  public Song getSong(JBAudio audio, JBProfile activeProfile) throws AccountNotFoundException{
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    Song result=null;

    try{
      String query="SELECT DISTINCT Audio.id, title, artistMail, idAlbum, audioFile, duration, releaseDate "+
              "FROM Audio "+
              "JOIN Song ON Audio.id=Song.id "+
              "JOIN ArtistAudios ON Audio.id=ArtistAudios.idAudio "+
              "JOIN AlbumSongs ON Audio.id=AlbumSongs.idSong "+
              "WHERE Audio.id=?;";

      st=connection.prepareStatement(query);
      st.setInt(1, audio.getId());

      rs=st.executeQuery();

      if(rs.next()){
        result=new Song(rs.getInt("id"),
                rs.getString("title"),
                new Artist(null, rs.getString("artistMail"), null),
                new Album(rs.getInt("idAlbum"), null, null, null),
                rs.getBlob("audioFile"),
                rs.getDouble("duration"),
                rs.getDate("releaseDate"),
                null,
                false,
                0);
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    if(result!=null){
      result.getMetadata().setGenres(getGenres(result));      //set Genres in metadata
      result.setNumberOfStreams(getNumberOfStreams(result));  //set total number of streams

      ProfileDAO pDAO=new ProfileDAO();
      CollectionDAO cDAO=new CollectionDAO();
      result.getMetadata().setArtist(pDAO.getArtist(result.getMetadata().getArtist()));       //set complete artist profile

      result.getMetadata().setCollection(cDAO.get(result.getMetadata().getCollection()));     //set complete collection data

      if(activeProfile!=null)
        result.setFavorite(isFavorite(result, activeProfile));  //set isFavorite (only if profile is specified)
    }

    return result;
  }

  @Override
  public Episode getEpisode(JBAudio audio, JBProfile activeProfile) throws AccountNotFoundException{
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    Episode result=null;

    try{
      String query="SELECT DISTINCT Audio.id, title, artistMail, idPodcast, audioFile, duration, releaseDate "+
              "FROM Audio "+
              "JOIN Episode ON Audio.id=Episode.id "+
              "JOIN ArtistAudios ON Audio.id=ArtistAudios.idAudio "+
              "JOIN PodcastEpisodes ON Audio.id=PodcastEpisodes.idEpisode "+
              "WHERE Audio.id=?;";

      st=connection.prepareStatement(query);
      st.setInt(1, audio.getId());

      rs=st.executeQuery();

      if(rs.next()){
        result=new Episode(rs.getInt("id"),
                rs.getString("title"),
                new Artist(null, rs.getString("artistMail"), null),
                new Podcast(rs.getInt("idPodcast"), null, null, null),
                rs.getBlob("audioFile"),
                rs.getDouble("duration"),
                rs.getDate("releaseDate"),
                null,
                false,
                0);
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    if(result!=null){
      result.getMetadata().setGenres(getGenres(result));      //set Genres in metadata
      result.setNumberOfStreams(getNumberOfStreams(result));  //set total number of streams

      ProfileDAO pDAO=new ProfileDAO();
      CollectionDAO cDAO=new CollectionDAO();
      result.getMetadata().setArtist(pDAO.getArtist(result.getMetadata().getArtist()));       //set complete artist profile
      result.getMetadata().setCollection(cDAO.get(result.getMetadata().getCollection()));     //set complete collection data

      if(activeProfile!=null)
        result.setFavorite(isFavorite(result, activeProfile));  //set isFavorite (only if profile is specified)
    }

    return result;
  }

  @Override
  public void addToListeningHistory(JBAudio audio, JBProfile activeProfile){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;

    try{
      String query="INSERT INTO ListeningHistory(idListeningHistory, profileMail, idAudio) VALUES(default, ?, ?);";

      st=connection.prepareStatement(query);
      st.setString(1, activeProfile.getMail());
      st.setInt(2, audio.getId());
      st.executeUpdate();
    }catch(Exception e){
      e.printStackTrace();
    }//end-try

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    try{
      User u=(User)activeProfile;
      ProfileDAO profileDAO=new ProfileDAO();

      u.setMinuteListened(profileDAO.getTotalListeningTime(u));
    }catch(ClassCastException ignored){
    }//end-try

    /* Updating number of streams */
    audio.setNumberOfStreams(getNumberOfStreams(audio));
  }

  @Override
  public ArrayList<JBAudio> selectByPlaylist(Playlist playlist, JBProfile activeProfile) throws AccountNotFoundException{
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    ArrayList<JBAudio> result=new ArrayList<>();

    try{
      String query="SELECT idAudio FROM PlaylistAudios WHERE idPlaylist=? LIMIT 25;";

      st=connection.prepareStatement(query);
      st.setInt(1, playlist.getId());

      rs=st.executeQuery();

      while(rs.next()){
        result.add(new Song(rs.getInt("idAudio"), null, null, null));
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    ArrayList<JBAudio> audioList=new ArrayList<>();
    Iterator<JBAudio> resultIT=result.iterator();
    while(resultIT.hasNext()){
      audioList.add(get(resultIT.next(), activeProfile));
    }

    return audioList;
  }

  @Override
  public ArrayList<Song> selectByAlbum(Album album, JBProfile activeProfile) throws AccountNotFoundException{
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    ArrayList<Song> result=new ArrayList<>();

    try{
      String query="SELECT idSong FROM AlbumSongs WHERE idAlbum=? LIMIT 25;";

      st=connection.prepareStatement(query);
      st.setInt(1, album.getId());

      rs=st.executeQuery();

      while(rs.next()){
        result.add(new Song(rs.getInt("idSong"), null, null, null));
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    ArrayList<Song> songList=new ArrayList<>();
    Iterator<Song> resultIT=result.iterator();
    while(resultIT.hasNext()){
      songList.add(getSong(resultIT.next(), activeProfile));
    }

    return songList;
  }

  @Override
  public ArrayList<Episode> selectByPodcast(Podcast podcast, JBProfile activeProfile) throws AccountNotFoundException{
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    ArrayList<Episode> result=new ArrayList<>();

    try{
      String query="SELECT idEpisode FROM PodcastEpisodes WHERE idPodcast=? LIMIT 25;";

      st=connection.prepareStatement(query);
      st.setInt(1, podcast.getId());

      rs=st.executeQuery();

      while(rs.next()){
        result.add(new Episode(rs.getInt("idEpisode"), null, null, null));
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    ArrayList<Episode> episodeList=new ArrayList<>();
    Iterator<Episode> resultIT=result.iterator();
    while(resultIT.hasNext()){
      episodeList.add(getEpisode(resultIT.next(), activeProfile));
    }

    return episodeList;
  }

  @Override
  public ArrayList<JBAudio> selectFavorites(JBProfile activeProfile) throws AccountNotFoundException{
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    ArrayList<JBAudio> result=new ArrayList<>();

    try{
      String query="SELECT idAudio FROM Favorites WHERE profileMail=?;";

      st=connection.prepareStatement(query);
      st.setString(1, activeProfile.getMail());

      rs=st.executeQuery();

      while(rs.next()){
        result.add(new Song(rs.getInt("idAudio"), null, null, null));
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    ArrayList<JBAudio> audioList=new ArrayList<>();
    Iterator<JBAudio> resultIT=result.iterator();
    while(resultIT.hasNext()){
      audioList.add(get(resultIT.next(), activeProfile));
    }

    return audioList;
  }


  //PRIVATE METHODS:
  private String[] getGenres(JBAudio audio){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    ArrayList<String> result=new ArrayList<>();

    try{
      String query="SELECT genre FROM AudioGenres WHERE idAudio=? LIMIT 10;";

      st=connection.prepareStatement(query);
      st.setInt(1, audio.getId());

      rs=st.executeQuery();

      while(rs.next()){
        String str=rs.getString("genre");
        result.add(str);
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    String[] arrayOut=new String[result.size()];      //converting arrayList to Array
    arrayOut=result.toArray(arrayOut);

    return arrayOut;
  }

  private int getNumberOfStreams(JBAudio audio){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    int result=0;

    try{
      String query="SELECT count(idAudio) as 'total' FROM ListeningHistory WHERE idAudio=?;";

      st=connection.prepareStatement(query);
      st.setInt(1, audio.getId());

      rs=st.executeQuery();

      if(rs.next()){
        result=rs.getInt("total");
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    return result;
  }

  private boolean isFavorite(JBAudio audio, JBProfile activeProfile){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    boolean result=false;

    try{
      String query="SELECT idAudio FROM Favorites WHERE profileMail=? AND idAudio=?;";

      st=connection.prepareStatement(query);
      st.setString(1, activeProfile.getMail());
      st.setInt(2, audio.getId());

      rs=st.executeQuery();

      result=rs.next();     //result=true if exists at least one instance

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    return result;
  }

  private void insertAudio(JBAudio audio){
    //insert audio to Audio table
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st1, st2, st3, st4;
    ResultSet rs2;

    try{
      String q1="INSERT INTO Audio(id, title, duration, releaseDate, audioFile)"+
              "VALUES(default, ?, ?, ?, ?);";

      st1=connection.prepareStatement(q1);
      st1.setString(1, audio.getMetadata().getTitle());
      st1.setDouble(2, audio.getMetadata().getDuration());
      st1.setDate(3, audio.getMetadata().getReleaseDate());
      st1.setBlob(4, audio.getAudioFileBlob());

      st1.executeUpdate();

      String q2="SELECT LAST_INSERT_ID();";
      st2=connection.prepareStatement(q2);
      rs2=st2.executeQuery();

      if(rs2.next())
        audio.setId(rs2.getInt("LAST_INSERT_ID()"));

      try{                           //if audio is a Song insert it into the Song table
        Song s=(Song)audio;
        String q3="INSERT INTO Song(id) VALUES(?);";
        st3=connection.prepareStatement(q3);
        st3.setInt(1, s.getId());
        st3.executeUpdate();
      }catch(ClassCastException e){  //if audio is an Episode insert it into the Episode table
        String q4="INSERT INTO Episode(id) VALUES(?);";
        st4=connection.prepareStatement(q4);
        st4.setInt(1, audio.getId());
        st4.executeUpdate();
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  private void linkAudioToArtist(JBAudio audio){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;

    try{
      String query="INSERT INTO ArtistAudios(idAudio, artistMail) VALUES(?, ?);";

      st=connection.prepareStatement(query);
      st.setInt(1, audio.getId());
      st.setString(2, audio.getMetadata().getArtist().getMail());

      st.executeUpdate();

    }catch(Exception ignored){

    }//end-try

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  private void linkAudioToCollection(JBAudio audio){
    if(audio.getMetadata().getCollection()!=null){       //sometimes metadata don't specify collection

      connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
      PreparedStatement st;

      try{
        try{                           //audio is a song --> link song to an Album
          Song s=(Song)audio;
          String query="INSERT INTO AlbumSongs(idSong, idAlbum) VALUES(?, ?);";
          st=connection.prepareStatement(query);
          st.setInt(1, s.getId());
          st.setInt(2, s.getMetadata().getCollection().getId());
          st.executeUpdate();
        }catch(ClassCastException e){  //audio is an Episode --> link episode to a Podcast
          String query="INSERT INTO PodcastEpisodes(idEpisode, idPodcast) VALUES(?, ?);";
          st=connection.prepareStatement(query);
          st.setInt(1, audio.getId());
          st.setInt(2, audio.getMetadata().getCollection().getId());
          st.executeUpdate();
        }
      }catch(Exception ignored){

      }//end-try

      DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    }
  }

  private void linkAudioToGenres(JBAudio audio){

    if(audio.getMetadata().getGenres()!=null){       //sometimes metadata don't specify genres
      String[] genreArray=audio.getMetadata().getGenres();

      connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
      PreparedStatement st1, st2, st3;
      ResultSet rs1;

      try{
        for(String s: genreArray){                                //for every genre

          String q1="SELECT genre FROM Genre WHERE genre=?;";
          st1=connection.prepareStatement(q1);

          st1.setString(1, s);                        //check if genre is already in DB
          rs1=st1.executeQuery();

          if(!rs1.isBeforeFirst()){         //if genre not in DB
            String q2="INSERT INTO Genre(genre) VALUE(?);";
            st2=connection.prepareStatement(q2);

            st2.setString(1, s);                    //insert new genre
            st2.executeUpdate();
          }

          String q3="INSERT INTO AudioGenres(idAudio, genre) VALUE(?, ?);";
          st3=connection.prepareStatement(q3);

          st3.setInt(1, audio.getId());               //link audio to genre
          st3.setString(2, s);
          st3.executeUpdate();
        }//end-for
      }catch(Exception ignored){
      }//end-try

      DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    }
  }

}