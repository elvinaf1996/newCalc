package pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static tests.BaseTest.cfg;

public class HomePage {

    private final By BUTTONS = By.cssSelector("a[data-testid=\"button\"]>[data-testid=\"flexbox\"]");
    private final By SECTIONS = By.cssSelector(".LinkWrapper-sc-a7l7fm-0.eaxjcO.styled__MenuItem-sc-ip06ne-6");
    private final By FOOTER_SECTION = By.cssSelector(".styled__NavigationBlockItem-sc-doagu6-1 [data-testid=\"text\"]");
    private final By HINTS_UNDER_SECTIONS = By.cssSelector("a[data-testid=\"link\"]>.Wrapper-sc-1vydk7-0.icoeUX");

    @Step("Открываем главную страницу")
    public HomePage openPage() {
        open(cfg.baseUrl());
        return this;
    }

    @Step("Нажимаем кнопку \"подробнее\"")
    public HomePage goToCreditCardPage() {
        $$(BUTTONS).first().click();
        return this;
    }

    @Step("Перейти в раздел из шапки сайта: {0}")
    public HomePage goToSection(String nameSection) {
        $$(SECTIONS).findBy(Condition.text(nameSection)).click();
        return this;
    }

    @Step("Перейти в раздел из футера: {0}")
    public HomePage goToFooterSections(String footerSectionName) {
        $$(FOOTER_SECTION).findBy(Condition.text(footerSectionName)).scrollTo().click();
        return this;
    }

    @Step("Навести на раздел из шапки сайта: {0}")
    public HomePage pointToSection(String nameSection) {
        $$(SECTIONS).findBy(Condition.text(nameSection)).hover();
        return this;
    }

    @Step("Выбираем подсказку снизу секции: {0}")
    public HomePage selectItemFromHint(String itemFromHint) {
        $$(HINTS_UNDER_SECTIONS).findBy(Condition.text(itemFromHint)).click();
        return this;
    }
}