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

        //label
        Label inputLabel = new Label("input");
        inputLabel.setFont(Font.font("Arial", 20));

        //key textfield
        TextField key = new TextField();
        key.setMaxWidth(27);
        key.setPromptText("Enter key");
        key.setMaxWidth(100);

        //label
        Label keyLabel = new Label("key");
        inputLabel.setFont(Font.font("Arial", 20));

        //display textarea
        TextArea display = new TextArea();
        display.setEditable(false);
        display.setMinSize(500,100);
        display.setMaxSize(500,100);

        //encrypt button
        Button en = new Button();
        en.setText("encrypt");
        en.setOnAction(e ->
        {
            if(inputText.getText().isEmpty() || key.getText().isEmpty()) {
                display.setText("Text and key must be entered");
            }
            else {
                encript.setInput(inputText.getText());
                encript.setKey(Integer.parseInt(key.getText()));

                display.setText(encript.encrypt());
            }
        });

        //HBox
        HBox inputComponent = new HBox(10);
        inputComponent.setAlignment(Pos.CENTER);
        inputComponent.getChildren().addAll(inputLabel,inputText,key,keyLabel);

        //vbox
        VBox root = new VBox(25);
        root.setBackground(Background.EMPTY);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(inputComponent,en,display);

        Scene scene = new Scene(root,800,700);
        stage.setScene(scene);
        stage.setTitle("cipher");
        stage.show();


    }


public static void main(String[] args) {

        launch(args);
    }
}
