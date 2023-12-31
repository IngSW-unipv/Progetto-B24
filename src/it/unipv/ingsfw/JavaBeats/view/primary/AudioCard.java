package it.unipv.ingsfw.JavaBeats.view.primary;

import javafx.geometry.Insets;
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

public class AudioCard extends VBox {

    //Attributi

    private static final Background bgSidebar=new Background(new BackgroundFill(Color.rgb(14, 14, 14), CornerRadii.EMPTY, Insets.EMPTY));

    public AudioCard(){
        super();
        initComponents();
    }

    private void initComponents(){

        //Image
        Image audioImage=new Image("it/unipv/ingsfw/JavaBeats/view/icons/Record.png", true);
        ImageView audioImageView=new ImageView(audioImage);
        audioImageView.setPreserveRatio(true);

        //Title
        Label songTitle= new Label("Unknown Title");
        songTitle.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        songTitle.setTextFill(Color.LIGHTGRAY);

        //Artist
        Label songArtist= new Label("Unknown Artist");
        songArtist.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        songArtist.setTextFill(Color.LIGHTGRAY);

        getChildren().addAll(audioImageView);
        setPadding(new Insets(15));

    }

}
