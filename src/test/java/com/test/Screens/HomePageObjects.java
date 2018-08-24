package com.test.Screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePageObjects {

//    @FindBy(id = "selectInsuraceType")
//    public WebElement insuranceType;
//
    @FindBy(id="zipCode")
    public WebElement m_zipCode;

    @FindBy(id = "startYourQuote")
    public WebElement startYourQuote;

    @FindBy(name = "landing:j_idt38")
    public WebElement startMyQuote;

    @FindBy(xpath = "//img[@title = 'Auto']")
    public WebElement AutoInsurance;

    @FindBy(id = "landing:quoteType")
    public WebElement quoteType;

    @FindBy(id = "landing:zipCode")
    public WebElement zipCode;

    @FindBy(id="landing:DecideQuote:1")
    public WebElement retriveQuote;

    @FindBy(id="landing:lastName")
    public WebElement lastName;

    @FindBy(id="landing:datepicker")
    public WebElement dateOfBirth;
}
