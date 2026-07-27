# OOP Lab Practical Portfolio — Semester Work

This repository contains the complete Java solutions for **Practical 1** and **Practical 2**, organized into practice programs and the semester-long **MiniBank** project.

---

## 📁 Repository Structure

```text
A.lab practical/
├── lab-01/                  (Practical 1 — Practice Programs)
│   ├── VendingMachine.java
│   ├── TollBooth.java
│   └── RPSLS.java           (Rock-Paper-Scissors-Lizard-Spock)
├── lab-02/                  (Practical 2 — Practice Programs)
│   ├── Thermostat.java
│   ├── CinemaShow.java
│   └── ParkingLot.java
├── Customer.java            (Practical 2 — MiniBank Customer Entity)
├── Account.java             (Practical 2 — MiniBank Account Entity)
├── MiniBank.java            (Interactive Banking Console Shell Application)
├── .gitignore               (Configured to ignore compiled .class files)
└── README.md                (Portfolio Documentation & Answers to Lab Questions)
```

---

## 🚀 Compilation and Execution

Make sure you have JDK 17 or higher installed on your system. Run all commands from the repository root directory.

### 1. Compiling All Programs
To compile all practice programs and the main project, run:
```powershell
javac lab-01/*.java lab-02/*.java Customer.java Account.java MiniBank.java
```

### 2. Running Practical 1 Programs
* **Vending Machine:**
  ```powershell
  java -cp lab-01 VendingMachine
  ```
* **Toll Booth:**
  ```powershell
  java -cp lab-01 TollBooth
  ```
* **Rock-Paper-Scissors-Lizard-Spock:**
  ```powershell
  java -cp lab-01 RPSLS
  ```

### 3. Running Practical 2 Programs
* **Smart Thermostat:**
  ```powershell
  java -cp lab-02 Thermostat
  ```
* **Cinema Show Booking:**
  ```powershell
  java -cp lab-02 CinemaShow
  ```
* **Parking Lot Simulator:**
  ```powershell
  java -cp lab-02 ParkingLot
  ```

### 4. Running the MiniBank Project
To start the fully interactive banking application:
```powershell
java MiniBank
```

---

## 📝 Key Questions, Analysis & Answers

### 💡 Practical 1 Questions

#### 1. What is the role of the JVM, and what file does the javac compiler produce?
* **JVM (Java Virtual Machine):** The JVM is the engine that drives Java code. It converts intermediate Java bytecode (which is platform-independent) into native machine language instructions for the host hardware. It also handles automatic memory management (garbage collection) and execution security.
* **`javac` Output File:** The `javac` compiler compiles human-readable source code (`.java` files) into Java bytecode, producing `.class` files.

#### 2. How does a switch expression differ from a traditional switch statement?
* **Value Return:** A **switch expression** resolves to a single value that can be assigned directly to a variable or returned. A **switch statement** only executes a block of code and does not return anything.
* **Fall-through Behavior:** Switch expressions using the arrow syntax (`case X -> ...`) do not have automatic fall-through, making `break` statements obsolete. Traditional switch statements require explicit `break` statements to prevent falling through to subsequent cases.
* **Exhaustiveness:** Switch expressions are strictly checked for completeness at compile time. You must cover all enum constants or provide a `default` case. Switch statements do not enforce this compile-time validation.

#### 3. Why is an enum a good choice for a fixed set of menu options, and a record for read-only data?
* **Enum for Menus:** Enums provide type-safety, eliminate magic numbers (like using 1, 2, 3), enforce that only predefined valid menu choices can be parsed, and make code highly self-documenting.
* **Record for Read-only Data:** Records (introduced in Java 14/16) are specialized classes designed to carry immutable data. Java automatically generates private final fields, getter methods (named after the fields directly, like `bank.branch()`), constructors, and standard implementations of `toString()`, `hashCode()`, and `equals()`, reducing boilerplate.

---

### 💡 Practical 2 Questions

#### 1. What is encapsulation, and how do private fields with public getters enforce it?
* **Encapsulation** is the bundling of data (fields) and methods that operate on that data into a single unit (class), while hiding internal details and restricting direct access from outside.
* **How private fields and public getters enforce it:** By declaring fields as `private`, we prevent external code from modifying or viewing fields arbitrarily. Providing only public `getters` permits read-only access. Excluding `setters` for critical fields (like account balance) ensures that the balance can only be updated through controlled, validated methods (`deposit()` and `withdraw()`), preserving class integrity.

#### 2. Why is accountNumber declared final?
* The account number is a unique, constant identifier. Once an account is created, its account number must never change throughout its lifecycle. Declaring it `final` guarantees that it can only be initialized once in the constructor and prevents accidental reassignment.

#### 3. What is the difference between a static field and an instance field when generating IDs?
* **Static Field (e.g., `customerCounter` / `accountCounter`):** Belongs to the class itself rather than individual instances. There is only a single copy shared across all objects of that class. It maintains state globally, allowing us to increment the counter every time a new object is created.
* **Instance Field (e.g., `customerId` / `accountNumber`):** Belongs to a specific object. Each object gets its own copy. It stores the unique ID generated by the static counter, ensuring that each instance keeps its own permanent identity.

---

## 🛠️ Implemented Features & Supplementary Solutions

### Practical 1
* **Input Validation:** MiniBank catches non-numeric inputs and range errors, re-prompting the user gracefully.
* **Timing Menu Option:** Added timing option to `MenuOption` enum and printed timing details.

### Practical 2
* **Positive Input Enforcements:** Both `deposit()` and `withdraw()` reject negative or zero values.
* **Local Transfer Helper:** Implemented `Account.transfer(Account source, Account destination, long amount)` which safely withdraws from one account and deposits into another, returning transaction status.
* **Full MiniBank Integration:** Pre-loads 3 testing accounts at startup to run the requested transactions, and then launches the main menu where you can open accounts, transfer money, deposit, withdraw, and check timings in real-time.
