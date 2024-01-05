package it.unipv.ingsfw.JavaBeats.view.library;

import com.pixelduke.control.skin.FXSkins;
import it.unipv.ingsfw.JavaBeats.model.playable.EJBPLAYABLE;
import it.unipv.ingsfw.JavaBeats.model.playable.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.Playlist;
import it.unipv.ingsfw.JavaBeats.model.playable.Song;
import it.unipv.ingsfw.JavaBeats.model.user.Artist;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioTable;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.stage.Screen;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

public class CollectionViewGui{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
  private Scene scene;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public CollectionViewGui(){
    super();
    initComponents();
  }

  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public Scene getScene(){
    return scene;
  }

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(){
    CollectionHeader collectionHeader=new CollectionHeader(EJBPLAYABLE.PLAYLIST);
    ObservableList<JBAudio> songList=FXCollections.observableArrayList();
    try{
      songList.add(new Song("id", "titolo", new Artist("rob", "rob", "rob"), new Playlist("id", "nomePlaylist", new Artist("rob", "rob", "rob")), new SerialBlob(new byte[] {0, 1}), new Time(100), new Date(100), new String[] {"rock", "pop"}, true));
      songList.add(new Song("id", "titolo", new Artist("rob", "rob", "rob"), new Playlist("id", "nomePlaylist", new Artist("rob", "rob", "rob")), new SerialBlob(new byte[] {0, 1}), new Time(100), new Date(100), new String[] {"rock", "pop"}, true));
      songList.add(new Song("id", "titolo", new Artist("rob", "rob", "rob"), new Playlist("id", "nomePlaylist", new Artist("rob", "rob", "rob")), new SerialBlob(new byte[] {0, 1}), new Time(100), new Date(100), new String[] {"rock", "pop"}, true));
      songList.add(new Song("id", "titolo", new Artist("rob", "rob", "rob"), new Playlist("id", "nomePlaylist", new Artist("rob", "rob", "rob")), new SerialBlob(new byte[] {0, 1}), new Time(100), new Date(100), new String[] {"rock", "pop"}, true));
    }catch(SQLException e){
      throw new RuntimeException(e);
    }
    TableView<JBAudio> audioTable=new AudioTable(songList);
    VBox mainVBox=new VBox(collectionHeader, audioTable);

    ScrollPane scrollPane=new ScrollPane(mainVBox);
    scrollPane.setFitToWidth(true);
    scrollPane.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    VBox scrollableScrollPane=new VBox(scrollPane);
    VBox.setVgrow(scrollPane, Priority.ALWAYS);

    /* Setup of left Sidebar, bottom songbar and center mainVBox */
    GridPane gp=new GridPane();
    gp.addRow(0, Sidebar.getInstance(), scrollableScrollPane);
    gp.add(Songbar.getInstance(), 0, 1, 2, 1);

    ColumnConstraints ccSidebar=new ColumnConstraints();
    ColumnConstraints ccHome=new ColumnConstraints();
    ccSidebar.setPercentWidth(20);
    ccHome.setPercentWidth(80);
    gp.getColumnConstraints().addAll(ccSidebar, ccHome);

    RowConstraints rcSongbar=new RowConstraints();
    RowConstraints rcSideHome=new RowConstraints();
    rcSongbar.setPercentHeight(12);
    rcSideHome.setPercentHeight(88);
    gp.getRowConstraints().addAll(rcSideHome, rcSongbar);

    scene=new Scene(gp, clientWidth, clientHeight);
  }
  /*---------------------------------------*/
}
