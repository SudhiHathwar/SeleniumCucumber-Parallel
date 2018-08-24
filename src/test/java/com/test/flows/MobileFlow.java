package com.test.flows;

import com.test.Configuration.BrowserHelper;
import com.test.Configuration.BrowserInterface;
import com.test.Configuration.LocalDriverManager;
import com.test.Screens.HomePageObjects;
import com.test.Screens.YourInfoPageObjects;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

public class MobileFlow implements BrowserInterface {

    private WebDriver driver;
    private HomePageObjects homePageObjects;
    private YourInfoPageObjects yourInfoPageObjects;
    private BrowserHelper helper;

    public MobileFlow ( WebDriver driver ) {
        this.driver = driver;

        homePageObjects = new HomePageObjects();
        yourInfoPageObjects = new YourInfoPageObjects();

        PageFactory.initElements(driver, homePageObjects);
        PageFactory.initElements(driver, yourInfoPageObjects);
        helper = new BrowserHelper(driver);
    }

    @Override
    public void selectInsuranceTypeAndEnterZipCode () {
        helper.waitForElement(homePageObjects.m_zipCode);

        homePageObjects.m_zipCode.sendKeys("92122");

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", homePageObjects.startYourQuote);

        helper.SelectDropdownByText(homePageObjects.quoteType, "Auto Insurance");

        homePageObjects.zipCode.sendKeys("92122");

        homePageObjects.startMyQuote.click();
    }

    @Override
    public void clickOnThanksButton () {
        helper.waitForElement(yourInfoPageObjects.thanksButton);

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", yourInfoPageObjects.thanksButton);
    }

    @Override
    public void selectDOB () {
        ((AppiumDriver) driver).context("NATIVE_APP");

        WebElement year = ((AppiumDriver) driver).findElementById("android:id/date_picker_header_year");

        year.click();

        selectYear();

        WebElement set = ((AppiumDriver) driver).findElementById("android:id/button1");

        set.click();

        ((AppiumDriver) driver).context("CHROMIUM");
    }

    private void selectYear () {

        String year = "1992";

        scroll(year);

        ((AndroidDriver) LocalDriverManager.getDriver()).findElementByAndroidUIAutomator("new UiSelector().textContains(\"" + year + "\")").click();
    }


    private void scroll ( String year ) {
        List<WebElement> elements = ((AndroidDriver) LocalDriverManager.getDriver()).findElementsByAndroidUIAutomator("new UiSelector().textContains(\"" + year + "\")");

        while (elements.size() == 0) {

            Dimension dimensions = LocalDriverManager.getDriver().manage().window().getSize();

            int height = dimensions.getHeight();
            int width = dimensions.getWidth();
            new TouchAction(((AppiumDriver) LocalDriverManager.getDriver())).
                    press(PointOption.point(width / 2, height / 3)).
                    waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1))).
                    moveTo(PointOption.point(width / 2, height / 2)).release().perform();
            elements = ((AndroidDriver) LocalDriverManager.getDriver()).findElementsByAndroidUIAutomator("new UiSelector().textContains(\"" + year + "\")");
        }
    }
}