package org.example.entity.card;

import org.example.entity.BankProduct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import static org.example.entity.ExceptName.WITHDRAW_UNAVAILABLE;


public abstract class CardProduct extends BankProduct implements Card {

    public CardProduct(String name, String currency, BigDecimal balance) {
        super(name, currency, balance.setScale(2, RoundingMode.HALF_UP));
    }

    @Override
    public void deposit(BigDecimal amount) {
        amount = checkAmount(amount);
        this.balance = this.balance.add(amount);
    }

    @Override
    public void withdraw(BigDecimal amount) {
        amount = checkAmount(amount);
        checkWithdrawAvailable(amount);

        balance = balance.subtract(amount);
    }

    @Override
    public BigDecimal checkBalance() {
        return balance;
    }

    private void checkWithdrawAvailable(BigDecimal amount) {
        if (amount.compareTo(balance) > 0) {
            throw new IllegalArgumentException(WITHDRAW_UNAVAILABLE.toString());
        }
    }
}
