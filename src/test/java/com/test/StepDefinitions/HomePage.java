package com.test.StepDefinitions;

import com.test.Configuration.BrowserHelper;
import com.test.Configuration.BrowserInterface;
import com.test.Configuration.LocalDriverManager;
import com.test.Configuration.TestRunnerInfo;
import com.test.Screens.HomePageObjects;
import com.test.Utils.URLGetter;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    WebDriver driver;
    public BrowserInterface runnerInfo;
    BrowserHelper helper;
    HomePageObjects homePageObjects;

    public HomePage () {
        this.driver = LocalDriverManager.getDriver();
        runnerInfo = new TestRunnerInfo().getRunnerInfo();
        helper = new BrowserHelper(driver);
        homePageObjects = new HomePageObjects();
        PageFactory.initElements(driver, homePageObjects);
    }

    @Given("^User lands on home page$")
    public void loadURL () {
        URLGetter getter = new URLGetter();

        LocalDriverManager.getDriver().get(getter.getURL("APP_URL"));
    }

    @When("^User selects insurance type, enters zipCode and click on Get a Quote button$")
    public void selectInsuranceTypeAndEnterZipcode ( ) {
        runnerInfo.selectInsuranceTypeAndEnterZipCode();
    }



}
