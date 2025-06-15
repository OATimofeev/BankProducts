package bankProduct.card;

import org.example.entity.card.CreditCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CreditCardTests {

    @ParameterizedTest(name = "actualDebt == {0}, deposit == {1}, expectedBalance == {2}, expectedDebt == {3}")
    @DisplayName("Проверка метода 'deposit' для CreditCard")
    @CsvSource(value = {
            "100, 1000, 1900, 0",
            "0, 1000.22, 2000.22, 0",
            "1000, 999.999, 1000, 0",
            "1000, 999.99, 1000, 0.01"})
    void creditCardDepositTest(Double actualDebt, Double deposit, Double expectedBalance, Double expectedDebt) {
        CreditCard card = new CreditCard("Кредитная", BigDecimal.valueOf(1000), BigDecimal.ONE, BigDecimal.valueOf(actualDebt));
        card.deposit(BigDecimal.valueOf(deposit));

        Assertions.assertEquals(BigDecimal.valueOf(expectedBalance).setScale(2, RoundingMode.HALF_UP), card.getBalance(), "Баланс неверный!!!");
        Assertions.assertEquals(BigDecimal.valueOf(expectedDebt).setScale(2, RoundingMode.HALF_UP), card.getDebt(), "Задолженность неверная!!!");
    }

    @ParameterizedTest(name = "actualBalance == {0}, actualDebt == {1}, withdraw == {2}, expectedBalance == {3}, expectedDebt == {4}")
    @DisplayName("Проверка метода 'withdraw' для CreditCard")
    @CsvSource(value = {
            "1000, 0, 2000, 0, 1000",
            "100, 100, 1000.01, 0, 1000.01",
            "1, 0, 100, 0, 99",
            "200, 0, 100, 100, 0"})
    void creditCardWithdrawTest(Double actualBalance, Double actualDebt, Double deposit, Double expectedBalance, Double expectedDebt) {
        CreditCard card = new CreditCard("Кредитная", BigDecimal.valueOf(actualBalance), BigDecimal.ONE, BigDecimal.valueOf(actualDebt));
        card.withdraw(BigDecimal.valueOf(deposit));

        Assertions.assertEquals(BigDecimal.valueOf(expectedBalance).setScale(2, RoundingMode.HALF_UP), card.getBalance(), "Баланс неверный!!!");
        Assertions.assertEquals(BigDecimal.valueOf(expectedDebt).setScale(2, RoundingMode.HALF_UP), card.getDebt(), "Задолженность неверная!!!");
    }

    @ParameterizedTest(name = "debt == {0}, interestRate == {1}")
    @DisplayName("Проверка обработки исключений для невалидных значений в конструкторе 'CreditCard'")
    @CsvSource(value = {
            "1000, NULL",
            "NULL, 1",
            "1, -1",
            "-0.01, 1",
            "0, 0.001"},
            nullValues = {"NULL"})
    void creditCardNegativeTest(Double interestRate, Double debt) {
        try {
            new CreditCard("Кредитная", BigDecimal.valueOf(10), BigDecimal.valueOf(interestRate), BigDecimal.valueOf(debt));
        } catch (NullPointerException | IllegalArgumentException exception) {
            return;
        }
        Assertions.fail("Не обработано невалидное значение в конструкторе 'CreditCard'!!!");
    }
}
