package org.example.entity.card;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static org.example.entity.ExceptName.INVALID_INTEREST_RATE;

@Getter
public class CreditCard extends CardProduct {
    private final BigDecimal interestRate;
    private BigDecimal debt;

    public CreditCard(String name, String currency, BigDecimal balance, BigDecimal interestRate, BigDecimal debt) {
        super(name, currency, balance);
        this.interestRate = interestRate;
        this.debt = debt;
    }

    public void deposit(BigDecimal amount) {
        checkAmount(amount);
        // Если долг больше депозита - уменьшаем только долг, иначе - долг = 0, а разница - в балансе
        if (amount.compareTo(debt) <= 0) {
            debt = debt.subtract(amount);
        } else {
            balance = balance.add(amount.subtract(debt));
            debt = BigDecimal.ZERO;
        }
    }

    @Override
    public void withdraw(BigDecimal amount) {
        checkAmount(amount);

        balance = balance.subtract(amount);
        // Если баланс - отрицательный, то balance = 0, а разница уходит в задолженность
        // возможна доработка, при добавлении значения кредитного лимита (нет в условиях ТЗ)
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            debt = debt.subtract(balance);
            balance = BigDecimal.ZERO;
        }
    }

    private static BigDecimal validateInterestRate(BigDecimal rate) {
        Objects.requireNonNull(rate, "Ставка не может быть null");
        if (rate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(INVALID_INTEREST_RATE.toString());
        }
        return rate.setScale(2, RoundingMode.HALF_UP);
    }
}