package org.example.entity.card;

import java.math.BigDecimal;
import java.util.Currency;

public class DebitCard extends CardProduct {
    protected DebitCard(String name, String currency, BigDecimal balance) {
        super(name, currency, balance);
    }

    public DebitCard(String name, BigDecimal balance) {
        super(name, "RUB", balance);
    }
}
