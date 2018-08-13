package com.test.Utils;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.utils.ExceptionUtil;
import com.test.Configuration.Hooks;
import com.test.Configuration.LocalDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Listener implements ITestListener, IInvokedMethodListener {

    private static String getTestMethodName ( ITestResult iTestResult ) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void beforeInvocation ( IInvokedMethod iInvokedMethod, ITestResult iTestResult ) {

        if (iInvokedMethod.isTestMethod()) {
            URL url = null;

            Hooks hooks = new Hooks();

            String platform = iInvokedMethod.getTestMethod().getXmlTest().getLocalParameters().get("platform");

            if (platform.toLowerCase().equals("android") || platform.toLowerCase().equals("ios")) {

                url = hooks.startServer();
            }

            String platformVersion = iInvokedMethod.getTestMethod().getXmlTest().getLocalParameters().get("platformVersion");
            String deviceName = iInvokedMethod.getTestMethod().getXmlTest().getLocalParameters().get("deviceName");
            String port = iInvokedMethod.getTestMethod().getXmlTest().getLocalParameters().get("port");
            String udid = iInvokedMethod.getTestMethod().getXmlTest().getLocalParameters().get("udid");
            String browserName = iInvokedMethod.getTestMethod().getXmlTest().getLocalParameters().get("browserName");

            hooks.launchDriver(url, platform, platformVersion, deviceName, port, udid, browserName);
        }
    }

    @Override
    public void afterInvocation ( IInvokedMethod iInvokedMethod, ITestResult iTestResult ) {

        if (iInvokedMethod.isTestMethod()) {
            if (LocalDriverManager.getDriver() != null) {
                LocalDriverManager.getDriver().quit();
            }
        }
    }

    @Override
    public void onStart ( ITestContext iTestContext ) {
        iTestContext.setAttribute("WebDriver", LocalDriverManager.getDriver());
    }

    @Override
    public void onFinish ( ITestContext iTestContext ) {
        ExtentManager.getReporter().flush();
    }

    @Override
    public void onTestStart ( ITestResult iTestResult ) {

        String deviceName = iTestResult.getMethod().getXmlTest().getLocalParameters().get("deviceName");
        String browserName = iTestResult.getMethod().getXmlTest().getLocalParameters().get("browserName");

        if (deviceName == null) {
            ExtentTestManager.createTest(iTestResult.getMethod().getMethodName().toUpperCase() + " - " + "DESKTOP", "Running tests on " + browserName + " browser");
        } else {
            ExtentTestManager.createTest(iTestResult.getMethod().getMethodName().toUpperCase() + " - " + deviceName.toUpperCase(), "Running tests on " + browserName + " browser");
        }
    }

    @Override
    public void onTestSuccess ( ITestResult iTestResult ) {
        ExtentTestManager.getTest().log(Status.INFO, "Test passed");
    }

    @Override
    public void onTestFailure ( ITestResult iTestResult ) {

        Object testClass = iTestResult.getInstance();
        WebDriver webDriver = LocalDriverManager.getDriver();

        File scrFile = ((TakesScreenshot) webDriver)
                .getScreenshotAs(OutputType.FILE);

        String failedScreen =
                System.getProperty("user.dir") + "/target/screenshot/" + "/"
                        + testClass.toString() + currentDateAndTime() + "_" + "_failed" + ".png";

        try {
            FileUtils.copyFile(scrFile, new File(failedScreen));

            ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
            ExtentTestManager.getTest().log(Status.INFO, "Failure Reason--->>> " + iTestResult.getThrowable().getCause().getMessage());
            ExtentTestManager.getTest().log(Status.INFO, "Exception Details--->>>" + ExceptionUtil.getStackTrace(iTestResult.getThrowable()));
            ExtentTestManager.getTest().addScreenCaptureFromPath(failedScreen, ExtentTestManager.getTest().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String currentDateAndTime () {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        return now.format(dtf);
    }

    @Override
    public void onTestSkipped ( ITestResult iTestResult ) {
        ExtentTestManager.extent.removeTest(ExtentTestManager.getTest());
        IRetryAnalyzer retryAnalyzer = iTestResult.getMethod().getRetryAnalyzer();
        if (((Retry) retryAnalyzer).retryCountForTest == ((Retry) retryAnalyzer).maxRetryCount) {
            ExtentManager.getReporter().flush();
        }
        System.out.println("I am onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped or failed on 1st Execution");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage ( ITestResult iTestResult ) {
        System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

}