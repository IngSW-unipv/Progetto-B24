package it.unipv.ingsfw.JavaBeats.dao;

import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import java.sql.Connection;

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

}