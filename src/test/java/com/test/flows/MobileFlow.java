package com.test.flows;

import com.test.Configuration.BrowserHelper;
import com.test.Configuration.BrowserInterface;
import com.test.Entities.InsuranceType;
import com.test.Screens.HomePageObjects;
import com.test.Screens.YourInfoPageObjects;
import com.test.StepDefinitions.YourInfoPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class MobileFlow implements BrowserInterface {

    private WebDriver driver;
    private HomePageObjects homePageObjects;
    private YourInfoPageObjects yourInfoPageObjects;
    private BrowserHelper helper;

    public MobileFlow ( WebDriver driver ) {
        this.driver = driver;

        homePageObjects = new HomePageObjects();
        yourInfoPageObjects = new YourInfoPageObjects();

        PageFactory.initElements(driver, homePageObjects);
        PageFactory.initElements(driver, yourInfoPageObjects);
        helper = new BrowserHelper(driver);
    }

    @Override
    public void selectInsuranceTypeAndEnterZipCode () {
        homePageObjects.zipCode.sendKeys("92122");
        homePageObjects.startYourQuote.click();

        switch ("") {
            case "auto":
                homePageObjects.AutoInsurance.click();
                break;

            default:
                homePageObjects.AutoInsurance.click();
                break;
        }
    }

    @Override
    public void clickOnThanksButton () {
        yourInfoPageObjects.thanksButton.click();
    }

}
