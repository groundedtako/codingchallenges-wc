# Word Count (wc) Utility

A Java implementation of the Unix `wc` utility that counts lines, words, and bytes in text files.

## Features

- Count lines (`-l`, `--lines`)
- Count words (`-w`, `--words`)
- Count bytes (`-c`, `--bytes`)
- Count characters (`-m`, `--chars`)
- Process multiple files at once
- Read from standard input when no files are specified
- Display totals when processing multiple files

## Framework and Technologies

- **Java**: Core implementation language
- **Gradle**: Build automation and dependency management
- **Picocli**: Command-line argument parsing framework
- **JUnit 5**: Testing framework
- **Mockito**: Mocking framework for testing
- **JaCoCo**: Code coverage reporting

## Design

The project follows a modular design with clear separation of concerns:

- `Main`: Entry point that handles command registration and execution
- `Command`: Interface defining the contract for all command implementations
- `WcCommand`: Core implementation of the word count functionality
  - Implements both the `Command` interface and Picocli's `Callable`
  - Uses the Command pattern for extensibility
  - Handles both file and stdin input streams
  - Preserves platform-specific line endings (CRLF/LF)

## Testing

Comprehensive test suite covering various scenarios:

- Single file processing
- Multiple file processing
- Standard input processing
- Different counting options:
  - Line counting
  - Word counting
  - Byte counting
- Edge cases:
  - Empty files
  - Files with different line endings
  - Non-existent files
  - Invalid options

## Building and Running

```bash
# Build the project
./gradlew build

# Run the application
java -jar build/libs/coreutils.jar wc [options] [file...]

# Examples
java -jar build/libs/coreutils.jar wc -l file.txt     # Count lines
java -jar build/libs/coreutils.jar wc -w file.txt     # Count words
java -jar build/libs/coreutils.jar wc -c file.txt     # Count bytes
java -jar build/libs/coreutils.jar wc file1.txt file2.txt  # Process multiple files
```

## Dependencies

- Picocli 4.7.6: Command-line parsing
- SLF4J 2.0.9: Logging facade
- Logback 1.4.11: Logging implementation
- JUnit Jupiter 5.9.1: Testing
- Mockito 5.8.0: Mocking framework

## Code Coverage

The project uses JaCoCo for code coverage reporting. Coverage reports can be generated using:

```bash
./gradlew jacocoTestReport
```

Reports are available in `build/jacocoHtml/`.