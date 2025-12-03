## SECURI- VAULT: A SECURE CONSOLE PASSWORD BUILDER

**DESCRIPTION / OVERVIEW**



&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;SecuriVault is a console-based password management system built in Java to demonstrate all four pillars of Object-Oriented Programming: Encapsulation, Inheritance, Abstraction, and Polymorphism. Its purpose is to give users a secure, structured way to generate, store, update, and manage account passwords while showcasing clean OOP architecture. The system solves the common problem of weak or reused passwords by offering customizable password generation rules and a safe vault structure for CRUD operations. With features like intelligent password composition, entry validation, vault security checks, and a user-friendly menu interface, SecuriVault provides both educational value and practical password management functionality.

 **OOP CONCEPT APPLIED**


    This section explains how the four fundamental Object-Oriented Programming (OOP) principles—encapsulation, inheritance, polymorphism, and abstraction—were applied in the development of the SecuriVault Password Manager System. Each pillar is described in relation to its actual function and behavior in the program, now organized with a modular package structure that emphasizes separation of concerns.

 *a. Encapsulation*

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Encapsulation in the SecuriVault system was implemented through the proper use of access modifiers, getters, setters, and data validation to protect sensitive data and ensure controlled access to class members. The encapsulation package contains two key classes that demonstrate this principle.

**VaultEntry Class**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;In the VaultEntry class, the accountName and password fields are declared as private, preventing direct external access and manipulation from classes in other packages. Public getter methods such as getAccountName() and getPassword() provide read-only access to these fields, while setter methods include validation logic to maintain data integrity.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The setPassword() method validates that passwords are not null and are at least 5 characters long before allowing the value to be set, throwing a custom PasswordException (imported from the polymorphism package) if validation fails. Similarly, the setAccountName() method checks for null or empty values and throws a PasswordException with a descriptive error message. The constructor enforces these validations immediately upon object creation by calling both setter methods, ensuring that no VaultEntry object can exist in an invalid state.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The class also overrides the equals(), hashCode(), and toString() methods, enabling proper comparison, hashing for use in collections, and formatted display of vault entries. This demonstrates encapsulation by controlling not only how data is accessed and modified, but also how objects are compared and represented.

**VaultManager Class**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The VaultManager class encapsulates the internal Map<String, VaultEntry> data structure that stores all password entries, exposing only necessary operations through public methods like createEntry(), readEntry(), updateEntry(), and deleteEntry(). The internal storage implementation remains completely hidden from external packages, meaning that the SecuriVaultSystem class in the root package cannot directly access or modify the Map—it must go through the controlled public interface.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The createEntry() and updateEntry() methods use try-catch blocks to handle PasswordException errors that may occur during entry creation or password updates. When exceptions occur, the system catches them, displays appropriate error messages using System.err.println(), and returns false to indicate failure. This demonstrates encapsulation by hiding error handling complexity within the class while providing a simple boolean return value to indicate success or failure.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Additional utility methods such as isEmpty(), size(), containsAccount(), and clearVault() provide safe ways to query and manage the vault state without exposing the underlying data structure. The readAllEntries() method returns a defensive copy of the vault Map (new HashMap<>(vault)) rather than the original, preventing external code from modifying the internal collection directly—a key principle of proper encapsulation.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The **SecuriVaultSystem** class demonstrates encapsulation by keeping its vaultManager, generator, scanner, and exitFlag fields private, allowing users to interact with the system only through well-defined public methods. This implementation of encapsulation protects the system's data from unauthorized access and modification while maintaining flexibility for future changes to internal implementations without affecting external code across different packages.

*b. Inheritance*

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Inheritance in the SecuriVault system establishes an **"IS-A" relationship** between password generators through a clear class hierarchy organized in the inheritance package, promoting code reuse and specialization. The system features an abstract superclass called **AbstractPasswordGenerator** (located in the abstraction package) that serves as the parent class for three concrete subclasses: **ComplexGenerator, LeetSpeakGenerator,** and **PhraseGenerator** (all located in the inheritance package).

