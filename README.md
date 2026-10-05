# OOP Lab Practical Portfolio — Semester Work

This repository contains the complete Java solutions for **Practical 1** through **Practical 7**, organized into practice programs and the modular semester-long **MiniBank** project.

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
│   ├── form/                (Part A1: Custom Annotations & Reflection Form Validator)
│   │   ├── NotBlank.java
│   │   ├── MaxLength.java
│   │   ├── SignupForm.java
│   │   ├── FormValidator.java
│   │   └── FormDriver.java
│   ├── testrunner/          (Part A2: Method-Level @Run Annotation & Mini JUnit Runner)
│   │   ├── Run.java
│   │   ├── TestSuite.java
│   │   ├── MiniTestRunner.java
│   │   └── TestRunnerDriver.java
│   └── csv/                 (Part A3: @Column Mapping & CSV Reflection Mapper)
│       ├── Column.java
│       ├── EmployeeRecord.java
│       ├── CsvMapper.java
│       └── CsvDriver.java
├── model/                   (Domain Entities)
│   ├── annotation/          (Practical 7 — Metadata Annotations)
│   │   ├── Id.java          (@Target(FIELD), @Retention(RUNTIME) marker)
│   │   ├── Positive.java    (@Positive with message "must be > 0")
│   │   └── MaxLength.java   (@MaxLength with int value and message)
│   ├── Account.java         (Annotated with @Id, @Positive, @MaxLength)
│   ├── SavingsAccount.java  (Extends Account, Implements Premium)
│   ├── CurrentAccount.java  (Extends Account)
│   ├── FixedDepositAccount.java (Extends Account, Implements Premium)
│   ├── Customer.java        (Customer Record with Address & Cloneable)
│   ├── BankInfo.java        (Record for Bank Branch Details)
│   ├── TransactionType.java (Enum for Transaction Types)
│   └── Command.java         (Record for Banking Commands)
├── service/                 (Core Capabilities & Rules)
│   ├── Transactable.java    (deposit & withdraw contract)
│   ├── InterestBearing.java (interestRate + default yearlyInterest & projectedBalance)
│   ├── WithdrawRule.java    (@FunctionalInterface for withdrawal validation)
│   └── Premium.java         (Marker Interface for VIP Accounts)
├── util/                    (Utilities & Reflection Helpers)
│   ├── AnnotationValidator.java (Practical 7 — Reflection-based Validator)
│   ├── Validator.java       (Regex Verification Methods)
│   ├── StatementFormatter.java (Account Statement Builder)
│   └── CommandParser.java   (Command Line Parser)
├── MiniBank.java            (Main Banking Shell & Test Driver)
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
javac -d bin model/annotation/*.java service/*.java model/*.java util/*.java MiniBank.java lab-07/form/*.java lab-07/testrunner/*.java lab-07/csv/*.java
```

### 2. Building the Runnable JAR (`minibank.jar`)
```powershell
jar cfm minibank.jar MANIFEST.MF -C bin .
```

### 3. Running Practical 7 Practice Programs
* **Form Validator (Field Annotations & Reflection):**
  ```powershell
  java -cp bin form.FormDriver
  ```
* **Mini Test Runner (Method @Run Reflection Execution):**
  ```powershell
  java -cp bin testrunner.TestRunnerDriver
  ```
* **CSV Column Mapper (Dynamic Header Matching):**
  ```powershell
  java -cp bin csv.CsvDriver
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
* Enables fail-fast behavior, protects internal domain integrity, and prevents injection attacks.

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
* **`@Retention`:** Defines the **lifecycle / retention policy** of an annotation (how long it is preserved):
  - `RetentionPolicy.SOURCE`: Discarded during compilation (e.g. `@Override`, `@SuppressWarnings`).
  - `RetentionPolicy.CLASS`: Recorded in the `.class` file by the compiler but discarded by the JVM at run time.
  - `RetentionPolicy.RUNTIME`: Retained in the bytecode and loaded into JVM memory, making it accessible via Java Reflection at run time (essential for validation frameworks like `@Positive` and `@MaxLength`).
* **`@Target`:** Restricts the **Java elements** where the annotation can be applied (e.g. `ElementType.FIELD`, `ElementType.METHOD`, `ElementType.TYPE`, `ElementType.PARAMETER`).

#### 2. What is reflection, and what does getDeclaredFields() return?
* **Reflection:** A feature of the Java language that allows inspecting, querying, and modifying the internal metadata and state of classes, interfaces, fields, methods, and constructors dynamically at run time without knowing their names at compile time.
* **`getDeclaredFields()`:** Returns an array of `Field` objects reflecting all the fields declared directly by the class, including `public`, `protected`, `default` (package), and `private` fields (excluding inherited fields). Private fields can be read using `field.setAccessible(true)` followed by `field.get(obj)`.

#### 3. How do annotations plus reflection let a framework validate any object?
* **Decoupling:** Annotations declare validation metadata directly on domain model fields without embedding validation logic into the domain class.
* **Generic Processing:** A generic validator (like `AnnotationValidator`) accepts `Object obj`, iterates through its fields using reflection, inspects active annotations (e.g., `field.isAnnotationPresent(Positive.class)`), retrieves the current field value via `field.get(obj)`, tests the rule constraint, and aggregates errors dynamically. This enables universal validation across arbitrary classes.

---

## 🛠️ Implemented Features & Supplementary Solutions

### Practical 7
* **Part A1 — Form Validator (`form` package):**
  - Custom runtime annotations `@NotBlank` and `@MaxLength(int value)`.
  - Annotated `SignupForm` model.
  - `FormValidator` inspects fields via reflection and reports formatted constraint violations.
* **Part A2 — Mini Test Runner (`testrunner` package):**
  - Method-level annotation `@Run(description)`.
  - `MiniTestRunner` inspects classes dynamically, invokes only `@Run` annotated test methods, tracks passed/failed tests, and prints a test summary.
* **Part A3 — CSV Column Mapper (`csv` package):**
  - `@Column(name, required)` annotation.
  - `CsvMapper` dynamically matches CSV header strings to object fields, handles type conversions, and provides graceful fallback for missing columns.
* **Part B — MiniBank Metadata & Reflection Validator:**
  - Standard annotations: `@Override` on all overridden methods; `@FunctionalInterface` on `WithdrawRule`.
  - `model.annotation` package:
    - `@Id`: Marker annotation for entity identifiers (`accountNumber`).
    - `@Positive`: Enforces numeric values $> 0$ with custom message.
    - `@MaxLength`: Enforces string character length caps.
  - `util.AnnotationValidator`:
    - Generic reflection method `public static String[] validate(Object obj)` evaluating `@Positive` and `@MaxLength` across class hierarchies.
    - **Supplementary Problem 1:** Added `@MaxLength(25)` constraint on `ownerName` in `Account` and validated behavior.
    - **Supplementary Problem 2:** Error messages explicitly report the field name and problematic value (e.g., `Field 'balance' with value -100: must be > 0`).
  - `MiniBank.java` test suite:
    - Asserts that a valid account produces 0 errors.
    - Asserts that an account with `balance = -100` produces the `@Positive` error message.
    - Asserts that an oversized owner name produces the `@MaxLength` error message.
    - Added interactive menu option 6 to validate any live account metadata dynamically.
