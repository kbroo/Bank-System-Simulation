package com.kbroo.bankSystemSimulation.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SavingsAccount extends Account {
    private BigDecimal interestRate;

    public SavingsAccount(String accountNumber, Client owner, AccountType accountType) {
        super(accountNumber, owner, accountType);
        this.interestRate = this.accountType.getInterestRate();
    }

    public BigDecimal getInterestRate() {
        return this.interestRate;
    }

    @Override
    public BigDecimal calculateMonthlyFee() {
        return BigDecimal.valueOf(0);
    }

    @Override
    public void withdraw(BigDecimal amount) {
        LocalDate today = LocalDate.now();
        LocalDate allowedWithdrawDate = this.openedAt.plusDays(30);
        if (today.isBefore(allowedWithdrawDate)) {
            System.out.println("Снятие средств доступно не ранее 30 дней после открытия счета.");
            return;
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Отрицательная или равная нулю сумма вывода средств.");
            return;
        }
        if (this.balance.compareTo(amount) < 0) {
            System.out.println("Недостаточно средств для вывода.");
            return;
        }
        this.balance = this.balance.subtract(amount);
        System.out.println("Успешно снято: " + amount + "$");
    }
}
