# OOP Lab Practical - Hour 1 & Hour 2

This repository contains the Java solutions for **Part A (Practice Programs)** and **Part B (Project Work: MiniBank)**.

---

## 📁 Repository Structure

```text
A.lab practical/
├── lab-01/
│   ├── VendingMachine.java  (Part A, Program 1)
│   ├── TollBooth.java       (Part A, Program 2)
│   └── RPSLS.java           (Part A, Program 3 - Rock-Paper-Scissors-Lizard-Spock)
├── MiniBank.java            (Part B, MiniBank Semester Project Shell)
└── README.md                (Lab Documentation & Analysis)
```

---

## 🚀 How to Compile and Run

Make sure you have JDK 17 or higher installed on your system.

### Compiling all files:
Run the following command from the root directory:
```bash
javac lab-01/*.java MiniBank.java
```

### Running the programs:

1. **Vending Machine:**
   ```bash
   java lab_01.VendingMachine
   ```

2. **Toll Booth:**
   ```bash
   java lab_01.TollBooth
   ```

3. **Rock-Paper-Scissors-Lizard-Spock (RPSLS):**
   ```bash
   java lab_01.RPSLS
   ```

4. **MiniBank CLI Shell:**
   ```bash
   java MiniBank
   ```

---

## 📝 Key Questions, Analysis & Answers

### 1. What is the role of the JVM, and what file does the javac compiler produce?
* **Role of the JVM (Java Virtual Machine):** 
  The JVM is the engine that drives the Java code. It converts Java bytecode into machine language (native instructions) that the host system's hardware can understand. It provides an execution environment (platform independence) and handles memory management (such as automatic Garbage Collection).
* **File produced by `javac`:**
  The `javac` compiler translates human-readable Java source files (`.java`) into intermediate bytecode files (`.class`). These `.class` files are what the JVM loads and executes.

### 2. How does a switch expression differ from a traditional switch statement?
* **Value Return:** A **switch expression** resolves to a single value and can be assigned directly to a variable or returned from a method, whereas a traditional **switch statement** only executes a block of statements without returning a value.
* **Fall-through Behavior:** Switch expressions using the arrow syntax (`case X -> ...`) do not have automatic fall-through (meaning no `break` statements are needed to prevent executing the next case). Traditional switch statements require explicit `break` statements, otherwise execution continues into subsequent cases.
* **Exhaustiveness:** Switch expressions are strictly checked by the compiler for exhaustiveness. If you are switching on an enum, you must cover all constants, or provide a `default` case. Traditional switch statements do not enforce completeness at compile time.

### 3. Why is an enum a good choice for a fixed set of menu options, and a record for read-only data?
* **Enum for Menu Options:**
  An `enum` defines a fixed set of named constants. Using it for menus provides **type-safety**, prevents invalid inputs at the logical layer, eliminates magic numbers (like using 1, 2, 3), and makes the code highly readable and self-documenting.
* **Record for Read-only Data:**
  A `record` (introduced in Java 14/16) is a special class declaration designed to hold immutable (read-only) data. It automatically generates:
  - Private final fields.
  - A canonical constructor.
  - Getter methods (without the `get` prefix, e.g., `header.name()` instead of `header.getName()`).
  - Standard implementations of `equals()`, `hashCode()`, and `toString()`.
  This eliminates boilerplate code, keeping data-carrier classes concise and clean.

---

## 🛠️ Implemented Features & Supplementary Solutions
* **Invalid Input Handling:** In `MiniBank.java`, entering a non-numeric value or a choice outside the range of 1–6 will not crash the application. The program catches invalid inputs and re-prompts the user until a correct choice is entered.
* **Bank Working Hours:** A dedicated menu option (`5. Check Bank Working Hours`) was integrated into the `MenuOption` enum and switch expression to display the bank's timing info.
