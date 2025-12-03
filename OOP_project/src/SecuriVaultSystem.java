import abstraction.AbstractPasswordGenerator;
import inheritance.ComplexGenerator;
import inheritance.LeetSpeakGenerator;
import inheritance.PhraseGenerator;
import encapsulation.VaultManager;
import encapsulation.VaultEntry;

import java.util.*;

public class SecuriVaultSystem {

    private VaultManager vaultManager;
    private AbstractPasswordGenerator generator;
    private Scanner scanner;
    private boolean exitFlag;

    public SecuriVaultSystem() {
        vaultManager = new VaultManager();
        generator = null; // No default - user must choose first
        scanner = new Scanner(System.in);
        exitFlag = false;
    }

    public void start() {
        clearScreen();
        displayWelcomeScreen();

        // Force user to select generator first
        clearScreen();
        changeGeneratorFlow();

        // Wait for user to acknowledge
        System.out.println("\n  Press Enter to continue to Main Menu...");
        scanner.nextLine();

        do {
            clearScreen();
            displayMainMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    createEntryFlow(); // Back to Create Entry
                    break;
                case "2":
                    viewContentsFlow(); // Back to View Entries
                    break;
                case "3":
                    updateEntryFlow(); // Back to Update Entry
                    break;
                case "4":
                    deleteEntryFlow(); // Back to Delete Entry
                    break;
                case "5":
                    changeGeneratorFlow(); // Back to Change Generator
                    break;
                case "6":
                    exitFlag = true;
                    break;
                default:
                    System.out.println("\n  \033[1;31mInvalid choice.\033[0m");
                    break;
            }

            if (!exitFlag) {
                System.out.println("\n  Press Enter to continue...");
                scanner.nextLine();
            }
        } while (!exitFlag);

        clearScreen();
        displayExitScreen();
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void displayWelcomeScreen() {
        System.out.println(
                "\033[1;35m=========================================================================================\033[0m");
        System.out.println(
                "\033[1;35m=========================================================================================\033[0m");
        System.out.println();
        System.out.println(
                "\033[1;33m   ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗██╗   ██╗ █████╗ ██╗   ██╗██╗  ████████╗\033[0m");
        System.out.println(
                "\033[1;33m   ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║██║   ██║██╔══██╗██║   ██║██║  ╚══██╔══╝\033[0m");
        System.out.println(
                "\033[1;33m   ███████╗█████╗  ██║     ██║   ██║██████╔╝██║██║   ██║███████║██║   ██║██║     ██║   \033[0m");
        System.out.println(
                "\033[1;33m   ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║╚██╗ ██╔╝██╔══██║██║   ██║██║     ██║   \033[0m");
        System.out.println(
                "\033[1;33m   ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║ ╚████╔╝ ██║  ██║╚██████╔╝███████╗██║   \033[0m");
        System.out.println(
                "\033[1;33m   ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚═╝   \033[0m");
        System.out.println();
        System.out.println(
                "\033[1;33m                            P A S S W O R D   G E N E R A T O R                      \033[0m");
        System.out.println();
        System.out.println(
                "\033[1;35m=========================================================================================\033[0m");
        System.out.println(
                "\033[1;35m=========================================================================================\033[0m");
        System.out.println();
        System.out.print("                                  Press Enter to continue...");
        scanner.nextLine();
    }

    private void displayMainMenu() {

        System.out.println("================================================================================");
        System.out.println();
        System.out.println(
                "\033[1;36m                                   MAIN MENU                                    \033[0m");
        System.out.println();
        System.out.println("================================================================================");
        System.out.println();
        System.out.println("\033[1;33m  [1]\033[0m Create Entry");
        System.out.println();
        System.out.println("\033[1;33m  [2]\033[0m View Entries");
        System.out.println();
        System.out.println("\033[1;33m  [3]\033[0m Update Entry");
        System.out.println();
        System.out.println("\033[1;33m  [4]\033[0m Delete Entry");
        System.out.println();
        System.out.println("\033[1;33m  [5]\033[0m changeGeneratorFlow");
        System.out.println();
        System.out.println("\033[1;33m  [6]\033[0m Exit");
        System.out.println();
        System.out.println("================================================================================");
        System.out.println();
        System.out.println("  \033[1;36mCurrent Generator:\033[0m " + getGeneratorName());
        System.out.println();
        System.out.print("  Enter your choice: ");
    }

