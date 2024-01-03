package it.unipv.ingsfw.JavaBeats.view.presets;

import it.unipv.ingsfw.JavaBeats.model.playable.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.JBCollection;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;


public class AudioTable extends TableView<JBAudio> {

    public AudioTable(ObservableList<JBAudio> jbAudios){
        super();
        initComponents(jbAudios);
    }

    private void initComponents(ObservableList<JBAudio> jbAudios) {
        //Columns

        //Title column
        TableColumn<JBAudio, String> titleColumn=new TableColumn<>("Title");
        titleColumn.setMinWidth(500);
        titleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMetadata().getTitle()));

        //Collection column
        TableColumn<JBAudio, String> collectionColumn=new TableColumn<>("Collection");
        collectionColumn.setMinWidth(450);
        collectionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMetadata().getCollection().getCreator().getUsername()));

        //Date column
        TableColumn<JBAudio, String> dateColumn=new TableColumn<>("Release date");
        dateColumn.setMinWidth(300);
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMetadata().getReleaseDate().toString()));

//        //Title column
//        TableColumn<JBAudio, Button> isFavoriteColumn=new TableColumn<>("");
//        titleColumn.setMinWidth(100);
//        titleColumn.setCellValueFactory(new PropertyValueFactory<>("isFavorite"));

        //Title column
        TableColumn<JBAudio, String> durationColumn=new TableColumn<>("Duration");
        durationColumn.setMinWidth(150);
        durationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMetadata().getDuration().toString()));

        setItems(jbAudios);
        getColumns().addAll(titleColumn, collectionColumn, dateColumn, durationColumn);
    }


}
