package com.kbroo.bankSystemSimulation.ui.command;

import com.kbroo.bankSystemSimulation.services.BankFileManager;
import com.kbroo.bankSystemSimulation.services.BankService;

import java.io.IOException;
import java.util.Scanner;

public class SaveAndExitCommand implements Command{
    @Override
    public void execute(Scanner scanner, BankService bankService) throws IOException {
        BankFileManager bankFileManager = new BankFileManager();
        try {
            bankFileManager.saveState(bankService, "bank_data.json");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
