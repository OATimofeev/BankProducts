package org.example.entity.card;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

import static org.example.entity.ExceptName.INVALID_EXCHANGE;

public class CurrencyDebitCard extends DebitCard {
    private final BigDecimal exchangeRate;

    public CurrencyDebitCard(String name, String currency, BigDecimal balance, BigDecimal exchangeRate) {
        super(name, currency, balance);
        this.exchangeRate = validateExchangeRate(exchangeRate);
    }

    public BigDecimal convertToMainCurrency() {
        return balance.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);
    }

    private static BigDecimal validateExchangeRate(BigDecimal rate) {
        Objects.requireNonNull(rate, "Курс обмена не может быть null");
        rate = rate.setScale(2, RoundingMode.HALF_UP);
        if (rate.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(INVALID_EXCHANGE.toString());
        }
        return rate;
    }
}
