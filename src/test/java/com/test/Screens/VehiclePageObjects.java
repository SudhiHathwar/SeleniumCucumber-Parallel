package com.test.Screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class VehiclePageObjects {

    @FindBy(id = "FFQAuto_Vehicle_vehicleYear")
    public WebElement year;

    @FindBy(id = "FFQAuto_Vehicle_vehicleMake")
    public WebElement make;

    @FindBy(id = "FFQAuto_Vehicle_vehicleModel")
    public WebElement model;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement continueButton;

    @FindBy(id = "FFQAuto_Vehicle_vehicleTypeSelection")
    public WebElement type;

    @FindBy(xpath = "//span[text()='Owned']")
    public WebElement owned;

    @FindBy(id = "d_expiry_button")
    public WebElement date;

    @FindBy(id = "FFQAuto_Vehicle_vehicleUsedFor_PLS_Wrap")
    public WebElement pleasure;

    @FindBy(id = "FFQAuto_Vehicle_milesDrivePerYear")
    public WebElement miles;

    @FindBy(xpath = "//span[text()='No']")
    public WebElement rideSharing_No;

    @FindBy(id = "FFQAuto_Vehicle_vehicleFeaturesbtn")
    public WebElement addThisVeicle;

    @FindBy(id = "FFQAuto_Vehicle_VehicleAnother_2_Wrap")
    public WebElement addAnotherVehicle_No;
}
