package CmdCut;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Cutter {
    private final Indent indent;
    private final int begin;
    private final int end;
    private final PrintStream output;
    private final Scanner input;
    public enum Indent {
        WORD,
        CHAR
    }

    public Cutter(Indent indent, int begin, int end, String outFile, String inFile) {
        this.indent = indent;
        this.begin = begin;
        this.end = end;

        try {
            input = inFile == null ? new Scanner(System.in) : new Scanner(new BufferedReader(new FileReader(inFile)));
        } catch (FileNotFoundException | NullPointerException e) {
            throw new IllegalArgumentException("Input file error: " + e.getMessage());
        }

        try {
            output = outFile == null ? System.out : new PrintStream(new BufferedOutputStream(new FileOutputStream(outFile)));
        } catch (FileNotFoundException | NullPointerException e) {
            throw new IllegalArgumentException("Output file error: " + e.getMessage());
        }
    }

    public void start() {
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
            output.println(indent == Indent.WORD ? cutWords(line) : cutChars(line));
        }
        input.close();
        output.close();
    }

    private String cutWords(String line) {
        List<String> list = Arrays.asList(line.split(" "));
        if (list.size() < begin) return "";
        return list.subList(begin - 1, end == 0 || end > list.size() ? list.size() : end)
                .stream().sequential().collect(Collectors.joining(" "));
    }

    private String cutChars(String line) {
        if (line.length() < begin) return "";
        return line.substring(begin - 1, end == 0 || end > line.length() ? line.length() : end);
    }
}