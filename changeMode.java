package sample;

import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import javax.print.attribute.standard.MediaPrintableArea;
import java.io.File;
import java.sql.SQLOutput;

public class changeMode extends Application {
    Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root2 = FXMLLoader.load(getClass().getResource("sample_1.fxml"));
        primaryStage.setTitle("模式选择");
        primaryStage.setScene(new Scene(root2, 700, 400));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    public void showWindow() throws Exception{
        start(stage);
    }
}
