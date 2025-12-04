# SECURIVAULT: A SECURE CONSOLE PASSWORD BUILDER

## OVERVIEW

**SecuriVault System** is a console-based password management system built in Java to demonstrate all four pillars of Object-Oriented Programming: Encapsulation, Inheritance, Abstraction, and Polymorphism. Its purpose is to give users a secure, structured way to generate, store, update, and manage account passwords while showcasing clean OOP architecture. The system solves the common problem of weak or reused passwords by offering customizable password generation rules and a safe vault structure for CRUD operations. With features like intelligent password composition, entry validation, vault security checks, and a user-friendly menu interface, SecuriVault provides both educational value and practical password management functionality.

 ## OOP CONCEPTS APPLIED

This section explains how the four fundamental Object-Oriented Programming (OOP) principles—encapsulation, inheritance, polymorphism, and abstraction were applied in the development of the SecuriVault Password Manager System. Each pillar is described in relation to its actual function and behavior in the program, now organized with a modular package structure that emphasizes separation of concerns.

 ## I. ENCAPSULATION

Encapsulation in the SecuriVault system was implemented through the proper use of access modifiers, getters, setters, and data validation to protect sensitive data and ensure controlled access to class members. The encapsulation package contains two key classes that demonstrate this principle.

***VaultEntry Class***

In the VaultEntry class, the accountName and password fields are declared as private, preventing direct external access and manipulation from classes in other packages. Public getter methods such as getAccountName() and getPassword() provide read-only access to these fields, while setter methods include validation logic to maintain data integrity.
The setPassword() method validates that passwords are not null and are at least 5 characters long before allowing the value to be set, throwing a custom PasswordException (imported from the polymorphism package) if validation fails. Similarly, the setAccountName() method checks for null or empty values and throws a PasswordException with a descriptive error message. The constructor enforces these validations immediately upon object creation by calling both setter methods, ensuring that no VaultEntry object can exist in an invalid state.
The class also overrides the equals(), hashCode(), and toString() methods, enabling proper comparison, hashing for use in collections, and formatted display of vault entries. This demonstrates encapsulation by controlling not only how data is accessed and modified, but also how objects are compared and represented.

***VaultManager Class***

The VaultManager class encapsulates the internal Map<String, VaultEntry> data structure that stores all password entries, exposing only necessary operations through public methods like createEntry(), readEntry(), updateEntry(), and deleteEntry(). The internal storage implementation remains completely hidden from external packages, meaning that the SecuriVaultSystem class in the root package cannot directly access or modify the Map, it must go through the controlled public interface. The createEntry() and updateEntry() methods use try-catch blocks to handle PasswordException errors that may occur during entry creation or password updates. When exceptions occur, the system catches them, displays appropriate error messages using System.err.println(), and returns false to indicate failure. This demonstrates encapsulation by hiding error handling complexity within the class while providing a simple boolean return value to indicate success or failure. Additional utility methods such as isEmpty(), size(), containsAccount(), and clearVault() provide safe ways to query and manage the vault state without exposing the underlying data structure. The readAllEntries() method returns a defensive copy of the vault Map (new HashMap<>(vault)) rather than the original, preventing external code from modifying the internal collection directly a key principle of proper encapsulation.

The **SecuriVault System** class demonstrates encapsulation by keeping its vaultManager, generator, scanner, and exitFlag fields private, allowing users to interact with the system only through well-defined public methods. This implementation of encapsulation protects the system's data from unauthorized access and modification while maintaining flexibility for future changes to internal implementations without affecting external code across different packages.

## II. INHERITANCE

Inheritance in the SecuriVault system establishes an **"IS-A" relationship** between password generators through a clear class hierarchy organized in the inheritance package, promoting code reuse and specialization. The system features an abstract superclass called **AbstractPasswordGenerator** (located in the abstraction package) that serves as the parent class for three concrete subclasses: **ComplexGenerator, LeetSpeakGenerator,** and **PhraseGenerator** (all located in the inheritance package).

***Abstract Superclass***

The abstract superclass defines an abstract method generate(int length, int uppercaseCount, int numberCount, int symbolCount) that all subclasses must implement, as well as two protected helper methods:

