package com.test.flows;

import com.test.Configuration.BrowserInterface;
import com.test.Configuration.LocalDriverManager;
import com.test.Utils.URLGetter;
import org.openqa.selenium.WebDriver;

public class WebFlow implements BrowserInterface {

    private WebDriver driver;

    public WebFlow ( WebDriver driver ) {
        this.driver = driver;
    }

    @Override
    public void loadURL(){
        URLGetter getter = new URLGetter();

        LocalDriverManager.getDriver().get(getter.getURL("APP_URL"));
    }
}
