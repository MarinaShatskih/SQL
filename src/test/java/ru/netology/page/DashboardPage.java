package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    private SelenideElement heading = $("h2");

    public DashboardPage() {
        heading.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Личный кабинет"));
    }
}