**Abstract Superclass**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The abstract superclass defines an abstract method generate(int length, int uppercaseCount, int numberCount, int symbolCount) that all subclasses must implement, as well as two protected helper methods:

1. getCharacterPool(String type): Returns an immutable List<Character> containing character sets for "lowercase", "uppercase", "numbers", or "symbols". The method uses a switch expression and throws an IllegalArgumentException for invalid character types, demonstrating proper input validation within inherited methods.
2. getRandomChar(List<Character> pool): Selects and returns a random character from the provided pool using Math.random() for index generation, providing safe random character selection.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;These protected methods are accessible to all subclasses but hidden from external classes, promoting code reuse while maintaining encapsulation.

**Concrete Subclasses**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Each subclass in the inheritance package extends AbstractPasswordGenerator using the import statement import abstraction.AbstractPasswordGenerator; and inherits these helper methods, eliminating code duplication while implementing its own unique password generation algorithm in the overridden generate() method:

**LeetSpeakGenerator**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Creates passwords by converting letters to leet speak symbols (such as 'a' to '@', 'e' to '3', 's' to '$'). It uses a static **HashMap** (LEET_MAP) initialized in a static block to store character mappings. The generation algorithm builds a base password with letters, converts them to leet speak to naturally add numbers, adds additional symbols, and shuffles the result using Collections.shuffle() for randomization. This demonstrates inheritance by reusing the parent's getCharacterPool() and getRandomChar() methods while implementing unique leet speak conversion logic.

**PhraseGenerator**

Generates memorable word-based passphrases by combining random words from a static String array (WORD_POOL) containing 48 words like "dragon", "tiger", "ocean", etc. It includes private helper methods capitalizeFirst() to uppercase the first letter of words and getRandomWord() for random word selection. The generator calculates optimal word count, adds separators (numbers or symbols) between words, and ensures the final password meets the exact length requirement through padding or trimming. This demonstrates inheritance by using inherited methods for character generation while adding specialized word-based functionality.

**ComplexGenerator**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Creates passwords with balanced random distribution of all character types using standard randomization techniques inherited from the abstract parent class.


&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This inheritance structure allows all three generators to share common functionality through the abstract parent class while specializing in their specific password generation approaches. The package separation makes it easy to add new generator types in the future by simply creating another subclass in the inheritance package that imports and extends AbstractPasswordGenerator and implements the generate() method according to its own algorithm.

*c. Polymorphism*

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Polymorphism in the SecuriVault system is achieved through method overriding, dynamic method dispatch, and runtime type checking, allowing the system to treat different generator objects uniformly while exhibiting unique behaviors at runtime.

**Method Overriding**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Each of the three generator subclasses—ComplexGenerator, LeetSpeakGenerator, and PhraseGenerator—overrides the abstract generate() method from the AbstractPasswordGenerator superclass with its own specific implementation, creating completely different password generation behaviors from the same method signature. This is the foundation of polymorphism in the system, where multiple classes provide their own implementation of the same method.

**Dynamic Binding and Superclass References**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;In the SecuriVaultSystem class, the generator is declared using a superclass reference as private AbstractPasswordGenerator generator, which can hold any of the three concrete generator objects through polymorphic assignment. The system imports the necessary classes using:

<img width="503" height="144" alt="Image" src="https://github.com/user-attachments/assets/f8d60d89-1084-47cc-a6cf-2f85e380f577" />

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;When the user selects a generator in the changeGeneratorFlow() method, the system assigns the appropriate subclass object to the superclass reference (e.g., generator = new LeetSpeakGenerator()), demonstrating polymorphism in action across package boundaries. The actual concrete type is determined at runtime based on user choice, not at compile time.

