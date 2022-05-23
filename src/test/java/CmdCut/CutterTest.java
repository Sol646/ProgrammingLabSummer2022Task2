package CmdCut;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

class CutterTest {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();

    private void assertFileContent(File actualFile, File expectedFile) {
        String expected = null;
        String actual = null;
        try {
            expected = FileUtils.readFileToString(expectedFile);
            actual = FileUtils.readFileToString(actualFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void testChars() throws IOException {
        File input = new File(classloader.getResource("inputs/1_text.txt").getFile());
        File expected = new File(classloader.getResource("outputs/1_otext.txt").getFile());
        File temp = File.createTempFile("temp-", ".txt");
        temp.deleteOnExit();
        new Cutter(Cutter.Indent.CHAR, 5, 13, temp.getAbsolutePath(), input.getAbsolutePath()).start();
        assertFileContent(temp, expected);
    }

    @Test
    public void testWords() throws IOException {
        File input = new File(classloader.getResource("inputs/2_text.txt").getFile());
        File expected = new File(classloader.getResource("outputs/2_otext.txt").getFile());
        File temp = File.createTempFile("temp-", ".txt");
        temp.deleteOnExit();
        new Cutter(Cutter.Indent.WORD, 4, 11, temp.getAbsolutePath(), input.getAbsolutePath()).start();
        assertFileContent(temp, expected);
    }

    @Test
    public void testRange() throws IOException {
        File input = new File(classloader.getResource("inputs/2_text.txt").getFile());
        File expected = new File(classloader.getResource("outputs/3_otext.txt").getFile());
        File temp = File.createTempFile("temp-", ".txt");
        temp.deleteOnExit();
        new Cutter(Cutter.Indent.WORD, 4, 0, temp.getAbsolutePath(), input.getAbsolutePath()).start();
        assertFileContent(temp, expected);
    }
}