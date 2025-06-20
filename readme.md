Проект представляет реализацию иерархии банковских продуктов (карты и вклады) согласно тестовому заданию на
должность специалиста по автоматизированному тестированию.

## Задание

Вам необходимо выстроить и написать архитектуру для банковских продуктов. Банковскими продуктами являются – кредитная
карта, дебетовая карта, валютная дебетовая карта и вклад.

- Карты и вклад имеют параметры: валюта, баланс и название- Карты содержат методы: пополнение, списание, запрос баланса
- Кредитная карта имеет дополнительный метод запрос задолженности и дополнительный параметр процентная ставка
- Вклад содержит методы: пополнение, запрос баланса, закрытие

Опишите вашу структуру/архитектуру в классах. Учтите, что продуктов в будущем станет больше - то есть предусмотрите
возможность изменения вашей архитектуры без различных проблем и лишних правок. Напишите unit тесты для проверки
реализованной архитектуры.

## Структура

Расположение [src/main/java/org/example/entity](src/main/java/org/example/entity)

### Базовые классы

1. BankProduct - абстрактный базовый класс для всех продуктов
    * Поля: название, валюта, баланс
    * Методы: пополнение (абстрактный), валидация баланса и суммы пополнения

2. Card - интерфейс для банковских карт
    * Методы: списание, запрос баланса

### Реализация карт

1. DebitCard - дебетовая карта
    * Базовая реализация

2. CurrencyDebitCard - валютная дебетовая карта
    * Доп. поле: курс обмена
    * Метод конвертации в основную валюту (RUB)

3. CreditCard - кредитная карта
    * Доп. поля: процентная ставка, задолженность
    * При превышении суммы баланса - разница зачисляется в задолженность

### Реализация вклада

1. Deposit - банковский вклад
    * Состояние: активен/неактивен
    * Метод закрытия вклада

# Тесты

Расположены в [src/test/java/bankProduct](src/test/java/bankProduct)

Запуск тестов
`mvn test`
