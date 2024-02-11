package qa.guru.allure.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import qa.guru.allure.steps.WebSteps;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.attachment;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class AnnotatedTests extends TestBase {

    private static final String REPOSITORY = "Kyzrwolf/qa-guru-allure-reports";
    private static final String ISSUE = "look at this dude";

    WebSteps steps = new WebSteps();

    @Test
    @DisplayName("Issue тест с лямбда разметкой")
    public void testLambdaStep() {

        step("Открываем главную страницу", () -> open("https://github.com"));
        step("Ищем репозиторий" + REPOSITORY, () -> {
            $(".search-input").click();
            $("#query-builder-test").sendKeys("Kyzrwolf/qa-guru-allure-reports", Keys.ENTER);
            steps.takeScreenshot();

        });
        step("Кликаем по ссылке репозитория " + REPOSITORY, () -> {
            $(linkText(REPOSITORY)).click();
            attachment("Source", webdriver().driver().source());
            steps.takeScreenshot();

        });
        step("Открываем таб Issues", () -> {
            $("#issues-tab").click();
            attachment("Source", webdriver().driver().source());
            steps.takeScreenshot();

        });
        step("Проверяем наличие Issue с названием " + ISSUE, () -> {
            $(withText("look at this dude")).should(Condition.exist);
            attachment("Source", webdriver().driver().source());
            steps.takeScreenshot();

        });

    }

    @Test
    @Feature("GitHub Issue")
    @Story("Issue")
    @Owner("seningv")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "Repo", url = "https://github.com/Kyzrwolf/qa-guru-allure-reports")
    @DisplayName("Issue тест с аннотациями и степами")
    public void testAnnotatedStep() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.clickOnRepositoryLink(REPOSITORY);
        steps.openIssuesTab();
        steps.shouldSeeIssueWithNumber(ISSUE);
        steps.takeScreenshot();


    }

}
