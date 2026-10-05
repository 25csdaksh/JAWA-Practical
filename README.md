# OOP Lab Practical Portfolio — Semester Work

This repository contains the complete Java solutions for **Practical 1** through **Practical 10**, organized into practice programs and the modular, concurrent, thread-safe, and fault-tolerant **MiniBank** enterprise project.

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
├── lab-09/                  (Practical 9 — Practice Programs)
├── lab-10/                  (Practical 10 — Practice Programs)
│   ├── threadpool/          (Part A1: Fixed Thread Pool Executor & Thread Recycling)
│   │   └── ThreadPoolDriver.java
│   ├── producerconsumer/    (Part A2: Bounded Buffer with wait() and notify())
│   │   ├── BoundedBuffer.java
│   │   └── ProducerConsumerDriver.java
│   └── deadlock/            (Part A3: Deadlock Reproduction & Consistent Lock Ordering Fix)
│       └── DeadlockDriver.java
├── exception/               (Custom Checked Exception Hierarchy)
│   ├── BankException.java
│   ├── InsufficientFundsException.java
│   ├── AccountNotFoundException.java
│   ├── InvalidAmountException.java
│   └── DailyLimitExceededException.java
├── model/                   (Domain Entities)
│   ├── annotation/
│   │   ├── Id.java
│   │   ├── Positive.java
│   │   └── MaxLength.java
│   ├── Account.java         (Thread-safe synchronized balance methods & annotations)
│   ├── SavingsAccount.java  (Thread-safe savings account)
│   ├── CurrentAccount.java  (Thread-safe current account)
│   ├── FixedDepositAccount.java (Thread-safe fixed deposit account)
│   └── ...
├── service/                 (Core Capabilities, Processors & Coordination)
│   ├── TransactionProcessor.java (Practical 10 — Managed Fixed Thread Pool Batch Engine)
│   ├── TransactionBuffer.java    (Practical 10 — Bounded Producer-Consumer Transaction Queue)
│   ├── TransferService.java      (Practical 10 — Deadlock-Free Safe Transfers via Lock Ordering)
│   ├── AccountWorker.java   (Multi-threaded Runnable Worker)
│   ├── BankingSession.java  (AutoCloseable Audit Session)
│   └── ...
├── util/                    (Utilities & Reflection Helpers)
│   ├── AnnotationValidator.java
│   ├── Validator.java
│   ├── StatementFormatter.java
│   └── CommandParser.java
├── MiniBank.java            (Main Banking Shell with Thread Pool, Queue & Deadlock Demonstrations)
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
javac -d bin exception/*.java model/annotation/*.java service/*.java model/*.java util/*.java MiniBank.java lab-10/threadpool/*.java lab-10/producerconsumer/*.java lab-10/deadlock/*.java
```

### 2. Building the Runnable JAR (`minibank.jar`)
```powershell
jar cfm minibank.jar MANIFEST.MF -C bin .
```

### 3. Running Practical 10 Practice Programs
* **Thread Pool Runner (Fixed Thread Pool & Recycling):**
  ```powershell
  java -cp bin threadpool.ThreadPoolDriver
  ```
* **Producer-Consumer (wait / notify Coordination):**
  ```powershell
  java -cp bin producerconsumer.ProducerConsumerDriver
  ```
* **Deadlock Reproduction & Fix (Canonical Lock Ordering):**
  ```powershell
  java -cp bin deadlock.DeadlockDriver
  ```

### 4. Running the MiniBank Runnable JAR
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
* **Checked Exceptions:** Subclasses of `java.lang.Exception` (excluding `RuntimeException`). The Java compiler strictly mandates that callers handle them with `try-catch` blocks or declare them using `throws` (e.g., `BankException`, `InsufficientFundsException`).
* **Unchecked Exceptions:** Subclasses of `java.lang.RuntimeException` and `java.lang.Error`. The compiler does not enforce explicit handling or declarations (e.g., `NullPointerException`, `ArithmeticException`).

#### 2. What is the purpose of the finally block, and when does it run?
* **Purpose:** Ensures critical cleanup logic executes deterministically.
* **When It Runs:** The `finally` block **always** executes after the `try` block and any executed `catch` blocks finish, even if an unhandled exception occurs or control leaves via `return`, `break`, or `continue`.

#### 3. How does try-with-resources guarantee a resource is closed?
* **Mechanism:** The target resource implements `AutoCloseable`. The compiler generates synthetic bytecode ensuring `resource.close()` is invoked automatically upon exiting the block, attaching any secondary errors as suppressed exceptions (`getSuppressed()`).

---

### 💡 Practical 9 Questions

#### 1. What is a race condition, and why does it occur here?
* Occurs when multiple threads interleave non-atomic read-modify-write sequences on shared mutable variables without synchronization, overwriting each other's updates (lost updates).

#### 2. What does the synchronized keyword do?
* Enforces mutual exclusion (only one thread holds the object monitor at a time) and establishes memory visibility barriers (happens-before ordering).

#### 3. What are the states in a thread’s life cycle?
* `NEW`, `RUNNABLE`, `BLOCKED`, `WAITING`, `TIMED_WAITING`, `TERMINATED`.

---

### 💡 Practical 10 Questions

#### 1. Why is a thread pool better than creating a new thread per task?
* **Thread Creation Overhead:** Creating an OS-level thread requires significant CPU and memory allocation (e.g. allocating native thread stacks of ~1MB per thread). Destroying and re-creating threads continuously degrades performance.
* **Thread Recycling:** A thread pool maintains a fixed set of reusable worker threads that process tasks from a shared work queue, virtually eliminating creation/destruction latency.
* **Resource Throttling & Stability:** Caps the maximum number of concurrent threads, preventing server crashes caused by memory exhaustion (`OutOfMemoryError`) or CPU thrashing under high request loads.

#### 2. How do wait() and notify() coordinate two threads?
* **`wait()`:** Called inside a `synchronized` block/method. It causes the executing thread to release the object monitor lock immediately and enter the `WAITING` state, sleeping until another thread awakens it.
* **`notify()` / `notifyAll()`:** Called inside a `synchronized` block/method by a producer or consumer. It awakens one (or all) threads waiting on that monitor. Once awakened, the waiting thread re-acquires the lock and resumes execution.
* **Condition Loop (`while` check):** Always invoked inside a `while (condition)` loop to defend against spurious wakeups and ensure the condition holds true before proceeding.

#### 3. What causes a deadlock, and how does consistent lock ordering prevent it?
* **Cause (Coffman Conditions):** A deadlock occurs when two or more threads are permanently blocked because each holds a lock that the other needs (Circular Wait):
  - Thread 1 locks Account A and waits for Account B.
  - Thread 2 locks Account B and waits for Account A.
* **Consistent Lock Ordering Prevention:** By establishing a strict, global lock acquisition hierarchy (e.g. always acquiring the lock on the account with the smaller `accountNumber` first: `min(accA, accB)` then `max(accA, accB)`), the circular wait condition is made mathematically impossible. Both threads will attempt to acquire Account A first, forcing Thread 2 to queue politely behind Thread 1.

---

### 💡 Practical 11 Questions

#### 1. What does it mean for a class to be Serializable, and why add serialVersionUID?
* **Serializable Interface:** `java.io.Serializable` is a marker interface (containing no methods) that grants JVM permission to flatten the object's instance state into a sequence of binary bytes via `ObjectOutputStream`, and reconstruct it back into memory via `ObjectInputStream`.
* **Purpose of `serialVersionUID`:** It is a unique version identifier for each `Serializable` class. During deserialization, the JVM compares the `serialVersionUID` encoded in the byte stream with the `serialVersionUID` of the current class.
  - If they match, deserialization succeeds.
  - If they differ (or if a class modification alters the auto-generated hash), the JVM throws `java.io.InvalidClassException`.
  - Explicitly declaring `private static final long serialVersionUID = 1L;` guarantees version compatibility across different JVM vendors, compiler versions, or minor class modifications.

#### 2. What is the difference between a byte stream and a character stream?
* **Byte Streams (`InputStream` / `OutputStream`):**
  - Read/write raw 8-bit binary bytes directly without character set translation.
  - Ideal for binary data such as serialized objects (`ObjectInputStream`/`ObjectOutputStream`), images, audio, video, and PDF documents.
* **Character Streams (`Reader` / `Writer`):**
  - Read/write 16-bit Unicode characters (`char`), automatically handling encoding/decoding between bytes and text based on character sets like `UTF-8` or `UTF-16` (e.g. `BufferedReader`, `BufferedWriter`, `FileReader`).
  - Ideal for textual content, configuration files, and log files.

#### 3. How does NIO’s Path/Files API differ from the old File class, and how is a directory stream iterated?
* **NIO (`Path` / `Files`) vs Legacy (`java.io.File`):**
  - `java.io.File` combines path representation and file operations into one class, frequently fails silently (returns `false` instead of throwing descriptive `IOException`s), and performs poorly on large directories (`listFiles()` loads all filenames into memory at once).
  - NIO `Path` represents a location in the file system, while static utility `Files` performs rich operations throwing explicit exceptions (`NoSuchFileException`, `AccessDeniedException`). It supports symbolic links, POSIX permissions, and atomic operations.
* **DirectoryStream Iteration:**
  - `Files.newDirectoryStream(Path dir)` or `Files.newDirectoryStream(Path dir, String glob)` returns an `AutoCloseable` `DirectoryStream<Path>`.
  - It lazily streams directory entries on-demand rather than buffering all entries into memory, providing exceptional scalability when scanning directories containing thousands of files. It is traversed using standard enhanced `for (Path entry : stream)` loops inside try-with-resources.

---

## 🛠️ Implemented Features & Supplementary Solutions

### Practical 11
* **Part A1 — Save/Load Object Serialization (`lab-11/saveload`):**
  - `UserProfile` model implementing `Serializable` with `serialVersionUID = 1L` and `private transient String sessionToken`.
  - `SaveLoadDemo`: Saves an array of `UserProfile[]` using `ObjectOutputStream`, deserializes it with `ObjectInputStream`, and proves that core state survives while the `transient` field is reset to `null`.
* **Part A2 — Log Analyzer (`lab-11/loganalyzer`):**
  - `LogAnalyzerDemo`: Reads multiple log files line-by-line using `Path` and `Files.newBufferedReader`.
  - Retrieves exact file sizes and modification timestamps using `Files.readAttributes(path, BasicFileAttributes.class)`.
  - Aggregates total scanned lines and keyword occurrences (`ERROR`) across all files.
* **Part A3 — Directory Tree Walker (`lab-11/treewalker`):**
  - `TreeWalkerDemo`: Recursively traverses directory hierarchies using `Files.walkFileTree` and `SimpleFileVisitor`.
  - Generates a structured audit report table with relative paths, sizes, timestamps, and node types, writing the summary to an output file.
* **Part B — MiniBank Persistence & Financial Reconciliation Engine:**
  - `model.Account`, `model.SavingsAccount`, `model.CurrentAccount`, `model.FixedDepositAccount`, and `model.Customer` implement `Serializable` with explicit `serialVersionUID`.
  - Added `transient String sessionToken` to demonstrate transient field exclusion during bank state saving.
  - `util.StatePersister`:
    - `save(Account[] accounts, Path file)` using `ObjectOutputStream` inside try-with-resources.
    - `load(Path file)` using `ObjectInputStream` inside try-with-resources.
  - `util.TransactionLog`:
    - `append(Path file, String line)` using `Files.write` with `StandardOpenOption.CREATE` and `StandardOpenOption.APPEND`.
  - `util.ReportGenerator`:
    - Walks the `logs/` directory using `Files.newDirectoryStream`.
    - **Supplementary Rule 1:** Skips files that do not end with `.log`.
    - **Supplementary Rule 2:** Skips empty files (`size == 0`).
    - Sums total deposits, total withdrawals, and calculates net financial balance change.
    - Inspects file attributes (`BasicFileAttributes`) and writes formatted end-of-day reports to disk.
  - `MiniBank.java` Integration:
    - Pre-flight automated verification of state persistence and report generation.
    - Interactive menu options 6-9 for live persistence, loading, logging, and audit reporting.

### Practical 10
* **Part A1 — Thread-Pool Runner (`threadpool` package):**
  - Managed `ExecutorService` fixed pool with 3 worker threads executing 10 asynchronous tasks.
  - Recorded thread recycling metrics demonstrating that 3 threads execute all 10 tasks.
* **Part A2 — Producer-Consumer Buffer (`producerconsumer` package):**
  - Circular bounded buffer `BoundedBuffer` coordinated using `synchronized`, `wait()`, and `notifyAll()`.
  - Producer and Consumer threads process 12 ordered items with 0 loss and FIFO integrity.
* **Part A3 — Deadlock Reproduction & Lock Ordering Fix (`deadlock` package):**
  - Demonstrated circular wait deadlock between two threads locking resources in opposite order.
  - Demonstrated canonical ID-based lock ordering eliminating deadlocks completely.
* **Part B — MiniBank Batch Transaction Engine & Safe Transfers:**
  - `service.TransactionProcessor`:
    - Fixed thread pool of 4 workers submitting concurrent transaction tasks.
    - Graceful shutdown via `stop()` with `awaitTermination`.
    - **Supplementary Problem 1:** Tracks and prints task execution metrics for every worker thread.
  - `service.TransactionBuffer`:
    - Producer-Consumer bounded queue coordinating transaction ingestion and worker execution using `wait()`/`notify()`.
  - `service.TransferService`:
    - `transferDeadlockProne`: Reproduces the A $\leftrightarrow$ B reverse locking deadlock.
    - `transferSafe`: Implements consistent lower-account-number-first locking (`compareTo()`) to guarantee deadlock-free execution.
    - **Supplementary Problem 2:** Measures transfer duration and logs warnings for long-running transfers.
  - `MiniBank.java` Test Suite:
    - Demonstrates managed thread pool execution (12 tasks across 4 threads).
    - Demonstrates producer-consumer queue processing 6 transactions smoothly.
    - Demonstrates deadlock detection and safe transfer verification.
    - Interactive menu option 7 for running thread pool batch benchmarks.
