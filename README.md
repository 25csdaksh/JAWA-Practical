# OOP Lab Practical Portfolio — Semester Work

This repository contains the complete Java solutions for **Practical 1** through **Practical 8**, organized into practice programs and the modular, fault-tolerant **MiniBank** project (Milestone 1).

---

## 📁 Repository & Package Structure

```text
A.lab practical/
├── lab-01/                  (Practical 1 — Practice Programs)
├── lab-02/                  (Practical 2 — Practice Programs)
├── lab-03/                  (Practical 3 — Practice Programs)
├── lab-04/                  (Practical 4 — Practice Programs)
├── lab-05/                  (Practical 5 — Practice Programs)
├── lab-06/                  (Practical 6 — Practice Programs)
├── lab-07/                  (Practical 7 — Practice Programs)
├── lab-08/                  (Practical 8 — Practice Programs)
│   ├── calculator/          (Part A1: Guarded Calculator with DivideByZeroException & try-finally)
│   │   ├── DivideByZeroException.java
│   │   ├── Calculator.java
│   │   └── CalculatorDriver.java
│   ├── warehouse/           (Part A2: Inventory System with OutOfStockException & Shortfall)
│   │   ├── OutOfStockException.java
│   │   ├── InvalidQuantityException.java
│   │   ├── Warehouse.java
│   │   └── WarehouseDriver.java
│   └── resource/            (Part A3: AutoCloseable Resource & Try-With-Resources)
│       ├── DatabaseConnection.java
│       └── ResourceDriver.java
├── exception/               (Practical 8 — Custom Checked Exception Hierarchy)
│   ├── BankException.java   (Base checked exception extending Exception)
│   ├── InsufficientFundsException.java (Carries shortfall field & getter)
│   ├── AccountNotFoundException.java (Thrown when account query fails)
│   ├── InvalidAmountException.java (Thrown for non-positive transaction amounts)
│   └── DailyLimitExceededException.java (Supplementary: Daily withdrawal cap violation)
├── model/                   (Domain Entities)
│   ├── annotation/          (Practical 7 — Metadata Annotations)
│   │   ├── Id.java
│   │   ├── Positive.java
│   │   └── MaxLength.java
│   ├── Account.java         (Fault-tolerant withdraw/deposit/transfer with throws)
│   ├── SavingsAccount.java  (Extends Account with minBalance shortfall calculations)
│   ├── CurrentAccount.java  (Extends Account with overdraft shortfall calculations)
│   ├── FixedDepositAccount.java (Extends Account with lock-in exception handling)
│   ├── Customer.java        (Customer Record with Address & Cloneable)
│   ├── BankInfo.java        (Record for Bank Branch Details)
│   ├── TransactionType.java (Enum for Transaction Types)
│   └── Command.java         (Record for Banking Commands)
├── service/                 (Core Capabilities & Rules)
│   ├── Transactable.java    (deposit/withdraw contracts declaring exceptions)
│   ├── InterestBearing.java (interestRate + default yearlyInterest & projectedBalance)
│   ├── WithdrawRule.java    (@FunctionalInterface for withdrawal validation)
│   ├── Premium.java         (Marker Interface for VIP Accounts)
│   └── BankingSession.java  (Practical 8 — AutoCloseable transactional session)
├── util/                    (Utilities & Reflection Helpers)
│   ├── AnnotationValidator.java (Reflection-based Validator)
│   ├── Validator.java       (Regex Verification Methods)
│   ├── StatementFormatter.java (Account Statement Builder)
│   └── CommandParser.java   (Command Line Parser)
├── MiniBank.java            (Milestone 1 Main Application with Structured Exception Handling)
├── MANIFEST.MF              (JAR Manifest specifying Main-Class: MiniBank)
├── minibank.jar             (Packaged Runnable JAR Artifact)
├── .gitignore
└── README.md
```

---

## 🚀 Compilation, Packaging & Execution

Make sure you have JDK 17 or higher installed on your system. Run all commands from the repository root directory.

### 1. Compiling MiniBank & All Packages into `bin/`
```powershell
javac -d bin exception/*.java model/annotation/*.java service/*.java model/*.java util/*.java MiniBank.java lab-08/calculator/*.java lab-08/warehouse/*.java lab-08/resource/*.java
```

### 2. Building the Runnable JAR (`minibank.jar`)
```powershell
jar cfm minibank.jar MANIFEST.MF -C bin .
```

