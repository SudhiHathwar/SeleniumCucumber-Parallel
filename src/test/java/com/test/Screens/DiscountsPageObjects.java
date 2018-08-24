package com.test.Screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DiscountsPageObjects {

    @FindBy(id = "d_expiry_button")
    public WebElement policyDate;

    @FindBy(id = "FFQ_Auto-bundlechk_continue-button")
    public WebElement skip;

    @FindBy(xpath = "//a[text()='Go to Farmers.com']")
    public WebElement farmersLink;
}