getCharacterPool(String type): Returns an immutable List<Character> containing character sets for "lowercase", "uppercase", "numbers", or "symbols". The method uses a switch expression and throws an IllegalArgumentException for invalid character types, demonstrating proper input validation within inherited methods.
getRandomChar(List<Character> pool): Selects and returns a random character from the provided pool using Math.random() for index generation, providing safe random character selection. 
These protected methods are accessible to all subclasses but hidden from external classes, promoting code reuse while maintaining encapsulation.

***Concrete Subclasses***

Each subclass in the inheritance package extends AbstractPasswordGenerator using the import statement import abstraction.AbstractPasswordGenerator; and inherits these helper methods, eliminating code duplication while implementing its own unique password generation algorithm in the overridden generate() method:

***LeetSpeakGenerator***

Creates passwords by converting letters to leet speak symbols (such as 'a' to '@', 'e' to '3', 's' to '$'). It uses a static **HashMap** (LEET_MAP) initialized in a static block to store character mappings. The generation algorithm builds a base password with letters, converts them to leet speak to naturally add numbers, adds additional symbols, and shuffles the result using Collections.shuffle() for randomization. This demonstrates inheritance by reusing the parent's getCharacterPool() and getRandomChar() methods while implementing unique leet speak conversion logic.

***PhraseGenerator***

Generates memorable word-based passphrases by combining random words from a static String array (WORD_POOL) containing 48 words like "dragon", "tiger", "ocean", etc. It includes private helper methods capitalizeFirst() to uppercase the first letter of words and getRandomWord() for random word selection. The generator calculates optimal word count, adds separators (numbers or symbols) between words, and ensures the final password meets the exact length requirement through padding or trimming. This demonstrates inheritance by using inherited methods for character generation while adding specialized word-based functionality.

***ComplexGenerator***

Creates passwords with balanced random distribution of all character types using standard randomization techniques inherited from the abstract parent class. 
This inheritance structure allows all three generators to share common functionality through the abstract parent class while specializing in their specific password generation approaches. The package separation makes it easy to add new generator types in the future by simply creating another subclass in the inheritance package that imports and extends AbstractPasswordGenerator and implements the generate() method according to its own algorithm.

## III. POLYMORPHISM

Polymorphism in the SecuriVault system is achieved through method overriding, dynamic method dispatch, and runtime type checking, enabling the system to treat various generator objects uniformly while they exhibit unique behaviors at runtime. The foundation is method overriding, where each of the three generator subclasses ComplexGenerator, LeetSpeakGenerator, and PhraseGenerator provides its own specific implementation of the abstract generate() method inherited from the AbstractPasswordGenerator superclass. Despite sharing an identical method signature, these overrides produce completely different password generation behaviors, such as random character combinations, leet speak conversions, or word-based passphrases. This establishes the core principle of polymorphism: one interface with multiple, distinct implementations.

***Dynamic Binding and Superclass References***

In the SecuriVaultSystem class, the generator is declared using a superclass reference as private AbstractPasswordGenerator generator, which can hold any of the three concrete generator objects through polymorphic assignment. The system imports the necessary classes using:

<img width="503" height="144" alt="Image" src="https://github.com/user-attachments/assets/f8d60d89-1084-47cc-a6cf-2f85e380f577" />

When the user selects a generator in the changeGeneratorFlow() method, the system assigns the appropriate subclass object to the superclass reference (e.g., generator = new LeetSpeakGenerator()), demonstrating polymorphism in action across package boundaries. The actual concrete type is determined at runtime based on user choice, not at compile time.

***Runtime Polymorphism in Action***

The key benefit of this polymorphic design is demonstrated in the createEntryFlow() method, where the single line String generated = generator.generate(length, upper, num, sym) produces entirely different password outputs based on which generator object is currently assigned. For instance, if generator references a ComplexGenerator, it creates random character combinations, while if it references a LeetSpeakGenerator, it returns leet speak conversions, and a PhraseGenerator produces word-based passphrases. The specific implementation of the method is determined at runtime through dynamic dispatch, where the JVM decides which version of generate() to execute based on the actual object type, not the reference type. This dynamic behavior is the essence of polymorphism, providing one interface with multiple possible implementations.

***Polymorphic Exception Handling***

The system also implements polymorphism in its error management through the custom PasswordException class within the polymorphism package. By extending the standard Exception class and offering two constructors, one for basic error messages and another for exception chaining this class integrates into Java's existing exception hierarchy. Both VaultEntry and VaultManager classes throw this specific exception, yet the catch blocks handle it polymorphically, treating it as a subtype of the general Exception. This design illustrates how polymorphism extends beyond regular classes into exception handling, allowing the same try-catch mechanism to process either the parent Exception type or the specialized PasswordException type interchangeably.

