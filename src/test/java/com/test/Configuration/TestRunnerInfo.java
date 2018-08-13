package com.test.Configuration;

import com.test.flows.WebFlow;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebDriver;

public class TestRunnerInfo {

    public BrowserInterface runnerInfo;
    static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    ViewFactory viewFactory;

    public TestRunnerInfo () {
        runnerInfo = runnerInfoStatus();
    }

    public BrowserInterface getRunnerInfo () {
        return runnerInfo;
    }

    public BrowserInterface runnerInfoStatus () {
        try {
            ((AppiumDriver) LocalDriverManager.getDriver()).getPlatformName();
            viewFactory = new ViewFactory(LocalDriverManager.getDriver());
            runnerInfo = viewFactory.getMobilePlatform(((AppiumDriver) LocalDriverManager.getDriver()).
                            getCapabilities().getCapability("platformName").toString(),
                    LocalDriverManager.getDriver());
            return runnerInfo;
        } catch (Exception e) {
            return new WebFlow(LocalDriverManager.getDriver());
        }
    }
}
