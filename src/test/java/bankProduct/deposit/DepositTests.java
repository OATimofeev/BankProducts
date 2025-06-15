package bankProduct.deposit;

import org.example.entity.deposit.Deposit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DepositTests {

    @Test
    @DisplayName("Проверка метода 'deposit' для Deposit")
    void depositDepositTest() {
        Deposit deposit = new Deposit("Депозит", "RUB", BigDecimal.valueOf(1000));
        deposit.deposit(BigDecimal.valueOf(100));

        Assertions.assertEquals(BigDecimal.valueOf(1100).setScale(2, RoundingMode.HALF_UP), deposit.getBalance(), "Баланс неверный!!!");
    }

    @Test
    @DisplayName("Проверка метода 'close' для Deposit")
    void depositCloseTest() {
        Deposit deposit = new Deposit("Депозит", "RUB", BigDecimal.valueOf(1000));
        deposit.close();

        Assertions.assertEquals(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP), deposit.getBalance(), "Баланс неверный!!!");
        Assertions.assertFalse(deposit.isActive(), "Депозит должен быть неактивным!!!");
    }

    @ParameterizedTest(name = "deposit == {0}, isActive == {1}")
    @DisplayName("Проверка обработки исключений для невалидных значений в методе 'deposit' для Deposit")
    @CsvSource(value = {"NULL, true",
            "0.001, true",
            "0, true",
            "-1, true",
            "1, false"}, nullValues = {"NULL"})
    void depositDepositNegativeTest(Double depositAmount, boolean isActive) {
        try {
            Deposit deposit = new Deposit("Депозит", "RUB", BigDecimal.valueOf(1000), isActive);
            deposit.deposit(BigDecimal.valueOf(depositAmount));
        } catch (NullPointerException | IllegalArgumentException exception) {
            return;
        }
        Assertions.fail("Не обработано невалидное значение в конструкторе 'DebitCard'!!!");
    }

    @Test
    @DisplayName("Проверка обработки исключений для невалидных значений в методе 'close' для Deposit")
    void depositCloseNegativeTest() {
        try {
            Deposit deposit = new Deposit("Депозит", "RUB", BigDecimal.valueOf(1000), false);
            deposit.close();
        } catch (NullPointerException | IllegalArgumentException exception) {
            return;
        }
        Assertions.fail("Не обработано невалидное значение в конструкторе 'DebitCard'!!!");
    }
}
