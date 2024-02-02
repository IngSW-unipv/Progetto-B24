package it.unipv.ingsfw.JavaBeats.view.presets.tableColumns;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class FavoriteButtonTableColumn extends TableColumn<JBAudio, JBAudio>{
  /*-----------------------------------------------*/
  //Constructor
  /*-----------------------------------------------*/
  public FavoriteButtonTableColumn(String s, JBProfile activeProfile){
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
          ImageView hearthImage=activeProfile.getFavorites().getTrackList().contains(audio) ? new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/FullHeart.png", true)) : new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyHeart.png", true));
          hearthImage.setPreserveRatio(true);
          hearthImage.setFitHeight(20);
          mainHBox.getChildren().add(hearthImage);

          setGraphic(mainHBox);
        }//end-if
      }
    });
    getStyleClass().add("favoriteColumn");
    setId("favoriteButton");
  }
  /*-----------------------------------------------*/
}