package encapsulation;

import polymorphism.PasswordException;

public class VaultEntry {

    private String accountName;
    private String password;

    public VaultEntry(String accountName, String password) throws PasswordException {
        setAccountName(accountName);
        setPassword(password);
    }   

    public String getAccountName() {
        return accountName;
    }

    public String getPassword() {
        return password;
    }

    //throws PasswordException
    public void setAccountName(String accountName) throws PasswordException {
        if (accountName == null || accountName.trim().isEmpty()) {
            throw new PasswordException("Account name cannot be empty");
        }
        this.accountName = accountName;
    }

    
    public void setPassword(String password) throws PasswordException {
        if (password == null || password.length() < 5) {
            throw new PasswordException("Password must be at least 5 characters");
        }
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("Account: %-20s | Password: %s", accountName, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof VaultEntry)) return false;
        VaultEntry v = (VaultEntry) obj;
        return accountName.equals(v.accountName);
    }

    @Override
    public int hashCode() {
        return accountName.hashCode();
    }
}