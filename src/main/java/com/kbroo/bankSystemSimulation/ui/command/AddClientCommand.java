package com.kbroo.bankSystemSimulation.ui.command;

import com.kbroo.bankSystemSimulation.entity.Client;
import com.kbroo.bankSystemSimulation.services.BankService;

import java.util.Scanner;

public class AddClientCommand implements Command {
    @Override
    public void execute(Scanner scanner, BankService bankService) {
        System.out.print("Введите ваше имя: ");
        String username = scanner.nextLine();
        System.out.print("Введите ваш email: ");
        String email = scanner.nextLine();
        Client client = bankService.addClient(username, email);
        System.out.println("Пользователь " + client.getUsername() + " успешно добавлен.\n" +
                "Почта: " + client.getEmail() +
                "\nUUID: " + client.getId());
    }
}
