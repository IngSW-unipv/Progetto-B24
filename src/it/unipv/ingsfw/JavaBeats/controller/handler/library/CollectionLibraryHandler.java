package it.unipv.ingsfw.JavaBeats.controller.handler.library;

import it.unipv.ingsfw.JavaBeats.controller.factory.CollectionManagerFactory;
import it.unipv.ingsfw.JavaBeats.model.playable.audio.JBAudio;
import it.unipv.ingsfw.JavaBeats.model.collection.Album;
import it.unipv.ingsfw.JavaBeats.model.collection.JBCollection;
import it.unipv.ingsfw.JavaBeats.model.collection.Playlist;
import it.unipv.ingsfw.JavaBeats.model.collection.Podcast;
import it.unipv.ingsfw.JavaBeats.model.profile.JBProfile;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionLibraryGUI;
import it.unipv.ingsfw.JavaBeats.view.library.CollectionViewGUI;
import it.unipv.ingsfw.JavaBeats.view.library.CreationGUI;
import it.unipv.ingsfw.JavaBeats.view.presets.AudioCard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CollectionLibraryHandler {

    //Attributi
    private CollectionLibraryGUI collectionLibraryGUI;


    //Costruttore
    public CollectionLibraryHandler(JBProfile activeProfile, JBAudio currentAudio, CollectionLibraryGUI collectionLibraryGUI) {
        this.collectionLibraryGUI = collectionLibraryGUI;
        initComponents(activeProfile, currentAudio);
    }


    //Metodi
    private void initComponents(JBProfile activeProfile, JBAudio currentAudio) {
        EventHandler<MouseEvent> collectionClickHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

                JBCollection jbCollection = ((JBCollection) ((AudioCard) mouseEvent.getSource()).getIjbResearchable());
                jbCollection.setTrackList(CollectionManagerFactory.getInstance().getCollectionManager().getCollectionAudios(jbCollection, activeProfile));
                CollectionViewGUI collectionViewGUI = new CollectionViewGUI(activeProfile, currentAudio, jbCollection);
                CollectionViewHandler collectionViewHandler = new CollectionViewHandler(collectionViewGUI, activeProfile, currentAudio);

                Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());
                stage.setScene(collectionViewGUI.getScene());
                stage.setTitle("Collection");
                stage.setWidth(previousDimension.getWidth());
                stage.setHeight(previousDimension.getHeight());
            }
        };
        EventHandler<ActionEvent> plusButtonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Dimension2D previousDimension = new Dimension2D(stage.getWidth(), stage.getHeight());

                JBCollection newCollection = null;
                /* Default collection image when inserting */
                BufferedImage bufferedImage = null;
                byte[] image = null;
                try {
                    bufferedImage = ImageIO.read(new File("src/it/unipv/ingsfw/JavaBeats/view/resources/icons/RecordBig.png"));
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
                    image = byteArrayOutputStream.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }//end-try
                switch (collectionLibraryGUI.getEjbentity()) {
                    case PLAYLIST:
                        newCollection = new Playlist(1, "New playlist", activeProfile);
                        CollectionManagerFactory.getInstance().getCollectionManager().createJBCollection(newCollection);
                        ArrayList<JBCollection> jbPlaylistsArraylist = CollectionManagerFactory.getInstance().getCollectionManager().getPlaylists(activeProfile);
                        CollectionLibraryGUI collectionLibraryGUI1 = new CollectionLibraryGUI(activeProfile, currentAudio, jbPlaylistsArraylist, collectionLibraryGUI.getEjbentity());
                        CollectionLibraryHandler collectionLibraryHandler = new CollectionLibraryHandler(activeProfile, currentAudio, collectionLibraryGUI1);
                        stage.setScene(collectionLibraryGUI1.getScene());
                        break;
                    case ALBUM:
                        try {
                            newCollection = new Album(1, "New album", activeProfile, new ArrayList<JBAudio>(), new SerialBlob(image));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }//end-try
                        CreationGUI creationGUI = new CreationGUI(activeProfile, currentAudio, newCollection);
                        CreationGUIHandler CreationGUIHandler = new CreationGUIHandler(creationGUI, activeProfile, currentAudio);
                        stage.setScene(creationGUI.getScene());
                        stage.setTitle("Create your album");
                        break;
                    case PODCAST:
                        try {
                            newCollection = new Podcast(1, "New podcast", activeProfile, new ArrayList<JBAudio>(), new SerialBlob(image));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }//end-try
                        newCollection = CollectionManagerFactory.getInstance().getCollectionManager().createJBCollection(newCollection);
                        creationGUI = new CreationGUI(activeProfile, currentAudio, newCollection);
                        CreationGUIHandler = new CreationGUIHandler(creationGUI, activeProfile, currentAudio);
                        stage.setScene(creationGUI.getScene());
                        stage.setTitle("Create your podcast");
                        break;
                }//end-switch

                stage.setWidth(previousDimension.getWidth());
                stage.setHeight(previousDimension.getHeight());
            }
        };
        collectionLibraryGUI.getCollectionFlowPane().getChildren().forEach(a -> a.setOnMouseClicked(collectionClickHandler));
        collectionLibraryGUI.getButtonPlus().setOnAction(plusButtonHandler);
    }

}
