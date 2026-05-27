package com.kbroo.bankSystemSimulation.ui.command;

import com.kbroo.bankSystemSimulation.entity.TransactionStatus;
import com.kbroo.bankSystemSimulation.services.BankService;

import java.math.BigDecimal;
import java.util.Scanner;

public class TransferCommand implements Command {
    @Override
    public void execute(Scanner scanner, BankService bankService) {
        System.out.print("Укажите номер счета с которого хотите перевести средства: ");
        String fromAccount = scanner.nextLine();
        System.out.print("Укажите номер счета на который хотите перевести средства:");
        String toAccount = scanner.nextLine();
        if (bankService.getAccounts().get(fromAccount) == null || bankService.getAccounts().get(toAccount) == null) {
            System.out.println("Один или оба счета не найдены.");
            return;
        }
        System.out.print("Укажите количество средств для перевода: ");
        BigDecimal amount = scanner.nextBigDecimal();
        scanner.nextLine();
        System.out.println("Добавьте комментарий к переводу:");
        String description = scanner.nextLine();
        TransactionStatus status = bankService.transfer(fromAccount, toAccount, amount, description);
    }
}
