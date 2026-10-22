# Java CSV Data Processing Project

This project is a Java console application designed to read, process, and analyze mock personal data from multiple CSV files. It demonstrates core Java concepts such as file I/O, object-oriented modeling, multithreading, sorting, filtering, and stream-based data processing.

The program reads records containing personal information, stores them in memory, and presents a simple menu-driven interface where the user can view, filter, and sort the dataset.

## Project purpose

The application was built as a practical exercise in:

- reading structured data from CSV files
- parsing and validating text-based input
- creating Java model classes
- working with collections and maps
- parallel file loading with threads
- filtering and sorting datasets using Java Streams
- building a simple interactive command-line user interface

## Main functionality

The program supports the following actions through a terminal menu:

1. Display all records
2. Filter records by birth date range
3. Sort records alphabetically by name or surname
4. Sort records by ID number
5. Exit the application

## Data model

Each record is mapped to a `Zmogus` object (Lithuanian for “person”), which contains:

- `id`
- `vardas` (first name)
- `pavarde` (surname)
- `elektronisPastas` (email)
- `lytis` (gender)
- `salis` (country)
- `svetaine` (website)
- `gimimoData` (birth date)

The project also contains helper types such as:

- `Zmogus2` — a simplified object used for selected person data
- `Duomenys` — a runnable class responsible for loading one CSV file

## CSV input files

The program expects these files in the project root directory:

- `MOCK_DATA1.csv`
- `MOCK_DATA2.csv`
- `MOCK_DATA3.csv`

These files are loaded concurrently by separate threads, and the parsed data is added into a synchronized list.

## Project structure

```text
6 labaratorinis/
├── README.md
├── MOCK_DATA1.csv
├── MOCK_DATA2.csv
├── MOCK_DATA3.csv
├── 6 labaratorinis.iml
└── src/
    └── Main.java
```

## Technical implementation overview

### 1. Data loading

Each CSV file is processed by a `Duomenys` thread.

- A `BufferedReader` reads the file line by line
- The header row is skipped
- Each subsequent row is split by commas
- A new `Zmogus` object is created from the data
- The object is added to a synchronized list

This approach allows the app to load several files at the same time and avoid data races.

### 2. Data parsing

Each CSV row is expected to contain 8 values:

```text
id,first_name,last_name,email,gender,country,website,date_of_birth
```

The parser converts the date string into a `java.util.Date` object using a `SimpleDateFormat` with the format:

```java
yyyy-MM-dd
```

### 3. Sorting and filtering

The application uses Java Streams to:

- sort by first name or surname
- sort by ID ascending or descending
- filter records by a date range

Examples of operations included in the code:

- `sarasas.stream().sorted(...)`
- `sarasas.stream().filter(...)`
- `Collectors.toMap(...)`

### 4. Gender-specific processing

The code also includes logic for creating a map based on male users only, using their first and last name initials as the key:

- first two letters of the first name
- first two letters of the surname

Example:

```text
John Smith -> JOsm
```

This is implemented in the `getVardPav()` method and later used in a `Map<String, Zmogus2>`.

## Menu behavior

When the program starts, it displays a menu like this:

```text
Pasirinkite ka norite daryti
1. Rodyti sarasa
2. Filtruoti pagal gimimo data
3. Rikiuoti pagal abecele
4. Rikiuoti pagal ID
5. Iseiti
```

### Option explanations

- 1: Print all records from the dataset
- 2: Ask for a date range and print all people whose birthday falls between them
- 3: Sort by name or surname in ascending/descending order
- 4: Sort by record ID in ascending/descending order
- 5: Exit the program

## Example workflow

A typical run of the program might look like this:

```text
Pasirinkite ka norite daryti
1. Rodyti sarasa
2. Filtruoti pagal gimimo data
3. Rikiuoti pagal abecele
4. Rikiuoti pagal ID
5. Iseiti

> 1
```

The program prints all records from all three source files.

Then the user can choose another option, for example:

```text
> 3
Pasirininkite pagal ka rikiuoti
1. Vardas nuo didziausio
2. Vardas nuo maziausio
3. Pavarde nuo didziausio
4. Pavarde nuo maziausio
```

The list is then sorted and displayed in the console.

## Requirements

To run this project, you need:

- Java JDK 8 or newer
- A terminal, IDE, or Java-compatible environment
- The CSV files placed in the project root directory

## How to compile and run

From the project root directory, compile the application:

```bash
javac -d out src/Main.java
```

Then run it:

```bash
java -cp out Main
```

If your Java compiler is configured differently in your IDE, the same code can be run directly from IntelliJ IDEA, Eclipse, or another Java IDE.

## Important notes

- The project uses relative paths to the CSV files. That means the files must remain in the current working directory when the program runs.
- The CSV parsing is simple and assumes a clean format without additional quoting rules.
- The code includes synchronous access (`Collections.synchronizedList`) and thread joins to reduce concurrency issues while loading data.
- The program is console-based and does not include a graphical user interface.

## Learning value

This project is a solid example of:

- object-oriented Java design
- practical data processing with CSV input
- multithreading and synchronization
- stream API usage in real code
- working with user input in a menu-driven application

## Summary

In short, this project loads mock personal records from multiple CSV files, stores them in memory, and allows the user to filter and sort the data through an interactive Java console application. It is a beginner-to-intermediate level Java project that covers important real-world programming patterns used in data processing applications.