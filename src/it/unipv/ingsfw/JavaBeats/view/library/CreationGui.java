package it.unipv.ingsfw.JavaBeats.view.library;

import it.unipv.ingsfw.JavaBeats.model.playable.EJBPLAYABLE;
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
import javafx.stage.Screen;


public class CreationGui {

    //Attributi
    private static final int clientWidth=(int) Screen.getPrimary().getBounds().getWidth();
    private static final int clientHeight=(int)Screen.getPrimary().getBounds().getHeight();
    private static final Background bg=new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY));
    private static final Background bgTitle=new Background(new BackgroundFill(Color.rgb(10, 10, 10), new CornerRadii(25), Insets.EMPTY));
    private static final Font fontTitle=Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 90);
    private static final Font fontAdd=Font.font("Verdana", FontWeight.LIGHT, FontPosture.ITALIC, 20);

    private Scene scene;


    //Getters and setters


    public Scene getScene() {
        return scene;
    }

    public CreationGui(EJBPLAYABLE ejbplayable){
        initComponents(ejbplayable);
    }

    private void initComponents(EJBPLAYABLE ejbplayable){

        //HBox Title

        //ImageButton
        Image collectionImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/RecordBig.png", true);
        ImageView collectionImageView=new ImageView(collectionImage);
        collectionImageView.setPreserveRatio(true);
        collectionImageView.setFitHeight(250);
        collectionImageView.setEffect(new BoxBlur(5, 5,5));
        Button collectionButton= new Button();
        collectionButton.setGraphic(collectionImageView);
        collectionButton.setTooltip(new Tooltip("Add Image"));
        collectionButton.setCursor(Cursor.HAND);
        collectionButton.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        //Title TextField
        TextField titleTextField= new TextField();
        titleTextField.setPrefSize(1200, 50);
        titleTextField.setFont(fontTitle);
        titleTextField.setBackground(bgTitle);
        titleTextField.setStyle("-fx-text-fill: #ffffffff; -fx-prompt-text-fill: #dededeaa;");
        titleTextField.setPromptText("Insert title");
        HBox textFieldHBox= new HBox(titleTextField);
        textFieldHBox.setPadding(new Insets(100, 0, 0, 10));

        HBox titleHbox= new HBox(collectionButton, textFieldHBox);
        titleHbox.setAlignment(Pos.TOP_LEFT);


        //Add button
        Image plusImage=new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Plus.png", true);
        ImageView plusImageView=new ImageView(plusImage);
        plusImageView.setPreserveRatio(true);
        plusImageView.setFitHeight(40);
        Button buttonPlus=new Button();
        buttonPlus.setGraphic(plusImageView);
        buttonPlus.setCursor(Cursor.HAND);
        buttonPlus.setStyle("-fx-background-radius: 30; -fx-pref-width: 60; -fx-pref-height: 60; -fx-background-color: #8A2BE2");

        //Add label
        Label addLabel=null;
        switch (ejbplayable){
            case ALBUM:
                addLabel=new Label("Add song");
                break;
            case PODCAST:
                addLabel=new Label("Add episode");
                break;
        }

        addLabel.setFont(fontAdd);
        addLabel.setTextFill(Color.LIGHTGRAY);
        addLabel.setPadding(new Insets(0, 10, 0, 0));


        //Add Hbox
        HBox addHBox= new HBox(addLabel, buttonPlus);
        addHBox.setAlignment(Pos.CENTER);


        //Save Button
        Button create=null;
        switch (ejbplayable){
            case ALBUM:
                create=new Button("Click here to create your album!");
                break;
            case PODCAST:
                create=new Button("Click here to create your podcast!");
                break;
        }

        create.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        create.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, FontPosture.ITALIC, 18));
        create.setTextFill(Color.WHITE);
        create.setUnderline(true);
        create.setCursor(Cursor.HAND);

        HBox createHBox= new HBox(create);
        createHBox.setAlignment(Pos.CENTER);

        //VBoxCreationVBox
        VBox creationVBox= new VBox(180, titleHbox, addHBox, createHBox);
        creationVBox.setBackground(bg);


        //Scene
        Sidebar sidebar=Sidebar.getInstance();
        Songbar songbar=Songbar.getInstance();
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

