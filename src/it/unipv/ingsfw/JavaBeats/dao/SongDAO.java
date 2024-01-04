package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.controller.factory.DBManagerFactory;
import it.unipv.ingsfw.JavaBeats.model.playable.Album;
import it.unipv.ingsfw.JavaBeats.model.playable.Song;
import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;
import it.unipv.ingsfw.JavaBeats.model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SongDAO implements ISongDAO {

    //ATTRIBUTES:
    private String schema;
    private Connection connection;



    //CONTRUCTOR:
    public SongDAO() {
        super();
        this.schema = "JavaBeats_DB";
    }



    //METHODS:
    @Override
    public ArrayList<Song> selectByAlbum(Album album) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        ArrayList<Song> result = new ArrayList<>();

        try {
            String query = "SELECT idSong FROM AlbumSongs WHERE idAlbum=?;";

            st = connection.prepareStatement(query);
            st.setString(1, album.getId());

            rs = st.executeQuery();

            while (rs.next()) {
                result.add(getSongByID(rs.getString("idSong")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }


    protected Song getSongByID(String id) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Song result = null;
        ProfileDAO pDAO = new ProfileDAO();
        AlbumDAO aDAO = new AlbumDAO();
        AudioDAO audDAO = new AudioDAO();

        try {
            String query =  "SELECT * FROM (SELECT id as 'idSong', isFavorite, duration, title, releaseDate, audioFile FROM Audio) A" +
                    "NATURAL JOIN (SELECT idAudio as 'idSong', artistMail FROM ArtistAudios) B" +
                    "NATURAL JOIN AlbumSongs WHERE idSong=?;";   //query template

            st = connection.prepareStatement(query);                //configure query
            st.setString(1, id);

            rs = st.executeQuery();                                 //execute query

            while(rs.next()) {                                      //while results are available (only 1 result expected, if not: take the last one)
                result = new Song(  rs.getString("idSong"),
                        rs.getString("title"),
                        pDAO.getArtistByMail(rs.getString("artistMail")),
                        aDAO.getAlbumByID(rs.getString("idAlbum")),
                        rs.getBlob("audioFile"),
                        rs.getTime("duration"),
                        rs.getDate("releaseDate"),
                        audDAO.getGenresByAudioID(rs.getString("idSong")),
                        rs.getBoolean("isFavorite"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }

}
