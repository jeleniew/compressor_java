package group5.compressor.GUI;

import static group5.compressor.Properties.*;
import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class NewWindow extends Stage {

    private final Button okButton;
    private String tree = null;

    public NewWindow() {
        okButton = new Button("OK");
        okButton.getStyleClass().add("whiteButton");
    }
    
    public Button giveOkButton() {
        return okButton;
    }

    private void setWindowStyle(VBox vBox) {
        vBox.setPadding(DEFAULT_PADDING);
        vBox.setSpacing(DEFAULT_SPACING);
        vBox.setAlignment(Pos.CENTER_RIGHT);
        vBox.getStyleClass().add("newWindowStyle");
    }

    public Scene errorWindow(String text) {
        Label errorText = new Label(text);
        Pane textPane = new Pane(errorText);
        textPane.setMinWidth(470);
        textPane.setMaxWidth(470);
        VBox errorVBox = new VBox(textPane, okButton);
        setWindowStyle(errorVBox);
        Scene errorScene = new Scene(errorVBox, 500, 100);

        return errorScene;
    }

    public Scene chooseFileWindow(String text) {
        Label chooseFileText = new Label(text);
        Button chooseTree = new Button("Wybierz lokalizację drzewa");
        chooseTree.getStyleClass().add("whiteButton");
        Pane textPane = new Pane(chooseFileText);
        Pane treePane = new Pane(chooseTree);
        textPane.setMaxWidth(470);
        textPane.setMinWidth(470);
        VBox chooseFileBox = new VBox(textPane, treePane, okButton);
        setWindowStyle(chooseFileBox);
        
        chooseTree.setOnAction(ex -> {
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File("."));
            File selectedFile = chooser.showSaveDialog(new Stage());
            if (selectedFile != null) {
                tree = selectedFile.getAbsolutePath();
            }
        });
        Scene chooseFileScene = new Scene(chooseFileBox, 500, 125);

        return chooseFileScene;
    }

    public String getTree() {
        return tree;
    }
}