package com.test.Screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DriversPageObjects {

    @FindBy(id = "FFQAuto_Driver_maritalStatus_S_Wrap")
    public WebElement single;

    @FindBy(xpath = "//span[text()='Architect']")
    public WebElement architect;

    @FindBy(id = "FFQAuto_Driver_ageLicensed")
    public WebElement age;

    @FindBy(id = "FFQAuto_Driver_emailAddress")
    public WebElement emailAddress;

    @FindBy(id = "FFQAuto_Driver_phoneNumber")
    public WebElement phoneNumber;

    @FindBy(xpath = "//button[text()='CONTINUE']")
    public WebElement continueButton;

    @FindBy(xpath = "//span[text()='No']")
    public WebElement no;

    @FindBy(id = "FFQAuto_Driver_incidentAddContbtn")
    public WebElement addthisDriver;
}
