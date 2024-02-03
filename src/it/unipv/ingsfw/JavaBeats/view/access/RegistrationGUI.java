package it.unipv.ingsfw.JavaBeats.view.access;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

public class RegistrationGUI {
    /*---------------------------------------*/
    //Attributi
    /*---------------------------------------*/
    private static final int clientWidth = (int) Screen.getPrimary().getBounds().getWidth();
    private static final int clientHeight = (int) Screen.getPrimary().getBounds().getHeight();
    private static final int vboxWidth = 640;
    private static final int vboxHeight = 800;
    private static final Background bgVBox = new Background(new BackgroundFill(Color.rgb(25, 25, 25), new CornerRadii(25), Insets.EMPTY));
    private static final Background bgTextField = new Background(new BackgroundFill(Color.WHITE, new CornerRadii(25), Insets.EMPTY));
    private static final Border borderTextField = new Border(new BorderStroke(Color.BLUEVIOLET, BorderStrokeStyle.SOLID, new CornerRadii(25), new BorderWidths(3)));
    private static final Font fontTextField = Font.font("Verdana", FontWeight.EXTRA_LIGHT, FontPosture.REGULAR, 17);
    private Scene scene;
    private TextField username, name, surname, mail;
    private PasswordField password1, password2;
    private Label confirmPassword, errorMessage;
    private Button login;
    private Button register;

    /*---------------------------------------*/
    //Costruttore
    /*---------------------------------------*/
    public RegistrationGUI() {
        initComponents();
    }

    /*---------------------------------------*/
    //Getter/Setter
    /*---------------------------------------*/
    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public TextField getUsername() {
        return username;
    }

    public void setUsername(TextField username) {
        this.username = username;
    }

    public TextField getSurname() {
        return surname;
    }

    public void setSurname(TextField surname) {
        this.surname = surname;
    }

    public TextField getName() {
        return name;
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public TextField getMail() {
        return mail;
    }

    public void setMail(TextField mail) {
        this.mail = mail;
    }

    public PasswordField getPassword1() {
        return password1;
    }

    public void setPassword1(PasswordField password1) {
        this.password1 = password1;
    }

    public PasswordField getPassword2() {
        return password2;
    }

    public void setPassword2(PasswordField password2) {
        this.password2 = password2;
    }

    public Label getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(Label confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Label getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Label errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Button getLogin() {
        return login;
    }

    public void setLogin(Button login) {
        this.login = login;
    }

    public Button getRegister() {
        return register;
    }

    public void setRegister(Button register) {
        this.register = register;
    }

    /*---------------------------------------*/
    //Metodi
    /*---------------------------------------*/
    private void initComponents() {
        /* Setup of Logo, App title in a HBox Layout */
        Image image = new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/Logo.png", true);
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        Label title = new Label("JavaBeats");
        title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 40));
        title.setTextFill(Color.WHITE);
        title.setPadding(new Insets(5, 0, 0, 0));
        HBox logoTitleHBox = new HBox(20, imageView, title);

        /* Setup of Username and Password TextFields as well as erroreMessage Label */
        name = new TextField();
        surname = new TextField();
        mail = new TextField();
        username = new TextField();
        password1 = new PasswordField();
        password2 = new PasswordField();

        TextField[] tf = {name, surname, mail, username, password1, password2};
        for (TextField t : tf) {
            t.setPrefSize(300, 50);
            t.setBorder(borderTextField);
            t.setBackground(bgTextField);
            t.setFont(fontTextField);
            t.setStyle("-fx-text-fill: #7a7a7a;");
        }//end-for

        for (int i = 0; i < tf.length; i += 1) {
            for (int j = 0; j < tf.length; j += 1) {
                if (i != j) {
                    tf[i].styleProperty().bind(Bindings.when(tf[j].focusedProperty()).then("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);").otherwise("-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);"));
                }//end-if
            }//end-for
        }//end-for

        name.setPromptText("Name");
        surname.setPromptText("Surname");
        mail.setPromptText("Mail");
        username.setPromptText("Username");
        password1.setPromptText("Password");
        password2.setPromptText("Password");

        confirmPassword = new Label("Confirm password");
        confirmPassword.setTextFill(Color.WHITE);

        errorMessage = new Label("");
        errorMessage.setTextFill(Color.RED);

        /* Setup of Login or Register Buttons, two different styles: rounded violet for login and clickable link for register */
        register = new Button("Register");
        register.setCursor(Cursor.HAND);
        register.setPrefSize(300, 50);
        register.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, new CornerRadii(25), Insets.EMPTY)));
        register.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        register.setTextFill(Color.WHITE);

        login = new Button("You already have an account? Login");
        login.setBackground(bgVBox);
        login.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 15));
        login.setTextFill(Color.WHITE);
        login.setUnderline(true);
        login.setCursor(Cursor.HAND);

        /* Set up of the main VBox containing all the elements for the login page */
        VBox v = new VBox(logoTitleHBox, name, surname, mail, username, password1, confirmPassword, password2, errorMessage, register, login);
        v.setPrefSize(vboxWidth, vboxHeight);
        v.setFillWidth(false);
        v.setAlignment(Pos.CENTER);
        v.setBackground(bgVBox);
        VBox.setMargin(logoTitleHBox, new Insets(0, 0, 50, 0));
        VBox.setMargin(name, new Insets(0, 0, 15, 0));
        VBox.setMargin(surname, new Insets(0, 0, 15, 0));
        VBox.setMargin(mail, new Insets(0, 0, 15, 0));
        VBox.setMargin(username, new Insets(0, 0, 15, 0));
        VBox.setMargin(password1, new Insets(0, 0, 20, 0));
        VBox.setMargin(confirmPassword, new Insets(0, 0, 15, 0));
        VBox.setMargin(password2, new Insets(0, 0, 15, 0));
        VBox.setMargin(errorMessage, new Insets(0, 0, 15, 0));
        VBox.setMargin(register, new Insets(0, 0, 30, 0));

        /* SetUp of the background GridPane that "holds" the VBox in the middle of the screen */
        GridPane gp = new GridPane();
        gp.add(v, 1, 1);
        gp.setBackground(new Background(new BackgroundFill(Color.rgb(15, 15, 15), CornerRadii.EMPTY, Insets.EMPTY)));

        ColumnConstraints columnVBox = new ColumnConstraints();
        ColumnConstraints columnEmpty = new ColumnConstraints();
        columnVBox.setPercentWidth((double) (100 * vboxWidth) / clientWidth);
        columnEmpty.setPercentWidth((100 - (double) (100 * vboxWidth) / clientWidth) / 2);
        gp.getColumnConstraints().add(columnEmpty);
        gp.getColumnConstraints().add(columnVBox);
        gp.getColumnConstraints().add(columnEmpty);

        RowConstraints rowEmpty = new RowConstraints();
        RowConstraints rowVBox = new RowConstraints();
        rowVBox.setPercentHeight((double) (100 * vboxHeight) / clientHeight);
        rowEmpty.setPercentHeight((100 - (double) (100 * vboxHeight) / clientHeight) / 2);
        gp.getRowConstraints().add(rowEmpty);
        gp.getRowConstraints().add(rowVBox);
        gp.getRowConstraints().add(rowEmpty);

        scene = new Scene(gp, clientWidth, clientHeight);
    }
    /*---------------------------------------*/
}