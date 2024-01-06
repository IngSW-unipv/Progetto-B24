package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.controller.factory.DBManagerFactory;
import it.unipv.ingsfw.JavaBeats.model.playable.*;
import it.unipv.ingsfw.JavaBeats.model.user.Artist;
import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

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

        insertCollection(collection);

        linkCollectionToProfile(collection);

    }

    @Override
    public void remove(JBCollection collection) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;

        try {
            String query = "DELETE FROM Collection WHERE id=?;";

            st = connection.prepareStatement(query);
            st.setString(1, collection.getId());

            st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    @Override
    public void update(JBCollection collection) {
        JBCollection oldCollection = get(collection);

        if (collection.getName() != null)               //check for null before using .equals to avoid exceptions
            if(!(collection.getName().equals(oldCollection.getName())))
                updateName(collection);

        if (collection.getPicture() != null)
            if(!(collection.getPicture().equals(oldCollection.getPicture())))
                updatePicture(collection);

        updateTrackList(collection);
    }

    @Override
    public JBCollection get(JBCollection collection) {
        return getCollectionByID(collection.getId());
    }

    @Override
    public ArrayList<JBCollection> selectByProfile(JBProfile profile) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st1;
        ResultSet rs1;
        ArrayList<JBCollection> result = null;

        //get all Playlists
        try {
            String q1 = "SELECT idPlaylist FROM ProfilePlaylists WHERE profileMail=?;";

            st1 = connection.prepareStatement(q1);
            st1.setString(1, profile.getMail());

            rs1 = st1.executeQuery();

            while (rs1.next()) {
                result.add(getPlaylistByID(rs1.getString("idPlaylist")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        //if profile is an artist get also Podcasts ad Albums
        if (profile instanceof Artist) {
            connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
            PreparedStatement st2, st3;
            ResultSet rs2, rs3;

            try {
                String q2 = "SELECT idAlbum FROM ArtistAlbums WHERE artistMail=?;";

                st2 = connection.prepareStatement(q2);
                st2.setString(1, profile.getMail());

                rs2 = st2.executeQuery();

                while(rs2.next()) {
                    result.add(getAlbumByID(rs2.getString("idAlbum")));
                }


                String q3 = "SELECT idAlbum FROM ArtistPodcasts WHERE artistMail=?;";

                st3 = connection.prepareStatement(q3);
                st3.setString(1, profile.getMail());

                rs3 = st3.executeQuery();

                while(rs3.next()) {
                    result.add(getPodcastByID(rs3.getString("idPodcast")));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
        }

        return result;
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
        AudioDAO aDAO = new AudioDAO();

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
                                        aDAO.selectByPlalist(new Playlist(id, null, null)),
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
        AudioDAO aDAO = new AudioDAO();

        try {
            String query =  "SELECT * FROM Collection A NATURAL JOIN Album B NATURAL JOIN" +
                            "(SELECT idAlbum AS 'id', artistMail FROM ArtistAlbums) C WHERE id=?;";

            st = connection.prepareStatement(query);
            st.setString(1, id);

            rs = st.executeQuery();

            while(rs.next()) {                                      //while results are available
                result = new Album (    rs.getString("id"),           //only take the last one (shouldn't be a problem because mail is primary key)
                                        rs.getString("name"),
                                        pDAO.get(new Artist(null, rs.getString("artistMail"), null)),
                                        aDAO.selectByAlbum(new Album(id, null, null, null)),
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
        AudioDAO aDAO = new AudioDAO();

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
                                        aDAO.selectByPodcast(new Podcast(id, null, null, null)),
                                        rs.getBlob("picture"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }



    //PRIVATE METHODS:
    private void updateName(JBCollection collection) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

        try {
            String q3 = "UPDATE Collection SET name=? WHERE id=?;";

            PreparedStatement st3 = connection.prepareStatement(q3);
            st3.setString(1, collection.getName());
            st3.setString(2, collection.getId());

            st3.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    private void updatePicture(JBCollection collection) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);

        try {
            String q6 = "UPDATE Collection SET picture=? WHERE id=?;";

            PreparedStatement st6 = connection.prepareStatement(q6);
            st6.setBlob(1, collection.getPicture());
            st6.setString(2, collection.getId());

            st6.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    private void updateTrackList(JBCollection collection) {

        if(collection instanceof Playlist) {
            ArrayList<JBAudio> audioList = ((Playlist) collection).getTrackList();
            Iterator<JBAudio> audioListIT = audioList.iterator();
            connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
            PreparedStatement st1, st2;

            try {
                String q1 = "DELETE FROM PlaylistAudios WHERE idPlaylist=?;";
                st1 = connection.prepareStatement(q1);
                st1.setString(1, collection.getId());
                st1.executeUpdate();

                while(audioListIT.hasNext()) {
                    String q2 = "INSERT INTO PlaylistAudios(idPlaylist, idAudio) VALUES(?, ?);";
                    st2 = connection.prepareStatement(q2);
                    st2.setString(1, collection.getId());
                    st2.setString(2, audioListIT.next().getId());
                    st2.executeUpdate();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
        }
        else if (collection instanceof Album) {
            //THROW EXCEPTION cant modify trackList of an album
        }
        else if (collection instanceof Podcast) {
            ArrayList<Episode> episodeList = ((Podcast) collection).getTrackList();
            Iterator<Episode> audioListIT = episodeList.iterator();
            connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
            PreparedStatement st1, st2;

            try {
                String q1 = "DELETE FROM PodcastEpisodes WHERE idPodcast=?;";
                st1 = connection.prepareStatement(q1);
                st1.setString(1, collection.getId());
                st1.executeUpdate();

                while(audioListIT.hasNext()) {
                    String q2 = "INSERT INTO PodcastEpisodes(idPodcast, idEpisodes) VALUES(?, ?);";
                    st2 = connection.prepareStatement(q2);
                    st2.setString(1, collection.getId());
                    st2.setString(2, audioListIT.next().getId());
                    st2.executeUpdate();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        } else {
            //THROW EXCEPTION
        }

    }

    private void insertCollection(JBCollection collection) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st1, st2, st3, st4;

        try {       //insert JBCollection to Collection table
            String q1 =  "INSERT INTO Collection(id, name, picture) VALUES(?, ?, ?);";

            st1 = connection.prepareStatement(q1);
            st1.setString(1, collection.getId());
            st1.setString(2, collection.getName());
            st1.setBlob(3, collection.getPicture());

            st1.executeUpdate();

            if (collection instanceof Playlist) {                //if collection is a Playlist insert it into Playlist table
                String q2 = "INSERT INTO Playlist(id, isVisible) VALUES(?, 1);";
                st2 = connection.prepareStatement(q2);
                st2.setString(1, collection.getId());
                st2.executeUpdate();

            }
            else if (collection instanceof Album) {             //if collection is an Album insert it to Album table
                String q3 = "INSERT INTO Album(id) VALUES(?);";
                st3= connection.prepareStatement(q3);
                st3.setString(1, collection.getId());
                st3.executeUpdate();
            }
            else if (collection instanceof Podcast) {           //if collection is a Podcast insert it to Podcast table
                String q4 = "INSERT INTO Podcast(id) VALUES(?);";
                st4= connection.prepareStatement(q4);
                st4.setString(1, collection.getId());
                st4.executeUpdate();
            }
            else {
                //THROW EXCEPTION
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    private void linkCollectionToProfile(JBCollection collection) {

        if (collection instanceof Playlist)
            linkPlaylistToProfile((Playlist)collection);

        else if (collection instanceof Album)
            linkAlbumToArtist((Album)collection);

        else if (collection instanceof Podcast)
            linkPodcastToArtist((Podcast)collection);

        else {
            //THROW EXCEPTION
        }

    }

    private void linkPlaylistToProfile(Playlist playlist) {
        ProfileDAO pDAO = new ProfileDAO();

        if (pDAO.getProfileByMail(playlist.getCreator().getMail()) == null)         //if artist not present in DB
            pDAO.insert(playlist.getCreator());                                   //insert new artist

        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;

        try {
            String query = "INSERT INTO ProfilePlaylists(idPlaylist, profileMail) VALUES(?, ?);";
            st = connection.prepareStatement(query);
            st.setString(1, playlist.getId());
            st.setString(2, playlist.getCreator().getMail());
            st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    private void linkAlbumToArtist(Album album) {
        ProfileDAO pDAO = new ProfileDAO();

        if(pDAO.getArtistByMail(album.getCreator().getMail()) == null)         //if artist not present in DB
            pDAO.insert(album.getCreator());                                   //insert new artist

        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;

        try {
            String query =  "INSERT INTO ArtistAlbums(idAlbum, artistMail) VALUES(?, ?);";
            st = connection.prepareStatement(query);
            st.setString(1, album.getId());
            st.setString(2, album.getCreator().getMail());
            st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    private void linkPodcastToArtist(Podcast podcast) {
        ProfileDAO pDAO = new ProfileDAO();

        if (pDAO.getArtistByMail(podcast.getCreator().getMail()) == null)         //if artist not present in DB
            pDAO.insert(podcast.getCreator());                                   //insert new artist

        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;

        try {
            String query = "INSERT INTO ArtistPodcast(idPodcast, artistMail) VALUES(?, ?);";
            st = connection.prepareStatement(query);
            st.setString(1, podcast.getId());
            st.setString(2, podcast.getCreator().getMail());
            st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    private void linkCollectionToAudios(JBCollection collection) {

    }

    private void linkPlaylistToAudios(JBCollection collection) {

    }

    private void linkAlbumToSongs(JBCollection collection) {

    }

    private void linkPodcastToEpisodes(JBCollection collection) {

    }

}

