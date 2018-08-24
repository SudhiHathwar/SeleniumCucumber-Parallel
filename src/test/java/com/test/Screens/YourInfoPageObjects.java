package com.test.Screens;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class YourInfoPageObjects {

    @FindBy(id = "thanks_btn")
    public WebElement thanksButton;

    @FindBy(id = "FFQAuto_Yourinfo_firstName")
    public WebElement firstName;

    @FindBy(id = "FFQAuto_Yourinfo_lastName")
    public WebElement lastName;

    @FindBy(name = "dob")
    public WebElement dateOfBirth;

    @FindBy(id = "FFQAuto_Yourinfo_ResedentialAddress")
    public WebElement residentialAddress;

    @FindBy(id = "FFQAuto_Yourinfo_Apartment")
    public WebElement apartment;

    @FindBy(id = "FFQAuto_Yourinfo_City")
    public WebElement city;

    @FindBy(id = "FFQAuto_Yourinfo_questionWrap")
    public WebElement currentAutoInsuranceCoverage_Yes;

    @FindBy(id = "FFQAuto_Yourinfo_question1Wrap")
    public WebElement currentAutoInsuranceCoverage_No;

    @FindBy(id = "FFQAuto_Yourinfo_genderWrap")
    public WebElement male;

    @FindBy(id = "FFQAuto_Yourinfo_gender1Wrap")
    public WebElement feMale;

    @FindBy(id = "FFQAuto_Yourinfo_startQuoteBtn")
    public WebElement startQuote;
}
