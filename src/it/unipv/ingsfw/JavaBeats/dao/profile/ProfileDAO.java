package it.unipv.ingsfw.JavaBeats.dao.profile;

import it.unipv.ingsfw.JavaBeats.controller.factory.DBManagerFactory;
import it.unipv.ingsfw.JavaBeats.dao.playable.AudioDAO;
import it.unipv.ingsfw.JavaBeats.dao.collection.CollectionDAO;
import it.unipv.ingsfw.JavaBeats.exceptions.AccountNotFoundException;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.profile.*;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implementation of the methods declared in {@link IProfileDAO}.
 * It encapsulates database interactions, providing a clean and reusable interface for accessing {@link JBProfile} data.
 *
 * @see JBProfile
 * @see User
 * @see Artist
 */
public class ProfileDAO implements IProfileDAO{

  //ATTRIBUTES:
  private static final String schema="JavaBeats_DB";
  private Connection connection;


  //CONSTRUCTOR:
  public ProfileDAO(){
    super();
  }


  //PUBLIC METHODS:
  @Override
  public void insert(JBProfile profile){         //add new profile to database
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st1, st2, st3;

    try{       //insert JBProfile to Profile table
      String q1="INSERT INTO Profile(username, mail, password, name, surname, biography, profilePicture)"+
              "VALUES(?, ?, ?, ?, ?, ?, ?);";

      st1=connection.prepareStatement(q1);
      st1.setString(1, profile.getUsername());
      st1.setString(2, profile.getMail());
      st1.setString(3, profile.getPassword());
      st1.setString(4, profile.getName());
      st1.setString(5, profile.getSurname());
      st1.setString(6, profile.getBiography());
      st1.setBlob(7, profile.getProfilePicture());

      st1.executeUpdate();

      try{                           //if profile is a User insert it into User table
        User u=(User)profile;
        String q2="INSERT INTO User(mail, isVisible) VALUES(?, 1);";
        st2=connection.prepareStatement(q2);
        st2.setString(1, u.getMail());
        st2.executeUpdate();
      }catch(ClassCastException e){  //if profile is an Artist insert it to Artist table
        String q3="INSERT INTO Artist(mail) VALUES(?);";
        st3=connection.prepareStatement(q3);
        st3.setString(1, profile.getMail());
        st3.executeUpdate();
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  @Override
  public void remove(JBProfile profile){         //remove profile from database
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;

    try{
      String query="DELETE FROM Profile WHERE mail=?;";

      st=connection.prepareStatement(query);
      st.setString(1, profile.getMail());

      st.executeUpdate();

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  @Override
  public void update(JBProfile profile) throws AccountNotFoundException{
    JBProfile oldProfile=get(profile);        //get profile as it is in DB to check for changes

    try{
      if(profile.getUsername()!=null)          //check for null before using .equals to avoid exceptions
        if(!(profile.getUsername().equals(oldProfile.getUsername())))
          updateUsername(profile);

      if(profile.getPassword()!=null)
        if(!(profile.getPassword().equals(oldProfile.getPassword())))
          updatePassword(profile);

      if(profile.getName()!=null)
        if(!(profile.getName().equals(oldProfile.getName())))
          updateName(profile);

      if(profile.getSurname()!=null)
        if(!(profile.getSurname().equals(oldProfile.getSurname())))
          updateSurname(profile);

      if(profile.getBiography()!=null)
        if(!(profile.getBiography().equals(oldProfile.getBiography())))
          updateBiography(profile);

      if(profile.getProfilePicture()!=null)
        if(!Arrays.equals(profile.getProfilePicture().getBinaryStream().readAllBytes(), oldProfile.getProfilePicture().getBinaryStream().readAllBytes()))
          updateProfilePicture(profile);

    }catch(SQLException | IOException s){
      throw new RuntimeException(s);
    }
    try{                           //if profile is a User
      User userProfile=(User)profile;
      User oldUserProfile=(User)oldProfile;
      if(userProfile.isVisible()!=oldUserProfile.isVisible())
        updateVisibility(userProfile);
    }catch(ClassCastException e){  //if profile is an Artist
      //do nothing
    }

  }

  @Override
  public JBProfile get(JBProfile profile) throws AccountNotFoundException{            //retrieve profile from database

    JBProfile profileOut=getUser(profile);                 //check if profile is in User table

    if(profileOut==null){
      profileOut=getArtist(profile);    //check if profile is in Artist table

      if(profileOut==null){
        throw new AccountNotFoundException();
      }//end-if

      /* Profile is an artist */
      Artist a=(Artist)profileOut;
      a.setTotalListeners(getTotalListeners(a));        //set total listeners
      a.setListeningHistory(getListeningHistory(a));    //set listening history
      CollectionDAO cDAO=new CollectionDAO();
      a.setFavorites(cDAO.getFavorites(a));             //set favorites playlist
    }else{
      /* profile is a user */
      User u=(User)profileOut;
      u.setMinuteListened(getTotalListeningTime(u));        //set minute listened
      u.setListeningHistory(getListeningHistory(u));        //set listening history
      CollectionDAO cDAO=new CollectionDAO();
      u.setFavorites(cDAO.getFavorites(u));                 //set favorites playlist
      u.setMinuteListened(getTotalListeningTime(u));
    }//end.if
    return profileOut;
  }

  @Override
  public Artist getArtist(JBProfile profile){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    Artist result=null;

    try{
      String query="SELECT * FROM Profile INNER JOIN Artist ON Profile.mail = Artist.mail WHERE Artist.mail=? OR username=?;";

      st=connection.prepareStatement(query);
      st.setString(1, profile.getMail());
      st.setString(2, profile.getUsername());

      rs=st.executeQuery();

      if(rs.next()){
        result=new Artist(rs.getString("username"),
                rs.getString("mail"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("biography"),
                rs.getBlob("profilePicture"),
                0,
                null, null);
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    return result;
  }

  @Override
  public User getUser(JBProfile profile){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    User result=null;

    try{
      String query="SELECT * FROM Profile INNER JOIN User ON Profile.mail = User.mail WHERE User.mail=? OR username=?;";

      st=connection.prepareStatement(query);
      st.setString(1, profile.getMail());
      st.setString(2, profile.getUsername());

      rs=st.executeQuery();

      if(rs.next()){
        result=new User(rs.getString("username"),
                rs.getString("mail"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("biography"),
                rs.getBlob("profilePicture"),
                rs.getBoolean("isVisible"),
                0,
                null, null);
      }

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    return result;
  }

  public void refreshProfileInfo(JBProfile jbProfile) throws AccountNotFoundException{
    CollectionDAO cDAO=new CollectionDAO();

    try{
      User u=(User)jbProfile;
      u.setMinuteListened(getTotalListeningTime(u));        //set minute listened
      u.setListeningHistory(getListeningHistory(u));        //set listening history
      u.setFavorites(cDAO.getFavorites(u));                 //set favorites playlist
      u.setMinuteListened(getTotalListeningTime(u));
    }catch(ClassCastException c){
      /* Profile is an artist */
      Artist a=(Artist)jbProfile;
      a.setTotalListeners(getTotalListeners(a));        //set total listeners
      a.setListeningHistory(getListeningHistory(a));    //set listening history

      a.setFavorites(cDAO.getFavorites(a));             //set favorites playlist
    }//end-try
    catch(AccountNotFoundException e){
      throw new AccountNotFoundException();
    }
  }

  //PRIVATE METHODS:
  private double getTotalListeningTime(User user){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    double result=00.00;

    try{
      String query="SELECT (SUM(TIMEDIFF(duration, '00:00:00'))) AS 'total' FROM "+
              "Audio JOIN ListeningHistory ON Audio.ID=ListeningHistory.idAudio "+
              "WHERE profileMail=?;";

      st=connection.prepareStatement(query);
      st.setString(1, user.getMail());

      rs=st.executeQuery();

      rs.next();
      result=rs.getDouble("total");

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    return result;
  }

  private int getTotalListeners(Artist artist){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    int result=0;

    try{
      String query="SELECT count(idAudio) as 'total' FROM ListeningHistory NATURAL JOIN ArtistAudios WHERE artistMail=?;";

      st=connection.prepareStatement(query);
      st.setString(1, artist.getMail());

      rs=st.executeQuery();

      if(rs.next())
        result=rs.getInt("total");

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

    return result;
  }

  private ArrayList<JBAudio> getListeningHistory(JBProfile profile) throws AccountNotFoundException{
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
    PreparedStatement st;
    ResultSet rs;
    ArrayList<Integer> audioIDs=new ArrayList<>();
    ArrayList<JBAudio> result=new ArrayList<>();

    try{
      String query="SELECT DISTINCT LHiA.idAudio "+
              "FROM (SELECT idAudio, listeningDate"+
              "      FROM ListeningHistory"+
              "      WHERE profileMail LIKE ?"+
              "      ORDER BY listeningDate DESC"+
              "      LIMIT 25) as LHiA;";

      st=connection.prepareStatement(query);
      st.setString(1, profile.getMail());

      rs=st.executeQuery();

      while(rs.next())
        audioIDs.add(rs.getInt("idAudio"));

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    AudioDAO aDAO=new AudioDAO();

    for(Integer track: audioIDs){
      result.add(aDAO.get(new Song(track, null, null, null), profile));      //aDAO.get() will return either Song or Episode (only cares about the id of the JBAudio input, not the type)
    }

    return result;
  }

  private void updateUsername(JBProfile profile){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

    try{
      String q1="UPDATE Profile SET username=? WHERE mail=?;";

      PreparedStatement st1=connection.prepareStatement(q1);
      st1.setString(1, profile.getUsername());
      st1.setString(2, profile.getMail());

      st1.executeUpdate();

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  private void updatePassword(JBProfile profile){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

    try{
      String q2="UPDATE Profile SET password=? WHERE mail=?;";

      PreparedStatement st2=connection.prepareStatement(q2);
      st2.setString(1, profile.getPassword());
      st2.setString(2, profile.getMail());

      st2.executeUpdate();

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  private void updateName(JBProfile profile){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

    try{
      String q3="UPDATE Profile SET name=? WHERE mail=?;";

      PreparedStatement st3=connection.prepareStatement(q3);
      st3.setString(1, profile.getName());
      st3.setString(2, profile.getMail());

      st3.executeUpdate();

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  private void updateSurname(JBProfile profile){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

    try{
      String q4="UPDATE Profile SET surname=? WHERE mail=?;";

      PreparedStatement st4=connection.prepareStatement(q4);
      st4.setString(1, profile.getSurname());
      st4.setString(2, profile.getMail());

      st4.executeUpdate();

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  private void updateBiography(JBProfile profile){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

    try{
      String q5="UPDATE Profile SET biography=? WHERE mail=?;";

      PreparedStatement st5=connection.prepareStatement(q5);
      st5.setString(1, profile.getBiography());
      st5.setString(2, profile.getMail());

      st5.executeUpdate();

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  private void updateProfilePicture(JBProfile profile){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

    try{
      String q6="UPDATE Profile SET profilePicture=? WHERE mail=?;";

      PreparedStatement st6=connection.prepareStatement(q6);
      st6.setBlob(1, profile.getProfilePicture());
      st6.setString(2, profile.getMail());

      st6.executeUpdate();

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

  private void updateVisibility(User user){
    connection=DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

    try{
      String q7="UPDATE User SET isVisible=? WHERE mail=?;";

      PreparedStatement st7=connection.prepareStatement(q7);
      st7.setBoolean(1, user.isVisible());
      st7.setString(2, user.getMail());

      st7.executeUpdate();

    }catch(Exception e){
      e.printStackTrace();
    }

    DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
  }

}
