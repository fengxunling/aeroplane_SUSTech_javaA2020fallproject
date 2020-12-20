package sample;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.print.attribute.standard.MediaPrintableArea;
import java.io.File;
import java.sql.SQLOutput;

public class ChessBoard extends Application {
    public static Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception{
        File file = new File("./src/sample/music1.mp3");
        Media media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

//        Parent root2 = FXMLLoader.load(getClass().getResource("sample2.fxml"));

        mediaPlayer.play();

        ChessBoardExecute.pre_solve();
        ChessBoardExecute.clear();
        ChessBoardExecute.loadGame();

//        Pane pane = new AnchorPane();
//        Scene scene = new Scene(pane, 1200, 600);
//
//
//        Image image = new Image("./pictures/red_version.png");
//        ImageView imageView = new ImageView(image);
//        pane.getChildren().add(imageView);
//
//        AnchorPane.setTopAnchor(imageView, ChessBoardExecute.Hangers[1].getY() * 1.0);
//
//        primaryStage.setTitle("飞行棋");
//        primaryStage.setScene(scene);
//        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    System.out.println("Save Game Successfully!");
                    ChessBoardExecute.saveGame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void showWindow() throws Exception{
        start(stage);
    }
}


