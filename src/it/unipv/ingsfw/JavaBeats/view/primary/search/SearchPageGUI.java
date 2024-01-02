package it.unipv.ingsfw.JavaBeats.view.primary.search;

import it.unipv.ingsfw.JavaBeats.view.primary.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.primary.Songbar;
import it.unipv.ingsfw.JavaBeats.view.primary.home.Home;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

import static javafx.scene.layout.GridPane.setVgrow;

public class SearchPageGUI {

    /*---------------------------------------*/
    //Attributi
    /*---------------------------------------*/
    private static final int clientWidth=(int) Screen.getPrimary().getBounds().getWidth();
    private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
    private static final Font fontUser=Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15);
    private static final Background bgSearchPage=new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY));
    private static final Background bgSearchBar=new Background(new BackgroundFill(Color.rgb(10, 10, 10), new CornerRadii(25), Insets.EMPTY));
    private static final Font fontSearchBar=Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 16);

    private Scene scene;
    /*---------------------------------------*/
    //Costruttori
    /*---------------------------------------*/
    public SearchPageGUI(){
        initComponents();
    }
    /*---------------------------------------*/
    //Getter/Setter
    /*---------------------------------------*/
    public Scene getScene(){
        return scene;
    }
    /*---------------------------------------*/
    //Metodi
    /*---------------------------------------*/
    private void initComponents(){

        //Search

        //Hbox searchbar


        //Search textfield
        TextField searchTextField= new TextField();
        searchTextField.setPrefSize(500, 50);
        searchTextField.setFont(fontSearchBar);
        searchTextField.setBackground(bgSearchBar);
        searchTextField.setStyle("-fx-text-fill: #ffffffff; -fx-prompt-text-fill: #dededeaa;");
        searchTextField.setPromptText("Search here");


        /* Pane for spacing */
        Pane whitePane=new Pane();
        HBox.setHgrow(whitePane, Priority.ALWAYS);

        /* Button with user's profile picture and username */
        Image userPic = new Image("it/unipv/ingsfw/JavaBeats/view/icons/DefaultUser.png", true);
        ImageView userPicImageView=new ImageView(userPic);
        userPicImageView.setPreserveRatio(true);
        Button userProfileButton=new Button("Username");
        userProfileButton.setBackground(bgSearchPage);
        userProfileButton.setGraphic(userPicImageView);
        userProfileButton.setCursor(Cursor.HAND);
        userProfileButton.setFont(fontUser);
        userProfileButton.setTextFill(Color.LIGHTGRAY);
        userProfileButton.setAlignment(Pos.CENTER_RIGHT);



        HBox searchBar= new HBox(20, searchTextField, whitePane, userProfileButton);
        searchBar.setPadding(new Insets(10, 0, 10, 5));
        searchBar.setAlignment(Pos.CENTER_LEFT);

        //Scrollpane Search

        //Label searchScrollPane
        Label results= new Label("The results will appear here.");
        results.setAlignment(Pos.CENTER);
        results.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 14));
        results.setTextFill(Color.LIGHTGRAY);

        //Image
        Image searchImage = new Image("it/unipv/ingsfw/JavaBeats/view/icons/Search.png", true);
        ImageView searchImageView=new ImageView(searchImage);
        searchImageView.setPreserveRatio(true);


        HBox scrollPaneHbox= new HBox(searchImageView, results);
        HBox.setHgrow(scrollPaneHbox, Priority.ALWAYS);
        scrollPaneHbox.setAlignment(Pos.CENTER);

        Pane pane1=new Pane();
        Pane pane2=new Pane();
        VBox.setVgrow(pane1, Priority.ALWAYS);
        VBox.setVgrow(pane2, Priority.ALWAYS);

        VBox centerVBox=new VBox(pane1, scrollPaneHbox, pane2);

        ScrollPane searchScrollPane= new ScrollPane(centerVBox);
        searchScrollPane.setStyle("-fx-background: #0F0F0FFF; -fx-border-color: #0F0F0FFF");
        VBox.setVgrow(searchScrollPane, Priority.ALWAYS);
        searchScrollPane.setFitToWidth(true);
        searchScrollPane.setFitToHeight(true);

        //Vbox Search
        VBox searchVBox= new VBox(searchBar, searchScrollPane);
        searchVBox.setBackground(bgSearchPage);













        /* Setup of left Sidebar, bottom songbar and center Search */
        Sidebar sidebar=new Sidebar();
        Songbar songbar= new Songbar();

        GridPane gp=new GridPane();
        gp.addRow(0, sidebar, searchVBox);
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
    /*---------------------------------------*/
}






