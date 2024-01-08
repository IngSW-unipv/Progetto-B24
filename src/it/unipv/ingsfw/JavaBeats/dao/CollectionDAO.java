package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.controller.factory.DBManagerFactory;
import it.unipv.ingsfw.JavaBeats.model.playable.*;
import it.unipv.ingsfw.JavaBeats.model.user.Artist;
import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;
import it.unipv.ingsfw.JavaBeats.model.user.User;

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

        linkCollectionToAudios(collection);
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

        if (collection.getTrackList() != null)
            if(!(collection.getTrackList().equals(oldCollection.getTrackList())))
                updateTrackList(collection);
    }

    @Override
    public JBCollection get(JBCollection collection) {

        JBCollection collectionOut = getPlaylist(collection);               //if collection is a playlist
        if(collectionOut==null) collectionOut = getAlbum(collection);       //if collection is an album
        if(collectionOut==null) collectionOut = getPodcast(collection);     //if collection is a podcast

        return collectionOut;
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
                result.add(get(new Playlist(rs1.getString("idPlaylist"), null, null)));
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
                    result.add(get(new Album(rs2.getString("idAlbum"), null, null, null)));
                }


                String q3 = "SELECT idPodcast FROM ArtistPodcasts WHERE artistMail=?;";

                st3 = connection.prepareStatement(q3);
                st3.setString(1, profile.getMail());

                rs3 = st3.executeQuery();

                while(rs3.next()) {
                    result.add(get(new Podcast(rs3.getString("idPodcast"), null, null, null, null)));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
        }

        return result;
    }



    //PRIVATE METHODS:
    private Playlist getPlaylist(JBCollection collection) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Playlist result = null;

        try {
            String query =  "SELECT * FROM Collection A NATURAL JOIN Playlist B NATURAL JOIN" +
                    "(SELECT idPlaylist AS 'id', profileMail FROM ProfilePlaylists) C WHERE id=?;";

            st = connection.prepareStatement(query);
            st.setString(1, collection.getId());

            rs = st.executeQuery();

            if(rs.next()) {
                result = new Playlist(  rs.getString("id"),
                        rs.getString("name"),
                        new User(null, rs.getString("profileMail"), null),
                        null,
                        rs.getBlob("picture"),
                        rs.getBoolean("isVisible"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }

    private Album getAlbum(JBCollection collection) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Album result = null;

        try {
            String query =  "SELECT * FROM Collection A NATURAL JOIN Album B NATURAL JOIN" +
                    "(SELECT idAlbum AS 'id', artistMail FROM ArtistAlbums) C WHERE id=?;";

            st = connection.prepareStatement(query);
            st.setString(1, collection.getId());

            rs = st.executeQuery();

            if(rs.next()) {
                result = new Album( rs.getString("id"),
                        rs.getString("name"),
                        new Artist(null, rs.getString("artistMail"), null),
                        null,
                        rs.getBlob("picture"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }

    private Podcast getPodcast(JBCollection collection) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Podcast result = null;

        try {
            String query =  "SELECT * FROM Collection A NATURAL JOIN Podcast B NATURAL JOIN" +
                    "(SELECT idPodcast AS 'id', artistMail FROM ArtistPodcasts) C WHERE id=?;";

            st = connection.prepareStatement(query);
            st.setString(1, collection.getId());

            rs = st.executeQuery();

            if(rs.next()) {
                result = new Podcast(rs.getString("id"),
                        rs.getString("name"),
                        new Artist(null, rs.getString("artistMail"), null),
                        null,
                        rs.getBlob("picture"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }

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

        if(collection.getCreator() != null) {

            if (collection instanceof Playlist)
                linkPlaylistToProfile((Playlist) collection);

            else if (collection instanceof Album)
                linkAlbumToArtist((Album) collection);

            else if (collection instanceof Podcast)
                linkPodcastToArtist((Podcast) collection);

            else {
                try {
                    throw new Exception("Unable to recognize if JBCollection IS-A Playlist, Album or Podcast.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void linkPlaylistToProfile(Playlist playlist) {
        ProfileDAO pDAO = new ProfileDAO();

        if (pDAO.get(playlist.getCreator()) == null)        //if artist not present in DB
            pDAO.insert(playlist.getCreator());             //insert new artist

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

        if(pDAO.get(album.getCreator()) == null)        //if artist not present in DB
            pDAO.insert(album.getCreator());            //insert new artist

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

        if (pDAO.get(podcast.getCreator()) == null)         //if artist not present in DB
            pDAO.insert(podcast.getCreator());              //insert new artist

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

        if(collection.getTrackList() != null) {

            if (collection instanceof Playlist)
                linkPlaylistToAudios((Playlist) collection);

            else if (collection instanceof Album)
                linkAlbumToSongs((Album) collection);

            else if (collection instanceof Podcast)
                linkPodcastToEpisodes((Podcast) collection);

            else {
                try {
                    throw new Exception("Unable to recognize if JBCollection IS-A Playlist, Album or Podcast.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void linkPlaylistToAudios(Playlist playlist) {
        AudioDAO aDAO = new AudioDAO();
        ArrayList<JBAudio> trackList = playlist.getTrackList();
        Iterator<JBAudio> trackListIT = trackList.iterator();

        while(trackListIT.hasNext()) {
            JBAudio track = trackListIT.next();
            if (aDAO.get(track) == null)        //if audio not present in DB
                aDAO.insert(track);             //insert new audio
        }

        trackListIT = trackList.iterator();     //get a new iterator

        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;

        try {
            String query = "INSERT INTO PlaylistAudios(idPlaylist, idAudio) VALUES(?, ?);";
            st = connection.prepareStatement(query);
            st.setString(1, playlist.getId());

            while(trackListIT.hasNext()) {
                JBAudio track = trackListIT.next();
                st.setString(2, track.getId());
                st.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    private void linkAlbumToSongs(Album album) {
        AudioDAO aDAO = new AudioDAO();
        ArrayList<Song> trackList = album.getTrackList();
        Iterator<Song> trackListIT = trackList.iterator();

        while(trackListIT.hasNext()) {
            Song track = trackListIT.next();
            if (aDAO.get(track) == null)        //if song not present in DB
                aDAO.insert(track);             //insert new song
        }

        trackListIT = trackList.iterator();     //get a new iterator

        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;

        try {
            String query = "INSERT INTO AlbumSongs(idAlbum, idSong) VALUES(?, ?);";
            st = connection.prepareStatement(query);
            st.setString(1, album.getId());

            while(trackListIT.hasNext()) {
                Song track = trackListIT.next();
                st.setString(2, track.getId());
                st.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    private void linkPodcastToEpisodes(Podcast podcast) {
        AudioDAO aDAO = new AudioDAO();
        ArrayList<Episode> trackList = podcast.getTrackList();
        Iterator<Episode> trackListIT = trackList.iterator();

        while(trackListIT.hasNext()) {
            Episode track = trackListIT.next();
            if (aDAO.get(track) == null)        //if episode not present in DB
                aDAO.insert(track);             //insert new episode
        }

        trackListIT = trackList.iterator();     //get a new iterator

        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;

        try {
            String query = "INSERT INTO PodcastEpisodes(idPodcast, idEpisode) VALUES(?, ?);";
            st = connection.prepareStatement(query);
            st.setString(1, podcast.getId());

            while(trackListIT.hasNext()) {
                Episode track = trackListIT.next();
                st.setString(2, track.getId());
                st.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

}

