package CmdCut;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class CutLauncherTest {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();

    private void assertFileEmpty(File file) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String rd = bf.readLine();
        assertNull(rd);
    }

    private void assertFileNotEmpty(File file) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String rd = bf.readLine();
        assertNotNull(rd);
    }

    //Returns temporary output file
    private File setUp() throws IOException {
        File temp = File.createTempFile("tempL-", ".txt");
        temp.deleteOnExit();
        System.setErr(new PrintStream(temp));
        return temp;
    }

    @Test
    public void launchWithoutCW() throws IOException {
        File file = setUp();
        CutLauncher.main("3-5".split(" "));
        assertFileNotEmpty(file);
    }

    @Test
    public void launchWithCW() throws IOException {
        File file = setUp();
        CutLauncher.main("-c -w 3-5".split(" "));
        assertFileNotEmpty(file);
    }

    @Test
    public void incorrectRangeFirst() throws IOException {
        File file = setUp();
        CutLauncher.main("-c 0-5".split(" "));
        assertFileNotEmpty(file);
    }

    @Test
    public void incorrectRangeSecond() throws IOException {
        File file = setUp();
        CutLauncher.main("-w 5-1".split(" "));
        assertFileNotEmpty(file);
    }

    @Test
    public void incorrectRangeThird() throws IOException {
        File file = setUp();
        CutLauncher.main("-w lsd".split(" "));
        assertFileNotEmpty(file);
    }

    @Test
    public void incorrectPath() throws IOException {
        File file = setUp();
        CutLauncher.main("-w -i teuxt.txt 5-1".split(" "));
        assertFileNotEmpty(file);
    }

    @Test
    public void oneCorrect() throws IOException {
        File file = setUp();
        File temp = File.createTempFile("tempL-", ".txt");
        temp.deleteOnExit();
        String path = classloader.getResource("inputs/1_text.txt").getPath();
        CutLauncher.main(("-w -i " + path + " -o " + temp.getPath() + " 3-5").split(" "));
        assertFileEmpty(file);
    }
}