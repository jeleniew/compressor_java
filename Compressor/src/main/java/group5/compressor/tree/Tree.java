package group5.compressor.tree;

import static group5.compressor.Properties.*;
import group5.compressor.file.Read;
import group5.compressor.file.Write;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Tree {

    public char symbol;
    public int value;
    public int branchLength = 0;
    public int maxLeft = 0;
    public int maxRight = 0;
    public Tree son0 = null;
    public Tree son1 = null;

    public void treeDecompress(Tree tree, int bit, Read read) throws IOException {
        int[] currentLocation = new int[MAX_CHAR_VALUE];
        int length = 0;
        Tree treeCopy = tree;
        if (bit == 0) {
            currentLocation[length++] = 0;
        } else {
            treeCopy.symbol = read.getSymbol();
            currentLocation[length] = 1;
        }
        while (length > 0) {
            bit = read.getNextBit();
            if (bit == 0) {
                for (int i = 0; i < length - 1; i++) {
                    treeCopy = (currentLocation[i] == 0) ? treeCopy.son0 : treeCopy.son1;
                }
                if (currentLocation[length - 1] == 0) {
                    treeCopy.son0 = new Tree();
                    treeCopy = treeCopy.son0;
                    currentLocation[length] = 0;
                } else {
                    treeCopy.son1 = new Tree();
                    treeCopy = treeCopy.son1;
                    currentLocation[length] = 0;
                }
                length++;
            } else {
                for (int i = 0; i < length - 1; i++) {
                    treeCopy = (currentLocation[i] == 0) ? treeCopy.son0 : treeCopy.son1;
                }
                if (currentLocation[length - 1] == 0) {
                    treeCopy.son0 = new Tree();
                    treeCopy.son0.symbol = read.getSymbol();
                    currentLocation[length - 1] = 1;
                } else {
                    treeCopy.son1 = new Tree();
                    treeCopy.son1.symbol = read.getSymbol();
                    while (length > 0 && (currentLocation[length - 1] == 1)) {
                        length--;
                    }
                    if (length > 0) {
                        currentLocation[length - 1] = 1;
                    }
                }
            }
            treeCopy = tree;
        }
    }

    public void fileDecompress(Tree node, Read read, Write write) throws IOException {
        Tree copyNode = node;
        int currentBit;
        while ((currentBit = read.getNextBit()) != -1) {
            if (copyNode.son0 == null) {
                write.print(copyNode.symbol);
                copyNode = node;
            }
            switch (currentBit) {
                case 0:
                    copyNode = copyNode.son0;
                    break;
                case 1:
                    copyNode = copyNode.son1;
                    break;
                default:
                    throw new IllegalArgumentException(WRONG_VALUE);
            }
        }
        if (copyNode.son0 == null) {
            write.print(copyNode.symbol);
            copyNode = node;
        }
    }

    class TreeComparator implements Comparator<Tree> {

        @Override
        public int compare(Tree x, Tree y) {
            return x.value - y.value;
        }
    }

    public Tree createTree(Tree tree, Read read) throws IOException {
        int[] countChar = new int[MAX_CHAR_VALUE];
        int currentSymbol;
        int symbolsQuantity = 0;
        for (int i = 0; i < MAX_CHAR_VALUE; i++) {
            countChar[i] = 0;
        }
        while ((currentSymbol = read.getNextByte()) != -1) {
            countChar[currentSymbol]++;
        }
        for (int i = 0; i < MAX_CHAR_VALUE; i++) {
            if (countChar[i] != 0) {
                symbolsQuantity++;
            }
        }

        PriorityQueue<Tree> treeQueue = new PriorityQueue<>(symbolsQuantity, new TreeComparator());

        for (int i = 0; i < MAX_CHAR_VALUE; i++) {
            if (countChar[i] != 0) {
                Tree newSymbol = new Tree();
                newSymbol.symbol = (char) i;
                newSymbol.value = countChar[i];
                treeQueue.add(newSymbol);
            }
        }

        while (treeQueue.size() > 1) {
            Tree x = treeQueue.peek();
            treeQueue.poll();
            Tree y = treeQueue.peek();
            treeQueue.poll();
            Tree newTree = new Tree();

            newTree.value = x.value + y.value;
            newTree.son0 = x;
            newTree.son1 = y;
            tree = newTree;

            treeQueue.add(newTree);
        }
        
        return tree;
    }
}
