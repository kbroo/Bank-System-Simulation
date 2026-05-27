package com.kbroo.bankSystemSimulation.ui.command;

import com.kbroo.bankSystemSimulation.entity.Account;
import com.kbroo.bankSystemSimulation.exception.InsufficientAccountException;
import com.kbroo.bankSystemSimulation.services.BankService;

import java.math.BigDecimal;
import java.util.Scanner;

public class WithdrawCommand implements Command{
    @Override
    public void execute(Scanner scanner, BankService bankService) {
        System.out.print("Укажите номер счета: ");
        String number = scanner.nextLine();
        Account account = bankService.getAccounts().get(number);
        if (account == null) {
            System.out.println("Счет не найден.");
            return;
        }
        System.out.println("Укажите сумму для вывода: ");
        BigDecimal amount = scanner.nextBigDecimal();
        scanner.nextLine();
        try {
            account.withdraw(amount);
        } catch (InsufficientAccountException e) {
            System.out.println(e.getMessage());
        }
    }
}
