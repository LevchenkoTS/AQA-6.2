package ru.netology.steps;

import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;

import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TransferSteps {
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;

    @Пусть("пусть пользователь залогинен с именем {string} и паролем {string}")
    public void loginUser (String login, String password) {
        var loginPage = open("http://localhost:9999/", LoginPage.class);
        var authInfo = new DataHelper.AuthInfo(login, password);
        var verificationPage = loginPage.validLogin(authInfo);
        dashboardPage = verificationPage.validVerify(DataHelper.getVerificationCodeFor(authInfo));
    }

    @Когда("когда пользователь переводит {int} рублей с карты с номером {string} на свою {int} карту с главной страницы")
    public void transferToCard (int amount, String cardFromNumber, int cardForTransfer) {
        var transferPage = dashboardPage.chooseCard(DataHelper.getCardByNumberOnPage(cardForTransfer));
        transferPage.moneyTransfer(String.valueOf(amount), DataHelper.getCardByNumber(cardFromNumber));
    }

    @Тогда("тогда баланс его {int} карты из списка на главной странице должен стать {int} рублей")
    public void finishBalance (int cardForTransfer, int expectedBalance) {
        var actualBalance = dashboardPage.getCardBalance(DataHelper.getCardByNumberOnPage(cardForTransfer));
        assertEquals(expectedBalance, actualBalance);
    }
}