package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.controller.factory.DBManagerFactory;
import it.unipv.ingsfw.JavaBeats.model.playable.*;
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



    //PUBLIC METHODS:
    @Override
    public void insert(JBAudio audio) {

        insertAudio(audio);             //insert audio to Audio table

        linkAudioToArtist(audio);       //link audio (already present in db) to artist (from audio metadata)

        linkAudioToCollection(audio);   //link audio to Album or Podcast (from audio metadata)

        linkAudioToGenres(audio);       //link audio to Genres (from audio metadata)

    }

    @Override
    public void remove(JBAudio audio) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;

        try {
            String query = "DELETE FROM Audio WHERE id=?;";

            st = connection.prepareStatement(query);
            st.setString(1, audio.getId());

            st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    @Override
    public JBAudio get(JBAudio audio) {
        return getAudioByID(audio.getId());
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



    //PROTECTED METHODS:
    protected JBAudio getAudioByID(String id) {

        JBAudio audioOut = getSongByID(id);
        if(audioOut==null)
            audioOut=getEpisodeByID(id);

        return audioOut;
    }

    protected Song getSongByID(String id) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Song result = null;
        ProfileDAO pDAO = new ProfileDAO();
        CollectionDAO cDAO = new CollectionDAO();

        try {
            String query =  "SELECT * FROM (SELECT id as 'idSong', isFavorite, duration, title, releaseDate, audioFile FROM Audio) A" +
                    "NATURAL JOIN (SELECT idAudio as 'idSong', artistMail FROM ArtistAudios) B" +
                    "NATURAL JOIN AlbumSongs WHERE idSong=?;";

            st = connection.prepareStatement(query);
            st.setString(1, id);

            rs = st.executeQuery();

            while(rs.next()) {  //while results are available (only 1 result expected, if not only the last one is taken)
                result = new Song(  rs.getString("idSong"),
                                    rs.getString("title"),
                                    pDAO.getArtistByMail(rs.getString("artistMail")),
                                    cDAO.getAlbumByID(rs.getString("idAlbum")),
                                    rs.getBlob("audioFile"),
                                    rs.getTime("duration"),
                                    rs.getDate("releaseDate"),
                                    getGenresByAudioID(rs.getString("idSong")),
                                    rs.getBoolean("isFavorite"));
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
        CollectionDAO cDAO = new CollectionDAO();

        try {
            String query =  "SELECT * FROM (SELECT id as 'idEpisode', isFavorite, duration, title, releaseDate, audioFile FROM Audio) A" +
                    "NATURAL JOIN (SELECT idAudio as 'idEpisode', artistMail FROM ArtistAudios) B" +
                    "NATURAL JOIN PodcastEpisodes WHERE idEpisode=?;";

            st = connection.prepareStatement(query);
            st.setString(1, id);

            rs = st.executeQuery();

            while(rs.next()) {      //while results are available (only 1 result expected, if not: take the last one)
                result = new Episode(   rs.getString("idEpisode"),
                                        rs.getString("title"),
                                        pDAO.getArtistByMail(rs.getString("artistMail")),
                                        cDAO.getPodcastByID(rs.getString("idPodcast")),
                                        rs.getBlob("audioFile"),
                                        rs.getTime("duration"),
                                        rs.getDate("releaseDate"),
                                        getGenresByAudioID(rs.getString("idEpisode")),
                                        rs.getBoolean("isFavorite"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }

    protected String[] getGenresByAudioID(String idAudio) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        ArrayList<String> result = new ArrayList<>();

        try {
            String query = "SELECT genre FROM Profile NATURAL JOIN Artist WHERE idAudio=?;";

            st = connection.prepareStatement(query);
            st.setString(1, idAudio);

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



    //PRIVATE METHODS:
    private void insertAudio(JBAudio audio) {
        //insert audio to Audio table
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st1, st2, st3;

        try {
            String q1 = "INSERT INTO Audio(id, title, duration, releaseDate, audioFile, isFavorite)" +
                    "VALUES(?, ?, ?, ?, ?, ?);";

            st1 = connection.prepareStatement(q1);
            st1.setString(1, audio.getId());
            st1.setString(2, audio.getMetadata().getTitle());
            st1.setTime(3, audio.getMetadata().getDuration());
            st1.setDate(4, audio.getMetadata().getReleaseDate());
            st1.setBlob(5, audio.getAudioFileBlob());
            st1.setBoolean(6, audio.isFavorite());

            st1.executeUpdate();

            if(audio instanceof Song) {                 //if audio is a song insert it into the Song table
                String q2 = "INSERT INTO Song(id) VALUES(?);";

                st2 = connection.prepareStatement(q2);
                st2.setString(1, audio.getId());

                st2.executeUpdate();

            } else if (audio instanceof Episode) {      //if audio is an episode insert it into the Episode table
                String q3 =  "INSERT INTO Episode(id) VALUES(?);";

                st3 = connection.prepareStatement(q3);
                st3.setString(1, audio.getId());

                st3.executeUpdate();

            } else {
                //THROW EXCEPTION
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    private void linkAudioToArtist(JBAudio audio) {
        //link inserted audio to artist:
        ProfileDAO pDAO = new ProfileDAO();

        if(pDAO.getArtistByMail(audio.getMetadata().getArtist().getMail()) == null)         //if artist not present in DB
            pDAO.insert(audio.getMetadata().getArtist());                                   //insert new artist

        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;

        try {
            String query =  "INSERT INTO ArtistAudios(idAudio, artistMail) VALUES(?, ?);";

            st = connection.prepareStatement(query);
            st.setString(1, audio.getId());
            st.setString(2, audio.getMetadata().getArtist().getMail());

            st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    private void linkAudioToCollection(JBAudio audio) {

        CollectionDAO cDAO = new CollectionDAO();

        if(cDAO.get(audio.getMetadata().getCollection()) == null)   //if the collection is not present in the DB
            cDAO.insert(audio.getMetadata().getCollection());       //insert new collection in DB

        if(audio instanceof Song) {                 //audio is a song --> link song to an Album

            connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
            PreparedStatement st;

            try {
                String query =  "INSERT INTO AlbumSongs(idSong, idAlbum) VALUES(?, ?);";

                st = connection.prepareStatement(query);
                st.setString(1, audio.getId());
                st.setString(2, audio.getMetadata().getCollection().getId());

                st.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }

            DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        }
        else if (audio instanceof Episode) {      //audio is an Episode --> link episode to a Podcast

            connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
            PreparedStatement st;

            try {
                String query =  "INSERT INTO PodcastEpisodes(idEpisode, idPodcast) VALUES(?, ?);";

                st = connection.prepareStatement(query);
                st.setString(1, audio.getId());
                st.setString(2, audio.getMetadata().getCollection().getId());

                st.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }

            DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        }
        else {
            //THROW EXCEPTION
        }
    }

    private void linkAudioToGenres(JBAudio audio) {

    }



}