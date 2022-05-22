package CmdCut;

public class Cutter {
    private final boolean indentWord;
    private final int begin;
    private final int end;

    public Cutter(boolean indentWord, int begin, int end, String outFile, String inFile) {
        this.indentWord = indentWord;
        this.begin = begin;
        this.end = end;
    }

}