package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.controller.factory.DBManagerFactory;
import it.unipv.ingsfw.JavaBeats.model.playable.Album;
import it.unipv.ingsfw.JavaBeats.model.playable.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.playable.Playlist;
import it.unipv.ingsfw.JavaBeats.model.playable.Podcast;
import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CollectionDAO implements ICollectionDAO {

    //ATTRIBUTES:
    private String schema;
    private Connection connection;



    //CONTRUCTOR:
    public CollectionDAO() {
        super();
        this.schema = "JavaBeats_DB";
    }



    //PUBLIC METHODS:
    @Override
    public void insert(JBCollection collection) {

    }

    @Override
    public void remove(JBCollection collection) {

    }

    @Override
    public void update(JBCollection collection) {

    }

    @Override
    public JBCollection get(JBCollection collection) {
        return getCollectionByID(collection.getId());
    }

    @Override
    public ArrayList<JBCollection> selectByProfile(JBProfile profile) {
        return null;
    }



    //PROTECTED METHODS:
    protected JBCollection getCollectionByID(String id) {
        JBCollection collectionOut = getPlaylistByID(id);               //if collection is a playlist
        if(collectionOut==null) collectionOut = getAlbumByID(id);       //if collection is an album
        if(collectionOut==null) collectionOut = getPodcastByID(id);     //if collection is a podcast

        return collectionOut;
    }

    protected Playlist getPlaylistByID(String id) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Playlist result = null;
        ProfileDAO pDAO = new ProfileDAO();

        try {
            String query =  "SELECT * FROM Collection A NATURAL JOIN Playlist B NATURAL JOIN" +
                            "(SELECT idPlaylist AS 'id', profileMail FROM ProfilePlaylists) C WHERE id=?;";

            st = connection.prepareStatement(query);
            st.setString(1, id);

            rs = st.executeQuery();

            while(rs.next()) {                                      //while results are available
                result = new Playlist(  rs.getString("id"),           //only take the last one (shouldn't be a problem because mail is primary key)
                                        rs.getString("name"),
                                        pDAO.getProfileByMail(rs.getString("profileMail")),
                                        rs.getBlob("picture"),
                                        rs.getBoolean("isVisible"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }

    protected Album getAlbumByID(String id) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Album result = null;
        ProfileDAO pDAO = new ProfileDAO();

        try {
            String query =  "SELECT * FROM Collection A NATURAL JOIN Album B NATURAL JOIN" +
                            "(SELECT idAlbum AS 'id', artistMail FROM ArtistAlbums) C WHERE id=?;";

            st = connection.prepareStatement(query);
            st.setString(1, id);

            rs = st.executeQuery();

            while(rs.next()) {                                      //while results are available
                result = new Album (    rs.getString("id"),           //only take the last one (shouldn't be a problem because mail is primary key)
                                        rs.getString("name"),
                                        pDAO.getArtistByMail(rs.getString("artistMail")),
                                        rs.getBlob("picture"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }

    protected Podcast getPodcastByID(String id) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Podcast result = null;
        ProfileDAO pDAO = new ProfileDAO();

        try {
            String query =  "SELECT * FROM Collection A NATURAL JOIN Podcast B NATURAL JOIN" +
                            "(SELECT idPodcast AS 'id', artistMail FROM ArtistPodcasts) C WHERE id=?;";

            st = connection.prepareStatement(query);
            st.setString(1, id);

            rs = st.executeQuery();

            while(rs.next()) {                                      //while results are available
                result = new Podcast (  rs.getString("id"),           //only take the last one (shouldn't be a problem because mail is primary key)
                                        rs.getString("name"),
                                        pDAO.getProfileByMail(rs.getString("artistMail")),
                                        rs.getBlob("picture"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }
}
