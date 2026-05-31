package com.kbroo.bankSystemSimulation.services;

import com.kbroo.bankSystemSimulation.entity.Account;
import com.kbroo.bankSystemSimulation.entity.AccountType;
import com.kbroo.bankSystemSimulation.entity.SavingsAccount;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InterestEmitter {
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void start(BankService bankService) {
        scheduler.scheduleAtFixedRate(
                () -> {
                    for (Account account : bankService.getAccounts().values()) {
                        if (account instanceof SavingsAccount savings) {
                            BigDecimal amount = savings.getInterestRate().multiply(savings.getBalance());
                            savings.deposit(amount);
                        }
                    }
                },
                0,
                10,
                TimeUnit.SECONDS
        );
    }

    public void stop() {
        scheduler.shutdown();
    }
}
