package com.groundedtako.coreutils.commands;

import com.groundedtako.coreutils.api.Command;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@CommandLine.Command(
    name = "wc",
    description = "Print newline, word, and byte counts for each file"
)
public class WcCommand implements Command, Callable<Integer> {
    @Option(names = {"-l", "--lines"}, description = "Print the newline counts")
    private boolean countLines;

    @Option(names = {"-w", "--words"}, description = "Print the word counts")
    private boolean countWords;

    @Option(names = {"-c", "--bytes"}, description = "Print the byte counts")
    private boolean countBytes;

    @Option(names = {"-m", "--chars"}, description = "Print the character counts")
    private boolean localeBytes;

    @Parameters(description = "Files to process")
    private List<File> files = new ArrayList<>();

    @Override
    public Integer call() throws Exception {
        // If no options specified, show all counts
        if (!countLines && !countWords && !countBytes && !localeBytes) {
            countLines = countWords = countBytes = localeBytes = true;
        }

        // If no files specified, read from stdin

        if (files.isEmpty()) {
            processStdin();
        } else {
            processFiles();
        }

        return 0;
    }

    protected CountResult processStdin() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        
        try (InputStream in = System.in) {
            while ((bytesRead = in.read(buffer)) != -1) {
                bytes.write(buffer, 0, bytesRead);
            }
        }
        
        CountResult result = countContent(bytes.toByteArray());
        printCounts(result, "");
        return result;
    }

    private CountResult processFiles() {
        long totalLines = 0;
        long totalWords = 0;
        long totalBytes = 0;

        CountResult totalResult = null;
        for (File file : files) {
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                CountResult result = countContent(bytes);
                printCounts(result, file.getName());
                
                totalLines += result.lines;
                totalWords += result.words;
                totalBytes += result.bytes;
            } catch (IOException e) {
                System.err.printf("wc: %s: %s%n", file.getName(), e.getMessage());
            }
        }

        // Print totals if more than one file
        if (files.size() > 1) {
            totalResult = new CountResult(totalLines, totalWords, totalBytes);
            printCounts(totalResult, "total");
        }
        return totalResult;
    }

    private CountResult countContent(byte[] fileInBytes) throws IOException {
        long lines = 0;
        long words = 0;
        long bytes = fileInBytes.length;  // Count all bytes directly

        // Count words by spaces
        var isWord = false;

        for (byte b : fileInBytes) {
            // count lines by newline characters
            if (b == '\n') {
                lines++;
            }

            // words
            if (Character.isWhitespace(b)) {
                if (isWord) {
                    words++;
                    isWord = false;
                }
            } else {
                isWord = true;
            }
        }
        // handle the last word
        if (isWord) {
            words++;
        }
        
        return new CountResult(lines, words, bytes);
    }

    private void printCounts(CountResult result, String filename) {
        StringBuilder output = new StringBuilder();
        
        if (countLines) output.append(String.format("%8d", result.lines));
        if (countWords) output.append(String.format("%8d", result.words));
        if (countBytes || localeBytes) output.append(String.format("%8d", result.bytes));
        
        if (!filename.isEmpty()) {
            output.append(" ").append(filename);
        }
        
        System.out.println(output);
    }

    @Override
    public int execute(String[] args) {
        return new CommandLine(this).execute(args);
    }

    @Override
    public String getDescription() {
        return "Print newline, word, and byte counts for each file";
    }

    @Override
    public String getUsage() {
        return "Usage: wc [-l] [-w] [-c] [-m] [file...]";
    }

    protected record CountResult(long lines, long words, long bytes) {
    }
}
