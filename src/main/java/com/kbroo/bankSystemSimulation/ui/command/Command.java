package com.kbroo.bankSystemSimulation.ui.command;

import com.kbroo.bankSystemSimulation.services.BankService;

import java.util.Scanner;

public interface Command {
    void execute(Scanner scanner, BankService bankService);
}
