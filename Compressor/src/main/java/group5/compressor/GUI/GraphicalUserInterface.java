package group5.compressor.GUI;

import static group5.compressor.Properties.*;
import group5.compressor.Statistics;
import java.io.IOException;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import group5.compressor.GUI.TreeTab;

import javafx.stage.Stage;

public class GraphicalUserInterface extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        prepareStage(stage);
    }

    private void prepareStage(Stage stage) throws IOException {
        TabPane tabPane = prepareTabPane();
        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox, 640, 480);
        String css = this.getClass().getResource("/mainStyles.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setTitle(PROGRAM_TITLE);
        stage.setResizable(false);
        stage.getIcons().add(new Image(GraphicalUserInterface.class.getResourceAsStream("/Logo.png")));
        stage.show();
    }

    private void startStatistics(CompressorTab compressorTab, StatsTab statsTab) throws IOException {
        Statistics statistics;
        switch (compressorTab.giveGroup()) {
            case 5:
                statistics = new Statistics(compressorTab.giveAction(), compressorTab.giveInputFile(), compressorTab.giveOutputFile());
                break;
            case 3:
                statistics = new Statistics(compressorTab.giveAction(), compressorTab.giveInputFile(), compressorTab.giveOutputFile(), compressorTab.giveTreeFile());
                break;
            default:
                throw new IllegalArgumentException(WRONG_GROUP_VALUE);
        }
        statsTab.updateText(statistics.giveOneBit(), statistics.giveZeroBit(), statistics.giveInputSize(), statistics.giveOutputSize(), statistics.giveCodesQuantity(), compressorTab.giveAction());
    }

    private TabPane prepareTabPane() throws IOException {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        tabPane.setMaxSize(640, 480);
        tabPane.setMinSize(640, 480);

        CompressorTab compressorTab = new CompressorTab();
        Tab compressor = compressorTab.giveCompressorTab();
        StatsTab statsTab = new StatsTab();
        Tab stats = statsTab.giveStatsTab();
        TreeTab treeTab = new TreeTab();
        Tab tree = treeTab.giveTreeTab();

        tabPane.getTabs().addAll(compressor, stats, tree);

        compressorTab.giveExecuteButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    if (compressorTab.executeButtonClicked()) {
                        switch (compressorTab.giveGroup()) {
                            case 3:
                                Stage treeStage = compressorTab.prepareTreeStage();
                                compressorTab.giveOkButton().setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent t) {
                                        try {
                                            compressorTab.executeGroup3(treeStage);
                                            startStatistics(compressorTab, statsTab);
                                            treeTab.updateTreeTab(compressorTab.giveTreeFile());
                                        } catch (InterruptedException | IOException ex) {
                                            throw new IllegalStateException(FAILED_TO_DO_ACTION);
                                        }
                                    }
                                });
                                break;
                            case 5:
                                compressorTab.executeGroup5();
                                startStatistics(compressorTab, statsTab);
                                switch (compressorTab.giveAction()) {
                                    case 'c':
                                        treeTab.updateTreeTab(compressorTab.giveInputFile());
                                        break;
                                    case 'd':
                                        treeTab.updateTreeTab(compressorTab.giveOutputFile());
                                        break;
                                    default:
                                        throw new IllegalArgumentException(WRONG_COMPRESSION_VALUE);
                                }
                                break;
                            default:
                                throw new IllegalArgumentException(WRONG_GROUP_VALUE);
                        }
                    }
                } catch (InterruptedException | IOException ex) {
                    throw new IllegalStateException(FAILED_TO_DO_ACTION);
                }
            }
        });

        return tabPane;
    }

}
