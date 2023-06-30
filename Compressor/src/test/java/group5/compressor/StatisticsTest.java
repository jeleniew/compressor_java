package group5.compressor;

import static com.sun.javafx.scene.control.skin.Utils.getResource;
import static group5.compressor.Properties.MAX_CHAR_VALUE;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

public class StatisticsTest {

    Statistics statistics;

    @Test
    public void shouldGiveCorrectInputSizeWhenCompressionAndGroup5() throws IOException {
        //given
        String inputFile = getResource("/testFiles/AlaMaKota.txt").getPath();
        String outputFile = getResource("/testFiles/output.txt").getPath();
        char compress = 'c';

        //when
        statistics = new Statistics(compress, inputFile, outputFile);
        long result = statistics.giveInputSize();

        //then
        long expected = 12;
        assertEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectOutputSizeWhenCompressionAndGroup5() throws IOException {
        //given
        String inputFile = getResource("/testFiles/AlaMaKota.txt").getPath();
        String outputFile = getResource("/testFiles/output.txt").getPath();
        char compress = 'c';

        //when
        statistics = new Statistics(compress, inputFile, outputFile);
        long result = statistics.giveOutputSize();

        //then
        long expected = 6;
        assertEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectInputSizeWhenCompressionAndGroup3() throws IOException {
        //given
        String inputFile = getResource("/testFiles/AlaMaKota.txt").getPath();
        String outputFile = getResource("/testFiles/output.txt").getPath();
        String treeFile = getResource("/testFiles/tree.txt").getPath();
        char compress = 'c';

        //when
        statistics = new Statistics(compress, inputFile, outputFile, treeFile);
        long result = statistics.giveInputSize();

        //then
        long expected = 12;
        assertEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectOutputSizeWhenCompressionAndGroup3() throws IOException {
        //given
        String inputFile = getResource("/testFiles/AlaMaKota.txt").getPath();
        String outputFile = getResource("/testFiles/output.txt").getPath();
        String treeFile = getResource("/testFiles/tree.txt").getPath();
        char compress = 'c';

        //when
        statistics = new Statistics(compress, inputFile, outputFile, treeFile);
        long result = statistics.giveOutputSize();

        //then
        long expected = 10;
        assertEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectInputSizeWhenDecompressionAndGroup5() throws IOException {
        //given
        String inputFile = getResource("/testFiles/AlaMaKota.txt").getPath();
        String outputFile = getResource("/testFiles/output.txt").getPath();
        char compress = 'd';

        //when
        statistics = new Statistics(compress, inputFile, outputFile);
        long result = statistics.giveInputSize();

        //then
        long expected = 12;
        assertEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectOutputSizeWhenDecompressionAndGroup5() throws IOException {
        //given
        String inputFile = getResource("/testFiles/AlaMaKota.txt").getPath();
        String outputFile = getResource("/testFiles/output.txt").getPath();
        char compress = 'd';

        //when
        statistics = new Statistics(compress, inputFile, outputFile);
        long result = statistics.giveOutputSize();

        //then
        long expected = 6;
        assertEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectInputSizeWhenDecompressionAndGroup3() throws IOException {
        //given
        String inputFile = getResource("/testFiles/AlaMaKota.txt").getPath();
        String outputFile = getResource("/testFiles/output.txt").getPath();
        String treeFile = getResource("/testFiles/tree.txt").getPath();
        char compress = 'd';

        //when
        statistics = new Statistics(compress, inputFile, outputFile, treeFile);
        long result = statistics.giveInputSize();

        //then
        long expected = 16;
        assertEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectOutputSizeWhenDecompressionAndGroup3() throws IOException {
        //given
        String inputFile = getResource("/testFiles/AlaMaKota.txt").getPath();
        String outputFile = getResource("/testFiles/output.txt").getPath();
        String treeFile = getResource("/testFiles/tree.txt").getPath();
        char compress = 'd';

        //when
        statistics = new Statistics(compress, inputFile, outputFile, treeFile);
        long result = statistics.giveOutputSize();

        //then
        long expected = 6;
        assertEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectZeroBitAmountWhenCompressionAndGroup5() throws IOException {
        //given
        String inputFile = getResource("/testFiles/A.txt").getPath();
        String outputFile = getResource("/testFiles/output.txt").getPath();
        char compress = 'c';

        //when
        statistics = new Statistics(compress, inputFile, outputFile);
        long result = statistics.giveZeroBit();

        //then
        long expected = 6;
        assertEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectOneBitAmountWhenCompressionAndGroup5() throws IOException {
        //given
        String inputFile = getResource("/testFiles/A.txt").getPath();
        String outputFile = getResource("/testFiles/output.txt").getPath();
        char compress = 'c';

        //when
        statistics = new Statistics(compress, inputFile, outputFile);
        long result = statistics.giveOneBit();

        //then
        long expected = 2;
        assertEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectZeroBitAmountWhenCompressionAndGroup3() throws IOException {
        //given
        String inputFile = getResource("/testFiles/A.txt").getPath();
        String outputFile = getResource("/testFiles/output.txt").getPath();
        String treeFile = getResource("/testFiles/tree.txt").getPath();
        char compress = 'c';

        //when
        statistics = new Statistics(compress, inputFile, outputFile, treeFile);
        long result = statistics.giveZeroBit();

        //then
        long expected = 6;
        assertEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectOneBitAmountWhenCompressionAndGroup3() throws IOException {
        //given
        String inputFile = getResource("/testFiles/A.txt").getPath();
        String outputFile = getResource("/testFiles/output.txt").getPath();
        String treeFile = getResource("/testFiles/tree.txt").getPath();
        char compress = 'c';

        //when
        statistics = new Statistics(compress, inputFile, outputFile, treeFile);
        long result = statistics.giveOneBit();

        //then
        long expected = 2;
        assertEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectZeroBitAmountWhenDecompressionAndGroup5() throws IOException {
        //given
        String inputFile = getResource("/testFiles/AlaMaKota.txt").getPath();
        String outputFile = getResource("/testFiles/C.txt").getPath();
        char compress = 'd';

        //when
        statistics = new Statistics(compress, inputFile, outputFile);
        long result = statistics.giveZeroBit();

        //then
        long expected = 5;
        assertEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectOneBitAmountWhenDecompressionAndGroup5() throws IOException {
        //given
        String inputFile = getResource("/testFiles/AlaMaKota.txt").getPath();
        String outputFile = getResource("/testFiles/C.txt").getPath();
        char compress = 'd';

        //when
        statistics = new Statistics(compress, inputFile, outputFile);
        long result = statistics.giveOneBit();

        //then
        long expected = 3;
        assertEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectZeroBitAmountWhenDecompressionAndGroup3() throws IOException {
        //given
        String inputFile = getResource("/testFiles/AlaMaKota.txt").getPath();
        String outputFile = getResource("/testFiles/C.txt").getPath();
        String treeFile = getResource("/testFiles/tree.txt").getPath();
        char compress = 'd';

        //when
        statistics = new Statistics(compress, inputFile, outputFile, treeFile);
        long result = statistics.giveZeroBit();

        //then
        long expected = 5;
        assertEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectOneBitAmountWhenDecompressionAndGroup3() throws IOException {
        //given
        String inputFile = getResource("/testFiles/AlaMaKota.txt").getPath();
        String outputFile = getResource("/testFiles/C.txt").getPath();
        String treeFile = getResource("/testFiles/tree.txt").getPath();
        char compress = 'd';

        //when
        statistics = new Statistics(compress, inputFile, outputFile, treeFile);
        long result = statistics.giveOneBit();

        //then
        long expected = 3;
        assertEquals(expected, result);
    }

    //
    @Test
    public void shouldGiveCorrectCodesQuantityWhenCompressionAndGroup5() throws IOException {
        //given
        String inputFile = getResource("/testFiles/AlaMaKota.txt").getPath();
        String outputFile = getResource("/testFiles/output.txt").getPath();
        char compress = 'c';

        //when
        statistics = new Statistics(compress, inputFile, outputFile);
        int result[] = statistics.giveCodesQuantity();

        //then
        int expected[] = new int[MAX_CHAR_VALUE];
        for (int i = 0; i < MAX_CHAR_VALUE; i++) {
            expected[i] = 0;
        }
        expected['A'] = 1;
        expected['l'] = 1;
        expected['a'] = 3;
        expected['m'] = 1;
        expected['k'] = 1;
        expected['o'] = 1;
        expected['t'] = 1;
        expected[' '] = 2;
        expected['.'] = 1;
        assertArrayEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectCodesQuantityWhenCompressionAndGroup3() throws IOException {
        //given
        String inputFile = getResource("/testFiles/AlaMaKota.txt").getPath();
        String outputFile = getResource("/testFiles/output.txt").getPath();
        String treeFile = getResource("/testFiles/tree.txt").getPath();
        char compress = 'c';

        //when
        statistics = new Statistics(compress, inputFile, outputFile, treeFile);
        int result[] = statistics.giveCodesQuantity();

        //then
        int expected[] = new int[MAX_CHAR_VALUE];
        for (int i = 0; i < MAX_CHAR_VALUE; i++) {
            expected[i] = 0;
        }
        expected['A'] = 1;
        expected['l'] = 1;
        expected['a'] = 3;
        expected['m'] = 1;
        expected['k'] = 1;
        expected['o'] = 1;
        expected['t'] = 1;
        expected[' '] = 2;
        expected['.'] = 1;
        assertArrayEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectCodesQuantityWhenDecompressionAndGroup5() throws IOException {
        //given
        String inputFile = getResource("/testFiles/AlaMaKota.txt").getPath();
        String outputFile = getResource("/testFiles/output.txt").getPath();
        char compress = 'd';

        //when
        statistics = new Statistics(compress, inputFile, outputFile);
        int result[] = statistics.giveCodesQuantity();

        //then
        int expected[] = new int[MAX_CHAR_VALUE];
        for (int i = 0; i < MAX_CHAR_VALUE; i++) {
            expected[i] = 0;
        }
        expected['o'] = 1;
        expected['u'] = 2;
        expected['t'] = 2;
        expected['p'] = 1;
        assertArrayEquals(expected, result);
    }

    @Test
    public void shouldGiveCorrectCodesQuantityWhenDecompressionAndGroup3() throws IOException {
        //given
        String inputFile = getResource("/testFiles/AlaMaKota.txt").getPath();
        String outputFile = getResource("/testFiles/output.txt").getPath();
        String treeFile = getResource("/testFiles/tree.txt").getPath();
        char compress = 'd';

        //when
        statistics = new Statistics(compress, inputFile, outputFile, treeFile);
        int result[] = statistics.giveCodesQuantity();

        //then
        int expected[] = new int[MAX_CHAR_VALUE];
        for (int i = 0; i < MAX_CHAR_VALUE; i++) {
            expected[i] = 0;
        }
        expected['o'] = 1;
        expected['u'] = 2;
        expected['t'] = 2;
        expected['p'] = 1;
        assertArrayEquals(expected, result);
    }

}
