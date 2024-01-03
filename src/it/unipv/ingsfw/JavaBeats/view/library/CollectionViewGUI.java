package it.unipv.ingsfw.JavaBeats.view.library;

import it.unipv.ingsfw.JavaBeats.model.playable.EJBPLAYABLE;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioCard;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

public class CollectionViewGUI {

    /*---------------------------------------*/
    //Attributi
    /*---------------------------------------*/
    private static final int clientWidth=(int) Screen.getPrimary().getBounds().getWidth();
    private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
    private static final Background bg=new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY));
    private static final Font fontCollection=Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25);
    private Scene scene;


    /*---------------------------------------*/
    //Getter/Setter
    /*---------------------------------------*/
    public Scene getScene(){
        return scene;
    }

    public CollectionViewGUI(EJBPLAYABLE ejbplayable){
        initComponents(ejbplayable);
    }

    private void initComponents(EJBPLAYABLE ejbplayable) {

        //Label collection title

        Label collectionLabel=new Label();

        switch(ejbplayable) {
            case PLAYLIST:
                collectionLabel= new Label("Your playlists");
                break;
            case ALBUM:
                collectionLabel= new Label("Your albums");
                break;
            case PODCAST:
                collectionLabel= new Label("Your podcasts");
                break;
        }

        collectionLabel.setFont(fontCollection);
        collectionLabel.setTextFill(Color.LIGHTGRAY);
        collectionLabel.setAlignment(Pos.CENTER);
        collectionLabel.setPadding(new Insets(20));

        //Hbox title

        HBox titleHBox= new HBox(collectionLabel);

        //Flowpane

        FlowPane collectionFlowPane= new FlowPane();
        for(int i=0; i<100; i+=1){
            collectionFlowPane.getChildren().add(new AudioCard());
        }//end-for
        collectionFlowPane.setPrefWrapLength(Double.MAX_VALUE);
        collectionFlowPane.setHgap(50);
        collectionFlowPane.setVgap(70);


        //Scrollpane
        ScrollPane collectionScrollPane= new ScrollPane(collectionFlowPane);
        collectionScrollPane.setFitToWidth(true);
        collectionScrollPane.setFitToHeight(true);
        collectionScrollPane.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");


        //Add button
        Image plusImage= new Image("it/unipv/ingsfw/JavaBeats/view/icons/Plus.png", true);
        ImageView plusImageView=new ImageView(plusImage);
        plusImageView.setPreserveRatio(true);
        plusImageView.setFitHeight(40);
        Button buttonPlus= new Button();
        buttonPlus.setGraphic(plusImageView);
        buttonPlus.setCursor(Cursor.HAND);
        buttonPlus.setStyle("-fx-background-radius: 30; -fx-pref-width: 60; -fx-pref-height: 60; -fx-background-color: #8A2BE2");


        //Hbox button
        HBox buttonHBox= new HBox(buttonPlus);
        buttonHBox.setAlignment(Pos.CENTER_RIGHT);
        buttonHBox.setPadding(new Insets(0, 20, 10, 0));

        //Vbox collection
        VBox collectionVBox= new VBox(25, titleHBox, collectionScrollPane, buttonHBox);
        collectionVBox.setBackground(bg);
        VBox.setVgrow(collectionScrollPane, Priority.ALWAYS);

        /* Setup of left Sidebar, bottom songbar and center collection */
        Sidebar sidebar =Sidebar.getInstance();
        Songbar songbar =Songbar.getInstance();
        GridPane gp=new GridPane();
        gp.addRow(0, sidebar, collectionVBox);
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
