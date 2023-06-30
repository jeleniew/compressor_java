package group5.compressor.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static group5.compressor.Properties.IMPOSSIBLE_TO_DECOMPRESS;

public class ReadTest {

    Read read;

    @Before
    public void setUp() {
        read = new Read();
    }

    @Test
    public void shouldThrowExceptionWhenInuptFileIsNull() {
        //given
        String pathFile = null;

        //when
        Throwable thrown = catchThrowable(() -> {
            read.readFile(pathFile);
        });

        //then
        String message = "Plik nie może być nullem.";
        assertThat(thrown).hasMessage(message);
    }

    @Test
    public void shouldThrowExceptionWhenInputCantBeOpened() {
        //given
        String pathFile = "";

        //when
        Throwable thrown = catchThrowable(() -> {
            read.readFile(pathFile);
        });

        //then
        String message = "Nie można otworzyć pliku " + pathFile + ".";
        assertThat(thrown).hasMessage(message);
    }

    @Test
    public void shouldThrowExceptionWhenNothingToRead() throws IOException {
        //given
        read.in = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));

        //when
        Throwable thrown = catchThrowable(() -> {
            read.getNextBit();
        });

        //then
        String message = IMPOSSIBLE_TO_DECOMPRESS;
        assertThat(thrown).hasMessage(message);
    }

    @Test
    public void shouldReturnNegativeValueWhenNothingLeftToRead() throws IOException {
        //given
        read.in = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));

        //when
        int result = read.getNextByte();

        //then
        int expected = -1;
        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnCorrectValueWhenASymbolIsRead() throws IOException {
        //given
        read.in = new ByteArrayInputStream("a".getBytes(StandardCharsets.UTF_8));

        //when
        int result = read.getNextByte();

        //then
        int expected = (int) 'a';
        assertEquals(expected, result);
    }

    @Test
    public void shouldThrowExceptionWhenFileIsTooShort() throws IOException {
        //given
        String text = "a";
        read.in = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));

        //when
        Throwable thrown = catchThrowable(() -> {
            read.getNextBit();
        });

        //then
        String message = "Nie można dokonać dekompresji podanego pliku.";
        assertThat(thrown).hasMessage(message);
    }

    @Test
    public void shouldThrowExceptionWhenCannotGetSymbol() {
        //given
        String text = 3 + "";
        read.in = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));

        //when
        Throwable thrown = catchThrowable(() -> {
            read.getSymbol();
        });

        //then
        String message = "Nie można dokonać dekompresji podanego pliku.";
        assertThat(thrown).hasMessage(message);
    }

    @Test
    public void shouldReturnSymbolWhenCorrectInput() throws IOException {
        //given
        String text = 0 + "a";
        read.in = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));

        //when
        char result = read.getSymbol();

        //then
        char expected = 'a';
        assertEquals(expected, result);
    }
}
