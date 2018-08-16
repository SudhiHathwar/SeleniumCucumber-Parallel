package com.test.Utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.utils.ExceptionUtil;
import com.test.Configuration.Hooks;
import com.test.Configuration.LocalDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import org.testng.xml.XmlClass;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class Listener implements ITestListener, IInvokedMethodListener {

    static HashMap<String, ExtentTest> extentMap = new HashMap();

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

            Object[] obj = iTestResult.getParameters();
            String methodName = iTestResult.getMethod().getMethodName();
            int counter = 0;
            while (obj.length != counter) {
                methodName = methodName + "_" + obj[counter];
                counter++;
            }

            ExtentTest child = extentMap.get(iInvokedMethod.getTestMethod().getRealClass().getSimpleName())
                    .createNode(iTestResult.getMethod().getMethodName() + "[" + obj[0] + "]")
                    .assignCategory(iInvokedMethod.getTestMethod().getRealClass().getSimpleName());

            ExtentTestManager.setExtentTest(child);

        }
    }

    @Override
    public void afterInvocation ( IInvokedMethod iInvokedMethod, ITestResult iTestResult ) {
    }

    @Override
    public void onStart ( ITestContext iTestContext ) {
        iTestContext.setAttribute("WebDriver", LocalDriverManager.getDriver());

        List<XmlClass> classnames = iTestContext.getCurrentXmlTest().getClasses();
        for (XmlClass classname : classnames) {
            String name = classname.getName().toString();
            String[] names = name.split("\\.");

            String deviceName = iTestContext.getCurrentXmlTest().getLocalParameters().get("deviceName");
            String browserName = iTestContext.getCurrentXmlTest().getLocalParameters().get("browserName");

            if (deviceName == null) {
                extentMap.put(names[names.length - 1], ExtentManager.getReporter().createTest(names[names.length - 1], "Running tests on Desktop browser:" + browserName));
            } else {

                extentMap.put(names[names.length - 1], ExtentManager.getReporter().createTest(names[names.length - 1], "Running tests on " + deviceName + " browser:" + browserName));

                //ExtentTestManager.createTest(names[names.length - 1]+ " - " + deviceName.toUpperCase(), "Running tests on " + browserName + " browser");
            }
        }

        try {
            FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + "/target/screenshot/" ));
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

        if (LocalDriverManager.getDriver() != null) {
            LocalDriverManager.getDriver().quit();
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

        System.out.println("I am onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
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