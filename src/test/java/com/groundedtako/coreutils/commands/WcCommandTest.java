package com.groundedtako.coreutils.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WcCommandTest {
    private WcCommand wcCommand;
    
    @BeforeEach
    void setUp() {
        wcCommand = new WcCommand();
    }

    @Test
    void testWcWithSingleFile(@TempDir Path tempDir) throws IOException {
        // Create a test file
        Path testFile = tempDir.resolve("test.txt");
        String content = "Hello World\nThis is a test\nThird line";
        Files.writeString(testFile, content);

        // Test with all options
        int result = wcCommand.execute(new String[]{
            "-l", "-w", "-c", testFile.toString()
        });

        assertEquals(0, result);
    }

    @Test
    void testWcWithMultipleFiles(@TempDir Path tempDir) throws IOException {
        // Create test files
        Path file1 = tempDir.resolve("file1.txt");
        Path file2 = tempDir.resolve("file2.txt");
        
        Files.writeString(file1, "Hello World\nTest file 1");
        Files.writeString(file2, "Another test\nWith more lines\nAnd content");

        int result = wcCommand.execute(new String[]{
            "-l", "-w", "-c",
            file1.toString(),
            file2.toString()
        });

        assertEquals(0, result);
    }

    @Test
    void testWcWithNoOptions(@TempDir Path tempDir) throws IOException {
        Path testFile = tempDir.resolve("test.txt");
        Files.writeString(testFile, "Hello World\nTest file");

        int result = wcCommand.execute(new String[]{testFile.toString()});
        assertEquals(0, result);
    }

    @Test
    void testByteCount(@TempDir Path tempDir) throws IOException {
        // Create a test file with known content
        Path testFile = tempDir.resolve("test.txt");
        String content = "Hello\nWorld\n";
        Files.writeString(testFile, content);

        // Get actual file size after writing (handles platform-specific line endings)
        long expectedBytes = Files.size(testFile);
        
        // Print debug info before redirecting stdout
        System.out.println("File size: " + expectedBytes + " bytes");

        // Redirect stdout to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            wcCommand.execute(new String[]{"-c", testFile.toString()});
            String output = outContent.toString().trim();
            
            // Restore stdout before printing debug info
            System.setOut(originalOut);
            System.out.println("Actual output: '" + output + "'");
            
            assertTrue(output.startsWith(""+expectedBytes));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testLineCount(@TempDir Path tempDir) throws IOException {
        // Create a test file with known content
        Path testFile = tempDir.resolve("test.txt");
        String content = "Hello\nWorld\n";
        Files.writeString(testFile, content);

        // Get actual file size after writing (handles platform-specific line endings)
        long expectedLines = content.split("\n").length;
        
        // Print debug info before redirecting stdout
        System.out.println("Line count: " + expectedLines + " lines");

        // Redirect stdout to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            wcCommand.execute(new String[]{"-l", testFile.toString()});
            String output = outContent.toString().trim();
            
            // Restore stdout before printing debug info
            System.setOut(originalOut);
            System.out.println("Actual output: '" + output + "'");
            
            assertTrue(output.startsWith(""+expectedLines));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testWordCount(@TempDir Path tempDir) throws IOException {
        // Create a test file with known content
        Path testFile = tempDir.resolve("test.txt");
        String content = "Hello World";
        Files.writeString(testFile, content);

        // Get actual file size after writing (handles platform-specific line endings)
        long expectedWords = content.split("\\s+").length;
        
        // Print debug info before redirecting stdout
        System.out.println("Word count: " + expectedWords + " words");

        // Redirect stdout to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            wcCommand.execute(new String[]{"-w", testFile.toString()});
            String output = outContent.toString().trim();
            
            // Restore stdout before printing debug info
            System.setOut(originalOut);
            System.out.println("Actual output: '" + output + "'");
            
            assertTrue(output.startsWith(""+expectedWords));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testByteCountUsingTestFile() throws IOException {
        // read from the test file
        Path testFile = Path.of("src/test/resources/test.txt");

        // Get actual file size after writing (handles platform-specific line endings)
        long expectedBytes = Files.size(testFile);

        // Print debug info before redirecting stdout
        System.out.println("File size: " + expectedBytes + " bytes");

        // Redirect stdout to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            wcCommand.execute(new String[]{"-c", testFile.toString()});
            String output = outContent.toString().trim();

            // Restore stdout before printing debug info
            System.setOut(originalOut);
            System.out.println("Actual output: '" + output + "'");

            assertTrue(output.startsWith(""+expectedBytes));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testLineCountUsingTestFile() throws IOException {
        // read from the test file
        Path testFile = Path.of("src/test/resources/test.txt");

        // Get actual file size after writing (handles platform-specific line endings)
        long expectedLines = Files.lines(testFile).count();

        // Print debug info before redirecting stdout
        System.out.println("Line count: " + expectedLines + " lines");

        // Redirect stdout to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            wcCommand.execute(new String[]{"-l", testFile.toString()});
            String output = outContent.toString().trim();

            // Restore stdout before printing debug info
            System.setOut(originalOut);
            System.out.println("Actual output: '" + output + "'");

            assertTrue(output.startsWith(""+expectedLines));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void testWordCountUsingTestFile() throws IOException {
        // read from the test file
        Path testFile = Path.of("src/test/resources/test.txt");

        // we know that the test file has 58164 words
        long expectedWords = 58164L;

        // Print debug info before redirecting stdout
        System.out.println("Word count: " + expectedWords + " words");

        // Redirect stdout to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            wcCommand.execute(new String[]{"-w", testFile.toString()});
            String output = outContent.toString().trim();

            // Restore stdout before printing debug info
            System.setOut(originalOut);
            System.out.println("Actual output: '" + output + "'");

            assertTrue(output.startsWith(""+expectedWords));
        } finally {
            System.setOut(originalOut);
        }
    }


    @Test
    void testProcessStdin() throws IOException {
        // Prepare the input data
        String inputData = "Line 1\nLine 2\nLine 3\n";
        InputStream inputStream = new ByteArrayInputStream(inputData.getBytes());
        System.setIn(inputStream); // Redirect System.in

        try {
            // Call the method under test
            WcCommand.CountResult result = wcCommand.processStdin();

            // Verify the result
            assertEquals(3, result.lines());
            assertEquals(6, result.words());
            assertEquals(21, result.bytes());
        } finally {
            // Restore System.in
            System.setIn(System.in);
        }
    }
}   
