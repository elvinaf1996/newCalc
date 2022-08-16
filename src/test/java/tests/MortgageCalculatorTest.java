package tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import pages.HomePage;
import pages.MortgageCalculatorPage;

@DisplayName("Проверка работы Ипотечного калькулятора")
public class MortgageCalculatorTest extends BaseTest{

    private String programNewBuilding = "Новостройка";
    private String programResellers = "Вторичное жилье";
    private String programLoanSecuredByRealEstate = "Кредит под залог недвижимости";
    private String programFamilyMortgage = "Семейная ипотека";
    private String programMortgageWithStateSupport = "Ипотека с господдержкой";
    private String programMortgageRefinancing = "Рефинансирование ипотеки";
    private String itemFromHint = "Ипотечный калькулятор";
    private String nameSection = "Ипотека";
    private int realEstateValue = 10_000_000;
    private int downPaymentAmount = 5_000_000;
    private int countOfYear = 10;

    @DisplayName("Проверка расчета ЕП в новостройке")
    @Test
    public void newBuildingCalculatorTest(){
        HomePage homePage = new HomePage();
        homePage.openPage()
                .pointToSection(nameSection)
                .selectItemFromHint(itemFromHint);
        MortgageCalculatorPage mortgageCalculatorPage = new MortgageCalculatorPage();
        mortgageCalculatorPage.chooseALoanProgram(programNewBuilding)
                .enterTheValueOfTheProperty(realEstateValue)
                .enterTheAmountOfTheDownPayment(downPaymentAmount);


                /*.enterTheTermOfTheLoan(countOfYear)
                .loanAmountCheck(realEstateValue, downPaymentAmount)
                .checkingTheMonthlyPayment(realEstateValue, downPaymentAmount, countOfYear)
                .checkingTheAmountOfTheTaxDeduction(realEstateValue, downPaymentAmount, countOfYear);*/
    }
}