package bankProduct.card;

import org.example.entity.card.CurrencyDebitCard;
import org.example.entity.card.DebitCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyDebitCardTests {

    @Test
    @DisplayName("Проверка метода 'deposit' для CurrencyDebitCard")
    void currencyDebitCardDepositTest() {
        CurrencyDebitCard card = new CurrencyDebitCard("Дебетовая валютная", "USD", BigDecimal.valueOf(1000), BigDecimal.valueOf(1));
        card.deposit(BigDecimal.valueOf(100));

        Assertions.assertEquals(BigDecimal.valueOf(1100).setScale(2, RoundingMode.HALF_UP), card.getBalance(), "Баланс неверный!!!");
    }

    @Test
    @DisplayName("Проверка метода 'withdraw' для CurrencyDebitCard")
    void currencyDebitCardWithdrawTest() {
        CurrencyDebitCard card = new CurrencyDebitCard("Дебетовая валютная", "USD", BigDecimal.valueOf(1000), BigDecimal.valueOf(1));
        card.withdraw(BigDecimal.valueOf(100));

        Assertions.assertEquals(BigDecimal.valueOf(900).setScale(2, RoundingMode.HALF_UP), card.getBalance(), "Баланс неверный!!!");
    }

    @Test
    @DisplayName("Проверка метода 'checkBalance' для CurrencyDebitCard")
    void debitCardCheckBalanceTest() {
        CurrencyDebitCard card = new CurrencyDebitCard("Дебетовая валютная", "USD", BigDecimal.valueOf(100), BigDecimal.valueOf(1));
        Assertions.assertEquals(BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP), card.checkBalance());
    }

    @ParameterizedTest(name = "balance == {0}, exchangeRate == {1}, expected == {2}")
    @DisplayName("Проверка метода 'convertToMainCurrency' для CurrencyDebitCard")
    @CsvSource({
            "10, 76.22, 762.2",
            "1000, 1, 1000",
    })
    void debitCardDepositTest(Double balance, Double exchangeRate, Double expectedBalance) {
        CurrencyDebitCard card = new CurrencyDebitCard("Дебетовая", "USD", BigDecimal.valueOf(balance), BigDecimal.valueOf(exchangeRate));

        Assertions.assertEquals(BigDecimal.valueOf(expectedBalance).setScale(2, RoundingMode.HALF_UP), card.convertToMainCurrency(), "Сумма обмена неверная!!!");
    }

    @ParameterizedTest(name = "name == {0},currency == {1}, balance == {2}, exchangeRate == {3}")
    @DisplayName("Проверка обработки исключений для невалидных значений в конструкторе 'CurrencyDebitCard'")
    @CsvSource(value = {
            "NULL,RUB, 1000, 1",
            "ДЕБИТ, NULL, 100, 1.2",
            "ДЕБИТ, EUR, NULL, 200",
            "ДЕБИТ, EUR, 10, NULL",
            "ДЕБИТ, ГГГ, 10, 200",
            "ДЕБИТ, USD, 0.001, 2",
            "ДЕБИТ, USD, 20, 0.001",
            "ДЕБИТ, RUB, 20, 0",
            "ДЕБИТ, EUR, 20, -1",
    },
            nullValues = {"NULL"})
    void debitCardNegativeTest(String name, String Currency, Double balance, Double exchangeRate) {
        try {
            new CurrencyDebitCard(name, Currency, BigDecimal.valueOf(balance), BigDecimal.valueOf(exchangeRate));
        } catch (NullPointerException | IllegalArgumentException exception) {
            return;
        }
        Assertions.fail("Не обработано невалидное значение в конструкторе 'CurrencyDebitCard'!!!");
    }
}