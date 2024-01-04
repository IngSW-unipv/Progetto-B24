package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.controller.factory.DBManagerFactory;
import it.unipv.ingsfw.JavaBeats.model.user.Artist;
import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;
import it.unipv.ingsfw.JavaBeats.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;

public class ProfileDAO implements IProfileDAO {

    //ATTRIBUTES:
    private String schema;
    private Connection connection;



    //CONTRUCTOR:
    public ProfileDAO() {
        super();
        this.schema = "JavaBeats_DB";
    }



    //METHODS:
    @Override
    public void insert(JBProfile profile) {         //add new profile to database
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;

        try {
            String query =  "INSERT INTO Profile(username, mail, password, name, surname, biography, profilePicture)" +
                            "VALUES(?, ?, ?, ?, ?, ?, ?);";

            st = connection.prepareStatement(query);
            st.setString(1, profile.getUsername());
            st.setString(2, profile.getMail());
            st.setString(3, profile.getPassword());
            st.setString(4, profile.getName());
            st.setString(5, profile.getSurname());
            st.setString(6, profile.getBiography());
            st.setBlob(7, profile.getProfilePicture());

            st.executeUpdate();

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
            String query = "DELETE FROM Profile WHERE mail=?;";      //query template

            st = connection.prepareStatement(query);                //configure query
            st.setString(1, profile.getMail());

            st.executeUpdate();                                     //execute query

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }


    @Override
    public void update(JBProfile profile) {         //push edited profile to database
        JBProfile oldProfile = get(profile);        //get profile as it is in DB to check for changes

        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

        try {

            if (profile.getUsername()!=oldProfile.getUsername() && profile.getUsername()!=null) {     //update username

                String q1 = "UPDATE Profile SET username=? WHERE mail=?;";

                PreparedStatement st1 = connection.prepareStatement(q1);
                st1.setString(1, profile.getUsername());
                st1.setString(2, profile.getMail());

                st1.executeUpdate();
            }

            if(profile.getPassword()!=oldProfile.getPassword() && profile.getPassword()!=null) {     //update password

                String q2 = "UPDATE Profile SET password=? WHERE mail=?;";

                PreparedStatement st2 = connection.prepareStatement(q2);
                st2.setString(1, profile.getPassword());
                st2.setString(2, profile.getMail());

                st2.executeUpdate();
            }

            if(profile.getName()!=oldProfile.getName() && profile.getName()!=null) {                //update name

                String q3 = "UPDATE Profile SET name=? WHERE mail=?;";

                PreparedStatement st3 = connection.prepareStatement(q3);
                st3.setString(1, profile.getName());
                st3.setString(2, profile.getMail());

                st3.executeUpdate();
            }

            if(profile.getSurname()!=oldProfile.getSurname() && profile.getSurname()!=null) {       //update surname

                String q4 = "UPDATE Profile SET surname=? WHERE mail=?;";

                PreparedStatement st4 = connection.prepareStatement(q4);
                st4.setString(1, profile.getSurname());
                st4.setString(2, profile.getMail());

                st4.executeUpdate();
            }

            if(profile.getBiography()!=oldProfile.getBiography() && profile.getBiography()!=null) { //update biography

                String q5 = "UPDATE Profile SET biography=? WHERE mail=?;";

                PreparedStatement st5 = connection.prepareStatement(q5);
                st5.setString(1, profile.getBiography());
                st5.setString(2, profile.getMail());

                st5.executeUpdate();
            }

            if(profile.getProfilePicture()!=oldProfile.getProfilePicture() && profile.getProfilePicture()!=null) {  //update profile picture

                String q6 = "UPDATE Profile SET profilePicture=? WHERE mail=?;";

                PreparedStatement st6 = connection.prepareStatement(q6);
                st6.setBlob(1, profile.getProfilePicture());
                st6.setString(2, profile.getMail());

                st6.executeUpdate();
            }

        } catch (Exception e){
             e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }


    @Override
    public JBProfile get(JBProfile profile) {            //retrieve profile from database
        return getProfileByMail(profile.getMail());
    }


    protected JBProfile getProfileByMail(String mail) {

        JBProfile profileOut = getUserByMail(mail);                 //check if profile is in User table
        if(profileOut==null) profileOut = getArtistByMail(mail);    //check if profile is in Artist table

        return profileOut;
    }


    protected Artist getArtistByMail(String mail) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Artist result = null;

        try {
            String query = "SELECT * FROM Profile NATURAL JOIN Artist WHERE mail=?;";   //query template

            st = connection.prepareStatement(query);                //configure query
            st.setString(1, mail);

            rs = st.executeQuery();                                 //execute query

            while(rs.next()) {                                      //while results are available
                result = new Artist(rs.getString("username"),           //only take the last one (shouldn't be a problem because mail is primary key)
                                    rs.getString("mail"),
                                    rs.getString("password"),
                                    rs.getString("name"),
                                    rs.getString("surname"),
                                    rs.getString("biography"),
                                    rs.getBlob("profilePicture"),
                                    rs.getInt("totalListeners"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }


    protected User getUserByMail(String mail) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        User result = null;

        try {
            String query = "SELECT * FROM Profile NATURAL JOIN User WHERE mail=?;";   //query template

            st = connection.prepareStatement(query);                //configure query
            st.setString(1, mail);

            rs = st.executeQuery();                                 //execute query

            while(rs.next()) {                                      //while results are available
                result = new User(rs.getString("username"),           //only take the last one (shouldn't be a problem because mail is primary key)
                                    rs.getString("mail"),
                                    rs.getString("password"),
                                    rs.getString("name"),
                                    rs.getString("surname"),
                                    rs.getString("biography"),
                                    rs.getBlob("profilePicture"),
                                    rs.getBoolean("isVisible"),
                                    getMinuteListenedByUserMail(rs.getString("mail")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }


    private Time getMinuteListenedByUserMail(String userMail) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Time result = null;

        try {
            String query = "SELECT (SUM(TIMEDIFF(minuteListened, '00:00:00'))) as 'minuteListened' FROM UserListenedAudios WHERE user_Mail=?;";   //query template

            st = connection.prepareStatement(query);                //configure query
            st.setString(1, userMail);

            rs = st.executeQuery();                                 //execute query

            while(rs.next()) {                                      //while results are available (only 1 result expected)
                result = rs.getTime("minuteListened");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;

    }

}
