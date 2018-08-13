package com.test.Runner;

import com.test.Configuration.ServiceManager;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.test.Configuration.Hooks;

@CucumberOptions(
        monochrome = true,
        plugin = {"pretty", "html:target/cucumberHtmlReport"},
        features = "src/test/resources/features/",
        glue = {"com.test.StepDefinitions"},
        tags = {"@smoke"}
)
public class RunCukesTest extends Hooks {

    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass () {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "smoke", description = "Runs Cucumber Feature", dataProvider = "features")
    public void bookingTest ( CucumberFeatureWrapper cucumberFeature ) {
        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }

    @DataProvider
    public Object[][] features () {
        return testNGCucumberRunner.provideFeatures();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass () {
        testNGCucumberRunner.finish();

        if (ServiceManager.getService() != null) {
            ServiceManager.getService().stop();
        }
    }

}
