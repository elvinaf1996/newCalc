package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.Assert.assertEquals;

public class MortgageCalculatorPage {

    private final By LOAN_PROGRAM_SELECTION_BUTTON = By.cssSelector("[label=\"Программа кредитования\"]>[name=\"mortgageProduct\"]");
    private final By LENDING_PROGRAMS = By.cssSelector("[role=\"option\"]>[data-testid=\"text\"]");
    private final By LOAN_TERM_ENTRY_FIELD = By.cssSelector("[label=\"Срок кредита\"]>input");
    private final By INPUT_FIELD_COST_OF_REAL_ESTATE = By.cssSelector("[label=\"Стоимость недвижимости\"]>input");
    private final By INITIAL_PAYMENT_INPUT_FIELD = By.cssSelector("[label=\"Первоначальный взнос\"]>input");
    private final By LOAN_OPTIONS = By.cssSelector("h4[data-testid=\"heading\"][class=\"Wrapper-sc-6nwvzq-0 kRJvZg\"]");
    private final By SLIDERS = By.cssSelector(".rc-slider-handle");

    //расчет еп
    private int calculationOfTheAmountOfTheMonthlyPayment(int sumOfCredit, double monthlyRate, int countOfYear) {
        return (int) Math.ceil(sumOfCredit *
                (monthlyRate * Math.pow(1 + monthlyRate, countOfYear * 12)) /
                (Math.pow(1 + monthlyRate, countOfYear * 12) - 1));
    }

    //расчет суммы налогового вычета
    private int taxDeductionCalculation(int sumOfCredit, int countOfYear, int monthlyPayment) {
        int maximumAmountOfRealEstate = 2000000;
        int maximumOverpaymentAmount = 3000000;
        double returnPercentage = 0.13;
        int refundAmount = 0;
        if (sumOfCredit >= maximumAmountOfRealEstate) {
            refundAmount += maximumAmountOfRealEstate * returnPercentage;
        } else {
            refundAmount += sumOfCredit * returnPercentage;
        }
        int overpayment = monthlyPayment * countOfYear * 12 - sumOfCredit; //Расчет переплаты
        if (overpayment >= maximumOverpaymentAmount) {
            refundAmount += maximumOverpaymentAmount * returnPercentage;
        } else {
            refundAmount += overpayment * returnPercentage;
        }
        return refundAmount;
    }

    //установка значения ползунком для суммы недвижимости
    private void setTheDesiredValueWithTheSliderForSum(@NotNull SelenideElement selenideElement, int desiredValue,
                                                 int sliderIndexNumber) {
        int presentValue = Integer.parseInt(
                (selenideElement.getAttribute("value")).replaceAll("\\D+", ""));
        if (desiredValue > presentValue) {
            while (presentValue != desiredValue) {
                $$(SLIDERS).get(sliderIndexNumber).sendKeys(Keys.ARROW_RIGHT, Keys.ARROW_RIGHT);
                presentValue = Integer.parseInt(
                        (selenideElement.getAttribute("value").replaceAll("\\D+", "")));
            }
        } else if (desiredValue < presentValue) {
            while (presentValue != desiredValue) {
                $$(SLIDERS).get(sliderIndexNumber).sendKeys(Keys.ARROW_LEFT);
                presentValue = Integer.parseInt(
                        (selenideElement.getAttribute("value").replaceAll("\\D+", "")));
            }
        }
    }

    //установка значения ползунком для ПВ
    private void setTheDesiredValueWithTheSliderForPV(@NotNull SelenideElement selenideElement, int desiredValue,
                                                       int sliderIndexNumber) {
        int presentValue = Integer.parseInt(
                (selenideElement.getAttribute("value")).replaceAll("\\D+", ""));
        if (desiredValue > presentValue) {
            while (presentValue != desiredValue) {
                $$(SLIDERS).get(sliderIndexNumber).sendKeys(Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT);
                presentValue = Integer.parseInt(
                        (selenideElement.getAttribute("value").replaceAll("\\D+", "")));
            }
        } else if (desiredValue < presentValue) {
            while (presentValue != desiredValue) {
                $$(SLIDERS).get(sliderIndexNumber).sendKeys(Keys.ARROW_LEFT, Keys.ARROW_LEFT, Keys.ARROW_LEFT, Keys.ARROW_LEFT, Keys.ARROW_LEFT);
                presentValue = Integer.parseInt(
                        (selenideElement.getAttribute("value").replaceAll("\\D+", "")));
            }
        }
    }

