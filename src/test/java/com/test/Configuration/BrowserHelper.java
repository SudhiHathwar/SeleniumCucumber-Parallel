package com.test.Configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserHelper {

    public WebDriver driver;

    public BrowserHelper( WebDriver driver ){
        this.driver = driver;
    }

    public void waitForPageToLoad ( WebElement id ) {
        WebDriverWait wait = new WebDriverWait(LocalDriverManager.getDriver(), 25);

        wait.until(ExpectedConditions.visibilityOf(id));

        wait.until(ExpectedConditions.elementToBeClickable(id));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WebElement waitForElement ( WebElement arg ) {
        waitForPageToLoad(arg);
        return arg;
    }

    public void SelectDropdownByText(WebElement dropDownelement, String textValue) {
        Select select = new Select(dropDownelement);
        select.selectByVisibleText(textValue);
        select = null;
    }

    public void SelectDropdownByIndex(WebElement dropDownelement, int indexValue) {
        Select select = new Select(dropDownelement);
        select.selectByIndex(indexValue);
        select = null;
    }

    public void SelectDropdownByValue(WebElement dropDownelement, String value)
    {
        Select select = new Select(dropDownelement);
        select.deselectByValue(value);
        select = null;
    }

    public String GetSelectedValueFromDropDown(WebElement element) {
        Select select = new Select(element);
        WebElement option = select.getFirstSelectedOption();
        return option.getText();
    }
}


