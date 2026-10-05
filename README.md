# OOP Lab Practical Portfolio — Semester Work

This repository contains the complete Java solutions for **Practical 1**, **Practical 2**, **Practical 3**, **Practical 4**, and **Practical 5**, organized into practice programs and the semester-long **MiniBank** project.

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
├── lab-03/                  (Practical 3 — Practice Programs)
│   ├── point/
│   │   ├── Point.java
│   │   └── PointDriver.java
│   ├── card/
│   │   ├── Card.java
│   │   └── CardDriver.java
│   └── fraction/
│       ├── Fraction.java
│       └── FractionDriver.java
├── lab-04/                  (Practical 4 — Practice Programs)
│   ├── password/
│   │   ├── PasswordChecker.java
│   │   └── Driver.java
│   ├── chat/
│   │   ├── ChatFilter.java
│   │   └── Driver.java
│   └── template/
│       ├── TemplateFiller.java
│       └── Driver.java
├── lab-05/                  (Practical 5 — Practice Programs)
│   ├── shape/               (Part A1: Polymorphic Shape Areas)
│   │   ├── Shape.java
│   │   ├── Circle.java
│   │   ├── Rectangle.java
│   │   ├── Triangle.java
│   │   └── ShapeDriver.java
│   ├── payroll/             (Part A2: Employee Hierarchy & Payroll)
│   │   ├── Employee.java
│   │   ├── FullTime.java
│   │   ├── PartTime.java
│   │   ├── Intern.java
│   │   └── PayrollDriver.java
│   └── media/               (Part A3: Advanced Late Fee Calculator)
│       ├── MediaItem.java
│       ├── Book.java
│       ├── DVD.java
│       ├── AudioBook.java
│       └── MediaDriver.java
├── Customer.java            (Practical 2 & 3 — MiniBank Customer Entity with Address & Cloneable)
├── Account.java             (Practical 5 — Abstract Base Account with interestRate & canWithdraw)
├── SavingsAccount.java      (Practical 5 — 4% Interest with Minimum Balance Enforcement)
├── CurrentAccount.java      (Practical 5 — 0% Interest with Overdraft Limit Support)
├── FixedDepositAccount.java (Practical 5 — 7% Interest with Maturity Lock Protection)
├── Validator.java           (Practical 4 — MiniBank Regex Inputs Validator)
├── TransactionType.java     (Practical 4 — MiniBank Transaction Types Enum)
├── Command.java             (Practical 4 — MiniBank Transaction Command Record)
├── CommandParser.java       (Practical 4 — MiniBank Command String Parser)
├── StatementFormatter.java  (Practical 4 — MiniBank Account Statement Formatter)
├── MiniBank.java            (Interactive Banking Console Shell Application with Polymorphic Accounts)
├── .gitignore               (Configured to ignore compiled .class files)
└── README.md                (Portfolio Documentation & Answers to Lab Questions)
```

---

## 🚀 Compilation and Execution

Make sure you have JDK 17 or higher installed on your system. Run all commands from the repository root directory.

### 1. Compiling All Programs
To compile the core MiniBank files and all Practicals (1 through 5), run:
```powershell
javac lab-01/*.java lab-02/*.java lab-03/point/*.java lab-03/card/*.java lab-03/fraction/*.java lab-04/password/*.java lab-04/chat/*.java lab-04/template/*.java lab-05/shape/*.java lab-05/payroll/*.java lab-05/media/*.java Customer.java Account.java SavingsAccount.java CurrentAccount.java FixedDepositAccount.java MiniBank.java Validator.java TransactionType.java Command.java CommandParser.java StatementFormatter.java
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

### 4. Running Practical 3 Programs
* **Distinct Points:**
  ```powershell
  java -cp lab-03/point PointDriver
  ```
* **Duplicate Card Check:**
  ```powershell
  java -cp lab-03/card CardDriver
  ```
* **Fractions Equivalence:**
  ```powershell
  java -cp lab-03/fraction FractionDriver
  ```

### 5. Running Practical 4 Programs
* **Password Strength Checker:**
  ```powershell
  java -cp lab-04/password Driver
  ```
* **Chat Log Filter:**
  ```powershell
  java -cp lab-04/chat Driver
  ```
* **Template Filler:**
  ```powershell
  java -cp lab-04/template Driver
  ```

### 6. Running Practical 5 Programs
* **Shape Areas (Polymorphism):**
  ```powershell
  java -cp lab-05/shape ShapeDriver
  ```
* **Payroll System (Polymorphism & `instanceof`):**
  ```powershell
  java -cp lab-05/payroll PayrollDriver
  ```
* **Media Late Fee Batch Calculator (Advanced Learners):**
  ```powershell
  java -cp lab-05/media MediaDriver
  ```

### 7. Running the MiniBank Project
To start the fully interactive banking application with polymorphic accounts:
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

### 💡 Practical 3 Questions

#### 1. Why must equals() and hashCode() be overridden together?
* **The contract:** According to the Java specification, if two objects are equal (as defined by `equals()`), they **must** return the same hash code (from `hashCode()`).
* **Why it matters:** Hashing-based collections (like `HashSet` and `HashMap`) use the object's `hashCode()` to find the memory bucket and then use `equals()` to search for the element inside that bucket. If they are not overridden together, two logically equal objects might return different hash codes, placing them in different buckets. This breaks the collection's ability to locate values or prevent duplicates.

#### 2. What does the default Object.toString() return, and why override it?
* **Default return:** The default implementation in `java.lang.Object` returns a string containing the class name, followed by the `@` symbol, and the hexadecimal representation of the object's hash code (e.g., `Account@5e25a50d`).
* **Why override it:** It is overridden to output a clear, user-friendly summary of the object's state (e.g., showing account numbers, balances, and names). This is crucial for logging, debugging, and printing messages.

#### 3. What is the difference between a static nested class and an inner class?
* **Static Nested Class:** Declared with the `static` keyword. It acts like an ordinary top-level class but is packaged inside another class for namespace scoping. It **cannot** directly access the instance variables or non-static methods of the outer class without creating an instance of the outer class.
* **Inner Class (Non-static Nested Class):** Declared without the `static` keyword. Each instance is bound to a specific instance of the outer class. It **can** directly access all variables and methods of its outer class (including private ones).

---

### 💡 Practical 4 Questions

#### 1. What is the difference between String, StringBuilder and StringBuffer, and when is each preferred?
* **`String`:** Immutable character sequence. Operations like concatenation create new String objects, which can cause high memory overhead in loops. Preferred for constant/read-only text, keys in maps, and general variables where thread-safety and simplicity are desired.
* **`StringBuilder`:** Mutable character sequence. Modifies characters in-place without generating garbage objects. It is **not thread-safe** (no synchronization overhead). Preferred for single-threaded string manipulation, dynamic construction, and text formatting in loops.
* **`StringBuffer`:** Mutable character sequence similar to `StringBuilder`, but **thread-safe** because its methods are synchronized. It introduces synchronization overhead. Preferred in multi-threaded environments where a single buffer is concurrently modified by multiple threads.

#### 2. What does the regular expression [6-9][0-9]{9} match, and why use anchors?
* **Match:** Matches any string that starts with a digit from `6` to `9`, followed by exactly 9 digits between `0` and `9` (representing a standard 10-digit Indian mobile number).
* **Why use anchors (`^` and `$`):** Caret (`^`) asserts the start of the string, and dollar (`$`) asserts the end of the string. Using `^[6-9][0-9]{9}$` guarantees that the *entire* input strictly conforms to the pattern. Without anchors, a longer string like `1239876543210456` would contain a matching substring and falsely validate, or invalid prefix/suffix characters would be ignored.

#### 3. Why should input be validated at the boundary before it is used?
* **Security:** Defends the system against malicious inputs (e.g. injection attacks, overflow payloads) before they reach internal layers.
* **Fail-Fast:** Detects and reports invalid parameters immediately, preventing wasted resources (CPU/memory) on processing doomed requests.
* **Data Integrity:** Ensures internal domain objects and databases are never transitioned into inconsistent, corrupt, or illegal states.
* **Separation of Concerns:** Business logic code can focus purely on banking operations under the safe assumption that incoming data is clean and valid.

---

### 💡 Practical 5 Questions

#### 1. What is the difference between an abstract class and a concrete class?
* **Instantiation:** An **abstract class** cannot be instantiated directly using `new` (e.g., `new Account(...)` will cause a compile-time error). It serves as an incomplete conceptual template. A **concrete class** is a complete, fully implemented class that can be instantiated directly (e.g., `new SavingsAccount(...)`).
* **Abstract Methods:** An abstract class can declare **abstract methods** (method signatures with no body using the `abstract` keyword) that subclasses *must* override. A concrete class cannot declare abstract methods and must provide concrete implementations for all inherited abstract methods.
* **Purpose:** Abstract classes define common state, shared helper methods, and contract specifications for a family of related objects, enabling polymorphic operations while preventing the creation of generic, incomplete base objects.

#### 2. What is dynamic method dispatch (run-time polymorphism)?
* **Definition:** Dynamic method dispatch is the mechanism by which Java resolves a call to an overridden method at **run time** rather than at compile time based on the actual object type being referenced, not the reference variable's type.
* **How it works:** When a superclass reference (such as `Account acc`) points to a subclass instance (`SavingsAccount`, `CurrentAccount`, or `FixedDepositAccount`), calling `acc.interestRate()` causes the JVM to look up the object's virtual method table (vtable) and execute the subtype's specific version of `interestRate()`.
* **Benefit:** Allows the client code (like loops or managers) to write flexible, extensible algorithms that work with generalized base types without needing hardcoded `switch` statements or subtype checks.

#### 3. What does the super keyword do in a subclass constructor?
* **Constructor Chaining:** In a subclass constructor, `super(...)` invokes a constructor of the direct superclass.
* **Initialization Order:** It ensures that all inherited fields in the base class (such as `accountNumber`, `ownerName`, and `balance` in `Account`, or `name` and `id` in `Employee`) are properly initialized and validated before the subclass executes its own constructor body.
* **Syntax Requirement:** If used, `super(...)` must strictly be the **very first statement** in the subclass constructor. If omitted, Java automatically inserts a call to the parameterless `super()` constructor (if one exists).

---

## 🛠️ Implemented Features & Supplementary Solutions

### Practical 1
* **Input Validation:** MiniBank catches non-numeric inputs and range errors, re-prompting the user gracefully.
* **Timing Menu Option:** Added timing option to `MenuOption` enum and printed timing details.

### Practical 2
* **Positive Input Enforcements:** Both `deposit()` and `withdraw()` reject negative or zero values.
* **Local Transfer Helper:** Implemented `Account.transfer(Account source, Account destination, long amount)` which safely withdraws from one account and deposits into another, returning transaction status.

### Practical 3
* **Account Statement:** Added `getStatement()` method to `Account` that returns a formatted multi-line statement.
* **Active Status Display:** `Account.toString()` includes active status details (e.g., `Active=true`).
* **Deep Customer Cloning:** `Customer` implements `Cloneable` and performs a deep clone of the nested `Address` class.

### Practical 4
* **Password Strength Checker:** Multi-rule checking (length, uppercase, digit, special characters) with a strength evaluation method.
* **Chat Log Filter:** Stream filter logging with `split()` tokenization, case-insensitive keyword searches, and `StringBuilder` report assembly.
* **Template Placeholder Substitution:** Dynamic placeholder matching and lookup replacement (`[?]` for missing parameters) using `Pattern` and `Matcher`.
* **Input Verification Regexes:** Pre-compiled standard Pattern regexes in `Validator` class for Mobile, Email, PAN, and IFSC.
* **Positive Amount Regex:** Supplementary method `Validator.isValidAmount` enforcing positive non-zero integers.
* **Transaction Commands:** Lightweight Java `record` class `Command` and type-safe `TransactionType` enum representing transactions.
* **Command Line Parser:** Splits input strings, validates token count (reporting errors for malformed lines), validates transaction enums, and returns parsed commands.
* **Statement Formatter:** Implemented `StatementFormatter.buildStatement` using a `StringBuilder`.

### Practical 5
* **Part A1 — Shape Areas (Polymorphism):**
  - Abstract base class `Shape` with abstract method `double area()`.
  - Concrete subclasses `Circle`, `Rectangle`, and `Triangle`.
  - `ShapeDriver` iterates over a polymorphic `Shape[]` array in a single loop, calculating individual areas, running totals, and tracking the shape with the largest area.
* **Part A2 — Payroll System (Hierarchy & `instanceof`):**
  - Abstract base class `Employee` with abstract `monthlySalary()` and shared `name`/`id` constructor calling `super(...)`.
  - Concrete subclasses `FullTime` (fixed salary), `PartTime` (hours × rate), and `Intern` (stipend with university metadata).
  - `PayrollDriver` processes a mixed array, calculating total company payroll and using Java 17 pattern matching `instanceof` to display special intern verification notices.
* **Part A3 — Media Late Fee Calculator (Advanced Learners):**
  - Abstract base class `MediaItem` with abstract `computeLateFee(int daysLate)`.
  - Concrete subclasses `Book` (grace period + daily rate with maximum cap), `DVD` (premium daily rate), and `AudioBook` (daily rate).
  - `MediaDriver` processes a batch of returned media items and prints an itemized fee breakdown with grand total.
* **Part B — MiniBank Polymorphic Account Hierarchy:**
  - `Account` refactored as an `abstract class` with abstract methods `double interestRate()` and `boolean canWithdraw(long amount)`.
  - `SavingsAccount` with `minBalance` constraint, returning `4.0%` interest and allowing withdrawals only when balance remains $\ge \text{minBalance}$.
  - `CurrentAccount` with `overdraftLimit`, returning `0.0%` interest and allowing balance to fall down to $-\text{overdraftLimit}$.
  - `FixedDepositAccount` returning `7.0%` interest with withdrawal lock protection until maturity.
  - **Supplementary Solutions:**
    - `monthlyInterest()` in `Account` computing monthly interest using `interestRate()` and current balance.
    - `FixedDepositAccount` includes maturity date calculation and `canWithdraw()` validation based on deposit lock status.
  - `MiniBank` demonstrates dynamic method dispatch on an `Account[]` array, pattern matching with `instanceof`, polymorphic withdrawal validation tests, and full interactive menu support for creating and managing all account subtypes.
