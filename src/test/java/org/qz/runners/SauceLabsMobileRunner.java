package org.qz.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/saucelabs_cart.feature",
        glue = {"org.qz.steps", "org.qz.hooks"},
        tags = "@SauceLabsCart",
        monochrome = true,
        plugin = {"pretty", "com.aventstack.chaintest.plugins.ChainTestCucumberListener:",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)
public class SauceLabsMobileRunner extends AbstractTestNGCucumberTests {
}
