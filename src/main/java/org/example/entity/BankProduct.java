package org.example.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

import static org.example.entity.ExceptName.INVALID_AMOUNT;
import static org.example.entity.ExceptName.INVALID_BALANCE;

@Data
public abstract class BankProduct {

    protected String name;
    protected Currency currency;
    protected BigDecimal balance;

    public BankProduct(String name, String currency, BigDecimal balance) {
        this.name = Objects.requireNonNull(name, "Обязательно имя");
        this.currency = Currency.getInstance(Objects.requireNonNull(currency, "Обязательна валюта"));
        this.balance = validateBalance(balance);
    }

    public abstract void deposit(BigDecimal amount);

    protected BigDecimal validateAmount(BigDecimal amount) {
        Objects.requireNonNull(amount);
        amount = amount.setScale(2, RoundingMode.HALF_UP);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(INVALID_AMOUNT.toString());
        }
        return amount;
    }

    protected BigDecimal validateBalance(BigDecimal balance) {
        Objects.requireNonNull(balance, "Обязателен баланс");
        balance = balance.setScale(2, RoundingMode.HALF_UP);
        if (balance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(INVALID_BALANCE.toString());
        }
        return balance;
    }
}
