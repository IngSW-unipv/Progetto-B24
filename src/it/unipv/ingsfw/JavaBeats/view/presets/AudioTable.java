package it.unipv.ingsfw.JavaBeats.view.presets;
import com.sun.javafx.collections.ObservableListWrapper;
import it.unipv.ingsfw.JavaBeats.model.playable.JBAudio;
import it.unipv.ingsfw.JavaBeats.view.presets.tableColumns.FavoriteButtonTableColumn;
import it.unipv.ingsfw.JavaBeats.view.presets.tableColumns.PlayButtonTableColumn;
import it.unipv.ingsfw.JavaBeats.view.presets.tableColumns.TitleTableColumn;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Screen;

public class AudioTable extends TableView<JBAudio>{
  /*---------------------------------------*/
  //Attributes
  /*---------------------------------------*/
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  /*---------------------------------------*/
  //Constructor
  /*---------------------------------------*/
  public AudioTable(ObservableList<JBAudio> jbAudios){
    super();
    initComponents(jbAudios);
  }
  /*---------------------------------------*/
  //Methods
  /*---------------------------------------*/
  private void initComponents(ObservableList<JBAudio> jbAudios){
    /* 80% of clientWidth and 125 is the padding */
    double tableWidth=((double)80/100*clientWidth)-125;

    /* PlayButton column, contains the button to play the audio */
    PlayButtonTableColumn playColumn=new PlayButtonTableColumn("#");
    playColumn.setPrefWidth((double)5/100*tableWidth);

    /* Title column, with audio picture, title and artist */
    TitleTableColumn titleColumn=new TitleTableColumn("Title");
    titleColumn.setPrefWidth((double)30/100*tableWidth);

    /* Collection column */
    TableColumn<JBAudio, String> collectionColumn=new TableColumn<>("Collection");
    collectionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMetadata().getCollection().getName()));
    collectionColumn.setPrefWidth((double)30/100*tableWidth);

    /* Release date column */
    TableColumn<JBAudio, String> dateColumn=new TableColumn<>("Release date");
    dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMetadata().getReleaseDate().toString()));
    dateColumn.setPrefWidth((double)20/100*tableWidth);

    /* Favorite Button column, contains the button to "like" the audio */
    FavoriteButtonTableColumn favoriteColumn=new FavoriteButtonTableColumn("");
    favoriteColumn.setPrefWidth((double)5/100*tableWidth);

    /* Duration column */
    TableColumn<JBAudio, String> durationColumn=new TableColumn<>("Duration");
    durationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMetadata().getDuration().toString()));
    durationColumn.setPrefWidth((double)10/100*tableWidth);
    durationColumn.getStyleClass().add("durationColumn");

    /* Adding the list of audios in the table and adding all the columns */
    setItems(jbAudios);
    getColumns().addAll(playColumn, titleColumn, collectionColumn, dateColumn, favoriteColumn, durationColumn);
    setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

    /* Block of code to lock the table height given the number of rows, maxHeight=N_Of_Rows * Rows_Size +29. The 29 is given for the header row which is 55px */
    setFixedCellSize(55);
    setMaxHeight(getItems().size()*getFixedCellSize()+35);
    setPrefHeight(getMaxHeight());
    setMinHeight(getMaxHeight());
    /* css file */
    getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/tableview.css");
  }


}
