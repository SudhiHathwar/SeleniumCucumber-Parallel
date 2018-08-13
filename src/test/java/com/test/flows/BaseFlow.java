package com.test.flows;

import org.openqa.selenium.WebDriver;

public class BaseFlow {

    private WebDriver driver;

    public BaseFlow ( WebDriver driver ) {
        this.driver = driver;
    }
}