***Runtime Type Checking***

The system uses runtime type checking with the instanceof operator in the getGeneratorName() method to identify the current generator type and display appropriate information to the user:

<img width="497" height="188" alt="Image" src="https://github.com/user-attachments/assets/668fe391-07f9-4da8-9316-b2c8f50181e9" />

This is another demonstration of polymorphism, as the system queries the actual runtime type of the object rather than its compile-time reference type, allowing different behavior based on the concrete class being used.
This polymorphic approach provides flexibility, allowing users to switch between different password generation strategies seamlessly without requiring any changes to the calling code in SecuriVaultSystem, and making it simple to add new generator types in the future by simply adding a new class to the inheritance package.

## IV. ABSTRACTION

Abstraction in the SecuriVault system is primarily implemented through the AbstractPasswordGenerator class in the abstraction package, which establishes a common interface for password generation while concealing complex implementation details from system users. The foundation of this abstraction is the abstract method contract, the generate() method specifies what all generators must do (create a password with given requirements) without dictating how, thereby enforcing a uniform interface that concrete subclasses in the inheritance package must fulfill. To support this contract, the abstract class provides concrete helper methods getCharacterPool() and getRandomChar() which offer complete, reusable implementations for creating immutable character lists and performing safe random selection, eliminating code duplication across subclasses while encapsulating these common details. This design culminates in simplified client code: from the perspective of the SecuriVaultSystem, the intricate task of password generation is reduced to a single, high-level call like generator.generate(12, 2, 3, 2). The system remains entirely shielded from the underlying algorithms whether leet speak conversion, phrase building, or complex randomization as well as from internal mechanics like character pool management, string building, shuffling, or padding. This allows the client to interact through a clean, consistent interface regardless of the specific generation strategy employed at runtime.

The system only needs to import AbstractPasswordGenerator from the abstraction package to declare the generator reference:

<img width="533" height="95" alt="Image" src="https://github.com/user-attachments/assets/d32bb5b5-3d6a-47de-9a17-9da48ab20698" />

And then import specific implementations from the inheritance package when instantiating concrete generators:

<img width="345" height="64" alt="Image" src="https://github.com/user-attachments/assets/7fbe3463-a4cc-4d3a-ba12-918a94eb7a17" />


***Benefits of Abstraction***

These abstraction layers establish a clear separation of concerns by defining the interface the set of available operations within the AbstractPasswordGenerator, while delegating the implementation, the specific mechanics of how those operations function to the concrete subclasses. The abstract class enforces consistency and predictability across all generator types by guaranteeing a uniform contract, yet it grants each subclass complete freedom in how it achieves its results. Consequently, the SecuriVaultSystem can interact with any password generator through this single, simplified interface, dramatically streamlining the overall system architecture. This design showcases the power of abstraction in building flexible and maintainable code, as the core system remains unchanged even when new generators are added; it continues to communicate seamlessly through the same abstract interface.

The class provides two constructors:

<img width="575" height="75" alt="Image" src="https://github.com/user-attachments/assets/e8f54ffc-0889-4f3a-9c67-fe72bc196825" />

This design supports both simple error reporting and exception chaining, making it flexible for different error scenarios throughout the application.


***Code Reusability***

The system maximizes code reuse through several carefully implemented strategies. Central to this effort are the protected helper methods within the abstract AbstractPasswordGenerator class, which provide common functionalities like character pool management and random selection to all concrete generator subclasses, eliminating redundancy. Furthermore, specific generators employ additional reuse techniques: the LeetSpeakGenerator utilizes static initialization blocks for efficient, one-time setup of its character mapping resources, while the PhraseGenerator relies on static arrays to maintain a single, shared pool of words accessible to all instances. Beyond inheritance, the principle of reuse extends to data handling; the VaultManager performs defensive copying when managing VaultEntry objects, allowing data to be safely shared and manipulated without exposing or compromising the internal state of the original entries. Together, these approaches ensure that logic and resources are implemented once and reused effectively throughout the application's architecture.


 ## PROGRAM STRUCTURE

