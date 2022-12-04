package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement transferMoney = $(withText("Пополнение карты"));
    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement from = $("[data-test-id=from] input");
    private SelenideElement buttonPay = $("[data-test-id=action-transfer]");


    public void checkHeadingPaymentCards() {
        transferMoney.shouldBe(Condition.visible);
    }

    public void setPayCardNumber(String card, int payment) {
        amount.setValue(String.valueOf(payment));
        from.setValue(String.valueOf(card));
        buttonPay.click();
    }

    public DashboardPage validPayCard() {
        return new DashboardPage();
    }

    public void invalidPayCard() {
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Произошла ошибка"));
    }

    public void validPayExtendAmount() {
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Недостаточно средств на карте"));
    }

}