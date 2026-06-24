# Contacts Manager

![Kotlin](https://img.shields.io/badge/Kotlin-2.0-blueviolet?logo=kotlin)
![Console](https://img.shields.io/badge/Platform-Console-lightgrey)
![OOP](https://img.shields.io/badge/Paradigm-OOP-blue)
![Serialization](https://img.shields.io/badge/Feature-Serialization-orange)
![Status](https://img.shields.io/badge/Status-Completed-brightgreen)

A fully object-oriented contact management system built in Kotlin that supports **person and organization records**, **field-level editing**, **search with regex**, **persistent storage via serialization**, and a **menu-driven interface**. This project demonstrates OOP principles, file I/O, data validation, and interactive CLI design.

---

## Overview

The Contacts Manager is a console-based application that allows users to create, view, edit, search, and delete contacts. It distinguishes between two types of records — **Person** and **Organization** — each with its own set of fields. All data is persisted to disk using Java serialization, ensuring records survive between sessions.

---

## Features

- **Dual Record Types** — Supports `Person` (name, surname, birth date, gender, phone number) and `Organization` (name, address, phone number)
- **CRUD Operations** — Full Create, Read, Update, Delete functionality
- **Field-Level Editing** — Edit individual fields of any record
- **Search with Regex** — Search all records using regular expression pattern matching (case-insensitive)
- **Persistent Storage** — Automatically saves and loads records via `ObjectOutputStream`/`ObjectInputStream`
- **Phone Number Validation** — Validates phone numbers against a robust regex pattern (supports international formats, parentheses, and spaced groups)
- **Birth Date & Gender Validation** — Gracefully handles invalid or missing data
- **Timestamps** — Automatically tracks creation and last edit times for every record
- **Menu-Driven Interface** — Intuitive nested menus: `[menu]`, `[list]`, `[search]`, `[record]`

---

## Technical Concepts Used

| Concept | Implementation |
|---|---|
| **Abstract Classes & Inheritance** | `Record` abstract base class with `Person` and `Organization` subclasses |
| **Polymorphism** | Overridden `shortInfo()`, `fullInfo()`, `getFields()`, `setField()` methods |
| **Java Serialization** | `ObjectOutputStream`/`ObjectInputStream` for persistent file storage |
| **Regex Validation** | Phone number format validation with complex regex pattern |
| **Data Encapsulation** | Private `phoneNumber` field with getter/setter pattern |
| **`LocalDateTime`** | Automatic creation and modification timestamps |
| **`List` & `MutableList`** | Dynamic record collection management |
| **File I/O** | `File` API with existence checks and error handling |

---

## Challenges Solved

1. **Polymorphic Serialization** — Serializing a `List<Record>` where the actual objects are `Person` or `Organization` instances required careful `Serializable` implementation on the abstract base class and proper unchecked casting on deserialization.

2. **Phone Number Validation** — The phone number regex `^\+?(\([A-Za-z0-9]+\)|[A-Za-z0-9]+)([ -](\([A-Za-z0-9]{2,}\)|[A-Za-z0-9]{2,}))*$` supports international formats with optional country codes, parenthesized area codes, and space/dash separators. Ensuring this covered edge cases (empty input, mixed separators) required iterative refinement.

3. **Graceful Degradation on Invalid Input** — For birth dates and gender, the system doesn't crash but instead stores `"[no data]"` and prints a warning, preserving the record structure while informing the user.

4. **Search with Regex on Multiple Fields** — Implementing `searchAction()` required compiling a user-provided regex, checking it against concatenated field values (`getAllValues()`), and supporting paginated results with actions (`back`, `again`, select a record).

5. **Nested Menu Navigation** — The interface has multiple levels (`menu → list → record → edit/delete`, `menu → search → record`) with clean state transitions and proper error handling for invalid inputs.

---

## What I Learned

- Designed and implemented an **abstract class hierarchy** in Kotlin
- Deepened understanding of **polymorphism** — calling overridden methods through a base class reference
- Gained hands-on experience with **Java serialization for data persistence**
- Practiced **input validation** with complex regex patterns
- Learned **defensive coding** — handling nulls, missing files, and invalid user input gracefully
- Understood the **`Serializable` interface** and its pitfalls (class versioning, transient fields)

---

## How to Run

### Prerequisites
- [Java JDK 11+](https://adoptium.net/)
- [Kotlin Compiler](https://kotlinlang.org/docs/command-line.html)

### Compile and Run

```bash
# Compile
kotlinc contacts.kt -include-runtime -d contacts.jar

# Run (with optional file for persistent storage)
java -jar contacts.jar contacts.dat
```

### Usage Example

```
[menu] Enter action (add, list, search, count, exit): add
Enter the type (person, organization): person
Enter the name: John
Enter the surname: Doe
Enter the birth date: 1990-05-15
Enter the gender (M, F): M
Enter the number: +1 (555) 123-4567
The record added.

[menu] Enter action (add, list, search, count, exit): list
1. John Doe

[menu] Enter action (add, list, search, count, exit): count
The Phone Book has 1 records.

[menu] Enter action (add, list, search, count, exit): exit
```

---

## Future Improvements

- [ ] Add a graphical user interface (JavaFX or Compose Multiplatform)
- [ ] Implement contact grouping/tagging
- [ ] Add email and social media fields
- [ ] Support import/export via CSV or vCard
- [ ] Add undo/redo for edits
- [ ] Implement unit tests with JUnit
- [ ] Migrate to JSON/XML serialization for cross-platform compatibility

---

## Author

**Ali Aybx** — Junior Kotlin Developer  
This project was built as part of a portfolio to demonstrate proficiency in Kotlin, OOP, data persistence, and interactive CLI development.  
[GitHub](https://github.com/aliaybx) • [LinkedIn](https://www.linkedin.com/in/ali-ayoub-233632369/)
