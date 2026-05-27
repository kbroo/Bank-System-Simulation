package com.kbroo.bankSystemSimulation;

import com.kbroo.bankSystemSimulation.ui.CommandUI;

import java.io.IOException;

public class Main {
    static void main(String[] args) throws IOException {
        CommandUI commandUI = new CommandUI();
        commandUI.run();
    }
}
