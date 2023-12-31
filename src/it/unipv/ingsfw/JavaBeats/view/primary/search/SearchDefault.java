package it.unipv.ingsfw.JavaBeats.view.primary.search;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class SearchDefault extends ScrollPane{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public SearchDefault(){
    initComponents();
  }
  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(){
    //Image
    Image searchImage = new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Search.png", true);
    ImageView searchImageView=new ImageView(searchImage);
    searchImageView.setPreserveRatio(true);
    //Label searchScrollPane
    Label results= new Label("The results will appear here.");
    results.setAlignment(Pos.CENTER);
    results.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 14));
    results.setTextFill(Color.LIGHTGRAY);

    HBox scrollPaneHbox= new HBox(searchImageView, results);
    HBox.setHgrow(scrollPaneHbox, Priority.ALWAYS);
    scrollPaneHbox.setAlignment(Pos.CENTER);

    Pane pane1=new Pane();
    Pane pane2=new Pane();
    VBox.setVgrow(pane1, Priority.ALWAYS);
    VBox.setVgrow(pane2, Priority.ALWAYS);

    VBox centerVBox=new VBox(pane1, scrollPaneHbox, pane2);

    setContent(centerVBox);
    VBox.setVgrow(this, Priority.ALWAYS);
    setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
    setFitToWidth(true);
    setFitToHeight(true);
  }
  /*---------------------------------------*/
}
