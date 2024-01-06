package it.unipv.ingsfw.JavaBeats.view.presets;
import it.unipv.ingsfw.JavaBeats.model.playable.JBAudio;
import it.unipv.ingsfw.JavaBeats.view.presets.tableColumns.PlayButtonTableColumn;
import it.unipv.ingsfw.JavaBeats.view.presets.tableColumns.TitleTableColumn;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

public class AudioTable extends TableView<JBAudio>{

  public AudioTable(ObservableList<JBAudio> jbAudios){
    super();
    initComponents(jbAudios);

  }

  private void initComponents(ObservableList<JBAudio> jbAudios){
    //Columns

    /* PlayButton column, contains the button to play the audio */
    PlayButtonTableColumn playColumn=new PlayButtonTableColumn("#");
    playColumn.setMinWidth(20);

    /* Title column, with audio picture, title and artist */
    TitleTableColumn titleColumn=new TitleTableColumn("Title");
    titleColumn.setMinWidth(100);


    //Collection column
    TableColumn<JBAudio, String> collectionColumn=new TableColumn<>("Collection");
    collectionColumn.setMinWidth(450);
    collectionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMetadata().getCollection().getName()));

    //Date column
    TableColumn<JBAudio, String> dateColumn=new TableColumn<>("Release date");
    dateColumn.setMinWidth(300);
    dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMetadata().getReleaseDate().toString()));

    //Title column
    TableColumn<JBAudio, String> durationColumn=new TableColumn<>("Duration");
    durationColumn.setMinWidth(150);
    durationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMetadata().getDuration().toString()));

    setItems(jbAudios);
    getColumns().addAll(playColumn, titleColumn, collectionColumn, dateColumn, durationColumn);

    /* Block of code to lock the table height given the number of rows, maxHeight=N.of rows * RowsSize */
    setFixedCellSize(55);
    setMaxHeight(getItems().size()*getFixedCellSize()+29);
    setPrefHeight(getMaxHeight());
    setMinHeight(getMaxHeight());
    //css file
    getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/tableview.css");
  }


}
