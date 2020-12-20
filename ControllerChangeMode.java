package sample;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class ControllerChangeMode {

    @FXML
    private ImageView selectButton1;

    @FXML
    private ImageView selectButton3;

    @FXML
    private ImageView selectButton2;

    @FXML
    private ImageView selectButton4;

    @FXML
    private ImageView regardButton;

    @FXML
    void selectButton1Excute(MouseEvent event) {
        selectButton1.setOpacity(1.0);
        selectButton2.setOpacity(0.4);
        regardButton.setOpacity(0.4);
    }

    @FXML
    void selectButton2Excute(MouseEvent event) {
        selectButton2.setOpacity(1.0);
        selectButton1.setOpacity(0.4);
        regardButton.setOpacity(0.4);
    }

    @FXML
    void selectButton3Excute(MouseEvent event) {
        selectButton3.setOpacity(1.0);
        selectButton4.setOpacity(0.4);
        regardButton.setOpacity(0.4);
    }

    @FXML
    void selectButton4Excute(MouseEvent event) {
        selectButton4.setOpacity(1.0);
        selectButton3.setOpacity(0.4);
        regardButton.setOpacity(0.4);
    }

    @FXML
    void regardButtonExcute(MouseEvent event) {
        regardButton.setOpacity(1.0);
        // 更改save选项
    }



}