    private void createEntryFlow() {
    System.out.println("\n================================================================================");
    System.out.println("\033[1;36m                              ADD PASSWORD ENTRY\033[0m");
    System.out.println("================================================================================");
    System.out.println();
    System.out.println("  \033[1;36mUsing:\033[0m " + getGeneratorName());
    System.out.println();
    System.out.print("  Account Name: ");
    String accountName = scanner.nextLine();

    try {
        if (vaultManager.containsAccount(accountName)) {
            System.out.println("\n  \033[1;31mAccount already exists!\033[0m");
            return;
        }

        int length = getValidated("  Password Length (5-20): ", 5, 20);
        int upper = getValidated("  Uppercase Count: ", 0, length);
        int num = getValidated("  Number Count: ", 0, length - upper);
        int sym = getValidated("  Symbol Count: ", 0, length - upper - num);

        String generated = generator.generate(length, upper, num, sym);
        System.out.println("\n  \033[1;32mGenerated Password: " + generated + "\033[0m");


        if (vaultManager.createEntry(accountName, generated)) {
            System.out.println("  \033[1;32mEntry saved successfully!\033[0m");
        } else {
            System.out.println("  \033[1;31mFailed to save entry. Please try again.\033[0m");
        }
        
    } catch (Exception e) {
        System.out.println("\n  \033[1;31mError: " + e.getMessage() + "\033[0m");
        System.out.println("  Please try again with valid inputs.");
    }
    }

    private void viewContentsFlow() {
        System.out.println("\n================================================================================");
        System.out.println("\033[1;36m                              PASSWORD ENTRIES\033[0m");
        System.out.println("================================================================================");
        System.out.println();

        if (vaultManager.isEmpty()) {
            System.out.println("\n  \033[1;31mNo password entries found.\033[0m");
            return;
        }

        int index = 1;
        for (VaultEntry e : vaultManager.readAllEntries().values()) {
            System.out.printf("  \033[1;33m[%d]\033[0m %s%n", index++, e);
        }
        System.out.println();
        System.out.println("================================================================================");
    }

    private void updateEntryFlow() {
    System.out.println("\n================================================================================");
    System.out.println("\033[1;36m                            UPDATE PASSWORD ENTRY\033[0m");
    System.out.println("================================================================================");
    System.out.println();
    System.out.print("  Account to Update: ");
    String name = scanner.nextLine();

    try {
        if (!vaultManager.containsAccount(name)) {
            System.out.println("\n  \033[1;31mAccount not found.\033[0m");
            return;
        }

        System.out.print("  New Password: ");
        String newPass = scanner.nextLine();

        if (newPass.length() < 5) {
            System.out.println("\n  \033[1;31mPassword must be at least 5 characters.\033[0m");
            return;
        }

        if (vaultManager.updateEntry(name, newPass)) {
            System.out.println("\n  \033[1;32mUpdated successfully!\033[0m");
        } else {
            System.out.println("\n  \033[1;31mUpdate failed!\033[0m");
        }
    } catch (Exception e) {
        System.out.println("\n  \033[1;31mError during update: " + e.getMessage() + "\033[0m");
    }
    }

    private void deleteEntryFlow() {
        System.out.println("\n================================================================================");
        System.out.println("\033[1;36m                            DELETE PASSWORD ENTRY\033[0m");
        System.out.println("================================================================================");
        System.out.println();
        System.out.print("  Account to Delete: ");
        String name = scanner.nextLine();

        if (vaultManager.deleteEntry(name)) {
            System.out.println("\n  \033[1;32mDeleted successfully.\033[0m");
        } else {
            System.out.println("\n  \033[1;31mAccount not found.\033[0m");
        }
    }

