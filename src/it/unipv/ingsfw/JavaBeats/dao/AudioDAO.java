package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.controller.factory.DBManagerFactory;
import it.unipv.ingsfw.JavaBeats.model.playable.*;
import it.unipv.ingsfw.JavaBeats.model.user.Artist;
import it.unipv.ingsfw.JavaBeats.model.user.JBProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AudioDAO implements IAudioDAO {

    //ATTRIBUTES:
    private String schema;
    private Connection connection;



    //CONTRUCTOR:
    public AudioDAO() {
        super();
        this.schema = "JavaBeats_DB";
    }



    //METHODS:
    @Override
    public void add(JBAudio audio) {        //add new audio to database
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;

        try {
            String query =  "INSERT INTO Audio(id, title, duration, releaseDate, audioFile, isFavorite)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?);";                 //query template

            st = connection.prepareStatement(query);                //configure query
            st.setString(1, audio.getId());
            st.setString(2, audio.getMetadata().getTitle());
            st.setTime(3, audio.getMetadata().getDuration());
            st.setDate(4, audio.getMetadata().getReleaseDate());
            st.setBlob(5, audio.getAudioFileBlob());
            st.setBoolean(6, audio.isFavorite());

            st.executeUpdate();                                     //execute query

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }


    @Override
    public void remove(JBAudio audio) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;

        try {
            String query = "DELETE FROM Audio WHERE id=?;";         //query template

            st = connection.prepareStatement(query);                //configure query
            st.setString(1, audio.getId());

            st.executeUpdate();                                     //execute query

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }


    @Override
    public ArrayList<JBAudio> selectByPlalist(Playlist playlist) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        ArrayList<JBAudio> result = new ArrayList<>();

        try {
            String query = "SELECT idAudio FROM PlaylistAudios WHERE idPlaylist=?;";

            st = connection.prepareStatement(query);
            st.setString(1, playlist.getId());

            rs = st.executeQuery();

            while(rs.next()) {
                result.add(getAudioByID(rs.getString("idAudio")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }


    protected JBAudio getAudioByID(String id) {

        SongDAO sDAO = new SongDAO();
        JBAudio audioOut = sDAO.getSongByID(id);

        if(audioOut==null) {
            EpisodeDAO eDAO = new EpisodeDAO();
            audioOut=eDAO.getEpisodeByID(id);
        }

        return audioOut;
    }


    protected String[] getGenresByAudioID(String field) {
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

}