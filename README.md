# OOP Lab Practical Portfolio — Semester Work

This repository contains the complete Java solutions for **Practical 1** through **Practical 9**, organized into practice programs and the modular, concurrent, thread-safe **MiniBank** project.

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
│   ├── counter/             (Part A1: Counter Race Condition & Synchronized Fix)
│   │   ├── Counter.java
│   │   └── CounterDriver.java
│   ├── seatbooking/         (Part A2: Cinema Seat Booking Race & Oversell Prevention)
│   │   ├── SeatManager.java
│   │   └── SeatBookingDriver.java
│   └── arraysum/            (Part A3: Parallel Array Sum Benchmark & Local Reduction)
│       └── ArraySumDriver.java
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
│   ├── Account.java         (Thread-safe synchronized deposit/withdraw & balance access)
│   ├── SavingsAccount.java  (Thread-safe savings account)
│   ├── CurrentAccount.java  (Thread-safe current account)
│   ├── FixedDepositAccount.java (Thread-safe fixed deposit account)
│   └── ...
├── service/                 (Core Capabilities & Concurrency Workers)
│   ├── AccountWorker.java   (Practical 9 — Multi-threaded Account Worker Runnable)
│   ├── Transactable.java
│   ├── InterestBearing.java
│   ├── WithdrawRule.java
│   ├── Premium.java
│   └── BankingSession.java
├── util/                    (Utilities & Reflection Helpers)
│   ├── AnnotationValidator.java
│   ├── Validator.java
│   ├── StatementFormatter.java
│   └── CommandParser.java
├── MiniBank.java            (Main Banking Shell with Concurrency Race & Fix Demonstration)
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
javac -d bin exception/*.java model/annotation/*.java service/*.java model/*.java util/*.java MiniBank.java lab-09/counter/*.java lab-09/seatbooking/*.java lab-09/arraysum/*.java
```

### 2. Building the Runnable JAR (`minibank.jar`)
```powershell
jar cfm minibank.jar MANIFEST.MF -C bin .
```

### 3. Running Practical 9 Practice Programs
* **Counter Race (Lost Updates vs Synchronized Fix):**
  ```powershell
  java -cp bin counter.CounterDriver
  ```
* **Seat Booking Race (Check-Then-Act Oversell vs Synchronized):**
  ```powershell
  java -cp bin seatbooking.SeatBookingDriver
  ```
* **Parallel Array Sum (Benchmark & Local Reduction):**
  ```powershell
  java -cp bin arraysum.ArraySumDriver
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
* **Definition:** A race condition is a concurrency flaw that occurs when multiple threads concurrently read, modify, and write shared mutable memory without proper synchronization, making the final result dependent on non-deterministic thread scheduling order.
* **Why it occurs in Account deposits:** An operation like `balance += amount` is **not atomic**. At the bytecode level, it involves three distinct steps:
  1. `READ` the current balance into a CPU register.
  2. `MODIFY` (add deposit amount to register).
  3. `WRITE` the register value back to main memory.
  When two or more threads interleave these steps simultaneously, thread B reads stale balance data before thread A writes its update, causing thread A's deposit to be completely overwritten (lost update anomaly).

#### 2. What does the synchronized keyword do?
* **Mutual Exclusion (Mutex):** When a method or block is marked `synchronized`, the executing thread must acquire the intrinsic lock (monitor) of the target object before entering. Only **one thread** can hold the monitor at any given moment; all other threads attempting to enter are put into the `BLOCKED` state until the lock is released.
* **Memory Visibility (Happens-Before):** It establishes a *happens-before* memory barrier. Changes made by a thread before releasing the monitor are flushed to main memory and guaranteed to be visible to the next thread that acquires the same monitor.

#### 3. What are the states in a thread’s life cycle?
Java defines 6 distinct thread states in the `java.lang.Thread.State` enum:
1. **`NEW`:** A thread instance has been created (via `new Thread()`) but not yet started (`start()` not called).
2. **`RUNNABLE`:** The thread is actively executing or ready to run in the JVM waiting for operating system CPU allocation.
3. **`BLOCKED`:** The thread is waiting to acquire a monitor lock to enter/re-enter a `synchronized` block or method.
4. **`WAITING`:** The thread is waiting indefinitely for another thread to perform a specific action (e.g. via `Object.wait()`, `Thread.join()`, `LockSupport.park()`).
5. **`TIMED_WAITING`:** The thread is waiting for another thread for up to a specified waiting time (e.g. `Thread.sleep(ms)`, `Object.wait(timeout)`, `Thread.join(timeout)`).
6. **`TERMINATED`:** The thread has completed its `run()` method execution or died due to an unhandled exception.

---

## 🛠️ Implemented Features & Supplementary Solutions

### Practical 9
* **Part A1 — Counter Race (`counter` package):**
  - Unsynchronized counter demonstrates lost updates ($10 \times 10,000 \rightarrow \sim 37,000$ final count).
  - Synchronized counter guarantees exact $100,000$ count.
* **Part A2 — Cinema Seat Booking Race (`seatbooking` package):**
  - Unsynchronized booking triggers a check-then-act race condition causing overselling (10 tickets sold for 5 seats).
  - Synchronized booking guarantees exactly 5 bookings succeed and remaining 5 requests are rejected.
* **Part A3 — Parallel Array Sum (`arraysum` package):**
  - Parallel array reduction across $1,000,000$ numbers.
  - Compares unsynchronized sum (wrong sum), shared synchronized lock (correct, high lock contention), and thread-local partial sum reduction (fastest throughput, lock-free).
* **Part B — MiniBank Multi-threaded Concurrency & Synchronization:**
  - `service.AccountWorker`: `Runnable` task executing bulk deposits or withdrawals across concurrent threads.
  - `model.Account`: `synchronized` deposit and withdraw methods ensuring thread-safe balance operations.
  - `MiniBank.java` Test Runs:
    - Step 1: 10 threads running 1,000 deposits of Rs. 1 on unsynchronized mode yields an incorrect balance ($< 10,000$).
    - Step 2: 10 threads running 1,000 deposits of Rs. 1 on synchronized mode guarantees **exactly Rs. 10,000**.
    - Step 3 (Supplementary): 5 deposit threads (+Rs. 5,000) and 5 withdrawal threads (-Rs. 2,500) on initial Rs. 5,000 balance yields exactly Rs. 7,500.
    - Step 4: Observed thread states transitions (`NEW` $\rightarrow$ `RUNNABLE` $\rightarrow$ `TERMINATED`).
    - Step 5: Interactive menu option 7 for running live multithreading stress tests.
