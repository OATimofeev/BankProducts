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

    @ParameterizedTest(name = "balance == {0}, exchangeRate == {1}, expected == {2}")
    @DisplayName("Проверка метода 'convertToMainCurrency' для CurrencyDebitCard")
    @CsvSource({
            "10, 76.22, 762.2",
            "1000, 1, 1000",
    })
    void currencyDebitCardDepositTest(Double balance, Double exchangeRate, Double expectedBalance) {
        CurrencyDebitCard card = new CurrencyDebitCard("Дебетовая", "USD", BigDecimal.valueOf(balance), BigDecimal.valueOf(exchangeRate));

        Assertions.assertEquals(BigDecimal.valueOf(expectedBalance).setScale(2, RoundingMode.HALF_UP), card.convertToMainCurrency(), "Сумма обмена неверная!!!");
    }

    @ParameterizedTest(name = "currency == {0}, exchangeRate == {1}")
    @DisplayName("Проверка обработки исключений для невалидных значений в конструкторе 'CurrencyDebitCard'")
    @CsvSource(value = {
            "NULL,  1.2",
            "EUR,  NULL",
            "ГГГ,  200",
            "USD,  0.001",
            "RUB,  0",
            "EUR,  -1",
    }, nullValues = {"NULL"})
    void currencyDebitCardNegativeTest(String Currency, Double exchangeRate) {
        try {
            new CurrencyDebitCard("Валютная", Currency, BigDecimal.valueOf(1000), BigDecimal.valueOf(exchangeRate));
        } catch (NullPointerException | IllegalArgumentException exception) {
            return;
        }
        Assertions.fail("Не обработано невалидное значение в конструкторе 'CurrencyDebitCard'!!!");
    }
}