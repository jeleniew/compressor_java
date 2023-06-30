package group5.compressor.GUI;

import static group5.compressor.Properties.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class StatsTab {
    private final Tab statsTab;

    Label bitsOnLabel = new Label(BITS_ON_TEXT);
    Label bitsOffLabel = new Label(BITS_OFF_TEXT);
    Label inputSizeLabel = new Label(INPUT_SIZE_NAME);
    Label outputSizeLabel = new Label(OUTPUT_SIZE_NAME);
    Label codesASCII = new Label();
    Label codesCount = new Label();
    Label countBitsTitle = new Label(COUNT_BITS_COMPRESSION);
    
    public StatsTab() {
        statsTab = prepareStatsTab();
    }
    
    public Tab giveStatsTab() {
        return statsTab;
    }
    
    public void updateText(long bitsOn, long bitsOff, long inputSize, long outputSize, int[] codesQuantity, char compress) {
        switch (compress) {
            case 'c':
                countBitsTitle.setText(COUNT_BITS_COMPRESSION);
                break;
            case 'd':
                countBitsTitle.setText(COUNT_BITS_DECOMPRESSION);
                break;
            default:
                throw new IllegalArgumentException(WRONG_COMPRESSION_VALUE);
        }
        bitsOnLabel.setText(BITS_ON_TEXT + bitsOn);
        bitsOffLabel.setText(BITS_OFF_TEXT + bitsOff);
        inputSizeLabel.setText(INPUT_SIZE_NAME + inputSize + "B");
        outputSizeLabel.setText(OUTPUT_SIZE_NAME + outputSize + "B");
        codesASCII.setText("");
        codesCount.setText("");
        for(int i = 0; i < MAX_CHAR_VALUE; i++) {
            if(codesQuantity[i] != 0) {
            codesASCII.setText(codesASCII.getText()+ i + "\n" );
            codesCount.setText(codesCount.getText() + codesQuantity[i] + "\n");
            }
        }
    }
    
    private Tab prepareStatsTab() {
        VBox signs = createSignsPane();
        VBox bitsAndSize = createBitsAndSizePane();

        HBox statsPane = new HBox(signs, bitsAndSize);

        statsPane.setPadding(DEFAULT_PADDING);
        statsPane.setSpacing(DEFAULT_SPACING);
        statsPane.setAlignment(Pos.CENTER);
        statsPane.getStyleClass().add("tabStyle");
        Tab stats = new Tab("Statystyki", statsPane);
        
        return stats;
    }
    
    private ScrollPane createScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMaxSize(328, 378);
        scrollPane.setMinSize(328, 378);
        scrollPane.setPannable(true);
        scrollPane.setStyle("-fx-focus-color: transparent;");
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        
        return scrollPane;
    }
    
    private VBox createSignsPane() {
        VBox signs = createTitlePanel(new Label("Wystąpienia znaków"), 330, 410, 30);

        VBox codes = createTitlePanelForScrollPane("Kod ASCII", 156, 376, 30);
        codes.getChildren().add(codesASCII);
        VBox quantity = createTitlePanelForScrollPane("Ilość wystąpień", 156, 376, 30);
        quantity.getChildren().add(codesCount);
        HBox hBox = new HBox(codes, quantity);
        ScrollPane scrollPane = createScrollPane();
        scrollPane.setContent(hBox);
        signs.getChildren().add(scrollPane);
        
        return signs;
    }
        
    private VBox createBitsAndSizePane() {
        Pane bits;
        bits = bitsOrSizeTemplate(countBitsTitle, bitsOnLabel, bitsOffLabel);
            
        Pane size = bitsOrSizeTemplate(new Label("Rozmiar plików"), inputSizeLabel, outputSizeLabel);
        VBox bitsAndSizePane = new VBox(bits, size);
        bitsAndSizePane.setSpacing(DEFAULT_SPACING);
        bitsAndSizePane.setAlignment(Pos.CENTER);
        
        return bitsAndSizePane;
    }
    
    private VBox bitsOrSizeTemplate(Label title, Label label1, Label label2) {
        VBox vBox = createTitlePanel(title, 240, 120, 30);
        VBox textBox = createTwoLabeledBox(label1, label2);
        vBox.getChildren().add(textBox);
        
        return vBox;
    }
    
    private VBox createTitlePanel(Label panelTitle, int width, int paneHeight, int textHeight) {
        VBox vBox = new VBox();
        vBox.setMaxSize(width, paneHeight);
        vBox.setMinSize(width, paneHeight);
        vBox.setStyle("-fx-background-color: #ffffff;" + "-fx-border-color: #000000;");
        StackPane signsText = new StackPane();
        
        signsText.setMaxSize(width - 1, textHeight);
        signsText.setMinSize(width - 1, textHeight);
        signsText.setAlignment(Pos.CENTER_LEFT);
        signsText.setPadding(new Insets(0, 0, 0, 10));
        signsText.getChildren().add(panelTitle);
        signsText.setStyle("-fx-border-style: hidden hidden solid hidden;");
        vBox.getChildren().add(signsText);
        
        return vBox;
    }
    
    private VBox createTitlePanelForScrollPane(String panelTitle, int width, int paneHeight, int textHeight) {
        VBox vBox = new VBox();
        vBox.setMaxWidth(width);
        vBox.setMinSize(width, paneHeight);
        vBox.setStyle("-fx-background-color: #ffffff;" + "-fx-border-color: #000000;" + "-fx-border-style: hidden solid hidden hidden;");
        StackPane signsText = new StackPane();
        
        signsText.setMaxSize(width - 1, textHeight);
        signsText.setMinSize(width - 1, textHeight);
        signsText.setAlignment(Pos.CENTER_LEFT);
        signsText.setPadding(new Insets(0, 0, 0, 10));
        signsText.getChildren().add(new Label(panelTitle));
        signsText.setStyle("-fx-border-style: hidden hidden solid hidden;");
        vBox.getChildren().add(signsText);
        
        return vBox;
    }

    private VBox createTwoLabeledBox(Label label1, Label label2) {
        VBox textBox = new VBox();
        textBox.setMaxSize(200, 90);
        textBox.setMinSize(200, 90);
        textBox.setPadding(DEFAULT_PADDING);
        textBox.setSpacing(DEFAULT_SPACING);
        textBox.getChildren().addAll(label1, label2);
        
        return textBox;
    }
}