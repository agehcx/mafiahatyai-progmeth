package scene;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

import javafx.scene.image.Image;


public class LoadingScreen implements Initializable{

    @FXML
    private ImageView imageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/loading_screen.jpg")));
        imageView.setImage(image);
        imageView.setFitHeight(760);
        imageView.setFitWidth(1280);

        /*
        media_player.setOnEndOfMedia(() -> {
            // media finished
            Scene lawn_scene = null;
            try {
                Parent lawn_parent = FXMLLoader.load(getClass().getResource("/fxml/welcome.fxml"));
                lawn_scene = new Scene(lawn_parent);
            }
            catch (Exception e) {System.out.println("error in loading_screen_controller.java");}

            // following line is used to get the stage information...
            Stage window = (Stage)media.getScene().getWindow();

            // setting scene to window and displaying window...
            window.setScene(lawn_scene);
            window.show();
        });

        media_player.play();*/

    }
}