    private void changeGeneratorFlow() {
        System.out.println("\n================================================================================");
        if (generator == null) {
            System.out.println("\033[1;36m                    WELCOME! SELECT YOUR PASSWORD GENERATOR\033[0m");
        } else {
            System.out.println("\033[1;36m                         SELECT PASSWORD GENERATOR\033[0m");
        }
        System.out.println("================================================================================");
        System.out.println();
        System.out.println("\033[1;33m  [1]\033[0m Complex Generator");
        System.out.println("      - Full control over all character types");
        System.out.println("      - Balanced random distribution");
        System.out.println("      - Example: aB3$xY9#mK");
        System.out.println();
        System.out.println("\033[1;33m  [2]\033[0m LeetSpeak Generator");
        System.out.println("      - Converts letters to leet speak symbols");
        System.out.println("      - Creative character substitutions");
        System.out.println("      - Example: P@$$w0rd$3cur3!");
        System.out.println();
        System.out.println("\033[1;33m  [3]\033[0m Phrase Generator");
        System.out.println("      - Memorable word-based passphrases");
        System.out.println("      - Easy to remember, still secure");
        System.out.println("      - Example: Dragon7tiger$ocean");
        System.out.println();
        System.out.println("================================================================================");
        System.out.println();
        if (generator != null) {
            System.out.println("  \033[1;36mCurrent:\033[0m " + getGeneratorName());
            System.out.println();
        }
        System.out.print("  Select generator (1-3): ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                generator = new ComplexGenerator();
                System.out.println("\n  \033[1;32mSwitched to Complex Generator!\033[0m");
                break;
            case "2":
                generator = new LeetSpeakGenerator();
                System.out.println("\n  \033[1;32mSwitched to LeetSpeak Generator!\033[0m");
                break;
            case "3":
                generator = new PhraseGenerator();
                System.out.println("\n  \033[1;32mSwitched to Phrase Generator!\033[0m");
                break;
            default:
                System.out.println("\n  \033[1;31mInvalid choice. Please try again.\033[0m");
                if (generator == null) {
                    // If no generator selected yet, force them to choose again
                    System.out.println("  Press Enter to select again...");
                    scanner.nextLine();
                    changeGeneratorFlow();
                }
                break;
        }
    }

    private String getGeneratorName() {
        if (generator instanceof ComplexGenerator) {
            return "\033[1;32mComplex Generator\033[0m";
        } else if (generator instanceof LeetSpeakGenerator) {
            return "\033[1;32mLeetSpeak Generator\033[0m";
        } else if (generator instanceof PhraseGenerator) {
            return "\033[1;32mPhrase Generator\033[0m";
        }
        return "\033[1;32mUnknown Generator\033[0m";
    }

    private void displayExitScreen() {
        System.out.println(
                "\033[1;35m================================================================================\033[0m");
        System.out.println(
                "\033[1;35m================================================================================\033[0m");
        System.out.println();
        System.out.println();
        System.out.println(
                "\033[1;33m                      Thank you for using SecuriVault!                            \033[0m");
        System.out.println();
        System.out.println(
                "\033[1;36m                         Your passwords are secure.                             \033[0m");
        System.out.println();
        System.out.println();
        System.out.println(
                "\033[1;35m================================================================================\033[0m");
        System.out.println(
                "\033[1;35m================================================================================\033[0m");
        System.out.println();
    }

    private int getValidated(String msg, int min, int max) {
        while (true) {
            try {
                System.out.print(msg);
                int val = Integer.parseInt(scanner.nextLine());
                if (val >= min && val <= max)
                    return val;
                System.out.println("  Enter between " + min + " and " + max);
            } catch (Exception e) {
                System.out.println("\n  \033[1;31mInvalid input.\033[0m");
            }
        }
    }

    public static void main(String[] args) {
        new SecuriVaultSystem().start();
    }
}