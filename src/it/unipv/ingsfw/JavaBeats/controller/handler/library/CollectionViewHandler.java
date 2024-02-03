package it.unipv.ingsfw.JavaBeats.controller.handler.library;

import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.factory.PlayerManagerFactory;
import it.unipv.ingsfw.JavaBeats.controller.handler.primary.home.HomePageHandler;
import it.unipv.ingsfw.JavaBeats.controller.handler.presets.AudioTableHandler;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.collection.Podcast;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.Episode;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.profile.Artist;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionViewGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.Sidebar;
import it.unipv.ingsfw.JavaBeats.view.presets.Songbar;
import it.unipv.ingsfw.JavaBeats.view.presets.dialogs.EditPlaylistDialog;
import it.unipv.ingsfw.JavaBeats.view.primary.home.HomePageGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class CollectionViewHandler {
    /*---------------------------------------*/
    //Attributi
    /*---------------------------------------*/
    private CollectionViewGUI gui;

    /*---------------------------------------*/
    //Costruttori
    /*---------------------------------------*/
    public CollectionViewHandler(CollectionViewGUI gui, JBProfile activeProfile, JBAudio currentAudio) {
        this.gui = gui;
        initComponents(activeProfile, currentAudio);
    }
    /*---------------------------------------*/
    //Getter/Setter
    /*---------------------------------------*/

    /*---------------------------------------*/
    //Metodi
    /*---------------------------------------*/
    private void initComponents(JBProfile activeProfile, JBAudio currentAudio) {
        EventHandler<ActionEvent> editButtonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                gui.getGp().setEffect(new BoxBlur(10, 10, 10));

                Playlist p = (Playlist) gui.getJbCollection();
                EditPlaylistDialog dialog = new EditPlaylistDialog(stage, p, (Playlist) p.getCopy());
                EditPlaylistDialogController editPlaylistDialogController = new EditPlaylistDialogController(dialog);
                dialog.showAndWait();
                gui.getGp().setEffect(null);
            }
        };
        EventHandler<ActionEvent> playPauseCollectionButtonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                PlayerManagerFactory.getInstance().getPlayerManager().play(gui.getJbCollection());
                PlayerManagerFactory.getInstance().getPlayerManager().setRandomized(false);
                gui.getCollectionHeader().getButtonRandom().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyRandom.png", true)));
                Songbar.getInstance().getButtonRandom().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/EmptyRandom.png", true)));

                stage.setScene(gui.update(activeProfile, PlayerManagerFactory.getInstance().getPlayerManager().getCurrentAudioPlaying()));
            }
        };
        EventHandler<ActionEvent> randomButtonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (gui.getJbCollection() != null) {
                    PlayerManagerFactory.getInstance().getPlayerManager().randomize(gui.getJbCollection());
                    gui.getCollectionHeader().getButtonRandom().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/FullRandom.png", true)));
                    Songbar.getInstance().getButtonRandom().setGraphic(new ImageView(new Image("it/unipv/ingsfw/JavaBeats/view/resources/icons/FullRandom.png", true)));
                }
            }
        };
        EventHandler<ActionEvent> binButtonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                if (gui.getJbCollection() == null) {

                    PlayerManagerFactory.getInstance().getPlayerManager().deleteQueue();

                } else {
                    
                    CollectionManagerFactory.getInstance().getCollectionManager().removeCollection(gui.getJbCollection());
                    HomePageGUI homePageGUI = new HomePageGUI(activeProfile, currentAudio);
                    HomePageHandler homePageHandler = new HomePageHandler(homePageGUI, activeProfile, currentAudio);
                    Sidebar.getInstance(activeProfile).setActive(Sidebar.getInstance(activeProfile).getHomeButton());

                    Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                    stage.setScene(homePageGUI.getScene());
                    stage.setTitle("HomePage");
                    stage.setWidth(previousDimension.getWidth());
                    stage.setHeight(previousDimension.getHeight());
                }
            }
        };
        EventHandler<ActionEvent> addEpisodeHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Add your episodes");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 file", "*mp3"));
                List<File> fileList = fileChooser.showOpenMultipleDialog(stage);
                for (File f : fileList) {
                    byte[] fileContent = new byte[(int) f.length()];
                    FileInputStream fileInputStream = null;
                    URL url = null;
                    try {
                        Media media = new Media(f.toURI().toURL().toString());
                        fileInputStream = new FileInputStream(f);
                        ContentHandler handler = new DefaultHandler();
                        Metadata metadata = new Metadata();
                        Parser parser = new Mp3Parser();
                        ParseContext parseContext = new ParseContext();
                        parser.parse(fileInputStream, handler, metadata, parseContext);
                        fileInputStream.close();

                        fileInputStream = new FileInputStream(f);
                        fileInputStream.read(fileContent);
                        fileInputStream.close();

                        JBAudio jbAudio = null;
                        Blob fileAudio = new SerialBlob(fileContent);

                        Podcast p = (Podcast) gui.getJbCollection();
                        jbAudio = new Episode(0, metadata.get("dc:title") == null ? FilenameUtils.removeExtension(f.getName()) : metadata.get("dc:title"), (Artist) p.getCreator(), gui.getJbCollection(), fileAudio, Double.parseDouble(metadata.get("xmpDM:duration")) * 1000, new Date(System.currentTimeMillis()), new String[]{metadata.get("xmpDM:genre")}, false, 0);

                        gui.getJbCollection().getTrackList().add(jbAudio);
                        CollectionManagerFactory.getInstance().getCollectionManager().addToCollection(gui.getJbCollection(), jbAudio);

                        AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING.refresh();

                    } catch (IOException | TikaException | SAXException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                }//end-foreach

            }
        };

        EventHandler<MouseEvent> tableViewClickHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    Node node = mouseEvent.getPickResult().getIntersectedNode();

                    // go up in node hierarchy until a cell is found, or we can be sure no cell was clicked
                    boolean foundPlayButton = false;
                    boolean foundIsFavoriteButton = false;
                    while (node != gui.getAudioTable() && !foundPlayButton && !foundIsFavoriteButton) {
                        String id = node.getId();
                        if (id != null && id.equals("playButton")) {
                            foundPlayButton = true;
                        } else if (id != null && id.equals("favoriteButton")) {
                            foundIsFavoriteButton = true;
                        }//end-if

                        node = node.getParent();
                    }//end-while

                    if (foundPlayButton) {
                        JBAudio audioClicked = gui.getAudioTable().getItems().get(gui.getAudioTable().getSelectionModel().getSelectedIndex());

                        PlayerManagerFactory.getInstance().getPlayerManager().play(audioClicked);
                    } else if (foundIsFavoriteButton) {
                        JBAudio audioClicked = gui.getAudioTable().getItems().get(gui.getAudioTable().getSelectionModel().getSelectedIndex());

                        if (activeProfile.getFavorites().getTrackList().contains(audioClicked)) {
                            activeProfile.getFavorites().getTrackList().remove(audioClicked);
                        } else {
                            activeProfile.getFavorites().getTrackList().add(audioClicked);
                        }//end-if
                        CollectionManagerFactory.getInstance().getCollectionManager().setFavorites(activeProfile);
                        if (AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING != null) {
                            AudioTableHandler.CURRENT_AUDIOTABLE_SHOWING.refresh();
                        }//end-if
                    }//end-if
                }//end-if
            }
        };
        gui.getCollectionHeader().getButtonRandom().setOnAction(randomButtonHandler);
        gui.getCollectionHeader().getEditButton().setOnAction(editButtonHandler);
        gui.getCollectionHeader().getButtonPlayPause().setOnAction(playPauseCollectionButtonHandler);
        gui.getAudioTable().setOnMouseClicked(tableViewClickHandler);
        gui.getCollectionHeader().getButtonBin().setOnAction(binButtonHandler);
        gui.getCollectionHeader().getAddEpisodeButton().setOnAction(addEpisodeHandler);

    }
    /*---------------------------------------*/
}
