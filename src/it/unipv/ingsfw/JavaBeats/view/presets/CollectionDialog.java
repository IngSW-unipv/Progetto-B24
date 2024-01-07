package it.unipv.ingsfw.JavaBeats.view.presets;
import it.unipv.ingsfw.JavaBeats.model.playable.JBCollection;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CollectionDialog extends Dialog<JBCollection>{
  /*---------------------------------------*/
  //Attributi
  /*---------------------------------------*/
  private static final Background bgHome=new Background(new BackgroundFill(Color.rgb(18, 18, 18), CornerRadii.EMPTY, Insets.EMPTY));
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
  private JBCollection originalCollection;

  /*---------------------------------------*/
  //Costruttori
  /*---------------------------------------*/
  public CollectionDialog(Stage stage, JBCollection originalCollection){
    super();
    this.originalCollection=originalCollection;
    initOwner(stage);
    initStyle(StageStyle.UNDECORATED);
    initComponents();
  }

  /*---------------------------------------*/
  //Getter/Setter
  /*---------------------------------------*/
  public JBCollection getOriginalCollection(){
    return originalCollection;
  }

  /*---------------------------------------*/
  //Metodi
  /*---------------------------------------*/
  private void initComponents(){
    Label editLabel=new Label("Edit details");

    Pane whitePane=new Pane();

    Image editImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Edit.png", true);
    ImageView editImageView=new ImageView(editImage);
    editImageView.setPreserveRatio(true);
    Button editButton=new Button();
    editButton.setGraphic(editImageView);
    editButton.setStyle("-fx-background-color: #0F0F0FFF;");
    editButton.setCursor(Cursor.HAND);
    editButton.setTooltip(new Tooltip("Edit"));

    HBox editLabelCloseButtonHBox=new HBox(editLabel, whitePane, editButton);
    HBox.setHgrow(whitePane, Priority.ALWAYS);

    VBox mainContent=new VBox(editLabelCloseButtonHBox);
    mainContent.setBackground(bgHome);
    
    setGraphic(mainContent);
    this.setWidth((double)clientWidth/4);
    this.setHeight((double)clientHeight/3);
  }
  /*---------------------------------------*/
}