**Runtime Polymorphism in Action**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The key benefit of this polymorphic design becomes evident in the createEntryFlow() method, where the single line String generated = generator.generate(length, upper, num, sym) produces completely different password outputs depending on which generator object is currently assigned:

-If generator references a ComplexGenerator, it produces random character combinations

-If generator references a LeetSpeakGenerator, it produces leet speak conversions

-If generator references a PhraseGenerator, it produces word-based passphrases

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The actual method implementation is determined at runtime through dynamic dispatch—the JVM determines which version of generate() to call based on the actual object type, not the reference type. This is the essence of polymorphism: one interface, multiple implementations.

**Polymorphic Exception Handling**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The system demonstrates polymorphism in exception handling through the custom PasswordException class located in the polymorphism package. This exception class extends the standard Exception class and provides two constructors:

1. PasswordException(String message) for basic error messages
2. PasswordException(String message, Throwable cause) for exception chaining

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Both VaultEntry and VaultManager classes import and throw this custom exception (import polymorphism.PasswordException;), while catch blocks handle it polymorphically as an Exception subtype. This demonstrates how polymorphism applies not only to regular classes but also to exception hierarchies—the same try-catch mechanism can handle the parent Exception type or the specialized PasswordException type interchangeably.

**Runtime Type Checking**

The system uses runtime type checking with the instanceof operator in the getGeneratorName() method to identify the current generator type and display appropriate information to the user:

<img width="497" height="188" alt="Image" src="https://github.com/user-attachments/assets/668fe391-07f9-4da8-9316-b2c8f50181e9" />

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This is another demonstration of polymorphism, as the system queries the actual runtime type of the object rather than its compile-time reference type, allowing different behavior based on the concrete class being used.
This polymorphic approach provides flexibility, allowing users to switch between different password generation strategies seamlessly without requiring any changes to the calling code in SecuriVaultSystem, and making it simple to add new generator types in the future by simply adding a new class to the inheritance package.

*d. Abstraction*

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Abstraction in the SecuriVault system is implemented through the AbstractPasswordGenerator abstract class (located in the abstraction package), which defines a common interface for password generation while hiding the complex implementation details from users of the system.

**Abstract Method Contract**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The abstract class declares an abstract method generate(int length, int uppercaseCount, int numberCount, int symbolCount) that specifies what all password generators must do—generate a password with specific character requirements—without dictating how they should accomplish this task. This enforces a contract that all subclasses must implement, leaving the implementation details to the concrete subclasses in the inheritance package.


**Concrete Helper Methods**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The abstract class provides two concrete protected helper methods that are implemented once in the abstract class and shared by all subclasses across packages:

1. getCharacterPool(String type): Uses a modern switch expression to return immutable character lists for different types ("lowercase", "uppercase", "numbers", "symbols"). The method includes validation with an IllegalArgumentException for invalid types, and uses List.of() to create unmodifiable lists, preventing accidental modification of character pools. This demonstrates abstraction by providing a complete, reusable implementation that all subclasses can use without reimplementing.
2. getRandomChar(List<Character> pool): Provides safe random character selection using Math.random() * pool.size(), returning a random character from any provided pool. This abstracts away the details of random selection, allowing subclasses to simply call this method without implementing their own randomization logic.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;These methods eliminate code duplication while providing commonly needed functionality that supports the abstract contract.

**Simplified Client Code**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;From the perspective of the SecuriVaultSystem class, the abstraction simplifies password generation to a single method call—generator.generate(12, 2, 3, 2)—without requiring knowledge of the underlying:

-Algorithm implementations (leet speak conversion, phrase building, complex randomization)

-Character pool management and immutable list creation

-Random selection logic

-String building processes with StringBuilder or ArrayList

-Shuffling, padding, or trimming operations


The system only needs to import AbstractPasswordGenerator from the abstraction package to declare the generator reference:

