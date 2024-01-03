package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.controller.factory.DBManagerFactory;
import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.playable.Album;
import it.unipv.ingsfw.JavaBeats.model.playable.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.playable.Podcast;
import it.unipv.ingsfw.JavaBeats.model.playable.Song;
import it.unipv.ingsfw.JavaBeats.model.user.Artist;
import it.unipv.ingsfw.JavaBeats.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

public class SearchDAO implements ISearchDAO {

    //ATTRIBUTES:
    private String schema;
    private Connection connection;


    //CONTRUCTOR:
    public SearchDAO() {
        super();
        this.schema = "JavaBeats_DB";
    }


    //METHODS:
    @Override
    public IJBResearchable search(String field) {
        return null;
    }

    @Override
    public IJBResearchable searchBy(String field, ERESEARCH mode) {
        switch (mode) {
            case USER:
                break;
            case ARTIST:
                break;
            case SONG:
                break;
            case EPISODE:
                break;
            case PLAYLIST:
                break;
            case ALBUM:
                break;
            case PODCAST:
                break;
        }

        return null;
    }


    public ArrayList<User> searchUser(String field) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        ArrayList<User> result = new ArrayList<>();

        try {
            String query = "SELECT * FROM Profile NATURAL JOIN User WHERE (username=? OR name=? OR surname=?) AND isVisible=1;";

            st = connection.prepareStatement(query);
            st.setString(1, field);
            st.setString(2, field);
            st.setString(3, field);

            rs = st.executeQuery();

            while(rs.next()) {
                User u = new User(  rs.getString("username"),
                                    rs.getString("mail"),
                                    rs.getString("password"),
                                    rs.getString("name"),
                                    rs.getString("surname"),
                                    rs.getString("biography"),
                                    rs.getBlob("profilePicture"),
                                    rs.getBoolean("isVisible"));
                result.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }


    public ArrayList<Artist> searchArtist(String field) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        ArrayList<Artist> result = new ArrayList<>();

        try {
            String query = "SELECT * FROM Profile NATURAL JOIN Artist WHERE username=? OR name=? OR surname=?;";

            st = connection.prepareStatement(query);
            st.setString(1, field);
            st.setString(2, field);
            st.setString(3, field);

            rs = st.executeQuery();

            while(rs.next()) {
                Artist a = new Artist(  rs.getString("username"),
                                        rs.getString("mail"),
                                        rs.getString("password"),
                                        rs.getString("name"),
                                        rs.getString("surname"),
                                        rs.getString("biography"),
                                        rs.getBlob("profilePicture"),
                                        rs.getInt("totalListeners"));
                result.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }


    public ArrayList<Song> searchSong(String field) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        ArrayList<Song> result = new ArrayList<>();

        try {
            String query =  "SELECT idSong, title, artistMail, artistUsername, artistName, artistSurname, idAlbum, albumName, isFavorite, duration, releaseDate, audioFile  FROM (" +
                                "SELECT idAudio as 'idSong', artistMail, artistUsername, artistName, artistSurname, isFavorite, duration, releaseDate, title, audioFile FROM ArtistAudios" +
                                "NATURAL JOIN" +
                                    "(SELECT mail AS 'artistMail', username AS 'artistUsername', name AS 'artistName', surname AS 'artistSurname' FROM Profile NATURAL JOIN Artist) A" +
                                "NATURAL JOIN" +
                                    "(SELECT id AS 'idAudio', isFavorite, duration, title, releaseDate, audioFile FROM Audio) B" +
                                ") C" +
                            "NATURAL JOIN (SELECT * FROM AlbumSongs NATURAL JOIN (SELECT id AS 'idAlbum', name AS 'albumName', picture AS 'albumPicture' FROM COLLECTION)D) E" +
                            "WHERE title=? OR artistUsername=? OR artistName=? OR artistSurname=? OR albumName=?;";

            st = connection.prepareStatement(query);
            st.setString(1, field);
            st.setString(2, field);
            st.setString(3, field);
            st.setString(4, field);
            st.setString(5, field);

            rs = st.executeQuery();

            while(rs.next()) {
                Song a = new Song(  rs.getString("idSong"),
                                    rs.getString("title"),
                                    getArtistByMail(rs.getString("artistMail")),
                                    getAlbumByID(rs.getString("idAlbum")),
                                    rs.getBlob("audioFile"),
                                    rs.getTime("duration"),
                                    rs.getDate("releaseDate"),
                                    getGenresByAudioID(rs.getString("idSong")),
                                    rs.getBoolean("isFavorite"));
                result.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }

    private Artist getArtistByMail(String field) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Artist result = null;

        try {
            String query = "SELECT * FROM Profile NATURAL JOIN Artist WHERE mail=?;";

            st = connection.prepareStatement(query);
            st.setString(1, field);

            rs = st.executeQuery();

            while(rs.next()) {
                result = new Artist(  rs.getString("username"),
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

    private String[] getGenresByAudioID(String field) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        ArrayList<String> result = new ArrayList<>();

        try {
            String query = "SELECT genre FROM Profile NATURAL JOIN Artist WHERE idAudio=?;";

            st = connection.prepareStatement(query);
            st.setString(1, field);

            rs = st.executeQuery();

            while(rs.next()) {
                String str = rs.getString("genre");
                result.add(str);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        String[] arrayOut = new String[result.size()];      //converting arrayList to Array
        arrayOut = result.toArray(arrayOut);

        return arrayOut;
    }

    private Album getAlbumByID(String field) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Album result = null;

        try {
            String query = "SELECT * FROM ArtistAlbums NATURAL JOIN (SELECT id as 'idAlbum', name, picture FROM Collection) A WHERE idAlbum=?;";

            st = connection.prepareStatement(query);
            st.setString(1, field);

            rs = st.executeQuery();

            while(rs.next()) {
                result = new Album(    rs.getString("idAlbum"),
                                        rs.getString("name"),
                                        getArtistByMail(rs.getString("artistMail")),
                                        rs.getBlob("picture"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }

    private Podcast getPodcastByID(String field) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Podcast result = null;

        try {
            String query = "SELECT * FROM ArtistPodcast NATURAL JOIN (SELECT id as 'idPodcast', name, picture FROM Collection) A WHERE idPodcast=?;";

            st = connection.prepareStatement(query);
            st.setString(1, field);

            rs = st.executeQuery();

            while(rs.next()) {
                result = new Podcast(    rs.getString("idPodcast"),
                                            rs.getString("name"),
                                            getArtistByMail(rs.getString("artistMail")),
                                            rs.getBlob("picture"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }

}