### 3. Running Practical 8 Practice Programs
* **Guarded Calculator (DivideByZeroException & Looped Recovery):**
  ```powershell
  java -cp bin calculator.CalculatorDriver
  ```
* **Warehouse Stock (OutOfStockException with Shortfall):**
  ```powershell
  java -cp bin warehouse.WarehouseDriver
  ```
* **AutoCloseable Resource (Try-With-Resources Execution):**
  ```powershell
  java -cp bin resource.ResourceDriver
  ```

### 4. Running the MiniBank Runnable JAR (Milestone 1)
```powershell
java -jar minibank.jar
```

---

## 📝 Key Questions, Analysis & Answers

### 💡 Practical 1 Questions

#### 1. What is the role of the JVM, and what file does the javac compiler produce?
* **JVM (Java Virtual Machine):** Converts platform-independent Java bytecode into native machine instructions while managing execution security and garbage collection.
* **`javac` Output File:** Translates `.java` source code into `.class` bytecode files.

#### 2. How does a switch expression differ from a traditional switch statement?
* Resolves directly to a value, uses arrow syntax (`->`) preventing accidental fall-through, and enforces compile-time exhaustiveness.

#### 3. Why is an enum a good choice for a fixed set of menu options, and a record for read-only data?
* **Enum:** Enforces compile-time type-safety and eliminates magic constants.
* **Record:** Provides an immutable, concise data carrier with auto-generated getters, `equals()`, `hashCode()`, and `toString()`.

---

### 💡 Practical 2 Questions

#### 1. What is encapsulation, and how do private fields with public getters enforce it?
* Restricts direct state tampering and exposes data exclusively through validated public methods, safeguarding object invariants.

#### 2. Why is accountNumber declared final?
* Guarantees that an account identifier is immutable once assigned.

#### 3. What is the difference between a static field and an instance field when generating IDs?
* **Static Field:** Global counter shared by all class instances.
* **Instance Field:** Stores the unique identifier assigned specifically to that instance.

---

### 💡 Practical 3 Questions

#### 1. Why must equals() and hashCode() be overridden together?
* To maintain the contract that equal objects must have identical hash codes, ensuring correct operation inside hashing collections like `HashSet` and `HashMap`.

#### 2. What does the default Object.toString() return, and why override it?
* Returns `ClassName@HashCodeHex`. Overriding it yields meaningful diagnostic state.

#### 3. What is the difference between a static nested class and an inner class?
* **Static Nested Class:** Does not carry a reference to an enclosing instance.
* **Inner Class:** Binds to an enclosing outer class instance and can directly access its members.

---

### 💡 Practical 4 Questions

#### 1. What is the difference between String, StringBuilder and StringBuffer, and when is each preferred?
* `String` is immutable; `StringBuilder` is mutable and fast (single-threaded); `StringBuffer` is mutable and synchronized (thread-safe).

#### 2. What does the regular expression [6-9][0-9]{9} match, and why use anchors?
* Matches a 10-digit Indian phone number. Anchors (`^` and `$`) ensure the full string conforms without extraneous characters.

#### 3. Why should input be validated at the boundary before it is used?
* Enables fail-fast error detection, protects internal domain integrity, and prevents injection attacks.

---

### 💡 Practical 5 Questions

#### 1. What is the difference between an abstract class and a concrete class?
* Abstract classes cannot be instantiated and can contain abstract method declarations. Concrete classes provide full implementations and can be instantiated with `new`.

#### 2. What is dynamic method dispatch (run-time polymorphism)?
* Dynamic resolution of overridden method calls at run time based on the actual object type rather than the reference type.

#### 3. What does the super keyword do in a subclass constructor?
* Chained execution invoking the parent class constructor to initialize inherited state before subclass initialization.

---

### 💡 Practical 6 Questions

#### 1. What is the difference between an interface and an abstract class?
* Classes can implement multiple interfaces but extend only one class. Abstract classes can have instance variables and constructors, while interfaces define pure capability contracts with constants, static, default, or abstract methods.

#### 2. What is a functional interface, and how does a lambda use it?
* An interface with exactly one abstract method (`@FunctionalInterface`). A lambda expression provides an inline, type-inferred implementation of that single method.

#### 3. Why is a default method useful in an interface?
* Enables backward-compatible interface evolution and provides shared, reusable default logic across all implementing classes.

---

### 💡 Practical 7 Questions

#### 1. What are the meta-annotations @Retention and @Target used for?
* **`@Retention`:** Configures the **lifespan** of an annotation (`SOURCE`, `CLASS`, or `RUNTIME` for reflection access).
* **`@Target`:** Defines the **syntactic elements** (`FIELD`, `METHOD`, `TYPE`, `PARAMETER`) to which the annotation applies.

