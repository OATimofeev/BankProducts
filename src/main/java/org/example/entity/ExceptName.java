package org.example.entity;

public enum ExceptName {
    INVALID_AMOUNT("Сумма пополнения/списания должна быть больше 0"),
    DEPOSIT_PRODUCT_INACTIVE("Вклад неактивен, операции запрещены"),
    WITHDRAW_UNAVAILABLE("Сумма списания больше доступного баланса"),
    INVALID_BALANCE("Баланс должен быть больше 0"),
    INVALID_EXCHANGE("Сумма списания больше доступного баланса"),
    INVALID_INTEREST_RATE("Ставка должна быть больше 0"),
    INVALID_DEBT("Задолженность должна быть не менее 0");

    private final String exceptionText;
    ExceptName(String exceptionText) {
        this.exceptionText = exceptionText;
    }

    @Override
    public String toString() {
        return exceptionText;
    }
}
