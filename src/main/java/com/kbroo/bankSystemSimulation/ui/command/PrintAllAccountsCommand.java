package com.kbroo.bankSystemSimulation.ui.command;

import com.kbroo.bankSystemSimulation.entity.Account;
import com.kbroo.bankSystemSimulation.services.BankService;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class PrintAllAccountsCommand implements Command{
    @Override
    public void execute(Scanner scanner, BankService bankService) throws IOException {
        Map<String, Account> accounts = bankService.getAccounts();
        System.out.println("Список счетов:");
        System.out.println("-------------------------");
        boolean hasAccounts = false;
        for (Account account : accounts.values()) {
            hasAccounts = true;
            System.out.println("Клиент: " + account.getOwner());
            System.out.println("Номер счета: " + account.getAccountNumber());
            System.out.println("Тип счета: " + account.getAccountType());
            System.out.println("-------------------------");
        }
        if (!hasAccounts) {
            System.out.println("Список пуст.");
            System.out.println("-------------------------");
        }
    }
}
