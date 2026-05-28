package com.kbroo.bankSystemSimulation.ui.command;

import com.kbroo.bankSystemSimulation.entity.Client;
import com.kbroo.bankSystemSimulation.services.BankService;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class PrintAllClientsCommand implements Command{
    @Override
    public void execute(Scanner scanner, BankService bankService) throws IOException {
        Map<String, Client> clients = bankService.getClients();
        System.out.println("Список клиентов:");
        System.out.println("-------------------------");
        boolean hasClients = false;
        for (Client client : clients.values()) {
            hasClients = true;
            System.out.println("Клиент: " + client.getUsername());
            System.out.println("UUID: " + client.getId());
            System.out.println("-------------------------");
        }
        if (!hasClients) {
            System.out.println("Список пуст.");
            System.out.println("-------------------------");
        }
    }
}
