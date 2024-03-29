package it.unipv.ingsfw.JavaBeats.view.presets.tableColumns;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
public class TitleTableColumn extends TableColumn<JBAudio, JBAudio>{
  /*-----------------------------------------------*/
  //Constructor
  /*-----------------------------------------------*/
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
        titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
        titleLabel.setTextFill(Color.LIGHTGRAY);

        artistLabel=new Label();
        artistLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 12));
        artistLabel.setTextFill(Color.LIGHTGRAY);

        titleArtistVBox=new VBox(5, titleLabel, artistLabel);
        titleArtistVBox.setAlignment(Pos.CENTER_LEFT);

        collectionImageView=new ImageView();

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
          collectionImageView.setImage(audio.getMetadata().getCollection().scalePicture(35));

          setGraphic(mainHBox);
        }//end-if
      }
    });
  }
  /*-----------------------------------------------*/
}
