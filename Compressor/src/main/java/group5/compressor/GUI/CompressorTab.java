package group5.compressor.GUI;

import static group5.compressor.Properties.*;

import group5.compressor.file.Read;
import group5.compressor.file.Write;
import group5.compressor.tree.Tree;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CompressorTab {

    private String inputFile = null;
    private String outputFile = null;
    private String treeFile = null;
    private final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().contains("win");
    private char compress = 0;
    private int group = 0;
    private Button executeButton;
    private final Tab compressorTab;
    private final File thisProgram = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getParentFile();
    private final String thisProgramPath = thisProgram.getAbsolutePath();
    private final NewWindow treeWindow = new NewWindow();

    public CompressorTab() {
        executeButton = createExecuteButton();
        compressorTab = prepareCompressorTab();
    }

    public Tab giveCompressorTab() {
        return compressorTab;
    }

    public Button giveExecuteButton() {
        return executeButton;
    }

    public String giveInputFile() {
        return inputFile;
    }

    public String giveOutputFile() {
        return outputFile;
    }

    public String giveTreeFile() {
        return treeFile;
    }

    public int giveGroup() {
        return group;
    }

    public char giveAction() {
        return compress;
    }

    public Button giveOkButton() {
        return treeWindow.giveOkButton();
    }

    private void buildCommand(String... command) throws InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(command);
        try {
            Process process = builder.start();
            int exitCode = process.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            if (exitCode != 0) {
                System.err.println(new File(".").getAbsolutePath());
                for (String str : command) {
                    System.err.print(str + " ");
                }
                System.err.println();
                throw new RuntimeException("'" + bufferedReader.readLine() + "'");
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException("Niepowodzenie podczas rozpoczącia programu.", ex);
        }
    }

    private void decompress() throws IOException {
        Read read = new Read();
        Write write = new Write();
        Tree tree = new Tree();
        read.readFile(inputFile);
        write.writeFile(outputFile);
        tree.treeDecompress(tree, read.getNextBit(), read);
        tree.fileDecompress(tree, read, write);
        read.closeReadFile();
        write.closeWriteFile();
    }

    private Stage prepareNewStage(Scene scene) {
        Stage stage = new Stage();
        stage.setTitle(PROGRAM_TITLE);
        stage.setScene(scene);
        String css = this.getClass().getResource("/mainStyles.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.getIcons().add(new Image(GraphicalUserInterface.class.getResourceAsStream("/Logo.png")));
        stage.setResizable(false);
        stage.show();
        return stage;
    }

    private void showWindowWhenNotChosen(String text) {
        NewWindow newWindow = new NewWindow();
        Stage stage = prepareNewStage(newWindow.errorWindow(text));
        stage.getIcons().add(new Image(GraphicalUserInterface.class.getResourceAsStream("/Logo.png")));
        newWindow.giveOkButton().setOnAction(action -> {
            stage.close();
        });
    }

    public Stage prepareTreeStage() {
        String text;
        switch (compress) {
            case 'c':
                text = "Wybierz, gdzie zapisać drzewo.";
                break;
            case 'd':
                text = "Wybierz plik zawierający drzewo.";
                break;
            default:
                throw new IllegalArgumentException(WRONG_COMPRESSION_VALUE);
        }
        return prepareNewStage(treeWindow.chooseFileWindow(text));

    }

    public void executeGroup3(Stage stage) throws InterruptedException {
        if ((treeFile = treeWindow.getTree()) != null) {
            stage.close();
            try {
                executeWhenHavingTree();
            } catch (InterruptedException ex) {
                throw new InterruptedException(FAILED_TO_EXECUTE_PROGRAM);
            }
        } else {
            throw new IllegalArgumentException("Nie udało się odczytać pliku.");
        }
    }

    private void executeWhenHavingTree() throws InterruptedException {
        switch (compress) {
            case 'c':
                try {
                    if (IS_WINDOWS) {
                        buildCommand(thisProgramPath + "\\group3compressor.exe", "--s", inputFile, "--d", outputFile, "--t", treeFile, "--m", "encode");
                    } else {
                        buildCommand(thisProgramPath + "/group3compressor", "--s", inputFile, "--d", outputFile, "--t", treeFile, "--m", "encode");
                    }
                } catch (InterruptedException ex) {
                    throw new InterruptedException(FAILED_TO_EXECUTE_PROGRAM);
            }
            break;
            case 'd':
                try {
                    if (IS_WINDOWS) {
                        buildCommand(thisProgramPath + "\\group3compressor.exe", "--s", inputFile, "--d", outputFile, "--t", treeFile, "--m", "decode");
                    } else {
                        buildCommand(thisProgramPath + "/group3compressor", "--s", inputFile, "--d", outputFile, "--t", treeFile, "--m", "decode");
                    }
                } catch (InterruptedException ex) {
                    throw new InterruptedException(FAILED_TO_EXECUTE_PROGRAM);
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void executeGroup5() throws IOException, InterruptedException {
        switch (compress) {
            case 'c':
                try {
                if (IS_WINDOWS) {
                    buildCommand(thisProgramPath + "\\group5compressor.exe", "-i", inputFile, "-o", outputFile);
                } else {
                    buildCommand(thisProgramPath + "/group5compressor", "-i", inputFile, "-o", outputFile);
                }
            } catch (InterruptedException ex) {
                throw new InterruptedException(FAILED_TO_EXECUTE_PROGRAM);
            }
            break;
            case 'd':
                decompress();
                break;
            default:
                throw new IllegalArgumentException(WRONG_COMPRESSION_VALUE);
        }
    }

    private Button createExecuteButton() {
        executeButton = new Button("Wykonaj");
        executeButton.getStyleClass().add("whiteButton");

        return executeButton;
    }

    public boolean executeButtonClicked() throws IOException {
        if (group == 0) {
            showWindowWhenNotChosen("Nie można wykonać działania, bo nie wybrano grupy.");
            return false;
        } else if (compress == 0) {
            showWindowWhenNotChosen("Nie można wykonać działania, bo nie wybrano, co ma wykonać program.");
            return false;
        } else if (inputFile == null) {
            showWindowWhenNotChosen("Nie można wykonać działania, bo nie wybrano pliku wejściowego.");
            return false;
        } else if (outputFile == null) {
            showWindowWhenNotChosen("Nie można wykonać działania, bo nie wybrano, gdzie zapisać plik wyjściowy.");
            return false;
        }
        return true;
    }

    private Tab prepareCompressorTab() {
        VBox chooseProgram = createPaneChooseProgram();
        VBox chooseAction = createPaneChooseAction();
        VBox chooseInput = createPaneChooseInput();
        VBox chooseOutput = createPaneChooseOutput();
        VBox compressorPane = new VBox(chooseProgram, chooseAction, chooseInput, chooseOutput, executeButton);
        
        compressorPane.setPadding(DEFAULT_PADDING);
        compressorPane.setSpacing(DEFAULT_SPACING);
        compressorPane.setAlignment(Pos.TOP_RIGHT);
        compressorPane.getStyleClass().add("tabStyle");
        Tab compressor = new Tab("Kompresor", compressorPane);

        return compressor;
    }

    private Button createButtonTemplateForCompressorTab(String buttonText) {
        Button button = new Button(buttonText);
        button.setMinSize(260, 30);
        button.setMaxSize(260, 30);
        button.setStyle(NONCLICKED_BUTTON_STYLE);

        return button;
    }

    private TextField createTextFieldTemplateForCompressorTab() {
        TextField textField = new TextField();
        textField.setMinSize(260, 30);
        textField.setMaxSize(260, 30);

        return textField;
    }

    private VBox createPaneTemplateForCompressorTab(Button button1, Node button2, String paneTitle) {
        HBox buttonPane = new HBox(button1, button2);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setSpacing(30);
        
        VBox vbox = new VBox(new Label(paneTitle), buttonPane);
        vbox.setMinSize(608, 80);
        vbox.setMaxSize(608, 80);
        vbox.setPadding(new Insets(10, 30, 30, 30));
        vbox.setSpacing(10);
        vbox.setStyle("-fx-background-color: #ffffff;" + "-fx-border-color: #000000;");

        return vbox;
    }

    private void changeClickedButton(Button button1, Button button2) {
        button1.setStyle(CLICKED_BUTTON_STYLE);
        button2.setStyle(NONCLICKED_BUTTON_STYLE);
    }

    private VBox createPaneChooseProgram() {
        Button button1 = createButtonTemplateForCompressorTab("Grupa 5");
        Button button2 = createButtonTemplateForCompressorTab("Grupa 3");
        VBox chooseProgram = createPaneTemplateForCompressorTab(button1, button2, "Wybierz program");
        
        button1.setOnAction(e -> {
            changeClickedButton(button1, button2);
            group = 5;
        });
        button2.setOnAction(e -> {
            changeClickedButton(button2, button1);
            group = 3;
        });

        return chooseProgram;
    }

    private VBox createPaneChooseAction() {
        Button button1 = createButtonTemplateForCompressorTab("Kompresja");
        Button button2 = createButtonTemplateForCompressorTab("Dekompresja");
        VBox chooseAction = createPaneTemplateForCompressorTab(button1, button2, "Wybierz działanie");
        
        button1.setOnAction(e -> {
            changeClickedButton(button1, button2);
            compress = 'c';
        });
        button2.setOnAction(e -> {
            changeClickedButton(button2, button1);
            compress = 'd';
        });

        return chooseAction;
    }

    private VBox createPaneChooseInput() {
        Stage stage = new Stage();
        Button button = createButtonTemplateForCompressorTab("Wybierz plik");
        TextField textField = createTextFieldTemplateForCompressorTab();

        VBox chooseInput = createPaneTemplateForCompressorTab(button, textField, "Podaj plik wejściowy");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        button.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                inputFile = selectedFile.getAbsolutePath();
                textField.setText(selectedFile.getAbsolutePath());
            }
        });
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                inputFile = newValue;
            }
        });

        return chooseInput;
    }

    private VBox createPaneChooseOutput() {
        Stage stage = new Stage();
        Button button = createButtonTemplateForCompressorTab("Wybierz plik");
        TextField textField = createTextFieldTemplateForCompressorTab();
        VBox chooseOutput = createPaneTemplateForCompressorTab(button, textField, "Podaj gdzie zapisać plik wyjściowy");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        button.setOnAction(e -> {
            File selectedFile = fileChooser.showSaveDialog(stage);
            if (selectedFile != null) {
                outputFile = selectedFile.getAbsolutePath();
                textField.setText(selectedFile.getAbsolutePath());
            }
        });
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                outputFile = newValue;
            }
        });

        return chooseOutput;
    }
}