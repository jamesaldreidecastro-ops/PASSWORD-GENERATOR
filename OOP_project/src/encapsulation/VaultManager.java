package encapsulation;

import polymorphism.PasswordException; 
import java.util.*;

public class VaultManager {

    private Map<String, VaultEntry> vault;

    public VaultManager() {
        vault = new HashMap<>();
    }

    public boolean createEntry(String accountName, String password) {
    try {
        if (vault.containsKey(accountName)) return false;
        vault.put(accountName, new VaultEntry(accountName, password));
        return true;
    } catch (PasswordException e) {
        System.err.println("Error creating entry: " + e.getMessage());
        return false;
    }
    }

    public VaultEntry readEntry(String accountName) {
        return vault.get(accountName);
    }

    public Map<String, VaultEntry> readAllEntries() {
        return new HashMap<>(vault);
    }

    public boolean updateEntry(String accountName, String newPassword) {
    if (!vault.containsKey(accountName)) return false;
    try {
        vault.get(accountName).setPassword(newPassword);
        return true;
    } catch (PasswordException e) {
        System.err.println("Error updating password: " + e.getMessage());
        return false;
    }
    }

    public boolean deleteEntry(String accountName) {
        return vault.remove(accountName) != null;
    }

    public boolean isEmpty() {
        return vault.isEmpty();
    }

    public int size() {
        return vault.size();
    }

    public boolean containsAccount(String accountName) {
        return vault.containsKey(accountName);
    }

    public void clearVault() {
        vault.clear();
    }
}