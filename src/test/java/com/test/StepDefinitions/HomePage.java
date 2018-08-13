package com.test.StepDefinitions;

import com.test.Configuration.BrowserInterface;
import com.test.Configuration.LocalDriverManager;
import com.test.Configuration.TestRunnerInfo;
import com.test.Utils.URLGetter;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.springframework.cglib.core.Local;

public class HomePage {

    WebDriver driver;
    public BrowserInterface runnerInfo;

    public HomePage( ){
        this.driver = LocalDriverManager.getDriver();
        runnerInfo = new TestRunnerInfo().getRunnerInfo();
    }

    @When("^User lands on home page$")
    public void loadURL () {
        runnerInfo.loadURL();
    }
}
