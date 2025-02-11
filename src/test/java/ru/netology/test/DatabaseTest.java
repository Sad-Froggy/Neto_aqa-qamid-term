package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.pages.Page;
import ru.netology.pages.PaymentPage;
import ru.netology.utils.SQLHelper;
import ru.netology.utils.DataHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void clear() {
        SQLHelper.clearDB();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldCheckStatusDBApprovedCard() {
        DataHelper.Card approvedCard = new DataHelper.Card();
        approvedCard.setNumber(DataHelper.getApprovedCardNumber());
        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(approvedCard);
        paymentPage.commit();
        paymentPage.checkOperationApproved();
        String actual = SQLHelper.getStatus();
        assertEquals("APPROVED", actual);
    }

    @Test
    void shouldCheckStatusDBDeclinedCard() {
        DataHelper.Card declinedCard = new DataHelper.Card();
        declinedCard.setNumber(DataHelper.getDeclinedCardNumber());

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(declinedCard);
        paymentPage.commit();
        paymentPage.checkOperationDeclined();

        String actual = SQLHelper.getStatus();
        assertEquals("DECLINED", actual);
    }
}
