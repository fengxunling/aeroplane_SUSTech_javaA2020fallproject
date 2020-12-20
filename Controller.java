package sample;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class Controller {
    private int clickCnt = 0;

    File file = new File("./src/sample/music1.mp3");
    Media media = new Media(file.toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(media);


    public static boolean switchCheck = false;


    @FXML
    private ImageView background;

    @FXML
    private ImageView button1;

    @FXML
    private ImageView musicButton2;

    @FXML
    private ImageView musicButton1;

    @FXML
    private ImageView button3;

    @FXML
    private ImageView menuButton2;

    @FXML
    private Text menuButton1;


    @FXML
    void changeModeExcute(MouseEvent event) throws Exception{
        changeMode newStage = new changeMode();
        newStage.showWindow();
    }

    @FXML
    void openNewPanel() throws Exception{
        RankList newStage = new RankList();
        newStage.showWindow();
    }

    @FXML
    void closeMusic(MouseEvent event) {
        clickCnt++;
        System.out.printf("clickCnt = %d\n", clickCnt);
        if(clickCnt % 2 == 1) {
            // 关闭背景音乐
            musicButton1.setOpacity(0.0);
            musicButton2.setOpacity(1.0);
            mediaPlayer.pause();
        }
        else{
            // 打开背景音乐
            musicButton1.setOpacity(1.0);
            musicButton2.setOpacity(0.0);
            mediaPlayer.play();
        }
    }

    @FXML
    void switchPanel(MouseEvent event) throws Exception{
        switchCheck = true;
//        System.out.println(1);
        ChessBoard newStage = new ChessBoard();
        newStage.showWindow();
    }

    @FXML
    void flick() throws Exception{
        FadeTransition ft = new FadeTransition(Duration.millis(1000), button1);
        ft.setFromValue(1.0);
        ft.setToValue(0.1);
        ft.setCycleCount(Timeline.INDEFINITE);
    }



    }






