package it.unipv.ingsfw.JavaBeats.view.presets.tableColumns;
import it.unipv.ingsfw.JavaBeats.model.playable.JBAudio;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class FavoriteButtonTableColumn extends TableColumn<JBAudio, JBAudio>{
  /*-----------------------------------------------*/
  //Constructor
  /*-----------------------------------------------*/
  public FavoriteButtonTableColumn(String s){
    super(s);
    super.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
    super.setCellFactory(column -> new TableCell<>(){
      private final Button favoriteButton;

      /* Anonymous constructor: */{
        favoriteButton=new Button();
        favoriteButton.setStyle("-fx-background-color: transparent");
        favoriteButton.setCursor(Cursor.HAND);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
      }

      @Override
      public void updateItem(JBAudio audio, boolean empty){
        if(audio==null){
          setGraphic(null);
        }else{
          ImageView hearthImage=audio.isFavorite() ? new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/FullHeart.png", true)) : new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyHeart.png", true));
          hearthImage.setPreserveRatio(true);
          hearthImage.setFitHeight(20);
          favoriteButton.setGraphic(hearthImage);
          setGraphic(favoriteButton);
        }//end-if
      }
    });
    getStyleClass().add("favoriteColumn");
  }
  /*-----------------------------------------------*/
}
