package com.test.Utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.utils.ExceptionUtil;
import com.test.Configuration.Hooks;
import com.test.Configuration.LocalDriverManager;
import cucumber.api.testng.CucumberFeatureWrapperImpl;
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

            Object[] obj = iTestResult.getParameters();
            String methodName = iTestResult.getMethod().getMethodName();
            int counter = 0;
            while (obj.length != counter) {
                methodName = methodName + "_" + obj[counter];
                counter++;
            }

            ExtentTest child = ExtentTestManager.getTest()
                    .createNode(iTestResult.getMethod().getMethodName() + "[" + obj[0] + "]")
                    .assignCategory(iInvokedMethod.getTestMethod().getRealClass().getSimpleName());

            ExtentTestManager.setExtentTest(child);

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
    }

    String deviceName;
    String browserName;

    @Override
    public void onStart ( ITestContext iTestContext ) {
        iTestContext.setAttribute("WebDriver", LocalDriverManager.getDriver());

        deviceName = iTestContext.getCurrentXmlTest().getLocalParameters().get("deviceName");
        browserName = iTestContext.getCurrentXmlTest().getLocalParameters().get("browserName");

        try {
            FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + "/target/screenshot/"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFinish ( ITestContext iTestContext ) {
        ExtentManager.getReporter().flush();
    }

    @Override
    public void onTestStart ( ITestResult iTestResult ) {

        String methodName = iTestResult.getMethod().getMethodName();
        String description = "Running tests on:" + deviceName;
        Object[] obj = iTestResult.getParameters();

        int counter = 0;
        while (obj.length != counter) {
            methodName = ((CucumberFeatureWrapperImpl) obj[0]).getCucumberFeature().getPath().replace(".feature", "");
            description = ((CucumberFeatureWrapperImpl) obj[0]).getCucumberFeature().getFeatureElements().get(0).getVisualName();
            counter++;
        }

        if (deviceName == null) {
            ExtentTestManager.setExtentTest(ExtentManager.getReporter().createTest(methodName + " - Desktop", description));
        } else {
            ExtentTestManager.setExtentTest(ExtentManager.getReporter().createTest(methodName + " - " + deviceName, description));
        }
    }

    @Override
    public void onTestSuccess ( ITestResult iTestResult ) {
        ExtentTestManager.getTest().log(Status.INFO, "Test passed");

        if (LocalDriverManager.getDriver() != null) {
            LocalDriverManager.getDriver().quit();
        }
    }

    @Override
    public void onTestFailure ( ITestResult iTestResult ) {

        Object testClass = iTestResult.getInstance();
        WebDriver webDriver = LocalDriverManager.getDriver();

        if (webDriver != null) {
            File scrFile = ((TakesScreenshot) webDriver)
                    .getScreenshotAs(OutputType.FILE);

            String failedScreen =
                    System.getProperty("user.dir") + "/target/screenshot/" + "/"
                            + testClass.toString() + currentDateAndTime() + "_" + "_failed" + ".png";

            try {
                FileUtils.copyFile(scrFile, new File(failedScreen));

                ExtentTestManager.getTest().log(Status.FAIL, "Test Failed" + "<br></br>");
                ExtentTestManager.getTest().log(Status.INFO, "<b>Failure Reason--->>> </b>  <br></br>" + iTestResult.getThrowable().getMessage());
                ExtentTestManager.getTest().log(Status.INFO, "<b>Exception Details--->>></b> + <br></br>" + "<pre>" + ExceptionUtil.getStackTrace(iTestResult.getThrowable()) + "</pre>");
                ExtentTestManager.getTest().addScreenCaptureFromPath(failedScreen, ExtentTestManager.getTest().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

            webDriver.quit();
        } else {
            ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
            ExtentTestManager.getTest().log(Status.INFO, "<b>Failure Reason--->>> </b>  <br></br>" + iTestResult.getThrowable().getMessage());
            ExtentTestManager.getTest().log(Status.INFO, "<b>Exception Details--->>></b> + <br></br>" + "<pre>" + ExceptionUtil.getStackTrace(iTestResult.getThrowable()) + "</pre>");

        }
    }

    public String currentDateAndTime () {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
        return now.format(dtf);
    }

    @Override
    public void onTestSkipped ( ITestResult iTestResult ) {
        ExtentManager.getReporter().removeTest(ExtentTestManager.getTest());
        IRetryAnalyzer retryAnalyzer = iTestResult.getMethod().getRetryAnalyzer();
        if (((Retry) retryAnalyzer).retryCountForTest == ((Retry) retryAnalyzer).maxRetryCount) {
        }

        ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped or failed on execution count: " + ((Retry) retryAnalyzer).retryCountForTest);

        if (LocalDriverManager.getDriver() != null) {
            LocalDriverManager.getDriver().quit();
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage ( ITestResult iTestResult ) {
        System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

}