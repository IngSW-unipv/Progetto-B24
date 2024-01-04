package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.controller.factory.DBManagerFactory;
import it.unipv.ingsfw.JavaBeats.model.playable.Album;
import it.unipv.ingsfw.JavaBeats.model.playable.Episode;
import it.unipv.ingsfw.JavaBeats.model.playable.Podcast;
import it.unipv.ingsfw.JavaBeats.model.playable.Song;
import it.unipv.ingsfw.JavaBeats.model.user.Artist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EpisodeDAO implements IEpisodeDAO {

    //ATTRIBUTES:
    private String schema;
    private Connection connection;



    //CONTRUCTOR:
    public EpisodeDAO() {
        super();
        this.schema = "JavaBeats_DB";
    }



    //METHODS:
    @Override
    public ArrayList<Episode> selectByPodcast(Podcast podcast) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        ArrayList<Episode> result = new ArrayList<>();

        try {
            String query = "SELECT idEpisode FROM PodcastEpisodes WHERE idPodcast=?;";

            st = connection.prepareStatement(query);
            st.setString(1, podcast.getId());

            rs = st.executeQuery();

            while (rs.next()) {
                result.add(getEpisodeByID(rs.getString("idEpisode")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }


    protected Episode getEpisodeByID(String id) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Episode result = null;
        ProfileDAO pDAO = new ProfileDAO();
        PodcastDAO podDAO = new PodcastDAO();
        AudioDAO aDAO = new AudioDAO();

        try {
            String query =  "SELECT * FROM (SELECT id as 'idEpisode', isFavorite, duration, title, releaseDate, audioFile FROM Audio) A" +
                    "NATURAL JOIN (SELECT idAudio as 'idEpisode', artistMail FROM ArtistAudios) B" +
                    "NATURAL JOIN PodcastEpisodes WHERE idEpisode=?;";   //query template

            st = connection.prepareStatement(query);                //configure query
            st.setString(1, id);

            rs = st.executeQuery();                                 //execute query

            while(rs.next()) {                                      //while results are available (only 1 result expected, if not: take the last one)
                result = new Episode(  rs.getString("idEpisode"),
                        rs.getString("title"),
                        pDAO.getArtistByMail(rs.getString("artistMail")),
                        podDAO.getPodcastByID(rs.getString("idPodcast")),
                        rs.getBlob("audioFile"),
                        rs.getTime("duration"),
                        rs.getDate("releaseDate"),
                        aDAO.getGenresByAudioID(rs.getString("idEpisode")),
                        rs.getBoolean("isFavorite"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }


}
