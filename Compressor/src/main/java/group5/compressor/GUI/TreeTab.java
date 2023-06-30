package group5.compressor.GUI;

import static group5.compressor.Properties.*;
import group5.compressor.file.Read;

import group5.compressor.tree.Tree;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class TreeTab {

    private ScrollPane scrollPane;
    private Tab treeTab;
    private StackPane stackPane;
    private BufferedImage bufferedImage;

    public TreeTab() {
        treeTab = prepareTreeTab();
    }

    public Tab giveTreeTab() {
        return treeTab;
    }

    private Tab prepareTreeTab() {
        VBox treePane = createTreePane();
        treeTab = new Tab("Drzewo", treePane);
        
        return treeTab;
    }

    private VBox createTreePane() {
        scrollPane = createScrollPane();
        stackPane = new StackPane();
        stackPane.getChildren().add(scrollPane);
        VBox treePane = new VBox();
        treePane.setAlignment(Pos.CENTER);
        treePane.getStyleClass().add("tabStyle");
        treePane.getChildren().add(stackPane);

        return treePane;
    }

    private ScrollPane createScrollPane() {
        scrollPane = new ScrollPane();
        scrollPane.setPannable(true);
        scrollPane.setMinViewportWidth(600);
        scrollPane.setMinViewportHeight(420);
        scrollPane.setStyle("-fx-focus-color: transparent;" + "-fx-background-color: transparent;");
        scrollPane.vbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.ALWAYS);

        return scrollPane;
    }
    
    public void updateTreeTab(String treeFile) throws IOException {
        Read read = new Read();
        read.readFile(treeFile);
        Tree tree = new Tree();
        tree = tree.createTree(tree, read); 
        Button button = createButton();
        stackPane.getChildren().add(button);
        tree = countBranchSize(tree);
        int leftWidth = leftWidth(tree);
        int rightWidth = rightWidth(tree);
        int maxDepth = maxDepth(tree);
        
        bufferedImage = new BufferedImage(leftWidth + rightWidth,  maxDepth * (CIRCLE_DIAMETER + LOWER) + LOWER, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setBackground(Color.blue);
        printTree(tree, graphics, leftWidth , LOWER * 3);
        
        scrollPane.setContent(ConvertToImageView(bufferedImage));
    }
    
    private Button createButton() {
        Button button = new Button("Zapisz");
        button.getStyleClass().add("whiteButtonWithBlackBorder");
        button.setTranslateX(260);
        button.setTranslateY(180);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    DirectoryChooser chooser = new DirectoryChooser();
                    chooser.setInitialDirectory(new File("."));
                    File selectedDirectory = chooser.showDialog(new Stage());
                    if (selectedDirectory != null) {
                        ImageIO.write(bufferedImage, "PNG", new File(selectedDirectory.getAbsolutePath() + "/picture.PNG"));
                    }
                } catch (IOException ex) {
                    throw new UnsupportedOperationException("Nie udało się utworzyć pliku .png.");
                }
            }
        });

        return button;
    }
        
    private Tree countBranchSize(Tree tree) {
        Tree treeCopy = tree;
        int currentBranch = 0;
        
        while(tree.branchLength == 0) {
            if (treeCopy.son0 == null && treeCopy.branchLength == 0) {
                treeCopy.branchLength = CIRCLE_DIAMETER / 2;
                treeCopy.maxLeft = CIRCLE_DIAMETER / 2;
                treeCopy.maxRight = CIRCLE_DIAMETER / 2;
                treeCopy = tree;
            } else if(treeCopy.son0.branchLength == 0) {
                treeCopy = treeCopy.son0;
                currentBranch = 0;
            } else if(treeCopy.son1.branchLength == 0) {
                treeCopy = treeCopy.son1;
                currentBranch = 1;
            } else {
                if(currentBranch == 0) {
                    treeCopy.branchLength = treeCopy.son1.maxRight + CIRCLE_DIAMETER / 2;
                    treeCopy.maxRight = treeCopy.son1.maxRight;
                    treeCopy.maxLeft = treeCopy.son0.maxLeft + treeCopy.branchLength;
                } else {
                    treeCopy.branchLength = treeCopy.son0.maxLeft + CIRCLE_DIAMETER / 2;
                    treeCopy.maxLeft = treeCopy.son0.maxLeft;
                    treeCopy.maxRight = treeCopy.son1.maxRight + treeCopy.branchLength;
                }
                treeCopy = tree;
                currentBranch = 0;
            }
        }
        return tree;
    }
    
    private int maxDepth(Tree tree) {
        if (tree == null)
            return 0;
        else {
            int son0Depth = maxDepth(tree.son0);
            int son1Depth = maxDepth(tree.son1);

            if (son0Depth > son1Depth) {
                return (son0Depth + 1);
            } else {
                return (son1Depth + 1);
            }
        }
    }
    
    private int leftWidth(Tree tree) {
        int width = CIRCLE_DIAMETER;
        if(tree == null) {
            return 0;
        }
        while(tree.son0 != null) {
            tree = tree.son0;
            width += tree.branchLength;
        }
        return width;
    }
    
    private int rightWidth(Tree tree) {
        int width = CIRCLE_DIAMETER;
        if(tree == null) {
            return 0;
        }
        while(tree.son1 != null) {
            tree = tree.son1;
            width += tree.branchLength;
        }
        return width;
    }

    public void printTree(Tree tree, Graphics2D graphics, int centerX, int centerY) {
        if (tree == null) {
            return;
        }
        graphics.setPaint(Color.white);
        graphics.fillOval(centerX - CIRCLE_DIAMETER / 2, centerY - CIRCLE_DIAMETER, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
        graphics.setPaint(Color.black);
        graphics.drawOval(centerX - CIRCLE_DIAMETER / 2, centerY - CIRCLE_DIAMETER, CIRCLE_DIAMETER, CIRCLE_DIAMETER);
        
        if (tree.son0 != null) {
            graphics.drawLine(centerX, centerY, centerX - tree.son0.branchLength, centerY + LOWER);
            printTree(tree.son0, graphics, centerX - tree.son0.branchLength, centerY + LOWER + CIRCLE_DIAMETER);
        }

        if (tree.son1 != null) {
            graphics.drawLine(centerX, centerY, centerX + tree.son1.branchLength, centerY + LOWER);
            printTree(tree.son1, graphics, centerX + tree.son1.branchLength, centerY + LOWER + CIRCLE_DIAMETER);
        }
        graphics.setPaint(Color.black);
        if(tree.son0 == null) {
            graphics.drawString(String.valueOf((int)tree.symbol + ":" + tree.value), centerX - 15, centerY - 15);
        } else {
            graphics.drawString(String.valueOf(tree.value), centerX - 15, centerY - 15);
        }
    }


    private static ImageView ConvertToImageView(BufferedImage bufferedImage) {
        WritableImage writableImage = null;
        if (bufferedImage != null) {
            writableImage = new WritableImage(bufferedImage.getWidth(), bufferedImage.getHeight());
            PixelWriter pixelWriter = writableImage.getPixelWriter();
            for (int i = 0; i < bufferedImage.getWidth(); i++) {
                for (int j = 0; j < bufferedImage.getHeight(); j++) {
                    pixelWriter.setArgb(i, j, bufferedImage.getRGB(i, j));
                }
            }
        }

        return new ImageView(writableImage);
    }
}