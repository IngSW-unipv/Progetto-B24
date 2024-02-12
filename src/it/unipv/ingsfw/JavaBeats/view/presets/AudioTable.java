package it.unipv.ingsfw.JavaBeats.view.presets;

import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.tableColumns.DeleteButtonTableColumn;
import it.unipv.ingsfw.JavaBeats.view.presets.tableColumns.FavoriteButtonTableColumn;
import it.unipv.ingsfw.JavaBeats.view.presets.tableColumns.PlayButtonTableColumn;
import it.unipv.ingsfw.JavaBeats.view.presets.tableColumns.TitleTableColumn;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Screen;

public class AudioTable extends TableView<JBAudio>{
  /*---------------------------------------*/
  //Attributes
  /*---------------------------------------*/
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private PlayButtonTableColumn playColumn;
  private TitleTableColumn titleColumn;
  private TableColumn<JBAudio, String> collectionColumn;
  private TableColumn<JBAudio, String> streamsColumn;
  private TableColumn<JBAudio, String> dateColumn;
  private FavoriteButtonTableColumn favoriteColumn;
  private TableColumn<JBAudio, String> durationColumn;
  private DeleteButtonTableColumn deleteColumn;


  /*---------------------------------------*/
  //Constructor
  /*---------------------------------------*/
  public AudioTable(ObservableList<JBAudio> jbAudios, JBProfile jbProfile, JBCollection jbCollection){
    super();
    initComponents(jbAudios, jbProfile);
    getCollectionColumns(jbAudios, jbProfile, jbCollection);

  }

  public AudioTable(ObservableList<JBAudio> jbAudios, JBProfile jbProfile){
    super();
    initComponents(jbAudios, jbProfile);
    getQueueColumns(jbAudios);
  }


  /*---------------------------------------*/
  //Methods
  /*---------------------------------------*/
  private void initComponents(ObservableList<JBAudio> jbAudios, JBProfile jbProfile){
    /* 80% of clientWidth and 125 is the padding */
    double tableWidth=((double)80/100*clientWidth)-125;

    /* PlayButton column, contains the button to play the audio */
    playColumn=new PlayButtonTableColumn("#");
    playColumn.setPrefWidth((double)5/100*tableWidth);

    /* Title column, with audio picture, title and artist */
    titleColumn=new TitleTableColumn("Title");
    titleColumn.setPrefWidth((double)30/100*tableWidth);

    /* Collection column */
    collectionColumn=new TableColumn<>("Collection");
    collectionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMetadata().getCollection().getName()));
    collectionColumn.setPrefWidth((double)30/100*tableWidth);

    /* Streams column */
    streamsColumn=new TableColumn<>("Streams");
    streamsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNumberOfStreams())));
    streamsColumn.setPrefWidth((double)30/100*tableWidth);

    /* Release date column */
    dateColumn=new TableColumn<>("Release date");
    dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMetadata().getReleaseDate().toString()));
    dateColumn.setPrefWidth((double)20/100*tableWidth);

    /* Favorite Button column, contains the button to "like" the audio */
    favoriteColumn=new FavoriteButtonTableColumn("", jbProfile);
    favoriteColumn.setPrefWidth((double)5/100*tableWidth);

    /* Duration column */
    durationColumn=new TableColumn<>("Duration");
    durationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(JBAudio.convertToHoursMinutesAndSeconds(cellData.getValue().getMetadata().getDuration())));
    durationColumn.setPrefWidth((double)10/100*tableWidth);
    durationColumn.getStyleClass().add("durationColumn");

    /* Delete Button column, contains the button to delete the audio */
    deleteColumn=new DeleteButtonTableColumn("");
    deleteColumn.setPrefWidth((double)5/100*tableWidth);
  }

  private void getCollectionColumns(ObservableList<JBAudio> jbAudios, JBProfile jbProfile, JBCollection jbCollection){

    /* If activeProfile is not the creator, then we don't allow the delete column */
    if(!jbProfile.equals(jbCollection.getCreator())){
      /* If showing a playlist -> we prefer to put emphasis on the collection name
       *  If showing an Album or a Podcast -> we prefer to put emphasis on the number of streams
       *  */
      try{
        Playlist p=(Playlist)jbCollection;

        getColumns().addAll(playColumn, titleColumn, collectionColumn, dateColumn, favoriteColumn, durationColumn);
      }catch(ClassCastException c){
        getColumns().addAll(playColumn, titleColumn, streamsColumn, dateColumn, favoriteColumn, durationColumn);
      }//end-try
    }else{
      /* If activeProfile is the creator then the choices vary depending on the type of the collection */
      try{
        Playlist playlist=(Playlist)jbCollection;

        if(jbCollection.getName().equals(jbProfile.getFavorites().getName())){
          getColumns().addAll(playColumn, titleColumn, collectionColumn, dateColumn, favoriteColumn, durationColumn);
        }else{
          getColumns().addAll(playColumn, titleColumn, collectionColumn, dateColumn, favoriteColumn, durationColumn, deleteColumn);
        }//end-if

      }catch(ClassCastException e){
        getColumns().addAll(playColumn, titleColumn, streamsColumn, dateColumn, favoriteColumn, durationColumn);
      }//end-try
    }

    /* Adding the list of audios in the table and adding all the columns */
    setItems(jbAudios);
    setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

    /* Block of code to lock the table height given the number of rows, maxHeight=N_Of_Rows * Rows_Size +29. The 29 is given for the header row which is 55px */
    setFixedCellSize(55);
    setMaxHeight(getItems().size()*getFixedCellSize()+35);
    setPrefHeight(getMaxHeight());
    setMinHeight(getMaxHeight());
    /* css file */
    getStylesheets().add("it/unipv/ingsfw/JavaBeats/view/resources/css/tableview.css");
  }

  private void getQueueColumns(ObservableList<JBAudio> jbAudios){
    getColumns().addAll(playColumn, titleColumn, dateColumn, favoriteColumn, durationColumn, deleteColumn);

    /* Adding the list of audios in the table and adding all the columns */
    setItems(jbAudios);
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
