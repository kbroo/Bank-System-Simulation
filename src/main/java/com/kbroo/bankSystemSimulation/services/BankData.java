package com.kbroo.bankSystemSimulation.services;

import com.kbroo.bankSystemSimulation.entity.Account;
import com.kbroo.bankSystemSimulation.entity.Client;
import com.kbroo.bankSystemSimulation.entity.Transaction;

import java.util.List;
import java.util.Map;

public class BankData {
    private Map<String, Client> clients;
    private Map<String, Account> accounts;
    private Map<String, List<Transaction>> transactions;
    private int counterAccounts;

    public BankData() {}

    public BankData(Map<String, Client> clients,  Map<String, Account> accounts, Map<String, List<Transaction>> transactions, int counterAccounts) {
        this.clients = clients;
        this.accounts = accounts;
        this.transactions = transactions;
        this.counterAccounts = counterAccounts;
    }

    public Map<String, Client> getClients() {
        return this.clients;
    }
    public Map<String, Account> getAccounts() {
        return accounts;
    }
    public Map<String, List<Transaction>> getTransactions() {
        return transactions;
    }
    public int getCounterAccounts() {
        return counterAccounts;
    }

    public void setAccounts(Map<String, Account> accounts) {
        this.accounts = accounts;
    }
    public void setClients(Map<String, Client> clients) {
        this.clients = clients;
    }
    public void setTransactions(Map<String, List<Transaction>> transactions) {
        this.transactions = transactions;
    }
    public void setCounterAccounts(int counterAccounts) {
        this.counterAccounts = counterAccounts;
    }
}
