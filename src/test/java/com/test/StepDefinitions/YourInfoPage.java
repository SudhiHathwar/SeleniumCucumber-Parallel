package com.test.StepDefinitions;

import com.test.Configuration.BrowserHelper;
import com.test.Configuration.BrowserInterface;
import com.test.Configuration.LocalDriverManager;
import com.test.Configuration.TestRunnerInfo;
import com.test.Screens.YourInfoPageObjects;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class YourInfoPage {

    private WebDriver driver;
    private YourInfoPageObjects yourInfoPageObjects;
    private BrowserInterface runnerInfo;
    private BrowserHelper helper;

    public YourInfoPage () {
        this.driver = LocalDriverManager.getDriver();
        helper = new BrowserHelper(driver);
        yourInfoPageObjects = new YourInfoPageObjects();
        runnerInfo = new TestRunnerInfo().getRunnerInfo();
        PageFactory.initElements(driver, yourInfoPageObjects);
    }

    @When("^User enters user info details$")
    public void enterYourInfoDetails () {

        runnerInfo.clickOnThanksButton();

        helper.waitForElement(yourInfoPageObjects.firstName);

        yourInfoPageObjects.firstName.sendKeys("Test");
        yourInfoPageObjects.lastName.sendKeys("Automation");
        yourInfoPageObjects.dateOfBirth.click();

        runnerInfo.selectDOB();

        yourInfoPageObjects.male.click();

        yourInfoPageObjects.residentialAddress.sendKeys("16, Main Street");
        yourInfoPageObjects.city.sendKeys("My city");

        yourInfoPageObjects.currentAutoInsuranceCoverage_Yes.click();

        yourInfoPageObjects.startQuote.click();

    }
}
