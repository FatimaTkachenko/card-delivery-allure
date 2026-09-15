package ru.netology.carddelivery;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

    private UserInfo userInfo;

    @BeforeEach
    void setUp() {
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));

        Configuration.browser = System.getProperty("selenide.browser", "chrome");
        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless", "false"));
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
        Configuration.holdBrowserOpen = false;

        userInfo = DataGenerator.generateUserInfo();
        open("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        SelenideLogger.removeListener("AllureSelenide");
    }

    @Test
    @DisplayName("Успешное планирование встречи с представителем банка")
    void shouldPlanMeeting() {
        $("[data-test-id='city'] input").setValue(userInfo.getCity());
        $("[data-test-id='date'] input").doubleClick().setValue(userInfo.getDate());
        $("[data-test-id='name'] input").setValue(userInfo.getName());
        $("[data-test-id='phone'] input").setValue(userInfo.getPhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.text("Запланировать")).click();

        $("[data-test-id='success-notification']").shouldBe(Condition.visible);

        Allure.addAttachment(
                "Финальный скриншот",
                "image/png",
                new ByteArrayInputStream(
                        ((TakesScreenshot) WebDriverRunner.getWebDriver())
                                .getScreenshotAs(OutputType.BYTES)
                ),
                "png"
        );
    }
}