package com.kbroo.bankSystemSimulation.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public class BankFileManager {
    private final ObjectMapper mapper;

    public BankFileManager() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    public void saveState(BankService bankService, String filename) throws IOException {
        BankData bankData = new BankData(
                bankService.getClients(),
                bankService.getAccounts(),
                bankService.getTransactions(),
                bankService.getCounterAccounts()
        );

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(filename), bankData);
    }

    public BankService loadState(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            return new BankService();
        }
        BankData bankData = mapper.readValue(file, BankData.class);
        BankService bankService = new BankService();
        bankService.restoreFromData(bankData);
        return bankService;
    }
}
