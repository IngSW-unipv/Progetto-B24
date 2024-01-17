package it.unipv.ingsfw.JavaBeats.view.library;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.sql.SQLException;
public class CreationGUI{

  //Attributi
  private static final int clientWidth=(int)Screen.getPrimary().getBounds().getWidth();
  private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
  private static final Background bg=new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY));
  private static final Background bgTitle=new Background(new BackgroundFill(Color.rgb(10, 10, 10), new CornerRadii(25), Insets.EMPTY));
  private static final Font fontTitle=Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 90);
  private static final Font fontAdd=Font.font("Verdana", FontWeight.LIGHT, FontPosture.ITALIC, 20);
  private JBCollection newCollection;
  private ImageView collectionImageView;
  private Button collectionPictureButton;
  private TextField nameTextField;
  private Button addButton;
  private Button create;
  private Scene scene;

  //Getters and setters
  public Scene getScene(){
    return scene;
  }
  public JBCollection getNewCollection(){
    return newCollection;
  }
  public ImageView getCollectionImageView(){
    return collectionImageView;
  }
  public Button getCollectionPictureButton(){
    return collectionPictureButton;
  }
  public TextField getNameTextField(){
    return nameTextField;
  }
  public Button getAddButton(){
    return addButton;
  }
  public Button getCreate(){
    return create;
  }
  public CreationGUI(JBProfile activeProfile, JBCollection jbCollection){
    initComponents(activeProfile, jbCollection);
  }

  //Methods
  private void initComponents(JBProfile activeProfile, JBCollection jbCollection){

    //HBox Title

    //ImageButton
    try{
      collectionImageView=new ImageView(new Image(jbCollection.getPicture().getBinaryStream()));
    }catch(SQLException e){
      throw new RuntimeException(e);
    }//end-try
    collectionImageView.setPreserveRatio(true);
    collectionImageView.setFitHeight(250);
    collectionImageView.setEffect(new BoxBlur(5, 5, 5));
    collectionPictureButton=new Button();
    collectionPictureButton.setGraphic(collectionImageView);
    collectionPictureButton.setTooltip(new Tooltip("Add Image"));
    collectionPictureButton.setCursor(Cursor.HAND);
    collectionPictureButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

    //Title TextField
    nameTextField=new TextField(jbCollection.getName());
    nameTextField.setPrefSize(1200, 50);
    nameTextField.setFont(fontTitle);
    nameTextField.setBackground(bgTitle);
    nameTextField.setStyle("-fx-text-fill: #FFFFFFFF; -fx-prompt-text-fill: #DEDEDEAA;");
    nameTextField.setPromptText("Insert title");

    HBox textFieldHBox=new HBox(nameTextField);
    textFieldHBox.setPadding(new Insets(100, 0, 0, 10));

    HBox titleHbox=new HBox(collectionPictureButton, textFieldHBox);
    titleHbox.setAlignment(Pos.TOP_LEFT);
    titleHbox.setPadding(new Insets(0, 0, 0, 10));

    //Add button
    Image plusImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Plus.png", true);
    ImageView plusImageView=new ImageView(plusImage);
    plusImageView.setPreserveRatio(true);
    plusImageView.setFitHeight(40);
    addButton=new Button();
    addButton.setGraphic(plusImageView);
    addButton.setCursor(Cursor.HAND);
    addButton.setStyle("-fx-background-radius: 30; -fx-pref-width: 60; -fx-pref-height: 60; -fx-background-color: #8A2BE2");

    //Add label
    Label addLabel=null;
    try{
      Album a=(Album)jbCollection;
      addLabel=new Label("Add song");
    }catch(ClassCastException c){
      addLabel=new Label("Add episode");
    }//end-try
    addLabel.setFont(fontAdd);
    addLabel.setTextFill(Color.LIGHTGRAY);
    addLabel.setPadding(new Insets(0, 10, 0, 0));

    //Add Hbox
    HBox addHBox=new HBox(addLabel, addButton);
    addHBox.setAlignment(Pos.CENTER);

    //Save Button
    create=null;
    try{
      Album a=(Album)jbCollection;
      create=new Button("Click here to create your album!");
    }catch(ClassCastException c){
      create=new Button("Click here to create your podcast!");
    }//end-try
    create.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
    create.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, FontPosture.ITALIC, 18));
    create.setTextFill(Color.WHITE);
    create.setUnderline(true);
    create.setCursor(Cursor.HAND);

    HBox createHBox=new HBox(create);
    createHBox.setAlignment(Pos.CENTER);

    //VBoxCreationVBox
    VBox creationVBox=new VBox(180, titleHbox, addHBox, createHBox);
    creationVBox.setBackground(bg);

    //Scene
    Sidebar sidebar=Sidebar.getInstance(activeProfile);
    Songbar songbar=Songbar.getInstance(activeProfile);
    GridPane gp=new GridPane();
    gp.addRow(0, sidebar, creationVBox);
    gp.add(songbar, 0, 1, 2, 1);

    ColumnConstraints ccSidebar=new ColumnConstraints();
    ColumnConstraints ccHome=new ColumnConstraints();
    ccSidebar.setPercentWidth(20);
    ccHome.setPercentWidth(80);
    gp.getColumnConstraints().addAll(ccSidebar, ccHome);

    RowConstraints rcSongbar=new RowConstraints();
    RowConstraints rcSideHome=new RowConstraints();
    rcSongbar.setPercentHeight(12);
    rcSideHome.setPercentHeight(88);
    gp.getRowConstraints().addAll(rcSideHome, rcSongbar);

    scene=new Scene(gp, clientWidth, clientHeight);
  }
}

