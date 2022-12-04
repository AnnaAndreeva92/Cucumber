package ru.netology.test;

import com.codeborne.selenide.Selenide;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferFromSecondToFirstCard() {
        val amount = 1000;
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        val initialBalanceToCard = dashboardPage.getFirstCardBalance();
        val initialBalanceFromCard = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.validChoosePay1();
        transferPage.checkHeadingPaymentCards();
        transferPage.setPayCardNumber(DataHelper.getCardSecond(), amount);
        val dashboardPage1 = transferPage.validPayCard();
        val actual = dashboardPage1.getFirstCardBalance();
        val expected = initialBalanceToCard + amount;
        val actual2 = dashboardPage1.getSecondCardBalance();
        val expected2 = initialBalanceFromCard - amount;
        assertEquals(expected, actual);
        assertEquals(expected2, actual2);
    }

    @Test
    void shouldTransferFromFirstToSecondCard() {
        val amount = 1000;
        val logiPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = logiPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        val initialBalanceToCard = dashboardPage.getSecondCardBalance();
        val initialBalanceFromCard = dashboardPage.getFirstCardBalance();
        val transferPage = dashboardPage.validChoosePay2();
        transferPage.checkHeadingPaymentCards();
        transferPage.setPayCardNumber(DataHelper.getCardFirst(), amount);
        val dashboardPage1 = transferPage.validPayCard();
        val actual = dashboardPage1.getSecondCardBalance();
        val expected = initialBalanceToCard + amount;
        val actual2 = dashboardPage1.getFirstCardBalance();
        val expected2 = initialBalanceFromCard - amount;
        assertEquals(expected, actual);
        assertEquals(expected2, actual2);

    }

    @Test
    void shouldCheckTheTransferFromAnInvalidCard() {
        val amount = 5000;
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        val transferPage = dashboardPage.validChoosePay1();
        transferPage.checkHeadingPaymentCards();
        transferPage.setPayCardNumber(DataHelper.getInvalidCard(), amount);
        transferPage.invalidPayCard();
    }

    @Test
    void shouldTransferAnAmountGreaterThanTheLimitFromTheFirstCard() {
        val amount = 30_000;
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        val initialBalanceFromCard = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.validChoosePay1();
        transferPage.checkHeadingPaymentCards();
        transferPage.setPayCardNumber(DataHelper.getCardSecond(), amount);
        transferPage.validPayExtendAmount();

    }

    @Test
    void shouldTransferAnAmountGreaterThanTheLimitFromTheSecondCard() {
        val amount = 30_000;
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        val initialBalanceFromCard = dashboardPage.getFirstCardBalance();
        val transferPage = dashboardPage.validChoosePay2();
        transferPage.checkHeadingPaymentCards();
        transferPage.setPayCardNumber(DataHelper.getCardFirst(), amount);
        transferPage.validPayExtendAmount();
    }

}
