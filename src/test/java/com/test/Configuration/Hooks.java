package com.test.Configuration;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class Hooks {

    public URL startServer () {

        URL url = null;

        AppiumDriverLocalService service;

        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder()
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                .withIPAddress("127.0.0.1")
                .usingAnyFreePort();
        service = appiumServiceBuilder.build();

        ServiceManager.setService(service);

        ServiceManager.getService().start();


        if (service == null || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException("An appium server node is not started!");
        }

        url = service.getUrl();

        System.out.println("Server started Successfully on " + service.getUrl());

        return url;
    }

    public void launchDriver ( URL url, String platform, String platformVersion, String deviceName, String port, String udid, String browserName ) {

        if (platform.toLowerCase().equals("android")) {
            LocalDriverManager.setWebDriver(getAndroidDriver(url, platformVersion, deviceName, port, udid, browserName));

        } else if (platform.toLowerCase().equals("ios")) {
            LocalDriverManager.setWebDriver(getIOSDriver(url, platformVersion, deviceName, port, udid, browserName));

        } else {
            LocalDriverManager.setWebDriver(getDesktopDriver(browserName));

        }
    }

    private WebDriver getIOSDriver ( URL url, String platformVersion, String deviceName, String wdaLocalPort, String udid, String browserName ) {

        if (url != null) {

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.IOS);
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
            capabilities.setCapability("wdaLocalPort", Integer.valueOf(wdaLocalPort));
            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
            capabilities.setCapability(IOSMobileCapabilityType.PREVENT_WDAATTACHMENTS, false);
            capabilities.setCapability("udid", udid);
            capabilities.setCapability(IOSMobileCapabilityType.WDA_CONNECTION_TIMEOUT, 80000);
            capabilities.setCapability(IOSMobileCapabilityType.WDA_LAUNCH_TIMEOUT, 80000);
            capabilities.setCapability(IOSMobileCapabilityType.SHOULD_USE_SINGLETON_TESTMANAGER, false);
            capabilities.setCapability(IOSMobileCapabilityType.SIMPLE_ISVISIBLE_CHECK, true);
            capabilities.setCapability(IOSMobileCapabilityType.MAX_TYPING_FREQUENCY, 10);
            capabilities.setCapability(IOSMobileCapabilityType.START_IWDP, true);
            return new IOSDriver<>(url, capabilities);
        } else {
            System.out.println("You have to launch appium server before launching driver");
            return null;
        }
    }

    private WebDriver getAndroidDriver ( URL url, String platformVersion, String deviceName, String portNumber, String udid, String browserName ) {

        if (url != null) {

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "appium");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);

            if (!udid.equals("")) {
                capabilities.setCapability("udid", udid);
            }

            capabilities.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, Integer.valueOf(portNumber));
            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browserName);
            capabilities.setCapability("startIWDP", true);
            capabilities.setCapability(MobileCapabilityType.ORIENTATION, "PORTRAIT");
            capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
            return new AndroidDriver<>(url, capabilities);

        } else {
            System.out.println("You have to launch appium server before launching driver");
            return null;
        }
    }

    private WebDriver getDesktopDriver ( String browserName ) {

        WebDriver driver = null;
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.ALL);

        switch (browserName.toLowerCase()) {
            case "chrome":

                System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("start-fullscreen");
                driver = new ChromeDriver(options);
                break;

            case "firefox":

                System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver");
                driver = new FirefoxDriver();
                driver.manage().window().maximize();
                break;

            case "edge":
                System.setProperty("webdriver.edge.driver", "./drivers/MicrosoftWebDriver");
                driver = new EdgeDriver();
                break;

            case "ie":
                System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
                DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                        true);
                driver = new InternetExplorerDriver(capabilities);
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

                //driver.manage().window().setSize(new Dimension(1024,768));
                break;

            default:

                System.out.println("Please provide a browser type and re run your tests");
                return null;


        }

        return driver;
    }
}