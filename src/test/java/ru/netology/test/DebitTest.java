package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.pages.Page;
import ru.netology.pages.PaymentPage;
import ru.netology.utils.DataHelper;
import ru.netology.utils.DataHelper.Card;

public class DebitTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    public void ShouldDebit() {
        Card approvedCard = new Card();
        approvedCard.setNumber(DataHelper.getApprovedCardNumber());

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(approvedCard);
        paymentPage.commit();
        paymentPage.checkOperationApproved();
    }

    @Test
    public void ShouldNotDebit() {
        Card declinedCard = new Card();
        declinedCard.setNumber(DataHelper.getDeclinedCardNumber());

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(declinedCard);
        paymentPage.commit();
        paymentPage.checkOperationDeclined();
    }

    @Test
    public void ShouldNotDebitWithPreviousYear() {
        Card cardWithWrongYear = new Card();
        cardWithWrongYear.setNumber(DataHelper.getApprovedCardNumber());
        cardWithWrongYear.setYear(DataHelper.getPreviousYear());

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(cardWithWrongYear);
        paymentPage.commit();

        paymentPage.checkEarlyYearError();
    }

    @Test
    public void ShouldNotDebitWithPreviousMonth() {
        Card cardWithWrongMonth = new Card();
        cardWithWrongMonth.setNumber(DataHelper.getApprovedCardNumber());
        cardWithWrongMonth.setYear(DataHelper.getCurrentYear());
        cardWithWrongMonth.setMonth(DataHelper.getPreviousMonth());
        cardWithWrongMonth.checkIfMonthIsCorrect();

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(cardWithWrongMonth);
        paymentPage.commit();
        if (cardWithWrongMonth.getMonth().equals("12")) {
            paymentPage.checkEarlyYearError();
        } else {
            paymentPage.checkWrongMonthError();
        }
    }

    @Test
    public void ShouldDebitWithCurrentDate() {
        Card cardWithCurrentDate = new Card();
        cardWithCurrentDate.setNumber(DataHelper.getApprovedCardNumber());
        cardWithCurrentDate.setMonth(DataHelper.getMonth());
        cardWithCurrentDate.setYear(DataHelper.getCurrentYear());

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(cardWithCurrentDate);
        paymentPage.commit();
        paymentPage.checkOperationDeclined();
    }

    @Test
    public void ShouldNotDebitWithTooShortCVC() {
        Card shortCVCCard = new Card();
        shortCVCCard.setNumber(DataHelper.getApprovedCardNumber());
        shortCVCCard.setCvc(DataHelper.getShortCVC());

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(shortCVCCard);
        paymentPage.commit();
        paymentPage.checkWrongCvcError();
    }

    @Test
    public void ShouldNotDebitWithEmptyName() {
        Card emptyNameCard = new Card();
        emptyNameCard.setNumber(DataHelper.getApprovedCardNumber());
        emptyNameCard.setOwner("");

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(emptyNameCard);
        paymentPage.commit();
        paymentPage.checkEmptyNameError();
    }

    @Test
    public void ShouldNotDebitWithEmptyCVC() {
        Card emptyCVCCard = new Card();
        emptyCVCCard.setNumber(DataHelper.getApprovedCardNumber());
        emptyCVCCard.setCvc("");

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(emptyCVCCard);
        paymentPage.commit();
        paymentPage.checkWrongCvcError();
    }

    @Test
    public void ShouldNotDebitWithEmptyCardNumber() {
        Card emptyCard = new Card();
        emptyCard.setNumber("");

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(emptyCard);
        paymentPage.commit();
        paymentPage.checkEmptyCardError();
    }

    @Test
    public void ShouldNotDebitWithEmptyYear() {
        Card EmptyYearCard = new Card();
        EmptyYearCard.setNumber(DataHelper.getApprovedCardNumber());
        EmptyYearCard.setYear("");

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(EmptyYearCard);
        paymentPage.commit();
        paymentPage.checkEmptyYearError();
    }

    @Test
    public void ShouldNotDebitWithEmptyMonth() {
        Card EmpryMonthCard = new Card();
        EmpryMonthCard.setNumber(DataHelper.getApprovedCardNumber());
        EmpryMonthCard.setMonth("");

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(EmpryMonthCard);
        paymentPage.commit();
        paymentPage.checkEmptyMonthError();
    }

    @Test
    public void ShouldNotDebitWithIncorrectName() {
        Card emptyNameCard = new Card();
        emptyNameCard.setNumber(DataHelper.getApprovedCardNumber());
        emptyNameCard.setOwner("###");

        Page startPage = new Page();
        PaymentPage paymentPage = startPage.toPaymentPage();
        paymentPage.fillData(emptyNameCard);
        paymentPage.commit();
        paymentPage.checkWrongNameError();
    }

}
