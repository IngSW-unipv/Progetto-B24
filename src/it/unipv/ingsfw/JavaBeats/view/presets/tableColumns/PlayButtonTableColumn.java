package it.unipv.ingsfw.JavaBeats.view.presets.tableColumns;
import it.unipv.ingsfw.JavaBeats.model.playable.JBAudio;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
public class PlayButtonTableColumn extends TableColumn<JBAudio, JBAudio>{
  public PlayButtonTableColumn(String s){
    super(s);
    super.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
    super.setCellFactory(column -> new TableCell<>(){
      private final Button playButton;

      /* Anonymous constructor: */{
        playButton=new Button();
        ImageView playImage=new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Play.png", true));
        playImage.setPreserveRatio(true);
        playImage.setFitHeight(15);
        playButton.setGraphic(playImage);
        playButton.setStyle("-fx-background-color: transparent");
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
      }

      @Override
      public void updateItem(JBAudio audio, boolean empty){
        if(audio==null){
          setGraphic(null);
        }else{

          setGraphic(playButton);
        }//end-if
      }
    });
    setId("playColumn");
  }
}
