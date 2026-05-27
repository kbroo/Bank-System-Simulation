package com.kbroo.bankSystemSimulation.services;

import com.kbroo.bankSystemSimulation.entity.*;
import com.kbroo.bankSystemSimulation.exception.AccountBlockedException;
import com.kbroo.bankSystemSimulation.exception.InsufficientAccountException;
import com.kbroo.bankSystemSimulation.util.AccountNumberGenerator;

import java.math.BigDecimal;
import java.util.*;

public class BankService {
    private Map<String, Client> clients;
    private Map<String, Account> accounts;
    private Map<String, List<Transaction>> transactions;
    private final AccountNumberGenerator accountNumberGenerator = new AccountNumberGenerator(0);
    private static int counterAccounts;

    public Client createClient(String username, String email) {
        Client newClient = new Client(username, email);
        if (clients.containsValue(newClient)) {
            System.out.println("Пользователь уже существует.");
            return null;
        }
        clients.put(newClient.getId().toString(), newClient);
        return newClient;
    }

    public Account openAccount(String clientID, AccountType accountType) {
        Client client = clients.get(clientID);
        if (client == null) {
            System.out.println("Ошибка: пользователь не найден.");
            return null;
        }
        String accountNumber = accountNumberGenerator.generate(accountType);
        Account newAccount = switch (accountType) {
            case DEBIT -> new DebitAccount(accountNumber, client);
            case CREDIT -> new CreditAccount(accountNumber, client);
            case SAVINGS -> new SavingsAccount(accountNumber, client);
        };
        accounts.put(clientID, newAccount);
        return newAccount;
    }

    public TransactionStatus transfer(String from, String to, BigDecimal amount, String description) {
        Account fromAccount = accounts.get(from);
        Account toAccount = accounts.get(to);
        if (fromAccount == null || toAccount == null) {
            System.out.println("Ошибка: один или оба аккаунты не найдены.");
             return TransactionStatus.ACCOUNT_NOT_FOUND;
        }
        try {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
        } catch (AccountBlockedException e) {
            System.out.println(e.getMessage());
            return TransactionStatus.ACCOUNT_BLOCKED;
        } catch (InsufficientAccountException e) {
            System.out.println(e.getMessage());
            return TransactionStatus.INSUFFICIENT_FUNDS;
        }
        Transaction transaction = new Transaction(from, to, amount, description);
        transactions.putIfAbsent(from, new ArrayList<>());
        transactions.putIfAbsent(to, new ArrayList<>());
        transactions.get(from).add(transaction);
        transactions.get(to).add(transaction);
        return TransactionStatus.SUCCESS;
    }

    public List<Transaction> getStatement(String accountNumber) {
        if (accounts.get(accountNumber) == null) {
            System.out.println("Счет не найден.");
            return Collections.emptyList();
        }
        return transactions.getOrDefault(accountNumber, Collections.emptyList());
    }

    public BigDecimal getTotalBankFunds() {
        return accounts.values().stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
