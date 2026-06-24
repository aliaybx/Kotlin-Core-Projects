# Phone Book Search Benchmark

![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blueviolet?logo=kotlin)
![Console](https://img.shields.io/badge/Platform-Console-lightgrey)
![Algorithms](https://img.shields.io/badge/Focus-Algorithms%20%26%20Data%20Structures-red)
![Complexity](https://img.shields.io/badge/Complexity-Advanced-orange)
![Status](https://img.shields.io/badge/Status-Completed-brightgreen)

A high-performance phone book search engine that benchmarks **five different search and sort algorithms** — Linear Search, Bubble Sort + Jump Search, Quick Sort + Binary Search, and Hash Table Lookup — and displays detailed timing statistics for each approach.

---

## Overview

The Phone Book Search Benchmark reads a directory of phone book entries and a list of names to find, then executes search operations using multiple algorithmic strategies. It measures and reports execution time for each approach, providing a real-world comparison of algorithmic efficiency. This project is an excellent demonstration of **algorithm analysis, sorting techniques, search strategies, and performance benchmarking** in Kotlin.

---

## Features

- **4 Algorithmic Strategies Compared** — Runs multiple search approaches on the same dataset and prints side-by-side timing results
- **Linear Search** — Baseline brute-force search
- **Bubble Sort + Jump Search** — Sorts data with bubble sort (with time-limit fallback), then searches with jump search
- **Quick Sort + Binary Search** — Efficient sorting with quicksort followed by binary search
- **Hash Table Lookup** — Builds a `HashMap` for O(1) amortized lookup time
- **Automatic Sorting Timeout** — Bubble sort automatically stops if it exceeds 10× the linear search time, falling back to linear search
- **Detailed Time Reporting** — Prints minutes, seconds, and milliseconds for each phase (sorting, searching, total)
- **Real-World Dataset** — Processes thousands of directory entries and search queries from text files

---

## Technical Concepts Used

| Concept | Implementation |
|---|---|
| **Linear Search** | O(n) brute-force matching with `String.contains()` |
| **Bubble Sort** | O(n²) sorting with early termination on sorted pass |
| **Jump Search** | O(√n) search on sorted data with block-based jumping |
| **Quick Sort** | O(n log n) divide-and-conquer sorting with random pivot selection |
| **Binary Search** | O(log n) divide-and-conquer search on sorted data |
| **Hash Table** | O(1) average-case lookup using `HashMap` |
| **Performance Benchmarking** | `System.currentTimeMillis()` for precise timing measurements |
| **Time-Limited Execution** | Bubble sort stops after 10× linear search duration |
| **File I/O** | `File.readLines()` for reading directory and query files |

### Algorithm Complexity Comparison

| Algorithm | Time Complexity | Space Complexity |
|---|---|---|
| Linear Search | O(n) | O(1) |
| Bubble Sort + Jump Search | O(n²) + O(√n) | O(1) |
| Quick Sort + Binary Search | O(n log n) + O(log n) | O(log n) |
| Hash Table | O(n) build + O(1) lookup | O(n) |

---

## Challenges Solved

1. **Bubble Sort Timeout** — Bubble sort is notoriously slow for large datasets. Implemented a time-check after each pass: if total sorting time exceeds 10× the linear search baseline, execution stops and falls back to linear search. This prevents the program from hanging on large inputs.

2. **String Name Extraction** — Directory entries are formatted as `"<number> <name>"`. Extracting just the name portion for comparison (`substringAfter(" ")`) was essential for correct sorting and searching. Getting this wrong would break all sorted searches.

3. **Random Pivot Quick Sort** — Choosing a random pivot from the current partition range prevents worst-case O(n²) behavior on already-sorted or reverse-sorted data. Implemented using `(left..right).random()`.

4. **Jump Search on Edge Cases** — Jump search must handle the transition between blocks correctly when the target lies near block boundaries. Careful `prev`/`curr` tracking and the `minOf(curr, n)` bound ensured no index-out-of-bounds errors.

5. **Consistent Performance Comparison** — All strategies operate on the same input data and search for the same names, ensuring fair comparison. Sorting time is separated from search time for transparency.

---

## What I Learned

- Deepened understanding of **algorithmic complexity (Big O notation)** through empirical benchmarking
- Implemented **five distinct algorithms** from scratch in a single Kotlin program
- Learned the importance of **algorithm selection** — hash tables dominate for lookup-heavy workloads, while sorting + binary search excels for one-time sort + many queries
- Gained practical experience with **recursive divide-and-conquer** (quick sort) and **block-based search** (jump search)
- Understood **timeouts and fallback strategies** in algorithmic design
- Reinforced **string manipulation** skills for parsing structured text data

---

## How to Run

### Prerequisites
- [Java JDK 11+](https://adoptium.net/)
- [Kotlin Compiler](https://kotlinlang.org/docs/command-line.html)

### Input Files

The program expects two text files in the same directory:

- **`directory.txt`** — Phone book entries, one per line: `"<phone_number> <full_name>"`
- **`find.txt`** — Names to search for, one per line

### Compile and Run

```bash
# Compile
kotlinc phonebook.kt -include-runtime -d phonebook.jar

# Run
java -jar phonebook.jar
```

### Sample Output

```
Start searching (linear search)...
Found 500 / 500 entries. Time taken: 0 min. 0 sec. 987 ms.

Start searching (bubble sort + jump search)...
Found 500 / 500 entries. Time taken: 0 min. 12 sec. 345 ms.
Sorting time: 0 min. 12 sec. 100 ms. - STOPPED, moved to linear search
Searching time: 0 min. 0 sec. 245 ms.

Start searching (quick sort + binary search)...
Found 500 / 500 entries. Time taken: 0 min. 0 sec. 123 ms.
Sorting time: 0 min. 0 sec. 89 ms.
Searching time: 0 min. 0 sec. 34 ms.

Start searching (hash table)...
Found 500 / 500 entries. Time taken: 0 min. 0 sec. 97 ms.
Creating time: 0 min. 0 sec. 78 ms.
Searching time: 0 min. 0 sec. 19 ms.
```

---

## Future Improvements

- [ ] Support dynamic input file selection via command-line arguments
- [ ] Add more search algorithms (Interpolation Search, Exponential Search, Ternary Search)
- [ ] Visualize results with charts or graphs
- [ ] Implement multithreaded benchmarking for concurrent execution
- [ ] Add memory usage profiling alongside time measurement
- [ ] Write unit tests for each algorithm in isolation
- [ ] Create a web-based demo to showcase the benchmark interactively

---

## Author

**Ali Aybx** — Junior Kotlin Developer  
This project was built as part of a portfolio to demonstrate proficiency in Kotlin, algorithm design and analysis, data structures, and performance benchmarking.  
[GitHub](https://github.com/aliaybx) • [LinkedIn](https://www.linkedin.com/in/ali-ayoub-233632369/)
