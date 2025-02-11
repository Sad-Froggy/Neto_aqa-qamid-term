package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import ru.netology.utils.DataHelper;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@Getter
public class Page {

    protected String host = "http://localhost:8080";
    protected SelenideElement paymentButton = $(byText("Купить")).parent().parent();
    protected SelenideElement creditButton = $(byText("Купить в кредит")).parent().parent();
    protected SelenideElement continueButton = $(byText("Продолжить")).parent().parent();
    protected SelenideElement cardNumberField = $(byText("Номер карты")).parent();
    protected SelenideElement monthField = $(byText("Месяц")).parent();
    protected SelenideElement yearField = $(byText("Год")).parent();
    protected SelenideElement ownerField = $(byText("Владелец")).parent();
    protected SelenideElement cvcField = $(byText("CVC/CVV")).parent();
    protected SelenideElement notificationSuccess = $(".notification_status_ok ");
    protected SelenideElement notificationError = $(".notification_status_error");

    public Page() {
        open(host);
    }

    public void commit() {
        continueButton.click();
    }

    public void fillData(DataHelper.Card card) {
        cardNumberField.$(".input__control").setValue(card.getNumber());
        monthField.$(".input__control").setValue(card.getMonth());
        yearField.$(".input__control").setValue(card.getYear());
        ownerField.$(".input__control").setValue(card.getOwner());
        cvcField.$(".input__control").setValue(card.getCvc());
    }

    public PaymentPage toPaymentPage() {
        PaymentPage paymentPage = page(PaymentPage.class);
        paymentPage.paymentButton.click();
        return paymentPage;
    }

    public void checkWrongMonthError() {
        monthField.$(".input__sub").shouldHave(Condition.exactText("Неверно указан срок действия карты"));
    }

    public void checkEarlyYearError() {
        yearField.$(".input__sub").shouldHave(Condition.exactText("Истёк срок действия карты"));
    }

    public void checkWrongCvcError() {
        cvcField.$(".input__sub").shouldHave(Condition.exactText("Неверный формат"));
    }

    public void checkEmptyNameError() {
        ownerField.$(".input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    public void checkWrongNameError() {
        ownerField.$(".input__sub").shouldHave(Condition.exactText("Неверный формат"));
    }

    public void checkEmptyCardError() {
        cardNumberField.$(".input__sub").shouldHave(Condition.exactText("Неверный формат"));
    }

    public void checkEmptyYearError() {
        yearField.$(".input__sub").shouldHave(Condition.exactText("Неверный формат"));
    }

    public void checkEmptyMonthError() {
        monthField.$(".input__sub").shouldHave(Condition.exactText("Неверный формат"));
    }
}
