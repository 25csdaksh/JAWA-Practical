# OOP Lab Practical Portfolio — Semester Work

This repository contains the complete Java solutions for **Practical 1** through **Practical 6**, organized into practice programs and the semester-long **MiniBank** project.

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
│   ├── shape/               (Shape Areas Polymorphism)
│   ├── payroll/             (Employee Hierarchy & Payroll)
│   └── media/               (Media Late Fee Calculator)
├── lab-06/                  (Practical 6 — Practice Programs)
│   ├── remote/              (Part A1: Switchable Interface, Fan, Light & Schedule Lambdas)
│   │   ├── Switchable.java
│   │   ├── Fan.java
│   │   ├── Light.java
│   │   ├── DeviceScheduleRule.java
│   │   └── RemoteDriver.java
│   ├── notification/        (Part A2: Notifier Functional Interface & Urgent Marker Interface)
│   │   ├── Notifier.java
│   │   ├── Urgent.java
│   │   ├── UrgentNotifier.java
│   │   └── NotificationDriver.java
│   └── discount/            (Part A3: Advanced Discount Engine & Runnable JAR)
│       ├── discount/rule/DiscountRule.java
│       ├── discount/service/DiscountEngine.java
│       ├── discount/app/DiscountApp.java
│       ├── DiscountDriver.java
│       ├── MANIFEST.MF
│       └── discount.jar
├── model/                   (Practical 6 — MiniBank Domain Entities)
│   ├── Account.java         (Implements Transactable & InterestBearing)
│   ├── SavingsAccount.java  (Extends Account, Implements Premium)
│   ├── CurrentAccount.java  (Extends Account)
│   ├── FixedDepositAccount.java (Extends Account, Implements Premium)
│   ├── Customer.java        (Customer Record with Address & Cloneable)
│   ├── BankInfo.java        (Record for Bank Branch Details)
│   ├── TransactionType.java (Enum for Transaction Types)
│   └── Command.java         (Record for Banking Commands)
├── service/                 (Practical 6 — MiniBank Core Capabilities & Rules)
│   ├── Transactable.java    (deposit & withdraw contract)
│   ├── InterestBearing.java (interestRate + default yearlyInterest & projectedBalance)
│   ├── WithdrawRule.java    (Functional Interface for withdrawal validation)
│   └── Premium.java         (Marker Interface for VIP/High-Yield Accounts)
├── util/                    (Practical 6 — MiniBank Utilities & Helpers)
│   ├── Validator.java       (Regex Verification Methods)
│   ├── StatementFormatter.java (Account Statement Builder)
│   └── CommandParser.java   (String Line Command Parser)
├── MiniBank.java            (Main Banking Application with Static Imports & Lambdas)
├── MANIFEST.MF              (JAR Manifest specifying Main-Class: MiniBank)
├── minibank.jar             (Packaged Runnable JAR Artifact)
├── .gitignore               (Configured to ignore compiled .class and bin/ artifacts)
└── README.md                (Portfolio Documentation & Answers to Lab Questions)
```

---

## 🚀 Compilation, Packaging & Execution

Make sure you have JDK 17 or higher installed on your system. Run all commands from the repository root directory.

### 1. Compiling MiniBank & All Packages into `bin/`
```powershell
javac -d bin service/*.java model/*.java util/*.java MiniBank.java lab-06/remote/*.java lab-06/notification/*.java lab-06/discount/discount/rule/*.java lab-06/discount/discount/service/*.java lab-06/discount/discount/app/*.java lab-06/discount/*.java
```

### 2. Building the Runnable JAR (`minibank.jar`)
```powershell
jar cfm minibank.jar MANIFEST.MF -C bin .
```

### 3. Running Practical 6 Programs
* **Remote Control (Switchable Default Methods & Schedule Lambdas):**
  ```powershell
  java -cp bin RemoteDriver
  ```
* **Notification Senders (Notifier Lambda & Urgent Marker Interface):**
  ```powershell
  java -cp bin NotificationDriver
  ```
* **Discount Engine (Packaged Runnable JAR):**
  ```powershell
  java -jar lab-06/discount/discount.jar 1
  ```

### 4. Running the MiniBank Runnable JAR
```powershell
java -jar minibank.jar
```

---

## 📝 Key Questions, Analysis & Answers

### 💡 Practical 1 Questions

#### 1. What is the role of the JVM, and what file does the javac compiler produce?
* **JVM (Java Virtual Machine):** The JVM is the runtime engine that converts intermediate Java bytecode into native machine instructions for the host platform while managing garbage collection and security.
* **`javac` Output File:** The `javac` compiler translates `.java` source files into platform-independent `.class` files containing bytecode.

#### 2. How does a switch expression differ from a traditional switch statement?
* **Value Return:** A switch expression resolves directly to a value that can be assigned or returned.
* **No Fall-through:** Arrow syntax (`case X ->`) executes only the matched branch, eliminating accidental fall-through without requiring `break`.
* **Exhaustiveness:** Switch expressions require exhaustive case coverage at compile-time.

#### 3. Why is an enum a good choice for a fixed set of menu options, and a record for read-only data?
* **Enum for Menus:** Enums provide type-safe constants, eliminating magic strings and invalid user selections.
* **Record for Read-only Data:** Records automatically synthesize immutable final fields, canonical constructors, getters, `equals()`, `hashCode()`, and `toString()`.

---

### 💡 Practical 2 Questions

#### 1. What is encapsulation, and how do private fields with public getters enforce it?
* Encapsulation hides internal object state and permits access only through controlled public methods (`getters`/`setters`), protecting data invariants (e.g. preventing direct balance manipulation).

#### 2. Why is accountNumber declared final?
* To guarantee that once an account identifier is generated, it remains immutable and cannot be altered or reassigned.

#### 3. What is the difference between a static field and an instance field when generating IDs?
* A **static field** belongs to the class and maintains a single global sequence counter across all objects.
* An **instance field** belongs to a specific object and stores the assigned permanent ID for that instance.

---

### 💡 Practical 3 Questions

#### 1. Why must equals() and hashCode() be overridden together?
* If two objects are equal according to `equals()`, they must return the exact same `hashCode()`. Failing to override both breaks hash-based data structures (like `HashSet` and `HashMap`).

#### 2. What does the default Object.toString() return, and why override it?
* Default `toString()` returns `ClassName@HashCodeHex`. Overriding it provides human-readable diagnostic state.

#### 3. What is the difference between a static nested class and an inner class?
* A **static nested class** does not retain an implicit reference to an enclosing outer class instance.
* An **inner class** (non-static) is bound to an enclosing instance and can access all outer members directly.

---

### 💡 Practical 4 Questions

#### 1. What is the difference between String, StringBuilder and StringBuffer, and when is each preferred?
* **`String`:** Immutable, thread-safe, best for constants, map keys, and read-only text.
* **`StringBuilder`:** Mutable, unsynchronized, fast, preferred for heavy string manipulation in single-threaded contexts.
* **`StringBuffer`:** Mutable, synchronized, thread-safe, used when multiple threads modify the same buffer concurrently.

#### 2. What does the regular expression [6-9][0-9]{9} match, and why use anchors?
* Matches a 10-digit Indian mobile number starting with 6, 7, 8, or 9. Anchors (`^` and `$`) ensure the *entire* input string matches without trailing or prefix noise.

#### 3. Why should input be validated at the boundary before it is used?
* Boundary validation enforces fail-fast error detection, prevents invalid domain state, and defends against security vulnerabilities.

---

### 💡 Practical 5 Questions

#### 1. What is the difference between an abstract class and a concrete class?
* An **abstract class** cannot be directly instantiated and may contain abstract methods serving as a contract. A **concrete class** provides full implementations and can be instantiated via `new`.

#### 2. What is dynamic method dispatch (run-time polymorphism)?
* The process where a call to an overridden method is resolved at run time based on the actual object type rather than the reference type.

#### 3. What does the super keyword do in a subclass constructor?
* Invokes a parent class constructor, ensuring inherited fields are initialized before the subclass executes its own logic.

---

### 💡 Practical 6 Questions

#### 1. What is the difference between an interface and an abstract class?
* **Multiple Inheritance:** A class can implement **multiple interfaces**, but can extend only **one abstract class** (single inheritance).
* **State (Fields):** Abstract classes can maintain instance variables, mutable fields, and constructors. Interfaces cannot have instance fields or constructors (only `public static final` constants).
* **Purpose:** Abstract classes define an *“is-a”* identity relationship with shared code and internal state. Interfaces define a *“can-do”* contract or capability (e.g. `Transactable`, `InterestBearing`) that unrelated classes can fulfill.

#### 2. What is a functional interface, and how does a lambda use it?
* **Definition:** A **functional interface** is an interface that declares **exactly one abstract method** (SAM — Single Abstract Method), optionally annotated with `@FunctionalInterface`.
* **How Lambda Uses It:** A lambda expression provides an inline, anonymous implementation of that single abstract method without class boilerplate. The Java compiler infers the method parameter types and return type from the target functional interface signature (e.g. `(account, amount) -> amount <= 50000`).

#### 3. Why is a default method useful in an interface?
* **Interface Evolution / Backward Compatibility:** Allows developers to add new methods (with concrete default bodies) to existing interfaces without breaking existing implementing classes.
* **Optional / Utility Behavior:** Enables interfaces to provide reusable default implementations that rely on the interface's abstract getters (e.g., `yearlyInterest()` and `projectedBalance(years)` in `InterestBearing`). Subclasses inherit this logic automatically or can override it if specialized behavior is needed.

---

## 🛠️ Implemented Features & Supplementary Solutions

### Practical 6
* **Part A1 — Remote Control (`Switchable` Default Method & Schedule Lambdas):**
  - Interface `Switchable` with `void on()`, `void off()`, and `default void toggle()`.
  - Concrete classes `Fan` and `Light`.
  - Functional interface `DeviceScheduleRule` implemented first as an **Anonymous Class** (night hours rule) and second as a **Lambda Expression** (daytime eco rule).
* **Part A2 — Notification Senders (`Notifier` Lambda & `Urgent` Marker Interface):**
  - Functional interface `Notifier` implemented as lambda expressions for Email, SMS, and Push.
  - Marker interface `Urgent` with no methods.
  - Broadcast loop checks `if (notifier instanceof Urgent)` to dispatch redundant urgent alerts twice.
* **Part A3 — Dynamic Discount Engine (Runnable JAR):**
  - Packaged modular engine with `discount.rule`, `discount.service`, and `discount.app`.
  - Runtime lambda discount selection applied to price batches.
  - Packaged and executed via runnable `discount.jar`.
* **Part B — MiniBank Clean Architecture & Interface Packaging:**
  - **`service` package:**
    - `Transactable`: Interface defining `void deposit(long amount)` and `boolean withdraw(long amount)`.
    - `InterestBearing`: Interface defining `double interestRate()`, `default double yearlyInterest()`, and supplementary `default double projectedBalance(int years)`.
    - `WithdrawRule`: Functional interface `@FunctionalInterface` with `boolean allow(Account account, long amount)`.
    - `Premium`: Marker interface identifying VIP accounts.
  - **`model` package:**
    - `Account` implements `Transactable` and `InterestBearing`.
    - `SavingsAccount` and `FixedDepositAccount` implement `Premium`.
    - `CurrentAccount`, `Customer`, `BankInfo`, `TransactionType`, `Command`.
  - **`util` package:**
    - `Validator`, `StatementFormatter`, `CommandParser`.
  - **`MiniBank.java`:**
    - Demonstrates `WithdrawRule` via **Anonymous Class** and **Lambda Expression**.
    - Demonstrates default methods on `InterestBearing`.
    - Detects `Premium` marker interface via `instanceof Premium`.
    - Uses **static imports** for `Validator` (`import static util.Validator.*;`).
  - **Runnable JAR:**
    - Built [minibank.jar](file:///c:/Users/daksh/Documents/1.%20SEM%20-%203/JAWA/A.lab%20practical/minibank.jar) with `MANIFEST.MF` specifying `Main-Class: MiniBank`.
    - Successfully verified with `java -jar minibank.jar`.
