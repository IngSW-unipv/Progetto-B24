package it.unipv.ingsfw.JavaBeats.dao.playable;

import it.unipv.ingsfw.JavaBeats.controller.factory.DBManagerFactory;
import it.unipv.ingsfw.JavaBeats.dao.profile.ProfileDAO;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.*;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.*;
import it.unipv.ingsfw.JavaBeats.model.profile.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class CollectionDAO implements ICollectionDAO{

  //ATTRIBUTES:
  private final String schema="JavaBeats_DB";
  private Connection connection;


  //CONSTRUCTOR:
  public CollectionDAO(){
    super();
  }


  //PUBLIC METHODS:
  @Override
  public JBCollection insert(JBCollection collection){
    insertCollection(collection);

    linkCollectionToProfile(collection);

    linkCollectionToAudios(collection);

    return collection;
  }

  @Override
  public void remove(JBCollection collection){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;

    try{
      String query="DELETE FROM Collection WHERE id=?;";

      st=connection.prepareStatement(query);
      st.setInt(1, collection.getId());

      st.executeUpdate();

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  @Override
  public void update(JBCollection collection){
    JBCollection oldCollection=get(collection);

    if(collection.getName()!=null)               //check for null before using .equals to avoid exceptions
      if(!(collection.getName().equals(oldCollection.getName())))
        updateName(collection);

    if(collection.getPicture()!=null)
      if(!(collection.getPicture().equals(oldCollection.getPicture())))
        updatePicture(collection);

    if(collection.getTrackList()!=null)
      if(!(collection.getTrackList().equals(oldCollection.getTrackList())))
        updateTrackList(collection);
  }

  @Override
  public JBCollection get(JBCollection collection){

    JBCollection collectionOut=getPlaylist(collection);               //if collection is a playlist
    if(collectionOut==null)
      collectionOut=getAlbum(collection);       //if collection is an album
    if(collectionOut==null)
      collectionOut=getPodcast(collection);     //if collection is a podcast

    return collectionOut;
  }

  @Override
  public Playlist getPlaylist(JBCollection collection){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    Playlist result=null;

    try{
      String query="SELECT * FROM Collection A NATURAL JOIN Playlist B NATURAL JOIN"+
              "(SELECT idPlaylist AS 'id', profileMail FROM ProfilePlaylists) C WHERE id=?;";

      st=connection.prepareStatement(query);
      st.setInt(1, collection.getId());

      rs=st.executeQuery();

      if(rs.next()){
        result=new Playlist(rs.getInt("id"),
                rs.getString("name"),
                new User(null, rs.getString("profileMail"), null),
                new ArrayList<JBAudio>(),
                rs.getBlob("picture"),
                rs.getBoolean("isVisible"));
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    if(result!=null){
      ProfileDAO pDAO=new ProfileDAO();
      result.setCreator(pDAO.get(result.getCreator()));
    }

    return result;
  }

  @Override
  public Album getAlbum(JBCollection collection){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    Album result=null;

    try{
      String query="SELECT * FROM Collection A NATURAL JOIN Album B NATURAL JOIN"+
              "(SELECT idAlbum AS 'id', artistMail FROM ArtistAlbums) C WHERE id=?;";

      st=connection.prepareStatement(query);
      st.setInt(1, collection.getId());

      rs=st.executeQuery();

      if(rs.next()){
        result=new Album(rs.getInt("id"),
                rs.getString("name"),
                new Artist(null, rs.getString("artistMail"), null),
                new ArrayList<JBAudio>(),
                rs.getBlob("picture"));
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    if(result!=null){
      ProfileDAO pDAO=new ProfileDAO();
      result.setCreator(pDAO.get(result.getCreator()));
    }

    return result;
  }

  @Override
  public Podcast getPodcast(JBCollection collection){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    Podcast result=null;

    try{
      String query="SELECT * FROM Collection A NATURAL JOIN Podcast B NATURAL JOIN"+
              "(SELECT idPodcast AS 'id', artistMail FROM ArtistPodcasts) C WHERE id=?;";

      st=connection.prepareStatement(query);
      st.setInt(1, collection.getId());

      rs=st.executeQuery();

      if(rs.next()){
        result=new Podcast(rs.getInt("id"),
                rs.getString("name"),
                new Artist(null, rs.getString("artistMail"), null),
                new ArrayList<JBAudio>(),
                rs.getBlob("picture"));
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    if(result!=null){
      ProfileDAO pDAO=new ProfileDAO();
      result.setCreator(pDAO.get(result.getCreator()));
    }

    return result;
  }

  @Override
  public Playlist getFavorites(JBProfile activeProfile){
    AudioDAO aDAO=new AudioDAO();
    return new Playlist(0, "Favorites", activeProfile, aDAO.selectFavorites(activeProfile));
  }

  @Override
  public ArrayList<JBCollection> selectPlaylistsByProfile(JBProfile profile){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st1;
    ResultSet rs1;
    ArrayList<JBCollection> result=new ArrayList<>();

    //get all Playlists
    try{
      String q1="SELECT idPlaylist FROM ProfilePlaylists WHERE profileMail=? LIMIT 50;";

      st1=connection.prepareStatement(q1);
      st1.setString(1, profile.getMail());

      rs1=st1.executeQuery();

      ArrayList<Playlist> resultSetPlaylists=new ArrayList<>();
      while(rs1.next()){
        resultSetPlaylists.add(new Playlist(rs1.getInt("idPlaylist"), null, null));
      }//end-while

      for(Playlist p: resultSetPlaylists){
        result.add(getPlaylist(p));
      }//end-foreach

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    return result;
  }


  @Override
  public ArrayList<JBCollection> selectAlbumsByArtist(Artist artist){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st1;
    ResultSet rs1;
    ArrayList<JBCollection> result=new ArrayList<>();


    try{
      String q1="SELECT idAlbum FROM ArtistAlbums WHERE artistMail=? LIMIT 50;";

      st1=connection.prepareStatement(q1);
      st1.setString(1, artist.getMail());

      rs1=st1.executeQuery();

      ArrayList<Album> resultSetAlbums=new ArrayList<>();
      while(rs1.next()){
        resultSetAlbums.add(new Album(rs1.getInt("idAlbum"), null, null, null));
      }//end-while

      for(Album p: resultSetAlbums){
        result.add(getAlbum(p));
      }//end-foreach

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    return result;
  }


  @Override
  public ArrayList<JBCollection> selectPodcastsByArtist(Artist artist){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st1;
    ResultSet rs1;
    ArrayList<JBCollection> result=new ArrayList<>();


    try{
      String q1="SELECT idPodcast FROM ArtistPodcasts WHERE artistMail=? LIMIT 50;";

      st1=connection.prepareStatement(q1);
      st1.setString(1, artist.getMail());

      rs1=st1.executeQuery();

      ArrayList<Podcast> resultSetPodcasts=new ArrayList<>();
      while(rs1.next()){
        resultSetPodcasts.add(new Podcast(rs1.getInt("idAlbum"), null, null, null));
      }//end-while

      for(Podcast p: resultSetPodcasts){
        result.add(getAlbum(p));
      }//end-foreach

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    return result;
  }


  //PRIVATE METHODS:
  private void updateName(JBCollection collection){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

    try{
      String q3="UPDATE Collection SET name=? WHERE id=?;";

      PreparedStatement st3=connection.prepareStatement(q3);
      st3.setString(1, collection.getName());
      st3.setInt(2, collection.getId());

      st3.executeUpdate();

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  private void updatePicture(JBCollection collection){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

    try{
      String q6="UPDATE Collection SET picture=? WHERE id=?;";

      PreparedStatement st6=connection.prepareStatement(q6);
      st6.setBlob(1, collection.getPicture());
      st6.setInt(2, collection.getId());

      st6.executeUpdate();

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  private void updateTrackList(JBCollection collection){

    if(collection instanceof Playlist){
      ArrayList<JBAudio> audioList=((Playlist)collection).getTrackList();
      Iterator<JBAudio> audioListIT=audioList.iterator();
      connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
      PreparedStatement st1, st2;

      try{
        String q1="DELETE FROM PlaylistAudios WHERE idPlaylist=?;";
        st1=connection.prepareStatement(q1);
        st1.setInt(1, collection.getId());
        st1.executeUpdate();

        while(audioListIT.hasNext()){
          String q2="INSERT INTO PlaylistAudios(idPlaylist, idAudio) VALUES(?, ?);";
          st2=connection.prepareStatement(q2);
          st2.setInt(1, collection.getId());
          st2.setInt(2, audioListIT.next().getId());
          st2.executeUpdate();
        }

      }catch(Exception e){
        e.printStackTrace();
      }

      DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }else if(collection instanceof Podcast){
      ArrayList<JBAudio> episodeList=((Podcast)collection).getTrackList();
      Iterator<JBAudio> audioListIT=episodeList.iterator();
      connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
      PreparedStatement st1, st2;

      try{
        String q1="DELETE FROM PodcastEpisodes WHERE idPodcast=?;";
        st1=connection.prepareStatement(q1);
        st1.setInt(1, collection.getId());
        st1.executeUpdate();

        while(audioListIT.hasNext()){
          String q2="INSERT INTO PodcastEpisodes(idPodcast, idEpisodes) VALUES(?, ?);";
          st2=connection.prepareStatement(q2);
          st2.setInt(1, collection.getId());
          st2.setInt(2, audioListIT.next().getId());
          st2.executeUpdate();
        }

      }catch(Exception e){
        e.printStackTrace();
      }

      DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

  }

  private void insertCollection(JBCollection collection){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st1, st2, st3;
    String q1, q2, q3;
    ResultSet rs2;

    try{       //insert JBCollection to Collection table
      q1="INSERT INTO Collection(id, name, picture) VALUES(default, ?, ?);";

      st1=connection.prepareStatement(q1);
      st1.setString(1, collection.getName());
      st1.setBlob(2, collection.getPicture());
      st1.executeUpdate();

      q2="SELECT LAST_INSERT_ID();";
      st2=connection.prepareStatement(q2);
      rs2=st2.executeQuery();

      if(rs2.next())
        collection.setId(rs2.getInt("LAST_INSERT_ID()"));

      switch(collection){
        case Playlist playlist ->               //if collection is a Playlist insert it into Playlist table
                q3="INSERT INTO Playlist(id, isVisible) VALUES(?, 1);";
        case Album album ->                     //if collection is an Album insert it to Album table
                q3="INSERT INTO Album(id) VALUES(?);";
        case Podcast podcast ->                //if collection is a Podcast insert it to Podcast table
                q3="INSERT INTO Podcast(id) VALUES(?);";
        default -> q3=null;

      }

      st3=connection.prepareStatement(q3);
      st3.setInt(1, collection.getId());
      st3.executeUpdate();

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  private void linkCollectionToProfile(JBCollection collection){

    if(collection.getCreator()!=null){

      switch(collection){
        case Playlist playlist -> linkPlaylistToProfile(playlist);
        case Album album -> linkAlbumToArtist(album);
        case Podcast podcast -> linkPodcastToArtist(podcast);
        default -> {
        }
      }
    }

  }

  private void linkPlaylistToProfile(Playlist playlist){
    ProfileDAO pDAO=new ProfileDAO();

    if(pDAO.get(playlist.getCreator())==null)        //if artist not present in DB
      pDAO.insert(playlist.getCreator());             //insert new artist

    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;

    try{
      String query="INSERT INTO ProfilePlaylists(idPlaylist, profileMail) VALUES(?, ?);";
      st=connection.prepareStatement(query);
      st.setInt(1, playlist.getId());
      st.setString(2, playlist.getCreator().getMail());
      st.executeUpdate();

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  private void linkAlbumToArtist(Album album){
    ProfileDAO pDAO=new ProfileDAO();

    if(pDAO.get(album.getCreator())==null)        //if artist not present in DB
      pDAO.insert(album.getCreator());            //insert new artist

    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;

    try{
      String query="INSERT INTO ArtistAlbums(idAlbum, artistMail) VALUES(?, ?);";
      st=connection.prepareStatement(query);
      st.setInt(1, album.getId());
      st.setString(2, album.getCreator().getMail());
      st.executeUpdate();

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  private void linkPodcastToArtist(Podcast podcast){
    ProfileDAO pDAO=new ProfileDAO();

    if(pDAO.get(podcast.getCreator())==null)         //if artist not present in DB
      pDAO.insert(podcast.getCreator());              //insert new artist

    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;

    try{
      String query="INSERT INTO ArtistPodcasts(idPodcast, artistMail) VALUES(?, ?);";
      st=connection.prepareStatement(query);
      st.setInt(1, podcast.getId());
      st.setString(2, podcast.getCreator().getMail());
      st.executeUpdate();

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  private void linkCollectionToAudios(JBCollection collection){

    if(collection.getTrackList()!=null){

      switch(collection){
        case Playlist playlist -> linkPlaylistToAudios(playlist);
        case Album album -> linkAlbumToSongs(album);
        case Podcast podcast -> linkPodcastToEpisodes(podcast);
        default -> {
        }
      }

    }
  }

  private void linkPlaylistToAudios(Playlist playlist){
    AudioDAO aDAO=new AudioDAO();
    ArrayList<JBAudio> trackList=playlist.getTrackList();
    Iterator<JBAudio> trackListIT=trackList.iterator();

    while(trackListIT.hasNext()){
      JBAudio track=trackListIT.next();
      if(aDAO.get(track)==null)        //if audio not present in DB
        aDAO.insert(track);             //insert new audio
    }

    trackListIT=trackList.iterator();     //get a new iterator

    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;

    try{
      String query="INSERT INTO PlaylistAudios(idPlaylist, idAudio) VALUES(?, ?);";
      st=connection.prepareStatement(query);
      st.setInt(1, playlist.getId());

      while(trackListIT.hasNext()){
        JBAudio track=trackListIT.next();
        st.setInt(2, track.getId());
        st.executeUpdate();
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  private void linkAlbumToSongs(Album album){
    AudioDAO aDAO=new AudioDAO();
    ArrayList<JBAudio> trackList=album.getTrackList();
    Iterator<JBAudio> trackListIT=trackList.iterator();

    while(trackListIT.hasNext()){
      JBAudio track=trackListIT.next();
      if(aDAO.get(track)==null)        //if song not present in DB
        aDAO.insert(track);             //insert new song
    }

    trackListIT=trackList.iterator();     //get a new iterator

    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;

    try{
      String query="INSERT INTO AlbumSongs(idAlbum, idSong) VALUES(?, ?);";
      st=connection.prepareStatement(query);
      st.setInt(1, album.getId());

      while(trackListIT.hasNext()){
        JBAudio track=trackListIT.next();
        st.setInt(2, track.getId());
        st.executeUpdate();
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  private void linkPodcastToEpisodes(Podcast podcast){
    AudioDAO aDAO=new AudioDAO();
    ArrayList<JBAudio> trackList=podcast.getTrackList();
    Iterator<JBAudio> trackListIT=trackList.iterator();

    while(trackListIT.hasNext()){
      JBAudio track=trackListIT.next();
      if(aDAO.get(track)==null)        //if episode not present in DB
        aDAO.insert(track);             //insert new episode
    }

    trackListIT=trackList.iterator();     //get a new iterator

    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;

    try{
      String query="INSERT INTO PodcastEpisodes(idPodcast, idEpisode) VALUES(?, ?);";
      st=connection.prepareStatement(query);
      st.setInt(1, podcast.getId());

      while(trackListIT.hasNext()){
        JBAudio track=trackListIT.next();
        st.setInt(2, track.getId());
        st.executeUpdate();
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

}

