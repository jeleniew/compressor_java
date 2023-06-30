package group5.compressor;

import javafx.geometry.Insets;

public class Properties {

    public static String CLICKED_BUTTON_STYLE = "-fx-background-color: #e1d5e7;" + "-fx-border-color: #9775a7;" + "-fx-border-radius: 5;" + "-fx-border-width: 3;";
    public static String NONCLICKED_BUTTON_STYLE = "-fx-background-color: #e1d5e7;" + "-fx-border-color: #9775a7;" + "-fx-border-radius: 5;" + "-fx-border-width: 1;";

    public static final String PROGRAM_TITLE = "Kompresor grupy 5";

    public static String COUNT_BITS_COMPRESSION = "Ilość bitów w pliku wejściowym";
    public static String COUNT_BITS_DECOMPRESSION = "Ilość bitów w pliku wyjściowym";
    public static String BITS_ON_TEXT = "Zapalone bity: ";
    public static String BITS_OFF_TEXT = "Zgaszone bity: ";
    public static String INPUT_SIZE_NAME = "Wejściowy: ";
    public static String OUTPUT_SIZE_NAME = "Wyjściowy: ";

    public static final int MAX_CHAR_VALUE = 256;
    public static final int BYTE = 8;
    public static final int DEFAULT_SPACING = 15;
    public static Insets DEFAULT_PADDING = new Insets(DEFAULT_SPACING, DEFAULT_SPACING, DEFAULT_SPACING, DEFAULT_SPACING);
    public static int CIRCLE_DIAMETER = 40;
    public static int LOWER = 20;

    public static String WRONG_VALUE = "Wystąpiła nieprawidłowa wartość.";
    public static String WRONG_COMPRESSION_VALUE = "Działanie jest nie możliwe do wykonania.";
    public static String WRONG_GROUP_VALUE = "Wybrana opcja jest niedostępna.";
    public static String FAILED_TO_EXECUTE_PROGRAM = "Niepowodzenie podczas wykonywania programu.";
    public static String FAILED_TO_DO_ACTION = "Nie udało się wykonać działania.";
    public static String IMPOSSIBLE_TO_DECOMPRESS = "Nie można dokonać dekompresji podanego pliku.";
}