<img width="533" height="95" alt="Image" src="https://github.com/user-attachments/assets/d32bb5b5-3d6a-47de-9a17-9da48ab20698" />

And then import specific implementations from the inheritance package when instantiating concrete generators:

<img width="345" height="64" alt="Image" src="https://github.com/user-attachments/assets/7fbe3463-a4cc-4d3a-ba12-918a94eb7a17" />

**Benefits of Abstraction**

This abstraction layer provides a clean separation of concerns between:

-Interface (what operations are available) - defined in AbstractPasswordGenerator

-Implementation (how those operations work) - defined in concrete subclasses

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The abstract class enforces consistency and predictability across all generator types while allowing complete freedom in how each generator achieves its results. This allows the SecuriVaultSystem to work with any password generator through a uniform interface, significantly simplifying the overall system design. When new generators are added, the main system doesn't need to change—it continues to use the same abstract interface, demonstrating the power of abstraction in creating flexible, maintainable code.

**Integration of OOP Principles**

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The SecuriVault system demonstrates how all four OOP principles work together to create a cohesive, well-designed application:

**Cross-Package Communication**

The package structure enforces proper separation while allowing controlled interaction:

-The SecuriVaultSystem (root package) imports classes from all other packages

-The inheritance package imports from the abstraction package

-The encapsulation package imports from the polymorphism package for exception handling

-Each package maintains its own responsibility while contributing to the overall system

**Custom Exception Handling**

The PasswordException class in the polymorphism package demonstrates multiple OOP principles:

* Inheritance: Extends the standard Exception class

* Polymorphism: Can be caught as Exception or PasswordException

* Encapsulation: Used by VaultEntry and VaultManager to protect data integrity

* Abstraction: Simplifies error handling by providing a single exception type for password-related errors


The class provides two constructors:

<img width="575" height="75" alt="Image" src="https://github.com/user-attachments/assets/e8f54ffc-0889-4f3a-9c67-fe72bc196825" />

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This design supports both simple error reporting and exception chaining, making it flexible for different error scenarios throughout the application.

**Code Reusability**

The system maximizes code reuse through:

-Protected helper methods in the abstract class shared by all generators

-Static initialization blocks in LeetSpeakGenerator for efficient resource management

-Static arrays in PhraseGenerator for shared word pools

-Defensive copying in VaultManager to safely share data without exposing internals

**Modern Java Features**

The codebase leverages modern Java features that support OOP principles:

-Switch expressions in getCharacterPool() for cleaner, more readable code

-Static initialization blocks for complex static field initialization

-Collections utilities like Collections.shuffle() and List.of()

-Method overriding of equals(), hashCode(), and toString() for proper object behavior

-Try-catch blocks for exception handling integrated with custom exception types

**CONCLUSION**

The **SecuriVault Password Manager System** successfully demonstrates the practical application of the four fundamental Object-Oriented Programming principles in creating a functional and maintainable software solution. The well-organized package structure enhances the clarity of how each OOP principle is applied:

-The abstraction package defines the abstract contract for all password generators
-The inheritance package showcases three specialized generators extending the common base
-The encapsulation package protects sensitive data with proper access control and validation
-The polymorphism package provides custom exception handling used throughout the system

The integration of these OOP principles results in a robust, scalable, and maintainable system. Future enhancements can be implemented with minimal code changes:
* New generators can extend AbstractPasswordGenerator in the inheritance package
* New validation rules can be added to VaultEntry without affecting other classes
* New exception types can extend PasswordException in the polymorphism package
* New storage mechanisms can replace HashMap in VaultManager without changing the public interface

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The SecuriVault system proves that proper application of OOP principles, combined with good package organization, leads to software that is not only functional but also prepared for evolution and future requirements. The clear separation of concerns across packages makes the codebase easy to understand, maintain, and extend.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This project reinforces the importance of object-oriented programming and proper code organization in modern software development and demonstrates its effectiveness in solving real-world problems in password management and security.




 **PROGRAM STRUCTURE**

    Program Structure - description of the main class and their roles. A text-based class diagram or list of relationships may be included.


