package it.unipv.ingsfw.JavaBeats.view.primary;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    private static final Background bgCard=new Background(new BackgroundFill(Color.rgb(25, 25, 25), new CornerRadii(20), Insets.EMPTY));

    public AudioCard(){
        super();
        initComponents();
    }

    private void initComponents(){

        //Image
        Image cardImage=new Image("it/unipv/ingsfw/JavaBeats/view/icons/Record.png", true);
        ImageView cardImageView=new ImageView(cardImage);
        cardImageView.setPreserveRatio(true);


        //LabelVbox

        //Title
        Label title= new Label("Unknown Title");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        title.setTextFill(Color.LIGHTGRAY);
        title.setPadding(new Insets(15, 0, 5, 0));

        //Artist
        Label creator= new Label("Unknown Artist");
        creator.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14));
        creator.setTextFill(Color.LIGHTGRAY);


        VBox labelVBox= new VBox(title, creator);
        labelVBox.setAlignment(Pos.CENTER_LEFT);

        getChildren().addAll(cardImageView, labelVBox);
        setPadding(new Insets(15));
        setAlignment(Pos.CENTER);
        setBackground(bgCard);


    }

}
