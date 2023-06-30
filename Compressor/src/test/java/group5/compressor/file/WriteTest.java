package group5.compressor.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import org.junit.Before;
import org.junit.Test;

public class WriteTest {

    Write write;

    @Before
    public void setUp() {
        write = new Write();
    }

    @Test
    public void shouldThrowExceptionWhenOutputFileIsNull() {
        //given
        String pathFile = null;

        //then
        Throwable thrown = catchThrowable(() -> {
            write.writeFile(pathFile);
        });

        //when
        String message = "Plik nie może być nullem.";
        assertThat(thrown).hasMessage(message);
    }

    @Test
    public void shouldThrowExceptionWhenOutputFileCantBeOpened() {
        //given
        String pathFile = "";

        //when
        Throwable thrown = catchThrowable(() -> {
            write.writeFile(pathFile);
        });

        //then
        String message = "Nie można otworzyć pliku.";
        assertThat(thrown).hasMessage(message);
    }

}
