package bankProduct.card;

import org.example.entity.card.DebitCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

public class DebitCardTests {

    @ParameterizedTest(name = "deposit == {0}")
    @DisplayName("Проверка метода 'deposit' для DebitCard")
    @CsvSource(value = {
            "1000.22, 2000.22",
            "1.0, 1001.0"})
    void debitCardDepositTest(Double deposit, Double expectedBalance) {
        DebitCard card = new DebitCard("Дебетовая", BigDecimal.valueOf(1000));
        card.deposit(BigDecimal.valueOf(deposit));

        Assertions.assertEquals(BigDecimal.valueOf(expectedBalance).setScale(2, RoundingMode.HALF_UP), card.getBalance(), "Баланс неверный!!!");
    }

    @ParameterizedTest(name = "withdraw == {0}")
    @DisplayName("Проверка метода 'withdraw' для DebitCard")
    @CsvSource(value = {
            "1.22, 99.0",
            "10.0, 90.22"})
    void debitCardWithdrawTest(Double deposit, Double expectedBalance) {
        DebitCard card = new DebitCard("Дебетовая", BigDecimal.valueOf(100.22));
        card.withdraw(BigDecimal.valueOf(deposit));

        Assertions.assertEquals(BigDecimal.valueOf(expectedBalance).setScale(2, RoundingMode.HALF_UP), card.getBalance(), "Баланс неверный!!!");
    }

    @Test
    @DisplayName("Проверка метода 'checkBalance' для DebitCard")
    void debitCardCheckBalanceTest() {
        DebitCard card = new DebitCard("Дебетовая", BigDecimal.valueOf(100));

        Assertions.assertEquals(BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP), card.checkBalance());
    }

    @ParameterizedTest(name = "deposit == {0}")
    @DisplayName("Проверка обработки исключений для невалидных значений в методе 'deposit' для DebitCard")
    @MethodSource("debitCardWithdrawDepositNegativeTestsData")
    void debitCardDepositNegativeTest(Double deposit) {
        try {
            DebitCard card = new DebitCard("Дебетовая", BigDecimal.valueOf(1000));
            card.deposit(BigDecimal.valueOf(deposit));
        } catch (NullPointerException | IllegalArgumentException exception) {
            return;
        }
        Assertions.fail("Не обработано невалидное значение в методе 'withdraw'!!!");
    }

    @ParameterizedTest(name = "withdraw == {0}")
    @DisplayName("Проверка обработки исключений для невалидных значений в методе 'withdraw' для DebitCard")
    @MethodSource("debitCardWithdrawDepositNegativeTestsData")
    void debitCardWithdrawNegativeTest(Double withdraw) {
        try {
            DebitCard card = new DebitCard("Дебетовая", BigDecimal.valueOf(1000));
            card.withdraw(BigDecimal.valueOf(withdraw));
        } catch (NullPointerException | IllegalArgumentException exception) {
            return;
        }
        Assertions.fail("Не обработано невалидное значение в методе 'withdraw'!!!");
    }

    private static Stream<Arguments> debitCardWithdrawDepositNegativeTestsData() {
        return Stream.of(
                Arguments.of((Double) null),
                Arguments.of(-1.2),
                Arguments.of(0.0),
                Arguments.of(0.001)
        );
    }

    @ParameterizedTest(name = "name == {0}, balance == {1}")
    @DisplayName("Проверка обработки исключений для невалидных значений в конструкторе 'DebitCard'")
    @CsvSource(value = {
            "NULL, 1000",
            "ДЕБИТ, NULL",
            "ДЕБИТ, 0.0",
            "NULL, -1.01",
            "ДЕБИТ, 0.001"},
            nullValues = {"NULL"})
    void debitCardNegativeTest(String name, Double balance) {
        try {
            new DebitCard(name, BigDecimal.valueOf(balance));
        } catch (NullPointerException | IllegalArgumentException exception) {
            return;
        }
        Assertions.fail("Не обработано невалидное значение в конструкторе 'DebitCard'!!!");
    }
}