&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The SecuriVaultSystem class is the main part of the program that controls how users interact with it. It shows menus, takes user commands, and performs actions like creating passwords and managing the password vault. As the main class, its primary role is to serve as the central controller that receives user input, processes commands through a menu-driven interface, directs operations to other classes, and ensures smooth execution by handling exceptions. This class coordinates how the different parts of the program work together. The VaultManager class is responsible for managing all the stored passwords. It handles adding new passwords, updating existing ones, retrieving saved passwords, and deleting entries as needed. It keeps the information organized and secure inside the system.

Each password and account information is stored in a VaultEntry object. This object holds the account name and password. It also checks that the password meets the required rules by using a special kind of error called PasswordException. Password creation is designed through an abstract class called AbstractPasswordGenerator. This class provides a general design for password creation but does not create passwords on its own. Instead, several child classes like ComplexGenerator, LeetSpeakGenerator, and PhraseGenerator inherit from this class and each implements a different way to generate passwords. SecuriVaultSystem uses VaultManager to manage the password entries and calls different password generators through the AbstractPasswordGenerator interface without depending on the specific type. VaultManager has a close relationship with VaultEntry, as it owns and controls the password entries. VaultEntry uses PasswordException to validate passwords, and the SecuriVaultSystem handles these exceptions properly to avoid program crashes and to inform the user of any errors smoothly.



**A text-based Diagram**

<img width="401" height="873" alt="Image" src="https://github.com/user-attachments/assets/738cc94e-5458-44d2-acc4-28ddbbc39813" />
<img width="667" height="887" alt="Image" src="https://github.com/user-attachments/assets/054b1d0e-846c-4ee3-8bf8-a8be4329a046" />


  


*Legend:
uses        = one class calls another

contains    = composition (class owns/holds the other)

inherits    = child extends parent (OOP inheritance)

abstract    = class cannot be instantiated

Throws      = for exceptions

composition = for “VaultManager owns VaultEntry”


# How to Run the Program 

     provide a step-by-step instruction on how to compile and run the program using the command line.

Follow these step-by-step instructions to compile and run the SecuriVault Password Manager from the command line:

Prerequisites
Java Development Kit (JDK) 11 or higher installed

Command Prompt (Windows) or Terminal (macOS/Linux)

**Step 1:** Navigate to Project Directory

Open your command line interface and navigate to the project root directory:

cd C:\Users\huawei\OneDrive\Documents\OOP_FinalProject

**Step 2:** Compile All Java Files

Compile the program in the following order (dependencies first):

For Windows (Command Prompt/PowerShell):

** Compile abstraction package**

javac src/abstraction/AbstractPasswordGenerator.java

**Compile polymorphism package** 

javac src/polymorphism/PasswordException.java

**Compile encapsulation package**

javac src/encapsulation/VaultEntry.java

javac src/encapsulation/VaultManager.java

**Compile inheritance package**

javac src/inheritance/ComplexGenerator.java

javac src/inheritance/LeetSpeakGenerator.java

javac src/inheritance/PhraseGenerator.java

**Compile main system**

javac src/SecuriVaultSystem.java






**Alternative: Compile all at once (Windows):**

javac src/abstraction/AbstractPasswordGenerator.java src/polymorphism/PasswordException.java src/encapsulation/VaultEntry.java src/encapsulation/VaultManager.java src/inheritance/ComplexGenerator.java src/inheritance/LeetSpeakGenerator.java src/inheritance/PhraseGenerator.java src/SecuriVaultSystem.java

**For macOS/Linux:**

