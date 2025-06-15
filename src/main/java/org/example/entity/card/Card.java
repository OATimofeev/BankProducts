package org.example.entity.card;

import java.math.BigDecimal;

public interface Card {
    void withdraw(BigDecimal amount);

    BigDecimal checkBalance();
}
