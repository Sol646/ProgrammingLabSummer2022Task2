/*
Вариант 8 -- cut
Выделение из каждой строки текстового файла некоторой подстроки:
● -i file задаёт имя входного файла. Если параметр отсутствует, следует считывать
входные данные с консольного ввода;+
● Флаг -o ofile задаёт имя выходного файла (в данном случае ofile). Если
параметр отсутствует, следует выводить результат на консольный вывод.+
● Флаг -с означает, что все числовые параметры задают отступы в символах
(буквах) входного файла.
● Флаг -w означает, что все числовые параметры задают отступы в словах (т.е.
последовательностях символов без пробелов) входного файла.
● Параметр range задаёт выходной диапазон и имеет один из следующих видов
(здесь N и К -- целые числа):
○ 1-K диапазон от начала строки до K
○ N- диапазон от N до конца строки
○ N-K диапазон от N до K
Command line: cut [-c|-w] [-o ofile] [file] range
Программа построчно обрабатывает входные данные и для каждой строки выдаёт
часть этой строки согласно заданному диапазону. Если какого-то из указанных файлов
не существует или неправильно указаны параметры -c и -w (должен быть указан ровно
один из них), следует выдать ошибку. Если в строке не хватает символов или слов, это
ошибкой не является, в этом случае следует выдать ту часть входных данных, которая
попадает в диапазон.
Кроме самой программы, следует написать автоматические тесты к ней.
 */
package CmdCut;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.Arrays;
import java.util.regex.Pattern;

public class CutLauncher {
    @Option(name = "-c", metaVar = "indentChar", usage = "required indent in chars", forbids = {"-w"})
    private boolean indentChar;

    @Option(name = "-w", metaVar = "indentWord", usage = "required indent in words", forbids = {"-c"})
    private boolean indentWord;

    @Option(name = "-i", metaVar = "inFile", usage = "Name of input file")
    private String inFile;

    @Option(name = "-o", metaVar = "oFile", usage = "Name of output file")
    private String outFile;

    @Argument(required = true, metaVar = "range", usage = "range from N to K")
    private String range;

    public static void main(String[] args) {
        new CutLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            if (!(indentChar || indentWord)) throw new IllegalArgumentException("-c or -w is required");
            if (!Pattern.matches("\\d+-\\d*", range)) throw new IllegalArgumentException("incorrect range");
            int[] intRange = Arrays.stream(range.split("-")).mapToInt(Integer::parseInt).toArray();
            if (intRange[0] == 0 || (intRange.length == 2 && intRange[0] > intRange[1]))
                throw new IllegalArgumentException("incorrect range");
            Cutter cutter = new Cutter(indentWord, intRange[0], intRange.length == 2 ? intRange[1] : 0, outFile, inFile);
            cutter.start();
        } catch (CmdLineException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar cutter.jar [-c|-w] [-i infile] [-o oFile] range");
            parser.printUsage(System.err);
        }
    }
}
