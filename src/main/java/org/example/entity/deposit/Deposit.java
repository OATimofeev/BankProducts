package org.example.entity.deposit;

import org.example.entity.BankProduct;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

import static org.example.entity.ExceptName.DEPOSIT_PRODUCT_INACTIVE;

public class Deposit extends BankProduct {

    private boolean isActive;

    public Deposit(String name, String currency, BigDecimal balance, boolean isActive) {
        super(name, currency, balance);
        this.isActive = isActive;
    }

    @Override
    public void deposit(BigDecimal amount) {
        Objects.requireNonNull(amount);
        checkAmount(amount);
        checkActive();

        this.balance = this.balance.add(amount);
    }

    public void close() {
        // TODO в реальной реализации потребуется проверка на наличие остатка и обработка этих значений
        isActive = false;
        balance = BigDecimal.ZERO;
    }

    private void checkActive() {
        if (!isActive) {
            throw new IllegalArgumentException(DEPOSIT_PRODUCT_INACTIVE.toString());
        }
    }
}
