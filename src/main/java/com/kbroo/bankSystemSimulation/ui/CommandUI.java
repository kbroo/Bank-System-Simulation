package com.kbroo.bankSystemSimulation.ui;

import com.kbroo.bankSystemSimulation.services.BankFileManager;
import com.kbroo.bankSystemSimulation.services.BankService;
import com.kbroo.bankSystemSimulation.ui.command.*;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class CommandUI {
    private final Map<Integer, Command> menu;
    private BankService bankService;
    private final BankFileManager bankFileManager;

    public CommandUI() throws IOException {
        this.bankFileManager = new BankFileManager();
        this.bankService = loadOrCreateBank();
        this.menu = Map.ofEntries(
                Map.entry(1, new AddClientCommand()),
                Map.entry(2, new AddAccountCommand()),
                Map.entry(3, new DepositCommand()),
                Map.entry(4, new WithdrawCommand()),
                Map.entry(5, new TransferCommand())
        );
    }

    private BankService loadOrCreateBank() throws IOException {
        try {
            this.bankService = bankFileManager.loadState("bank_data.json");
            return this.bankService;
        } catch (IOException e) {
            return new BankService();
        }
    }

    private void printMenu() {
        System.out.println("============ MENU ============\n" +
                "1. Добавить нового клиента.\n" +
                "2. Открыть новый счет\n" +
                "3. Внести депозит\n" +
                "4. Вывести средства\n" +
                "5. Выполнить перевод средств\n" +
                "==============================");
    }

    private int readChoice(Scanner scanner) {
        System.out.print("Выберите действие: ");
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        } finally {
            scanner.nextLine();
        }
    }

    public void run() {
        System.out.println("Bank System Simulation v1.0 запущен\n");
        Scanner scanner = new Scanner(System.in);
        int choice;
        while (true) {
            printMenu();
            choice = readChoice(scanner);
            Command command = menu.get(choice);
            if (command == null) {
                System.out.println("Некорректное значение.");
                continue;
            }
            command.execute(scanner, bankService);
        }
    }
}