The SecuriVaultSystem class is the main part of the program that controls how users interact with it. It shows menus, takes user commands, and performs actions like creating passwords and managing the password vault. As the main class, its primary role is to serve as the central controller that receives user input, processes commands through a menu-driven interface, directs operations to other classes, and ensures smooth execution by handling exceptions. This class coordinates how the different parts of the program work together. The VaultManager class is responsible for managing all the stored passwords. It handles adding new passwords, updating existing ones, retrieving saved passwords, and deleting entries as needed. It keeps the information organized and secure inside the system.

Each password and account information is stored in a VaultEntry object. This object holds the account name and password. It also checks that the password meets the required rules by using a special kind of error called PasswordException. Password creation is designed through an abstract class called AbstractPasswordGenerator. This class provides a general design for password creation but does not create passwords on its own. Instead, several child classes like ComplexGenerator, LeetSpeakGenerator, and PhraseGenerator inherit from this class and each implements a different way to generate passwords. SecuriVaultSystem uses VaultManager to manage the password entries and calls different password generators through the AbstractPasswordGenerator interface without depending on the specific type. VaultManager has a close relationship with VaultEntry, as it owns and controls the password entries. VaultEntry uses PasswordException to validate passwords, and the SecuriVaultSystem handles these exceptions properly to avoid program crashes and to inform the user of any errors smoothly.



**A text-based Diagram**

<img width="838" height="867" alt="Image" src="https://github.com/user-attachments/assets/eca4993d-fcaa-43a4-8e56-85afd805190b" />
<img width="878" height="885" alt="Image" src="https://github.com/user-attachments/assets/ecf2159c-56cd-4252-810b-63d1fde70dc2" />


  


**Legends:**

uses        = one class calls another

contains    = composition (class owns/holds the other)

inherits    = child extends parent (OOP inheritance)

abstract    = class cannot be instantiated

Throws      = for exceptions

composition = for “VaultManager owns VaultEntry”


## HOW TO RUN THE PROGRAM

Follow these step-by-step instructions to compile and run the SecuriVault Password Manager from the command line:

***Prerequisites***

Java Development Kit (JDK) 11 or higher installed

Command Prompt (Windows) or Terminal (macOS/Linux)


***Step 1: Navigate to Project Directory***

Open your command line interface and navigate to the project root directory:

cd C:\Users\huawei\OneDrive\Documents\OOP_FinalProject


***Step 2: Compile All Java Files***

Compile the program in the following order (dependencies first):

***For Windows (Command Prompt/PowerShell):***

***Compile abstraction package***

javac src/abstraction/AbstractPasswordGenerator.java


***Compile polymorphism package***

javac src/polymorphism/PasswordException.java

***Compile encapsulation package***

javac src/encapsulation/VaultEntry.java

javac src/encapsulation/VaultManager.java


***Compile inheritance package***

javac src/inheritance/ComplexGenerator.java

javac src/inheritance/LeetSpeakGenerator.java

javac src/inheritance/PhraseGenerator.java


***Compile main system***

javac src/SecuriVaultSystem.java


***Alternative: Compile all at once (Windows):***

javac src/abstraction/AbstractPasswordGenerator.java src/polymorphism/PasswordException.java src/encapsulation/VaultEntry.java src/encapsulation/VaultManager.java src/inheritance/ComplexGenerator.java src/inheritance/LeetSpeakGenerator.java src/inheritance/PhraseGenerator.java src/SecuriVaultSystem.java

***For macOS/Linux:***

