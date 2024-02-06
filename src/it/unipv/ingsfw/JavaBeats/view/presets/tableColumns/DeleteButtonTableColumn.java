package it.unipv.ingsfw.JavaBeats.view.presets.tableColumns;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
public class DeleteButtonTableColumn extends TableColumn<JBAudio, JBAudio>{
  /*-----------------------------------------------*/
  //Constructor
  /*-----------------------------------------------*/
  public DeleteButtonTableColumn(String s){
    super(s);
    super.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
    super.setCellFactory(column -> new TableCell<>(){
      private final HBox mainHBox;

      /* Anonymous constructor: */{
        mainHBox=new HBox();
        mainHBox.setAlignment(Pos.CENTER);
        mainHBox.setCursor(Cursor.HAND);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
      }

      @Override
      public void updateItem(JBAudio audio, boolean empty){
        if(audio==null){
          setGraphic(null);
        }else{
          ImageView binImageView=new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Bin.png", true));
          binImageView.setPreserveRatio(true);
          binImageView.setFitHeight(20);
          mainHBox.getChildren().add(binImageView);
          setTooltip(new Tooltip("Delete"));

          setGraphic(mainHBox);
        }//end-if
      }
    });
    getStyleClass().add("favoriteColumn");
    setId("binButton");
  }
  /*-----------------------------------------------*/
}