package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
public abstract class Cipher extends Application{
    private Encript encript = new Encript("", 0);

    @Override
    public void start(Stage stage) {

        //Input textfield
        TextField inputText = new TextField();
        inputText.setMinSize(500,100);
        inputText.setMaxSize(500,100);
        inputText.setPromptText("Enter text here");


    }
}

public static void main(String[] args) {

        launch(args);
    }
}
