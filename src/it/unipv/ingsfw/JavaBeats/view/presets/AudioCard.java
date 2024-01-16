package it.unipv.ingsfw.JavaBeats.view.presets;

import it.unipv.ingsfw.JavaBeats.model.IJBResearchable;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.playable.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.sql.SQLException;

public class AudioCard extends VBox{

  //Attributi
  private IJBResearchable ijbResearchable;

  private static final Background bgCard=new Background(new BackgroundFill(Color.rgb(25, 25, 25), new CornerRadii(20), Insets.EMPTY));

  public AudioCard(IJBResearchable ijbResearchable){
    super();
    this.ijbResearchable=ijbResearchable;
    initComponents();
  }

  public IJBResearchable getIjbResearchable(){
    return ijbResearchable;
  }
  private void initComponents(){

    ImageView cardImageView=null;
    Label title=null;
    Label creator=null;
    try{
      JBProfile jbProfile=(JBProfile)ijbResearchable;
      try{
        cardImageView=new ImageView(new Image(jbProfile.getProfilePicture().getBinaryStream()));
        title=new Label(jbProfile.getUsername());
        creator=new Label();
      }catch(SQLException e){
        throw new RuntimeException(e);
      }//end-try
    }catch(ClassCastException c){
      try{
        JBCollection jbCollection=(JBCollection)ijbResearchable;
        try{
          cardImageView=new ImageView(new Image(jbCollection.getPicture().getBinaryStream()));
          title=new Label(jbCollection.getName());
          creator=new Label(jbCollection.getCreator().getUsername());
        }catch(SQLException e){
          throw new RuntimeException(e);
        }//end-try
      }catch(ClassCastException c1){
        JBAudio jbAudio=(JBAudio)ijbResearchable;
        try{
          cardImageView=new ImageView(new Image(jbAudio.getMetadata().getCollection().getPicture().getBinaryStream()));
          title=new Label(jbAudio.getMetadata().getTitle());
          creator=new Label(jbAudio.getMetadata().getArtist().getUsername());
        }catch(SQLException e){
          throw new RuntimeException(e);
        }//end-try
      }//end-try
    }//end-try

    //Image
    cardImageView.setPreserveRatio(true);

    //LabelVbox

    //Title
    title.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
    title.setTextFill(Color.LIGHTGRAY);
    title.setPadding(new Insets(15, 0, 5, 0));

    //Artist
    creator.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
    creator.setTextFill(Color.LIGHTGRAY);


    VBox labelVBox=new VBox(title, creator);
    labelVBox.setAlignment(Pos.CENTER_LEFT);

    getChildren().addAll(cardImageView, labelVBox);
    setPadding(new Insets(15));
    setAlignment(Pos.CENTER);
    setBackground(bgCard);
    setCursor(Cursor.HAND);
  }
}
