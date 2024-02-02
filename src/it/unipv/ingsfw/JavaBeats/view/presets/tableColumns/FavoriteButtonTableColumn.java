package it.unipv.ingsfw.JavaBeats.view.presets.tableColumns;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class FavoriteButtonTableColumn extends TableColumn<JBAudio, JBAudio>{
  /*-----------------------------------------------*/
  //Constructor
  /*-----------------------------------------------*/
  public FavoriteButtonTableColumn(String s, JBProfile activeProfile){
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
          ImageView hearthImage=activeProfile.getFavorites().getTrackList().contains(audio) ? new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/FullHeart.png", true)) : new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyHeart.png", true));
          hearthImage.setPreserveRatio(true);
          hearthImage.setFitHeight(20);
          favoriteButton.setGraphic(hearthImage);
          favoriteButton.setTooltip(new Tooltip("Favorite"));
          setGraphic(favoriteButton);
        }//end-if
      }
    });
    getStyleClass().add("favoriteColumn");
  }
  /*-----------------------------------------------*/
}