javac src/abstraction/*.java src/polymorphism/*.java src/encapsulation/*.java src/inheritance/*.java src/SecuriVaultSystem.java

***Step 3: Verify Successful Compilation***

Check that .class files were created in each directory:

 ***Check for compiled files***

dir src\*.class
dir src\abstraction\*.class
dir src\encapsulation\*.class  
dir src\inheritance\*.class
dir src\polymorphism\*.class

***Step 4: Run the Program***
Navigate to the src directory and run the main class:

**Option A:** 

From project root:

java SecuriVaultSystem


**Option B:** 

Without changing directory:

java -cp src SecuriVaultSystem

***Step 5: Using the Program***

Once the program starts:

First: Select a password generator type (1-3)



**Main Menu Options:**

1 - Create new password entry

2 - View all saved entries

3 - Update existing entry

4 - Delete an entry

5 - Change password generator

6 - Exit program


 ## SAMPLE OUTPUT

<img width="548" height="254" alt="Image" src="https://github.com/user-attachments/assets/28b5110b-9e50-4817-8a4f-328c51051369" />

A sample console output showing the startup screen for the SecuriVault System, featuring the program header and a prompt to begin.

<img width="547" height="361" alt="Image" src="https://github.com/user-attachments/assets/4c06188a-d33b-476c-bfcf-bcdf0f53fdd4" />

This console output displays the generator selection screen. It clearly outlines the features and provides an example for each of the three available password creation styles before prompting the user for their choice.

<img width="549" height="437" alt="Image" src="https://github.com/user-attachments/assets/818f275c-0f69-47b0-9974-5c1607ec3cf0" />

This sample shows the user selecting and switching between password generators. The menu displays the currently active generator before the user selects option 1, resulting in a successful switch to the Complex Generator.

<img width="545" height="266" alt="Image" src="https://github.com/user-attachments/assets/e0e92fc0-35ca-4962-b94d-ac0315ea7923" />

This output shows the password creation process using the Complex Generator. It captures the user's configuration (length and character counts) for an account named "Alucard," then displays the resulting secure password and a success confirmation.

<img width="431" height="165" alt="Image" src="https://github.com/user-attachments/assets/57d5aa73-e1a1-49e9-96aa-a31f19bfd67f" />

Interactive output where the user selects option 2 from the generator menu, resulting in the system switching from the Complex Generator to the LeetSpeak Generator and displaying a success message.

<img width="551" height="199" alt="Image" src="https://github.com/user-attachments/assets/cd2bb079-a5ef-417d-806e-940db15767e4" />

The console screen showing the user's saved password entries. Each entry lists an account name and its corresponding password, demonstrating the variety of outputs from the different available generators.

<img width="547" height="175" alt="Image" src="https://github.com/user-attachments/assets/ae560605-289c-47f7-aa02-b3dcc0a97a19" />

Console interface for updating a saved password. The user specifies the account "Clint" and provides the new password "OOP_Programming/j@vA," after which the system confirms the entry has been successfully updated.

<img width="548" height="209" alt="Image" src="https://github.com/user-attachments/assets/571bf598-d9df-45f0-917f-2bb97ecb0a92" />

Post-update view of the password entries. The accounts are listed first, then their corresponding passwords in the same order below, demonstrating that Clint's password has been successfully changed to "OOP_Programming/j@vA".

<img width="549" height="156" alt="Image" src="https://github.com/user-attachments/assets/58062f9e-e2a7-449c-b0ac-d13786bba2c3" />


This output shows the password deletion process, where the user removes the saved entry for the account "Miya" and receives a successful deletion confirmation.

<img width="548" height="185" alt="Image" src="https://github.com/user-attachments/assets/4b9c0356-adf5-4669-9d50-c624801ef43b" />

This output displays the password vault after the deletion of the "Miya" entry. It confirms the updated state of the system by listing the three remaining accounts (Clint, Zetian, Alucard) and their corresponding passwords.

<img width="548" height="158" alt="Image" src="https://github.com/user-attachments/assets/4b5d7a65-7404-4b8e-9dab-7e691c4e2665" />

The exit screen of the SecuriVault System password manager, displaying a thank-you message and confirming that stored passwords remain secure.

## ACKNOWLEDGEMENT

We would like to express our deepest gratitude to our professor, Mr. Jayson Abretique, for his patient guidance and unwavering support throughout this project. His expertise, insightful feedback, and continuous encouragement have been crucial in shaping our work and leading it to successful completion. We sincerely appreciate the time and effort he dedicated to mentoring us and helping us overcome challenges along the way.

Our heartfelt thanks also go to our colleagues, whose significant help and collaborative spirit greatly contributed to the success of this project. Their willingness to share knowledge, engage in constructive discussions, and work together as a team made the entire project process valuable. We are grateful for their inspiration and friendship that uplifted our collective progress.

Additionally, we are deeply grateful to our families for their constant emotional support and motivation. Their understanding and unwavering encouragement have provided us with strength and resilience to maintain our focus on achieving our goals. Their presence has been an essential source of comfort throughout this journey.

Finally, words cannot express our gratitude to God for the strength, blessings, and courage throughout this journey.  It is through His grace that we have been sustained at every step, allowing us to overcome the challenges of this project with determination and hope.

## AUTHORS

**Rheywen M. De Guzman | James Aldrei D. De Castro | Hanna Krescha D. Corona | Hanna Mae U. Cumal**

Bachelor of Science in Information Technology - 2103

College of Informatics and Computing Sciences

Batangas State University - The National Engineering University

 