javac src/abstraction/*.java src/polymorphism/*.java src/encapsulation/*.java src/inheritance/*.java src/SecuriVaultSystem.java

**Step 3:** Verify Successful Compilation
Check that .class files were created in each directory:

 *Check for compiled files*

dir src\*.class
dir src\abstraction\*.class
dir src\encapsulation\*.class  
dir src\inheritance\*.class
dir src\polymorphism\*.class

**Step 4:** Run the Program
Navigate to the src directory and run the main class:

Option A: From project root:

cd src
java SecuriVaultSystem

Option B: Without changing directory:

powershell
java -cp src SecuriVaultSystem

Step 5: Using the Program
Once the program starts:

First: Select a password generator type (1-3)




Main Menu Options:

1 - Create new password entry

2 - View all saved entries

3 - Update existing entry

4 - Delete an entry

5 - Change password generator

6 - Exit program


 **SAMPLE OUTPUT**

    show what the program looks like when it runs. A short code block or screenshot may be included.

<img width="548" height="254" alt="Image" src="https://github.com/user-attachments/assets/28b5110b-9e50-4817-8a4f-328c51051369" />

A sample console output showing the startup screen for the SecuriVault System, featuring the program header and a prompt to begin.



This console output displays the generator selection screen. It clearly outlines the features and provides an example for each of the three available password creation styles before prompting the user for their choice.


This sample shows the user selecting and switching between password generators. The menu displays the currently active generator before the user selects option 1, resulting in a successful switch to the Complex Generator.

This output shows the password creation process using the Complex Generator. It captures the user's configuration (length and character counts) for an account named "Alucard," then displays the resulting secure password and a success confirmation.

Interactive output where the user selects option 2 from the generator menu, resulting in the system switching from the Complex Generator to the LeetSpeak Generator and displaying a success message.

The console screen showing the user's saved password entries. Each entry lists an account name and its corresponding password, demonstrating the variety of outputs from the different available generators.

Console interface for updating a saved password. The user specifies the account "Clint" and provides the new password "OOP_Programming/j@vA," after which the system confirms the entry has been successfully updated.

Post-update view of the password entries. The accounts are listed first, then their corresponding passwords in the same order below, demonstrating that Clint's password has been successfully changed to "OOP_Programming/j@vA".


This output shows the password deletion process, where the user removes the saved entry for the account "Miya" and receives a successful deletion confirmation.


This output displays the password vault after the deletion of the "Miya" entry. It confirms the updated state of the system by listing the three remaining accounts (Clint, Zetian, Alucard) and their corresponding passwords.


The exit screen of the SecuriVault System password manager, displaying a thank-you message and confirming that stored passwords remain secure.

## Acknowledgements

ACKNOWLEDGEMENT

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;We would like to express our deepest gratitude to our professor, Mr. Jayson Abretique, for his patient guidance and unwavering support throughout this project. His expertise, insightful feedback, and continuous encouragement have been crucial in shaping our work and leading it to successful completion. We sincerely appreciate the time and effort he dedicated to mentoring us and helping us overcome challenges along the way.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Our heartfelt thanks also go to our colleagues, whose significant help and collaborative spirit greatly contributed to the success of this project. Their willingness to share knowledge, engage in constructive discussions, and work together as a team made the entire project process valuable. We are grateful for their inspiration and friendship that uplifted our collective progress.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Additionally, we are deeply grateful to our families for their constant emotional support and motivation. Their understanding and unwavering encouragement have provided us with strength and resilience to maintain our focus on achieving our goals. Their presence has been an essential source of comfort throughout this journey.

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Finally, words cannot express our gratitude to God for the strength, blessings, and courage throughout this journey.  It is through His grace that we have been sustained at every step, allowing us to overcome the challenges of this project with determination and hope.

# AUTHOR

James Aldrei D. Decastro | Rheywen M. De Guzman | Hanna Krescha D. Corona | Hanna Mae U. Cumal

Bachelor of Science in Information Technology - 2103

College of Informatics and Computing Sciences

Batangas State University - The National Engineering University

 
