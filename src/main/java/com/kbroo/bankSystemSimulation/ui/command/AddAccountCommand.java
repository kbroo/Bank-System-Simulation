package com.kbroo.bankSystemSimulation.ui.command;

import com.kbroo.bankSystemSimulation.entity.Account;
import com.kbroo.bankSystemSimulation.entity.AccountType;
import com.kbroo.bankSystemSimulation.entity.Client;
import com.kbroo.bankSystemSimulation.entity.DebitAccount;
import com.kbroo.bankSystemSimulation.services.BankService;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class AddAccountCommand implements Command{
    @Override
    public void execute(Scanner scanner, BankService bankService) {
        System.out.print("Укажите id пользователя: ");
        String userid = scanner.nextLine();
        Client client = bankService.getClients().get(userid);
        if (client == null) {
            System.out.println("Пользователь не найден.");
            return;
        }
        System.out.println("Доступные типы счета:\n" +
                "1. Дебетовый\n" +
                "2. Кредитный\n" +
                "3. Сберегательный");
        System.out.print("Выберите тип счета:");
        Account account = null;
        account = switch(scanner.nextInt()) {
            case 1 -> bankService.openAccount(userid, AccountType.DEBIT);
            case 2 -> bankService.openAccount(userid, AccountType.CREDIT);
            case 3 -> bankService.openAccount(userid, AccountType.SAVINGS);
            default -> {
                System.out.println("Некорректное значение.");
                yield null;
            }
        };
        scanner.nextLine();
        if (account != null) {
            System.out.println("Счет " + account.getAccountNumber() + " успешно открыт.");
        }
    }
}
