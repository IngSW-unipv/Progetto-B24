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
    public void updateIsFavorite(JBAudio audio, JBProfile activeProfile) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;

        try {
            if(audio.isFavorite()) {
                String query = "INSERT INTO Favorites(profileMail, idAudio) VALUES(?, ?);";
                st = connection.prepareStatement(query);
                st.setString(1, activeProfile.getMail());
                st.setString(2, audio.getId());
                st.executeUpdate();
            }
            else {
                String query = "REMOVE FROM Favorites WHERE (profileMail=? AND idAudio=?);";
                st = connection.prepareStatement(query);
                st.setString(1, activeProfile.getMail());
                st.setString(2, audio.getId());
                st.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
    }

    @Override
    public JBAudio get(JBAudio audio, JBProfile activeProfile) {
        JBAudio audioOut = getSong(audio, activeProfile);
        if(audioOut==null)
            audioOut=getEpisode(audio, activeProfile);

        return audioOut;
    }

    @Override
    public JBAudio get(JBAudio audio) {
        return get(audio, null);
    }

    @Override
    public void addToListeningHistory(JBAudio audio, JBProfile profile) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;

        try {
            String query = "INSERT INTO ListeningHistory(profileMail, idAudio) VALUE(?, ?);";
            st = connection.prepareStatement(query);
            st.setString(1, profile.getMail());
            st.setString(2, audio.getId());
            st.executeUpdate();

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
                if(get(new Song(rs.getString("idAudio"), null, null, null)) != null) {              //if audio is in Song table
                    result.add(new Song(rs.getString("idAudio"), null, null, null));
                }
                else if((get(new Episode(rs.getString("idAudio"), null, null, null)) != null)) {    //if audio is in Episode table
                    result.add(new Episode(rs.getString("idAudio"), null, null, null));
                }
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
                result.add( (Song) get(new Song(rs.getString("idSong"), null, null, null) ) );
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
                result.add( (Episode) get(new Episode(rs.getString("idEpisode"), null, null, null) ) );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }



    //PRIVATE METHODS:
    private Song getSong(JBAudio audio, JBProfile activeProfile) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Song result = null;

        try {
            String query =  "SELECT * FROM (SELECT id as 'idSong', duration, title, releaseDate, audioFile FROM Audio) A " +
                    "NATURAL JOIN (SELECT idAudio as 'idSong', artistMail FROM ArtistAudios) B " +
                    "NATURAL JOIN AlbumSongs WHERE idSong=?;";

            st = connection.prepareStatement(query);
            st.setString(1, audio.getId());

            rs = st.executeQuery();

            if(rs.next()) {
                result = new Song(  rs.getString("idSong"),
                        rs.getString("title"),
                        new Artist(null, rs.getString("artistMail"), null),
                        new Album(rs.getString("idAlbum"), null, null, null),
                        rs.getBlob("audioFile"),
                        rs.getTime("duration"),
                        rs.getDate("releaseDate"),
                        null,
                        false,
                        0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        if(result != null) {
            result.getMetadata().setGenres(getGenres(result));      //set Genres in metadata
            result.setFavorite(isFavorite(result, activeProfile));  //set isFavorite
            result.setNumberOfStreams(getNumberOfStreams(result));  //set total number of streams
            if(activeProfile!=null) result.setFavorite(isFavorite(result, activeProfile));
        }

        return result;
    }

    private Episode getEpisode(JBAudio audio, JBProfile activeProfile) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        Episode result = null;

        try {
            String query =  "SELECT * FROM (SELECT id as 'idEpisode', duration, title, releaseDate, audioFile FROM Audio) A " +
                    "NATURAL JOIN (SELECT idAudio as 'idEpisode', artistMail FROM ArtistAudios) B " +
                    "NATURAL JOIN PodcastEpisodes WHERE idEpisode=?;";

            st = connection.prepareStatement(query);
            st.setString(1, audio.getId());

            rs = st.executeQuery();

            if(rs.next()) {
                result = new Episode(   rs.getString("idEpisode"),
                        rs.getString("title"),
                        new Artist(null, rs.getString("artistMail"), null),
                        new Podcast(rs.getString("idPodcast"), null, null, null),
                        rs.getBlob("audioFile"),
                        rs.getTime("duration"),
                        rs.getDate("releaseDate"),
                        null,
                        false,
                        0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        if(result != null) {
            result.getMetadata().setGenres(getGenres(result));
            result.setFavorite(isFavorite(result, activeProfile));
            result.setNumberOfStreams(getNumberOfStreams(result));
            if(activeProfile!=null) result.setFavorite(isFavorite(result, activeProfile));  //THROWS EXCEPTIONS IF ACTIVE PROFILE IS NULL
        }

        return result;
    }

    private String[] getGenres(JBAudio audio) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        ArrayList<String> result = new ArrayList<>();

        try {
            String query = "SELECT genre FROM AudioGenres WHERE idAudio=?;";

            st = connection.prepareStatement(query);
            st.setString(1, audio.getId());

            rs = st.executeQuery();

            while(rs.next()) {
                String str = rs.getString("genre");
                result.add(str);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        String[] arrayOut = null;
        if(result != null) {
            arrayOut = new String[result.size()];      //converting arrayList to Array
            arrayOut = result.toArray(arrayOut);
        }

        return arrayOut;
    }

    private int getNumberOfStreams(JBAudio audio) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        int result = 0;

        try {
            String query = "SELECT count(idAudio) as 'total' FROM ListeningHistory WHERE idAudio=?;";

            st = connection.prepareStatement(query);
            st.setString(1, audio.getId());

            rs = st.executeQuery();

            if(rs.next())
                result = rs.getInt("total");

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }

    private boolean isFavorite(JBAudio audio, JBProfile activeProfile) {
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st;
        ResultSet rs;
        boolean result = false;

        try {
            String query = "SELECT idAudio FROM Favorites WHERE profileMail=?;";

            st = connection.prepareStatement(query);
            st.setString(1, activeProfile.getMail());

            rs = st.executeQuery();

            result = rs.next();     //result=true if exists at least one instance

        } catch (Exception e) {
            e.printStackTrace();
        }

        DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

        return result;
    }

    private void insertAudio(JBAudio audio) {
        //insert audio to Audio table
        connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
        PreparedStatement st1, st2, st3;

        try {
            String q1 = "INSERT INTO Audio(id, title, duration, releaseDate, audioFile)" +
                    "VALUES(?, ?, ?, ?, ?);";

            st1 = connection.prepareStatement(q1);
            st1.setString(1, audio.getId());
            st1.setString(2, audio.getMetadata().getTitle());
            st1.setTime(3, audio.getMetadata().getDuration());
            st1.setDate(4, audio.getMetadata().getReleaseDate());
            st1.setBlob(5, audio.getAudioFileBlob());

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

        ProfileDAO pDAO = new ProfileDAO();

        if(pDAO.get(audio.getMetadata().getArtist()) == null)    //if artist not present in DB
            pDAO.insert(audio.getMetadata().getArtist());           //insert new artist

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

        if(audio.getMetadata().getCollection() != null) {       //sometimes metadata don't specify collection

            CollectionDAO cDAO = new CollectionDAO();

            if (cDAO.get(audio.getMetadata().getCollection()) == null)      //if the collection is not present in the DB
                cDAO.insert(audio.getMetadata().getCollection());           //insert new collection in DB

            if (audio instanceof Song) {                 //audio is a song --> link song to an Album

                connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
                PreparedStatement st;

                try {
                    String query = "INSERT INTO AlbumSongs(idSong, idAlbum) VALUES(?, ?);";

                    st = connection.prepareStatement(query);
                    st.setString(1, audio.getId());
                    st.setString(2, audio.getMetadata().getCollection().getId());

                    st.executeUpdate();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

            } else if (audio instanceof Episode) {      //audio is an Episode --> link episode to a Podcast

                connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
                PreparedStatement st;

                try {
                    String query = "INSERT INTO PodcastEpisodes(idEpisode, idPodcast) VALUES(?, ?);";

                    st = connection.prepareStatement(query);
                    st.setString(1, audio.getId());
                    st.setString(2, audio.getMetadata().getCollection().getId());

                    st.executeUpdate();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                DBManagerFactory.getInstance().getDBManager().closeConnection(connection);

            } else {
                //THROW EXCEPTION
            }
        }
    }

    private void linkAudioToGenres(JBAudio audio) {

        if(audio.getMetadata().getGenres() != null) {       //sometimes metadata don't specify genres
            String[] genreArray = audio.getMetadata().getGenres();

            connection = DBManagerFactory.getInstance().getDBManager().startConnection(connection, schema);
            PreparedStatement st1, st2, st3;
            ResultSet rs1;

            try {
                String q1 = "SELECT genre FROM Genre WHERE genre=?;";
                st1 = connection.prepareStatement(q1);
                String q2 = "INSERT INTO Genre(genre) VALUE(?);";
                st2 = connection.prepareStatement(q2);
                String q3 = "INSERT INTO AudioGenres(idAudio, genre) VALUE(?, ?);";
                st3 = connection.prepareStatement(q3);

                for (int i = 0; i < genreArray.length; i++) {                //for every genre

                    st1.setString(1, genreArray[i]);       //check if genre is already in DB
                    rs1 = st1.executeQuery();

                    if (rs1.getString("genre") == null) {        //if genre not in DB
                        st2.setString(1, genreArray[i]);      //insert new genre
                        st2.executeUpdate();
                    }

                    st3.setString(1, audio.getId());         //link audio to genre
                    st3.setString(2, genreArray[i]);
                    st3.executeUpdate();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            DBManagerFactory.getInstance().getDBManager().closeConnection(connection);
        }
    }

}