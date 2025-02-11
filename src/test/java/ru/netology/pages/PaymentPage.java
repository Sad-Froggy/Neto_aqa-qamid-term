package ru.netology.pages;

import com.codeborne.selenide.Condition;

import java.time.Duration;

public class PaymentPage extends Page {

    public PaymentPage() {}

    public void checkOperationApproved() {
        notificationSuccess.shouldBe(Condition.visible, Duration.ofSeconds(15));
        notificationError.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void checkOperationDeclined() {
        notificationError.shouldNotBe(Condition.visible, Duration.ofSeconds(15));
        notificationSuccess.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
}
