package CmdCut;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Cutter {
    private final boolean indentWord;
    private final int begin;
    private final int end;
    private PrintStream output;
    private Scanner input;

    public Cutter(boolean indentWord, int begin, int end, String outFile, String inFile) {
        this.indentWord = indentWord;
        this.begin = begin;
        this.end = end;

        try {
            input = inFile.isBlank() ? new Scanner(System.in) : new Scanner(new BufferedReader(new FileReader(inFile)));
        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println(e.getMessage());
        }

        try {
            output = outFile.isBlank() ? System.out : new PrintStream(new BufferedOutputStream(new FileOutputStream(outFile)));
        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void start() {
        String line;
        while (input.hasNextLine()) {
            line = input.nextLine();
            output.println(indentWord ? cutWords(line) : cutChars(line));
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