package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.model.playable.Episode;
import it.unipv.ingsfw.JavaBeats.model.playable.Podcast;
import java.util.ArrayList;

public interface IEpisodeDAO {

    //METHODS:
    public ArrayList<Episode> selectByPodcast(Podcast podcast);

}
