package it.unipv.ingsfw.JavaBeats.view.presets.tableColumns;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
public class PlayButtonTableColumn extends TableColumn<JBAudio, JBAudio>{
  /*-----------------------------------------------*/
  //Constructor
  /*-----------------------------------------------*/
  public PlayButtonTableColumn(String s){
    super(s);
    super.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
    super.setCellFactory(column -> new TableCell<>(){
      private final HBox mainHBox;
      private final Button playButton;

      /* Anonymous constructor: */{
        playButton=new Button();
        ImageView playImage=new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Play.png", true));
        playImage.setPreserveRatio(true);
        playImage.setFitHeight(15);
        playButton.setGraphic(playImage);
        playButton.setStyle("-fx-background-color: transparent");
        //playButton.setCursor(Cursor.HAND);

        mainHBox=new HBox(playButton);
        mainHBox.setAlignment(Pos.CENTER);
        mainHBox.setCursor(Cursor.HAND);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
      }

      @Override
      public void updateItem(JBAudio audio, boolean empty){
        if(audio==null){
          setGraphic(null);
        }else{
          setGraphic(mainHBox);
        }//end-if
      }
    });
    getStyleClass().add("playColumn");
  }
  /*-----------------------------------------------*/
}
