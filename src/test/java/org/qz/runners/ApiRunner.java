package org.qz.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/api_test.feature",
        glue = {"org.qz.steps"},
        tags = "@API",
        monochrome = true,
        plugin = {
                "pretty",
                "com.aventstack.chaintest.plugins.ChainTestCucumberListener:",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }
)
public class ApiRunner extends AbstractTestNGCucumberTests {

}
