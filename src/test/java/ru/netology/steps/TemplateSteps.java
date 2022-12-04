package ru.netology.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;
import ru.netology.page.VerificationPage;

public class TemplateSteps {
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;

    private static TransferPage transferPage;

    @Пусть("открыта страница с формой авторизации {string}")
    public void openAuthPage(String url) {
        loginPage = Selenide.open(url, LoginPage.class);
    }

    @Когда("пользователь пытается авторизоваться с именем {string} и паролем {string}")
    public void loginWithNameAnnPassword(String login, String password) {
        verificationPage = loginPage.validLogin(new DataHelper.AuthInfo(login, password));
    }

    @И("пользователь вводит проверочный код из смс {string}")
    public void setValidCode(String verificationCode) {
        dashboardPage = verificationPage.validVerify(new DataHelper.VerificationCode(verificationCode));
    }

    @Тогда("происходит успешная авторизация и пользователь попадает на страницу 'Личный кабинет'")
    public void verifyDashboardPage() {
        dashboardPage.checkHeadingYourCards();
    }

    @Когда("пользователь переводит 5 000 рублей с карты с номером 5559 0000 0000 0002 на свою 1 карту с главной страницы")
    public void verifyMoneyTransfer() {
        transferPage = dashboardPage.validChoosePay2();
    }

    @Тогда("баланс его 1 карты из списка на главной странице должен стать 15 000 рублей.")
    public void initialBalanceFromCard() {
        transferPage.setPayCardNumber(DataHelper.getCardFirst(), 15_000);
    }

}
