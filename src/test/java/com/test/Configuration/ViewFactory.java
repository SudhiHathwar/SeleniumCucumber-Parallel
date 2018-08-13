package com.test.Configuration;

import com.test.flows.MobileFlow;
import com.test.flows.WebFlow;
import org.openqa.selenium.WebDriver;

public class ViewFactory extends BrowserHelper {

    private MobileFlow mobileFlow;
    private WebFlow webFlow;

    public ViewFactory ( WebDriver driver ) {
        super(driver);
    }

    public BrowserInterface getMobilePlatform ( String platform, WebDriver driver ) {
        if (platform == null) {
            return null;
        }
        return mobileFlow = new MobileFlow(driver);
    }
}