package group5.compressor;

import static group5.compressor.Properties.*;
import group5.compressor.file.Read;
import java.io.File;
import java.io.IOException;

public class Statistics {

    private String inputFile = null;
    private String outputFile = null;
    private String treeFile = null;
    private long inputSize = 0;
    private long outputSize = 0;
    private long zeroBit = 0;
    private long oneBit = 0;
    private char compress = 0;
    private final int[] codesQuantity = new int[MAX_CHAR_VALUE];

    public Statistics(char compress, String inputFile, String outputFile) throws IOException {
        this.compress = compress;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        getStatistics();
    }

    public Statistics(char compress, String inputFile, String outputFile, String treeFile) throws IOException {
        this.compress = compress;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.treeFile = treeFile;
        getStatistics();
    }

    public long giveInputSize() {
        return inputSize;
    }

    public long giveOutputSize() {
        return outputSize;
    }

    public long giveZeroBit() {
        return zeroBit;
    }

    public long giveOneBit() {
        return oneBit;
    }

    public int[] giveCodesQuantity() {
        return codesQuantity;
    }

    private void getStatistics() throws IOException {
        countBits();
        countChars();
        inputSize = getFileSize(inputFile);
        outputSize = getFileSize(outputFile);
        if (treeFile != null) {
            switch (compress) {
                case 'd':
                    inputSize += getFileSize(treeFile);
                    break;
                case 'c':
                    outputSize += getFileSize(treeFile);
                    break;
                default:
                    throw new IllegalArgumentException(WRONG_COMPRESSION_VALUE);
            }
        }
    }

    private void countBits() throws IOException {
        int nextByte;
        char nextChar;
        Read read = new Read();
        switch (compress) {
            case 'c':
                read.readFile(inputFile);
                break;
            case 'd':
                read.readFile(outputFile);
                break;
            default:
                throw new IllegalArgumentException(WRONG_COMPRESSION_VALUE);
        }
        while ((nextByte = read.getNextByte()) != -1) {
            nextChar = (char) nextByte;
            for (int i = 0; i < BYTE; i++) {
                if (((nextChar >> i) & 1) == 1) {
                    oneBit++;
                } else {
                    zeroBit++;
                }
            }
        }
        read.closeReadFile();
    }

    private void countChars() throws IOException {
        Read read = new Read();
        int currentChar;

        switch (compress) {
            case 'c':
                read.readFile(inputFile);
                break;
            case 'd':
                read.readFile(outputFile);
                break;
            default:
                throw new IllegalArgumentException(WRONG_COMPRESSION_VALUE);
        }
        for (int i = 0; i < MAX_CHAR_VALUE; i++) {
            codesQuantity[i] = 0;
        }
        while ((currentChar = read.getNextByte()) != -1) {
            codesQuantity[currentChar]++;
        }
        read.closeReadFile();
    }

    private long getFileSize(String fileName) {
        File file = new File(fileName);
        long result = file.length();

        return result;
    }

}
