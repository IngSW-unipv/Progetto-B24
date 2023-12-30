package it.unipv.ingsfw.JavaBeats.view.primary;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


public class Songbar extends HBox {

    //Attributi
    private static final Background bgSongbar=new Background(new BackgroundFill(Color.rgb(18, 18, 18), CornerRadii.EMPTY, Insets.EMPTY));



    public Songbar(){
        super();
        initComponents();
    }


    private void initComponents(){

        //SongBar: SongBox, PlayBox, VolumeBox

        //SongHbox

        //RecordImage
        Image recordImage= new Image("it/unipv/ingsfw/JavaBeats/view/icons/Record.png", true);
        ImageView recordImageView=new ImageView(recordImage);
        recordImageView.setPreserveRatio(true);


        //Vbox
        Label songTitle= new Label("Unknown Title");
        songTitle.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        songTitle.setTextFill(Color.LIGHTGRAY);

        Label songArtist= new Label("Unknown Artist");
        songArtist.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        songArtist.setTextFill(Color.LIGHTGRAY);

        VBox songLabelVbox= new VBox(5, songTitle, songArtist);
        songLabelVbox.setAlignment(Pos.CENTER_LEFT);

        //Heart
        Image heartImage= new Image("it/unipv/ingsfw/JavaBeats/view/icons/EmptyHeart.png", true);
        ImageView heartImageView=new ImageView(heartImage);
        heartImageView.setPreserveRatio(true);
        Button buttonHeart= new Button();
        buttonHeart.setGraphic(heartImageView);
        buttonHeart.setBackground(bgSongbar);
        buttonHeart.setCursor(Cursor.HAND);

        HBox songHbox= new HBox(20, recordImageView, songLabelVbox, buttonHeart);
        songHbox.setAlignment(Pos.CENTER_LEFT);
        songHbox.setPadding(new Insets(5));


        //Pane 1
        Pane pane1= new Pane();
        setHgrow(pane1, Priority.ALWAYS);


        //PlayVbox

        //Hbox 1

        //Random
        Image randomImage= new Image("it/unipv/ingsfw/JavaBeats/view/icons/EmptyRandom.png", true);
        ImageView randomImageView=new ImageView(randomImage);
        randomImageView.setPreserveRatio(true);

        HBox playHbox= new HBox(randomImageView);
        playHbox.setAlignment(Pos.CENTER);

        //Hbox 2

        HBox sliderHbox= new HBox();
        sliderHbox.setAlignment(Pos.CENTER);

        VBox playVbox= new VBox(playHbox, sliderHbox);


        //Pane 2

        Pane pane2= new Pane();
        setHgrow(pane2, Priority.ALWAYS);













        getChildren().addAll(songHbox,pane1, playVbox, pane2);
        setBackground(bgSongbar);


    }


}
