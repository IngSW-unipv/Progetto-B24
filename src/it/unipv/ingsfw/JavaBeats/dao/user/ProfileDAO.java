package it.unipv.ingsfw.JavaBeats.dao.user;

import it.unipv.ingsfw.JavaBeats.controller.factory.DBManagerFactory;
import it.unipv.ingsfw.JavaBeats.dao.playable.AudioDAO;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Song;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.model.profile.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;

public class ProfileDAO implements IProfileDAO {

    //ATTRIBUTES:
    private String schema;
    private Connection connection;



    //CONTRUCTOR:
    public ProfileDAO() {
        super();
        this.schema = "JavaBeats_DB";
    }



    //PUBLIC METHODS:
    @Override
    public void insert(JBProfile profile) {         //add new profile to database
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st1, st2, st3;

        try {       //insert JBProfile to Profile table
            String q1 = "INSERT INTO Profile(username, mail, password, name, surname, biography, profilePicture)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?);";

            st1 = connection.prepareStatement(q1);
            st1.setString(1, profile.getUsername());
            st1.setString(2, profile.getMail());
            st1.setString(3, profile.getPassword());
            st1.setString(4, profile.getName());
            st1.setString(5, profile.getSurname());
            st1.setString(6, profile.getBiography());
            st1.setBlob(7, profile.getProfilePicture());

            st1.executeUpdate();

            if(profile instanceof User) {               //if JBProfile is a User insert it into User table
                String q2=  "INSERT INTO User(mail, isVisible) VALUES(?, 1);";
                st2 = connection.prepareStatement(q2);
                st2.setString(1, profile.getMail());
                st2.executeUpdate();

            }
            else if (profile instanceof Artist) {     //if JBProfile is an Artist insert it to Artist table
                String q3=  "INSERT INTO Artist(mail) VALUES(?);";
                st3= connection.prepareStatement(q3);
                st3.setString(1, profile.getMail());
                st3.executeUpdate();
            }
            else {
                throw new Exception("Unable to recognize if JBProfile IS-A User or Artist.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    @Override
    public void remove(JBProfile profile) {         //remove profile from database
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;

        try {
            String query = "DELETE FROM Profile WHERE mail=?;";

            st = connection.prepareStatement(query);
            st.setString(1, profile.getMail());

            st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    @Override
    public void update(JBProfile profile) {
        JBProfile oldProfile = get(profile);        //get profile as it is in DB to check for changes

        if (profile.getUsername() != null)          //check for null before using .equals to avoid exceptions
            if (!(profile.getUsername().equals(oldProfile.getUsername())))
                updateUsername(profile);

        if (profile.getPassword() != null)
            if (!(profile.getPassword().equals(oldProfile.getPassword())))
                updatePassword(profile);

        if (profile.getName() != null)
            if (!(profile.getName().equals(oldProfile.getName())))
                updateName(profile);

        if (profile.getSurname() != null)
            if (!(profile.getSurname().equals(oldProfile.getSurname())))
                updateSurname(profile);

        if (profile.getBiography() != null)
            if (!(profile.getBiography().equals(oldProfile.getBiography())))
                updateBiography(profile);

        if (profile.getProfilePicture() != null)
            if (!(profile.getProfilePicture().equals(oldProfile.getProfilePicture())))
                updateProfilePicture(profile);

        if ((profile instanceof User)) {                                    //if profile is a USER
            User userProfile = (User) profile;
            User oldUserProfile = (User) oldProfile;

            if (userProfile.isVisible() != oldUserProfile.isVisible())
                updateVisibility(userProfile);

        }
        else if (profile instanceof Artist) {                               //if profile is an ARTIST
            //do nothing
        }
        else {
            try {
                throw new Exception("Unable to recognize if JBProfile IS-A User or Artist.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public JBProfile get(JBProfile profile) {            //retrieve profile from database

        JBProfile profileOut = getUser(profile);                 //check if profile is in User table
        if(profileOut==null) profileOut = getArtist(profile);    //check if profile is in Artist table

        return profileOut;
    }



    //PRIVATE METHODS:
    private Artist getArtist(JBProfile profile) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Artist result = null;

        try {
            String query = "SELECT * FROM Profile NATURAL JOIN Artist WHERE mail=?;";

            st = connection.prepareStatement(query);
            st.setString(1, profile.getMail());

            rs = st.executeQuery();

            if(rs.next()) {
                result = new Artist(rs.getString("username"),
                        rs.getString("mail"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("biography"),
                        rs.getBlob("profilePicture"),
                        0,
                        null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        if(result!=null) {
            result.setTotalListeners(getTotalListeners(result));        //set total listeners
            result.setListeningHistory(getListeningHistory(result));    //set listening history
        }

        return result;
    }

    private User getUser(JBProfile profile) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        User result = null;

        try {
            String query = "SELECT * FROM Profile NATURAL JOIN User WHERE mail=?;";

            st = connection.prepareStatement(query);
            st.setString(1, profile.getMail());

            rs = st.executeQuery();

            if(rs.next()) {
                result = new User(rs.getString("username"),
                        rs.getString("mail"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("biography"),
                        rs.getBlob("profilePicture"),
                        rs.getBoolean("isVisible"),
                        null,
                        null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        if(result != null) {
            result.setMinuteListened(getTotalListeningTime(result));        //set minute listened
            result.setListeningHistory(getListeningHistory(result));    //set listening history
        }

        return result;
    }

    private Time getTotalListeningTime(User user) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Time result = null;

        try {
            String query =  "SELECT (SUM(TIMEDIFF(duration, '00:00:00'))) as 'total' FROM ListeningHistory A " +
                            "NATURAL JOIN (SELECT id as 'idAudio', duration FROM Audio) B WHERE profileMail=?;";

            st = connection.prepareStatement(query);
            st.setString(1, user.getMail());

            rs = st.executeQuery();

            rs.next();
            result = rs.getTime("total");

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }

    private int getTotalListeners(Artist artist) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        int result = 0;

        try {
            String query = "SELECT count(idAudio) as 'total' FROM ListeningHistory NATURAL JOIN ArtistAudios WHERE artistMail=?;";

            st = connection.prepareStatement(query);
            st.setString(1, artist.getMail());

            rs = st.executeQuery();

            if(rs.next())
                result = rs.getInt("total");

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }

    private ArrayList<JBAudio> getListeningHistory (JBProfile profile) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        ArrayList<String> audioIDs = null;
        ArrayList<JBAudio> result = new ArrayList<>();

        try {
            String query = "SELECT idAudio FROM ListeningHistory WHERE profileMail=? ORDER BY listeningDate DESC LIMIT 25;";

            st = connection.prepareStatement(query);
            st.setString(1, profile.getMail());

            rs = st.executeQuery();

            while(rs.next())
                audioIDs.add(rs.getString("idAudio"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        if(audioIDs != null) {
            AudioDAO aDAO = new AudioDAO();
            Iterator<String> audioIDsIT = audioIDs.iterator();
            while (audioIDsIT.hasNext()) {
                String track = audioIDsIT.next();
                if (aDAO.get(new Song(track, null, null, null)) != null) {                  //if audio is in Song table
                    result.add(aDAO.get(new Song(track, null, null, null)));
                } else if ((aDAO.get(new Episode(track, null, null, null)) != null)) {      //if audio is in Episode table
                    result.add(aDAO.get(new Episode(track, null, null, null)));
                }
            }
        }

        return result;
    }

    private void updateUsername(JBProfile profile) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

        try {
            String q1 = "UPDATE Profile SET username=? WHERE mail=?;";

            PreparedStatement st1 = connection.prepareStatement(q1);
            st1.setString(1, profile.getUsername());
            st1.setString(2, profile.getMail());

            st1.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    private void updatePassword(JBProfile profile) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

        try {
            String q2 = "UPDATE Profile SET password=? WHERE mail=?;";

            PreparedStatement st2 = connection.prepareStatement(q2);
            st2.setString(1, profile.getPassword());
            st2.setString(2, profile.getMail());

            st2.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    private void updateName(JBProfile profile) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

        try {
            String q3 = "UPDATE Profile SET name=? WHERE mail=?;";

            PreparedStatement st3 = connection.prepareStatement(q3);
            st3.setString(1, profile.getName());
            st3.setString(2, profile.getMail());

            st3.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    private void updateSurname(JBProfile profile) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

        try {
            String q4 = "UPDATE Profile SET surname=? WHERE mail=?;";

            PreparedStatement st4 = connection.prepareStatement(q4);
            st4.setString(1, profile.getSurname());
            st4.setString(2, profile.getMail());

            st4.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    private void updateBiography(JBProfile profile) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

        try {
            String q5 = "UPDATE Profile SET biography=? WHERE mail=?;";

            PreparedStatement st5 = connection.prepareStatement(q5);
            st5.setString(1, profile.getBiography());
            st5.setString(2, profile.getMail());

            st5.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    private void updateProfilePicture(JBProfile profile) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

        try {
            String q6 = "UPDATE Profile SET profilePicture=? WHERE mail=?;";

            PreparedStatement st6 = connection.prepareStatement(q6);
            st6.setBlob(1, profile.getProfilePicture());
            st6.setString(2, profile.getMail());

            st6.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    private void updateVisibility(User user) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

        try {
            String q7 = "UPDATE User SET isVisible=? WHERE mail=?;";

            PreparedStatement st7 = connection.prepareStatement(q7);
            st7.setBoolean(1, user.isVisible());
            st7.setString(2, user.getMail());

            st7.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

}
