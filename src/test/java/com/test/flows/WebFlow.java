package com.test.flows;

import com.test.Configuration.BrowserHelper;
import com.test.Configuration.BrowserInterface;
import com.test.Entities.InsuranceType;
import com.test.Screens.HomePageObjects;
import com.test.Screens.YourInfoPageObjects;
import com.test.StepDefinitions.YourInfoPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class WebFlow implements BrowserInterface {

    private WebDriver driver;
    private HomePageObjects homePageObjects;
    private YourInfoPageObjects yourInfoPageObjects;
    private BrowserHelper helper;

    public WebFlow ( WebDriver driver ) {
        this.driver = driver;

        homePageObjects = new HomePageObjects();
        yourInfoPageObjects = new YourInfoPageObjects();

        PageFactory.initElements(driver, homePageObjects);
        PageFactory.initElements(driver, yourInfoPageObjects);
        helper = new BrowserHelper(driver);
    }

    @Override
    public void selectInsuranceTypeAndEnterZipCode () {

//        helper.SelectDropdownByText(homePageObjects.insuranceType, insuranceType.toString());

//        homePageObjects.zipCode.sendKeys(zipCode);

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", homePageObjects.startYourQuote);

        helper.SelectDropdownByText(homePageObjects.quoteType, "Auto Insurance");

        homePageObjects.zipCode.sendKeys("92122");

        homePageObjects.startMyQuote.click();
    }

    @Override
    public void clickOnThanksButton () {
    }
}
