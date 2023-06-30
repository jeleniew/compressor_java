package group5.compressor.file;

import static group5.compressor.Properties.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Read {

    public InputStream in = null;
    private int currentByte = -1;
    private int nextByte = -1;
    private int unusedBits = 0;
    private int emptyBits = -1;

    public void readFile(String pathFile) throws IOException {
        try {
            in = new FileInputStream(pathFile);
        } catch (NullPointerException ex) {
            throw new NullPointerException("Plik nie może być nullem.");
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("Nie można otworzyć pliku " + pathFile + ".");
        }
    }

    public int getNextByte() throws IOException {
        int oneByte = -1;

        try {
            oneByte = in.read();
        } catch (IOException ex) {
            throw new IOException("Wystąpił błąd z odczytaniem pliku.");
        }
        
        return oneByte;
    }

    private int getBitsFromLastByte() {
        if (unusedBits <= emptyBits) {
            return -1;
        } else if (emptyBits == 0) {
            if (currentByte >= unusedBits) {
                currentByte -= unusedBits;
                return 1;
            } else {
                return 0;
            }
        } else {
            if (currentByte >= unusedBits) {
                currentByte -= unusedBits;
                return 1;
            } else {
                return 0;
            }
        }
    }

    private int getEmptyBits() throws IOException {
        int quantityOfBits = getNextByte();
        int result = 0;
        if (quantityOfBits > 0) {
            result = 1;
        }
        for (int i = 1; i < quantityOfBits; i++) {
            result *= 2;
        }
        if (quantityOfBits == -1) {
            throw new IllegalArgumentException(IMPOSSIBLE_TO_DECOMPRESS);
        }
        
        return result;
    }

    public int getNextBit() throws IOException {
        unusedBits /= 2;

        if (currentByte == -1) {
            emptyBits = getEmptyBits();
            nextByte = getNextByte();
        }

        if (nextByte != -1) {
            if (unusedBits == 0) {
                currentByte = nextByte;
                nextByte = getNextByte();
                unusedBits = 128;
            }
            if (nextByte == -1) {
                if (currentByte == -1) {
                    throw new IllegalArgumentException(IMPOSSIBLE_TO_DECOMPRESS);
                }
                return getBitsFromLastByte();
            }
            if (currentByte >= unusedBits) {
                currentByte -= unusedBits;
                return 1;
            } else {
                return 0;
            }
        } else {
            if (currentByte == -1) {
                throw new IllegalArgumentException(IMPOSSIBLE_TO_DECOMPRESS);
            }
            return getBitsFromLastByte();
        }
    }

    public char getSymbol() throws IOException {
        int symbol = 0;
        int nextBit;
        for (int i = 0; i < BYTE; i++) {
            if ((nextBit = getNextBit()) == -1) {
                throw new IllegalArgumentException(IMPOSSIBLE_TO_DECOMPRESS);
            }
            symbol = symbol * 2 + nextBit;
        }
        
        return (char) symbol;
    }

    public void closeReadFile() throws IOException {
        try {
            in.close();
        } catch (IOException ex) {
            throw new IOException("Wystąpił błąd z zakończeniem odczytu pliku.");
        }
    }
}