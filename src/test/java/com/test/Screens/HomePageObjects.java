package com.test.Screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePageObjects {

    @FindBy(id = "selectInsuraceType")
    public WebElement insuranceType;

    @FindBy(id="zipCode")
    public WebElement zipCode;

    @FindBy(id="startYourQuote")
    public WebElement startYourQuote;

    @FindBy(xpath = "//img[@title = 'Auto']")
    public WebElement AutoInsurance;
}
