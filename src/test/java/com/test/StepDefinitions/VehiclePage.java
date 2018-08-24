package com.test.StepDefinitions;

import com.test.Configuration.BrowserHelper;
import com.test.Configuration.LocalDriverManager;
import com.test.Screens.VehiclePageObjects;
import cucumber.api.java.en.When;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class VehiclePage {

    private WebDriver driver;
    private VehiclePageObjects vehiclePageObjects;
    private BrowserHelper browserHelper;

    public VehiclePage () {
        this.driver = LocalDriverManager.getDriver();
        vehiclePageObjects = new VehiclePageObjects();
        browserHelper = new BrowserHelper(driver);
        PageFactory.initElements(driver, vehiclePageObjects);
    }

    @When("^User enters vehicle details$")
    public void enterVehicleDetails () throws InterruptedException {

        browserHelper.waitForElement(vehiclePageObjects.year);

        browserHelper.SelectDropdownByText(vehiclePageObjects.year, "2018");

        browserHelper.waitForElement(vehiclePageObjects.make);

        browserHelper.SelectDropdownByText(vehiclePageObjects.make, "BUGATTI");

        browserHelper.waitForElement(vehiclePageObjects.model);

        browserHelper.SelectDropdownByText(vehiclePageObjects.model, "CHIRON 2D");


        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", vehiclePageObjects.continueButton);

        browserHelper.waitForElement(vehiclePageObjects.type);

        browserHelper.SelectDropdownByText(vehiclePageObjects.type, "Convertibles");

        executor.executeScript("arguments[0].click();", vehiclePageObjects.continueButton);

        browserHelper.waitForElement(vehiclePageObjects.owned);

        executor.executeScript("arguments[0].click();", vehiclePageObjects.owned);

        browserHelper.waitForElement(vehiclePageObjects.date);

        vehiclePageObjects.date.click();

        browserHelper.waitForElement(vehiclePageObjects.pleasure);

        vehiclePageObjects.pleasure.click();

        Thread.sleep(1111);

        vehiclePageObjects.miles.clear();

        vehiclePageObjects.miles.sendKeys("12000");

        vehiclePageObjects.continueButton.click();

        browserHelper.waitForElement(vehiclePageObjects.rideSharing_No);

        executor.executeScript("arguments[0].click();", vehiclePageObjects.rideSharing_No);

        browserHelper.waitForElement(vehiclePageObjects.addThisVeicle);

        vehiclePageObjects.addThisVeicle.click();

        browserHelper.waitForElement(vehiclePageObjects.addAnotherVehicle_No);

        Thread.sleep(1111);

        executor.executeScript("arguments[0].click();", vehiclePageObjects.addAnotherVehicle_No);
    }
}
