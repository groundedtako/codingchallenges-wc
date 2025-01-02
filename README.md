# Checksum Calculator

A simple command-line tool to calculate file checksums using various algorithms (SHA-1, SHA-256, MD5).

## Requirements

- Java 17 or higher

## Installation

1. Download `checksum.jar` from the releases
2. (Optional) Add the directory containing `checksum.jar` to your PATH

## Usage

You can run the tool in two ways:

### Using java -jar directly:
```bash
java -jar checksum.jar --algorithm SHA-1 file.txt
```

### Using the provided scripts:
Windows:
```bash
checksum.bat --algorithm SHA-1 file.txt
```

Unix/Linux/MacOS:
```bash
./checksum.sh --algorithm SHA-1 file.txt
```

### Options

- `--algorithm, -a`: Specify the hash algorithm (default: SHA-256)
  - Supported algorithms: MD5, SHA-1, SHA-256
- `--help, -h`: Show help message
- `--version, -V`: Show version information

### Examples

Calculate SHA-1 hash:
```bash
java -jar checksum.jar --algorithm SHA-1 myfile.txt
```

Calculate default SHA-256 hash:
```bash
java -jar checksum.jar myfile.txt
```

## Building from Source

If you want to build the project yourself:

1. Clone the repository
2. Run `./gradlew build` (or `gradlew.bat build` on Windows)
3. Find the built jar in `build/libs/checksum.jar`
