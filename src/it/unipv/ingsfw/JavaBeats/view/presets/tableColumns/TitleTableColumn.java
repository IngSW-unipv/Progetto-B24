package it.unipv.ingsfw.JavaBeats.view.presets.tableColumns;
import it.unipv.ingsfw.JavaBeats.model.playable.JBAudio;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
public class TitleTableColumn extends TableColumn<JBAudio, JBAudio>{
  public TitleTableColumn(String s){
    super(s);
    super.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue()));
    super.setCellFactory(column -> new TableCell<>(){
      private final HBox mainHBox;
      private final VBox titleArtistVBox;
      private final ImageView collectionImageView;
      private final Label titleLabel;
      private final Label artistLabel;

      /* Anonymous constructor: */{
        titleLabel=new Label();
        artistLabel=new Label();

        titleArtistVBox=new VBox(titleLabel, artistLabel);
        titleArtistVBox.setAlignment(Pos.CENTER_LEFT);

        collectionImageView=new ImageView();
        collectionImageView.setPreserveRatio(true);
        collectionImageView.setFitHeight(35);
        mainHBox=new HBox(10, collectionImageView, titleArtistVBox);
        mainHBox.setAlignment(Pos.CENTER_LEFT);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
      }

      @Override
      public void updateItem(JBAudio audio, boolean empty){
        if(audio==null){
          setGraphic(null);
        }else{
          titleLabel.setText(audio.getMetadata().getTitle());
          artistLabel.setText(audio.getMetadata().getArtist().getUsername());
          /* Once the DB is up and running we can do this like this, for now we'll use default image */
//          try{
//            collectionImageView=new ImageView(new Image(audio.getMetadata().getCollection().getPicture().getBinaryStream()));
//          }catch(SQLException e){
//            throw new RuntimeException(e);
//          }//end-try
          collectionImageView.setImage(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Record.png", true));
          setGraphic(mainHBox);
        }//end-if
      }
    });
  }
}
