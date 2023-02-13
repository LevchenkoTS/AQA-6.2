package ru.netology.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }
        public static AuthInfo getAuthInfo() {
            return new AuthInfo("vasya", "qwerty123");
        }


    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }


    @Value
    public static class Card {
        private String cardId;
        private String secretCardId;
    }

    public static Card getFirstCard() {
        return new Card("5559 0000 0000 0001", "**** **** **** 0001");
    }

    public static Card getSecondCard() {
        return new Card("5559 0000 0000 0002", "**** **** **** 0002");
    }

    public static Card getCardByNumberOnPage(int numberOnPage) {
        Card card = null;
        if (numberOnPage == 1) {
            card = getFirstCard();
        }
        if (numberOnPage == 2) {
            card = getSecondCard();
        }
        return card;
    }

    public static Card getCardByNumber(String cardFromNumber) {
        Card card = null;
        if (cardFromNumber.equals("5559 0000 0000 0001")) {
            card = getFirstCard();
        }
        if (cardFromNumber.equals("5559 0000 0000 0002")) {
            card = getSecondCard();
        }
        return card;
    }
}

