package com.test.Configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserHelper {

    public WebDriver driver;

    public BrowserHelper( WebDriver driver ){
        this.driver = driver;
    }

    public void waitForPageToLoad ( WebElement id ) {
        WebDriverWait wait = new WebDriverWait(LocalDriverManager.getDriver(), 25);
        wait.until(ExpectedConditions.elementToBeClickable(id));
    }

    public WebElement waitForElement ( WebElement arg ) {
        waitForPageToLoad(arg);
        return arg;
    }

}


