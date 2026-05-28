package com.kbroo.bankSystemSimulation.entity;

import com.kbroo.bankSystemSimulation.exception.AccountBlockedException;
import com.kbroo.bankSystemSimulation.exception.InsufficientAccountException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SavingsAccount extends Account {
    private BigDecimal interestRate;

    public SavingsAccount() {}

    public SavingsAccount(String accountNumber, Client owner) {
        super(accountNumber, owner, AccountType.SAVINGS);
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
        LocalDate allowedWithdrawDate = this.openedIn.plusDays(30);
        if (today.isBefore(allowedWithdrawDate)) {
            throw new AccountBlockedException("Снятие средств доступно не ранее 30 дней после открытия счета.");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Отрицательная или равная нулю сумма вывода средств.");
            return;
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new InsufficientAccountException("Недостаточно средств для вывода.");
        }
        this.balance = this.balance.subtract(amount);
        System.out.println("Успешно снято: " + amount + "$");
    }
}
