package com.kbroo.bankSystemSimulation.ui.command;

import com.kbroo.bankSystemSimulation.services.BankService;

import java.io.IOException;
import java.util.Scanner;

public interface Command {
    void execute(Scanner scanner, BankService bankService) throws IOException;
}
