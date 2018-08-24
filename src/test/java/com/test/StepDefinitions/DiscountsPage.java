package com.test.StepDefinitions;

import com.test.Configuration.BrowserHelper;
import com.test.Configuration.LocalDriverManager;
import com.test.Screens.DiscountsPageObjects;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class DiscountsPage {

    private WebDriver driver;
    private DiscountsPageObjects discountsPageObjects;
    private BrowserHelper helper;

    public DiscountsPage () {
        this.driver = LocalDriverManager.getDriver();

        discountsPageObjects = new DiscountsPageObjects();
        helper = new BrowserHelper(driver);
        PageFactory.initElements(driver, discountsPageObjects);
    }

    @When("^User enter discount details$")
    public void enterDiscountDetails () {

        helper.waitForElement(discountsPageObjects.policyDate);

        discountsPageObjects.policyDate.click();

        helper.waitForElement(discountsPageObjects.skip);

        discountsPageObjects.skip.click();
    }

    @Then("^User must be displayed with special attrntion modal$")
    public void verifyFarmersLink () {

        helper.waitForElement(discountsPageObjects.farmersLink);

        Assert.assertTrue(discountsPageObjects.farmersLink.isDisplayed());
    }

}
