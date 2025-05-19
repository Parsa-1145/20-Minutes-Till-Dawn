package model;

import java.util.ArrayList;

public class AccountManager {
    private final ArrayList<Account> accounts = new ArrayList<>();
    private Account currentAccount = null;

    public AccountManager() {
        accounts.add(new Account("parsa", "asd"));
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    public void addAccount(Account account){
        this.accounts.add(account);
    }

    public Account getAccountByUsername(String username){
        for(Account a : accounts){
            if(a.getUsername().equals(username)){
                return a;
            }
        }
        return null;
    }
}
