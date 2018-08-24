package com.test.StepDefinitions;

import com.test.Configuration.BrowserHelper;
import com.test.Configuration.LocalDriverManager;
import com.test.Screens.DriversPageObjects;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class DriversPage {

    private WebDriver driver;
    private DriversPageObjects driversPageObjects;
    private BrowserHelper helper;

    public DriversPage () {

        this.driver = LocalDriverManager.getDriver();
        helper = new BrowserHelper(driver);
        driversPageObjects = new DriversPageObjects();

        PageFactory.initElements(driver, driversPageObjects);
    }

    @When("^User enters driver details$")
    public void enterDriverDetails () throws InterruptedException {

        helper.waitForElement(driversPageObjects.single);

        driversPageObjects.single.click();

        helper.waitForElement(driversPageObjects.architect);

        driversPageObjects.architect.click();

        helper.waitForElement(driversPageObjects.architect);

        driversPageObjects.age.sendKeys("16");

        driversPageObjects.continueButton.click();

        helper.waitForElement(driversPageObjects.emailAddress);

        driversPageObjects.emailAddress.sendKeys("test@automation.com");
        driversPageObjects.phoneNumber.sendKeys("4087859969");

        driversPageObjects.continueButton.click();

        helper.waitForElement(driversPageObjects.no);

        driversPageObjects.no.click();

        helper.waitForElement(driversPageObjects.no);

        driversPageObjects.addthisDriver.click();

        Thread.sleep(5555);

        driversPageObjects.no.click();
    }
}
