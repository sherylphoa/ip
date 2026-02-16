package sasa;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The main entry point for the JavaFX application.
 * Handles the initialization of the graphical user interface, sets up the primary stage,
 * and injects the chatbot logic into the FXML controller.
 */
public class Main extends Application {
    private static final String FILE_PATH = "data/sasa.txt";
    private static final String PRODUCT_NAME = "Sasa";
    private Sasa sasa = new Sasa(FILE_PATH);

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle(PRODUCT_NAME);
            fxmlLoader.<MainWindow>getController().setSasa(sasa);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
