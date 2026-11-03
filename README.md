# OOP Lab Practical Portfolio — Semester Work

This repository contains the complete Java solutions for **Practical 1** through **Practical 14** (Milestone 3 Capstone Deliverable), organized into practice programs and the modular, layered, concurrent, thread-safe, and fault-tolerant **MiniBank** enterprise project.

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
├── lab-11/                  (Practical 11 — Practice Programs)
│   ├── saveload/            (Part A1: Serialization & Transient Session Tokens)
│   │   ├── UserProfile.java
│   │   └── SaveLoadDemo.java
│   ├── loganalyzer/         (Part A2: NIO Log File Reading & Attribute Inspection)
│   │   └── LogAnalyzerDemo.java
│   └── treewalker/          (Part A3: Recursive walkFileTree Directory Audit)
│       └── TreeWalkerDemo.java
├── lab-12/                  (Practical 12 — Practice Programs)
│   ├── wordfreq/            (Part A1: Word Frequency Counting with HashMap & Comparator)
│   │   └── WordFrequencyDemo.java
│   ├── visitors/            (Part A2: Deduplication via HashSet & First-Seen via LinkedHashSet)
│   │   └── UniqueVisitorsDemo.java
│   └── leaderboard/         (Part A3: Gaming Leaderboard with Map & Tie-Breaker Comparator)
│       └── LeaderboardDemo.java
├── lab-13/                  (Practical 13 — Practice Programs)
│   ├── containers/          (Part A1: Generic Box<T>, Pair<A, B> & <T> getFirst())
│   │   └── GenericContainersDemo.java
│   ├── bounded/             (Part A2: Bounded Stats <N extends Number> average())
│   │   └── BoundedStatsDemo.java
│   └── stack/               (Part A3: Generic ArrayStack<T> Container with Underflow Handling)
│       └── GenericStackDemo.java
├── lab-14/                  (Practical 14 — Layered App, Unit Testing & Runnable JAR)
│   ├── app/
│   │   ├── model/           (Part A1: Item Domain Entity)
│   │   │   └── Item.java
│   │   ├── service/         (Part A1: ItemService with ArrayList & Persistence)
│   │   │   └── ItemService.java
│   │   ├── test/            (Part A1: Lightweight @Test Annotation, Assertions & Suite)
│   │   │   ├── Test.java
│   │   │   ├── Assert.java
│   │   │   └── ItemServiceTest.java
│   │   ├── ui/              (Part A1: Console Menu Driver with --test CLI Flag)
│   │   │   └── ItemApp.java
│   │   └── MANIFEST.MF      (Manifest for Part A Runnable JAR)
│   └── itemapp.jar          (Part A2: Packaged Runnable JAR Artifact)
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
│   │   ├── MaxLength.java
│   │   └── Audit.java       (Practical 14 — @Audit Annotation for Reflective Method Logging)
│   ├── Account.java         (Thread-safe balance, Comparable<Account>, Serializable)
│   ├── SavingsAccount.java  (Thread-safe savings account)
│   ├── CurrentAccount.java  (Thread-safe current account)
│   ├── FixedDepositAccount.java (Thread-safe fixed deposit account)
│   └── Customer.java        (Customer Profile Entity with PAN, Mobile, Email)
├── service/                 (Core Capabilities, Processors & Coordination)
│   ├── BankService.java          (Practical 14 — Milestone 3 Capstone Service Layer Orchestrating Repo, Validator & Exceptions)
│   ├── AuditLogger.java          (Practical 14 — Reflection-Based Method Audit Interceptor)
│   ├── Repository.java           (Practical 13 — Generic Repository<T>, Reflection @Id, @SafeVarargs saveAll, Bounded average)
│   ├── AccountStore.java         (Practical 12 — HashMap Storage, ArrayList Audit, Comparator Sort)
│   ├── TransactionProcessor.java (Practical 12 — Thread-Safe ConcurrentHashMap Shared Accounts Engine)
│   ├── TransactionBuffer.java    (Practical 10 — Bounded Producer-Consumer Transaction Queue)
│   ├── TransferService.java      (Practical 10/12 — Deadlock-Free Safe Transfers via Comparable)
│   ├── AccountWorker.java   (Multi-threaded Runnable Worker)
│   ├── BankingSession.java  (AutoCloseable Audit Session)
│   └── ...
├── test/                    (Practical 14 — Unit Testing Framework & Test Suite)
│   ├── Test.java            (Custom @Test Annotation)
│   ├── Assert.java          (Assertion Utilities: assertEquals, assertTrue, assertThrows)
│   ├── MiniBankTest.java    (Unit Test Suite for MiniBank Milestone 3)
│   └── MiniBankTestRunner.java (Reflection-Based Test Runner Executing All @Test Methods)
├── util/                    (Utilities, Reflection & NIO Helpers)
│   ├── StatePersister.java  (Practical 11/12 — Object Serialization for Array & Collections)
│   ├── TransactionLog.java  (Practical 11 — Append-Only NIO Channel Logging)
│   ├── ReportGenerator.java (Practical 11 — NIO DirectoryStream Financial Reconciliation)
│   ├── AnnotationValidator.java
│   ├── Validator.java
│   ├── StatementFormatter.java
│   └── CommandParser.java
├── MiniBank.java            (Main Banking Shell with Layered BankService, Auto-Save/Load, Batch Concurrency & JUnit Test Mode)
├── MANIFEST.MF              (JAR Manifest specifying Main-Class: MiniBank)
├── minibank.jar             (Packaged Runnable JAR Artifact)
├── .gitignore
└── README.md
```

---

## 🗺️ Practical-Wise Project & Lab Roadmap (Practical 1 to Practical 14)

This roadmap details the exact problem statements, practice programs (`lab-NN/`), and the step-by-step modular progression of the **MiniBank** project across all 14 practicals:

| Practical No. | Core Java Theme / Focus | Part A: Practice Programs (`lab-NN/`) | Part B: MiniBank Project Work & Milestone Deliverables |
|:---:|---|---|---|
| **Practical 1** | Language Basics, Syntax & Control Flow | • `VendingMachine` (Coin dispensing state machine)<br>• `TollBooth` (Vehicle categorization & tolls)<br>• `RPSLS` (Rock-Paper-Scissors-Lizard-Spock switch expression) | **MiniBank Shell & Header**:<br>• Created `BankInfo` entity<br>• Initial interactive console menu skeleton and user prompt loop |
| **Practical 2** | Encapsulation, Access Specifiers & State | • `Thermostat` (Temperature boundary enforcement)<br>• `CinemaShow` (Seat allocation & revenue tracking)<br>• `ParkingLot` (Vehicle slot tracking) | **Customer & Account Encapsulation**:<br>• Encapsulated balance fields using `private`<br>• Introduced getters/setters and defensive balance modification checks |
| **Practical 3** | Constructors, `this` & Object Composition | • `Point` (Cartesian distance formula)<br>• `Fraction` (Arithmetic operations & simplification)<br>• `Card` (Playing card suit and rank deck) | **Account Identity & Composition**:<br>• Auto-generating unique account IDs (`AC0001`, `AC0002`...)<br>• Aggregated `Customer` details inside `Account`<br>• Multiple overloaded constructors with `this()` chaining |
| **Practical 4** | Strings, Regular Expressions & Validation | • `ChatFilter` (Offensive word masking)<br>• `PasswordChecker` (Password complexity regex check)<br>• `TemplateFiller` (Placeholder replacement engine) | **MiniBank Validation Utility Layer**:<br>• Implemented `util.Validator` with regex validation for PAN cards, mobile numbers, email addresses, and account numbers |
| **Practical 5** | Inheritance, Abstract Classes & Polymorphism | • `Payroll` (`Employee`, `FullTime`, `PartTime`, `Intern`)<br>• `Shapes` (Area & perimeter polymorphism)<br>• `Media` (`Book`, `DVD`, `AudioBook` library system) | **Account Class Hierarchy**:<br>• Abstract `Account` base class<br>• `SavingsAccount` with minimum balance enforcement<br>• `CurrentAccount` with overdraft protection<br>• `FixedDepositAccount` with locked maturity dates |
| **Practical 6** | Interfaces, Default Methods & JAR Packaging | • `DiscountApp` (Pluggable discount rule engine)<br>• `RemoteDriver` (Switchable home appliances)<br>• `NotificationDriver` (Urgent alert dispatchers) | **Banking Capabilities & Executable JAR**:<br>• `Transactable` and `InterestBearing` interfaces<br>• Default method for compound interest projection (`projectedBalance()`)<br>• Standalone runnable `minibank.jar` with `MANIFEST.MF` |
| **Practical 7** | Custom Annotations & Java Reflection | • `CsvMapper` (CSV header mapping via reflection)<br>• `FormValidator` (`@NotBlank`, `@MaxLength` reflection check)<br>• `MiniTestRunner` (`@Run` lightweight test runner) | **Account Metadata Validation**:<br>• Declared custom annotations `@Id`, `@Positive`, `@MaxLength`<br>• Built `AnnotationValidator` inspecting account annotations at runtime using Java Reflection |
| **Practical 8** | Custom Exceptions & Resources (**Milestone 1**) | • `Calculator` (`DivideByZeroException`)<br>• `Warehouse` (`OutOfStockException`, `InvalidQuantityException`)<br>• `ResourceDriver` (AutoCloseable database connection) | **Fault-Tolerant Exception Architecture**:<br>• Checked exception hierarchy: `BankException`, `InsufficientFundsException`, `AccountNotFoundException`, `InvalidAmountException`<br>• `BankingSession` with `try-with-resources` (`AutoCloseable`) |
| **Practical 9** | Multithreading & Thread Synchronization | • `CounterDriver` (Race condition demonstration)<br>• `SeatBookingDriver` (Thread-safe synchronized cinema bookings)<br>• `ArraySumDriver` (Parallel multi-threaded chunk summation) | **Thread-Safe Account Operations**:<br>• Atomic `synchronized deposit()` and `synchronized withdraw()`<br>• `depositUnsafe()` explicitly proving lost updates<br>• Concurrent `AccountWorker` stress testing |
| **Practical 10** | Thread Pools, Producer-Consumer & Deadlocks | • `ThreadPoolDriver` (Fixed thread pool & thread recycling)<br>• `ProducerConsumerDriver` (Bounded buffer with wait/notify)<br>• `DeadlockDriver` (Deadlock reproduction & lock ordering) | **Batch Processing & Safe Transfers**:<br>• `service.TransactionProcessor` with fixed thread pool<br>• `service.TransactionBuffer` bounded queue coordinating threads via `wait()` and `notify()`<br>• Deadlock-free `TransferService` with canonical lock ordering |
| **Practical 11** | Java I/O Streams, Serialization & NIO | • `SaveLoadDemo` (Object serialization & transient fields)<br>• `LogAnalyzerDemo` (NIO file reading & metadata inspection)<br>• `TreeWalkerDemo` (Recursive walkFileTree audit) | **MiniBank Persistence & Financial Audit**:<br>• `StatePersister` saving/loading accounts via `ObjectOutputStream`/`ObjectInputStream`<br>• `transient String sessionToken` serialization exclusion<br>• Append-only `TransactionLog` and NIO `ReportGenerator` |
| **Practical 12** | Collections Framework & Sorting (**Milestone 2**) | • `WordFrequencyDemo` (`HashMap` word counter & `Comparator`)<br>• `UniqueVisitorsDemo` (`HashSet` deduplication & `LinkedHashSet` first-seen)<br>• `LeaderboardDemo` (Map player scores & tie-breaking Comparator) | **Collections Storage Engine Upgrade**:<br>• `service.AccountStore` replacing arrays with `HashMap<String, Account>`<br>• `ArrayList<String>` transaction audit log<br>• `sortedByBalance()` using custom `Comparator`<br>• `Comparable<Account>` natural account number ordering<br>• Thread-safe `ConcurrentHashMap` in `TransactionProcessor`<br>• `TreeMap` sorted key order and balance threshold filter |
| **Practical 13** | Generics, Type Erasure & Repository Pattern | • `GenericContainersDemo` (`Box<T>`, `Pair<A, B>`, `<T> getFirst()` generic method)<br>• `BoundedStatsDemo` (`<N extends Number> average()`)<br>• `GenericStackDemo` (Array-backed `ArrayStack<T>` with underflow protection) | **Reusable Generic Repository Pattern**:<br>• Generic `service.Repository<T>` holding `HashMap<String, T>`<br>• Reflection extraction of `@Id` annotated fields across class hierarchies<br>• `@SafeVarargs saveAll(T... items)` batch persistence<br>• Bounded generic average utility `Repository.average(<N extends Number>)`<br>• Reused seamlessly across both `Account` and `Customer` entities |
| **Practical 14** | Layered Architecture, Unit Testing & Capstone Integration (**Milestone 3**) | • Layered Item App (`app.model.Item`, `app.service.ItemService`, `app.ui.ItemApp`)<br>• `@Test` unit test suite (`ItemServiceTest`) asserting count increases & invalid exceptions<br>• Packaged runnable `itemapp.jar` & state persistence reload test | **MiniBank Final Capstone Deliverable (Milestone 3)**:<br>• Layered `service.BankService` wiring `Repository<Account>`, `Validator`, and custom exceptions<br>• UI-to-Service wiring for all banking operations (`openAccount`, `deposit`, `withdraw`, `transfer`, `statement`, `listAccounts`)<br>• Auto-loads state from `data/accounts.dat` at start-up & auto-saves on exit<br>• Batch concurrency engine (`TransactionProcessor`) and audit report (`ReportGenerator`) as menu options<br>• `@Audit` method annotation & reflective execution logger (`AuditLogger`)<br>• Comprehensive `@Test` suite (`test.MiniBankTest`) covering deposits, overdraft limits, concurrent deposits, owner searches, and state persistence<br>• Standalone runnable `minibank.jar` with automated test CLI flag (`--test`) |

---

## 🚀 Compilation, Packaging & Execution

Make sure you have JDK 17 or higher installed on your system. Run all commands from the repository root directory.

### 1. Compiling MiniBank & All Packages into `bin/`
```powershell
javac -d bin exception/*.java model/annotation/*.java service/*.java model/*.java util/*.java test/*.java MiniBank.java lab-13/containers/*.java lab-13/bounded/*.java lab-13/stack/*.java
```

### 2. Building the Runnable JAR (`minibank.jar`)
```powershell
jar cfm minibank.jar MANIFEST.MF -C bin .
```

### 3. Running MiniBank Unit Tests (Practical 14)
* **Using the Reflection Test Runner:**
  ```powershell
  java -cp bin test.MiniBankTestRunner
  ```
* **Using the Runnable JAR directly:**
  ```powershell
  java -jar minibank.jar --test
  ```

### 4. Running Practical 14 Layered Item App & Tests
* **Compiling and Packaging `lab-14/itemapp.jar`:**
  ```powershell
  javac -d lab-14/bin lab-14/app/model/*.java lab-14/app/service/*.java lab-14/app/test/*.java lab-14/app/ui/*.java
  jar cfm lab-14/itemapp.jar lab-14/app/MANIFEST.MF -C lab-14/bin .
  ```
* **Running the Item App Unit Tests:**
  ```powershell
  java -jar lab-14/itemapp.jar --test
  ```
* **Running the Item App Interactively:**
  ```powershell
  java -jar lab-14/itemapp.jar
  ```

### 5. Running Practical 13 Practice Programs
* **Generic Containers (Box, Pair & getFirst):**
  ```powershell
  java -cp bin containers.GenericContainersDemo
  ```
* **Bounded Stats (<N extends Number>):**
  ```powershell
  java -cp bin bounded.BoundedStatsDemo
  ```
* **Generic Array-Backed Stack:**
  ```powershell
  java -cp bin stack.GenericStackDemo
  ```

### 6. Running the MiniBank Interactive Shell
```powershell
java -jar minibank.jar
```
  java -cp bin stack.GenericStackDemo
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

### 💡 Practical 12 Questions (Collections Framework & Milestone 2)

#### 1. What is the difference between a List, a Set and a Map?
* **`List` (e.g., `ArrayList`, `LinkedList`):**
  - An **ordered collection** (sequence) that preserves insertion order.
  - Allows **duplicate elements**.
  - Elements are accessed positionally by a zero-based integer index (`get(int index)`, `set(int index, E element)`).
* **`Set` (e.g., `HashSet`, `LinkedHashSet`, `TreeSet`):**
  - An **unordered or specially ordered collection** that models mathematical sets.
  - Prohibits **duplicate elements** (each element can appear at most once, determined via `equals()` and `hashCode()`).
  - No index-based access; items are retrieved via iterators or enhanced `for` loops.
* **`Map` (e.g., `HashMap`, `TreeMap`, `ConcurrentHashMap`):**
  - A collection of **key-value pairs** (`Entry<K, V>`), mapping unique keys to values.
  - Prohibits **duplicate keys**, but allows duplicate values.
  - Lookups occur via key (`get(Object key)`), offering near-instantaneous $O(1)$ average time complexity in hash-based implementations.

#### 2. When do you use a HashMap versus a TreeMap?
* **Use `HashMap` when:**
  - Fast search, insertion, and deletion are the primary priorities ($O(1)$ constant average time complexity).
  - Ordering of elements does **not** matter.
  - Keys implement proper `hashCode()` and `equals()` methods.
  - Single `null` key and multiple `null` values are permissible.
* **Use `TreeMap` when:**
  - Keys **must be sorted** in natural order (`Comparable`) or according to a custom `Comparator`.
  - Range-based queries or navigational lookups are required (`firstKey()`, `lastKey()`, `subMap()`, `headMap()`, `tailMap()`).
  - Performance trade-off of $O(\log N)$ (Red-Black Tree balanced tree operations) is acceptable in exchange for guaranteed ordering.
  - Prohibits `null` keys.

#### 3. What is the difference between Comparable and Comparator?
* **`Comparable<T>` (Internal / Natural Ordering):**
  - Belongs to `java.lang` and defines the **inherent, natural sorting order** of a class.
  - Implemented directly by the entity class by overriding a single method: `int compareTo(T other)`.
  - Modifies the original class source code (e.g., `Account implements Comparable<Account>` sorting by `accountNumber`).
  - Only one natural sorting sequence can be defined per class.
* **`Comparator<T>` (External / Custom Ordering):**
  - Belongs to `java.util` and defines **external, pluggable, flexible sorting strategies**.
  - Implemented outside the entity class or defined via lambda expressions / method references: `int compare(T o1, T o2)`.
  - Does **not** require modifying the domain class.
  - Multiple distinct comparators can coexist (e.g., sorting accounts by balance descending, sorting by owner name, sorting by interest rate, or sorting leaderboards with multi-criteria tie-breakers).

---

### 💡 Practical 13 Questions (Generics, Type Erasure & Repository Pattern)

#### 1. What problem do generics solve compared with using Object?
* **Compile-Time Type Safety:** Without generics, collections stored raw `Object` references, allowing accidental insertion of mismatched types (e.g., adding an `Integer` into a list intended for `String`). Errors only surfaced at runtime as `ClassCastException`. Generics detect type mismatches at compile time.
* **Elimination of Explicit Casting:** With raw `Object`, every retrieval required an explicit cast (e.g., `String s = (String) list.get(0);`). Generics allow direct assignment (`String s = list.get(0);`), making code cleaner, readable, and refactor-safe.
* **Reusable Code:** A single generic class or method (such as `Box<T>`, `Pair<A, B>`, or `Repository<T>`) can operate on any reference type safely without duplicating code for every entity type.

#### 2. What is a bounded type parameter, and why is <N extends Number> useful?
* **Definition:** A bounded type parameter restricts the allowed type arguments to a specific class hierarchy or interface set using the `extends` keyword (e.g., `<T extends SuperClass & Interface>`).
* **Why `<N extends Number>` is Useful:**
  - **Method Availability:** Standard unbound type variables (`<T>`) only expose methods of `java.lang.Object` (`toString()`, `equals()`). By declaring `<N extends Number>`, the compiler guarantees that numeric methods such as `.doubleValue()`, `.intValue()`, and `.longValue()` exist on every instance.
  - **Type Constraint:** Prevents nonsensical operations at compile time—passing incompatible types like `String[]`, `Boolean[]`, or `Account[]` to statistical methods like `average()` or `sum()` fails immediately at compilation.

#### 3. What is type erasure?
* **Definition:** Type erasure is the mechanism by which the Java compiler enforces generic type constraints strictly at compile time, and then **erases (removes)** all generic type information when emitting `.class` bytecode.
* **How It Works:**
  - Unbounded type parameters (`<T>`) are replaced with their upper bound `Object`.
  - Bounded type parameters (`<N extends Number>`) are replaced with their bound `Number`.
  - Synthetic casts are inserted at call sites where necessary to preserve type safety.
  - Bridge methods are generated to preserve polymorphism in inheritance hierarchies.
* **Consequences:** At runtime, `Repository<Account>` and `Repository<Customer>` are represented by the exact same raw `Repository.class`. Consequently, you cannot do `new T()`, `new T[size]`, or `instanceof T` directly.

---

### 💡 Practical 14 Questions (Layered Architecture, Unit Testing & Milestone 3 Capstone)

#### 1. What is a layered architecture (model, service, user interface), and why is it useful?
* **Layers Defined:**
  - **Model Layer (`model` / `model.annotation`):** Encapsulates core business entities, domain data, internal state, and data invariants (e.g., `Account`, `Customer`, `Item`). It has no knowledge of UI or services.
  - **Service Layer (`service` / `service.BankService`):** Contains core business logic, application workflows, transaction boundaries, and coordinates between repositories, validation, and processing engines.
  - **Presentation / User Interface Layer (`MiniBank`, `ItemApp`):** Handles user input parsing, menu navigation, formatted screen output, and delegates commands directly to the underlying service layer.
* **Why Layered Architecture is Useful:**
  - **Separation of Concerns:** Each layer possesses a distinct, single responsibility, preventing spaghetti code where business logic is mixed with console I/O.
  - **Independent Testability:** Business logic inside services and domain validation rules inside models can be thoroughly verified through automated unit tests (`@Test`) without launching or interacting with the UI.
  - **Maintainability & Flexibility:** The presentation layer can be completely swapped (e.g., from a console CLI to a Spring REST API, JavaFX desktop app, or web interface) without altering a single line of business logic in `BankService`.
  - **Reusability & Modularity:** Repositories, models, and utility processors can be reused across different services and workflows seamlessly.

#### 2. How do you assert in JUnit that a method throws an expected exception?
* In modern testing frameworks (such as JUnit 5 or our custom test harness `Assert.assertThrows`), you use:
  ```java
  assertThrows(Class<T> expectedType, Executable executable);
  ```
* **Example Usage:**
  ```java
  assertThrows(InsufficientFundsException.class, () -> {
      bankService.withdraw("AC0001", 100000.0);
  });
  ```
* **How It Works:**
  - The test framework executes the provided lambda expression or functional interface inside an internal `try-catch` block.
  - If the code executes without throwing any exception, the assertion immediately **fails** reporting that an exception was expected.
  - If the code throws an exception that is an instance of `expectedType`, the assertion passes and returns the caught exception object for optional downstream inspection (such as verifying error messages).
  - If the code throws an unexpected exception type, the assertion fails and re-throws the unexpected error.

#### 3. What does packaging the application as a runnable JAR provide?
* **Single-File Distribution & Portability:** Packages all compiled `.class` files, subpackages, resources, and manifest metadata into a single, standardized, compressed ZIP-format archive (`.jar`), making delivery and deployment frictionless across any operating system with a JVM.
* **Encapsulation & Code Integrity:** Shields individual bytecode class files from accidental deletion, tampering, or folder misplacement. It also supports cryptographic code signing to verify artifact authenticity and author identity.
* **Zero-Configuration Execution:** By specifying `Main-Class: MiniBank` in `META-INF/MANIFEST.MF`, end users can run the entire multi-package application with a single command (`java -jar minibank.jar`) without manually configuring complex classpath strings (`-cp`) or locating individual class roots.
* **Standard Industrial Packaging:** Mimics real-world software delivery where artifacts are built, verified via continuous testing, and released as executable bundles.

---

## 🛠️ Implemented Features & Supplementary Solutions

### Practical 14 (Milestone 3 Final Capstone Deliverable)
* **Part A1 — Layered Item Application (`lab-14/app`):**
  - Layered architecture with strict separation:
    - `app.model.Item`: Domain entity with `id`, `name`, `price`, getters, validation, and formatted string representation.
    - `app.service.ItemService`: Business logic managing items in an `ArrayList<Item>`, enforcing positive prices, unique IDs, search, and persistence.
    - `app.ui.ItemApp`: Interactive console menu supporting interactive execution and headless automated verification (`--test`).
* **Part A2 — Unit Testing Suite & Runnable JAR (`lab-14/itemapp.jar`):**
  - Custom JUnit-style testing harness (`app.test.Test` and `app.test.Assert`):
    - `Assert.assertEquals`, `Assert.assertTrue`, and `Assert.assertThrows`.
  - `ItemServiceTest` executing 4 comprehensive automated tests:
    - `testAddItemIncreasesCount()`: Asserts item collection count increments on addition.
    - `testInvalidPriceThrowsException()`: Asserts non-positive prices throw `IllegalArgumentException`.
    - `testDuplicateIdThrowsException()`: Asserts duplicate IDs throw `IllegalStateException`.
    - `testSaveAndReloadState()`: Saves items to disk, reloads from disk, and asserts all records remain identical.
  - Standalone runnable JAR `lab-14/itemapp.jar` built with manifest specifying `Main-Class: app.ui.ItemApp`.
* **Part B — MiniBank Milestone 3 Capstone Deliverable:**
  - `service.BankService`:
    - Full enterprise service layer integrating `Repository<Account>`, `AccountStore`, `Validator`, `TransferService`, and `ReportGenerator`.
    - Implements all core banking operations: `openAccount`, `deposit`, `withdraw`, `transfer`, `statement`, `listAccounts`, `sortedByBalance`, and `searchByOwnerName`.
    - Decorated with `@Audit` annotations for runtime tracking.
  - **Reflective Audit Logger (`service.AuditLogger` & `model.annotation.Audit`):**
    - Inspects methods for `@Audit(action = "...")` and logs method invocation timestamps, action descriptions, and arguments using Java Reflection.
  - **UI-to-Service Integration & Auto-Persistence (`MiniBank.java`):**
    - Wires all 23 menu options to `BankService`.
    - Auto-loads saved accounts from `data/accounts.dat` at start-up via `StatePersister`.
    - Auto-saves all accounts to `data/accounts.dat` upon selecting exit (Option 23).
  - **Concurrency & Financial Audit Menu Options:**
    - Integrated multi-threaded batch transaction processing (`service.TransactionProcessor` with 4 pool workers) and end-of-day NIO directory reconciliation (`util.ReportGenerator`).
  - **Unit Testing Suite (`test.MiniBankTest` & `test.MiniBankTestRunner`):**
    - Automated test runner verifying 5 comprehensive JUnit tests:
      1. `testDepositIncreasesBalance()`: Asserts deposits increment account balance.
      2. `testWithdrawalOverLimitThrowsException()`: Asserts overdrafts throw `InsufficientFundsException`.
      3. `testConcurrentDepositsKeepBalanceCorrect()`: Stresses 20 concurrent deposit threads against a single account, proving balance consistency without race conditions.
      4. `testStatePersistence()`: Serializes accounts to disk, deserializes them, and asserts data integrity.
      5. `testSearchByOwnerName()`: Validates case-insensitive owner name search filtering.
    - Test runner callable via `java -cp bin test.MiniBankTestRunner` or `java -jar minibank.jar --test`.
  - **Runnable Executable JAR (`minibank.jar`):**
    - Packaged standalone executable containing all compiled modules, manifest, and runtime entry point.

### Practical 13 (Generics & Generic Repository Pattern)
* **Part A1 — Generic Containers (`lab-13/containers`):**
  - Implements generic `Box<T>` with get/set and emptiness check.
  - Implements generic `Pair<A, B>` holding heterogeneous data types.
  - Generic method `<T> T getFirst(T[] array)` safely extracting the first element from any array type without casts.
* **Part A2 — Bounded Numeric Stats (`lab-13/bounded`):**
  - Generic method `<N extends Number> double average(N[] values)` calculating averages across `Integer[]`, `Double[]`, `Long[]`, and `Float[]`.
  - Explains compiler restriction rejecting non-Number types (`String[]`) at compile-time.
* **Part A3 — Generic Array-Backed Stack (`lab-13/stack`):**
  - Array-backed generic `ArrayStack<T>` with dynamic doubling and clean underflow handling via `EmptyStackException`.
  - Reused across `String` and `Integer` types.
* **Part B — Reusable Generic Repository Pattern (`service/Repository.java`):**
  - `service.Repository<T>`:
    - Replaces type-specific storage with a generic `HashMap<String, T>` repository.
    - Inspects `@Id` annotations at runtime via recursive class hierarchy reflection (`extractId()`).
    - Full CRUD support: `save(T entity)`, `findById(String id)`, `findAll()`, `delete(String id)`, and `count()`.
    - Batch persistence method `@SafeVarargs public final ArrayList<T> saveAll(T... items)`.
    - Bounded utility method `<N extends Number> double average(N[] values)`.
    - **Supplementary Problem 1:** Reused directly for `Customer` (`Repository<Customer>`) without modifying any code.
    - **Supplementary Problem 2:** Tracks item count via `count()` / `size()`.
  - `MiniBank.java`:
    - Integrated automated Practical 13 verification on startup, confirming `@Id` reflection extraction, generic repository lookups, Customer entity reuse, and bounded type parameter statistics.

### Practical 12 (Milestone 2 Collections Framework Upgrade)
* **Part A1 — Word Frequency Analysis (`lab-12/wordfreq`):**
  - Tokenizes paragraphs into normalized lowercase words using regex patterns.
  - Counts word occurrences using `HashMap<String, Integer>`.
  - Sorts entries by frequency (highest first) using a custom `Comparator<Map.Entry<String, Integer>>`, breaking ties alphabetically.
* **Part A2 — Unique Visitors Deduplication (`lab-12/visitors`):**
  - Uses `HashSet<String>` for $O(1)$ membership checking and calculating true unique count.
  - Uses `LinkedHashSet<String>` to preserve first-seen chronological insertion order while eliminating repeat logins.
  - Extracts and displays the first three distinct visitors in first-seen order.
* **Part A3 — Gaming Leaderboard System (`lab-12/leaderboard`):**
  - Maintains player scores in a `Map<String, Integer>`.
  - Dynamically extracts Top-N leaders using a multi-criteria `Comparator`: primary sort by score descending, secondary tie-breaker by player name alphabetically.
  - Supports live score adjustments and tie resolution via `Map.merge()`.
* **Part B — MiniBank Collections Framework Upgrade (Milestone 2):**
  - `service.AccountStore`:
    - Replaces fixed-size `Account[]` arrays with a fast `HashMap<String, Account>` keyed by `accountNumber`.
    - Implements $O(1)$ operations: `add(Account account)`, `get(String accountNumber)`, `getAll()`, `contains(String accountNumber)`.
    - Maintains an append-only audit trail in an `ArrayList<String>` (`recordTransaction()`, `getTransactionHistory()`).
    - Implements `sortedByBalance()` returning an `ArrayList<Account>` sorted descending by balance using a `Comparator`.
    - Implements `sortedByAccountNumber()` using `Comparable<Account>`.
    - **Supplementary Problem 1:** `getAccountsWithBalanceAbove(long threshold)` returning matching accounts.
    - **Supplementary Problem 2:** `getAccountsInKeyOrder()` returning a `TreeMap<String, Account>` keeping accounts naturally sorted by key.
  - `model.Account`:
    - Implements `Comparable<Account>` with `compareTo(Account other)` comparing account numbers.
  - `service.TransactionProcessor`:
    - Incorporates a thread-safe `ConcurrentHashMap<String, Account>` for shared accounts (`sharedAccounts`).
    - Supports concurrent deposit and withdrawal task submissions across pool worker threads without synchronization bottlenecks.
  - `MiniBank.java`:
    - Pre-flight automated verification asserting exact lab requirements: adds accounts with balances 5000, 2000, 9000; proves `get("AC0002")` returns the correct account; verifies `sortedByBalance()` yields the exact sequence `[9000, 5000, 2000]`.
    - Seamlessly upgraded interactive console shell with 20 comprehensive options managing accounts directly via `AccountStore`.

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
