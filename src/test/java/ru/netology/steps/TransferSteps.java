package ru.netology.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.И;
import org.junit.jupiter.api.Assertions;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;

import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;


public class TransferSteps {
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;


    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void loginUser(String name, String password) {
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        DataHelper.AuthInfo authInfo = new DataHelper.AuthInfo(name, password);
        VerificationPage verificationPage = loginPage.validLogin(authInfo);
        DataHelper.VerificationCode verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Когда("пользователь переводит {int} рублей с карты с номером {string} на свою 1 карту с главной страницы")
    public void transferMoney(String amount, String cardFrom) {
        var transferPage = dashboardPage.chooseCard(DataHelper.getSecondCard().getSecretCardId());;
        dashboardPage = transferPage.moneyTransfer(amount, cardFrom);
    }

    @Тогда("баланс его 1 карты из списка на главной странице должен стать {int} рублей")
    public void verifyBalance(String cardId, String expectedBalance) {
        var actualBalance = dashboardPage.getCardBalance(DataHelper.getFirstCard().getCardId());
        Assertions.assertEquals(expectedBalance, actualBalance);
    }
}