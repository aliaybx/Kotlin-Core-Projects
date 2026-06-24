# Kotlin Core Projects

![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blueviolet?logo=kotlin&logoColor=white)
![Java](https://img.shields.io/badge/Java-JDK%2011%2B-orange?logo=openjdk&logoColor=white)
![Console](https://img.shields.io/badge/Platform-Console-lightgrey)
![Algorithms](https://img.shields.io/badge/Skills-Algorithms%20%26%20Data%20Structures-red)
![OOP](https://img.shields.io/badge/Skills-OOP-blue)
![Image Processing](https://img.shields.io/badge/Skills-Image%20Processing-green)
![License](https://img.shields.io/badge/License-MIT-yellow)
![Portfolio](https://img.shields.io/badge/Status-Portfolio%20Ready-brightgreen)

A curated collection of **four standalone Kotlin console applications** demonstrating core programming concepts, algorithmic thinking, object-oriented design, and real-world problem-solving. Each project is self-contained, production-quality, and designed to showcase the skills expected of a professional Kotlin/Android developer.

---

## 📋 Project Overview

| # | Project | Description | Key Skills |
|---|---------|-------------|------------|
| 1 | **Smart Calculator** | A REPL calculator with variable assignment, operator precedence, and big integer arithmetic | Shunting-Yard algorithm, postfix evaluation, tokenization, BigInteger API |
| 2 | **Contacts Manager** | An OOP contact management system with person/organization records, search, and persistent storage | Abstract classes, inheritance, polymorphism, Java serialization, regex validation |
| 3 | **Phone Book Search Benchmark** | A performance benchmark comparing 5 search/sort algorithms with detailed timing statistics | Linear/binary/jump search, bubble/quick sort, hash tables, algorithmic complexity |
| 4 | **Image Watermark Processor** | A pixel-level image watermarking tool with alpha channel support and configurable blending | BufferedImage API, alpha compositing, weighted blending, input validation |

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| **Kotlin** | Primary programming language for all projects |
| **Java JDK 11+** | Runtime environment and standard library access |
| **Java AWT** | Image processing (`BufferedImage`, `Color`, `Transparency`) |
| **Java I/O & Serialization** | File persistence (`ObjectOutputStream`, `ObjectInputStream`) |
| **Java Time API** | Timestamp management (`LocalDateTime`) |
| **Java Math API** | Arbitrary-precision arithmetic (`BigInteger`) |
| **Java Collections** | Data structures (`Stack`, `Map`, `HashMap`, `List`) |

---

## 🎯 Skills Demonstrated

### Programming Paradigms
- **Object-Oriented Programming** — Abstract classes, inheritance, polymorphism, encapsulation
- **Functional Programming** — Collection operations, lambdas, scope functions
- **Procedural Programming** — Well-structured function decomposition in utility modules

### Algorithms & Data Structures
- **Sorting** — Quick Sort (random pivot), Bubble Sort (with timeout)
- **Searching** — Linear Search, Jump Search, Binary Search
- **Parsing** — Shunting-Yard algorithm, infix-to-postfix conversion, postfix evaluation
- **Data Structures** — Stack, Map/HashMap, List, MutableMap

### Software Engineering
- **Input Validation** — Comprehensive error handling for all user inputs
- **Defensive Programming** — Null safety, edge case handling, graceful degradation
- **File I/O** — Reading/writing text files, binary serialization, image file handling
- **Performance Benchmarking** — Empirical algorithm comparison with precise timing

### Language-Specific
- **Kotlin Standard Library** — Regex, collections, string manipulation
- **Java Interoperability** — Seamless use of Java APIs from Kotlin code
- **`readln()` / `Scanner`** — Interactive console input handling

---

## 📁 Repository Structure

```
Kotlin-Core-Projects/
├── Calculator/
│   ├── calculator.kt          # Smart Calculator source code
│   └── README.md              # Calculator documentation
│
├── Contacts/
│   ├── contacts.kt            # Contacts Manager source code
│   └── README.md              # Contacts documentation
│
├── PhoneBook/
│   ├── phonebook.kt           # Phone Book Search Benchmark source code
│   └── README.md              # PhoneBook documentation
│
├── Watermark/
│   ├── watermark.kt           # Image Watermark Processor source code
│   └── README.md              # Watermark documentation
│
├── README.md                  # You are here
└── LICENSE                    # MIT License
```

---

## 📚 Learning Outcomes

Through building these projects, I developed and reinforced the following competencies:

| Competency | Evidence |
|---|---|
| **Algorithm Implementation** | Implemented 5+ algorithms from scratch (Shunting-Yard, Quick Sort, Binary Search, Jump Search, Hash Tables) |
| **Code Organization** | Each project is a single well-structured `.kt` file with clear function decomposition and documentation |
| **Error Handling** | Every project validates inputs comprehensively and provides descriptive error messages |
| **Data Persistence** | Contacts Manager uses Java serialization; PhoneBook reads structured text files |
| **Performance Awareness** | PhoneBook benchmarks algorithmic performance with millisecond precision |
| **Image Processing** | Watermark processes images at the pixel level with alpha compositing and blending |
| **Clean Code** | Meaningful variable names, consistent formatting, documentation comments where appropriate |

---

## 🚀 Getting Started

### Prerequisites

- [Java JDK 11+](https://adoptium.net/) (or OpenJDK 11+)
- [Kotlin Compiler](https://kotlinlang.org/docs/command-line.html) (latest version recommended)

### Run Any Project

```bash
# Navigate to the project directory
cd Kotlin-Core-Projects/<ProjectName>

# Compile
kotlinc <filename>.kt -include-runtime -d <filename>.jar

# Run
java -jar <filename>.jar
```

### Quick Start

```bash
# Example: Run the Smart Calculator
cd Kotlin-Core-Projects/Calculator
kotlinc calculator.kt -include-runtime -d calculator.jar
java -jar calculator.jar
```

---

## 📄 License

This repository is available under the [MIT License](LICENSE). Feel free to use, modify, and distribute the code for learning and portfolio purposes.

---

## 👨‍💻 Author

**Ali Aybx** — Junior Kotlin & Android Developer

I am a passionate junior developer building a strong foundation in **Kotlin**, **algorithm design**, **object-oriented programming**, and **software engineering best practices**. This portfolio represents my commitment to writing clean, well-documented, and functionally complete code.

**What I'm working on:**
- Deepening my understanding of Android development with Jetpack Compose
- Exploring Kotlin Multiplatform for cross-platform applications
- Building real-world projects that solve practical problems

**Connect with me:**
- [GitHub](https://github.com/aliaybx)
- [LinkedIn](https://www.linkedin.com/in/ali-ayoub-233632369/)

---

> *"First, solve the problem. Then, write the code."* — John Johnson

⭐ If you find this repository helpful or inspiring, consider giving it a star!