    //установка значения ползунком для количества лет
    private void setTheDesiredValueWithTheSliderForYear(@NotNull SelenideElement selenideElement, int desiredValue,
                                                      int sliderIndexNumber) {
        int presentValue = Integer.parseInt(
                (selenideElement.getAttribute("value")).replaceAll("\\D+", ""));
        if (desiredValue > presentValue) {
            while (presentValue != desiredValue) {
                $$(SLIDERS).get(sliderIndexNumber).sendKeys(Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT);
                presentValue = Integer.parseInt(
                        (selenideElement.getAttribute("value").replaceAll("\\D+", "")));
            }
        } else if (desiredValue < presentValue) {
            while (presentValue != desiredValue) {
                $$(SLIDERS).get(sliderIndexNumber).sendKeys(Keys.ARROW_LEFT, Keys.ARROW_LEFT, Keys.ARROW_LEFT, Keys.ARROW_LEFT, Keys.ARROW_LEFT);
                presentValue = Integer.parseInt(
                        (selenideElement.getAttribute("value").replaceAll("\\D+", "")));
            }
        }
    }

    @Step("Выбираем программу кредитования: {0}")
    public MortgageCalculatorPage chooseALoanProgram(String nameALoanProgram) {
        $(LOAN_PROGRAM_SELECTION_BUTTON).scrollTo().click();
        $$(LENDING_PROGRAMS).findBy(Condition.text(nameALoanProgram)).click();
        return this;
    }

    @Step("Вводим стоимость недвижимости: {0}")
    public MortgageCalculatorPage enterTheValueOfTheProperty(int realEstateValue) {
        setTheDesiredValueWithTheSliderForSum($(INPUT_FIELD_COST_OF_REAL_ESTATE), realEstateValue, 0);
        return this;
    }

    @Step("Вводим сумму первоначального взноса: {0}")
    public MortgageCalculatorPage enterTheAmountOfTheDownPayment(int downPaymentAmount) {
        setTheDesiredValueWithTheSliderForPV(
                $(INITIAL_PAYMENT_INPUT_FIELD), downPaymentAmount, 1);
        return this;
    }

    @Step("Вводим срок кредита: {0}")
    public MortgageCalculatorPage enterTheTermOfTheLoan(int countOfYear) {
        $(LOAN_TERM_ENTRY_FIELD).scrollTo();
        setTheDesiredValueWithTheSliderForYear($(LOAN_TERM_ENTRY_FIELD), countOfYear, 2);
        return this;
    }

    @Step("Проверяем сумму кредита")
    public MortgageCalculatorPage loanAmountCheck(int realEstateValue, int downPaymentAmount) {
        int creditAmount = Integer.parseInt(($$(LOAN_OPTIONS).get(2).text().replaceAll("\\D+", "")));
        assertEquals(realEstateValue - downPaymentAmount, creditAmount);
        return this;
    }

    @Step("Проверяем ежемесячный платеж")
    public MortgageCalculatorPage checkingTheMonthlyPayment(int realEstateValue, int downPaymentAmount, int countOfYear) {
        int creditAmount = realEstateValue - downPaymentAmount;
        double monthlyRate = Double.parseDouble(
                $$(LOAN_OPTIONS).get(1).getText().replace("%", "")
                        .replace(",", ".")) / 1200;
        int monthlyPaymentAmount = Integer.parseInt($$(LOAN_OPTIONS).get(0).getText()
                .replaceAll("\\D+", ""));
        assertEquals(calculationOfTheAmountOfTheMonthlyPayment(creditAmount, monthlyRate, countOfYear),
                monthlyPaymentAmount);
        return this;
    }

    @Step("Проверяем сумму налогового вычета")
    public MortgageCalculatorPage checkingTheAmountOfTheTaxDeduction
            (int realEstateValue, int downPaymentAmount, int countOfYear) {
        int monthlyPaymentAmount = Integer.parseInt(
                $$(LOAN_OPTIONS).get(0).getText().replaceAll("\\D+", ""));
        int deductionAmount = Integer.parseInt(
                $$(LOAN_OPTIONS).get(4).getText().replaceAll("\\D+", ""));
        assertEquals(taxDeductionCalculation(realEstateValue - downPaymentAmount,
                countOfYear, monthlyPaymentAmount), deductionAmount);
        return this;
    }
}