#### 2. What is reflection, and what does getDeclaredFields() return?
* **Reflection:** Runtime introspection and invocation API.
* **`getDeclaredFields()`:** Returns all declared fields of a class (including private fields).

#### 3. How do annotations plus reflection let a framework validate any object?
* Decouples declarative metadata constraints from execution logic, allowing a generic reflection validator to dynamically inspect field annotations and validate arbitrary object graphs.

---

### 💡 Practical 8 Questions

#### 1. What is the difference between a checked and an unchecked exception?
* **Checked Exceptions:** Subclasses of `java.lang.Exception` (excluding `RuntimeException`). The compiler forces the programmer to handle them using a `try-catch` block or declare them in the method signature using the `throws` keyword (e.g. `BankException`, `InsufficientFundsException`, `IOException`). Used for recoverable conditions that well-written applications must anticipate.
* **Unchecked Exceptions:** Subclasses of `java.lang.RuntimeException` and `java.lang.Error`. The compiler does not enforce explicit handling or declarations (e.g. `NullPointerException`, `ArithmeticException`, `IllegalArgumentException`). Used for programming bugs or unrecoverable environmental failures.

#### 2. What is the purpose of the finally block, and when does it run?
* **Purpose:** To execute essential cleanup code (such as releasing database connections, closing files, flushing audit logs, or completing transaction auditing) regardless of whether the try block completes normally or throws an exception.
* **When It Runs:** The `finally` block **always** executes after the `try` block and any matching `catch` blocks finish, even if a `return`, `break`, `continue`, or unhandled exception occurs inside the `try` or `catch` block (the only exception being an explicit JVM termination via `System.exit()`).

#### 3. How does try-with-resources guarantee a resource is closed?
* **Mechanism:** Any object whose class implements `java.lang.AutoCloseable` or `java.io.Closeable` can be instantiated inside the parentheses of a `try (...)` statement.
* **Guarantee:** The Java compiler automatically generates synthetic bytecode that invokes `resource.close()` when exiting the try block (either on success or due to an exception). If an exception occurs inside the try block and `close()` also throws an exception, the original exception is preserved and thrown, while the exception from `close()` is attached as a *suppressed exception* (`getSuppressed()`).

---

## 🛠️ Implemented Features & Supplementary Solutions

### Practical 8
* **Part A1 — Guarded Calculator (`calculator` package):**
  - Custom checked exception `DivideByZeroException`.
  - Looped input retry mechanism catching `NumberFormatException`, `DivideByZeroException`, and `IllegalArgumentException` independently.
  - Mandatory `finally` block recording an audit timestamp for every attempt.
* **Part A2 — Warehouse Inventory (`warehouse` package):**
  - Custom checked exception `OutOfStockException` holding shortfall count (`getShortfall()`).
  - Custom checked exception `InvalidQuantityException`.
  - Batch request processing with fault tolerance (continuous execution upon individual failures).
* **Part A3 — AutoCloseable Resource (`resource` package):**
  - `DatabaseConnection` implementing `AutoCloseable`.
  - Verified that `close()` is invoked automatically both on success and when an unexpected error occurs within the block.
* **Part B — MiniBank Fault-Tolerant Exception Hierarchy (Milestone 1):**
  - `exception` package:
    - `BankException`: Base checked exception.
    - `InsufficientFundsException`: Stores `shortfall` amount with `getShortfall()`.
    - `AccountNotFoundException`: Thrown when querying nonexistent account numbers.
    - `InvalidAmountException`: Thrown when transaction amounts are $\le 0$.
    - **Supplementary Problem 1:** `DailyLimitExceededException` thrown when withdrawal exceeds daily cap.
  - Domain Model & Service Updates:
    - `Transactable` declares `throws InvalidAmountException, InsufficientFundsException, BankException`.
    - `Account.deposit()` throws `InvalidAmountException` for negative or zero amounts.
    - `Account.withdraw()` computes explicit shortfall amounts and throws `InsufficientFundsException`.
    - `Account.transfer()` wraps operations in `try-catch-finally`, executes rollback on destination failures, and re-throws `BankException`.
  - `service.BankingSession`: AutoCloseable session for transactional audit logging.
  - Interactive Shell:
    - All banking menu operations (Deposit, Withdraw, Transfer, Statement, Validate) run inside structured `try-catch` blocks, providing clear user feedback without application crashes.
