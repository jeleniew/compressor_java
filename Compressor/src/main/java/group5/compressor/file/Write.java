package group5.compressor.file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Write {

    OutputStream out = null;

    public void writeFile(String pathFile) throws IOException {
        try {
            out = new FileOutputStream(pathFile);
        } catch (NullPointerException ex) {
            throw new NullPointerException("Plik nie może być nullem.");
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("Nie można otworzyć pliku.");
        }
    }

    public void print(char symbol) throws IOException {
        try {
            out.write(symbol);
        } catch (IOException ex) {
            throw new IOException("Wystąpił błąd z wypisaniem znaku do pliku.");
        }
    }

    public void closeWriteFile() throws IOException {
        try {
            out.close();
        } catch (IOException ex) {
            throw new IOException("Wystąpił błąd z zakończeniem odczytu pliku.");
        }
    }
}
